package com.mirco.notes.shared.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    private static final String BEARER_AUTHENTICATION = "Bearer Authentication";
    private static final String BEARER_FORMAT_JWT = "JWT";
    private static final String BEARER_SCHEME = "bearer";

    @Bean
    public OpenAPI openAPI() {
        Contact contactInfo = new Contact().name("Mirco Estefano Pilco Peralta");

        Info license = new Info()
                .title("Notes REST API")
                .description("REST API to manage notes created by Mirco Estefano Pilco Peralta.")
                .version("1.0-SNAPSHOT")
                .contact(contactInfo);

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(BEARER_AUTHENTICATION))
                .components(new Components().addSecuritySchemes(BEARER_AUTHENTICATION, createAPIKeyScheme()))
                .info(license);
    }


    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat(BEARER_FORMAT_JWT)
                .scheme(BEARER_SCHEME);
    }

}
