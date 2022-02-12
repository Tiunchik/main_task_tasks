package manager.task.tasks.repositories

import manager.task.tasks.domains.TaskUserRelation
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface TaskUserRelationsRepository: ReactiveCrudRepository<TaskUserRelation, Long> {
}
