package com.brewtooth.server.web.resources;

import com.brewtooth.server.domain.Malt;
import com.brewtooth.server.service.MaltService;
import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/malt")
@Produces("application/json")
public class MaltResource {

	@Inject
	private MaltService maltService;

	@GET
	@Path("/")
	@Timed
	public Response doStaff() {
		final List<Malt> malts = maltService.getAll();
		// using response to render entities inside unit of work and avoid lazy load exceptions
		return Response.ok(malts).build();
	}
}
