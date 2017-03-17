package br.com.bemobi.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

@Configuration
@EnableJpaRepositories(basePackages={ "br.com.bemobi.domain" })
@EntityScan(basePackages={ "br.com.bemobi.domain" })
public class HireMeConfiguration {

	@Bean
	public HashFunction hashing() {
		return Hashing.murmur3_32();
	}
}
