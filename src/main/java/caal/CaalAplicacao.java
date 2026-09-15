package caal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "caal.config",
        "caal.controller",
        "caal.service"
})
@EntityScan(basePackages = "caal.entity")
@EnableJpaRepositories(basePackages = "caal.repository")
public class CaalAplicacao {
    public static void main (String[] args){
        SpringApplication.run(CaalAplicacao.class, args);
    }
}
