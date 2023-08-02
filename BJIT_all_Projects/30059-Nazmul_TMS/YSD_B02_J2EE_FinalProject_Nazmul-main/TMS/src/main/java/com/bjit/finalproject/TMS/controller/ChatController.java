package com.bjit.finalproject.TMS.controller;

import com.bjit.finalproject.TMS.config.MySocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ChatController {

    @Autowired
    private MySocketHandler socketHandler;

    @GetMapping("/")
    public String index() {
        // Return the index.html or main frontend page here
        return "index";
    }

    // Example of a REST endpoint to send a message to the frontend via WebSocket
    @RequestMapping("/socket.io/**")
    @ResponseBody
    public void handleWebSocketHandshake() {
        // No action needed, simply having this endpoint to handle the WebSocket handshake request
    }

    @RequestMapping("/send-message")
    @ResponseBody
    public String sendMessage() {
        try {
            // Simulate sending a message from the backend to the frontend
            String message = "Hello from the backend!";
            socketHandler.sendMessageToAll(message);
            return "Message sent successfully!";
        } catch (Exception e) {
            return "Failed to send message: " + e.getMessage();
        }
    }
}
