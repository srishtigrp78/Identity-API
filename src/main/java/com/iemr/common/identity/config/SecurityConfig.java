package com.iemr.common.identity.config;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.authentication.RememberMeServices;
//import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

//@Configuration
public class SecurityConfig /*extends WebSecurityConfigurerAdapter*/ {

//    private static final String[] AUTH_WHITELIST = {
//
//            // -- swagger ui
//            "/swagger-resources/**",
//            "/swagger-ui.html",
//            "/v2/api-docs",
//            "/webjars/**"
//    };
//    
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//        	.authorizeRequests()
//            	.antMatchers(AUTH_WHITELIST).permitAll()
//            	.antMatchers("/**/*").denyAll();
//        http
//        	.rememberMe().rememberMeServices(rememberMeServices());
//        
//        http
//        	.sessionManagement().init(getHttp());
//        
//    }
//    
//    @Bean
//    RememberMeServices rememberMeServices() {
//        SpringSessionRememberMeServices rememberMeServices =
//                new SpringSessionRememberMeServices();
//        // optionally customize
//        rememberMeServices.setAlwaysRemember(true);
//        return rememberMeServices;
//    }
}
