package com.investingchannel.uat.webservice.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UATService implements Service {

	private static final String PROPERTY_SPRING_CONFIGURATIONS = "spring.configurations";
	private ClassPathXmlApplicationContext mClassPathXmlApplicationContext;
	
	
	public void startService() {
		// TODO Auto-generated method stub
		
		final String springConfigurationsProperty =
	            System.getProperty(PROPERTY_SPRING_CONFIGURATIONS);
			final String[] springConfigurations = springConfigurationsProperty.split(",");
			
			mClassPathXmlApplicationContext = new ClassPathXmlApplicationContext();
			mClassPathXmlApplicationContext.setId("ApplicationContext");
			mClassPathXmlApplicationContext.setConfigLocations(springConfigurations);
			mClassPathXmlApplicationContext.refresh();
			mClassPathXmlApplicationContext.start();
	}

	public void stopService() {
		// TODO Auto-generated method stub
		
		 	mClassPathXmlApplicationContext.stop();
		 	mClassPathXmlApplicationContext.close();
		 	mClassPathXmlApplicationContext = null;
		
	}

}
