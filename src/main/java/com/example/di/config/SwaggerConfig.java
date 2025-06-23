package com.example.di.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select().apis(RequestHandlerSelectors.basePackage("com.example.di.controllers"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false);
    }
    
 	private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("Devs4j api")
                .version("1.0.0")
                .license("Apache 2.0")
                .contact(new Contact("@raidentrance", "http://www.devs4j.com", "contacto@devs4j.com"))
                .build();
    }
    
}
