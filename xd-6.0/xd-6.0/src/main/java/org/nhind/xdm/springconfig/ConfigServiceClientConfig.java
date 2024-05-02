package org.nhind.xdm.springconfig;

import org.nhind.config.rest.AddressService;
import org.nhind.config.rest.SettingService;
import org.nhind.config.rest.feign.AddressClient;

import org.nhind.config.rest.feign.SettingClient;
import org.nhind.config.rest.impl.DefaultAddressService;
import org.nhind.config.rest.impl.DefaultSettingService;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.ribbon.FeignRibbonClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@Configuration
@EnableFeignClients({"org.nhind.config.rest.feign"})
@ImportAutoConfiguration({RibbonAutoConfiguration.class, FeignRibbonClientAutoConfiguration.class, FeignAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class})
public class ConfigServiceClientConfig
{
	private static final Logger logger = LogManager.getLogger(ConfigServiceClientConfig.class);
	@Bean
	@ConditionalOnMissingBean
	public SettingService settingService(SettingClient settingClient)
	{
		logger.info("Creating SettingService bean");
		return new DefaultSettingService(settingClient);
	}	
	
	@Bean
	@ConditionalOnMissingBean
	public AddressService addressService(AddressClient addressClient)
	{
		logger.info("Creating AddressService bean");
		return new DefaultAddressService(addressClient);
	}
}
