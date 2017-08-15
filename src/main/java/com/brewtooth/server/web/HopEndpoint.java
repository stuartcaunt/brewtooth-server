package com.brewtooth.server.web;

import com.brewtooth.server.domain.Hop;

import javax.ws.rs.Path;

@Path("/hops")
public class HopEndpoint extends IngredientEndpoint<Hop> {

}
