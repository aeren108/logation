package aeren.logation;

import aeren.logation.db.Database;
import aeren.logation.models.Logation;
import aeren.logation.models.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.text.DecimalFormat;

public class EventListener implements Listener {

  private Database db;
  private DecimalFormat df = new DecimalFormat("0.00");

  public EventListener() {
    db = Database.getInstance();
  }

  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent event) {
    Player player = event.getEntity();
    User user = db.getUserByName(player.getDisplayName());

    if (user == null) {
      user = new User(player.getDisplayName(), "", "");
      db.createUser(user);

      LogationMain.users.remove(user.getName());
      LogationMain.users.put(user.getName(), user);
    }

    Location loc = player.getLocation();
    String deathPoint = df.format(loc.getX()) + " " + df.format(loc.getY()) + " " + df.format(loc.getZ());
    user.setDeaths(deathPoint + "/" + user.getDeaths());

    db.updateUser(user);
    player.sendMessage(ChatColor.AQUA + "Your last death location: " + ChatColor.GOLD + deathPoint);
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    User user = db.getUserByName(player.getDisplayName());

    if (user == null) {
      user = new User(player.getDisplayName(), "", "");

      db.createUser(user);
    }

    LogationMain.users.remove(user.getName());
    LogationMain.users.put(user.getName(), user);
  }

}
