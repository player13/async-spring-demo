package com.example.demo.async;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SomeService {

    @Async
    public void someAsyncMethod(String header) {
        String name = Thread.currentThread().getName();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
        System.out.println("Thread name:" + name + ", real header: " + header);
        System.out.println("Thread name:" + name + ", holder header: " + DemoAsyncApplication.AuthorizationHeaderHolder.getHeader());
    }
}
