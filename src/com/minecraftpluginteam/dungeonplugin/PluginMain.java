package com.minecraftpluginteam.dungeonplugin;

import com.minecraftpluginteam.dungeonplugin.common.EventHandler;
import com.onarandombox.MultiverseCore.MultiverseCore;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginMain extends JavaPlugin {

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("Hi, the mod isn't here :(");
    }

    @Override
    public void onEnable() {
       getServer().getConsoleSender().sendMessage("Hi, the mod is here!");
        getServer().getPluginManager().registerEvents(new EventHandler(), this);
       MultiverseCore multiverseCore = (MultiverseCore) getServer().getPluginManager().getPlugin("Multiverse-Core");




    }
}
