package com.ipsas.projet.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
		System.out.println("************* gateway service is running ! *************");
	}

	@Bean
	DiscoveryClientRouteDefinitionLocator discoveryRoutes(ReactiveDiscoveryClient dc, DiscoveryLocatorProperties dlp) {
	    return new DiscoveryClientRouteDefinitionLocator(dc, dlp);
	}

}
