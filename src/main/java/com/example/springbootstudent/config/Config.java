package com.example.springbootstudent.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class Config {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.url}")
    private String urlDb;

    @Value("${spring.datasource.username}")
    private String usernameDb;

    @Value("${spring.datasource.password}")
    private String passwordDb;

    @Bean
    public DataSource postgresqlDataSource(){
        DriverManagerDataSource dataSource=new DriverManagerDataSource();

        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(urlDb);
        dataSource.setUsername(usernameDb);
        dataSource.setPassword(passwordDb);

        return dataSource;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Student App")
                        .version("1.0.0")
                        .description("This is a sample Spring Boot RESTful service using OpenAPI 3."));

    }
}
