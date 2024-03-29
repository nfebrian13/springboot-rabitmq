package com.rabbitmq.header.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitmq.header.model.Messages;

@RestController
@RequestMapping(value = "/header/exchange")
public class RabbitMQHeaderExchangeController {

	/** 
	 * - Header exchange
	 *   Antrian perutean dipilih berdasarkan kriteria yang ditentukan di header kunci perutean
	 *   Header exchange mirip dengan topic namun header exchange dapat menentukan kriteria kompleks untuk memilih antrian perutean
	 * **/
	
	@Autowired
	private AmqpTemplate amqpTemplate;

	@GetMapping(value = "/producer")
	public String producer(@RequestParam("exchangeName") String exchange, 
						   @RequestParam("department") String department,
						   @RequestParam("messageData") String messageData) {

		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setHeader("department", department);
		MessageConverter messageConverter = new SimpleMessageConverter();
		Message message = messageConverter.toMessage(messageData, messageProperties);
		amqpTemplate.send(exchange, "", message);

		return "Message sent to the RabbitMQ Header Exchange Successfully";
	}
	
	@PostMapping(value = "/producer")
	public String producerMessage(@RequestBody Messages m) {

		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setHeader("department", m.getDepartment());
		MessageConverter messageConverter = new SimpleMessageConverter();
		Message message = messageConverter.toMessage(m.getMessageData(), messageProperties);
		amqpTemplate.send(m.getExchangeName(), "", message);

		return "Message sent to the RabbitMQ Header Exchange Successfully";
	}
	
}