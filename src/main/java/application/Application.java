package application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by ekonovalova on 12/5/2016.
 */

@EnableAutoConfiguration
@SpringBootApplication
public class Application {

    @Autowired
    public static void main(String[] args) { SpringApplication.run(Application.class, args); }
}
