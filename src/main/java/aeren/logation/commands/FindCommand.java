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

public class FindCommand implements CommandExecutor {

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

      Logation log = Utils.getLogationByLabel(user, args[0]);
      if (log != null)
        player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + log.getLabel() + ": " + ChatColor.RESET + "" + ChatColor.GOLD + log.getLocation());
      else
        player.sendMessage(ChatColor.RED + "Logation not found");

      return true;
    }

    return false;
  }

}
