package com.investingchannel.uat.webservice.config;

public interface ServiceCredential {
	
	public String getHostname();
	public void setHostname(final String pHostname);
	
	public String getUsername();
	public void setUsername(final String pUsername);
	
	public String getPassword();
	public void setPassword(final String pPassword);
	
	public String getPort();
	public void setPort(final String pPort);
	
	public String getDriver();
	public void setDriver(final String pDriver);
	
	public String getReferrerExpiry();
	public void setReferrerExpiry(String mRedisExpiryPeriod);
	
	public String getSecurityKey();
	public void setSecurityKey(String securityKey);
	
}
