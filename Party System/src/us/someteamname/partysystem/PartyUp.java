package us.someteamname.partysystem;

import org.bukkit.plugin.java.JavaPlugin;

import us.someteamname.partysystem.commands.FriendCommand;
import us.someteamname.partysystem.commands.FriendsCommand;
import us.someteamname.partysystem.commands.PartyCommand;
import us.someteamname.partysystem.gui.Manager;
import us.someteamname.partysystem.listener.Players;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class PartyUp extends JavaPlugin{
  public static PartyUp instance;

  private static final Logger log = Logger.getLogger("Minecraft");
  
  private static final HashMap<Player, Manager> GuiMap = new HashMap<>();
  
  public void onEnable() {
    log.info(String.format("[PartySystem] - Time to make some friends! Haha... ha....", new Object[] { getDescription().getName() }));
    setInstance(this);
    registerCommand((BukkitCommand)new FriendCommand());
    registerCommand((BukkitCommand)new FriendsCommand());
    registerCommand((BukkitCommand)new PartyCommand()); 
    getServer().getPluginManager().registerEvents((Listener)new Players(), (Plugin)get());
    for (Player all : Bukkit.getOnlinePlayers())
      all.closeInventory(); 
  }
  
  public void onDisable() {
    log.warning(String.format("[PartySystem] - Oh im so sad to see you go! Bye fren...", new Object[] { getDescription().getName() }));
  }
  
  public static PartyUp get() {
    return instance;
  }
  
  public static void setInstance(PartyUp instance) {
    PartyUp.instance = instance;
  }
  
  public static Manager getMenuView(Player p) {
    if (!GuiMap.containsKey(p)) {
      Manager playerMenuUtility = new Manager(p);
      GuiMap.put(p, playerMenuUtility);
      return playerMenuUtility;
    } 
    return GuiMap.get(p);
  }
  
  public void registerCommand(BukkitCommand command) {
    try {
      Field commandMapField = getServer().getClass().getDeclaredField("commandMap");
      commandMapField.setAccessible(true);
      CommandMap commandMap = (CommandMap)commandMapField.get(getServer());
      commandMap.register(command.getLabel(), (Command)command);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}

