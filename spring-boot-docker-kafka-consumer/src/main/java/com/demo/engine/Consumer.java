package com.demo.engine;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import io.opentracing.Span;
import io.opentracing.Tracer;

@Service
public class Consumer {

	private final Logger logger = LoggerFactory.getLogger(Consumer.class);

	private Tracer tracer;

	public Consumer(Tracer tracer) {
		super();
		this.tracer = tracer;
	}

	@KafkaListener(topics = "users", groupId = "group_id")
	public void consume(String message) throws IOException {
		Span span = tracer.buildSpan("service consume message").start();
		logger.info(String.format("#### -> Consumed message -> %s", message));
		span.finish();
	}
}
