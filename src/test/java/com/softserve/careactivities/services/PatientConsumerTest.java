package com.softserve.careactivities.services;

import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
@ActiveProfiles("test")
class PatientConsumerTest {

    @ClassRule
    public static KafkaContainer kafka = new KafkaContainer(DockerImageName
            .parse("confluentinc/cp-kafka:5.4.3"));

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        kafka.start();
        registry.add("spring.cloud.stream.kafka.binder.brokers",
                kafka::getBootstrapServers);
    }

    @AfterAll()
    static void tearDown() {
        kafka.close();
    }

    @Test
    void consume() {
    }
}