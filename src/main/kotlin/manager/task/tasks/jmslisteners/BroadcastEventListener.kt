package manager.task.tasks.jmslisteners


import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Service
import javax.jms.Message

const val QUEUE_NAME = "INT-MY-ADDRESS"
const val QUEUE_HEADER = "FILTER"

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
        selector = "$QUEUE_HEADER in ('ALL', '\${application.name}')"
    )
    fun onMessage(message: Message): Unit {
        println("Receive message from listener - ${message.getBody(String::class.java)}")
    }

}
