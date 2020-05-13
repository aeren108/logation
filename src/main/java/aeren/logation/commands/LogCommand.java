package aeren.logation.commands;

import aeren.logation.LogationMain;
import aeren.logation.Utils;
import aeren.logation.db.UserDao;
import aeren.logation.db.UserDaoImpl;
import aeren.logation.models.Logation;
import aeren.logation.models.User;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class LogCommand implements CommandExecutor {

  private UserDao dao = new UserDaoImpl();
  private DecimalFormat df = new DecimalFormat("0.00");

  @Override
  public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player) sender;
      User user = LogationMain.USERS.get(player.getDisplayName());

      if (user == null) {
        player.sendMessage(ChatColor.RED + "Something went wrong. Try reconnecting to server");
        return false;
      }

      if (args.length != 1) {
        player.sendMessage(ChatColor.RED + "Wrong usage");
        return false;
      }

      String task = args[0];

      if (task.equalsIgnoreCase("list")) {
        for (Logation l : Utils.getLogations(user.getLocations())) {
          player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + l.getLabel() + ": " +  ChatColor.RESET + "" + ChatColor.GOLD + l.getLocation());
        }
      } else {
        Location loc = player.getLocation();

        String label = args[0];
        String logation = df.format(loc.getX()) + " " + df.format(loc.getY()) + " " + df.format(loc.getZ()) + "?" + label + "/";

        if (Utils.getLogationByLabel(user, label) != null) {
          player.sendMessage(ChatColor.RED + "A logation named " + ChatColor.DARK_PURPLE + label + ChatColor.RED + " already exists");
          return true;
        }

        user.setLocations(logation + user.getLocations());
        dao.updateUser(user);

        player.sendMessage(ChatColor.AQUA + "(" + df.format(loc.getX()) + " " + df.format(loc.getY()) + " " + df.format(loc.getZ()) + ") saved as " + ChatColor.GOLD + label);
      }

      return true;
    }

    return false;
  }

}
