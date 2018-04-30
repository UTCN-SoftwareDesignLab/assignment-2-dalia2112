package bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EntityScan("bookstore.model")
@EnableJpaRepositories({"bookstore.repository"})
@ComponentScan({"bookstore.model","bookstore.repository","bookstore.service.book","bookstore.service.user","bookstore.service.bookorder","bookstore.service.report","bookstore.service.bookApi","bookstore.controller","bookstore.config"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
