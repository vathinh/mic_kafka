package com.aptech.group.event;

import com.aptech.group.dto.user.UserRequest;
import com.aptech.group.service.UserService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;
import com.aptech.group.utils.Constant;

import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventConsumer {
    Gson gson = new Gson();
    private UserService userService;
    public EventConsumer(ReceiverOptions<String, String> receiverOptions){
        log.info("Profile Onboarded event");
        KafkaReceiver.create(receiverOptions.subscription(Collections.singleton(Constant.ACCOUNT_CREATED_TOPIC)))
                .receive().subscribe(this::profileOnboarded);
    }
    public void profileOnboarded(ReceiverRecord<String,String> receiverRecord){
        log.info("Profile Onboarded event");
        UserRequest dto = gson.fromJson(receiverRecord.value(),UserRequest.class);
        userService.updateKCId(dto);
    }
}
