package com.foo;

/**
 * Created by IntelliJ IDEA.
 * User: ryan
 * Date: 10/2/12
 * Time: 6:59 PM
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String name) {
        super("No user named " + name + " exists");
    }
}
