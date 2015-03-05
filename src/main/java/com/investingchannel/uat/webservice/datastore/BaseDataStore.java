package com.investingchannel.uat.webservice.datastore;

import java.sql.Connection;
import java.util.HashMap;


public interface BaseDataStore {
	
	public HashMap<String, String> getRedis();
	
	public Connection getDatabaseConnection();
	
	public String getValidationKey();
}
