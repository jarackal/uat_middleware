package com.investingchannel.uat.webservice.config;


public class ServiceCredentialImpl implements ServiceCredential {

	private String mHostname;
	private String mUsername;
	private String mPassword;
	private String mPort;
	private String mDriver;
	private String referrerExpiry;
	private String securityKey;
	
	
	public String getHostname() {
		// TODO Auto-generated method stub
		return mHostname;
	}

	public void setHostname(String pHostname) {
		// TODO Auto-generated method stub
		mHostname = pHostname;
	}

	public String getUsername() {
		// TODO Auto-generated method stub
		return mUsername;
	}

	public void setUsername(String pUsername) {
		// TODO Auto-generated method stub
		mUsername = pUsername;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return mPassword;
	}

	public void setPassword(String pPassword) {
		// TODO Auto-generated method stub
		mPassword = pPassword;
	}

	public String getPort() {
		// TODO Auto-generated method stub
		return mPort;
	}

	public void setPort(String pPort) {
		// TODO Auto-generated method stub
		mPort = pPort;
	}

	public String getDriver() {
		// TODO Auto-generated method stub
		return mDriver;
	}

	public void setDriver(String pDriver) {
		// TODO Auto-generated method stub
		mDriver = pDriver;
	}

	public String getReferrerExpiry() {
		return referrerExpiry;
	}

	public void setReferrerExpiry(String keyExpiry) {
		this.referrerExpiry = keyExpiry;
	}

	public String getSecurityKey() {
		return securityKey;
	}

	public void setSecurityKey(String securityKey) {
		this.securityKey = securityKey;
	}

	
}
