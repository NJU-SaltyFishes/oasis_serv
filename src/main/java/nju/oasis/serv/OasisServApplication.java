package nju.oasis.serv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class OasisServApplication {

	public static void main(String[] args) {
		SpringApplication.run(OasisServApplication.class, args);
	}

}
