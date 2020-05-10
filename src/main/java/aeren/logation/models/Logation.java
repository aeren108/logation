package aeren.logation.models;

public class Logation {
  private String location;
  private String label;

  public Logation(String location, String label) {
    this.location = location;
    this.label = label;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }
}
