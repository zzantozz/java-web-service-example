package com.foo;

import com.sun.jersey.api.NotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

/**
 * Created by IntelliJ IDEA.
 * User: ryan
 * Date: 10/2/12
 * Time: 6:45 PM
 */
@Path("user")
public class UserApi {
    private static Map<String, User> usersByName
            = new HashMap<>();

    @POST
    @Consumes(APPLICATION_JSON)
    public Response create(User user, @Context UriInfo uriInfo) {
        if (usersByName.containsKey(user.getName())) {
            return Response.status(409).entity("User already exists with name " + user.getName()).build();
        }
        usersByName.put(user.getName(), user);
        URI location = uriInfo.getAbsolutePathBuilder().path(user.getName()).build();
        return Response.created(location).build();
    }

    @GET
    @Path("{name}")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    public User getUser(@PathParam("name") String name) {
        if (!usersByName.containsKey(name)) {
            throw new WebApplicationException(Response.status(404).entity("No user with name " + name).build());
        }
        return usersByName.get(name);
    }

    @PUT
    @Path("{name}")
    @Consumes(APPLICATION_JSON)
    public void update(@PathParam("name") String name, User user) {
        if (!usersByName.containsKey(name)) {
            throw new NotFoundException("No user with name " + name);
        }
        usersByName.put(name, user);
    }

    @DELETE
    @Path("{name}")
    public void delete(@PathParam("name") String name) {
        if (!usersByName.containsKey(name)) {
            throw new UserNotFoundException(name);
        }
        usersByName.remove(name);
    }
}
