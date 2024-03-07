package io.taesu.kafkaspring.user.command

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.Duration

/**
 * Created by itaesu on 2024/03/05.
 *
 * @author Lee Tae Su
 * @version kafka-spring
 * @since kafka-spring
 */
@SpringBootTest
class UserEventProducerTest {
    @Autowired
    private lateinit var userEventProducer: UserEventProducer

    @Test
    fun `fireAndForget test`() {
        userEventProducer.fireAndForget(UserCreatedEvent("fireAndForget", "taesulee93@gmail.com"))
    }

    @Test
    fun `sync test`() {
        println(userEventProducer.sync(UserCreatedEvent("sync", "taesulee93@gmail.com")))
    }

    @Test
    fun `async test`() {
        userEventProducer.async(UserCreatedEvent("async", "taesulee93@gmail.com"),
                                { println(it) })
        {
            println(it)
        }
    }

    @Test
    fun `send with key test`() {
        repeat(10) {
            userEventProducer.sendWithKey(
                UserCreatedEvent("with-key-$it", "taesulee93@gmail.com"),
                it.toString(),
                { println(it) }
            )
            {
                println(it)
            }
        }

        Thread.sleep(Duration.ofSeconds(30))
    }

    @Test
    fun `send with key1 test`() {
        repeat(10) {
            userEventProducer.sendWithKey(
                UserCreatedEvent("with-key-1", "taesulee93@gmail.com"), "1",
                { println(it) }
            )
            {
                println(it)
            }
        }
    }

    @Test
    fun `send without key test`() {
        repeat(10) {
            userEventProducer.async(UserCreatedEvent("without-key-$it", "taesulee93@gmail.com"),
                                    { println(it) }
            )
            {
                println(it)
            }
        }
    }
}
