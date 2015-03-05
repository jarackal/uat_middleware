package com.investingchannel.uat.webservice.service;

import java.util.ArrayList;

import com.investingchannel.uat.webservice.util.TargetCollection;

public interface TargetCalculator  {
	String calculateTarget(ArrayList<TargetCollection> targetCollections, String referrer);
}
