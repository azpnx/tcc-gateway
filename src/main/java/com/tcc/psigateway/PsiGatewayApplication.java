package com.tcc.psigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PsiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(PsiGatewayApplication.class, args);
	}

}
