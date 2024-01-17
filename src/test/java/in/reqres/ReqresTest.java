package in.reqres;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ReqresTest {

    //1
    @Test
    public void createUserWithNameAndJobTest() {

        String userData = "{\n" +
                "    \"name\": \"Gregory\",\n" +
                "    \"job\": \"dvornik\"\n" +
                "}";

        given()
                .body(userData)
                .contentType(ContentType.JSON)
                .log().uri()
                .log().body()
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("name", is("Gregory"))
                .body("job", is("dvornik"))
                .body("id", notNullValue())
                .body("createdAt", notNullValue());
    }

    //2
    @Test
    public void createUserWithoutJobTest() {

        String userData = "{\n" +
                "    \"name\": \"Gregory\",\n" +
                "}";

        given()
                .body(userData)
                .contentType(ContentType.JSON)
                .log().uri()
                .log().body()
                .post("https://reqres.in/api/users")
                .then()
                .log().all()
                .statusCode(400);
    }

    //3
    @Test
    public void deleteUserTest() {

        int userId = 23;

        given()
                .log().uri()
                .when()
                .delete("https://reqres.in/api/users/" + userId)
                .then()
                .statusCode(204);
    }

    //4
    @Test
    public void registerUser() {
        String userData = "{\n" +
                "    \"username\": \"Emma\",\n" +
                "    \"email\": \"emma.wong@reqres.in\",\n" +
                "    \"password\": \"pistol\"\n" +
                "}";

        given()
                .body(userData)
                .contentType(ContentType.JSON)
                .log().uri()
                .log().body()
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().all()
                .statusCode(400)
                .body("error", is("Note: Only defined users succeed registration"));
    }

    //5
    @Test
    public void verifyUsersOnPage() {

        given()
                .log().uri()
                .when()
                .get("https://reqres.in/api/users?page=1")
                .then()
                .log().all()
                .statusCode(200)
                .body("data.size()", equalTo(6))
                .body("data.email", hasItems(
                        "george.bluth@reqres.in",
                        "janet.weaver@reqres.in",
                        "eve.holt@reqres.in",
                        "emma.wong@reqres.in",
                        "charles.morris@reqres.in",
                        "tracey.ramos@reqres.in"
                ));
    }


}
