package manager.task.tasks.dto

import manager.task.tasks.domains.Task

data class TaskDto(
    val id: Long = 0,
    val name: String,
    val description: String = "",
)

fun Task.toTaskDto(): TaskDto {
    return TaskDto(
        id = id,
        name = name,
        description = description
    )
}
