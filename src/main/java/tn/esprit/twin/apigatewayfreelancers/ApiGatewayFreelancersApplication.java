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
                "mission",r->r.path("/freelancerProject/mission/**").uri("lb://freelancerProject") //lb: load balance //lb://freelancerProject : le nom de l'application mission nour njibha men application.properties
        ).build();
    }
}
