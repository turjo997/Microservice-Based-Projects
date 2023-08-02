package com.bjit.finalproject.TMS.config;

import com.bjit.finalproject.TMS.service.UserService;
import com.bjit.finalproject.TMS.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
//@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketConfigurer {
    private  final JwtService jwtService;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new MySocketHandler(jwtService), "/socket").setAllowedOrigins("*").addInterceptors(new HttpSessionHandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                           WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
                // Retrieve the token from the query parameter
                String token = request.getURI().getQuery().replace("token=Bearer ", "");
                // Add the token as an attribute to the WebSocketSession
                attributes.put("token", token);
                return true;
            }
        });
    }
    // Add this bean to enable CORS for WebSocket handshake
    @Bean
    public WebSocketHandlerDecoratorFactory webSocketHandlerDecoratorFactory() {
        return new WebSocketHandlerDecoratorFactory() {
            @Override
            public WebSocketHandler decorate(WebSocketHandler handler) {
                return new WebSocketHandlerDecorator(handler) {
                    @Override
                    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                        super.afterConnectionEstablished(session);
                        session.setTextMessageSizeLimit(64 * 1024); // Optional: Set text message size limit
                    }
                };
            }
        };
    }
}

