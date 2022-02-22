package manager.task.tasks.services

import com.fasterxml.jackson.databind.ObjectMapper
import manager.task.tasks.domains.Task
import manager.task.tasks.dto.JmsWrapper
import manager.task.tasks.dto.toTaskDto
import manager.task.tasks.jmslisteners.QUEUE_HEADER
import manager.task.tasks.jmslisteners.QUEUE_NAME
import manager.task.tasks.repositories.TasksRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.io.IOException


@Service
class TaskCrudServices(
    private val taskRepository: TasksRepository,
    private val jmsSenderService: JmsSenderService,
    private val jacksonMapper: ObjectMapper,
    @Value("\${application.name}") private val appName: String
) {

    fun getAllTasks(): Flux<Task> = taskRepository.findAll()

    fun getTask(id: Long) = taskRepository.findById(id)

    fun saveTask(task: Task) = taskRepository
        .save(task)
        .doOnNext() {
            sendCrudServiceBroadcastMessage("SAVE", it)
        }

    fun updateTask(task: Task) = taskRepository
        .save(task)
        .doOnNext {
            sendCrudServiceBroadcastMessage("UPDATE", it)
        }

    fun deleteTask(id: Long) = taskRepository
        .deleteById(id)
        .doOnSuccess {
            sendCrudServiceBroadcastMessage("DELETE", Task(id = id))
        }

    private fun sendCrudServiceBroadcastMessage(operation: String, task: Task): Unit {
        val wrappedDto = JmsWrapper(
            version = "v1",
            event = operation,
            payload = task.toTaskDto()
        )
        jmsSenderService.sendMulticastMessage(
            queue = QUEUE_NAME,
            message = wrappedDto,
            headers = mapOf(QUEUE_HEADER to appName))
    }
}

