package com.iemr.common.identity.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.iemr.common.identity.utils.config.ConfigProperties;
import com.iemr.common.identity.utils.gateway.email.EmailService;
import com.iemr.common.identity.utils.gateway.email.GenericEmailServiceImpl;
import com.iemr.common.identity.utils.km.KMService;
import com.iemr.common.identity.utils.km.openkm.OpenKMServiceImpl;
import com.iemr.common.identity.utils.redis.RedisStorage;
import com.iemr.common.identity.utils.sessionobject.SessionObject;
import com.iemr.common.identity.utils.validator.Validator;

@Configuration
public class IEMRApplBeans {
	@Bean
	public KMService getOpenKMService() {
		KMService kmService = new OpenKMServiceImpl();
		return kmService;
	}

	@Bean
	public Validator getVaidator() {
		Validator validator = new Validator();
		return validator;
	}

	@Bean
	public EmailService getEmailService() {
		EmailService emailService = new GenericEmailServiceImpl();
		return emailService;
	}

	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSender mailSender = new JavaMailSenderImpl();
		return mailSender;
	}

	@Bean
	public ConfigProperties configProperties() {
		return new ConfigProperties();
	}

	@Bean
	public SessionObject sessionObject() {
		return new SessionObject();
	}

	@Bean
	public RedisStorage redisStorage() {
		return new RedisStorage();
	}

	// @Beanss
	// public RedisConnection redisConnection()
	// {
	// return new RedisConnection();
	// }

	// @Configuration
	// @EnableRedisHttpSession
	// public class Config
	// {

//	@Bean
//	public LettuceConnectionFactory connectionFactory()
//	{
//		return new LettuceConnectionFactory();
//	}
	// }

//	@Bean
//	public RedisHttpSessionConfiguration redisSession()
//	{
//		return new RedisHttpSessionConfiguration();
//	}

	private @Value("${spring.redis.host}") String redisHost;
	private @Value("${spring.redis.port}") int redisPort;

	@Bean
	public LettuceConnectionFactory connectionFactory() {
		System.out.print("Connecting to Redis " + redisHost + ":" + redisPort);

		return new LettuceConnectionFactory(redisHost, redisPort);
	}

	// @Bean
	// public HTTPRequestInterceptor myInterceptor()
	// {
	// return new HTTPRequestInterceptor();
	// }
	// @Bean
	// public KMService getOpenKMService()
	// {
	// KMService kmService = new OpenKMServiceImpl();
	// return kmService;
	// }

	// public static void main(String[] args) {
	// SpringApplication.run(CommonMain.class, args);
	// }
}
