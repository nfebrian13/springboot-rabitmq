package com.rabbitmq.fanout.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitmq.fanout.model.Messages;

@RestController
@RequestMapping(value = "/fanout/exchange")
public class RabbitMQFanoutExchangeController {
	
	/**
	 * -- Fanout Exchange --
	 * Pesan dikirim dan dirutekan ke antrian yang tersedia.
	 * Routing key jika ada, maka akan diabaikan.
	 * Fanout exchange adalah desain publish subscribe 
	 **/

	@Autowired
	private AmqpTemplate amqpTemplate;

	@GetMapping(value = "/producer")
	public String producer(@RequestParam("exchangeName") String exchange, 
						   @RequestParam("routingKey") String routingKey,
						   @RequestParam("messageData") String messageData) {
		amqpTemplate.convertAndSend(exchange, "", messageData);
		return "Message sent to the RabbitMQ Successfully";
	}

	@PostMapping(value = "/producer")
	public String messageProducer(@RequestBody Messages message) {
		amqpTemplate.convertAndSend(message.getExchangeName(), "", message.getMessageData());
		return "Message sent to the RabbitMQ Successfully";
	}
}
