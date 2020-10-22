package us.someteamname.partysystem.gui.party;

import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import us.someteamname.partysystem.PartyUp;
import us.someteamname.partysystem.gui.Manager;
import us.someteamname.partysystem.gui.Menu;

public class PartyFinderOptions extends Menu {
  public PartyFinderOptions(Manager manager) {
    super(manager);
  }
  
  String color(String message) {
    return ChatColor.translateAlternateColorCodes('&', message);
  }
  
  public String getMenuName() {
    return "";
  }
  
  public int getSlots() {
    return 9;
  }
  
  public void handleMenu(InventoryClickEvent e) {
    Player p = (Player)e.getWhoClicked();
    Material mat = e.getCurrentItem().getType();
    if (mat == Material.DARK_OAK_BUTTON) {
      Manager view = PartyUp.getMenuView(p);
      view.setPlayerFetch(p);
      (new PartyFinder(view)).open();
      p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_CELEBRATE, 8.0F, 1.0F);
    } 
    if (mat == Material.DIRT) {
      p.closeInventory();
      this.api.joinParty(p, Bukkit.getPlayer(this.manager.getPlayerEdit()));
      p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_HURT, 8.0F, 1.0F);
    } 
  }
  
  public void setMenuItems() {
    ItemStack yes = new ItemStack(Material.DIRT, 1);
    ItemMeta yes_meta = yes.getItemMeta();
    yes_meta.setDisplayName(color("&a&oJoin this players party."));
    yes_meta.setLore(Arrays.asList(new String[] { color("&2&oClick to join their party.") }));
    yes.setItemMeta(yes_meta);
    ItemStack back = new ItemStack(Material.DARK_OAK_BUTTON, 1);
    ItemMeta back_meta = back.getItemMeta();
    back_meta.setDisplayName(ChatColor.DARK_RED + "Go back");
    back.setItemMeta(back_meta);
    this.inventory.setItem(3, yes);
    this.inventory.setItem(8, back);
    setFillerGlass();
  }
}
