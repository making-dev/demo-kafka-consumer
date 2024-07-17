package com.example;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoKafkaConsumerApplicationTests {
	@Autowired RestClient.Builder restClientBuildr;

	@LocalServerPort int port;

	RestClient restClient;

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;

	@Value("${demo.topic}") String topic;

	@BeforeEach
	void setUp() throws Exception {
		this.restClient = this.restClientBuildr
				.baseUrl("http://localhost:%d".formatted(port))
				.build();
	}

	@Test
	void contextLoads() throws Exception {
		this.kafkaTemplate.send(this.topic, "Hello World!").get(3, TimeUnit.SECONDS);
		List<String> body = this.restClient.get()
				.uri("/")
				.retrieve()
				.body(new ParameterizedTypeReference<>() {
				});
		assertThat(body).containsExactly("Hello World!");
	}

}
