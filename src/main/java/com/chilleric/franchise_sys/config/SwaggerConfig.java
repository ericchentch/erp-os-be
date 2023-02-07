package com.chilleric.franchise_sys.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Franchise API", version = "${api.version}"))
@SecurityScheme(name = "Bearer Authentication", type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT", scheme = "Bearer")
public class SwaggerConfig {

}
