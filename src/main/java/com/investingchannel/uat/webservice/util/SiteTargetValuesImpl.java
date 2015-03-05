package com.investingchannel.uat.webservice.util;

import java.util.ArrayList;
import java.util.Hashtable;

import com.investingchannel.uat.webservice.util.TargetCollection;

public class SiteTargetValuesImpl implements SiteTargetValue {
 
	//Declare location one hashtabe which is private and static
	private static Hashtable<String, ArrayList<TargetCollection>> mSiteTargetValues = null;
	
	// make this a singleton
	private static SiteTargetValuesImpl mSiteTargetValueLocationImpl = null;
	   
	private SiteTargetValuesImpl() {
	}
	   
	public static SiteTargetValuesImpl getInstance() {
	      
		if(mSiteTargetValueLocationImpl == null) {
	    	  mSiteTargetValueLocationImpl = new SiteTargetValuesImpl();
	    }
		return mSiteTargetValueLocationImpl;
	}
			
	public Hashtable<String, ArrayList<TargetCollection>> getSiteTargetValue() {
		return mSiteTargetValues;
	}
	
	public void setSiteTargetValue(Hashtable<String, ArrayList<TargetCollection>> pSiteTargetValues) {
		mSiteTargetValues = pSiteTargetValues;
	}
	
	public ArrayList<TargetCollection> getTargetCollection(String pSitename){
			return mSiteTargetValues.get(pSitename);
	}
}
