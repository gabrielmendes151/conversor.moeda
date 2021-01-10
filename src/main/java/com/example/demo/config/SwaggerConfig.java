package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    private String descricao = "Documentação da aplicação De Moedas Converter API";
    private String url = "Apache License Version";
    private String pacoteBase = "com.example.demo";

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/api/docs", "/swagger-ui.html");
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(info())
            .select()
            .apis(RequestHandlerSelectors.basePackage(pacoteBase))
            .paths(PathSelectors.any())
            .build();
    }

    private ApiInfo info() {
        return new ApiInfoBuilder()
            .title("Conversor de Moeda")
            .description(descricao)
            .version("1.0.0")
            .licenseUrl(url)
            .build();
    }
}
