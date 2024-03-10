package io.taesu.kafkaspring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkaSpringApplication

// @Component
// class KafkaMessageListenerContainerRunner(
//     private val container: KafkaMessageListenerContainer<String, UserCreatedEventMessage>
// ): ApplicationRunner {
//     override fun run(args: ApplicationArguments?) {
//         container.start()
//     }
// }

fun main(args: Array<String>) {
    runApplication<KafkaSpringApplication>(*args)
}
