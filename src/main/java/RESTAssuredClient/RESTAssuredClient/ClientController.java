package RESTAssuredClient.RESTAssuredClient;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.bot.schema.models.Activity;
import com.sun.xml.bind.v2.runtime.reflect.ListIterator;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.*;
import org.xml.sax.SAXException;


import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.xml.bind.JAXBException;
import java.io.IOException;

import java.io.StringReader;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;



import static RESTAssuredClient.RESTAssuredClient.CreateDummyObjectActivity.createActivityObj;


public class ClientController {

   public static ArrayList<Activity> activities = new ArrayList<>();
    public static Activity activity = new Activity();
    public static boolean checkEmployeeParameters(Employee employee) {
        boolean response = false;
        if (employee.getEmpNo().equals("E01")) {
            if (employee.getEmpName().toLowerCase().equals("ira")) {
                if (employee.getPosition().toLowerCase().equals("qa")) {
                    response = true;
                }
            }
        }
        return response;
    }

    public static Response createConnectionForBot (String secret, String conversationUrl){
        System.out.println("___________________START TESTING!!!________________________");
        RestAssured.baseURI = conversationUrl;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Authorization", "Bearer "+secret);
        io.restassured.response.Response response = httpRequest.request(Method.POST);
        return response;
    }


    public static Response sendActionToBot(String conversationId, String secret, String message){
        System.out.println("_____________________SEND ACTION TO BOT!!!___________________");
        String newClientUrl = "https://directline.botframework.com/v3/directline/conversations/" + conversationId + "/activities";
        System.out.println("newUrl=>" + newClientUrl);
        RestAssured.baseURI = newClientUrl;
        RequestSpecification httpRequestClient = RestAssured.given();
        httpRequestClient.header("Authorization", "Bearer "+secret);
        httpRequestClient.header("Content-type", "application/json");
        Activity activity = createActivityObj(message ,conversationId);
        httpRequestClient.body(activity);
     /*   System.out.println("Simple activity"+activity);*/
        Response responseClient = httpRequestClient.request(Method.POST);
        return responseClient;
        /*System.out.println("response Client Body is =>"+responseClient.getBody().prettyPrint());
        System.out.println("response client Direct line is =>"+responseClient.getStatusLine());*/

    }

    public static Response receiveActionFromBot(String conversationId, String secret){
        System.out.println("__________________________RECEIVE ACTION FROM BOT_____________________");
        String newBotUrl = "https://directline.botframework.com/v3/directline/conversations/" + conversationId + "/activities";
        RestAssured.baseURI = newBotUrl;
        RequestSpecification httpRequestBot = RestAssured.given();
        httpRequestBot.header("Authorization", "Bearer "+secret);
        Response responseBot = httpRequestBot.request(Method.GET);
     /*   System.out.println("response Client Body is =>" + responseBot.getBody().prettyPrint());
        System.out.println("response client Direct line is =>" + responseBot.getStatusLine());*/
        return responseBot;
    }

    public static AzureAuthorization StringToObjAzureAuthorization(String msg) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        AzureAuthorization azureAuthorization = mapper.readValue(msg, AzureAuthorization.class);
        return azureAuthorization;
    }

    public static Activity StringToObjActivity(String msg) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Activity activity = mapper.readValue(msg, Activity.class);
        return activity;
    }

    public static Activity StringToObjActivities(Response response)  {

/*      JSONArray jsonArray= new JSONArray(msg);
        ArrayList<Activity> list = new ArrayList<>();

        Product p = new Product();
        p.setId( jsonArray.getJSONObject(i).getInt("id") );
        p.setName( jsonArray.getJSONObject(i).getString("name") );
        list.add(p)*/


     /*   JsonArray jsonArray;
        String replyString = response.asString().readEntity(String.class);

        JsonReader jsonReader = Json.createReader(new StringReader(response.readEntity(String.class)));
            jsonArray = jsonReader.readArray();

        ListIterator l = (ListIterator) jsonArray.listIterator();
        while ( l.hasNext() ) {
            JsonObject j = (JsonObject) l.next();
            JsonObject ciAttr = j.getJsonObject("ciAttributes");
        }




        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        activity = mapper.readValue(msg, Activity.class);

      //  Activity activity = mapper.readValue(msg, Activity.class);
        //todo add parsing site

        System.out.println(activities.get(0));

       // Activity[] activities = new Activity [3];

        Activity activity = new Activity();
        p.setId( jsonArray.getJSONObject(i).getInt("id") );
        p.setName( jsonArray.getJSONObject(i).getString("name") );
        list.add(p)
*/
        return  activity;
    }


   /* static final String URL_EMPLOYEES = "http://localhost:8080/employees";
    static final String URL_CREATE_EMPLOYEE = "http://localhost:8080/employee";
    static final String URL_UPDATE_EMPLOYEE = "http://localhost:8080/employee";
    static final String URL_EMPLOYEE_PREFIX = "http://localhost:8080/employee";

    public static void getAllEmployees(){
        // HttpHeaders
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        // Request to return JSON format
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("my_other_key", "my_other_value");

        // HttpEntity<String>: To get result as String.
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        // RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Send request with GET method, and Headers.
        ResponseEntity<String> response = restTemplate.exchange(URL_EMPLOYEES,
                HttpMethod.GET, entity, String.class);

        String result = response.getBody();

        System.out.println(result);

    }

    public static void putEmployee(){

        String empNo = "E01";

        Employee updateInfo = new Employee(empNo, "Tom", "Clerk");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();

        // Data attached to the request.
        HttpEntity<Employee> requestBody = new HttpEntity<>(updateInfo, headers);

        // Send request with PUT method.
        restTemplate.put(URL_UPDATE_EMPLOYEE, requestBody, new Object[] {});

        String resourceUrl = URL_EMPLOYEE_PREFIX + "/" + empNo;

        Employee e = restTemplate.getForObject(resourceUrl, Employee.class);

        if (e != null) {
            System.out.println("(Client side) Employee after update: ");
            System.out.println("Employee: " + e.getEmpNo() + " - " + e.getEmpName());
        }
    }


    public static void deleteEmployee(){
        RestTemplate restTemplate = new RestTemplate();

        // empNo="E01"
        String resourceUrl = "http://localhost:8080/employee/E01";

        // Send request with DELETE method.
        restTemplate.delete(resourceUrl);

        // Get
        Employee e = restTemplate.getForObject(resourceUrl, Employee.class);

        if (e != null) {
            System.out.println("(Client side) Employee after delete: ");
            System.out.println("Employee: " + e.getEmpNo() + " - " + e.getEmpName());
        } else {
            System.out.println("Employee not found!");
        }

    }
    public static void postEmployee(){
        Employee newEmployee = new Employee("E11", "New", "Bew");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();

        // Data attached to the request.
        HttpEntity<Employee> requestBody = new HttpEntity<>(newEmployee, headers);

        // Send request with POST method.
        ResponseEntity<Employee> result
                = restTemplate.postForEntity(URL_CREATE_EMPLOYEE, requestBody, Employee.class);

        System.out.println("Status code:" + result.getStatusCode());

        // Code = 200.
        if (result.getStatusCode() == HttpStatus.OK) {
            Employee e = result.getBody();
            System.out.println("(Client Side) Employee Created: "+ e.getEmpNo());
        }

    }*/
}
