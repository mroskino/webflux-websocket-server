package com.mroskino.playground.websocketserver.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
public class EchoWebsocketHandler implements WebSocketHandler {

    @Value("${echo.response-delay}")
    private Long responseDelay;

    @Value("${echo.idle-timeout}")
    private Long idleTimeout;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(session.receive()
                .timeout(Duration.ofMillis(idleTimeout))
                .doOnNext(message -> log.info("Received message: {}", message.getPayloadAsText()))
                .map(message -> session.textMessage("Echo " + message.getPayloadAsText()))
                .doOnSubscribe(s -> log.info("WebSocket session {} initiated by {}", session.getId(), session.getHandshakeInfo().getRemoteAddress()))
                .doOnTerminate(() -> log.info("WebSocket session {} closed", session.getId())));
    }
}
