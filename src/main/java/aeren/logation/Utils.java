package aeren.logation;

import aeren.logation.models.Logation;

import java.util.ArrayList;
import java.util.List;

public class Utils {

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

}
