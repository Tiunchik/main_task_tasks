package manager.task.tasks.controllers;

import manager.task.tasks.domains.Task
import manager.task.tasks.services.TaskCrudService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/tasks")
@CrossOrigin(origins = ["http://localhost:3000"])
class TaskController(
    private val taskCrudService: TaskCrudService
) {

    @GetMapping
    fun getAllTasks(): Flux<Task> = taskCrudService.getAllTasks()

    @GetMapping("/{id}")
    fun getTask(@PathVariable("id") id: Long): Mono<Task> = taskCrudService.getTask(id)

    @PostMapping
    fun saveTask(@RequestBody task: Task): Mono<Task> = taskCrudService.saveTask(task);

    @PutMapping
    fun updateTask(@RequestBody task: Task): Mono<Task> = taskCrudService.updateTask(task)

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable("id") id: Long): Mono<Void> = taskCrudService.deleteTask(id)

}
