package us.someteamname.partysystem.gui.party;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import us.someteamname.partysystem.PartyUp;
import us.someteamname.partysystem.gui.Manager;
import us.someteamname.partysystem.gui.Menu;
import us.someteamname.partysystem.util.Config;
import us.someteamname.partysystem.util.enums.FriendAction;


public class PartyMenu extends Menu {
  public PartyMenu(Manager manager) {
    super(manager);
  }
  
  String color(String message) {
    return ChatColor.translateAlternateColorCodes('&', message);
  }
  
  public String getMenuName() {
    return color("|&m-----&r &3&oPARTY HELP &r&m-----&r|");
  }
  
  public int getSlots() {
    return 27;
  }
  
  public void handleMenu(InventoryClickEvent e) {
    Player p = (Player)e.getWhoClicked();
    Config data = new Config(p.getUniqueId().toString());
    Material mat = e.getCurrentItem().getType();
    if (mat.equals(Material.CRAFTING_TABLE)) {
      p.closeInventory();
      this.api.formParty(p, FriendAction.OPEN);
      Manager view = PartyUp.getMenuView(p);
      view.setPlayerFetch(p);
      (new PartyInfo(view)).open();
      return;
    } 
    if (mat.equals(Material.FURNACE)) {
      p.closeInventory();
      this.api.formParty(p, FriendAction.CLOSED);
      Manager view = PartyUp.getMenuView(p);
      view.setPlayerFetch(p);
      (new PartyInfo(view)).open();
      return;
    } 
    if (mat.equals(Material.IRON_DOOR)) {
      Manager view = PartyUp.getMenuView(p);
      view.setPlayerFetch(p);
      (new PartyFinder(view)).open();
      return;
    } 
    if (mat.equals(Material.SKELETON_SKULL)) {
      if (this.api.isInParty(data, p)) {
        this.api.leaveParty(p);
        Manager view = PartyUp.getMenuView(p);
        view.setPlayerFetch(p);
        (new PartyMenu(view)).open();
      } 
      return;
    } 
    if (mat.equals(Material.TORCH)) {
      Manager view = PartyUp.getMenuView(p);
      view.setPlayerFetch(p);
      (new PartyInfo(view)).open();
    } 
  }
  
  public void setMenuItems() {
    ItemStack c = makeItem(Material.CRAFTING_TABLE, "&e&oCreate a &3OPEN &e&oparty.", new String[] { color("&a&lANYONE &2can join.") });
    ItemStack cc = makeItem(Material.FURNACE, "&e&oCreate a &3CLOSED &e&oparty.", new String[] { color("&6&lONLY &e&ofriends can join.") });
    ItemStack j = makeItem(Material.IRON_DOOR, "&a&oJoin a &3party", new String[] { "" });
    ItemStack k = makeItem(Material.TORCH, "&3&oParty &a&oinformation", new String[] { "" });
    ItemStack l = makeItem(Material.SKELETON_SKULL, "&a&oLeave your &ccurrent &3party", new String[] { "" });
    Config data = new Config(this.manager.getPlayerFetch().getUniqueId().toString());
    if (this.api.isInParty(data, this.manager.getPlayerFetch()))
      this.inventory.setItem(7, l); 
    if (this.api.isInParty(data, this.manager.getPlayerFetch()))
      this.inventory.setItem(5, k); 
    this.inventory.setItem(1, c);
    this.inventory.setItem(2, cc);
    this.inventory.setItem(3, j);
    setFillerGlass();
  }
}

