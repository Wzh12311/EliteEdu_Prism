package com.example.eliteedu_prism;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import springfox.documentation.oas.annotations.EnableOpenApi;


//@ServletComponentScan 使用filter所需要的注解 开启了对servlet组件的支持


@ServletComponentScan
@SpringBootApplication
public class EliteEduPrismApplication {

    public static void main(String[] args) {
        SpringApplication.run(EliteEduPrismApplication.class, args);
    }

}
