package com.springboot.blog;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Spring-boot Blog App REST-API's '",
                description = "Spring-Boot Blog App REST API's Documentation ",
                version = "v1.0",
                contact = @Contact(
                        name = "Amarnath",
                        email = "amarnath.kumar@gmail.com",
                        url = "http://localhost:9091"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Spring boot Blog App Documentation GIT Repo",
                url = "https://github.com/MandalKumar"
        )
)
@ComponentScan(basePackages = "com.springboot.blog")
@EnableJpaRepositories(basePackages = "com.springboot.blog.repository")
public class SpringBlogRestApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBlogRestApiApplication.class, args);
        System.out.println(":::::::::::::::::::MyBlogs Application Started::::::::::::::::::::::");
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
