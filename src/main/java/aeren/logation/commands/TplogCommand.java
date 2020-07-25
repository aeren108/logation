package aeren.logation.commands;

import aeren.logation.LogationMain;
import aeren.logation.Utils;
import aeren.logation.models.Logation;
import aeren.logation.models.User;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TplogCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player) sender;
      User user = LogationMain.USERS.get(player.getDisplayName());

      if (user == null) {
        player.sendMessage(ChatColor.RED + "Something went wrong. Try reconnecting to server");
        return false;
      } else if (args.length != 1) {
        player.sendMessage(ChatColor.RED + "Wrong usage");
        return false;
      }

      Logation logation = Utils.getLogationByLabel(user, args[0]);

      if (logation == null) {
        player.sendMessage(ChatColor.RED + "Logation named " +  ChatColor.DARK_PURPLE + args[0] + ChatColor.RED + ", couldn't be found");
        return true;
      }

      player.teleport(Utils.getLocationFromLogation(player.getWorld(), logation));
    }

    return true;
  }

}
