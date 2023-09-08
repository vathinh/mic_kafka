package com.aptech.group.controller;

import com.aptech.group.dto.user.UserRequest;
import com.aptech.group.event.EventProducer;
import com.aptech.group.service.impl.KafkaProducer;
import com.aptech.group.utils.Constant;
import com.google.gson.Gson;
import com.netflix.discovery.converters.Auto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.aptech.group.controller.UserServiceEndpoints.USER_SERVICE_PATH;

@RestController
@RequestMapping(USER_SERVICE_PATH)
@RequiredArgsConstructor
public class KafkaProducerController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private EventProducer eventProducer;

    private Gson gson = new Gson();

    @PostMapping("publish")
    public ResponseEntity<String> publish(@RequestBody UserRequest user){
        kafkaProducer.sendMessage(user);
        return ResponseEntity.ok("Message sent to kafka topic");
    }

    @PostMapping("event")
    public ResponseEntity<String> event(@RequestBody UserRequest user){
        eventProducer.send(Constant.ACCOUNT_CREATING_TOPIC, gson.toJson(user));
        return ResponseEntity.ok("Message sent to kafka topic");
    }


}
