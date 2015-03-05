package com.investingchannel.uat.webservice.main;

public class StopServiceImpl implements StopService {

	public void run() {
		// TODO Auto-generated method stub
		final ServiceManager mServiceManager = ServiceManagerImpl.getServiceManager();
		
		mServiceManager.stopAllService();
	}

}
