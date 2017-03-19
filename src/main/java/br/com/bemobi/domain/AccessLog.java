package br.com.bemobi.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AccessLog implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable=false)
	private String url;

	@Column
	private Long shortenedUrlId;
	
	@Column
	private Date date;
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}

	public void setShortenedUrlId(Long shortenedUrlId) {
		this.shortenedUrlId = shortenedUrlId;;		
	}
	
	public Long getShortenedUrlId() {
		return shortenedUrlId;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = new Date();
	}
}
