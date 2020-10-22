package us.someteamname.partysystem.gui.party;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import us.someteamname.partysystem.PartyUp;
import us.someteamname.partysystem.gui.Manager;
import us.someteamname.partysystem.gui.Menu;
import us.someteamname.partysystem.util.Config;

public class PartyInfo extends Menu {
  public PartyInfo(Manager manager) {
    super(manager);
  }
  
  String color(String message) {
    return ChatColor.translateAlternateColorCodes('&', message);
  }
  
  public String getMenuName() {
    return color("|&m------&r &3&oPARTY &r&m------&r|");
  }
  
  public int getSlots() {
    return 27;
  }
  
  public void handleMenu(InventoryClickEvent e) {
    Player p = (Player)e.getWhoClicked();
    Config data = new Config(p.getUniqueId().toString());
    Material mat = e.getCurrentItem().getType();
    if (mat.equals(Material.SKELETON_SKULL)) {
      if (this.api.isInParty(data, p)) {
        this.api.leaveParty(p);
        Manager view = PartyUp.getMenuView(p);
        view.setPlayerFetch(p);
        (new PartyMenu(view)).open();
      } 
      return;
    } 
    if (mat.equals(Material.PLAYER_HEAD)) {
      Manager view = PartyUp.getMenuView(p);
      view.setPlayerFetch(p);
      (new PartyInviter(view)).open();
      return;
    } 
    if (mat.equals(Material.TOTEM_OF_UNDYING)) {
      Manager manager = PartyUp.getMenuView(p);
      manager.setPlayerFetch(p);
      (new PartyMenu(manager)).open();
    } 
    if (mat.equals(Material.PURPLE_STAINED_GLASS_PANE)) {
      p.closeInventory();
      Bukkit.dispatchCommand((CommandSender)p, "party chat");
    } 
    if (mat.equals(Material.ACACIA_FENCE_GATE)) {
      Manager manager = PartyUp.getMenuView(p);
      manager.setPlayerFetch(p);
      (new PartyTeleport(manager)).open();
    } 
  }
  
  public void setMenuItems() {
    ArrayList<String> members = new ArrayList<>(this.api.PartyMembers(this.api.getParty(this.manager.getPlayerFetch())));
    ItemStack c = makeItem(Material.ACACIA_BOAT, "&r[&6&oMembers&r] - &e" + this.api.PartyMembers(this.api.getParty(this.manager.getPlayerFetch())).size(), members);
    ItemStack j = makeItem(Material.REDSTONE_TORCH, "&r[&6&oLeader&r] - &e" + this.api.getPartyLeader(this.manager.getPlayerFetch()), new String[] { "" });
    ItemStack n = makeItem(Material.PLAYER_HEAD, "&e&oInvite players.", new String[] { "" });
    ItemStack p = makeItem(Material.ACACIA_FENCE_GATE, "&e&oTeleport to members.", new String[] { "" });
    ItemStack l = makeItem(Material.SKELETON_SKULL, "&a&oLeave the &3party", new String[] { "" });
    ItemStack o = makeItem(Material.PURPLE_STAINED_GLASS_PANE, "&b&o&nEnter&3&n Party &b&o&nchat.", new String[] { "" });
    ItemStack back = makeItem(Material.TOTEM_OF_UNDYING, color("&a&oGo back."), new String[] { "" });
    this.inventory.setItem(19, back);
    this.inventory.setItem(25, o);
    this.inventory.setItem(7, l);
    this.inventory.setItem(5, n);
    this.inventory.setItem(4, p);
    this.inventory.setItem(1, c);
    this.inventory.setItem(2, j);
    setFillerGlass();
  }
}
