package com.investingchannel.uat.webservice.resource;


import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

import com.investingchannel.uat.webservice.datastore.BaseDataStoreImpl;
import com.investingchannel.uat.webservice.service.*;

/*
 * Web service implementation for auto target related features.
 * Currently there are two features that are provided 1) calculation and 
 * 2) validate target rule regular expression.
 */
@Path("/autotarget")
@Component
public class ATWebServiceResourceImpl implements ATWebServiceResource {
	
	
	/*
	 *This is the function on which the /autotarget/calculate request lands.
	 *this will trigger a new process of auto-target calculation request in a thread and respond back a 203 status.
	 *The parameters sitename and referrer is passed by the client in the header module of the the HTTP request
	 *
	 * @param pSitename contains the sitename.
	 * @param pReferrer holds the referrer for which target value has to be found.
	 * 
	 * @return HTTP response via status code.
	 */
	@GET
	@Path("/calculate")
	public Response calculateTarget(@HeaderParam("site-name") String pSiteName
										,@HeaderParam("referrer") String pReferrer, @HeaderParam("key") String pKey) {
		String mKey = new BaseDataStoreImpl().getValidationKey();
		try {
			if(!pKey.equals(mKey) || pSiteName == null || pReferrer==null)
				return Response.status(HttpsURLConnection.HTTP_BAD_REQUEST).build();
			
			
			CalculatorRequestHandler handler = new CalculatorRequestHandler(pSiteName,pReferrer);
			final Thread startThread = new Thread(handler);
			startThread.start();
			
			return Response.status(HttpsURLConnection.HTTP_NOT_AUTHORITATIVE).build();
		
		} catch(Exception ex) {
			return Response.status(HttpsURLConnection.HTTP_INTERNAL_ERROR).build();
		}
		
	}
	
	@GET
	@Path("/evaluateRegEx")
	@Produces(MediaType.TEXT_PLAIN)
	public String evaluateRegEx() {
		// TODO Auto-generated method stub
		return "false";
	}

	
}
