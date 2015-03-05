package com.investingchannel.uat.webservice.config;

import java.util.Map;

public interface ServiceConfiguration {
	public ServiceCredential getServiceCredential(final ServiceCredentialTypeEnum pServiceCredentialTypeEnum);
    public Map<ServiceCredentialTypeEnum, ServiceCredential> getServiceCredentialMap();
    public void setServiceCredentialMap(final  Map<ServiceCredentialTypeEnum, ServiceCredential> pServiceCredential);
}
