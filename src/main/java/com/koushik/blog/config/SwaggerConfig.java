package com.koushik.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;



@Configuration
public class SwaggerConfig {
	
    @Bean
	OpenAPI openAPI() {
    	
    	String schemaName="bearerSchema";
    	
    	
		return new OpenAPI()
				
				.addSecurityItem(new SecurityRequirement()
						.addList(schemaName)
						)
				.components(new Components().addSecuritySchemes(schemaName, new SecurityScheme()
						.name(schemaName)
						.type(SecurityScheme.Type.APIKEY)
						.bearerFormat("JWT")
						.scheme("bearer")))
				.info(new Info().title("Blogging API")
						.description("This is bolgging api")
						.version("1.0")
						.contact(new Contact().email("koushikj389@gmail.com").name("koushik"))
						)
				 .externalDocs(new ExternalDocumentation()
			              .description("SpringShop Wiki Documentation")
			              .url("https://springshop.wiki.github.org/docs"));
			
	}

}
