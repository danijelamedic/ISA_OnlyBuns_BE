package com.onlybuns.isa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.Map;

@Controller
public class WebSocketController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private ObjectMapper mapper;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value="/sendMessageRest", method = RequestMethod.POST)
    public ResponseEntity<?> sendMessage(@RequestBody Map<String, String> message) {
        if (message.containsKey("message")) {
            if (message.containsKey("toId") && message.get("toId") != null && !message.get("toId").equals("")) {
                System.out.println("Slanje poruke korisniku: " + message.get("toId"));
//                rabbitMQProducer.sendDirectMessage(message.get("fromId"), message.get("toId"), message.get("message"));
            }
            /*else if(message.containsKey("groupId") && message.get("groupId") != null && !message.get("groupId").equals("")) {
                System.out.println("Slanje poruke grupi: " + message.get("groupId"));
                rabbitMQProducer.sendTo(message.get("fromId"), message.get("groupId"), message.get("message"));
                System.out.println("Message sent to RabbitMQ.");
            }*/
            else {
//                rabbitMQProducer.sendDirectMessage("global", "global", message.get("message"));
            }
            return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.OK);
        }

        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @MessageMapping("/send/message")
    public Map<String, String> broadcastNotification(String message) {
        Map<String, String> messageConverted = parseMessage(message);

        if (messageConverted != null) {
            if (messageConverted.containsKey("toId") && messageConverted.get("toId") != null
                    && !messageConverted.get("toId").equals("")) {
                this.simpMessagingTemplate.convertAndSend("/socket-publisher/" + messageConverted.get("toId"),
                        messageConverted);
            }
            /*else if(messageConverted.containsKey("groupId") && messageConverted.get("groupId") != null
                    && !messageConverted.get("groupId").equals("")){
                this.simpMessagingTemplate.convertAndSend("/socket-publisher/group/" + messageConverted.get("groupId"),
                        messageConverted);
            }*/
            else {
                this.simpMessagingTemplate.convertAndSend("/socket-publisher", messageConverted);
            }
        }

        return messageConverted;
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> parseMessage(String message) {
//        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> retVal;

        try {
            retVal = mapper.readValue(message, Map.class); // parsiranje JSON stringa
        } catch (IOException e) {
            retVal = null;
        }

        return retVal;
    }
}
