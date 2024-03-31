package io.taesu.kafkaspring.user.listener

/**
 * Created by taesu on 2024/03/10.
 *
 * @author Lee Tae Su
 * @version kafka-spring
 * @since kafka-spring
 */
data class UserCreatedEventMessage(
    val name: String,
    val email: String,
)
