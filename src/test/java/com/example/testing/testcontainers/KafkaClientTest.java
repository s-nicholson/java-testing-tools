package com.example.testing.testcontainers;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.awaitility.Awaitility.await;

@Tag("testcontainers")
class KafkaClientTest {
    private static KafkaContainer kafka;
    private static KafkaConsumer<String, String> consumer;

    @BeforeAll
    static void setup() {
        kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"))
                .withEnv("KAFKA_AUTO_CREATE_TOPICS_ENABLE", "true")
                .withEnv("KAFKA_CREATE_TOPICS", "kafka_topic");
        kafka.start();

        consumer = new KafkaConsumer<>(Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers(),
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName(),
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName(),
                ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString(),
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"
        ));
        consumer.subscribe(List.of("test-topic"));
    }

    @Test
    void test() {
        new KafkaClient(kafka.getBootstrapServers())
                .publishMessage("test-topic", "hello world");

        await().atMost(Duration.ofSeconds(10L))
                .pollInterval(Duration.ofSeconds(1L))
                .pollDelay(Duration.ofSeconds(1L))
                .ignoreExceptions()
                .untilAsserted(() -> {
                    var pollResult = consumer.poll(Duration.ofMillis(100));
                    pollResult.forEach(r -> {
                        assertThat(r.topic()).isEqualTo("test-topic");
                        assertThat(r.value()).isEqualTo("hello world");
                    });
                });
    }

    @AfterAll
    static void tearDown() {
        kafka.stop();
    }
}
