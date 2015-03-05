package com.investingchannel.uat.webservice.main;

public class RunService {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		final StartService start = new StartServiceImpl();
        final Thread startThread = new Thread(start);
        startThread.start();
        
        final StopService stop = new StopServiceImpl();
        final Thread stopThread = new Thread(stop);
        final Runtime runtime = Runtime.getRuntime();
        runtime.addShutdownHook(stopThread);
	}
}