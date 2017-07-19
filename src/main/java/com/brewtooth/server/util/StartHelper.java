package com.brewtooth.server.util;

import com.brewtooth.server.BrewToothConfiguration;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import io.dropwizard.configuration.ConfigurationFactory;
import io.dropwizard.configuration.YamlConfigurationFactory;
import io.dropwizard.jackson.Jackson;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.Validation;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class StartHelper {

    private static Injector injector = null;
    public static final String JPA_UNIT = "d1";
    public static final String CONFIG_FILENAME_TEST = "src/test/resources/todo.yml";
    private static String configFilename;

    public static String getConfigFilename() {
        return configFilename;
    }

    public static void setConfigFilename(String configFilename) {
        StartHelper.configFilename = configFilename;
    }

    public static Properties createPropertiesFromConfiguration(BrewToothConfiguration localConfiguration) {

		BrewToothConfiguration.DatabaseConfiguration databaseConfiguration = localConfiguration.getDatabaseConfiguration();
        List<String> propertiesList = new ArrayList();
        propertiesList.add("charset");
        propertiesList.add("useSSL");
        propertiesList.add("hibernate.dialect");
        propertiesList.add("hibernate.show_sql");
        propertiesList.add("hibernate.hbm2ddl.auto");
        propertiesList.add("hibernate.dialect");
//        propertiesList.add("hibernate.connection.driver_class");
//        propertiesList.add("hibernate.username");
//        propertiesList.add("hibernate.password");

        Properties properties = new Properties();
        properties.setProperty("javax.persistence.jdbc.url", databaseConfiguration.getUrl());

        for (String p : propertiesList) {
            String val = databaseConfiguration.getProperties().get(p);
            if (val != null) {
                properties.setProperty(p, val);
            }
        }

        return properties;
    }


    public static Injector getInjector() {
        if (injector == null) {
            throw new RuntimeException("call StartHelper.init() first!");
        }
        return injector;
    }

    public static void init() {
        init(CONFIG_FILENAME_TEST);
    }

	public static void init(String localConfigFilename) {
		configFilename = localConfigFilename;
		BrewToothConfiguration configuration = createConfiguration(localConfigFilename);
		Properties properties = createPropertiesFromConfiguration(configuration);
		JpaPersistModule jpaPersistModule = new JpaPersistModule(JPA_UNIT);
		jpaPersistModule.properties(properties);
		injector = Guice.createInjector(jpaPersistModule /*, new ToDoGuiceModule() */);
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
