package manager.task.tasks.services

import manager.task.tasks.domains.Task
import manager.task.tasks.repositories.TasksRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

const val QUEUE_NAME = "INT-MY-ADDRESS"

@Service
class TaskCrudServices(
    private val taskRepository: TasksRepository,
    private val jmsSenderService: JmsSenderService
) {

    fun getAllTasks(): Flux<Task> {
        return taskRepository.findAll().also {
            jmsSenderService.sendMessage(QUEUE_NAME, "Message send", mapOf("myHeader" to "any"))
            jmsSenderService.sendMessage(QUEUE_NAME, "Message send", mapOf("myHeader" to "none"))
        }
    }

    fun getTask(id: Long) = taskRepository.findById(id)

    fun saveTask(task: Task) = taskRepository.save(task);

    fun updateTask(task: Task) = taskRepository.save(task)

    fun deleteTask(id: Long) = taskRepository.deleteById(id)
}

