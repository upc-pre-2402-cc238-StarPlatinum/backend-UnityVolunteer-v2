package com.acme.backendunityvolunteer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.net.URI;

@SpringBootApplication
public class BackendUnityVolunteerApplication {

    public static void main(String[] args) {
        // Imprimir las variables de entorno para asegurarse de que están bien configuradas
        System.out.println("DB_HOST: " + System.getenv("DB_HOST"));
        System.out.println("DB_PORT: " + System.getenv("DB_PORT"));
        System.out.println("DB_NAME: " + System.getenv("DB_NAME"));
        System.out.println("DB_USER: " + System.getenv("DB_USER"));

        // Iniciar la aplicación Spring Boot
        SpringApplication.run(BackendUnityVolunteerApplication.class, args);

        // Abrir Swagger solo si se está ejecutando localmente
        if (isLocalEnvironment()) {
            openSwaggerUI();
        } else {
            System.out.println("Entorno de producción detectado. No se abrirá automáticamente Swagger UI.");
        }
    }

    private static void openSwaggerUI() {
        try {
            String swaggerUrl = "http://localhost:8080/swagger-ui/index.html";
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(swaggerUrl));
            } else {
                System.out.println("Desktop no soportado. Abre Swagger manualmente: " + swaggerUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para detectar si estamos en un entorno local o de producción
    private static boolean isLocalEnvironment() {
        String railwayEnv = System.getenv("RAILWAY_ENVIRONMENT");
        // Si no se encuentra la variable de entorno, asumimos que es local
        return railwayEnv == null || railwayEnv.isEmpty();
    }
}
