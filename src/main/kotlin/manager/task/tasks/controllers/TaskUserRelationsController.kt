package manager.task.tasks.controllers

import manager.task.tasks.domains.Task
import manager.task.tasks.domains.TaskUserRelation
import manager.task.tasks.repositories.TaskUserRelationsRepository
import manager.task.tasks.services.TaskUserRelationsCrudService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/tasks/users/relations")
class TaskUserRelationsController(
    private val taskUserRelCrudService: TaskUserRelationsCrudService
) {

    @GetMapping
    fun getAllTaskUserRelations(): Flux<TaskUserRelation> = taskUserRelCrudService.getAllTaskUserRelations()

    @GetMapping("/{id}")
    fun getTaskUserRelations(@PathVariable("id") id: Long): Mono<TaskUserRelation> =
        taskUserRelCrudService.getTaskUserRelations(id)

    @PostMapping
    fun saveTaskUserRelations(@RequestBody task: TaskUserRelation): Mono<TaskUserRelation> =
        taskUserRelCrudService.saveTaskUserRelations(task);

    @PutMapping
    fun updateTaskUserRelations(@RequestBody task: TaskUserRelation): Mono<TaskUserRelation> =
        taskUserRelCrudService.updateTaskUserRelations(task)

    @DeleteMapping("/{id}")
    fun deleteTaskUserRelations(@PathVariable("id") id: Long): Mono<Void> =
        taskUserRelCrudService.deleteTaskUserRelations(id)

}
