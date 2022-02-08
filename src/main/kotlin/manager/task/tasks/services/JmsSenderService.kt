package manager.task.tasks.services

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.core.MessagePostProcessor
import org.springframework.stereotype.Service

@Service
class JmsSenderService(
    @Qualifier("artemisTopicJmsTemplate")
    private val topicJmsTemplate: JmsTemplate,
    @Qualifier("artemisQueueJmsTemplate")
    private val queueJmsTemplate: JmsTemplate
) {

    fun sendMulticastMessage(queue: String, message: Any): Unit {
        topicJmsTemplate.convertAndSend(queue, message)
    }

    fun sendMulticastMessage(queue: String, message: Any, map: Map<String, String>): Unit {
        topicJmsTemplate.convertAndSend(queue, message, MessagePostProcessor {
            map.entries.forEach { entity -> it.setStringProperty(entity.key, entity.value) }
            return@MessagePostProcessor it
        })
    }

    fun sendAnycastMessage(queue: String, message: Any): Unit {
        topicJmsTemplate.convertAndSend(queue, message)
    }

    fun sendAnycastMessage(queue: String, message: Any, map: Map<String, String>): Unit {
        topicJmsTemplate.convertAndSend(queue, message, MessagePostProcessor {
            map.entries.forEach { entity -> it.setStringProperty(entity.key, entity.value) }
            return@MessagePostProcessor it
        })
    }
}


