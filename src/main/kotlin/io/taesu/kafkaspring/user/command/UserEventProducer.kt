package io.taesu.kafkaspring.user.command

import kotlinx.coroutines.runBlocking
import org.apache.kafka.clients.producer.Callback
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Service
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Created by itaesu on 2024/03/05.
 *
 * @author Lee Tae Su
 * @version kafka-spring
 * @since kafka-spring
 */
@Service
class UserEventProducer(private val kafkaTemplate: KafkaTemplate<String, Any>) {
    fun fireAndForget(userCreatedEvent: UserCreatedEvent) {
        kafkaTemplate.send(TOPIC_USER_CREATED, userCreatedEvent)
    }

    fun sync(userCreatedEvent: UserCreatedEvent): SendResult<String, Any>? {
        return kafkaTemplate.send(TOPIC_USER_CREATED, userCreatedEvent).get()
    }

    fun async(
        userCreatedEvent: UserCreatedEvent,
        accept: (t: SendResult<String, Any>) -> Unit,
        exceptionally: (t: Throwable) -> Unit,
    ) {
        kafkaTemplate.send(TOPIC_USER_CREATED, userCreatedEvent)
            .thenAccept(accept)
            .exceptionally {
                exceptionally(it)
                null
            }
    }

    fun sendWithKey(
        userCreatedEvent: UserCreatedEvent,
        key: String,
        accept: (t: SendResult<String, Any>) -> Unit,
        exceptionally: (t: Throwable) -> Unit,
    ) {
        kafkaTemplate.send(TOPIC_USER_CREATED, key, userCreatedEvent)
            .thenAccept(accept)
            .exceptionally {
                exceptionally(it)
                null
            }
    }

    fun sendWithSuspend(
        userCreatedEvent: UserCreatedEvent,
        key: String,
        completeCallback: (t: SendResult<String, Any>, u: Throwable) -> Unit,
    ) {
        kafkaTemplate

    }

    companion object {
        const val TOPIC_USER_CREATED = "user-created"
    }
}

data class UserCreatedEvent(
    val name: String,
    val email: String,
)
