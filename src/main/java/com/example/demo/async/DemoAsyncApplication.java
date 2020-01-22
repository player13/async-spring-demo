package com.example.demo.async;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class DemoAsyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoAsyncApplication.class, args);
	}

	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(3);
		executor.setMaxPoolSize(3);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("AsyncTestThread-");
		executor.initialize();
		return executor;
	}

	@Bean
	public HandlerInterceptorAdapter authorizationRequestInterceptor() {
		return new HandlerInterceptorAdapter() {
			@Override
			public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
				String authorization = request.getHeader("XXX-Auth1");
				System.out.println("Thread " + Thread.currentThread().getName() + ": setting header to holder: " + authorization);
				AuthorizationHeaderHolder.setHeader(authorization);
				return true;
			}
		};
	}

	@Bean
	public WebMvcConfigurer webMvcConfiguration(HandlerInterceptorAdapter authorizationRequestInterceptor) {
		return new WebMvcConfigurer() {
			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				registry.addInterceptor(authorizationRequestInterceptor);
			}
		};
	}

	public static class AuthorizationHeaderHolder {

		private static final InheritableThreadLocal<String> threadLocalHeader = new InheritableThreadLocal<>();

		public static void setHeader(String header) {
			threadLocalHeader.set(header);
		}

		public static String getHeader() {
			return threadLocalHeader.get();
		}
	}
}
