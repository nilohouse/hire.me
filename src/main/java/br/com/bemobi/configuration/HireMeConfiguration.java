package br.com.bemobi.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages={ "br.com.bemobi.domain" })
@EntityScan(basePackages={ "br.com.bemobi.domain" })
public class HireMeConfiguration {

}
