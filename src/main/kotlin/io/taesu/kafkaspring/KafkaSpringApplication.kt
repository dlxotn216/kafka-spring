package io.taesu.kafkaspring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkaSpringApplication

fun main(args: Array<String>) {
    runApplication<KafkaSpringApplication>(*args)
}
