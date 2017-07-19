package com.brewtooth.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Maps;
import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class BrewToothConfiguration extends Configuration {

	public class DatabaseConfiguration {
		@NotNull
		private String url = null;
		@NotNull
		private Map<String, String> properties = Maps.newLinkedHashMap();

		public Map<String, String> getProperties() {
			return properties;
		}

		public void setProperties(Map<String, String> properties) {
			this.properties = properties;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}

	@JsonProperty
	private DatabaseConfiguration databaseConfiguration = null;

	public DatabaseConfiguration getDatabaseConfiguration() {
		return databaseConfiguration;
	}

	public void setDatabaseConfiguration(DatabaseConfiguration databaseConfiguration) {
		this.databaseConfiguration = databaseConfiguration;
	}

//	@Valid
//	@NotNull
//	@JsonProperty("database")
//	private DataSourceFactory database = new DataSourceFactory();
//
//	public DataSourceFactory getDataSourceFactory() {
//		return database;
//	}
}
