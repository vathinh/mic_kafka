package com.aptech.group.service.impl;

import com.aptech.group.dto.user.UserRequest;
import com.aptech.group.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, UserRequest> kafkaTemplate;

    public void sendMessage(UserRequest data){
        LOGGER.info(String.format("Message sent -> %s", data.toString()));

        Message<UserRequest> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, Constant.ACCOUNT_CREATING_TOPIC)
                .build();

        kafkaTemplate.send(message);
    }
}
