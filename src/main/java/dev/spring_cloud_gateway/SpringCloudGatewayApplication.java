package dev.spring_cloud_gateway;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.factory.SpringCloudCircuitBreakerFilterFactory;
import org.springframework.cloud.gateway.filter.factory.SpringCloudCircuitBreakerResilience4JFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.Duration;


@SpringBootApplication

public class SpringCloudGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("expense-auth-route",
						r -> r
						.path("/api/**")  // Match any path under /api
						.filters(f -> f
								//this is a pre filter
								.rewritePath("/api/(?<segment>.*)", "/api/v1/${segment}")  // Rewrite the path cleanly
								//this is a post filter, depending on what you are doing, is a pre or post filter
								.addResponseHeader("X-Powered-By", "Danson Gateway Service")
								.circuitBreaker(c ->
										c.setName("backendA")
										.setFallbackUri("forward:/fallback")

								)

						)

						.uri("http://localhost:8080")
				)
//				.route("host_route", r -> r.host("*.myhost.org")
//						.uri("http://httpbin.org"))
//				.route("rewrite_route", r -> r.host("*.rewrite.org")
//						.filters(f -> f.rewritePath("/foo/(?<segment>.*)", "/${segment}"))
//						.uri("http://httpbin.org"))
//				.route("hystrix_route", r -> r.host("*.hystrix.org")
//						.filters(f -> f.hystrix(c -> c.setName("slowcmd")))
//						.uri("http://httpbin.org"))
//				.route("hystrix_fallback_route", r -> r.host("*.hystrixfallback.org")
//						.filters(f -> f.hystrix(c -> c.setName("slowcmd").setFallbackUri("forward:/hystrixfallback")))
//						.uri("http://httpbin.org"))
//				.route("limit_route", r -> r
//						.host("*.limited.org").and().path("/anything/**")
//						.filters(f -> f.requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter())))
//						.uri("http://httpbin.org"))
				.build();
	}





}
