package com.example.demo.async;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SomeController {

    private final SomeService service;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/tests")
    public void test(@RequestHeader("XXX-Auth1") String auth) {
        service.someAsyncMethod(auth);
    }
}
