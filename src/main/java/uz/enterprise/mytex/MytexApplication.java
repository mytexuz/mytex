package uz.enterprise.mytex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MytexApplication {

    public static void main(String[] args) {
        SpringApplication.run(MytexApplication.class, args);
    }

}
