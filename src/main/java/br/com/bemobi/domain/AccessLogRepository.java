package br.com.bemobi.domain;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface AccessLogRepository extends Repository<AccessLog, Long> {

	void save(AccessLog accessLog);
	
	@Query(value="select count(al.id) as hits, al.url as url from AccessLog as al group by url order by hits")
	List<Map<String, Object>> getCompleteAccessLogSummary();
}
