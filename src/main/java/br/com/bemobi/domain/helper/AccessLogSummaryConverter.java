package br.com.bemobi.domain.helper;

import java.util.HashMap;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.bemobi.domain.AccessLogSummary;

import com.google.common.collect.Maps;

@Converter(autoApply=true)
public class AccessLogSummaryConverter implements AttributeConverter<AccessLogSummary, HashMap<String, Object>>{

	@Override
	public HashMap<String, Object> convertToDatabaseColumn(AccessLogSummary summary) {
		HashMap<String, Object> entityAttr = Maps.newHashMap();
		
		entityAttr.put("hits", summary.getHits());
		entityAttr.put("url", summary.getUrl());
		
		return entityAttr;
	}

	@Override
	public AccessLogSummary convertToEntityAttribute(
			HashMap<String, Object> map) {
		return new AccessLogSummary(map.get("url").toString(), Long.valueOf(map.get("hits").toString()));
	}
}
