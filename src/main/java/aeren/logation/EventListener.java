package aeren.logation;

import aeren.logation.db.Database;
import aeren.logation.models.User;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.text.DecimalFormat;

public class EventListener implements Listener {

  private Database db = Database.getInstance();
  private DecimalFormat df = new DecimalFormat("0.00");

  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent event) {
    Player player = event.getEntity();
    User user = LogationMain.USERS.get(player.getDisplayName());

    if (user == null) {
      user = new User(player.getDisplayName(), "", "");
      db.createUser(user);

      LogationMain.USERS.remove(user.getName());
      LogationMain.USERS.put(user.getName(), user);
    }

    Location loc = player.getLocation();
    String deathPoint = df.format(loc.getX()) + " " + df.format(loc.getY()) + " " + df.format(loc.getZ());
    Utils.addDeathpoint(user, deathPoint);

    db.updateUser(user);
    player.sendMessage(ChatColor.LIGHT_PURPLE + "Last Deathpoint: " + ChatColor.GOLD + deathPoint);
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    User user = db.getUserByName(player.getDisplayName());

    if (user == null) {
      user = new User(player.getDisplayName(), "", "");

      db.createUser(user);
    }

    LogationMain.USERS.remove(user.getName());
    LogationMain.USERS.put(user.getName(), user);
  }

}
