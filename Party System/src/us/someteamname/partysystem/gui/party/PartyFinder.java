package us.someteamname.partysystem.gui.party;

import java.text.SimpleDateFormat;
import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import us.someteamname.partysystem.PartyUp;
import us.someteamname.partysystem.gui.Manager;
import us.someteamname.partysystem.gui.MenuPaginated;
import us.someteamname.partysystem.gui.friend.FriendMenu;
import us.someteamname.partysystem.util.Config;


public class PartyFinder extends MenuPaginated {
  public PartyFinder(Manager manager) {
    super(manager);
  }
  
  String color(String message) {
    return ChatColor.translateAlternateColorCodes('&', message);
  }
  
  public String getMenuName() {
    return color("&5&oPlayer Finder |&m------");
  }
  
  public int getSlots() {
    return 54;
  }
  
  public void handleMenu(InventoryClickEvent e) {
    Player p = (Player)e.getWhoClicked();
    ArrayList<Player> clans = new ArrayList<>(Bukkit.getOnlinePlayers());
    if (e.getCurrentItem().getType().equals(Material.TOTEM_OF_UNDYING)) {
      Manager manager = PartyUp.getMenuView(p);
      manager.setPlayerFetch(p);
      (new FriendMenu(manager)).open();
    } 
    if (e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
      Manager manager = PartyUp.getMenuView(p);
      manager.setPlayerUUID(UUID.fromString((String)e.getCurrentItem().getItemMeta().getPersistentDataContainer()
          .get(new NamespacedKey((Plugin)PartyUp.get(), "uuid"), PersistentDataType.STRING)));
      if (manager.getPlayerUUID().equals(p.getName())) {
        p.closeInventory();
        this.api.msg(p, this.api.prefix + "&c&oYou cannot join yourself... Nice try though");
        p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_HURT, 8.0F, 1.0F);
        return;
      } 
      (new PartyFinderOptions(manager)).open();
    } else if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
      p.closeInventory();
      p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_DEATH, 8.0F, 1.0F);
    } else if (e.getCurrentItem().getType().equals(Material.DARK_OAK_BUTTON)) {
      if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Left")) {
        if (this.page == 0) {
          p.sendMessage(ChatColor.GRAY + "You are already on the first page.");
          p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 8.0F, 1.0F);
        } else {
          this.page--;
          open();
        } 
      } else if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())
        .equalsIgnoreCase("Right")) {
        if (this.index + 1 < clans.size()) {
          this.page++;
          open();
        } else {
          p.sendMessage(ChatColor.GRAY + "You are on the last page.");
          p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 8.0F, 1.0F);
        } 
      } 
    } 
  }
  
  public void setMenuItems() {
    ItemStack back = makeItem(Material.TOTEM_OF_UNDYING, color("&a&oGo back."), new String[] { "" });
    this.inventory.setItem(45, back);
    addMenuBorder();
    ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
    if (players != null && !players.isEmpty()) {
      for (int i = 0; i < getMaxItemsPerPage(); i++) {
        this.index = getMaxItemsPerPage() * this.page + i;
        if (this.index >= players.size())
          break; 
        if (players.get(this.index) != null) {
          Date date = new Date(((Player)players.get(this.index)).getLastPlayed());
          Date date2 = new Date(((Player)players.get(this.index)).getFirstPlayed());
          SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy z");
          sdf.setTimeZone(TimeZone.getTimeZone("PST"));
          String firstPlayed = sdf.format(date2);
          String lastPlayed = sdf.format(date);
          ItemStack playerItem = new ItemStack(Material.PLAYER_HEAD, 1);
          ItemMeta playerMeta = playerItem.getItemMeta();
          Config data = new Config(((Player)players.get(this.index)).getUniqueId().toString());
          if (this.api.isInParty(data, players.get(this.index)) && this.api.getPartyStatus(this.api.getParty(players.get(this.index))).equals("OPEN")) {
            if (this.api.getRequests(players.get(this.index)).contains(this.manager.getPlayerFetch().getName())) {
              playerMeta.setDisplayName(color("(&b&lWAITING&r) [&aOPEN&r] &a&lOnline: &e&o" + ((Player)players.get(this.index)).getName()));
            } else if (this.api.getFriends(this.manager.getPlayerFetch()).contains(((Player)players.get(this.index)).getName())) {
              playerMeta.setDisplayName(color("(&6&lFRIENDS&r) [&aOPEN&r] &a&lOnline: &e&o" + ((Player)players.get(this.index)).getName()));
            } else if (((Player)players.get(this.index)).getName().equals(this.manager.getPlayerFetch().getName())) {
              playerMeta.setDisplayName(color("&a&lYOU &r[&aOPEN&r]"));
            } else {
              playerMeta.setDisplayName(color("[&aOPEN&r] &a&lOnline: &e&o" + ((Player)players.get(this.index)).getName()));
            } 
          } else if (this.api.isInParty(data, players.get(this.index)) && this.api.getPartyStatus(this.api.getParty(players.get(this.index))).equals("LOCKED")) {
            if (this.api.getRequests(players.get(this.index)).contains(this.manager.getPlayerFetch().getName())) {
              playerMeta.setDisplayName(color("(&b&lWAITING&r) [&cCLOSED&r] &a&lOnline: &e&o" + ((Player)players.get(this.index)).getName()));
            } else if (this.api.getFriends(this.manager.getPlayerFetch()).contains(((Player)players.get(this.index)).getName())) {
              playerMeta.setDisplayName(color("(&6&lFRIENDS&r) [&cCLOSED&r] &a&lOnline: &e&o" + ((Player)players.get(this.index)).getName()));
            } else if (((Player)players.get(this.index)).getName().equals(this.manager.getPlayerFetch().getName())) {
              playerMeta.setDisplayName(color("&a&lYOU &r[&cCLOSED&r]"));
            } else {
              playerMeta.setDisplayName(color("[&cCLOSED&r] &a&lOnline: &e&o" + ((Player)players.get(this.index)).getName()));
            } 
          } else if (this.api.getRequests(players.get(this.index)).contains(this.manager.getPlayerFetch().getName())) {
            playerMeta.setDisplayName(color("(&b&lWAITING&r) &a&lOnline: &e&o" + ((Player)players.get(this.index)).getName()));
          } else if (this.api.getFriends(this.manager.getPlayerFetch()).contains(((Player)players.get(this.index)).getName())) {
            playerMeta.setDisplayName(color("(&6&lFRIENDS&r) &a&lOnline: &e&o" + ((Player)players.get(this.index)).getName()));
          } else if (((Player)players.get(this.index)).getName().equals(this.manager.getPlayerFetch().getName())) {
            playerMeta.setDisplayName(color("&a&lYOU"));
          } else {
            playerMeta.setDisplayName(color("&a&lOnline: &e&o" + ((Player)players.get(this.index)).getName()));
          } 
          playerMeta.setLore(Arrays.asList(new String[] { color("&a&l&oFirst joined: &r" + firstPlayed), color("&a&l&oLast on: &r" + lastPlayed) }));
          playerMeta.getPersistentDataContainer().set(new NamespacedKey((Plugin)PartyUp.get(), "uuid"), PersistentDataType.STRING, ((Player)players
              .get(this.index)).getName());
          playerItem.setItemMeta(playerMeta);
          this.inventory.addItem(new ItemStack[] { playerItem });
        } 
      } 
      setFillerGlass();
    } 
  }
}
