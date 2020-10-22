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
import us.someteamname.partysystem.gui.party.PartyMenu;

public class FriendMenu extends Menu {
	public FriendMenu(Manager manager) {
		super(manager);
	}

String color(String message) {
    return ChatColor.translateAlternateColorCodes('&', message);
  }
  
  public String getMenuName() {
    return color("|&m-----&r " + this.api.prefix + "&m------&r|");
  }
  
  public int getSlots() {
    return 54;
  }
  
  public void handleMenu(InventoryClickEvent e) {
    Player p = (Player)e.getWhoClicked();
    Material mat = e.getCurrentItem().getType();
    if (mat == Material.COMPASS) {
      Manager view = PartyUp.getMenuView(p);
      view.setPlayerFetch(p);
      (new OnlinePlayers(view)).open();
      p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_TRADE, 8.0F, 1.0F);
    } 
    if (mat == Material.GOLD_NUGGET) {
      Manager view = PartyUp.getMenuView(p);
      view.setPlayerFetch(p);
      (new FriendList(view)).open();
      p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_CELEBRATE, 8.0F, 1.0F);
    } 
    if (mat == Material.BOOK) {
      p.closeInventory();
      sendHelpMenu(p);
      p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 8.0F, 1.0F);
    } 
    if (mat == Material.ENCHANTED_BOOK) {
      Manager view = PartyUp.getMenuView(p);
      view.setPlayerFetch(p);
      (new FriendIncomingList(view)).open();
      p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_TRADE, 8.0F, 1.0F);
    } 
    if (mat == Material.SPECTRAL_ARROW) {
      Manager view = PartyUp.getMenuView(p);
      view.setPlayerFetch(p);
      (new FriendOutgoingList(view)).open();
      p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 8.0F, 1.0F);
    } 
    if (mat == Material.FIREWORK_STAR) {
      Manager view = PartyUp.getMenuView(p);
      view.setPlayerFetch(p);
      (new PartyMenu(view)).open();
      p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES, 8.0F, 1.0F);
    } 
  }
  
  public void setMenuItems() {
    ItemStack author = makeItem(Material.WRITABLE_BOOK, color(this.api.prefix + "&3&oAuthor"), new String[] { color("&6Team &0Awesome") });
    ItemStack yes = makeItem(Material.COMPASS, color("Add an online user as a friend"), new String[] { color("Click me to view the online user list") });
    ItemStack no = makeItem(Material.GOLD_NUGGET, color("Manage your friends list"), new String[] { color("Click me to view your friend list") });
    ItemStack party = makeItem(Material.FIREWORK_STAR, color("View the party GUI"), new String[] { color("Click me to change GUI's") });
    ItemStack list = makeItem(Material.BOOK, color("View the list of commands via text."), new String[] { color("Click me to view the full command list.") });
    ItemStack list2 = makeItem(Material.ENCHANTED_BOOK, color("&oFriend Requests [&b" + this.api.getRequests(this.manager.getPlayerFetch()).size() + "&r&o]"), new String[] { color("Click me to view your current requests.") });
    ItemStack sent = makeItem(Material.SPECTRAL_ARROW, color("&oSent Requests [&a" + this.api.getSentRequests(this.manager.getPlayerFetch()).size() + "&r&o]"), new String[] { color("Click me to manage sent friend requests.") });
    this.inventory.setItem(13, list);
    this.inventory.setItem(20, yes);
    this.inventory.setItem(24, no);
    this.inventory.setItem(31, list2);
    this.inventory.setItem(43, party);
    this.inventory.setItem(49, author);
    if (this.api.getSentRequests(this.manager.getPlayerFetch()).size() > 0)
      this.inventory.setItem(37, sent); 
    setFillerGlass();
  }
}
