package com.soo.webfluxstudy.ex.backpressure;

import com.soo.webfluxstudy.utils.Logger;
import com.soo.webfluxstudy.utils.TimeUtils;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

public class Request {
    public static int count = 0;
    public static void main(String[] args) {
        // Subscriber가 처리 가능한 만큼의 request 개수를 1개로 조절
        System.out.println(">>>> request 1");
        Flux.range(1, 5)
                .doOnNext(Logger::doOnNext)
                .doOnRequest(Logger::doOnRequest)
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(1);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        TimeUtils.sleep(2000L);
                        Logger.onNext(value);
                        request(1); // data 1개 요청
                    }
                });

        // Subscriber가 처리 가능한 만큼의 request 개수를 2개로 조절
        System.out.println(">>>> request 2");
        Flux.range(1, 5)
                .doOnNext(Logger::doOnNext)
                .doOnRequest(Logger::doOnRequest)
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(2);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        count++;
                        Logger.onNext(value);
                        if (count == 2) { // data 2개 받았을 때
                            TimeUtils.sleep(2000L); // 2초 기다리고
                            request(2); // 2개 요청
                            count = 0;
                        }
                    }
                });
    }
}
