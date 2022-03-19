package com.epam.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
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

    @Value("${enable.swagger.plugin:true}")
    private boolean enableSwaggerPlugin;

    @Bean
    public Docket customImplementation()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .enable(enableSwaggerPlugin)
                .apiInfo(apiInfo());
    }

    ApiInfo apiInfo()
    {
        return new ApiInfoBuilder()
                .title("Employee Swagger UI")
                .description("SWagger UI for Employee microservices")
                .license("Yuvaraj")
                .licenseUrl("https://www.google.com")
                .version("1.0")
                .contact(new Contact("Yuvaraj Pradhan","https://www.google.com","test@gmail.com"))
                .build();
    }


}
