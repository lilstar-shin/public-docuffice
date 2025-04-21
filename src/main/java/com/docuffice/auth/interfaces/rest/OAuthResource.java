package com.docuffice.auth.interfaces.rest;

import com.docuffice.auth.application.dto.request.GoogleCallbackRequestDTO;
import com.docuffice.auth.application.service.AuthService;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/oauth")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OAuthResource {

    @Inject
    AuthService authService;

    @Inject
    RoutingContext ctx;


    @GET
    @Path("/google")
    public Uni<Response> googleOAuth(@QueryParam("code") String code) {
        String redirectUri = "http://localhost:8080/oauth/google";
        GoogleCallbackRequestDTO googleCallbackRequestDTO = new GoogleCallbackRequestDTO();
        googleCallbackRequestDTO.setCode(code);
        googleCallbackRequestDTO.setRedirectUri(redirectUri);
        return authService.googleOAuthLogin(googleCallbackRequestDTO, ctx)
            .map(auth -> Response.ok().entity(auth).build());
    }

    @POST
    @Path("/google")
    public Uni<Response> googleOAuthPost(@Valid GoogleCallbackRequestDTO dto) {
        return authService.googleOAuthLogin(dto, ctx)
            .map(auth -> Response.ok().entity(auth).build());
    }
}
