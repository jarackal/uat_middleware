package com.investingchannel.uat.webservice.resource;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.Response;


public interface ATWebServiceResource {

	public Response calculateTarget(@HeaderParam("site-name") String siteName
			,@HeaderParam("referrer") String referrer, @HeaderParam("key") String pKey);
	
	public String evaluateRegEx();
}
