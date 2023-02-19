package ru.practicum.ewm.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerSpringConfig {

    //http://localhost:8080/swagger-ui/index.html
    public OpenAPI customOpenAPI() {
        OpenAPI openAPI = new OpenAPI();
        openAPI.info(new Info().title("Explore-with-me service").version("1.0.0"));
        return openAPI;
    }
}
