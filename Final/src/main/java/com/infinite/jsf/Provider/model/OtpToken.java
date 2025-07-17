package com.infinite.jsf.Provider.model;

import java.util.Date;

public class OtpToken {
	private Long id;
	  private Provider provider;
	  private String token;
	  private Date expiresAt;
	  public OtpToken(Long id, Provider provider, String token, Date expiresAt) {
		super();
		this.id = id;
		this.provider = provider;
		this.token = token;
		this.expiresAt = expiresAt;
	}
	  public OtpToken(Provider provider, String token, Date expiresAt) {
		    this.provider = provider;
		    this.token    = token;
		    this.expiresAt= expiresAt;
		}

	  @Override
	public String toString() {
		return "OtpToken [id=" + id + ", provider=" + provider + ", token=" + token + ", expiresAt=" + expiresAt + "]";
	}
	  public OtpToken() {
		super();
		// TODO Auto-generated constructor stub
	}
	  public Long getId() {
		  return id;
	  }
	  public void setId(Long id) {
		  this.id = id;
	  }
	  public Provider getProvider() {
		  return provider;
	  }
	  public void setProvider(Provider provider) {
		  this.provider = provider;
	  }
	  public String getToken() {
		  return token;
	  }
	  public void setToken(String token) {
		  this.token = token;
	  }
	  public Date getExpiresAt() {
		  return expiresAt;
	  }
	  public void setExpiresAt(Date expiresAt) {
		  this.expiresAt = expiresAt;
	  }

}
