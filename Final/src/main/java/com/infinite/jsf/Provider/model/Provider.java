package com.infinite.jsf.Provider.model;

import java.util.HashSet;
import java.util.Set;


	public class Provider {
	    private Long id;
	    private String email;
	    private String passwordHash;
	    private Set<PasswordHistory> history = new HashSet<>();
	    private Set<OtpToken> otps = new HashSet<>();

	    // Constructors
	    public Provider() {}

	    public Provider(String email, String passwordHash) {
	        this.email = email;
	        this.passwordHash = passwordHash;
	    }

	    public Provider(Long id, String email, String passwordHash) {
	        this.id = id;
	        this.email = email;
	        this.passwordHash = passwordHash;
	    }

	    // Getters & Setters

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getPasswordHash() {
	        return passwordHash;
	    }

	    public void setPasswordHash(String passwordHash) {
	        this.passwordHash = passwordHash;
	    }

	    public Set<PasswordHistory> getHistory() {
	        return history;
	    }

	    public void setHistory(Set<PasswordHistory> history) {
	        this.history = history;
	    }

	    public Set<OtpToken> getOtps() {
	        return otps;
	    }

	    public void setOtps(Set<OtpToken> otps) {
	        this.otps = otps;
	    }

	    // Helper methods to manage collections

	    public void addPasswordHistory(PasswordHistory ph) {
	        ph.setProvider(this);
	        this.history.add(ph);
	    }

	    public void removePasswordHistory(PasswordHistory ph) {
	        ph.setProvider(null);
	        this.history.remove(ph);
	    }

	    public void addOtpToken(OtpToken token) {
	        token.setProvider(this);
	        this.otps.add(token);
	    }

	    public void removeOtpToken(OtpToken token) {
	        token.setProvider(null);
	        this.otps.remove(token);
	    }
	}

	
	  
