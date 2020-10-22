package us.someteamname.partysystem.gui.friend;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import us.someteamname.partysystem.PartyUp;
import us.someteamname.partysystem.gui.Manager;
import us.someteamname.partysystem.gui.Menu;
import us.someteamname.partysystem.gui.OnlinePlayers;
import us.someteamname.partysystem.util.enums.FriendAction;

public class FriendInvite extends Menu{
  public FriendInvite(Manager manager) {
    super(manager);
  }
  
  String color(String message) {
    return ChatColor.translateAlternateColorCodes('&', message);
  }
  
  public String getMenuName() {
    return color("&5&oAdd " + this.manager.getPlayerEdit() + " as a friend!");
  }
  
  public int getSlots() {
    return 9;
  }
  
  public void handleMenu(InventoryClickEvent e) {
    Player p = (Player)e.getWhoClicked();
    Material mat = e.getCurrentItem().getType();
    if (mat == Material.EMERALD) {
      this.api.perform(FriendAction.BEFRIEND, p, this.manager.getPlayerEdit());
      p.closeInventory();
      p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 8.0F, 1.0F);
      Player target = Bukkit.getPlayer(this.manager.getPlayerEdit());
      if (!this.api.isFriends(p, target)) {
        Manager view = PartyUp.getMenuView(target);
        view.setPlayerEdit(p.getName());
        (new FriendIncoming(view)).open();
      } 
    } 
    if (mat == Material.DARK_OAK_BUTTON) {
      Manager view = PartyUp.getMenuView(p);
      view.setPlayerFetch(p);
      (new OnlinePlayers(view)).open();
      p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_TRADE, 8.0F, 1.0F);
    } 
  }
  
  public void setMenuItems() {
    ItemStack add = makeItem(Material.EMERALD, color("&a&l&oAdd &r" + this.manager.getPlayerEdit() + " &aas a friend."), new String[] { "" });
    ItemStack back = makeItem(Material.DARK_OAK_BUTTON, color("&a&oGo back."), new String[] { "" });
    this.inventory.setItem(3, add);
    this.inventory.setItem(8, back);
    setFillerGlass();
  }
}
