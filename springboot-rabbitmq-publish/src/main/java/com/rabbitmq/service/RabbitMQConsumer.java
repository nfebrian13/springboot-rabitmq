package com.rabbitmq.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.model.Employee;

@Component
public class RabbitMQConsumer {

	/* ketika ada queue atau antrian data (dihasilkan dari trigger maupun yg lain) baru 
	 * rabbitmq maka listener berikut akan mentrigger dan mengambil data dari queue tersebut */
	
	@RabbitListener(queues = "${param.rabbitmq.queue}")
	public void recievedMessage(Employee employee) {
		System.out.println("Recieved Message From RabbitMQ: " + employee);
	}
	
}
