package com.brewtooth.server.web.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RestError {

	private static final Logger log = LoggerFactory.getLogger(RestError.class);

	private String message = null;
	private int statusCode = 0;
	private String statusMessage = null;

	public RestError(Response.Status status, String message) {
		this.statusCode = status.getStatusCode();
		this.statusMessage = status.getReasonPhrase();
		this.message = message;
	}

	public Integer getStatusCode() {
		return this.statusCode;
	}

	public String getStatusMessage() {
		return this.statusMessage;
	}

	public String getMessage() {
		return this.message;
	}


	public static Response buildResponse(Response.Status status, String message) {
		return Response.status(status).entity(new RestError(status, message)).type(MediaType.APPLICATION_JSON).build();
	}

}
