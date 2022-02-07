package manager.task.tasks.jms

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Service

@Service
class JmsSender(
    @Qualifier("artemisJmsTemplate")
    private val jmsTemplate: JmsTemplate
) {

    fun sendMessage(queue: String, message: Any): Unit {
        jmsTemplate.convertAndSend(queue, message)
    }

}
