package RESTAssuredClient;

import RESTAssuredClient.RESTAssuredClient.DataProviderSource;
import RESTAssuredClient.RESTAssuredClient.User;
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

public class MyWireMockTests {
    WireMockServer wireMockServer;


    public final String URLForMocking = "http://localhost:8080";
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
        RestAssured.baseURI = URLForMocking;
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
        RestAssured.baseURI = URLForMocking;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/pet/1");


        verify(getRequestedFor(urlEqualTo("/pet/1")));
        //  Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals("application/json", response.getHeader("Content-Type"));
        String responseBody ;
        if (response.getBody() != null) {
            responseBody = response.getBody().asString();
            System.out.println("Response Body is =>  " + responseBody);
        }

        Assert.assertEquals("\"testing-library\": \"WireMock\"", response.getBody().asString());

    }


    @Test
    public void configureMockBody() {
        wireMockServer.stubFor(get(urlPathEqualTo("/pet/1"))
                //  .withHeader("Accept", matching("text/.*"))
                .willReturn(aResponse()
                        .withStatus(503)
                        .withHeader("Content-Type", "text/html")
                        .withBody("!!! Service Unavailable !!!")));


        RestAssured.baseURI = URLForMocking;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/pet/1");

        String responseBody ;
        if (response.getBody() != null) {
            responseBody = response.getBody().asString();
            System.out.println("Response Body is =>  " + responseBody);
        }


        verify(getRequestedFor(urlEqualTo("/pet/1")));
        Assert.assertEquals(response.getStatusCode(), 503);
        Assert.assertEquals(response.getHeader("Content-Type"), "text/html");
        Assert.assertEquals(response.getBody().asString(), "!!! Service Unavailable !!!");

    }

    @Test
    public void bodyMatchingPOST() {
       stubFor(post(urlEqualTo("/pet"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(containing("\"petId\": \"WireMock\""))
                .withRequestBody(containing("\"name\": \"Tom Akehurst\""))
                .withRequestBody(containing("\"status\": \"wiremock.org\""))
                .willReturn(aResponse()
                        .withStatus(200).withHeader("Content-Type", "application/json")));

        RestAssured.baseURI = URLForMocking;
        RequestSpecification httpRequest = RestAssured.given();


        JSONObject requestParam = new JSONObject();
        requestParam.put("petId", "WireMock");
        requestParam.put("name", "Tom Akehurst");
        requestParam.put("status", "wiremock.org");

        // Add the Json to the body of the request
        httpRequest.body(requestParam.toJSONString());
        httpRequest.header("Content-Type", "application/json");
        System.out.println(requestParam.toJSONString());
        Response response = httpRequest.request(Method.POST, "/pet");

        System.out.println(response.getStatusCode());
     /*   verify(postRequestedFor(urlEqualTo("/pet/10"))
                .withHeader("Content-Type", equalTo("application/json")));*/
        /*  Assert.assertEquals(200, response.getStatusCode());
         */

    }

    @Test(dataProvider = "createUser", dataProviderClass = DataProviderSource.class)
    public void POSTRequestForUserPetstoreWithDataProvider() {
      //  RestAssured.baseURI = "https://petstore.swagger.io/v2";
        User user = new User(5, "username", "firstname", "lastname", "email", "password", "phone", 123);
        stubFor(post(urlEqualTo("/user"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(containing("\"petId\": \"WireMock\""))
                .withRequestBody(containing("\"name\": \"Tom Akehurst\""))
                .withRequestBody(containing("\"status\": \"wiremock.org\""))
                .willReturn(aResponse()
                        .withStatus(200).withHeader("Content-Type", "application/json")));




        RestAssured.baseURI = URLForMocking;
        RequestSpecification httpRequest = RestAssured.given();
      //  User user = new User(5, "username", "firstname", "lastname", "email", "password", "phone", 123);
        httpRequest.body(user);
        httpRequest.header("Content-type", "application/json");
        Response response = httpRequest.request(Method.POST, "/user");

        int responseStatusCode = response.getStatusCode();
        Assert.assertEquals(responseStatusCode, 200, "User was created");
        System.out.println("content-type=>" + response.getContentType());
        Assert.assertEquals(response.getContentType(), "application/json", "content type is application/json");
        System.out.println("headers =>" + response.getHeaders());
        Assert.assertEquals(response.getHeader("Server"), "Jetty(9.2.9.v20150224)", "server is jetty");
        Assert.assertEquals(response.getHeader("Content-Type"), "application/json", "content type is correct");
        System.out.println("status line =>" + response.getStatusLine());
        System.out.println("time=>" + response.getTime());
    }
}
