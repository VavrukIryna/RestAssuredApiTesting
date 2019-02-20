package RESTAssuredClient;

import RESTAssuredClient.RESTAssuredClient.AzureAuthorization;
import RESTAssuredClient.RESTAssuredClient.ClientController;
import RESTAssuredClient.RESTAssuredClient.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;
import org.testng.Assert;

import java.io.IOException;
import java.net.URISyntaxException;


public class AzureTests {
AzureAuthorization azureAuthorization;
    String simpleActivity = "{ \"type\": \"message\"," +
            "  \"from\": { \"id\": \"test_user_id\" } },"+
            "  \"text\": \"Hi\"," +
            "  \"inputHint\": \"expectingInput\"," +
            "  \"channelId\": \"test_channel\"," +
            "  \"conversation\": { \"id\": 3 }," +
            "  \"recipient\": { \"id\": \"test_user_02\" } }";
    /*   @Test
       public void firstTest() throws URISyntaxException {

           final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint();
           String sendedMessage = "My new message";
           clientEndPoint.sendMessage(sendedMessage);
           System.out.println("send message=>"+ sendedMessage);
           System.out.println("received message:"+clientEndPoint.onMessage(sendedMessage));
           String answer = clientEndPoint.onMessage(sendedMessage);
           Assert.assertEquals("My new message" ,answer);

       }

       */
    @Test
    public void createConnection() {

        System.out.println("___________________START TESTING!!!________________________");
        RestAssured.baseURI = "https://directline.botframework.com/v3/directline/conversations";
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Authorization", "Bearer MLEqhmOXPXk.1uPoTuUlr34mMovP4pgV6jroqxo67TsDo6VMqYVlU4s");
     /*httpRequest.body(user);
     httpRequest.header("Content-type", "application/json");*/
        Response response = httpRequest.request(Method.POST);

        int responseStatusCode = response.getStatusCode();
        System.out.println("response Status Code: " + response.getStatusCode());
        System.out.println("status line =>" + response.getStatusLine());
        System.out.println("response body=>" + response.getBody().prettyPrint());
        /* System.out.println("time=>" + response.getTime());*/

        String jsonAsString = response.getBody().asString();
        System.out.println("jsonAsString=>"+jsonAsString);

        ObjectMapper m = new ObjectMapper();
        try {
            AzureAuthorization azureAuthorization = m.readValue(jsonAsString, AzureAuthorization.class);
            System.out.println(azureAuthorization.toString());
            System.out.println("conversationId=>" + azureAuthorization.getConversationId());

            //sendAction To Bot

            System.out.println("_____________________SEND ACTION TO BOT!!!___________________");
            String newClientUrl = "https://directline.botframework.com/v3/directline/conversations/" + azureAuthorization.getConversationId() + "/activities";
            System.out.println("newUrl=>" + newClientUrl);
            RestAssured.baseURI = newClientUrl;
            RequestSpecification httpRequestClient = RestAssured.given();
            httpRequestClient.header("Authorization", "Bearer MLEqhmOXPXk.1uPoTuUlr34mMovP4pgV6jroqxo67TsDo6VMqYVlU4s");
            httpRequestClient.header("Content-type", "application/json");
            httpRequestClient.body(simpleActivity);
            System.out.println("Simple activity"+simpleActivity);
            Response responseClient = httpRequestClient.request(Method.POST);
            System.out.println("response Client Body is =>"+responseClient.getBody().prettyPrint());
            System.out.println("response client Direct line is =>"+responseClient.getStatusLine());


            //receive Actions from Bot
            System.out.println("__________________________RECEIVE ACTION FROM BOT_____________________");
            String newBotUrl = "https://directline.botframework.com/v3/directline/conversations/" + azureAuthorization.getConversationId() + "/activities";
            RestAssured.baseURI =newBotUrl ;
            RequestSpecification httpRequestBot = RestAssured.given();
            httpRequestBot.header("Authorization", "Bearer MLEqhmOXPXk.1uPoTuUlr34mMovP4pgV6jroqxo67TsDo6VMqYVlU4s");
            Response responseBot = httpRequestBot.request(Method.GET);
            System.out.println("response Client Body is =>"+responseBot.getBody().prettyPrint());
            System.out.println("response client Direct line is =>"+responseBot.getStatusLine());


            System.out.println("_____________________SEND ACTION TO BOT!!!___________________");
            newClientUrl = "https://directline.botframework.com/v3/directline/conversations/" + azureAuthorization.getConversationId() + "/activities";
            System.out.println("newUrl=>" + newClientUrl);
            RestAssured.baseURI = newClientUrl;
            RequestSpecification httpRequestClient1 = RestAssured.given();
            httpRequestClient1.header("Authorization", "Bearer MLEqhmOXPXk.1uPoTuUlr34mMovP4pgV6jroqxo67TsDo6VMqYVlU4s");
            httpRequestClient1.header("Content-type", "application/json");
            String simpleActivity2 = "{ \"type\": \"message\"," +
                    "  \"from\": { \"id\": \"test_user_id\" } },"+
                    "  \"text\": \"HELLO NEW MESSAGE\"," +
                    "  \"inputHint\": \"expectingInput\"," +
                    "  \"channelId\": \"test_channel\"," +
                    "  \"conversation\": { \"id\": 3 }," +
                    "  \"recipient\": { \"id\": \"test_user_02\" } }";
            httpRequestClient1.body(simpleActivity2);
            System.out.println("Simple activity"+simpleActivity2);
            Response responseClient1 = httpRequestClient1.request(Method.POST);
            System.out.println("response Client Body is =>"+responseClient1.getBody().prettyPrint());
            System.out.println("response client Direct line is =>"+responseClient1.getStatusLine());


            //receive Actions from Bot
            System.out.println("__________________________RECEIVE ACTION FROM BOT_____________________");
            newBotUrl = "https://directline.botframework.com/v3/directline/conversations/" + azureAuthorization.getConversationId() + "/activities";
            RestAssured.baseURI =newBotUrl ;
            RequestSpecification httpRequestBot1 = RestAssured.given();
            httpRequestBot1.header("Authorization", "Bearer MLEqhmOXPXk.1uPoTuUlr34mMovP4pgV6jroqxo67TsDo6VMqYVlU4s");
            Response responseBot1 = httpRequestBot1.request(Method.GET);
            System.out.println("response Client Body is =>"+responseBot1.getBody().prettyPrint());
            System.out.println("response client Direct line is =>"+responseBot1.getStatusLine());






        } catch (IOException e) {
            e.printStackTrace();
        }





    }


   /* @Test
    public void sendActionToBot(){
        //send message to bot
        String newUrl = "https://directline.botframework.com/v3/directline/conversations/" + azureAuthorization.getConversationId() + "/activities";
        System.out.println("newUrl=>" + newUrl);
        RestAssured.baseURI = newUrl;
        RequestSpecification httpRequestClient = RestAssured.given();
        httpRequestClient.header("Content-type", "application/json");
        httpRequestClient.body(simpleActivity);
        Response responseClient = httpRequestClient.request(Method.POST);
        System.out.println("response Client Body is =>"+responseClient.getBody().prettyPrint());
        System.out.println("response client Direct line is =>"+responseClient.getStatusLine());
    }*/

}
