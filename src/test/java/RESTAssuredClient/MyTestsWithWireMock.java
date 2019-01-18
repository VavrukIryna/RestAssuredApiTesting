package RESTAssuredClient;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

public class MyTestsWithWireMock {
    //використання моку в АПІ
    @Test
    public void setupSimpleStub() {
        //ініціалізація сервера мок сервера
        WireMockServer wireMockServer = new WireMockServer();
        wireMockServer.start();

        configureFor("localhost", 8080);
        wireMockServer.stubFor(get(urlPathEqualTo("/pet/1")).willReturn(aResponse().withBody("TEST MOCK BODY")));

//дії як в звичайного користувача
        //   RestAssured.baseURI = "https://petstore.swagger.io/v2";
        RestAssured.baseURI = "http://localhost:8080";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/pet/1");
        String responseBody = response.getBody().asString();
        if (response.getBody() != null) {
            responseBody = response.getBody().asString();
            System.out.println("Response Body is =>  " + responseBody);
        }
        Assert.assertTrue(responseBody.contains("TEST MOCK BODY"), "test mock body is there");
//зупинка мок сервера
        wireMockServer.stop();
    }

    @Test
    public void setupStubWithHeader() {
        //ініціалізація сервера мок сервера
        WireMockServer wireMockServer = new WireMockServer();
        wireMockServer.start();

        configureFor("localhost", 8080);
        wireMockServer.stubFor(get(urlPathEqualTo("/pet/1")).willReturn(aResponse().withStatus(200).withBody("\"testing-library\": \"WireMock\"").withHeader("Content-Type", "application/json")));

        RestAssured.baseURI = "http://localhost:8080";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/pet/1");
        verify(getRequestedFor(urlEqualTo("/pet/1")));
      //  Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals("application/json", response.getHeader("Content-Type"));
        System.out.println(response.getBody().toString());
       // Assert.assertEquals("\"testing-library\": \"WireMock\"", response.getBody().toString());
        wireMockServer.stop();
}
}
