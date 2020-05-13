package aeren.logation.commands;

import aeren.logation.LogationMain;
import aeren.logation.Utils;
import aeren.logation.db.UserDao;
import aeren.logation.db.UserDaoImpl;
import aeren.logation.models.Logation;
import aeren.logation.models.User;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelCommand implements CommandExecutor {

  private UserDao dao = new UserDaoImpl();

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
      if (log == null) {
        player.sendMessage(ChatColor.RED + "There is no logation named " + ChatColor.DARK_PURPLE + args[0]);
        return true;
      }

      Utils.deleteLogation(user, log);
      dao.updateUser(user);

      player.sendMessage(ChatColor.DARK_PURPLE + "Logation deleted: " + ChatColor.GOLD + log.getLabel());

      return true;
    }

    return false;
  }
}
