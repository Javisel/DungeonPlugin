package us.someteamname.partysystem.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import us.someteamname.partysystem.FriendAPI;

public class Announcer extends BukkitRunnable {
Player p;

	public Announcer(Player player) {
		this.p = player;
	}
	
	  public void run() {
    FriendAPI api = new FriendAPI();
    if (api.getRequests(this.p).size() > 0) {
      this.p.sendMessage(ChatColor.translateAlternateColorCodes('&', api.prefix + "&e&oYou have &f&l" + api.getRequests(this.p).size() + " &e&onew friend requests!\nUse &7/friend&e&o to respond to them."));
      return;
    } 
    if (api.hasUpdate(this.p)) {
      api.updateUser(this.p);
      api.handleUpdate(this.p);
      return;
      }
   }
}