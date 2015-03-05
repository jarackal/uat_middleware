package com.investingchannel.uat.webservice.process;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class AutoTargetJob extends QuartzJobBean {

	private AutoTargetTask mAutoTargetTask;
	
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		
		mAutoTargetTask.populateTargets();
	}

	public void setAutoTargetTask(AutoTargetTask mAutoTargetTask) {
		this.mAutoTargetTask = mAutoTargetTask;
	}
}
