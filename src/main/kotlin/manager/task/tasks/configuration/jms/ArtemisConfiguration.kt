package manager.task.tasks.configuration.jms

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.config.DefaultJmsListenerContainerFactory
import org.springframework.jms.core.JmsTemplate

@Configuration
class ArtemisConfiguration(
    @Qualifier("artemisConfigurationProperties") private val props: ArtemisConfigurationProperties
) {

    @Bean
    fun artemisConnectionFactory(): ActiveMQConnectionFactory {
        return ActiveMQConnectionFactory().apply {
            setBrokerURL(props.url)
            user = props.user
            password = props.password
        }
    }

    @Bean
    fun artemisJmsTemplate(
        @Qualifier("artemisConnectionFactory") factory: ActiveMQConnectionFactory
    ): JmsTemplate {
        return JmsTemplate().apply {
            connectionFactory = factory
        }
    }

    @Bean
    fun artemisJmsListenerContainerFactory(
        @Qualifier("artemisConnectionFactory") factory: ActiveMQConnectionFactory
    ): DefaultJmsListenerContainerFactory {
        return DefaultJmsListenerContainerFactory().apply {
            setConnectionFactory(factory)
            setConcurrency(props.concurrency)
        }
    }

}
