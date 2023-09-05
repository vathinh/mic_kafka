package com.aptech.group.event;

import com.aptech.group.dto.UserRequest;
import com.aptech.group.service.AccountService;
import com.aptech.group.utils.Constant;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventConsumer {
    Gson gson = new Gson();

    private AccountService accountService;

    @Autowired
    EventProducer eventProducer;

    public EventConsumer(ReceiverOptions<String,String> receiverOptions){
        KafkaReceiver.create(receiverOptions.subscription(Collections.singleton(Constant.ACCOUNT_CREATING_TOPIC)))
                .receive().subscribe(this::profileOnboarding);
    }
    public void profileOnboarding(ReceiverRecord<String,String> receiverRecord){
        log.info("Profile Onboarding event");
        UserRequest dto = gson.fromJson(receiverRecord.value(),UserRequest.class);
        UserRequest accountDTO = new UserRequest();
        accountDTO.setEmail(dto.getEmail());
        accountDTO.setFirstName(dto.getFirstName());
        accountDTO.setLastName(dto.getLastName());
        accountDTO.setUserType(accountDTO.getUserType());
        accountDTO.setPassword(accountDTO.getPassword());
        String keycloakId = accountService.createAccount(accountDTO);
        dto.setKeycloakId(keycloakId);
        eventProducer.send(Constant.ACCOUNT_CREATED_TOPIC, gson.toJson(dto));
    }
}
