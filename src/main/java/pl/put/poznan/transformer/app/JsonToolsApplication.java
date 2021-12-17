package pl.put.poznan.transformer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class description
 * @author Me
 * @version 1.0
 */
@SpringBootApplication(scanBasePackages = {"pl.put.poznan.transformer.rest"})
public class JsonToolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JsonToolsApplication.class, args);
    }
}
