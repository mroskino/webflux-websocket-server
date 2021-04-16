package com.mroskino.playground.websocketserver.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.stream.IntStream;

@Slf4j
public class EchoWebsocketHandler implements WebSocketHandler {

    @Value("${echo.response-delay}")
    private Long responseDelay;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(session.receive()
                .doOnNext(message -> log.info("Received message: {}", message.getPayloadAsText()))
                .flatMap(message -> Flux.fromStream(IntStream
                        .range(1, 10)
                        .mapToObj(i -> i + " " + message.getPayloadAsText())))
                .delayElements(Duration.ofMillis(responseDelay))
                .map(message -> session.textMessage("Echo " + message))
                .doOnSubscribe(s -> log.info("WebSocket session {} initiated by {}",
                        session.getId(), session.getHandshakeInfo().getRemoteAddress()))
                .doOnTerminate(() -> log.info("WebSocket session {} closed", session.getId())));
    }
}
