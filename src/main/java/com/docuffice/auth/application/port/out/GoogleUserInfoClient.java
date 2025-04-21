package com.docuffice.auth.application.port.out;

import com.docuffice.auth.application.dto.response.GoogleUserInfoResponseDTO;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/oauth2/v1")
@RegisterRestClient(configKey = "google-userinfo-api")
public interface GoogleUserInfoClient {

    @GET
    @Path("/userinfo")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<GoogleUserInfoResponseDTO> fetchUserInfo(
        @HeaderParam("Authorization") String bearerToken
    );
}
