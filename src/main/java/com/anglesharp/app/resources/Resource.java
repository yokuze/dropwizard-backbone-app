package com.anglesharp.app.resources;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public abstract class Resource {
  
  protected void notFound() {
    throw new WebApplicationException(Response.status(Status.NOT_FOUND).build());
  }
  
  protected void notFound(Object entity) {
    throw new WebApplicationException(Response.status(Status.NOT_FOUND).entity(entity).build());
  }
  
  protected void invalid(Object entity) {
    throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity(entity).build());
  }
}
