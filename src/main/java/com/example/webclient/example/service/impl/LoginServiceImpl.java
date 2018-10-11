package com.example.webclient.example.service.impl;

import com.example.webclient.example.model.User;
import com.example.webclient.example.service.LoginService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class LoginServiceImpl implements LoginService {

    private final WebClient webClient;

    public String JSESSIONID;

    public LoginServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    @Override
    public String login() {

        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        System.out.println(webClient);
        Mono<HttpHeaders> result = this.webClient.post()
                .uri("/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(user))
                .exchange()
                .map(response -> response.headers().asHttpHeaders());

        HttpHeaders httpHeaders = result.block();
        java.util.List<java.lang.String> value = httpHeaders.entrySet().stream().filter(x -> x.getKey().contains("Set-Cookie")).findFirst().orElseThrow(() -> new RuntimeException("无Cookie")).getValue();

        System.out.println(value);

        JSESSIONID = value.get(0).substring(0, value.get(0).indexOf(";"));

        return JSESSIONID;
    }

    @Override
    public Boolean getLogin() {
        System.out.println(webClient);
        Mono<Boolean> mono = this.webClient.get()
                .uri("/getLogin")
                .header("Cookie", JSESSIONID)
                .retrieve()
                .bodyToMono(Boolean.class);

        Boolean returnValue = mono.block();
        System.out.println("方法返回值:" + returnValue);
        return returnValue;
    }

}
