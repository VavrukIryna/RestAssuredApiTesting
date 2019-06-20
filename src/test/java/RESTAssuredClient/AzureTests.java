package RESTAssuredClient;

import RESTAssuredClient.RESTAssuredClient.*;
import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.bot.schema.models.Activity;
import com.sun.jmx.snmp.Timestamp;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.testng.Assert;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static RESTAssuredClient.RESTAssuredClient.ClientController.*;
import static RESTAssuredClient.RESTAssuredClient.CreateDummyObjectActivity.createActivityObj;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class AzureTests {
    private static final Logger LOG = Logger.getLogger(AzureTests.class);
    AzureAuthorization azureAuthorization;
    String simpleActivity = "{ \"type\": \"message\"," +
            "  \"from\": { \"id\": \"test_user_id\" } }," +
            "  \"text\": \"Hi\"," +
            "  \"inputHint\": \"expectingInput\"," +
            "  \"channelId\": \"test_channel\"," +
            "  \"conversation\": { \"id\": 3 }," +
            "  \"recipient\": { \"id\": \"test_user_02\" } }";

    private WebsocketClientEndpoint clientEndpoint = null;

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
    public void createConnectionViaWebSockets() throws IOException, InterruptedException, URISyntaxException {

        String log4jConfPath = "\\src\\test\\resources\\log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);

        //Given
        LOG.info("create connection to bot");
        String secret = "MLEqhmOXPXk.1uPoTuUlr34mMovP4pgV6jroqxo67TsDo6VMqYVlU4s";
        String UrlStartConversation = "https://directline.botframework.com/v3/directline/conversations";
        Response response = createConnectionForBot(secret, UrlStartConversation);
       // System.out.println("response is=> "+response.prettyPrint());


        //Then
        //Assert Response
        int actualResponseStatusCode = response.getStatusCode();
        String actualStatusLine = response.getStatusLine();
        int expectedResponseStatusCode = 201;
        String expectedResponseStatusLine = "HTTP/1.1 201 Created";
        assertEquals(actualResponseStatusCode, expectedResponseStatusCode);
        assertEquals(actualStatusLine, expectedResponseStatusLine);

        System.out.println("response body =>" + response.getBody().prettyPrint());
        String jsonAsString = response.getBody().asString();
        AzureAuthorization azureAuthorization = StringToObjAzureAuthorization(jsonAsString);

        System.out.println("conversationId=>" + azureAuthorization.getConversationId());

        //streamUrl is used to connect to Bot
        System.out.println("streamUrl =>"+azureAuthorization.getStreamUrl());





        //When
        //sendAction To Bot
        String message = "My first message is - Hello my darling BOT via websockets";
        Response responseClient = sendActionToBot(azureAuthorization.getConversationId(), secret, message);


        //Then check answer
        int expectedStatusCode = 200;
        int actualStatusCode = responseClient.getStatusCode();
        assertEquals(actualStatusCode, expectedStatusCode);
        String actualId = responseClient.getBody().asString();
        assertTrue(actualId.contains(azureAuthorization.getConversationId()));
        System.out.println("response body Client=>" + responseClient.getBody().prettyPrint());


        //When Ask for receiving action from bot

        System.out.println("__________________________RECEIVE ACTION FROM BOT VIA WEBSOCKETS_____________________");

        String newBotWebSocketUrl =azureAuthorization.getStreamUrl();
        System.out.println("newBotWebSocketUrl"+newBotWebSocketUrl);

        clientEndpoint = new WebsocketClientEndpoint(new URI(newBotWebSocketUrl));
        // add listener
        clientEndpoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
            public void handleMessage(String message) throws IOException {
                Activity activity = StringToObjActivity(message);
                System.out.println("SERVER MESSAGE IS:" + activity.text() + " =>user number is: ");
            }
        });

        // wait 10 seconds for messages from websocket

        String newBotWebSocketUrlREST ="http"+ azureAuthorization.getStreamUrl().substring(2);
        RestAssured.baseURI =  newBotWebSocketUrlREST;
        RequestSpecification httpRequestBot = RestAssured.given();
        httpRequestBot.header("Upgrade", "websocket");
        httpRequestBot.header("Connection", "upgrade");
        Response responseBot = httpRequestBot.request(Method.GET);
        System.out.println("response Client Body is =>" + responseBot.getBody().prettyPrint());
        System.out.println("response client Direct line is =>" + responseBot.getStatusLine());

        Thread.sleep(10000000);
      //  clientEndpoint.sendMessage("message");
       // Thread.sleep(10000000);


    /*    String newBotWebSocketUrl ="http"+ azureAuthorization.getStreamUrl().substring(2);

        //   String newBotWebSocketUrl =azureAuthorization.getStreamUrl().substring(33);

        RestAssured.baseURI = newBotWebSocketUrl;
        RequestSpecification httpRequestBot = RestAssured.given();
        httpRequestBot.header("Upgrade", "websocket");
        httpRequestBot.header("Connection", "Upgrade");
        Response responseBot = httpRequestBot.request(Method.GET);
        System.out.println("response Client Body is =>" + responseBot.getBody().prettyPrint());
        System.out.println("response client Direct line is =>" + responseBot.getStatusLine());*/






       /* System.out.println("new websocket URL=>"+ newBotWebSocketUrl);
     //   RestAssured.baseURI = newBotWebSocketUrl;
        clientEndpoint = new WebsocketClientEndpoint(new URI(newBotWebSocketUrl));
        // add listener
        clientEndpoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
            public void handleMessage(String message) throws IOException {
                Activity activity = StringToObjActivity(message);
                System.out.println("SERVER MESSAGE IS:" + activity.text() + " =>user number is: ");
              }
        });

        // wait 10 seconds for messages from websocket
        clientEndpoint.sendMessage("message");
        Thread.sleep(10000);*/
    }

    @Test
    public void createConnection() throws IOException, ParseException {

        String log4jConfPath = "C:\\Users\\Iryna_Vavruk\\JAVAPROJECTS\\RESTAssuredClient\\RestAssuredApiTesting\\src\\test\\resources\\log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);



        //Given
        String secret = "MLEqhmOXPXk.1uPoTuUlr34mMovP4pgV6jroqxo67TsDo6VMqYVlU4s";
        String UrlStartConversation = "https://directline.botframework.com/v3/directline/conversations";
        Response response = createConnectionForBot(secret, UrlStartConversation);

        //Then
        //Assert Response
        int actualResponseStatusCode = response.getStatusCode();
        String actualStatusLine = response.getStatusLine();
        int expectedResponseStatusCode = 201;
        String expectedResponseStatusLine = "HTTP/1.1 201 Created";
        assertEquals(actualResponseStatusCode, expectedResponseStatusCode);
        assertEquals(actualStatusLine, expectedResponseStatusLine);

        System.out.println("response body =>" + response.getBody().prettyPrint());
        String jsonAsString = response.getBody().asString();
        AzureAuthorization azureAuthorization = StringToObjAzureAuthorization(jsonAsString);

        System.out.println("conversationId=>" + azureAuthorization.getConversationId());

        //When
        //sendAction To Bot
        String message = "test";
        Response responseClient = sendActionToBot(azureAuthorization.getConversationId(), secret, message);


        //Then check answer
        int expectedStatusCode = 200;
        int actualStatusCode = responseClient.getStatusCode();
        assertEquals(actualStatusCode, expectedStatusCode);
        String actualId = responseClient.getBody().asString();
        assertTrue(actualId.contains(azureAuthorization.getConversationId()));

        //When Ask for receiving action from bot
        Response responseBot = receiveActionFromBot(azureAuthorization.getConversationId(),secret);
        System.out.println("response Client Body is =>" + responseBot.getBody().prettyPrint());
        System.out.println("response client Direct line is =>" + responseBot.getStatusLine());
        //then check answers

        String myActivitiesString =  responseBot.getBody().asString();
        System.out.println("String Activities=>\n"+myActivitiesString);
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JodaModule());

        ResponseParserActivities activities =  mapper.readValue(myActivitiesString, ResponseParserActivities.class);

        Activity activity = activities.getActivities().get(Integer.parseInt(activities.getWatermark()));
        String actualOutput = "Turn 1: You sent 'test'" +
                "\n";
        System.out.println(activity.text());
        assertEquals(actualOutput, activity.text());
        }
    }

