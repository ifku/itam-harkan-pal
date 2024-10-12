package com.ifkusyoba.itam_harkan_pal.core;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketStompController {
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String handleGreetings(String message) {
        System.out.println("Received message: " + message);
        return "Hello, " + message + "!";
    }
}
