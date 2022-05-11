package journal

import manager.task.tasks.dto.JmsWrapper
import manager.task.tasks.jmslisteners.QUEUE_HEADER
import manager.task.tasks.jmslisteners.QUEUE_NAME
import manager.task.tasks.services.JmsSenderService
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

@Component
class JournalToAnnotationBeanPostProcessor(
    private val applicationContext: ApplicationContext,
    private val jmsSenderService: JmsSenderService,
    @Value("\${application.name}") val appName: String,
    @Value("\${journaling.enable}") var enable: Boolean,
) : BeanPostProcessor {
    val beanNameAndOriginalClazz = mutableMapOf<String, Class<out Any>>()


    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if (bean.javaClass.isAnnotationPresent(JournalTo::class.java))
            beanNameAndOriginalClazz[beanName] = bean::class.java
        return bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? =
        beanNameAndOriginalClazz[beanName]?.let { makeProxyWithJournaling(bean, it, beanName) } ?: bean

    private fun makeProxyWithJournaling(bean: Any, originalClass: Class<out Any>, beanName: String) =
        Proxy.newProxyInstance(
            originalClass.classLoader, originalClass.interfaces,
            JournalingToProxyInvocationHandler(
                originalClass, bean, beanName, enable,
                findJournalerForBean(originalClass, beanName)
            )
        )

    private fun findJournalerForBean(originalClass: Class<out Any>, beanName: String): Journaler {
        return originalClass::class.annotations.find { it == JournalTo::class }?.let { annotation ->
            (annotation as JournalTo).run {
                println("found! $this")
                this.journalingToBeanName.apply {
                    if (this.isBlank()) throw IllegalArgumentException(
                        "For bean $beanName for annotation ${JournalTo::class.qualifiedName} "
                                + "parameter \"journalingToBeanName\" is required but provided = EMPTY_STRING"
                    )
                }.run { applicationContext.getBean(journalingToBeanName) as Journaler }
            }
        } ?: throw IllegalArgumentException("Annotation not found")
    }

    // todo - вынести в бин artemis
    private fun sendCrudServiceBroadcastMessage(operation: String, task: Any?) {
        println("BPP - proxy send")
        jmsSenderService.sendMulticastMessage(
            queue = QUEUE_NAME,
            message = JmsWrapper(
                version = "v1",
                event = operation,
                payload = task
            ),
            headers = mapOf(QUEUE_HEADER to appName)
        )

    }

    // Extracted Lambda - only for easy debugging in reflection
    class JournalingToProxyInvocationHandler(
        private val originalClass: Class<out Any>,
        private val bean: Any,
        private val beanName: String,
        private val enable: Boolean,
        private val journaler: Journaler
    ) : InvocationHandler {
        override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {

            // Kotlin Array != java vararg - if not null - convert to vararg
            // * - is spread operator AND can't be applied to Nullable type
            // https://stackoverflow.com/questions/45854994/convert-kotlin-array-to-java-varargs
            val rsl = if (args == null) method?.invoke(bean, null) else method?.invoke(bean, *args)

            println("${this::class.simpleName} # enable=$enable :: return=$rsl")
//            if (enable) method?.name?.let { sendMsg(it, args?.get(0)) }
            if (enable) journaler.invoke(JournalToContext(originalClass, beanName, method!!, args, rsl, null))
            return rsl
        }
    }
}