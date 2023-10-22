package com.soo.webfluxstudy.ex;

import com.soo.webfluxstudy.utils.Logger;
import com.soo.webfluxstudy.utils.TimeUtils;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

public class HotSequence {
    public static void main(String[] args) {
        Flux<String> concertFlux =
                Flux.fromStream(Stream.of("Singer A", "Singer B", "Singer C", "Singer D", "Singer E"))
                        .delayElements(Duration.ofSeconds(1)).share();  // 1초 마다 공유. share() 원본 Flux를 여러 Subscriber가 공유(Cold -> Hot)

        concertFlux.subscribe(singer -> Logger.info("# Subscriber1 is watching {}'s song.", singer));

        TimeUtils.sleep(2500); // subscriber1이 SingerB까지 소모한 뒤

        // SingerC부터 데이터를 받을 수 있음
        concertFlux.subscribe(singer -> Logger.info("# Subscriber2 is watching {}'s song.", singer));

        TimeUtils.sleep(3000);
    }
}
