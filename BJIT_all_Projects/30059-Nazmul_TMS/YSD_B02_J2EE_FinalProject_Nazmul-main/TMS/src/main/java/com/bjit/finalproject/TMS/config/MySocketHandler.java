package com.bjit.finalproject.TMS.config;

import com.bjit.finalproject.TMS.dto.ChatMessage;
import com.bjit.finalproject.TMS.service.UserService;
import com.bjit.finalproject.TMS.utils.JwtService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@RequiredArgsConstructor
public class MySocketHandler extends TextWebSocketHandler {

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final Map<WebSocketSession, String> userSessionMap = new ConcurrentHashMap<>();
    private final JwtService jwtService;
//    ChatMessage customMessage = new ChatMessage();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String token = (String) session.getAttributes().get("token");
        String userEmail = jwtService.extractUsername(token);
        userSessionMap.put(session, userEmail);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        sessions.remove(session);
        userSessionMap.remove(session);
    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Received message from frontend: " + payload);

        String email = userSessionMap.get(session);
        ChatMessage customMessage = new ChatMessage();
        customMessage.setEmail(email);
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonNode jsonNode = objectMapper.readTree(payload);
            String content = jsonNode.get("content").asText();
            String userEmail = customMessage.getEmail();
            customMessage.setText(content);
            String responseMessage = customMessage.getText();
            // You can send a response back to the frontend if needed
            String response = "{\"email\": \"" + userEmail + "\", \"text\":\"" + responseMessage + "\"}";
//            session.sendMessage(new TextMessage(response));
            sendMessageToAll(response);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void sendMessageToAll(String message) throws IOException {
        for (WebSocketSession session : userSessionMap.keySet()) {
            session.sendMessage(new TextMessage(message));
        }
    }
}
