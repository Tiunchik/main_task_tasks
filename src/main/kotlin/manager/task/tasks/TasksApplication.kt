package manager.task.tasks

import manager.task.tasks.configuration.jms.ArtemisConfigurationProperties
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

private val logger = KotlinLogging.logger {}

@SpringBootApplication
@EnableConfigurationProperties(ArtemisConfigurationProperties::class)
@ComponentScan("journal", "manager.task.tasks")
class TasksApplication

fun main(args: Array<String>) {
    runApplication<TasksApplication>(*args)
    logger.info { "Application Start OK!" }
}
