package RESTAssuredClient.RESTAssuredClient;

import com.microsoft.bot.schema.models.*;
import com.microsoft.bot.schema.models.ChannelAccount;

import java.util.ArrayList;
import java.util.List;


public class CreateDummyObjectActivity {
    public static String createSimpleActivityStr() {


        String simpleActivity = "{ \"type\": \"message\"," +
                "  \"text\": \"Hi\"," +
                "  \"inputHint\": \"expectingInput\"," +
                "  \"channelId\": \"test_channel\"," +
                "  \"conversation\": { \"id\": 3 }," +
                "  \"recipient\": { \"id\": \"test_user_02\" } }";
        return simpleActivity;
    }

    public static ArrayList<String> createExpectedResponseList(String conversationId) {
        ArrayList<String> serverExpectedResponceList = new ArrayList<String>();

        switch (conversationId) {
            case "1": {
                serverExpectedResponceList.add("Do you know your GroupID? (1) Yes or (2) No");
                serverExpectedResponceList.add("I am sorry, we can proceed only if you are a member of some group");
                serverExpectedResponceList.add("Type something to start from very beginning");
                break;
            }
            case "2": {
                serverExpectedResponceList.add("Do you know your GroupID? (1) Yes or (2) No");
                serverExpectedResponceList.add("Enter your GroupID");
                serverExpectedResponceList.add("Confirm that 'ABCD1234' is the name of your group (1) Yes or (2) No");
                serverExpectedResponceList.add("Great! Now let me know your full name");
                serverExpectedResponceList.add("Nice to meet you Yuriy Znak");
                serverExpectedResponceList.add("Yuriy I also need to know your e-mail");
                serverExpectedResponceList.add("Type something to start from very beginning");
                break;
            }
            case "3": {
                serverExpectedResponceList.add("Oops. Something went wrong!");
                break;
            }

        }
        return serverExpectedResponceList;
    }


    public static Activity createActivityObj(String text, String conversationId) {
        Activity activity = new Activity();
        activity.withChannelId("TESTChannelId");
        activity.withId("TESTId");


        ChannelAccount channelAccount = new ChannelAccount();
        channelAccount.withId("TESTChannelAccountId");
        channelAccount.withName("TESTChannelAccountName");
        channelAccount.withRole(RoleTypes.USER);
        activity.withFrom(channelAccount);
        activity.withRecipient(channelAccount);

        ConversationAccount conversationAccount = new ConversationAccount();
        conversationAccount.withConversationType("TESTMessage");
        conversationAccount.withId("TESTConversationAccountId");


        activity.withConversation(conversationAccount);
        conversationAccount.withIsGroup(true);
        conversationAccount.withIsGroup(true);
        conversationAccount.withName("TESTConversationAccountName");
        conversationAccount.withRole(RoleTypes.BOT);
        conversationAccount.withId(conversationId);
        activity.withConversation(conversationAccount);

        activity.withName("TESTActivityName");
        activity.withType(ActivityTypes.MESSAGE);

        activity.withServiceUrl("TESTURLString");
        activity.withTextFormat(TextFormatTypes.MARKDOWN);
        activity.withAttachmentLayout(AttachmentLayoutTypes.CAROUSEL);

        List<ChannelAccount> channelAccountList = new ArrayList<>();
        channelAccountList.add(channelAccount);
        //there may be added some another channel accounts
        activity.withMembersAdded(channelAccountList);
        activity.withMembersRemoved(channelAccountList);

        MessageReaction messageReaction = new MessageReaction();
        messageReaction.withType(MessageReactionTypes.PLUS_ONE);
        List<MessageReaction> messageReactionList = new ArrayList<>();
        messageReactionList.add(messageReaction);
        //there may be added some another  MessageReaction
        activity.withReactionsAdded(messageReactionList);
        activity.withReactionsRemoved(messageReactionList);

        activity.withTopicName("TESTTopicName");
        activity.withHistoryDisclosed(true);
        activity.withLocale("TESTLocale");
        activity.withText(text);
        activity.withSpeak("TESTSpeak");
        activity.withInputHint(InputHints.ACCEPTING_INPUT);
        activity.withSummary("TESTSummary");

        SuggestedActions suggestedActions = new SuggestedActions();
        CardAction cardAction = new CardAction();
        cardAction.withDisplayText("TESTDisplayText");
        cardAction.withImage("TESTImage");
        cardAction.withText("TESTCardActionText");
        cardAction.withTitle("TESTCardActionTitle");
        cardAction.withType(ActionTypes.CALL);

        List<CardAction> cardActionList = new ArrayList<>();
        cardActionList.add(cardAction);
        //there may be added some another cardAction
        suggestedActions.withActions(cardActionList);
        activity.withSuggestedActions(suggestedActions);
        return activity;
    }
/*
    public static boolean checkIfIncludeListElements(List<ChannelAccount> membersAddedList, ChannelAccount expectedChannelAccount) {
        for (ChannelAccount item : membersAddedList) {
            if (item.id().equals(expectedChannelAccount.id()) && (item.name().equals(expectedChannelAccount.name())) && item.role().equals(expectedChannelAccount.role()) &&
                    (item.properties().equals(expectedChannelAccount.properties())))
                return true;
            else
                return false;
        }
        return false;
    }*/
}
