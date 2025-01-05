package com.springboot.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.springboot.blog")
@EnableJpaRepositories(basePackages = "com.springboot.blog.repository")
public class SpringBlogRestApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBlogRestApiApplication.class, args);
        System.out.println(":::::::::::::::::::MyBlogs Application Started::::::::::::::::::::::");
    }

}
