package us.someteamname.partysystem.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import us.someteamname.partysystem.FriendAPI;

public abstract class Menu implements InventoryHolder {
  protected Manager manager;
  
  protected Inventory inventory;
  
  private String color(String msg) {
    return ChatColor.translateAlternateColorCodes('&', msg);
  }
  
  protected FriendAPI api = new FriendAPI();
  
  protected ItemStack FILLER_GLASS = makeItem(Material.BLACK_STAINED_GLASS_PANE, " ", new String[0]);
  
  protected ItemStack FILLER_GLASS_LIGHT = makeItem(Material.RED_STAINED_GLASS_PANE, 
      color("&7&oEmpty space."), new String[0]);
  
  public Menu(Manager manager) {
    this.manager = manager;
  }
  
  public abstract String getMenuName();
  
  public abstract int getSlots();
  
  public abstract void handleMenu(InventoryClickEvent paramInventoryClickEvent);
  
  public abstract void setMenuItems();
  
  public void open() {
    this.inventory = Bukkit.createInventory(this, getSlots(), getMenuName());
    setMenuItems();
    this.manager.getPartyOwner().openInventory(this.inventory);
  }
  
  public Inventory getInventory() {
    return this.inventory;
  }
  
  public void setFillerGlass() {
    for (int i = 0; i < getSlots(); i++) {
      if (this.inventory.getItem(i) == null)
        this.inventory.setItem(i, this.FILLER_GLASS); 
    } 
  }
  
  public void sendHelpMenu(Player p) {
    ArrayList<String> help = new ArrayList<>();
    help.add(this.api.prefix + "- Command list&b&l|&o&m---------------------------");
    help.add("&b|&7)&r /friend add <playerName> - &7&oSend or accept a friend request.");
    help.add("&b|&7)&r /friend remove <playerName> - &7&oRemove a user from your friend list.");
    help.add("&b|&7)&r /friends - &7&oView your friendlist");
    help.add("&b|&7)&r /friend - &a&oGo back to the GUI.");
    help.add("&b&l&o&m-----------------------------------");
    for (String s : help)
      p.sendMessage(ChatColor.translateAlternateColorCodes('&', s)); 
  }
  
  public ItemStack makeItem(Material material, String displayName, String... lore) {
    ItemStack item = new ItemStack(material);
    ItemMeta itemMeta = item.getItemMeta();
    itemMeta.setDisplayName(color(displayName));
    itemMeta.setLore(Arrays.asList(lore));
    item.setItemMeta(itemMeta);
    return item;
  }
  
  public ItemStack makeItem(Material material, String displayName, List<String> lore) {
    ItemStack item = new ItemStack(material);
    ItemMeta itemMeta = item.getItemMeta();
    itemMeta.setDisplayName(color(displayName));
    itemMeta.setLore(lore);
    item.setItemMeta(itemMeta);
    return item;
  }
}