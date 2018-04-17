package bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;

@SpringBootApplication
@EntityScan("model")
@EnableJpaRepositories({"repository"})
@ComponentScan({"model","repository","service","controller"})
public class Application {



    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
