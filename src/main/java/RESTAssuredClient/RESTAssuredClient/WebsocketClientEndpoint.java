package RESTAssuredClient.RESTAssuredClient;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;


@ClientEndpoint
public class WebsocketClientEndpoint {

   // String clientId = null; //id client for identification
    public String answer;
    public ArrayList<String> answerList = new ArrayList<>();
    Session userSession = null;
    private MessageHandler messageHandler;


    public WebsocketClientEndpoint(URI endpointURI) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Callback hook for Connection open events.
     *
     * @param userSession the userSession which is opened.
     */
    //session is opened between client and server
    @OnOpen
    public void onOpen(Session userSession) throws InterruptedException {

        System.out.println("opening websocket");
        this.userSession = userSession;
        Thread.sleep(100000);
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param userSession the userSession which is getting closed.
     * @param reason      the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("class WebsocketClientEndpoint method onClose=>");
        System.out.println("closing websocket");
        this.userSession = null;
    }

    /**
     * Callback hook for Message Events. This method will be invoked when a client send a message.
     *
     * @param message The text message
     */
    //this method can be triggered when client receive message from server (RESPONSE)????
   /* @OnMessage
    public String onMessage(String message) {
        System.out.println("class WebsocketClientEndpoint method onMessage=>");

        // sendMessage("User select 1");
        return message;

     //  if (this.messageHandler != null) {
      //      this.messageHandler.handleMessage(message);
      //  }
    }
*/
    /**
     * Callback hook for Message Events. This method will be invoked when a client send a message.
     *
     * @param message The text message
     */
    @OnMessage
    public void onMessage(String message) throws IOException, InterruptedException {
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
            this.answer=message;
            this.answerList.add(message);
        }
    }

    /**
     * register message handler
     *
     * @param msgHandler
     */


   public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }

    /**
     * Send a message.
     *
     * @param message
     */
    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }

    /**
     * Message handler.
     *
     * @author Jiji_Sasidharan
     */
    public static interface MessageHandler {
        public void handleMessage(String message) throws IOException, InterruptedException;
    }
}
   /*

    @OnMessage
    public String onMessage(String message) {
        this.userSession.getAsyncRemote().sendObject(message, new SendHandler() {
            @Override
            public void onResult(SendResult result) {
                System.out.println(userSession.getId() + ":"
                        + result.isOK());
                System.out.println("RESULT"+result);
                System.out.println("MESSAGE"+message);
               // return message;
            }
        });
        return message;
    }
*/
    /**/
/*
    public String getAnswer() {
        return answer;
    }
    */
    /**
     * register message handler
     *
     * @param msgHandler
     */
   /* public void addMessageHandler(MessageHandler msgHandler) {
        System.out.println("class WebsocketClientEndpoint method addMessageHandler=>");
        this.messageHandler = msgHandler;
    }


    public void sendMessage(String message) throws IOException {
        System.out.println("class WebsocketClientEndpoint method sendMessage=>"+ message);
        this.userSession.getAsyncRemote().sendText(message);
      //  this.userSession.getBasicRemote().sendText(message);
    }




    public static interface MessageHandler {

        public void handleMessage(String message);
    }

*///}