package us.someteamname.motdtab.motd;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class motd implements Listener {
	 
	@EventHandler
	public void onPing(ServerListPingEvent event) {
		event.setMotd("§e§l       ◄§6§l▮§e§l► §6§lH2M REMAKE §e§l◄§6§l▮§e§l► §7§l- §8§l[§6§l1.16.3+!§8§l]\n§3§l       ✸ §b§lYour Next Adventure! §3§l✸");
	}
}