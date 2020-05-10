package aeren.logation.commands;

import aeren.logation.LogationMain;
import aeren.logation.Utils;
import aeren.logation.models.User;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class DeathlogCommand implements CommandExecutor {

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

      List<String> deaths = Utils.getDeaths(user.getDeaths());
      for (int i = deaths.size() - 1; i >= 0; i--) {
        int deathOrder = i + 1;

        if (deathOrder != 1)
          player.sendMessage(ChatColor.AQUA + "" + i + ". Last Deathpoint: " + deaths.get(i));
        else
          player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Last Deathpoint" + ChatColor.GOLD + "" + deaths.get(i));
      }

    }

    return false;
  }

}
