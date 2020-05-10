package aeren.logation.models;

public class User {
  private String name;
  private String locations;
  private String deaths;

  public User(String name, String locations, String deaths) {
    this.name = name;
    this.locations = locations;
    this.deaths = deaths;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLocations() {
    return locations;
  }

  public void setLocations(String locations) {
    this.locations = locations;
  }

  public String getDeaths() {
    return deaths;
  }

  public void setDeaths(String deaths) {
    this.deaths = deaths;
  }
}
