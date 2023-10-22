package com.example.webfluxstudy;

import com.example.webfluxstudy.utils.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class HelloReactor {
    public static void main(String[] args) {
        // project init test
        Mono.just("Hello Reactor")
//                .subscribe(message -> System.out.println(message));
                .subscribe(System.out::println);

        // reactor 구성 요소 및 용어 정의
        // Flux, Mono : Publisher
        // just, map ... : operator
        Flux<String> sequence = Flux.just("Hello", "Reactor"); // 데이터 생성
        sequence.map(String::toLowerCase) // 데이터 가공
                .subscribe(Logger::onNext);
    }
}
