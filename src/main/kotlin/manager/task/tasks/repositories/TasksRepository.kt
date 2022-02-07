package manager.task.tasks.repositories

import manager.task.tasks.domains.Task
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface TasksRepository : ReactiveCrudRepository<Task, Long> {
}
