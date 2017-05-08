package model;

public class User{
    private String id;
    private String createdDate;
    private String lastModifiedDate;

    public void setId(String id){
      this.id = id;
    }

    public String getId(){
      return id;
    }

    public void setCreatedDate(String createdDate){
      this.createdDate = createdDate;
    }

    public String getCreatedDate(){
      return createdDate;
    }

    public void setLastModifiedDate(String lastModifiedDate){
      this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedDate(){
      return lastModifiedDate;
    }
}
