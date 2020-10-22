package us.someteamname.start;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	public void onEnable() {
		System.out.print("DisableJoinMessge Enabled!");
		System.out.print("Enjoy!");
		getServer().getPluginManager().registerEvents(this, (Plugin)this);
	}

	public void onDisable() {
		System.out.print("DisableJoinMessge Disabled!");
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage(null);
	}
	
	@EventHandler
	public void OnQuit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
	}
}
