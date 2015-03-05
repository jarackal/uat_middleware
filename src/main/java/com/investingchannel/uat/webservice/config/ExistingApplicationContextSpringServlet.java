package com.investingchannel.uat.webservice.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.spi.container.WebApplication;
import com.sun.jersey.spi.spring.container.SpringComponentProviderFactory;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;

public class ExistingApplicationContextSpringServlet extends SpringServlet implements ApplicationContextAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8537389917595333473L;

	private final static transient Log LOG = LogFactory.getLog(ExistingApplicationContextSpringServlet.class);

    private ApplicationContext mApplicationContext;

    public ExistingApplicationContextSpringServlet() {
    }
    
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		// TODO Auto-generated method stub
		mApplicationContext = applicationContext;
		
	}
	
	protected void initiate(final ResourceConfig pResourceConfig, final WebApplication pWebApplication) {
        try {
            final SpringComponentProviderFactory springComponentProviderFactory = new SpringComponentProviderFactory(
                    pResourceConfig, (ConfigurableApplicationContext) mApplicationContext);
            pWebApplication.initiate(pResourceConfig, springComponentProviderFactory);
        }
        catch (final RuntimeException e) {
            LOG.fatal("Exception occurred when intialization", e);
            throw e;
        }
    }

}
