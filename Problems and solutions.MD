# Project Overview

This project is the **API Gateway** for my **Expense Tracker** project. However, it can also be used as an example for other projects. I implemented it in a reactive manner, as reactive is much better suited for an API gateway. The individual microservices are still MVC servlet style, but they work fine with this setup.

## Spring Cloud Gateway

We used **Spring Cloud Gateway**, which is reactive and uses non-blocking I/O. This is advantageous for a gateway as it improves performance and scalability.

There is another gateway which is mvc gateway, that is not reactive, that is servlet based.

### Route Locator

The reactive gateway's route locator looks a little different. I got the example from the Spring documentation. I tried other ways of defining routes in YAML, but they did not work. Only the way I implemented here, using the route locator with filters and circuit breaker, works.

Currently, the route locator has only one definition for `localhost:8080`. You can customize it later as needed.

```

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
}
```

### Spring Security Configuration
Because of the reactive nature of the gateway, we had to use the reactive configuration style for Spring Security. The Spring Security plugin remains the same, but the configuration is now reactive.

### Resilience4j Configuration
For Resilience4j, we had to use the reactive version of the plugin. Although the configuration in the YAML file looks almost the same, it is tailored for reactive programming.


### Actuator
This project also includes Spring Boot Actuator. The Actuator plugin remains the same for reactive applications, providing useful endpoints for monitoring and managing the application.