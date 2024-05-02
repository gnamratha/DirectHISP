package org.nhind.xdm.springconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter
{
    private static final Logger logger = LogManager.getLogger(WebSecurityConfiguration.class);
    @Override
    public void configure(WebSecurity web) throws Exception 
    {
        try {
            web.ignoring().antMatchers("/actuator/**");
            logger.info("Configured ignoring for /actuator/**");
        } catch (Exception e) {
            logger.error("Error occurred while configuring WebSecurity", e);
            throw e;
        }
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        try {
            http.authorizeRequests().antMatchers("/").permitAll();
            logger.info("Configured authorization for /");

            http.csrf().disable();
            logger.info("Disabled CSRF protection");
        } catch (Exception e) {
            logger.error("Error occurred while configuring HttpSecurity", e);
            throw e;
        }
    }
}

