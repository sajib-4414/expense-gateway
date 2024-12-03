package dev.spring_cloud_gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity//new for reactive
public class SecurityConfiguration {

    @Bean
    //SecurityWebFilterChain, ServerHttpSecurity is for webflux reactive
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http) {

        //authorizeExchange is for webflux way of doing auth->
        return http
                .authorizeExchange(exchanges -> exchanges
                        .anyExchange().permitAll())
                .csrf(csrfSpec -> csrfSpec.disable())


                .build();
    }
}
