package com.bobocode.stringtrimmer;

import com.bobocode.stringtrimmer.annotation.EnableStringTrimming;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableStringTrimming
public class StringTrimmerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(StringTrimmerApplication.class, args);
    }
}
