package aeren.logation;

import aeren.logation.models.Logation;
import aeren.logation.models.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {

  public static final int MAX_DEATHPOINTS = 5;

  public static List<Logation> getLogations(String raw) {
    //Structure of raw logations = "166.89 65.75 -895?[label1]/4856 -67 745?[label2]/..."
    List<Logation> logations = new ArrayList();
    String[] wholeLogations = raw.split("/");

    for (String data : wholeLogations) {
      String[] logData = data.split("\\?");
      if (logData.length != 2)
        continue;

      String location = logData[0];
      String label = logData[1];

      logations.add(new Logation(location, label));
    }

    return logations;
  }

  public static List<String> getDeaths(String raw) {
    //Structure of raw deaths = "-187.0 28.0 548.45/789.71 62.0 487.4/..."
    List<String> deaths = new ArrayList();

    for (String death : raw.split("/"))
      deaths.add(death);

    return deaths;
  }

  public static Logation getLogationByLabel(User user, String label) {
    Logation log = null;

    for (Logation l : getLogations(user.getLocations())) {
      if (l.getLabel().equalsIgnoreCase(label))
        log = l;
    }

    return log;
  }

  public static void deleteLogation(User user, Logation l) {
    List<Logation> logs = getLogations(user.getLocations());
    StringBuilder raw = new StringBuilder();

    for (Logation log : logs) {
      if (log.getLabel().equals(l.getLabel()))
        continue;

      raw.append(convertLogationToRaw(log));
    }

    user.setLocations(raw.toString());
  }

  private static String convertLogationToRaw(Logation l) {
    return l.getLocation() + "?" + l.getLabel() + "/";
  }

  public static void addDeathpoint(User user, String deathPoint) {
    List<String> deaths = getDeaths(user.getDeaths());
    StringBuilder raw = new StringBuilder();

    if (deaths.size() == MAX_DEATHPOINTS)
      deaths.remove(deaths.size() - 1);

    deaths.add(0, deathPoint);

    for (String dp : deaths) {
      raw.append(dp + "/");
    }

    user.setDeaths(raw.toString());
  }

}
