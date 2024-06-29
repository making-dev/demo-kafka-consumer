package com.example;

import org.springframework.boot.SpringApplication;

public class TestDemoKafkaConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.from(DemoKafkaConsumerApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
