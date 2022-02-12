package manager.task.tasks.configuration.jackson

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.support.converter.MappingJackson2MessageConverter
import org.springframework.jms.support.converter.MessageConverter
import org.springframework.jms.support.converter.MessageType

@Configuration
class JacksonConfiguration {

    @Bean
    fun jacksonMessageConverter(mapper: ObjectMapper): MessageConverter {
        return MappingJackson2MessageConverter().apply {
            setTargetType(MessageType.TEXT)
            setObjectMapper(mapper)
        }
    }

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper().apply {
            findAndRegisterModules()
            setSerializationInclusion(JsonInclude.Include.NON_NULL)
            configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
            configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true)
        }
    }
}
