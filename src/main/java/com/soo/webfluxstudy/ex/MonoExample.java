package com.soo.webfluxstudy.ex;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.soo.webfluxstudy.utils.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;

public class MonoExample {
    public static void main(String[] args) {
        // Mono: 1개의 데이터를 생성해서 emit
        System.out.println(">>>> Mono: 1개의 데이터를 생성해서 emit");
        Mono.just("Hello Reactor!")
                .subscribe(data -> Logger.info("# emitted data: {}", data));

        // 원본 데이터의 emit 없이 onComplete signal만 emit해서 확인
        System.out.println(">>>> 원본 데이터의 emit 없이 onComplete signal만 emit해서 확인");
        Mono.empty()
                .subscribe(
                        data -> Logger.info("# emitted data: {}", data), // data 없음
                        error -> {
                        }, // error signal
                        () -> Logger.info("# emitted onComplete signal") // on complete signal
                );

        // open api를 사용해서 가져온 데이터를 가공 후 출력
        System.out.println(">>>> open api를 사용해서 가져온 데이터를 가공 후 출력");

        URI worldTimeUri = UriComponentsBuilder.newInstance().scheme("http")
                .host("worldtimeapi.org")
                .port(80)
                .path("/api/timezone/Asia/Seoul")
                .build()
                .encode()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Mono.just(
                        restTemplate.exchange(worldTimeUri, HttpMethod.GET, new HttpEntity<String>(headers), String.class)
                )
                .map(response -> {
                    DocumentContext jsonContext = JsonPath.parse(response.getBody());
                    return jsonContext.<String>read("$.datetime");
                })
                .subscribe(
                        data -> Logger.info("# emitted data: " + data),
                        Logger::onError,
                        () -> Logger.info("# emitted onComplete signal")
                );

    }
}
