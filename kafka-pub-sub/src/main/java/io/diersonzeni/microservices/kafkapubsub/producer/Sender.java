package io.diersonzeni.microservices.kafkapubsub.producer;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class Sender {

	private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void send(String topic, String data) throws InterruptedException, ExecutionException {
		LOGGER.info("sending data='{}' to topic='{}'", data, topic);
		
		ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(topic, data);
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			@Override
			public void onSuccess(SendResult<String, String> result) {
				LOGGER.info("Sent message=[ {} ] with offset=[ {} ]", data, result.getRecordMetadata().offset());
			}

			@Override
			public void onFailure(Throwable ex) {
				LOGGER.error("Unable to send message=[ {} ] due to : {}", data, ex.getMessage(), ex);
			}
		});
	}
}