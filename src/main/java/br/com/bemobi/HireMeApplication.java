package br.com.bemobi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "br.com.bemobi")
public class HireMeApplication extends SpringBootServletInitializer {

	private static Class<HireMeApplication> applicationClass = HireMeApplication.class;

	public static void main(String[] args) {
		SpringApplication.run(applicationClass, args);
	}
}
