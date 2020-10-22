package us.someteamname.partysystem.commands;

import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import us.someteamname.partysystem.FriendAPI;
import us.someteamname.partysystem.PartyUp;
import us.someteamname.partysystem.gui.Manager;
import us.someteamname.partysystem.gui.friend.FriendList;
import us.someteamname.partysystem.gui.friend.FriendMenu;
import us.someteamname.partysystem.util.enums.FriendAction;

public class FriendCommand extends BukkitCommand{
public FriendCommand() {
	super("friend");
    setDescription("Primary command for Making new friends.");
    setAliases(Arrays.asList(new String[] { "f" }));
  }
  
  public void sendMessage(CommandSender player, String message) {
    player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
  }
  
  public boolean execute(CommandSender sender, String commandLabel, String[] args) {
    FriendAPI api = new FriendAPI();
    if (!(sender instanceof Player)) {
      sendMessage(sender, api.prefix + " This is a player only command.");
      return true;
    } 
    Player p = (Player)sender;
    int length = args.length;
    if (length == 0) {
      Manager manager = PartyUp.getMenuView(p);
      manager.setPlayerFetch(p);
      (new FriendMenu(manager)).open();
      return true;
    } 
    if (length == 1) {
      if (args[0].equalsIgnoreCase("list")) {
        Manager manager = PartyUp.getMenuView(p);
        manager.setPlayerFetch(p);
        (new FriendList(manager)).open();
      } 
      return true;
    }

    UUID targetid = Bukkit.getPlayer(args[1]).getUniqueId();

    if (length == 2) {
      if (args[0].equalsIgnoreCase("add"))
        api.doFriendAction(FriendAction.BEFRIEND, p, targetid);
      if (args[0].equalsIgnoreCase("remove"))
        api.doFriendAction(FriendAction.UNFRIEND, p, targetid);
      return true;
    } 
    Manager view = PartyUp.getMenuView(p);
    view.setPlayerFetch(p);
    (new FriendMenu(view)).open();
    return true;
  }
}


