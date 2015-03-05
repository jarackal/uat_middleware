package com.investingchannel.uat.webservice.util;

import java.util.regex.Pattern;

public class TargetCollectionImpl implements TargetCollection {

	private String mSname;
	private Pattern mUrlkey;
	private String mTarget;
	
	public String getSitename() {
		// TODO Auto-generated method stub
		return mSname;
	}

	public void setSitename(String pSitename) {
		// TODO Auto-generated method stub
		mSname = pSitename;
	}

	
	public String getTarget() {
		// TODO Auto-generated method stub
		return mTarget;
	}

	public void setTarget(String pTarget) {
		// TODO Auto-generated method stub
		mTarget = pTarget;
	}

	public Pattern getUrlkey() {
		// TODO Auto-generated method stub
		return mUrlkey;
	}

	public void setUrlkey(Pattern pUrlkey) {
		mUrlkey = pUrlkey;
		// TODO Auto-generated method stub
		
	}

}
