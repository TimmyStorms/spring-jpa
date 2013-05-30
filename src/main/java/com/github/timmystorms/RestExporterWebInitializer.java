package com.github.timmystorms;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class RestExporterWebInitializer implements WebApplicationInitializer {

	public void onStartup(final ServletContext servletContext)
			throws ServletException {
		final AnnotationConfigWebApplicationContext webCtx = new AnnotationConfigWebApplicationContext();
		webCtx.register(SpringConfiguration.class);
		final DispatcherServlet dispatcherServlet = new DispatcherServlet(
				webCtx);
		final ServletRegistration.Dynamic reg = servletContext.addServlet(
				"rest-exporter", dispatcherServlet);
		reg.setLoadOnStartup(1);
		reg.addMapping("/*");
	}

}
