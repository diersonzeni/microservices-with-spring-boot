package io.diersonzeni.microservices.batch.batchkafkareader.config;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.batch.item.kafka.builder.KafkaItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfig {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private KafkaProperties properties;
	
	@Bean
	KafkaItemReader<String, String> kafkaItemReader() {
		Properties props = new Properties();
		props.putAll(this.properties.buildConsumerProperties());
		return new KafkaItemReaderBuilder<String, String>()
				.partitions(0)
				.consumerProperties(props)
				.pollTimeout(Duration.ofMillis(10000))
				.name("customer-reader")
				.saveState(true)
				.topic("boot.topic")
				.build();
	}
	
	public ItemProcessor<String, String> itemProcessor(){
		ItemProcessor<String, String> processor = new ItemProcessor<String, String>() {
			
			@Override
			public String process(String param) throws Exception {
				System.out.println(param);
				return param;
			}
		};
		
		return processor;
	}
	
	public ItemWriter<String> emptyItemWriter() {
		
		ItemWriter<String> writer = new ItemWriter<String>() {
			
			@Override
			public void write(List<? extends String> arg0) throws Exception {
				
			}
		};
		
		return writer;
	}
		
	@Bean
	public Step step1() throws Exception {
		return stepBuilderFactory.get("step1")
				.<String, String>chunk(10)
				.reader(kafkaItemReader())
				.processor(itemProcessor())
				.writer(emptyItemWriter())
				.build();
	}
	
	@Bean
	public Job job() throws Exception {
		return jobBuilderFactory.get("job")
				.incrementer(new RunIdIncrementer())
				.start(step1())
				.build();
	}
}
