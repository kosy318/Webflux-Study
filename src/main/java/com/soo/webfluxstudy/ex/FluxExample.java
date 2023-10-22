package com.soo.webfluxstudy.ex;

import com.soo.webfluxstudy.utils.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxExample {
    public static void main(String[] args) {
        // Flux: 여러개의 데이터를 생성해서 emit
        System.out.println(">>>> Flux: 여러개의 데이터를 생성해서 emit");
        Flux.just(6, 9, 13)
                .map(num -> num % 2)
                .subscribe(remainder -> Logger.info("# remainder: {}", remainder));

        // operator 체인 사용 예제
        System.out.println(">>>> operator 체인 사용 예제");
        Flux.fromArray(new Integer[]{3, 6, 7, 9})
                .filter(num -> num > 6)
                .map(num -> num * 2)
                .subscribe(multiply -> Logger.info("# multiply: {}", multiply));

        // concatWith로 2개의 Mono를 연결해서 Flux로 변환
        System.out.println(">>>> concatWith로 2개의 Mono를 연결해서 Flux로 변환");
        Flux<Object> flux =
                Mono.justOrEmpty(null)
                        .concatWith(Mono.justOrEmpty("Jobs"));
        flux.subscribe(data -> Logger.info("# result: {}", data));

        // 여러개의 Flux를 연결해서 하나의 Flux로 결합(Mono를 결합하는 것도 가능)
        System.out.println(">>>> 여러개의 Flux를 연결해서 하나의 Flux로 결합(Mono를 결합하는 것도 가능)");
        Flux.concat(
                        Flux.just("Venus"),
                        Flux.just("Earth"),
                        Flux.just("Mars"))
                .collectList()
                .subscribe(planetList -> Logger.info("# Solar System: {}", planetList));
    }
}
