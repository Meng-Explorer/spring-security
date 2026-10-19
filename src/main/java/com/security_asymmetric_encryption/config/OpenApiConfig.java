package com.security_asymmetric_encryption.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Spring Security JWT Asymmetric Encryption",
                        email = "langmeng514@gmail.com",
                        url = "https://github.com/Meng-Explorer"

                ),
                description = "OpenApi Documentation for Spring Security Project",
                title = "OpenAPI Specification",
                version = "1.0",
                license = @License(
                        name = "Di MengLang",
                        url = "https://some-url.com"
                ),
                termsOfService = ""
        ),
        servers = {
                @Server(url = "http://localhost:8081",description = "Local Server Development"),
                @Server(url = "http://production.url",description = "Production Server")
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }

)
@SecurityScheme(
        name = "bearerAuth",
        description ="JWT Auth Description" ,
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@Configuration
public class OpenApiConfig {

}
