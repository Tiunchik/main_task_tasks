package manager.task.tasks.dto

import com.fasterxml.jackson.databind.JsonNode

data class JmsWrapper(
    val version: String,
    val event: String,
    val payload: Any
)
