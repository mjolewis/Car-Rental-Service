package com.crd.carrental.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/**********************************************************************************************************************
 * Configure message brokers and register STOMP (simple text oriented messaging protocol that sits on top of a lower
 * level WebSocket)
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Configure the message converters to use when extracting the payload of messages
     *
     * @param config A registry for configuring message broker options
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/reservation");      // prefix to @SendTo
        config.setApplicationDestinationPrefixes("/app");                 // prefix bound to @MessageMapping
    }

    /**
     * Register STOMP endpoints mapping each to a specific URL and configure a SockJS fallback option
     *
     * @param registry A contract for registering STOMP over WebSocket endpoints
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/reservationsystem").withSockJS();
    }
}

