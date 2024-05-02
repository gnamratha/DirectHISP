/* 
 * Copyright (c) 2010, NHIN Direct Project
 * All rights reserved.
 *  
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright 
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright 
 *    notice, this list of conditions and the following disclaimer in the 
 *    documentation and/or other materials provided with the distribution.  
 * 3. Neither the name of the the NHIN Direct Project (nhindirect.org)
 *    nor the names of its contributors may be used to endorse or promote products 
 *    derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY 
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND 
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.nhind.james.matcher;

import java.util.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.james.core.MailAddress;
import org.apache.mailet.Mail;
import org.apache.mailet.base.GenericMatcher;
import org.nhind.config.rest.AddressService;
import org.nhind.james.mailet.DirectXdMailet;
import org.nhindirect.xd.routing.RoutingResolver;
import org.nhindirect.xd.routing.impl.RoutingResolverImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Matcher for recipients that are mapped to XD addresses on non-S/MIME mails.
 * 
 * @author beau
 */
public class RecipientIsXdAndNotSMIME extends GenericMatcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(RecipientIsXdAndNotSMIME.class);
    private static final Log LOGGER1 = LogFactory.getFactory().getInstance(RecipientIsXdAndNotSMIME.class);
    private RoutingResolver routingResolver;
    protected ApplicationContext ctx;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        LOGGER.info("Initializing RecipientIsXdAndNotSMIME matcher");

        ctx = new ClassPathXmlApplicationContext("contexts/XDMailet.xml");
        
        routingResolver = new RoutingResolverImpl(ctx.getBean(AddressService.class));

        LOGGER.info("Initialized RecipientIsXdAndNotSMIME matcher");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<MailAddress> match(Mail mail) throws MessagingException {
        LOGGER.info("Attempting to match XD recipients");

        Collection<MailAddress> recipients = new ArrayList<MailAddress>();

        MimeMessage message = mail.getMessage();
        LOGGER.info("Checking if MimeMessage is S/MIME");
        if (message.isMimeType("application/x-pkcs7-mime") || message.isMimeType("application/pkcs7-mime")) {
            LOGGER.info("MimeMessage is SMIME, skipping");
            return Collections.<MailAddress>emptyList();
        } else {
            LOGGER.info("MimeMessage is not S/MIME");
        }
        LOGGER.info("Iterating through mail recipients");
        for (MailAddress addr : (Collection<MailAddress>) mail.getRecipients()) {
            LOGGER.info("Checking recipient: " + addr);
            if (routingResolver.isXdEndpoint(addr.toString())) {
                recipients.add(addr);
                LOGGER1.info("Matched XD recipient: " + addr);
            }    else {
                LOGGER.info("Recipient " + addr + " is not an XD endpoint");
              //  List<String> addrs = Arrays.asList(addr.toString());
                routingResolver.getXdEndpoint(addr.toString());
                LOGGER.info("Line 101" +  routingResolver.getXdEndpoint(addr.toString()));
            }
        }
        LOGGER.info("Trying to fetch XD ");

        if (recipients.isEmpty()) {
            LOGGER.info("Matched no recipients");
        } else {
            for (MailAddress addr : recipients) {
                LOGGER.info("Matched recipient " + addr.toString());
            }
        }

        return recipients;
    }

  
}
