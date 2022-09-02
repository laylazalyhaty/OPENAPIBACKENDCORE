package controller;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

@QuarkusTest
class MovieApiTest {

    @Test
    void list() {
        given()
                .when().get("/api/movies")
                .then()
                .statusCode(200)
                .body("id", hasItems(-1))
                .body("code", hasItems("SUPERMAN"))
                .body("title", hasItems("The Amazing Spiderman"))
                .body("description", hasItems("The Amazing Spider-Man is a 2012 American superhero film based on the Marvel Comics character Spider-Man."))
                .statusCode(Response.Status.OK.getStatusCode());
    }


}