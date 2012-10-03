package com.foo;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by IntelliJ IDEA.
 * User: ryan
 * Date: 10/2/12
 * Time: 7:00 PM
 */
@Provider
public class UserNotFoundExceptionMapper implements ExceptionMapper<UserNotFoundException> {
    @Override
    public Response toResponse(UserNotFoundException exception) {
        return Response.status(404).entity(exception.getMessage()).build();
    }
}
