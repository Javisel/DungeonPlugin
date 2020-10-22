package us.someteamname.partysystem.gui.friend;

import java.util.Arrays;

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
import us.someteamname.partysystem.util.enums.FriendAction;

public class FriendOptions extends Menu {
  public FriendOptions(Manager manager) {
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
      (new FriendList(view)).open();
      p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_CELEBRATE, 8.0F, 1.0F);
    } 
    if (mat == Material.BOOKSHELF) {
      Manager view = PartyUp.getMenuView(p);
      view.setPlayerEdit(this.manager.getPlayerEdit());
      (new FriendMailer(view)).open();
    } 
    if (mat == Material.DIRT) {
      p.closeInventory();
      this.api.perform(FriendAction.UNFRIEND, p, this.manager.getPlayerEdit());
      p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_HURT, 8.0F, 1.0F);
    } 
  }
  
  public void setMenuItems() {
    ItemStack yes = new ItemStack(Material.DIRT, 1);
    ItemMeta yes_meta = yes.getItemMeta();
    yes_meta.setDisplayName(color("&a&oRemove friend"));
    yes_meta.setLore(Arrays.asList(new String[] { color("&a&oClick to remove friendship.") }));
    yes.setItemMeta(yes_meta);
    ItemStack back = new ItemStack(Material.DARK_OAK_BUTTON, 1);
    ItemMeta back_meta = back.getItemMeta();
    back_meta.setDisplayName(ChatColor.DARK_RED + "Go back");
    back.setItemMeta(back_meta);
    ItemStack info = new ItemStack(Material.BOOKSHELF, 1);
    ItemMeta info_meta = info.getItemMeta();
    info_meta.setDisplayName(color("&e&oSend mail"));
    info_meta.setLore(Arrays.asList(new String[] { color("&e&oClick to send mail to your friend.") }));
    info.setItemMeta(info_meta);
    this.inventory.setItem(3, yes);
    this.inventory.setItem(5, info);
    this.inventory.setItem(8, back);
    setFillerGlass();
  }
}

