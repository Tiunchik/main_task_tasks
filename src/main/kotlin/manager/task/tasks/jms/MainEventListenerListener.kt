package manager.task.tasks.jms


import manager.task.tasks.services.QUEUE_NAME
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Service
import javax.jms.Message

@Service
class MainEventListenerListener {

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
        id = "NAN",
        destination = QUEUE_NAME,
        containerFactory = "artemisJmsListenerContainerFactory",
        subscription = "TESTSUBSCRIBER",
        selector = "myHeader in ('none', 'any')"
    )
    fun onMessage(message: Message): Unit {
        message.getStringProperty("myHeader").also { println(it) }
        println("Receive message from first listener - ${message.getBody(String::class.java)}")
    }

}
