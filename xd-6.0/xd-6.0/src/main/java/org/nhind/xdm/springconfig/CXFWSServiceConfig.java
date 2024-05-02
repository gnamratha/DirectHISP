package org.nhind.xdm.springconfig;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;

import org.apache.cxf.jaxws.EndpointImpl;
import org.nhind.xdr.XDR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class CXFWSServiceConfig
{
	private static final Logger logger = LoggerFactory.getLogger(CXFWSServiceConfig.class);
	@Autowired
	protected Bus bus;
	
	@Bean 
	@ConditionalOnMissingBean
	public XDR xdSvc()
	{
		logger.info("Creating XDR bean");
		return new XDR();
	}	
	
    @Bean
	@ConditionalOnMissingBean    
    public Endpoint xdrEndpointService() 
    {
		logger.info("Publishing XDR endpoint service");
        final EndpointImpl endpoint = new EndpointImpl(bus, xdSvc());
        endpoint.publish("/DocumentRepository_Service");
        return endpoint;
    }
}
