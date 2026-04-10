package tn.esprit.twin.apigatewayfreelancers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayFreelancersApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayFreelancersApplication.class, args);
    }


    @Bean
    public RouteLocator getRoutes(RouteLocatorBuilder builder){
        return builder.routes().route(
                        //id mission c le nom de la route sur le controller de mission
                        "mission",r->r.path("/freelancerProject/mission/**")
                                 // ← AJOUTE CECI
                                .uri("lb://freelancerProject") //lb: load balance //lb://freelancerProject : le nom de l'application mission nour njibha men application.properties
                ).route(

                        "reviews",r->r.path("/reviews/**")
                                 // ← AJOUTE CECI
                                .uri("lb://REVIEWRATING") //lb: load balance //lb://freelancerProject : le nom de l'application mission nour njibha men application.properties
                ).route(

                        "api/contracts",r->r.path("/api/contracts/**").uri("lb://Contract") //lb: load balance //lb://freelancerProject : le nom de l'application mission nour njibha men application.properties
                ).route(

                        "api/auth",r->r.path("/ProjetMicroUseryahya/api/auth/**").uri("lb://ProjetMicroUseryahya") //lb: load balance //lb://freelancerProject : le nom de l'application mission nour njibha men application.properties
                )
                .route(

                        "api/users",r->r.path("/ProjetMicroUseryahya/api/users/**").uri("lb://ProjetMicroUseryahya") //lb: load balance //lb://freelancerProject : le nom de l'application mission nour njibha men application.properties
                )
                .route(

                        "profiles",r->r.path("/profiles/**").uri("lb://freelancer-profile-service") //lb: load balance //lb://freelancerProject : le nom de l'application mission nour njibha men application.properties
                ).route(

                        "profiles/me/portfolio",r->r.path("/profiles/me/portfolio/**").uri("lb://freelancer-profile-service") //lb: load balance //lb://freelancerProject : le nom de l'application mission nour njibha men application.properties
                ).route(

                        "skills",r->r.path("/skills/**").uri("lb://freelancer-profile-service") //lb: load balance //lb://freelancerProject : le nom de l'application mission nour njibha men application.properties
                ).route("nestjs-service", r -> r
                        .path("/nestjs/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .addRequestHeader("X-Debug-Gateway", "Route-NestJS-Touched")   // ← ajoute ce header pour debug
                                .filter((exchange, chain) -> {
                                    System.out.println("Gateway → Requête vers NestJS : " + exchange.getRequest().getURI());
                                    return chain.filter(exchange);
                                }))
                        .uri("lb://NESTJS-FREELANCER-SERVICE"))      // ← doit être en MAJUSCULES + le nom que j'ai mis dans "app"
                // === AJOUTE CETTE ROUTE SPÉCIALE POUR SOCKET.IO ===
                .route("nestjs-socketio", r -> r
                        .path("/socket.io/**")                     // tous les paths Socket.IO
                        .uri("lb://NESTJS-FREELANCER-SERVICE"))    // forward vers NestJS
                .build();
    }
}
