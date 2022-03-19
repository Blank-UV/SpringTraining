package com.consumer.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"com.consumer"})
@Import({MessagingConfiguration1.class})
public class ConsumerAppConfig {

}
