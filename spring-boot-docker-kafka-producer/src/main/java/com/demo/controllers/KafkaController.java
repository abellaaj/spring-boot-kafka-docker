package com.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.engine.Producer;
import com.google.common.collect.ImmutableMap;

import io.opentracing.Span;
import io.opentracing.Tracer;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

	private final Producer producer;
	private Tracer tracer;

	@Autowired
	KafkaController(Tracer tracer, Producer producer) {
		this.tracer = tracer;
		this.producer = producer;
	}

	@GetMapping(value = "/publish/{send}")
	public void sendMessageToKafkaTopic(@PathVariable String send) {
		Span span = tracer.buildSpan("Send Message To Kafka").start();
		if ("true".equalsIgnoreCase(send)) {
			this.producer.sendMessage("Hello World" + send, span);
			span.log(ImmutableMap.of("event", "send-message-token", "value", send));
			span.setTag("http.status_code", 201);
		} else {
			span.setTag("http.status_code", 403);
		}
		span.finish();
	}
}
