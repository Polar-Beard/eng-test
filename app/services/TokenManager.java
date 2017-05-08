package services;

import javax.inject.Inject;

import play.mvc.*;
import play.libs.ws.*;
import java.util.concurrent.CompletionStage;
import com.fasterxml.jackson.databind.JsonNode;

public class TokenManager implements TokenService{

    @Inject WSClient wsClient;

    private CompletionStage<String> accessToken;

    public CompletionStage<String> getAccessToken(){
        if(accessToken == null){
          initiateAccessToken();
        }
        return accessToken;
    }

    private void initiateAccessToken(){
      this.accessToken = wsClient.url(Constants.ACCESS_TOKEN_URL)
        .setAuth(Constants.CLIENT_ID, Constants.CLIENT_SECRET, WSAuthScheme.BASIC)
        .get()
        .thenApply(WSResponse::asJson)
        .thenApply(jsonNode -> jsonNode.findValue("access_token"))
        .thenApply(JsonNode::asText);
    }

}
