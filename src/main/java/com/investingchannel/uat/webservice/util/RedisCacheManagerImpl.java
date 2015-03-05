package com.investingchannel.uat.webservice.util;

import com.investingchannel.uat.webservice.datastore.BaseDataStoreImpl;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

/*
 * Manages Redis related functions
 */
public class RedisCacheManagerImpl extends BaseDataStoreImpl implements CacheManager {

	private static Jedis mRedisConnector;
	
	private static RedisCacheManagerImpl mRedisCacheManagerImpl = null;
	   
	private RedisCacheManagerImpl() {
	}
	   
	public static RedisCacheManagerImpl getInstance() {
	      
		if(mRedisCacheManagerImpl == null) {
			mRedisCacheManagerImpl = new RedisCacheManagerImpl();
	    }
		return mRedisCacheManagerImpl;
	}
	
	
	private final String REDIS_HOST = getRedisHost();
	
	/*
	 *Sets referrer as key and target as it's values 
	 *
	 *@param pReferrer referrer which has to be set as unique key
	 *@param pTarget the calculated target which is stored as values
	 */

	public void setKeyValue(String pReferrer, String pTarget) {
		if(pReferrer == null) 
			return;
		if(mRedisConnector == null)
			ensureRedisConnection();	
			
		mRedisConnector.set(pReferrer,pTarget);
		int expiryPeriod = getKeyExpiryTimePeriod();
		mRedisConnector.expire(pReferrer, expiryPeriod);
		
	}
	/*
	 *Checks if referrer exists in the Redis data store
	 *
	 *@param pReferrer referrer which is to checked in Redis data store.
	 *
	 *@return boolean true if referrer exists and false if not.
	 */
	public boolean keyExist(String pReferrer) {
		try{
			if(pReferrer == null)
				return false;
			if(mRedisConnector == null)
				ensureRedisConnection();
			
			return mRedisConnector.exists(pReferrer);
			
		}catch(Exception ex)	{
			System.out.println(ex.getMessage());
			return false;
		}
	}
	/*
	 *Fetched redis Host address from config file.
	 *
	 *@return String hostname for Redis
	 */
	public String getRedisHost() {
		try{
			return getRedis().get("hostname");
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}
	public Integer getKeyExpiryTimePeriod() {
		try{
			return Integer.parseInt(getRedis().get("expiry"));
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}
	/*
	 * establish a connection if and when the static variable mRedisConnector is null.
	 */
	public void ensureRedisConnection() {
		try{
			if (mRedisConnector == null) 
				mRedisConnector = new Jedis(REDIS_HOST);	
		}catch(JedisException ex) {
			System.out.println(ex.getMessage());
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
 
}
