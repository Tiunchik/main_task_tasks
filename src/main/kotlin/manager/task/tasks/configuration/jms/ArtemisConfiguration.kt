package manager.task.tasks.configuration.jms

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.jms.config.DefaultJmsListenerContainerFactory
import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.support.converter.MessageConverter

@Configuration
@Profile("!test")
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
    fun artemisQueueJmsTemplate(
        @Qualifier("artemisConnectionFactory") factory: ActiveMQConnectionFactory,
        @Qualifier("jacksonMessageConverter") converter: MessageConverter
    ): JmsTemplate {
        return JmsTemplate().apply {
            connectionFactory = factory
            messageConverter = converter
        }
    }

    @Bean
    fun artemisTopicJmsTemplate(
        @Qualifier("artemisConnectionFactory") factory: ActiveMQConnectionFactory,
        @Qualifier("jacksonMessageConverter") converter: MessageConverter
    ): JmsTemplate {
        return JmsTemplate().apply {
            connectionFactory = factory
            connectionFactory = factory
            messageConverter = converter
            isPubSubDomain = true
        }
    }

    @Bean
    fun artemisJmsListenerContainerFactory(
        @Qualifier("artemisConnectionFactory") factory: ActiveMQConnectionFactory,
        @Qualifier("jacksonMessageConverter") converter: MessageConverter
    ): DefaultJmsListenerContainerFactory {
        return DefaultJmsListenerContainerFactory().apply {
            setSubscriptionDurable(true)
            setSubscriptionShared(true)
            setPubSubDomain(true)
            setConnectionFactory(factory)
            setMessageConverter(converter)
            setConcurrency(props.concurrency)
        }
    }

}
