package com.acme.backendunityvolunteer.shared.intraestructure;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        OpenAPI openAPI = new OpenAPI()
                .info(new Info()
                        .title("VolunteerLink API")
                        .description("API for managing VolunteerLink platform functionalities")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("VolunteerLink Wiki Documentation")
                        .url("https://volunteerlink.wiki.github.org/docs"));

        // Definir el servidor con `https` para producción
        Server server = new Server();
        if (isProductionEnvironment()) {
            server.setUrl("https://backend-movil-production.up.railway.app"); // Cambia esta URL a tu dominio de producción
        } else {
            server.setUrl("http://localhost:8080"); // Para desarrollo local
        }

        openAPI.addServersItem(server);
        return openAPI;
    }

    private boolean isProductionEnvironment() {
        String railwayEnv = System.getenv("RAILWAY_ENVIRONMENT");
        // Si la variable RAILWAY_ENVIRONMENT existe, asumimos que estamos en producción
        return railwayEnv != null && !railwayEnv.isEmpty();
    }
}