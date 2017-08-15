package com.brewtooth.server.util;

import com.brewtooth.server.BrewToothConfiguration;
import com.brewtooth.server.BrewtoothModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import io.dropwizard.configuration.ConfigurationFactory;
import io.dropwizard.configuration.YamlConfigurationFactory;
import io.dropwizard.jackson.Jackson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Validation;
import java.io.File;
import java.util.Properties;

public class StartHelper {

	private static final Logger log = LoggerFactory.getLogger(StartHelper.class);

    private static Injector injector = null;
    public static final String JPA_UNIT = "d1";
    private static String configFilename;

    public static String getConfigFilename() {
        return configFilename;
    }

    public static void setConfigFilename(String configFilename) {
        StartHelper.configFilename = configFilename;
    }

    public static Properties createPropertiesFromConfiguration(BrewToothConfiguration localConfiguration) {

		BrewToothConfiguration.DatabaseConfiguration databaseConfiguration = localConfiguration.getDatabaseConfiguration();

        Properties properties = new Properties();
        properties.setProperty("javax.persistence.jdbc.url", databaseConfiguration.getUrl());

        for (String key : databaseConfiguration.getProperties().keySet()) {
        	String value = databaseConfiguration.getProperties().get(key);
        	if (value != null) {
				properties.setProperty(key, value);
			}
        	log.debug("property \"" + key + "\" = " + value);
		}

        return properties;
    }


    public static Injector getInjector() {
        if (injector == null) {
            throw new RuntimeException("call StartHelper.init() first!");
        }
        return injector;
    }


	public static void init(String localConfigFilename) {
		configFilename = localConfigFilename;
		BrewToothConfiguration configuration = createConfiguration(localConfigFilename);
		Properties properties = createPropertiesFromConfiguration(configuration);
		JpaPersistModule jpaPersistModule = new JpaPersistModule(JPA_UNIT);
		jpaPersistModule.properties(properties);
		injector = Guice.createInjector(jpaPersistModule, new BrewtoothModule());
		injector.getInstance(PersistService.class).start();
	}

	public static BrewToothConfiguration createConfiguration(String configFilename) {
		ConfigurationFactory<BrewToothConfiguration> factory =
			new YamlConfigurationFactory<>(
				BrewToothConfiguration.class,
				Validation.buildDefaultValidatorFactory().getValidator(),
				Jackson.newObjectMapper(),
				""
			);
		BrewToothConfiguration configuration;
		try {
			configuration = factory.build(new File(configFilename));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return configuration;
	}

	public static <T> T getInstance(Class<T> c) {
		return getInjector().getInstance(c);
	}

}
