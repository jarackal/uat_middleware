package com.investingchannel.uat.webservice.util;

import java.util.regex.Pattern;

public interface TargetCollection {

	public String getSitename();
	public void setSitename(String pSitename);
	
	public Pattern getUrlkey();
	public void setUrlkey(Pattern pUrlkey);
	
	public String getTarget();
	public void setTarget(String pTarget);
	
}
