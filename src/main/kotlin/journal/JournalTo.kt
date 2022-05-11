package journal

import java.lang.reflect.Method

/**
 * todo - repeatable
 * todo - exclude methods
 * todo - catch exception -> journaling exceptions
 * todo -
 * todo -
 * todo -
 */
// todo - https://stackoverflow.com/questions/2011089/aspectj-pointcut-for-all-methods-of-a-class-with-specific-annotation
@Target(AnnotationTarget.CLASS)
@Retention
annotation class JournalTo(
    /**
     * Spring bean name that Support journaling. Default = "artemis"
     */
    val journalingToBeanName: String = "artemis-journaling"

)

interface Journaler {
    fun invoke(context: JournalToContext)
}

data class JournalToContext(
    val proxiedClazz: Class<out Any>,
    val beanName: String,
    val proxiedMethod: Method,
    val proxiedMethodArgs: Array<out Any>?,
    val returnValue: Any?,
    val exception: Exception?
)


