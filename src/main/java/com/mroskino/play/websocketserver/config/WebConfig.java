package com.mroskino.play.websocketserver.config;

import com.mroskino.play.websocketserver.handler.TestWebsocketHandler;
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
    public HandlerMapping handlerMapping() {
        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/test", new TestWebsocketHandler());
        return new SimpleUrlHandlerMapping(map, -1);
    }
}
