package us.someteamname.partysystem.gui.friend;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import us.someteamname.partysystem.gui.Manager;
import us.someteamname.partysystem.gui.Menu;
import us.someteamname.partysystem.util.enums.MailingAction;

public class FriendMailer extends Menu {
public FriendMailer(Manager manager) {
    super(manager);
  }
  
  String color(String message) {
    return ChatColor.translateAlternateColorCodes('&', message);
  }
  
  public String getMenuName() {
    return color(this.api.prefix + "&e&oSend mail to &a" + this.manager.getPlayerUUID());
  }
  
  public int getSlots() {
    return 27;
  }
  
  public void handleMenu(InventoryClickEvent e) {
    Player p = (Player)e.getWhoClicked();
    Material mat = e.getCurrentItem().getType();
    if (mat == Material.BLACK_DYE) {
      p.closeInventory();
      this.api.sendMail(MailingAction.ONE, p, this.manager.getPlayerUUID());
    } 
    if (mat == Material.BLUE_DYE) {
      p.closeInventory();
      this.api.sendMail(MailingAction.TWO, p, this.manager.getPlayerUUID());
    } 
    if (mat == Material.BROWN_DYE) {
      p.closeInventory();
      this.api.sendMail(MailingAction.THREE, p, this.manager.getPlayerUUID());
    } 
    if (mat == Material.CYAN_DYE) {
      p.closeInventory();
      this.api.sendMail(MailingAction.FOUR, p, this.manager.getPlayerUUID());
    } 
    if (mat == Material.GRAY_DYE) {
      p.closeInventory();
      this.api.sendMail(MailingAction.FIVE, p, this.manager.getPlayerUUID());
    } 
    if (mat == Material.GREEN_DYE) {
      p.closeInventory();
      this.api.sendMail(MailingAction.SIX, p, this.manager.getPlayerUUID());
    } 
    if (mat == Material.LIGHT_BLUE_DYE) {
      p.closeInventory();
      this.api.sendMail(MailingAction.SEVEN, p, this.manager.getPlayerUUID());
    } 
    if (mat == Material.LIGHT_GRAY_DYE) {
      p.closeInventory();
      this.api.sendMail(MailingAction.EIGHT, p, this.manager.getPlayerUUID());
    } 
    if (mat == Material.LIME_DYE) {
      p.closeInventory();
      this.api.sendMail(MailingAction.NINE, p, this.manager.getPlayerUUID());
    } 
    if (mat == Material.MAGENTA_DYE) {
      p.closeInventory();
      this.api.sendMail(MailingAction.TEN, p, this.manager.getPlayerUUID());
    } 
  }
  
  public void setMenuItems() {
    ItemStack one = makeItem(Material.BLACK_DYE, "&e&oResponse &7#&e1", new String[] { this.api.getMailingAction(MailingAction.ONE) });
    ItemStack two = makeItem(Material.BLUE_DYE, "&e&oResponse &7#&e2", new String[] { this.api.getMailingAction(MailingAction.TWO) });
    ItemStack three = makeItem(Material.BROWN_DYE, "&e&oResponse &7#&e3", new String[] { this.api.getMailingAction(MailingAction.THREE) });
    ItemStack four = makeItem(Material.CYAN_DYE, "&e&oResponse &7#&e4", new String[] { this.api.getMailingAction(MailingAction.FOUR) });
    ItemStack five = makeItem(Material.GRAY_DYE, "&e&oResponse &7#&e5", new String[] { this.api.getMailingAction(MailingAction.FIVE) });
    ItemStack six = makeItem(Material.GREEN_DYE, "&e&oResponse &7#&e6", new String[] { this.api.getMailingAction(MailingAction.SIX) });
    ItemStack seven = makeItem(Material.LIGHT_BLUE_DYE, "&e&oResponse &7#&e7", new String[] { this.api.getMailingAction(MailingAction.SEVEN) });
    ItemStack eight = makeItem(Material.LIGHT_GRAY_DYE, "&e&oResponse &7#&e8", new String[] { this.api.getMailingAction(MailingAction.EIGHT) });
    ItemStack nine = makeItem(Material.LIME_DYE, "&e&oResponse &7#&e9", new String[] { this.api.getMailingAction(MailingAction.NINE) });
    ItemStack ten = makeItem(Material.MAGENTA_DYE, "&e&oResponse &7#&e10", new String[] { this.api.getMailingAction(MailingAction.TEN) });
    this.inventory.setItem(1, one);
    this.inventory.setItem(2, two);
    this.inventory.setItem(3, three);
    this.inventory.setItem(4, four);
    this.inventory.setItem(5, five);
    this.inventory.setItem(6, six);
    this.inventory.setItem(7, seven);
    this.inventory.setItem(12, eight);
    this.inventory.setItem(13, nine);
    this.inventory.setItem(14, ten);
    setFillerGlass();
  }
}

