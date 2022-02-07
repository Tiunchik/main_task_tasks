package manager.task.tasks.configuration.jms

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
@ConfigurationProperties("myjms")
class ArtemisConfigurationProperties {
    lateinit var url: String
    lateinit var user: String
    lateinit var password: String
    lateinit var concurrency: String
}
