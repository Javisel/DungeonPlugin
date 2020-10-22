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

public class FriendsCommand extends BukkitCommand {
	public FriendsCommand() {
		super("friends");
		setDescription("Primary command for BuddyUp.");
		setAliases(Arrays.asList(new String[] { "friends" }));
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
      Manager view = PartyUp.getMenuView(p);
      view.setPlayerFetch(p);
      (new FriendList(view)).open();
      return true;
    } 
    sendMessage((CommandSender)p, "Try \"/buddy\" for help - Unknown sub-command.");
    return true;
  }
}
