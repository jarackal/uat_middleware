package com.investingchannel.uat.webservice.process;

import com.investingchannel.uat.webservice.datastore.DatabaseStoreImpl;

public class AutoTargetTask {
	
	public void populateTargets() {
		
//		System.out.println("I am in......");
		
		DatabaseStoreImpl mDatabaseStoreImpl = new DatabaseStoreImpl();
		
		mDatabaseStoreImpl.getAutoTargets();
		
//		System.out.println("I am out......");
	}

}
