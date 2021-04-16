package com.mroskino.playground.websocketserver.config;

import com.mroskino.playground.websocketserver.handler.EchoWebsocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebFlux
public class WebConfig {

    @Bean
    public EchoWebsocketHandler echoWebsocketHandler() {
        return new EchoWebsocketHandler();
    }

    @Bean
    public HandlerMapping handlerMapping() {
        Map<String, WebSocketHandler> map = new HashMap<>(1);
        map.put("/test", echoWebsocketHandler());
        return new SimpleUrlHandlerMapping(map, -1);
    }
}
