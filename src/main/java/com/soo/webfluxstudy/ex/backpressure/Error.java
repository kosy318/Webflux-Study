package com.soo.webfluxstudy.ex.backpressure;

import com.soo.webfluxstudy.utils.Logger;
import com.soo.webfluxstudy.utils.TimeUtils;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

/**
 * Unbounded request 일 경우, Downstream 에 Backpressure Error 전략을 적용하는 예제
 *  - Downstream 으로 전달 할 데이터가 버퍼에 가득 찰 경우, Exception을 발생 시키는 전략
 */
public class Error {
    public static void main(String[] args) {
        Flux
                .interval(Duration.ofMillis(1L)) // 1ms 마다 emit
                .onBackpressureError() // buffer가 가득 찼을 때 exception 발생
                .doOnNext(Logger::doOnNext)
                .publishOn(Schedulers.parallel()) // thread를 추가
                .subscribe(data -> {
                            TimeUtils.sleep(5L); // 5ms 마다 데이터 처리
                            Logger.onNext(data);
                        },
                        Logger::onError);

        TimeUtils.sleep(2000L);
    }
}
