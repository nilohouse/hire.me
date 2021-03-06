package br.com.bemobi.domain;

import org.springframework.data.repository.Repository;

public interface ShortenedURLRepository extends Repository<ShortenedURL, Long>{

	void save(ShortenedURL build);

	ShortenedURL findByAlias(String alias);
}
