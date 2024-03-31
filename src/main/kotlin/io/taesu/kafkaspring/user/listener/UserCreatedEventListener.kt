package io.taesu.kafkaspring.user.listener

import io.taesu.kafkaspring.user.command.UserEventProducer.Companion.TOPIC_USER_CREATED
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.AcknowledgingMessageListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component


/**
 * Created by taesu on 2024/03/09.
 *
 * @author Lee Tae Su
 * @version kafka-spring
 * @since kafka-spring
 */
@Component
class UserCreatedEventListener {
    @KafkaListener(
        topics = [TOPIC_USER_CREATED],
        containerFactory = "userCreatedNotifyContainerFactory",
    )
    fun onMessage(
        consumerRecord: ConsumerRecord<String, UserCreatedEventMessage>,
        acknowledgment: Acknowledgment
    ) {
        println("Received Message in group: $consumerRecord")
        acknowledgment.acknowledge()
    }
}

// @Component
// class UserCreatedEventListener2: AcknowledgingMessageListener<String, UserCreatedEventMessage> {
//     override fun onMessage(
//         consumerRecord: ConsumerRecord<String, UserCreatedEventMessage>,
//         acknowledgment: Acknowledgment?
//     ) {
//         println("Received Message in group: $consumerRecord")
//         acknowledgment?.acknowledge()
//     }
// }
//
