package manager.task.tasks.dto


data class JmsWrapper<T>(
    val version: String,
    val event: String,
    val payload: T
)
