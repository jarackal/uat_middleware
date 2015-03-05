package com.investingchannel.uat.webservice.util;

import java.util.ArrayList;
import java.util.Hashtable;

import com.investingchannel.uat.webservice.util.TargetCollection;

public class ATCalculationProgressImpl implements ATCalculationProgress {
 //Declare location one hashtabe which is private and static
	private ArrayList<String> mReferrers = new ArrayList<String>() ;
	
	private static ATCalculationProgressImpl mCalculationInProgressUrl = null;
	   protected ATCalculationProgressImpl() {
	   }
	   
	 public static ATCalculationProgressImpl getInstance() {
	      if(mCalculationInProgressUrl == null) {
	    	  mCalculationInProgressUrl = new ATCalculationProgressImpl();
	      }
	   return mCalculationInProgressUrl;
	   }

	public ArrayList<String> getReferrers() {
		return mReferrers;
	}

	public void setReferrers(ArrayList<String> pReferrers) {
		this.mReferrers = pReferrers;
	}
	
	
//
}
