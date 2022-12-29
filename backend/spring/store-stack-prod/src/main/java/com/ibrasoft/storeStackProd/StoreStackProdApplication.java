package com.ibrasoft.storeStackProd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ibrasoft.storeStackProd.service.DBInitializationService;

@SpringBootApplication
@EnableJpaRepositories
public class StoreStackProdApplication {
	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(StoreStackProdApplication.class, args);
		DBInitializationService dbInitService = applicationContext.getBean(DBInitializationService.class);
	}

	@Bean
	public ServletWebServerFactory servletContainer() {
		// Enable SSL Trafic
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/*");
				securityConstraint.addCollection(collection);
				context.addConstraint(securityConstraint);
			}
		};

		// Add HTTP to HTTPS redirect
		tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());

		return tomcat;
	}

	/*
	 * We need to redirect from HTTP to HTTPS. Without SSL, this application used
	 * port 8050. With SSL it will use port 8075. So, any request for 8050 needs to
	 * be redirected to HTTPS on 8077.
	 */
	private Connector httpToHttpsRedirectConnector() {
		Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
		connector.setScheme("http");
		connector.setPort(8050);
		connector.setSecure(false);
		connector.setRedirectPort(8077);
		return connector;
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**");
			}
		};
	}

}
