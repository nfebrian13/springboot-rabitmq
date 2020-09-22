package com.rabbitmq.topic.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitmq.topic.model.Messages;

@RestController
@RequestMapping(value = "/topic/exchange")
public class RabbitMQTopicExchangeController {

	/**
	 * -- Topic Exchange -- 
	 * topic exchange menggunakan routing key.
	 * routing key dan binding queue tidak harus sama persis.
	 * selain itu, topic exchange menggunakan ekspresi reguler seperti wildcard 
	 * dan dapat mengirim pertukaran ke beberapa antrian terikat
	 **/
		
	@Autowired
	private AmqpTemplate amqpTemplate;

	@GetMapping(value = "/producer")
	public String producer(@RequestParam("exchangeName") String exchange, @RequestParam("routingKey") String routingKey,
			@RequestParam("messageData") String messageData) {
		amqpTemplate.convertAndSend(exchange, routingKey, messageData);
		return "Message sent to the RabbitMQ Successfully";
	}

	@PostMapping(value = "/producer")
	public String messageProducer(@RequestBody Messages message) {
		amqpTemplate.convertAndSend(message.getExchangeName(), message.getRoutingKey(), message.getMessageData());
		return "Message sent to the RabbitMQ Successfully";
	}
}
