package com.example.order.demoorders.service;

import com.example.order.demoorders.model.OrderModel;
import com.example.order.demoorders.model.OrderResponse;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


@Service
public class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private NewTopic topic;
    private KafkaTemplate<String, OrderModel> kafkaTemplate;

    public KafkaProducerService(NewTopic topic, KafkaTemplate<String, OrderModel> kafkaTemplate){
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendMessage(OrderResponse orderResponse){
        logger.info(String.format("Order event => %s", orderResponse.toString()));
        Message<OrderResponse> message = MessageBuilder.withPayload(orderResponse)
                                        .setHeader(KafkaHeaders.TOPIC, topic.name())
                                        .build();
        kafkaTemplate.send(message);
    }
}
