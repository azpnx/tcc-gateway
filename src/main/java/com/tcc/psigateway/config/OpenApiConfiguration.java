package com.tcc.psigateway.config;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenApiConfiguration {

    @Bean
    @Lazy(value = false)
    public List<GroupedOpenApi> apis(SwaggerUiConfigParameters swaggerUiConfigParameters,
                                     RouteDefinitionLocator locator){

        var definitions = locator.getRouteDefinitions().collectList().block();

        definitions.stream().filter(
                routeDefinition -> routeDefinition.getId()
                        .matches(".psi-*"))
                .forEach(routeDefinition -> {
                    String name = routeDefinition.getId();
                    swaggerUiConfigParameters.addGroup(name);
                    GroupedOpenApi.builder()
                            .pathsToMatch("/" + name + "/**")
                            .group(name).build();
                });

        return new ArrayList<>();
    }
}
