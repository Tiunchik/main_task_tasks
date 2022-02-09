package manager.task.tasks.jms


import manager.task.tasks.services.QUEUE_HEADER
import manager.task.tasks.services.QUEUE_NAME
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Service
import javax.jms.Message

@Service
class BroadcastEventListener {

    /**
     * @JmsListener(
     * id = "id листенера",
     * destination = "адрес",
     * subscription = "имя подписчика",
     * selector = "myHeader in ('none', 'any')"
     * containerFactory = "factory",
     * concurrency = "1-2")
     */

    @JmsListener(
        id = "\${application.name}",
        destination = QUEUE_NAME,
        containerFactory = "artemisJmsListenerContainerFactory",
        subscription = "\${application.name}",
        selector = "$QUEUE_HEADER in ('\${application.name}')"
    )
    fun onMessage(message: Message): Unit {
        println("Receive message from listener - ${message.getBody(String::class.java)}")
    }

}
