package com.github.timmystorms;

import java.util.TimeZone;

import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public final class App {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

	public static void main(final String[] args) {
		// UTC auditing
		TimeZone.setDefault(DateTimeZone.UTC.toTimeZone());
		final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				SpringConfiguration.class);
		final PersonRepository repo = ctx.getBean(PersonRepository.class);
		final Person person = new Person("Timmy");
		repo.save(person);
		LOGGER.debug("All persons: {}", repo.findAll());
		LOGGER.debug("All Timmy's: {}", repo.findByName("Timmy"));
		ctx.close();
	}

}
