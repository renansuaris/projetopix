package com.pix.gc.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Gerência de Configuração - Projeto PIX",
                description = "Essa API provê endpoints para simular um sistema bancário com Pix.",
                version = "1.0.0",
                contact = @Contact(
                        name = "Equipe 7",
                        email = "renanalencar@alu.ufc.br"
                )
        )
)

public class OpenApiConfiguration {

}

