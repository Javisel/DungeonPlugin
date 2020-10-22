package us.someteamname.motdtab;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import us.someteamname.motdtab.TabEdit.PJ;
import us.someteamname.motdtab.motd.motd;

public class Main extends JavaPlugin {
	
	public static Main instance;
	
	public void onEnable() {
		instance = this;
		Bukkit.getPluginManager().registerEvents(new motd(), this);
		Bukkit.getPluginManager().registerEvents(new PJ(), this);
		
	}

	public static Main getInstance() {
		return instance;
	}
}
