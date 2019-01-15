package RESTAssuredClient;

import RESTAssuredClient.RESTAssuredClient.DataProviderSource;
import RESTAssuredClient.RESTAssuredClient.Employee;
import RESTAssuredClient.RESTAssuredClient.ClientController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class MyTests {
    static final String URL_EMPLOYEES = "http://localhost:8080/employees";
    static final String URL_CREATE_EMPLOYEE = "http://localhost:8080/employee";
    static final String URL_UPDATE_EMPLOYEE = "http://localhost:8080/employee";
    static final String URL_EMPLOYEE_PREFIX = "http://localhost:8080/employee";

    @Test(dataProvider = "getAllEmployeeNumber",dataProviderClass=DataProviderSource.class)
    public void CheckCorrectEmployeeDetails(String empNo) {
        // Specify the base URL to the RESTful web service
        RestAssured.baseURI = URL_CREATE_EMPLOYEE;

        // Get the RequestSpecification of the request that you want to sent
        // to the server. The server is specified by the BaseURI that we have
        // specified in the above step.
        RequestSpecification httpRequest = RestAssured.given();

        // Make a request to the server by specifying the method Type and the method URL.
        // This will return the Response from the server. Store the response in a variable.
        //Response response = httpRequest.request(Method.GET, "/E01");
        Response response = httpRequest.get("/"+empNo);




        // Now let us print the body of the message to see what response
        // we have recieved from the server

        //check response
        String responseBody = response.getBody().asString();
        System.out.println("Response Body is =>  " + responseBody);

//****1*****StatusCode
        int responseCode = response.getStatusCode();
        System.out.println("StatusCode is => " + responseCode);
        Assert.assertEquals(responseCode, 200, "Status code is correct");


//****2*****StatusLine
        String responseStatusLine = response.getStatusLine();
        System.out.println("responseStatusLine is => " + responseStatusLine);
        Assert.assertEquals(responseStatusLine, "HTTP/1.1 200 ", "Correct status code (= 200) returned at status line");


//****3***** HEADER

        // Reader header of a give name. In this line we will get
        // Header named Content-Type
        String contentType = response.header("Content-Type");
        System.out.println("Content-Type value: " + contentType);
        Assert.assertEquals(contentType /* actual value */, "application/json;charset=UTF-8" /* expected value */);


        // Reader header of a give name. In this line we will get
        // Header named Server
        String serverType = response.header("Server");
        System.out.println("Server value: " + serverType);
        //  Assert.assertEquals(serverType /* actual value */, "nginx/1.12.1" /* expected value */);


        // Reader header of a give name. In this line we will get
        // Header named Content-Encoding
        String acceptLanguage = response.header("Content-Encoding");
        System.out.println("Content-Encoding: " + acceptLanguage);
        // Assert.assertEquals(contentEncoding /* actual value */, "gzip" /* expected value */);

    }

    @Test
    public void CheckIncorrectEmployeeDetails() {
        RestAssured.baseURI = URL_CREATE_EMPLOYEE;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "/4335345345345");

        int responseStatusCode = response.getStatusCode();
        System.out.println("Response Status Code is: " + responseStatusCode);
        //tyt maje bytu cod 404 zamist 200 bo vin maje bytu incorrect!!!!!
        Assert.assertEquals(responseStatusCode, 200/*404 or 400 */, "Status Code is not equal 200");

        //check response
        String responseBody = response.getBody().asString();
        System.out.println("Response Body is =>  " + responseBody);


        String responseContentType = response.getContentType();
        System.out.println("responseContentType is => " + responseContentType);
        String responseHeader = response.getHeader("Content-Type");
        System.out.println("responseHeader content type is => " + responseHeader);

    }

    @Test(dataProvider = "getAllEmployeeName",dataProviderClass=DataProviderSource.class)
    public void getEmployeeBodyAndCheck(String empNo, String empName) {
        RestAssured.baseURI = URL_CREATE_EMPLOYEE;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "/"+empNo);
        // Retrieve the body of the Response
        ResponseBody body = response.getBody();


        // To check for sub string presence get the Response body as a String.
        // Do a String.contains
        String bodyAsString = body.asString();
        // By using the ResponseBody.asString() method, we can convert the  body
        // into the string representation.
        System.out.println("Response Body is: " + bodyAsString);
        Assert.assertTrue(bodyAsString.toLowerCase().contains(empName.toLowerCase()), "Response body contains Ira");
    }

    @Test(dataProvider = "getAllEmployeeName",dataProviderClass=DataProviderSource.class)
    public void checkTheEmployeeNameByJsonPathEvaluator(String empNo, String actualempName) {
        RestAssured.baseURI = URL_CREATE_EMPLOYEE;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "/"+empNo);
        // First get the JsonPath object instance from the Response interface
        JsonPath jsonPathEvaluator = response.jsonPath();

        // Then simply query the JsonPath object to get a String value of the node
        // specified by JsonPath: City (Note: You should not put $. in the Java code)
        String empName = jsonPathEvaluator.get("empName");

        // Let us print the city variable to see what we got
        System.out.println("empName received from Response " + empName);

        // Validate the response
        Assert.assertEquals(empName.toLowerCase(), actualempName.toLowerCase(), "Correct employee name received in the Response");
    }

    @Test
    public void checkTheBodyByGSONSerialization() {
        RestAssured.baseURI = URL_CREATE_EMPLOYEE;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "/E01");
        ResponseBody responseBody = response.getBody();
        String jsonAsString = responseBody.asString();
        Employee employee01 = new Gson().fromJson(jsonAsString, Employee.class);

        System.out.println(employee01.toString());

        Assert.assertTrue(ClientController.checkEmployeeParameters(employee01), "Employee Parameters Are Good with GSON");
    }

    @Test
    public void checkTheBodyByJacksonSerialization() {
        RestAssured.baseURI = URL_CREATE_EMPLOYEE;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "/E01");
        ResponseBody responseBody = response.getBody();
        String jsonAsString = responseBody.asString();

        ObjectMapper m = new ObjectMapper();
        try {
            Employee employee01 = m.readValue(jsonAsString, Employee.class);
            System.out.println(employee01.toString());
            Assert.assertTrue(ClientController.checkEmployeeParameters(employee01), "Employee Parameters Are Good with Jackson");
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
         How to convert List of objects
         String json = "[{\"deleteFlag\":0,\"codeId\":\"1\",\"codeName\":\"IT\",\"sort\":\"1\",\"id\":\"60\"},{\"deleteFlag\":0,\"codeId\":\"2\",\"codeName\":\"Accounting\",\"sort\":\"2\",\"id\":\"61\"},{\"deleteFlag\":0,\"codeId\":\"3\",\"codeName\":\"Support\",\"sort\":\"3\",\"id\":\"62\"}]";
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<MstCode> mstCodes = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, MstCode.class));
            System.out.println(mstCodes.size());
            System.out.println(mstCodes.get(0).getCodeName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    }

    @Test
    public void POSTRequestForUserSwagger() {
        RestAssured.baseURI = "http://petstore.swagger.io/v2";
        RequestSpecification httpRequest = RestAssured.given();

// JSONObject is a class that represents a Simple JSON.
// We can add Key - Value pairs using the put method
        JSONObject requestParam = new JSONObject();
        requestParam.put("id", 1);
        requestParam.put("username", "string");
        requestParam.put("firstName", "string");
        requestParam.put("lastName", "string");
        requestParam.put("email", "string");
        requestParam.put("password", "string");
        requestParam.put("phone", "string");
        requestParam.put("userStatus", 1);

        // Add a header stating the Request body is a JSON
        httpRequest.header("Content-Type", "application/json");

// Add the Json to the body of the request
        httpRequest.body(requestParam.toJSONString());

// Post the request and check the response
        Response response = httpRequest.post("/user");

//****1*****StatusCode
        int responseCode = response.getStatusCode();
        System.out.println("StatusCode is => " + responseCode);
        Assert.assertEquals(responseCode, 201, "Status code is correct");
        System.out.println("Response body: " + response.body().asString());
       // String successCode = response.jsonPath().get("SuccessCode");
       // Assert.assertEquals("Correct Success code was returned", successCode, "OPERATION_SUCCESS");
    }

    @Test
    public void PostRequestForEmployee(){
        RestAssured.baseURI = URL_CREATE_EMPLOYEE;
        RequestSpecification httpRequest = RestAssured.given();
        JSONObject requestParam = new JSONObject();
        requestParam.put("empNo","E33");
        requestParam.put("empName","E33");
        requestParam.put("position","E33");

        httpRequest.header("Content-Type", "application/json");
        httpRequest.body(requestParam.toJSONString());
        Response response = httpRequest.request(Method.POST);
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, "201");
        System.out.println("The status code recieved: " + statusCode);
        System.out.println("Response body: " + response.body().asString());
    }


}
