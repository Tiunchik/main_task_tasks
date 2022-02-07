package manager.task.tasks.services

import manager.task.tasks.domains.Task
import manager.task.tasks.jms.JmsSender
import manager.task.tasks.repositories.TasksRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

const val QUEUE_NAME = "INT-MY-ADDRESS"

@Service
class TaskCrudServices(
    private val taskRepository: TasksRepository,
    private val jmsSender: JmsSender
) {

    fun getAllTasks(): Flux<Task> {
        return taskRepository.findAll().also {
            jmsSender.sendMessage(QUEUE_NAME, "Message send")
        }
    }

    fun getTask(id: Long) = taskRepository.findById(id)

    fun saveTask(task: Task) = taskRepository.save(task);

    fun updateTask(task: Task) = taskRepository.save(task)

    fun deleteTask(id: Long) = taskRepository.deleteById(id)
}

