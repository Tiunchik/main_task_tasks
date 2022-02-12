package manager.task.tasks.services

import manager.task.tasks.domains.TaskUserRelation
import manager.task.tasks.repositories.TaskUserRelationsRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class TaskUserRelationsCrudService(
    private val taskUserRelRepo: TaskUserRelationsRepository
) {

    fun getAllTaskUserRelations(): Flux<TaskUserRelation> = taskUserRelRepo.findAll()

    fun getTaskUserRelations(id: Long) = taskUserRelRepo.findById(id)

    fun saveTaskUserRelations(relations: TaskUserRelation) = taskUserRelRepo.save(relations)

    fun updateTaskUserRelations(relations: TaskUserRelation) = taskUserRelRepo.save(relations)

    fun deleteTaskUserRelations(id: Long) = taskUserRelRepo.deleteById(id)

}
