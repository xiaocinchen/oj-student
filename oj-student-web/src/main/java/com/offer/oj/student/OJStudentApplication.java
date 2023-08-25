package com.offer.oj.student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@ServletComponentScan
@ImportResource("classpath:oj-student-consumer.xml")
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class OJStudentApplication {
    public static void main(String[] args) {
        SpringApplication.run(OJStudentApplication.class, args);
    }
}
