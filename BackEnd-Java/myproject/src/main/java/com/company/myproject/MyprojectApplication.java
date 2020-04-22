package com.company.myproject;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.util.ContextInitializer;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class MyprojectApplication {

    public static final Logger logger = LoggerFactory.getLogger(MyprojectApplication.class);

    public static void main(String[] args) {

        logger.info("Hello from my app!!");


        // Instead of the following code -> Set debug="true" in logback.xml configuration tag
//		// assume SLF4J is bound to logback in the current environment
//        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
//		// print logback's internal status
//        StatusPrinter.print(lc);
		// Instead of the following code -> Set packagingData="true" in logback.xml configuration tag
//		lc.setPackagingDataEnabled(true);

        SpringApplication.run(MyprojectApplication.class, args);

    }


}
