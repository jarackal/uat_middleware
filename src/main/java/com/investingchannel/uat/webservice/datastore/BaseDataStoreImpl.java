package com.investingchannel.uat.webservice.datastore;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.investingchannel.uat.webservice.config.ServiceConfiguration;
import com.investingchannel.uat.webservice.config.ServiceCredential;
import com.investingchannel.uat.webservice.config.ServiceCredentialTypeEnum;


public class BaseDataStoreImpl implements BaseDataStore {

	private static ServiceConfiguration mServiceConfiguration;
	private Connection mDBConnection = null;
	
	public ServiceConfiguration getmServiceConfiguration() {
		return mServiceConfiguration;
	}

	@Autowired
	public void setmServiceConfiguration(ServiceConfiguration mServiceConfiguration) {
		BaseDataStoreImpl.mServiceConfiguration = mServiceConfiguration;
	}

	
	public HashMap<String,String> getRedis() {
		ServiceCredential redisServiceCredential = mServiceConfiguration.getServiceCredential(ServiceCredentialTypeEnum.REDIS);
		HashMap<String,String> redisConfigs = new HashMap<String,String>();
		
		redisConfigs.put("hostname",redisServiceCredential.getHostname());
		redisConfigs.put("expiry",redisServiceCredential.getReferrerExpiry());
		return redisConfigs;
	}
	
	public String getValidationKey() {
		ServiceCredential SecurityKeyServiceCredential = mServiceConfiguration.getServiceCredential(ServiceCredentialTypeEnum.SECURITY_KEY);
		String key = SecurityKeyServiceCredential.getSecurityKey();
		return key;
	}

	public Connection getDatabaseConnection() {
		// TODO Auto-generated method stub
		
		if(mDBConnection == null) {
			try {
				final ServiceCredential serviceCredential = mServiceConfiguration.getServiceCredential(ServiceCredentialTypeEnum.DB);
				
				MysqlDataSource datasource = new MysqlDataSource();
				datasource.setUrl(serviceCredential.getHostname());
				datasource.setUser(serviceCredential.getUsername());
				datasource.setPassword(serviceCredential.getPassword());
				
				mDBConnection = datasource.getConnection();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
		}
		
		return mDBConnection;
	}

}
