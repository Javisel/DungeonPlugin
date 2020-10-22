package us.someteamname.motdtab.TabEdit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PJ implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer(); 
			p.setPlayerListHeaderFooter("◄§6§l▮§e§l► §6§lH2M REMAKE §e§l◄§6§l▮§e§l►", "✸ §b§lYour Next Adventure! §3§l✸ \n§6§lStore: H2MREMAKE.COM/SHOP");
			p.getScoreboardTags();
	}
}
		
		