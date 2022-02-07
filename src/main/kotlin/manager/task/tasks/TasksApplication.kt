package manager.task.tasks

import manager.task.tasks.configuration.jms.ArtemisConfiguration
import manager.task.tasks.configuration.jms.ArtemisConfigurationProperties
import manager.task.tasks.jms.ArtemusListener
import manager.task.tasks.jms.JmsSender
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(ArtemisConfigurationProperties::class)
class TasksApplication

fun main(args: Array<String>) {
    runApplication<TasksApplication>(*args)
}
