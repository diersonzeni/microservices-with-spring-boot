package io.diersonzeni.microservices.kafkapubsub.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

	@KafkaListener(topics = "${topic.boot}", groupId = "test.group1")
	public void receive(ConsumerRecord<?, ?> consumerRecord) {
		LOGGER.info("received data='{}'", consumerRecord.toString());
	}
}
