package aeren.logation;

import aeren.logation.commands.*;
import aeren.logation.db.UserDao;
import aeren.logation.db.UserDaoImpl;
import aeren.logation.models.User;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogationMain extends JavaPlugin {

  public static final Map<String, User> USERS = new HashMap();
  private UserDao dao = new UserDaoImpl();

  @Override
  public void onEnable() {
    super.onEnable();

    loadUsers();

    this.getCommand("log").setExecutor(new LogCommand());
    this.getCommand("log").setTabCompleter(this);
    this.getCommand("deathlog").setExecutor(new DeathlogCommand());
    this.getCommand("find").setExecutor(new FindCommand());
    this.getCommand("del").setExecutor(new DelCommand());
    this.getCommand("tplog").setExecutor(new TplogCommand());
    this.getServer().getPluginManager().registerEvents(new EventListener(), this);
  }

  private void loadUsers() {
    ((UserDaoImpl) dao).createUserTable();

    for (Player player : Bukkit.getOnlinePlayers()) {
      User user = dao.getUserByName(player.getDisplayName());

      if (user == null) {
        user = new User(player.getDisplayName(), "", "");
        dao.createUser(user);
      }

      USERS.put(user.getName(), user);
    }
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
    List<String> tabs = new ArrayList();
    String command = cmd.getName();

    if (command.equalsIgnoreCase("log")) {
      if (args.length == 1) {
        tabs.add("list");
      }
    }

    return tabs;
  }

  @Override
  public void onDisable() {
    super.onDisable();
  }
}
