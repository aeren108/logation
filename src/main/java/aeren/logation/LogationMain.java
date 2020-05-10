package aeren.logation;

import aeren.logation.commands.DeathlogCommand;
import aeren.logation.commands.LogCommand;
import aeren.logation.commands.LogationsCommand;
import aeren.logation.db.Database;
import aeren.logation.models.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class LogationMain extends JavaPlugin {

  public static Map<String, User> users = new HashMap();
  public static String rootFolder = "";

  private Database db;

  @Override
  public void onEnable() {
    super.onEnable();

    this.getCommand("logations").setExecutor(new LogationsCommand());
    this.getCommand("log").setExecutor(new LogCommand());
    this.getCommand("deathlog").setExecutor(new DeathlogCommand());
    this.getServer().getPluginManager().registerEvents(new EventListener(), this);

    rootFolder = this.getDataFolder().getAbsolutePath();
    Bukkit.getLogger().info("INFFFF" + rootFolder);
    db = Database.getInstance();

    for (Player player : Bukkit.getOnlinePlayers()) {
      User user = db.getUserByName(player.getDisplayName());

      if (user == null) {
        user = new User(player.getDisplayName(), "", "");
        db.createUser(user);
      }

      users.put(user.getName(), user);
    }
  }

  @Override
  public void onDisable() {
    super.onDisable();

    db.close();
  }
}
