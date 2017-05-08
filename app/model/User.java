package model;

public class User{
  private final String id;
  private final String createdDate;
  private final String lastModifiedDate;

  public User(String id, String createdDate, String lastModifiedDate){
    this.id = id;
    this.createdDate = createdDate;
    this.lastModifiedDate = lastModifiedDate;
  }

  public String getId(){
    return id;
  }

  public String getCreatedDate(){
    return createdDate;
  }

  public String getLastModifiedDate(){
    return lastModifiedDate;
  }
}
