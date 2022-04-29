package manager.task.tasks.domains;

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

const val STANDARD_DAY_PENDING = 5L;

@Table
data class Task (
    @Id
    var id: Long = 0,
    var name: String = "none",
    var description: String = "none",
)
