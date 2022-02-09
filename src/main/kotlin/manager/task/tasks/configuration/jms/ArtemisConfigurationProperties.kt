package manager.task.tasks.configuration.jms

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration


@Configuration
@ConfigurationProperties("jms")
class ArtemisConfigurationProperties {
    lateinit var url: String
    lateinit var user: String
    lateinit var password: String
    lateinit var concurrency: String
}
