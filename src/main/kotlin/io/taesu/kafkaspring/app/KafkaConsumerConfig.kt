package io.taesu.kafkaspring.app

import io.taesu.kafkaspring.user.listener.UserCreatedEventMessage
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.support.serializer.JsonSerializer


/**
 * Created by taesu on 2024/03/09.
 *
 * @author Lee Tae Su
 * @version kafka-spring
 * @since kafka-spring
 */

@Configuration
class UserCreateNotifyConsumerConfig {
    // @Bean
    // fun messageListenerContainer(): KafkaMessageListenerContainer<String, UserCreatedEventMessage> {
    //     val containerProperties = ContainerProperties(UserEventProducer.TOPIC_USER_CREATED)
    //     containerProperties.messageListener = UserCreatedEventListener2()
    //
    //     val consumerFactory: ConsumerFactory<String, UserCreatedEventMessage> = consumerFactory()
    //     val listenerContainer = KafkaMessageListenerContainer(consumerFactory, containerProperties)
    //     listenerContainer.isAutoStartup = false
    //     listenerContainer.setBeanName("kafka-message-listener")
    //     return listenerContainer
    // }

    @Bean
    fun userCreatedNotifyContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, UserCreatedEventMessage> {
        return ConcurrentKafkaListenerContainerFactory<String, UserCreatedEventMessage>().apply {
            this.consumerFactory = consumerFactory()
            this.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE
            this.setConcurrency(2)
        }
    }

    fun consumerFactory(): ConsumerFactory<String, UserCreatedEventMessage> {
        val deserializer = JsonDeserializer<UserCreatedEventMessage>()
        deserializer.trustedPackages("*")

        val props: Map<String, Any> = consumerConfig(
            "user-create-notify",
            deserializer,
            "user-created:io.taesu.kafkaspring.user.listener.UserCreatedEventMessage"
        )

        return DefaultKafkaConsumerFactory(props, StringDeserializer(), deserializer)
    }
}

@Configuration
class TeamCreateNotifyConsumerConfig {
    @Bean
    fun teamCreatedContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, UserCreatedEventMessage> {
        return ConcurrentKafkaListenerContainerFactory<String, UserCreatedEventMessage>().apply {
            this.consumerFactory = consumerFactory()
            this.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE
        }
    }

    fun consumerFactory(): ConsumerFactory<String, UserCreatedEventMessage> {
        val deserializer = JsonDeserializer<UserCreatedEventMessage>()
        deserializer.trustedPackages("*")

        val props: Map<String, Any> = consumerConfig(
            "team-create-notify",
            deserializer,
            "team-created:io.taesu.kafkaspring.team.listener.TeamCreatedEventMessage"
        )

        return DefaultKafkaConsumerFactory(props, StringDeserializer(), deserializer)
    }
}

fun consumerConfig(
    groupId: String,
    deserializer: JsonDeserializer<*>,
    typeMapping: String
): Map<String, Any> {
    return mapOf(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092,localhost:9093,localhost:9094",
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to deserializer,
        ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to false,
        ConsumerConfig.GROUP_ID_CONFIG to groupId,
        JsonSerializer.TYPE_MAPPINGS to typeMapping,
    )
}
