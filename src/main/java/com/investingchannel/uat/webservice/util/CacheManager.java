package com.investingchannel.uat.webservice.util;

public interface CacheManager {
	
	//will have a static variable in-order to maintain a persistence connection with Redis
	
//	public String getRedisHost();
	
	//will set redis object
	public void setKeyValue(String pReferrer, String pTarget);
	
	//will check if object exists
	public boolean keyExist(String pReferrer);
	
	//validate connection with Redis Server
	
//	public void establishRedisConnection();
	
}
