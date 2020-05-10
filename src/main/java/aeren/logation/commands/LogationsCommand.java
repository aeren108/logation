package aeren.logation.commands;

import aeren.logation.LogationMain;
import aeren.logation.Utils;
import aeren.logation.db.Database;
import aeren.logation.models.Logation;
import aeren.logation.models.User;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LogationsCommand implements CommandExecutor {

  private Database db = Database.getInstance();

  @Override
  public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player) sender;
      User user = LogationMain.users.get(player.getDisplayName());

      if (user == null) {
        player.sendMessage(ChatColor.RED + "Something went wrong. Try reconnection to server");
        return false;
      }

      if (args.length != 0) {
        player.sendMessage(ChatColor.RED + "Wrong usage");
        return false;
      }

      for (Logation l : Utils.getLogations(user.getLocations())) {
        player.sendMessage(ChatColor.AQUA + l.getLabel() + ": " + ChatColor.GOLD + l.getLocation());
      }

    }

    return false;
  }

}
