package RESTAssuredClient.RESTAssuredClient;

public class AzureAuthorization {
    private String conversationId;
    private String token;
    private String expires_in;
    private String streamUrl;
    private String referenceGrammarId;

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public String getReferenceGrammarId() {
        return referenceGrammarId;
    }

    public void setReferenceGrammarId(String referenceGrammarId) {
        this.referenceGrammarId = referenceGrammarId;
    }

    public AzureAuthorization(String conversationId, String token, String expires_in, String streamUrl, String referenceGrammarId) {
        this.conversationId = conversationId;
        this.token = token;
        this.expires_in = expires_in;
        this.streamUrl = streamUrl;
        this.referenceGrammarId = referenceGrammarId;
    }

    public AzureAuthorization() {
    }
}
