package com.soo.webfluxstudy.ex;

import com.soo.webfluxstudy.utils.Logger;
import reactor.core.publisher.Flux;

import java.util.Arrays;

public class ColdSequence {
    public static void main(String[] args) {
        Flux<String> coldFlux = Flux.fromIterable(Arrays.asList("RED", "YELLOW", "PINK"))
                .map(String::toLowerCase);

        // subscriber 1과 2 모두 데이터 전체를 받음
        coldFlux.subscribe(country -> Logger.info("# Subscriber1: {}", country));
        Logger.info("-------------------------");
        coldFlux.subscribe(country -> Logger.info("# Subscriber2: {}", country));
    }
}
