package us.someteamname.partysystem.gui.friend;

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

public class FriendOutgoing extends Menu {
	public FriendOutgoing(Manager manager){
		super(manager);
	}

	String color(String message) {
    return ChatColor.translateAlternateColorCodes('&', message);
  }
  
  public String getMenuName() {
    return color("&5&oCancel your request to " + this.manager.getPlayerUUID() + " ?");
  }
  
  public int getSlots() {
    return 9;
  }
  
  public void handleMenu(InventoryClickEvent e) {
    Player p = (Player)e.getWhoClicked();
    Material mat = e.getCurrentItem().getType();
    if (mat == Material.EMERALD) {
      this.api.doFriendAction(FriendAction.CANCEL_REQUEST, p, this.manager.getPlayerUUID());
      p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 8.0F, 1.0F);
      p.closeInventory();
    } 
    if (mat == Material.DARK_OAK_BUTTON) {
      Manager view = PartyUp.getMenuView(p);
      view.setPlayerFetch(p);
      (new OnlinePlayers(view)).open();
    } 
  }
  
  public void setMenuItems() {
    ItemStack add = makeItem(Material.EMERALD, color("&a&l&oYes"), new String[] { "" });
    ItemStack back = makeItem(Material.DARK_OAK_BUTTON, color("&4&oNo, &a&oGo back."), new String[] { "" });
    this.inventory.setItem(3, add);
    this.inventory.setItem(8, back);
    setFillerGlass();
  }
}

