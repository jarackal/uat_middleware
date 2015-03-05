package com.investingchannel.uat.webservice.util;

import java.util.ArrayList;
import java.util.Hashtable;

import com.investingchannel.uat.webservice.util.TargetCollection;

public interface SiteTargetValue {
	
	public Hashtable<String, ArrayList<TargetCollection>> getSiteTargetValue();
	public void setSiteTargetValue(Hashtable<String, ArrayList<TargetCollection>> siteTargetValuesParam);
	
	
}
