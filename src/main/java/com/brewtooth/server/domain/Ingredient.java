package com.brewtooth.server.domain;

import java.util.Map;

public interface Ingredient {

	Long getId();
	Map<String, Object> getDetails();
}
