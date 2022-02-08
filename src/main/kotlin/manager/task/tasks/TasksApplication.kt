package manager.task.tasks

import manager.task.tasks.configuration.jms.ArtemisConfigurationProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(ArtemisConfigurationProperties::class)
class TasksApplication

fun main(args: Array<String>) {
    runApplication<TasksApplication>(*args)
}
