package RESTAssuredClient;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

public class MyTestsWithWireMock {
    WireMockServer wireMockServer;

    @BeforeTest
    public void setUp() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        configureFor("localhost", 8080);
    }

    @AfterTest
    public void tearDown() {
        //зупинка мок сервера
        wireMockServer.stop();
    }

    //використання моку в АПІ
    @Test
    public void setupSimpleStub() {
        //ініціалізація сервера мок сервера
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
    }

    @Test
    public void setupStubWithHeader() {
        //ініціалізація сервера мок сервера

        wireMockServer.stubFor(get(urlPathEqualTo("/pet/1")).willReturn(aResponse().withStatus(200).withBody("\"testing-library\": \"WireMock\"").withHeader("Content-Type", "application/json")));
        RestAssured.baseURI = "http://localhost:8080";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/pet/1");


        verify(getRequestedFor(urlEqualTo("/pet/1")));
        //  Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals("application/json", response.getHeader("Content-Type"));
        String responseBody = response.getBody().asString();
        if (response.getBody() != null) {
            responseBody = response.getBody().asString();
            System.out.println("Response Body is =>  " + responseBody);
        }

        Assert.assertEquals("\"testing-library\": \"WireMock\"", response.getBody().asString());

    }


    @Test
    public void configureMockBody(){
        wireMockServer.stubFor(get(urlPathEqualTo("/pet/1"))
              //  .withHeader("Accept", matching("text/.*"))
                .willReturn(aResponse()
                        .withStatus(503)
                        .withHeader("Content-Type", "text/html")
                        .withBody("!!! Service Unavailable !!!")));


        RestAssured.baseURI = "http://localhost:8080";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/pet/1");

        String responseBody = response.getBody().asString();
        if (response.getBody() != null) {
            responseBody = response.getBody().asString();
            System.out.println("Response Body is =>  " + responseBody);
        }


        verify(getRequestedFor(urlEqualTo("/pet/1")));
        Assert.assertEquals(response.getStatusCode(),503);
        Assert.assertEquals(response.getHeader("Content-Type"),"text/html");
        Assert.assertEquals( response.getBody().asString(),"!!! Service Unavailable !!!");

    }

    @Test
    public void bodyMatchingPOST(){
        stubFor(post(urlEqualTo("/pet/1"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(containing("\"testing-library\": \"WireMock\""))
                .withRequestBody(containing("\"creator\": \"Tom Akehurst\""))
                .withRequestBody(containing("\"website\": \"wiremock.org\""))
                .willReturn(aResponse()
                        .withStatus(200)));
    RestAssured.baseURI = "http://localhost:8080";
    RequestSpecification httpRequest = RestAssured.given();


        JSONObject requestParam = new JSONObject();
        requestParam.put("testing-library", "WireMock");
        requestParam.put("creator", "Tom Akehurst");
        requestParam.put("website", "wiremock.org");

    // Add the Json to the body of the request
        httpRequest.body(requestParam.toJSONString());
        System.out.println( httpRequest.body(requestParam.toJSONString()));
    Response response = httpRequest.post("/pet/1");

        verify(postRequestedFor(urlEqualTo("/pet/1"))
                .withHeader("Content-Type", equalTo("application/json")));
        Assert.assertEquals(200, response.getStatusCode());


    }
}
