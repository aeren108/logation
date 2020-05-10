package aeren.logation.commands;

import aeren.logation.LogationMain;
import aeren.logation.db.Database;
import aeren.logation.models.User;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class LogCommand implements CommandExecutor {

  private Database db = Database.getInstance();
  private DecimalFormat df = new DecimalFormat("0.00");

  @Override
  public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player) sender;
      User user = LogationMain.users.get(player.getDisplayName());

      if (user == null) {
        player.sendMessage(ChatColor.RED + "Something went wrong. Try reconnecting to server");
        return false;
      }

      if (args.length != 1) {
        player.sendMessage(ChatColor.RED + "Wrong usage");
        return false;
      }

      String label = args[0];
      Location loc = player.getLocation();
      String logation = df.format(loc.getX()) + " " + df.format(loc.getY()) + " " + df.format(loc.getZ()) + "?" + label + "/";

      user.setLocations(logation + user.getLocations());
      db.updateUser(user);

      player.sendMessage(ChatColor.AQUA + "(" + df.format(loc.getX()) + " " + df.format(loc.getY()) + " " + df.format(loc.getZ()) + ") saved as " + ChatColor.GOLD + label);

      return true;
    }

    return false;
  }

}
