package com.foo;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ryan
 * Date: 10/2/12
 * Time: 6:17 PM
 */
@Data
@XmlRootElement
public class User {
    private String name;
    private List<String> roles = new ArrayList<>();

    public User withRole(String role) {
        this.roles.add(role);
        return this;
    }
}
