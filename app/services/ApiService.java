package services;

import java.util.List;
import java.util.concurrent.CompletionStage;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.ImplementedBy;

@ImplementedBy(ApiManager.class)
public interface ApiService{
  public CompletionStage<JsonNode> getUsersFromApi(int limit, int offset);
}
