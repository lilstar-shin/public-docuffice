package com.docuffice.auth.application.port.out;

import com.docuffice.auth.application.dto.response.GoogleTokenResponseDTO;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/token")
@RegisterRestClient(configKey = "google-token-api")
public interface GoogleTokenClient {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<GoogleTokenResponseDTO> exchangeCode(
        @FormParam("code") String code,
        @FormParam("client_id") String clientId,
        @FormParam("client_secret") String clientSecret,
        @FormParam("redirect_uri") String redirectUri,
        @FormParam("grant_type") String grantType
    );
}