package com.softserve.careactivities.services;

import com.softserve.careactivities.repositories.CareActivityRepository;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.shaded.com.google.common.collect.ImmutableMap;
import org.testcontainers.utility.DockerImageName;

import static org.mockito.Mockito.*;

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

    @Autowired
    PatientConsumer patientConsumer;

    @MockBean
    CareActivityRepository careActivityRepository;

    private final String MPI = "MPI-test-value";

    @Value("${spring.cloud.stream.bindings.consume-in-0.destination}")
    private String topic;

    @AfterAll()
    static void tearDown() {
        kafka.close();
    }

    @Test
    void shouldConsumeInputMPI() {

        KafkaProducer<String, String> producer = new KafkaProducer<>(
                ImmutableMap.of(
                        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers()
                ), new StringSerializer(), new StringSerializer()
        );

        producer.send(new ProducerRecord<>(topic, MPI));

        doNothing().when(careActivityRepository).declineAllCareActivitiesByMPI(MPI);

        producer.close();

        verify(careActivityRepository, timeout(5000).times(1))
                .declineAllCareActivitiesByMPI(MPI);
    }
}