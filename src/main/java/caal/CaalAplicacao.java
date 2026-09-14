package caal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "config",
        "controller",
        "service"
})
@EntityScan(basePackages = "entity")
@EnableJpaRepositories(basePackages = "repository")
public class CaalAplicacao {
    public static void main (String[] args){
        SpringApplication.run(CaalAplicacao.class, args);
    }
}
