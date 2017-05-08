package services;

import com.google.inject.ImplementedBy;
import java.util.concurrent.CompletionStage;

@ImplementedBy(TokenManager.class)
public interface TokenService{

   public CompletionStage<String> getAccessToken();
}
