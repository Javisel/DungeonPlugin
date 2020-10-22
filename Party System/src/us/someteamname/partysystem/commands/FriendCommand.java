package us.someteamname.partysystem.commands;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import us.someteamname.partysystem.Friend;
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
    Friend api = new Friend();
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
    if (length == 2) {
      if (args[0].equalsIgnoreCase("add"))
        api.perform(FriendAction.BEFRIEND, p, args[1]); 
      if (args[0].equalsIgnoreCase("remove"))
        api.perform(FriendAction.UNFRIEND, p, args[1]); 
      return true;
    } 
    Manager view = PartyUp.getMenuView(p);
    view.setPlayerFetch(p);
    (new FriendMenu(view)).open();
    return true;
  }
}


