package com.aptech.group.service.impl;

import com.aptech.group.dto.user.UserRequest;
import com.aptech.group.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafKaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafKaConsumer.class);

    @KafkaListener(topics = Constant.ACCOUNT_CREATING_TOPIC,
            groupId = Constant.GROUP_ID)
    public void consume(UserRequest data){
        LOGGER.info(String.format("Message received -> %s", data.toString()));
    }
}
