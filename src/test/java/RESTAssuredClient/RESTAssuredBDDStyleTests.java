package RESTAssuredClient;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class RESTAssuredBDDStyleTests {
    @Test
    public void GETUserTestSuccess() {
        given()
                .contentType(ContentType.JSON)
                .pathParam("username", "user1")
                .when()
                .get("https://petstore.swagger.io/v2/user/{username}")
                .then()
                .statusCode(200)
                .body("email", equalTo("ap"))
                .body("firstName", equalTo("awp"))
                .body("id", equalTo(11))
                .body("lastName", equalTo("www"))
                .body("password", equalTo("we"))
                .body("phone", equalTo("qw"))
                .body("userStatus", equalTo(123))
                .body("username", equalTo("user1"));
    }

    @Test
    public void getUserWrongId(){
        given()
                .contentType(ContentType.JSON)
                .pathParam("username","wrongName")
                .when()
                .get("https://petstore.swagger.io/v2/user/{username}")
                .then()
                .statusCode(400)
                .body("message",equalTo("User not found"))
                .body("type",equalTo("error"));
    }






}
