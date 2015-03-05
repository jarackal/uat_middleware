	package com.investingchannel.uat.webservice.main;

import java.util.List;
import java.util.ArrayList;

public class ServiceManagerImpl implements ServiceManager {
	
	private static ServiceManager mServiceManager;
	private final List<Service> mServices;
	
	static {
		mServiceManager = new ServiceManagerImpl();
	}

	public static ServiceManager getServiceManager() {
		
		return mServiceManager;
	}
	
	private ServiceManagerImpl() {
		 mServices = new ArrayList<Service>();
	}
	
	public void addService(final Service pService) {
		// TODO Auto-generated method stub
		
		mServices.add(pService);
		
	}

	public void stopAllService() {
		// TODO Auto-generated method stub
		
		for(final Service service : mServices) {
			
			service.stopService();
		}
		
	}

}
