package io.taesu.kafkaspring.app

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer
import org.springframework.retry.annotation.EnableRetry

/**
 * Created by itaesu on 2024/03/05.
 *
 * @author Lee Tae Su
 * @version kafka-spring
 * @since kafka-spring
 */
@Configuration
@EnableRetry
class KafkaProducerConfig {
    @Bean
    fun producerFactory(): ProducerFactory<String, Any> {
        val config: Map<String, Any> = mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092,localhost:9093,localhost:9094",
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java,

            // 재시도 관련 설정
            ProducerConfig.RETRIES_CONFIG to 10,
            ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG to 120000,
            ProducerConfig.LINGER_MS_CONFIG to 0,
            ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG to 30000,
            ProducerConfig.RETRY_BACKOFF_MS_CONFIG to 1000,
            ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG to true,
            ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION to 5,

            ProducerConfig.ACKS_CONFIG to "all",
        )
        return DefaultKafkaProducerFactory(config)
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, Any> {
        return KafkaTemplate(producerFactory())
    }
}
