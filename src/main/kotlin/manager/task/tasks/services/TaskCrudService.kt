package manager.task.tasks.services

import journal.JournalTo
import manager.task.tasks.domains.Task
import manager.task.tasks.dto.JmsWrapper
import manager.task.tasks.dto.toTaskDto
import manager.task.tasks.jmslisteners.QUEUE_HEADER
import manager.task.tasks.jmslisteners.QUEUE_NAME
import manager.task.tasks.repositories.TasksRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * https://docs.spring.io/spring-data/r2dbc/docs/current/reference/html/#project-metadata - про аудит в r2dbc
 */
interface TaskCrudService {
    fun getAllTasks(): Flux<Task>
    fun getTask(id: Long): Mono<Task>
    fun saveTask(task: Task): Mono<Task>
    fun updateTask(task: Task): Mono<Task>
    fun deleteTask(id: Long): Mono<Void>
}

@Service
@Transactional
@JournalTo
class TaskCrudServiceImpl(
    private val taskRepository: TasksRepository,
    private val jmsSenderService: JmsSenderService,
    @Value("\${application.name}") private val appName: String
) : TaskCrudService {

    override fun getAllTasks(): Flux<Task> = taskRepository.findAll()

    override fun getTask(id: Long) = taskRepository.findById(id)

    override fun saveTask(task: Task) = taskRepository
        .save(task)
//        .doOnNext() {
//            sendCrudServiceBroadcastMessage("SAVE", it)
//        }

    override fun updateTask(task: Task) = taskRepository
        .save(task)
//        .doOnNext {
//            sendCrudServiceBroadcastMessage("UPDATE", it)
//        }

    override fun deleteTask(id: Long) = taskRepository
        .deleteById(id)
//        .doOnSuccess {
//            sendCrudServiceBroadcastMessage("DELETE", Task(id = id))
//        }


//    private fun sendCrudServiceBroadcastMessage(operation: String, task: Task): Unit {
//        jmsSenderService.sendMulticastMessage(
//            queue = QUEUE_NAME,
//            message = JmsWrapper(
//                version = "v1",
//                event = operation,
//                payload = task.toTaskDto()
//            ),
//            headers = mapOf(QUEUE_HEADER to appName)
//        )
//    }
}

