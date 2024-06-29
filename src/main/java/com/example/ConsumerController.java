package com.example;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {
	private final List<String> messages = new CopyOnWriteArrayList<>();

	private final Logger log = LoggerFactory.getLogger(ConsumerController.class);

	@GetMapping(path = "")
	public List<String> getMessages() {
		return this.messages;
	}

	@KafkaListener(topics = "${demo.topic}")
	public void onMessage(String message) {
		log.info("onMessage({})", message);
		this.messages.add(message);
	}
}
