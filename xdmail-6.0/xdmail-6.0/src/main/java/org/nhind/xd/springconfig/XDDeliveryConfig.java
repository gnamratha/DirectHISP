package org.nhind.xd.springconfig;

import jdk.nashorn.internal.runtime.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nhind.james.mailet.DirectXdMailet;
import org.nhind.mail.service.DocumentRepository;
import org.nhind.mail.service.XDDeliveryCallback;
import org.nhind.mail.service.XDDeliveryCore;
import org.nhindirect.common.tx.impl.DefaultTxDetailParser;
import org.nhindirect.gateway.smtp.NotificationSettings;
import org.nhindirect.gateway.smtp.ReliableDispatchedNotificationProducer;
import org.nhindirect.xd.routing.RoutingResolver;
import org.nhindirect.xd.transform.impl.DefaultMimeXdsTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XDDeliveryConfig
{
	@Value("direct.gateway.xd.endpointUrl")
	//@Value("${direct.gateway.xd.endpointUrl}")
	protected String endpointURL;
	
	@Autowired
	protected RoutingResolver resolver;
	
	@Autowired
	protected XDDeliveryCallback xdDeliveryCallback;
	private static final Log LOGGER1 = LogFactory.getFactory().getInstance(XDDeliveryConfig.class);
	@Bean
	@ConditionalOnMissingBean
	public XDDeliveryCore xdDeliveryCore()
	{
		final ReliableDispatchedNotificationProducer notificationProducer = 
				new ReliableDispatchedNotificationProducer(new NotificationSettings(true, "Direct XD Delivery Agent", "Your message was successfully dispatched."));
		return new XDDeliveryCore(resolver, xdDeliveryCallback, new DefaultTxDetailParser(), 
				new DefaultMimeXdsTransformer(), new DocumentRepository(), notificationProducer, endpointURL);
	}	
}
