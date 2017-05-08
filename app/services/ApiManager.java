package services;

import services.TokenService;
import services.Constants;

import java.util.*;
import javax.inject.Inject;
import play.libs.ws.*;
import java.util.concurrent.*;
import com.fasterxml.jackson.databind.JsonNode;

public class ApiManager implements ApiService{

  @Inject private WSClient wsClient;

  private String accessToken;
  private boolean accessTokenAvailable = false;

  @Inject
  public ApiManager(TokenService tokenService){
    accessTokenAvailable = retrieveAccessToken(tokenService);
  }

  private boolean retrieveAccessToken(TokenService tokenService){
    try{
      accessToken = tokenService.getAccessToken().toCompletableFuture().get();
      return true;
    } catch(InterruptedException | CancellationException |
            ExecutionException e){
      return false;
    }
  }

  public CompletionStage<JsonNode> getUsersFromApi(int limit, int offset){
      StringBuilder builder = new StringBuilder();
      builder.append(Constants.API_BASE)
             .append(Constants.PARAM_LIMIT)
             .append(limit)
             .append(Constants.PARAM_OFFSET)
             .append(offset);

     return wsClient.url(builder.toString())
             .setHeader("Authorization", "Bearer " + accessToken)
             .get()
             .thenApply(WSResponse::asJson);

  }
  
}
