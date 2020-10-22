package us.someteamname.partysystem.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;

import us.someteamname.partysystem.FriendAPI;
import us.someteamname.partysystem.PartyUp;
import us.someteamname.partysystem.gui.Manager;
import us.someteamname.partysystem.gui.Menu;
import us.someteamname.partysystem.gui.party.PartyIncoming;
import us.someteamname.partysystem.util.Config;

public class Players implements Listener {
	  public static String color(String text) {
	    return ChatColor.translateAlternateColorCodes('&', text);
	  }
	  
	  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	  public void onJoin(PlayerJoinEvent e) {
	    Player p = e.getPlayer();
	    Announcer rem = new Announcer(p);
	    rem.runTaskTimerAsynchronously((Plugin)PartyUp.get(), 20L, 6000L);
	  }
	  
	  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	  public void onQuit(PlayerQuitEvent e) {
	    Player p = e.getPlayer();
	    FriendAPI api = new FriendAPI();
	    Config data = new Config(p.getUniqueId().toString());
	    if (api.isInParty(data, p)) {
	      api.leaveParty(p);
	      return;
	    } 
	  }
	  
	  private List<Material> getSwords() {
	    ArrayList<Material> mats = new ArrayList<>();
	    mats.add(Material.DIAMOND_SWORD);
	    mats.add(Material.IRON_SWORD);
	    mats.add(Material.GOLDEN_SWORD);
	    mats.add(Material.WOODEN_SWORD);
	    mats.add(Material.NETHERITE_SWORD);
	    return mats;
	  }
	  
	  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	  public void onPlayerInteractPlayer(PlayerInteractAtEntityEvent e) {
	    if (e.getRightClicked() instanceof Player) {
	      Player p = e.getPlayer();
	      Player clicked = (Player)e.getRightClicked();
	      FriendAPI api = new FriendAPI();
	      for (Material mat : getSwords()) {
	        if (p.getInventory().getItemInMainHand().getType().equals(mat)) {
	          p.sendMessage(color(api.prefix + "You sent a party invite to &e&o" + clicked.getName()));
	          Manager view = PartyUp.getMenuView(clicked);
	          view.setPlayerUUID(p.getUniqueId());
	          (new PartyIncoming(view)).open();
	          break;
	        } 
	      } 
	    } 
	  }
	  
	  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	  public void onPlayerChat(AsyncPlayerChatEvent e) {
	    Player p = e.getPlayer();
	    FriendAPI api = new FriendAPI();
	    if (api.inPartyChat(p)) {
	      e.setCancelled(true);
	      for (String members : api.PartyMembers(api.getParty(p))) {
	        Player rec = Bukkit.getPlayer(members);
	        rec.sendMessage(color("&f(&3PARTY&f) &7" + p.getName() + " &f: &b&o&n" + e.getMessage()));
	      } 
	      return;
	    } 
	  }
	  
	  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	  public void onMenuClick(InventoryClickEvent e) {
	    InventoryHolder holder = e.getInventory().getHolder();
	    if (holder instanceof Menu) {
	      e.setCancelled(true);
	      if (e.getCurrentItem() == null)
	        return; 
	      Menu menu = (Menu)holder;
	      menu.handleMenu(e);
	    } 
	  }
	  
	  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	  public void onPlayerHurtPlayer(EntityDamageByEntityEvent event) {
	    FriendAPI api = new FriendAPI();
	    if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
	      Player p = (Player)event.getDamager();
	      Player target = (Player)event.getEntity();
	      if (api.getFriends(p).contains(target.getName())) {
	        event.setCancelled(true);
	        p.sendMessage(ChatColor.translateAlternateColorCodes('&', api.prefix + "&4&oYou can't hurt friends.. You don't deserve friends.. meany.."));
	        target.sendMessage(ChatColor.translateAlternateColorCodes('&', api.prefix + "&fYour " + '"' + "&efriend&f" + '"' + " " + p.getName() + " tried &c&ohurting you &f:&b'&f("));
	        return;
	      } 
	    } 
	    if (event.getEntity() instanceof Player && event.getDamager() instanceof Projectile && ((Projectile)event
	      .getDamager()).getShooter() instanceof Player) {
	      Projectile pr = (Projectile)event.getDamager();
	      Player p = (Player)pr.getShooter();
	      Player target = (Player)event.getEntity();
	      event.setCancelled(true);
	      p.sendMessage(ChatColor.translateAlternateColorCodes('&', api.prefix + "&4&oYou can't hurt friends.. You don't deserve friends.. meany.."));
	      target.sendMessage(ChatColor.translateAlternateColorCodes('&', api.prefix + "&fYour " + '"' + "&efriend&f" + '"' + " " + p.getName() + " tried &c&ohurting you &f:&b'&f("));
	      return;
	    } 
	  }
	}
