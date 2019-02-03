package com.filemanager.springfilemanagerapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger configuration class
 * @author Sergio Díez
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(Predicates.or(PathSelectors.ant("/api/**"), PathSelectors.ant("/actuator/shutdown")))
          .build()
          .ignoredParameterTypes(Exception.class, Throwable.class)
          .useDefaultResponseMessages(false);                                           
    }
	
}
