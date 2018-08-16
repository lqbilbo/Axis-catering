package com.threequick.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author luoqi04
 * @version $Id: WebConfig.java, v 0.1 2018/8/16 下午11:38 luoqi Exp $
 */
@Configuration
@ComponentScan("com.threequick.catering")
public class WebConfig extends WebMvcConfigurationSupport {


}
