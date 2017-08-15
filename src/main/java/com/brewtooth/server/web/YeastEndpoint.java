package com.brewtooth.server.web;

import com.brewtooth.server.domain.Yeast;

import javax.ws.rs.Path;

@Path("/yeasts")
public class YeastEndpoint extends IngredientEndpoint<Yeast> {

}
