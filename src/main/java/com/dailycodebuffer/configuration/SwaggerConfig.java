package com.dailycodebuffer.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myCustomConfig(){
       return new OpenAPI()
               .info(
                       new Info().title("Splitting Expenses App Api")
                               .description("By Charvi")
               )
               .servers(List.of(new Server().url("http://localhost:8080").description("local"),
                       new Server().url("http://localhost:8082").description("Live")));
    }

}
