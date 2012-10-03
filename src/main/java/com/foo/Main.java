package com.foo;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import org.glassfish.grizzly.http.server.HttpServer;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: ryan
 * Date: 10/2/12
 * Time: 7:09 PM
 */
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String host = args.length == 2 ? args[0] : "localhost";
        String port = args.length == 2 ? args[1] : "8080";
        HttpServer server = GrizzlyServerFactory.createHttpServer("http://" + host + ":" + port,
                new PackagesResourceConfig("com.foo"));
        server.start();
        Thread.sleep(Long.MAX_VALUE);
    }
}
