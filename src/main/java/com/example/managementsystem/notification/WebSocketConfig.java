package com.example.managementsystem.notification;

import com.example.managementsystem.config.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.security.Principal;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/queue");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:4200")
                .withSockJS()
                .setInterceptors(new HandshakeInterceptor() {
                    @Override
                    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
                        if (request instanceof ServletServerHttpRequest) {
                            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
                            String token = extractToken(servletRequest);
                            if (token != null && jwtTokenProvider.validateToken(token)) {
                                String userEmail = jwtTokenProvider.getUserEmailFromToken(token);
                                attributes.put(Principal.class.getName(), new Principal() {
                                    @Override
                                    public String getName() {
                                        return userEmail;
                                    }
                                });
                                return true;
                            } else {
                                return false;
                            }
                        }
                        return false;
                    }

                    @Override
                    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {}
                });
    }

    private String extractToken(ServletServerHttpRequest request) {
        String bearerToken = request.getServletRequest().getHeader("Authorization");
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            bearerToken = request.getServletRequest().getParameter("token");
        } else {
            bearerToken = bearerToken.substring(7);
        }
        return bearerToken;
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
                    String destination = accessor.getDestination();
                    logger.info("Client subscribed to topic: {}", destination);
                } else if (StompCommand.UNSUBSCRIBE.equals(accessor.getCommand())) {
                    String destination = accessor.getDestination();
                    logger.info("Client unsubscribed from topic: {}", destination);
                }
                return message;
            }
        });
    }
}
