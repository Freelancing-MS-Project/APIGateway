package tn.esprit.twin.apigatewayfreelancers;

import org.springframework.context.annotation.*;
import org.springframework.security.config.Customizer;
import
org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity)
    {
        return serverHttpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange.pathMatchers("/eureka/**")
                        .permitAll()
                        .anyExchange().authenticated()
                ).oauth2ResourceServer((oauth) -> oauth
                        .jwt(Customizer.withDefaults()))
                .build();
    }
//@Bean
//public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
//    return serverHttpSecurity
//            .csrf(csrf -> csrf.disable())
//            .authorizeExchange(exchange -> exchange
//                    .anyExchange().permitAll()  // ← Autorise tout pour tester
//            )
//            .build();
//}
}
