package com.cat.texttranslator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan(basePackages = "com.cat.texttranslator")
public class TextTranslatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TextTranslatorApplication.class, args);
    }
}
