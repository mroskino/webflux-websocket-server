package com.mroskino.play.websocketserver.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Slf4j
public class EchoWebsocketHandler implements WebSocketHandler {

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(session.receive()
                .doOnNext(message -> log.info("Received message: {}", message.getPayloadAsText()))
                .map(value -> session.textMessage("Echo " + value.getPayloadAsText())));
    }
}
