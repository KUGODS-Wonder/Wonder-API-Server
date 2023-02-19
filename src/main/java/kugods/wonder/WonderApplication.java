package kugods.wonder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class WonderApplication {

	public static void main(String[] args) {
		SpringApplication.run(WonderApplication.class, args);
	}

}
