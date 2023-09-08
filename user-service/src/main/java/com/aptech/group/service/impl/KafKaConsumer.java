package com.aptech.group.service.impl;

import com.aptech.group.dto.user.UserRequest;
import com.aptech.group.service.UserService;
import com.aptech.group.utils.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafKaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafKaConsumer.class);

    @Autowired
    private UserService userService;

    @KafkaListener(topics = Constant.ACCOUNT_CREATED_TOPIC,
            groupId = Constant.GROUP_ID)
    public void consume(UserRequest data){
        LOGGER.info(String.format("Message received -> %s", data.toString()));
        userService.updateKCId(data);
    }
}
