package com.investingchannel.uat.webservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import com.investingchannel.uat.webservice.util.CacheManager;
import com.investingchannel.uat.webservice.util.RedisCacheManagerImpl;
import com.investingchannel.uat.webservice.util.SiteTargetValuesImpl;
import com.investingchannel.uat.webservice.util.TargetCollection;

public class CalculatorRequestHandler implements Runnable {
	private static Logger log = Logger.getLogger(
			CalculatorRequestHandler.class.getName());
	

	TargetCalculator targetCalculator;
	
	private static CacheManager mCacheStore;
	private String mSiteName;
	private String mReferrer;
	
	public CalculatorRequestHandler(String pSiteName, String pReferrer) {
		this.mSiteName = pSiteName;
		this.mReferrer = pReferrer;
		targetCalculator = new TargetCalculatorImpl();
		CalculatorRequestHandler.mCacheStore = RedisCacheManagerImpl.getInstance();
	}
	/*Fetches the collection of all rule and target for that rule for the given mSiteName.
	 * If mSiteName dosn't exists or it there is no rule that matches mReferrer then all the rules 
	 * for global_auto_target will calculated.
	 */
	public void computeTargets() {
		try {
			String globalAT = "_global_auto_target";
	
			SiteTargetValuesImpl activeSiteTargetValues = SiteTargetValuesImpl.getInstance();
			ArrayList<TargetCollection> targetCollections = activeSiteTargetValues.getTargetCollection(mSiteName);
	
			String calculatedTarget = null;
			
			if (targetCollections != null) {
				calculatedTarget = targetCalculator.calculateTarget(targetCollections, mReferrer);
			}
			if (targetCollections == null || calculatedTarget == null) {
				mSiteName = globalAT;
				targetCollections = activeSiteTargetValues.getTargetCollection(mSiteName);	
				calculatedTarget = targetCalculator.calculateTarget(targetCollections, mReferrer);
			}
			
			mCacheStore.setKeyValue(mReferrer, calculatedTarget);
		}catch (Exception ex){
			System.out.println(ex.getMessage());
		}
	}

	/*This is the implemented method of Runnable interface.
	 * It checks if Auto Target calculation process is in progress,
	 * If not it check if the Auto Target for the given mReferrer has already been calculated.
	 * If not then it will initiate the process of calculation Auto Target
	 */
	private static ArrayList<String> mReferrerInProcess = new ArrayList<String>() ;

	public void run() {
		try {
			Boolean inCalulation = mReferrerInProcess.contains(mReferrer);
			Boolean inRadisCache = mCacheStore.keyExist(mReferrer);
			
			log.info(new Date().toString()+"::"+" :: Sitename: "+mSiteName+" :: Referrer: "+mReferrer
						+" :: InCalculation: "+inCalulation+" :: inRadisCache: "+inRadisCache);
			
			
			if(inCalulation) 
				return;
			
			if(inRadisCache) 
				return;
			
			mReferrerInProcess.add(mReferrer);
			
			computeTargets();

		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		finally{
			mReferrerInProcess.remove(mReferrer);
		}
		
	}
}
