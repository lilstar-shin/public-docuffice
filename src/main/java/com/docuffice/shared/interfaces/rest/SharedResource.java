package com.docuffice.shared.interfaces.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/")
public class SharedResource {

    @GET
    @Path("/favicon.ico")
    public Response favicon() {
        return Response.status(Status.OK)
            .build();
    }
}
