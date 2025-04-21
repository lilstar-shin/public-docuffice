package com.docuffice.user.interfaces.rest;

import com.docuffice.user.application.dto.request.CreateUserRequestDTO;
import com.docuffice.user.application.service.UserService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    //    @GET
//    public List<User> getAllUsers() {
//        return userService.getAllUsers();
//    }
//
    @GET
    @Path("/{id}")
    public Uni<Response> getUserById(@PathParam("id") Long id) {
        return userService.getUserById(id)
            .onItem().transform(user -> Response.ok(user).build());

    }

    @POST
    public Uni<Response> createUser(@Valid CreateUserRequestDTO user) {

        return userService.createUser(user)
            .onItem()
            .transform(createdUser -> Response.created(URI.create("/users/" + createdUser.getId()))
                .entity(createdUser).build());
    }

//    @PUT
//    @Path("/{id}")
//    public Response updateUser(@PathParam("id") Long id, @Valid User user) {
//        return userService.updateUser(id, user).map(updatedUser -> Response.ok(updatedUser).build())
//                .orElse(Response.status(Response.Status.NOT_FOUND).build());
//    }

//    @DELETE
//    @Path("/{id}")
//    public Response deleteUser(@PathParam("id") Long id) {
//        if (userService.deleteUser(id)) {
//            return Response.noContent().build();
//        } else {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//    }
}
