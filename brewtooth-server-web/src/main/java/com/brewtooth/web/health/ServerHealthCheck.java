package com.brewtooth.web.health;

public class ServerHealthCheck extends com.codahale.metrics.health.HealthCheck {

	@Override
	protected Result check() throws Exception {
		return Result.healthy();
	}
}
