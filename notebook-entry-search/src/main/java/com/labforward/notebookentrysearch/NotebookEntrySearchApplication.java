package com.labforward.notebookentrysearch;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableJpaAuditing
@EnableTransactionManagement
public class NotebookEntrySearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotebookEntrySearchApplication.class, args);
	}
	
	@Bean
	public Docket SwaggerConfiguration() {
	      
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("com.labforward.notebookentrysearch"))
				.build()
				.apiInfo(apiInfo())
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET,                     
				  Arrays.asList(new ResponseMessageBuilder()   
				    .code(500)
				    .message("internal Server error")
				    .responseModel(new ModelRef("Error"))
				    .build(),
				    new ResponseMessageBuilder() 
				      .code(403)
				      .message("Forbidden!")
				      .build()));
	}
	
	private ApiInfo apiInfo() {
	    return new ApiInfo(
	      "LabForward REST APIs", 
	      "Researcher Notebook APIs", 
	      "API TOS", 
	      "Terms of service", 
	      new Contact("Dev Team", "https://labforward.com", "dev@labforward.com"), 
	      "License of API", "API license URL", Collections.emptyList());
	}

}
