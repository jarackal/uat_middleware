package com.investingchannel.uat.webservice.config;

import java.util.Map;

public class ServiceConfigurationImpl implements ServiceConfiguration {

	private Map<ServiceCredentialTypeEnum, ServiceCredential> mServiceCredentialMap;
	
	public ServiceConfigurationImpl() {
		
	}
	
	public ServiceCredential getServiceCredential(
			ServiceCredentialTypeEnum pServiceCredentialTypeEnum) {
		// TODO Auto-generated method stub
		return mServiceCredentialMap.get(pServiceCredentialTypeEnum);
	}

	public Map<ServiceCredentialTypeEnum, ServiceCredential> getServiceCredentialMap() {
		// TODO Auto-generated method stub
		return mServiceCredentialMap;
	}

	public void setServiceCredentialMap(
			Map<ServiceCredentialTypeEnum, ServiceCredential> pServiceCredential) {
		// TODO Auto-generated method stub
		mServiceCredentialMap = pServiceCredential;
	}

}
