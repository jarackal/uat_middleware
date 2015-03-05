package com.investingchannel.uat.webservice.service;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.investingchannel.uat.webservice.util.TargetCollection;

public class TargetCalculatorImpl implements TargetCalculator {

	/*
	 * Matches all the url_patterns for a given mSiteName with mReferrer.
	 * @ return String|NULL will return target if found else will pass null.
	 */
	public String calculateTarget(
			ArrayList<TargetCollection> pTargetCollections, String pReferrer) {
		{
			try{
				String calculatedTarget = null;
				 
				for (TargetCollection targetCollection : pTargetCollections) {
					Pattern urlPattern = targetCollection.getUrlkey();
					
					Matcher matcher = urlPattern.matcher(pReferrer);
					
					if (matcher.matches()) {
						calculatedTarget = targetCollection.getTarget();
						break;
					}
				}
		
				return calculatedTarget;
			}catch (Exception ex) {
				System.out.println(ex.getMessage());
				return null;
			}
		}
	}

}