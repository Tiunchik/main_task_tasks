package manager.task.tasks.jms

import jakarta.jms.TextMessage
import manager.task.tasks.services.QUEUE_NAME
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Service

@Service
class ArtemusListener {

    @JmsListener(destination = QUEUE_NAME, containerFactory = "artemisJmsListenerContainerFactory")
    fun onMessage(message: TextMessage): Unit {
        println(message.text)
    }

}
