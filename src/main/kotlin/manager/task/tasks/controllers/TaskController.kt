package manager.task.tasks.controllers;

import manager.task.tasks.domains.Task
import manager.task.tasks.services.TaskCrudServices
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/tasks")
class TaskController(
    private val taskCrudServices: TaskCrudServices
) {

    @GetMapping
    fun getAllTasks(): Flux<Task> = taskCrudServices.getAllTasks()

    @GetMapping("/{id}")
    fun getTask(@PathVariable("id") id: Long): Mono<Task> = taskCrudServices.getTask(id)

    @PostMapping
    fun saveTask(@RequestBody task: Task): Mono<Task> = taskCrudServices.saveTask(task);

    @PutMapping
    fun updateTask(@RequestBody task: Task): Mono<Task> = taskCrudServices.updateTask(task)

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable("id") id: Long): Mono<Void> = taskCrudServices.deleteTask(id)

}
