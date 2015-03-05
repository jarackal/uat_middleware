package com.investingchannel.uat.webservice.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.investingchannel.uat.webservice.datastore.DatabaseStoreImpl;

public class StartServiceImpl implements StartService {

	Log log = LogFactory.getLog(StartServiceImpl.class);
	
	public void run() {
		ServiceManager mServiceManager = ServiceManagerImpl.getServiceManager();
		
		UATService mUATService = new UATService();
		mServiceManager.addService(mUATService);
		mUATService.startService();
		
		DatabaseStoreImpl mDatabaseStoreImpl = new DatabaseStoreImpl();
		mDatabaseStoreImpl.getAutoTargets();
	 
	}
}
