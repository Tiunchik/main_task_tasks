package manager.task.tasks.domains

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("task_user_relations")
class TaskUserRelation(
    @Id
    var id: Long = 0,
    val taskId: Long,
    val userId: Long
)

