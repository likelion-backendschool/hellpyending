package com.example.hellpyending.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;

import org.springframework.web.socket.config.annotation.*;

//핸들러를 이용해 WebSocket을 활성화하기 위한 Config
@Configuration
@EnableWebSocketMessageBroker
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {


    //endpoint를 /stomp로 하고, allowedOrigins를 "*"로 하면 페이지에서
    //Get /info 404 Error가 발생한다. 그래서 아래와 같이 2개의 계층으로 분리하고
    //origins를 개발 도메인으로 변경하니 잘 동작하였다.
    //이유는 왜 그런지 아직 찾지 못함
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp/tigerShrimp")
                .setAllowedOrigins("172.17.0.1:8080")
                .withSockJS();
    }

    /*어플리케이션 내부에서 사용할 path를 지정할 수 있음*/
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/pub");
        registry.enableSimpleBroker("/sub");
    }


}