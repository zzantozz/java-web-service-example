package com.foo

import com.sun.jersey.api.client.ClientResponse
import com.sun.jersey.test.framework.JerseyTest
import spock.lang.Shared
import spock.lang.Specification

import static javax.ws.rs.core.MediaType.APPLICATION_JSON
/**
 * Created by IntelliJ IDEA.
 * User: ryan
 * Date: 10/2/12
 * Time: 6:22 PM
 */
class SystemTest extends Specification {
    @Shared def jerseyTest = new JerseyTest(['com.foo'] as String[]) {}
    @Shared def resource = jerseyTest.resource()
    def randomness = UUID.randomUUID()

    def setupSpec() {
        jerseyTest.setUp()
    }

    def cleanupSpec() {
        jerseyTest.tearDown()
    }

    def 'users can be create'() {
        when:
        def response = create user("bob $randomness")

        then:
        response.status == 201
        response.location != null
    }

    def 'creating two users with the same name is a conflict'() {
        when:
        def username = "bob $randomness"
        def firstResponse = create user(username)
        def secondResponse = create user(username)

        then:
        firstResponse.status == 201
        secondResponse.status == 409
    }

    def 'created users can be retrieved'() {
        given:
        def createResponse = create user("fred $randomness")

        when:
        def retrieveResponse = retrieve createResponse.location

        then:
        retrieveResponse.status == 200
        def user = retrieveResponse.getEntity(User)
        user.name == "fred $randomness"
    }

    def "operations on non-existent users return 'not found'"() {
        expect:
        response.status == 404

        where:
        response << [
            retrieve("a user that doesn't exist"),
            update("a user that doesn't exist", user('foo')),
            delete("a user that doesn't exist"),
        ]
    }

    def 'updating a user succeeds'() {
        given:
        create user("bob $randomness")

        when:
        def updateResponse =
            update("bob $randomness",
                    user("bob $randomness").withRole('admin'))

        then:
        updateResponse.status == 204
        def user = retrieve("bob $randomness").getEntity(User)
        user.roles.contains 'admin'
    }

    def 'deleting a user succeeds'() {
        given:
        create user("bob $randomness")

        when:
        def response = delete "bob $randomness"

        then:
        response.status == 204
        retrieve("bob $randomness").status == 404
    }

    def create(user) {
        resource.path('user').entity(user).type(APPLICATION_JSON)
            .post(ClientResponse)
    }

    def retrieve(URI location) {
        resource.uri(location).accept(APPLICATION_JSON).get(ClientResponse)
    }

    def retrieve(String username) {
        resource.path("user/$username").accept(APPLICATION_JSON).get(ClientResponse)
    }

    def update(String username, User user) {
        resource.path("user/$username").type(APPLICATION_JSON).entity(user).put(ClientResponse)
    }

    def delete(String username) {
        resource.path("user/$username").delete(ClientResponse)
    }

    def user(String name) {
        new User().with {
            it.name = name
            return it
        }
    }
}
