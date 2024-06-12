package com.compulynx.compas.services;

import com.compulynx.compas.models.AccountDetailObject;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;


import java.time.Duration;
@Service
@RequiredArgsConstructor
public class HttpService {

    private final WebClient webClient;
    public JsonNode sendApiCallRequest(HttpMethod httpMethod, String url, AccountDetailObject accountDetailObject) {
        return webClient.method(httpMethod)
                .uri(url)
                .body(Mono.just(accountDetailObject), AccountDetailObject.class)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }
}
