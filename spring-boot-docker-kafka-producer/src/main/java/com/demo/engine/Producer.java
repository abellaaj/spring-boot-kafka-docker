package com.demo.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import io.opentracing.Span;
import io.opentracing.Tracer;

@Service
public class Producer {

	private static final Logger logger = LoggerFactory.getLogger(Producer.class);
	private static final String TOPIC = "users";

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	private Tracer tracer;

	public Producer(Tracer tracer) {
		super();
		this.tracer = tracer;
	}

	public void sendMessage(String message, Span rootSpan) {
		Span span = tracer.buildSpan("service send message").asChildOf(rootSpan).start();
		logger.info(String.format("#### -> Producing message -> %s", message));
		ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(TOPIC, message);

		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			@Override
			public void onSuccess(SendResult<String, String> result) {
				System.out.println(
						"Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
			}

			@Override
			public void onFailure(Throwable ex) {
				System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
			}
		});
	}
}
