package com.docuffice.auth.interfaces.rest;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.eclipse.microprofile.config.inject.ConfigProperty;


@Path("/secure")
public class SecureResource {

    @ConfigProperty(name = "oauth.google.client.id")
    String googleClientId;

    @GET
    @Path("login")
    public Response login() {
        String params = "client_id=" + googleClientId +
            "&redirect_uri=" + URLEncoder.encode("http://localhost:8080/oauth/google",
            StandardCharsets.UTF_8) +
            "&response_type=code" +
            "&scope=openid%20email%20profile";
        return Response.seeOther(
            URI.create("https://accounts.google.com/o/oauth2/v2/auth?" + params)).build();
    }
}
