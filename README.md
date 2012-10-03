A Quick ReSTful Web Service in Java
-----------------------------------

This is the final result of a JUG presentation I gave in San Antonio on 2 October. It's an example of developing a simple ReST service in Java using Gradle to build, Groovy+Spock for testing, and JAX-RS/Jersey to create the ReST endpoint.

You can run the service with `gradle run`, run the tests and get code coverage with `gradle test`, or build a distributable zip of the application (including startup scripts and dependencies) with `gradle distZip`.

In presentation, I used the zip file to deploy the service to OpenShift, the RedHat PaaS provider. You can do so, too, by creating a free account and adding a "Do It Yourself" application. Then follow the instructions to clone the OpenShift project and modify the `start` script to call the app's startup script, passing `$OPENSHIFT_INTERNAL_IP` and `$OPENSHIFT_INTERNAL_PORT` as command-line arguments. Make sure to modify the `stop` script to kill the started process, or you won't be able to redeploy the app after changing it.
