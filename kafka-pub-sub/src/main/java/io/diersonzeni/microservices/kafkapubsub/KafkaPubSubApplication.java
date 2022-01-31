package io.diersonzeni.microservices.kafkapubsub;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.diersonzeni.microservices.kafkapubsub.producer.Sender;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableKafka
@RestController
public class KafkaPubSubApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaPubSubApplication.class, args);
	}
	
	@Autowired
	private Sender sender;
	
	@Bean
	public NewTopic topic() {
		return new NewTopic("boot.topic", 1, (short) 1);
	}
	
	@PostMapping(path = "/sendMessageToKafka", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Void> create(@RequestBody String message) throws InterruptedException, ExecutionException {
		
		sender.send("boot.topic", message);
		
		return Mono.empty();
	}

}
