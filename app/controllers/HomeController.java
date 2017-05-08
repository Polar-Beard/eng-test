package controllers;

import services.ApiService;
import model.User;
import views.html.*;

import javax.inject.Inject;
import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;

import play.mvc.*;

import com.fasterxml.jackson.databind.JsonNode;

public class HomeController extends Controller {
    @Inject private ApiService apiService;

    private static final int USERS_PER_PAGE = 5;

    private int totalUsers = 100;

    public CompletionStage<Result> index(int pageNumber)
            throws InterruptedException, ExecutionException{

        if(pageNumber * USERS_PER_PAGE > totalUsers || pageNumber < 1){
          return CompletableFuture.supplyAsync(() -> notFound(notfound.render()));
        }

        int offset = calculateOffset(pageNumber);

        CompletionStage<JsonNode> fullResult = apiService.getUsersFromApi(USERS_PER_PAGE, offset);

        CompletionStage<Integer> totalResults = fullResult.thenApply(json -> json.findValue("totalResults").intValue());

        totalUsers = totalResults.toCompletableFuture().get();

        String base = "?pageNumber=";
        String previous = pageNumber == 1? null: base + (pageNumber - 1);
        String next = (pageNumber + 1) * USERS_PER_PAGE > totalUsers? null: base + (pageNumber + 1);

        return fullResult.thenApply(json -> json.findValue("results"))
                  .thenApply(json -> getUsersFromJson(json))
                  .thenApply(listOfUsers -> ok(index.render(previous, next, listOfUsers)));
    }

    private List<User> getUsersFromJson(JsonNode jsonNode){
      List<User> userArray = new ArrayList<>();

      if(jsonNode.isArray()){
        for(JsonNode node: jsonNode){
            userArray.add(processUserJson(node));
        }
      }

      return userArray;
    }

    private User processUserJson(JsonNode jsonNode){
      User user = new User(jsonNode.findValue("id").textValue(),
                           jsonNode.findValue("createdTs").textValue(),
                           jsonNode.findValue("lastModifiedTs").textValue());
      return user;
    }

    private int calculateOffset(int pageNumber){
      return (pageNumber - 1) * USERS_PER_PAGE;
    }
}
