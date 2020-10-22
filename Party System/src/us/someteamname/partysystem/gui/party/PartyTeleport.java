package us.someteamname.partysystem.gui.party;

import java.util.ArrayList;
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


public class PartyTeleport extends MenuPaginated {
  public PartyTeleport(Manager manager) {
    super(manager);
  }
  
  String color(String message) {
    return ChatColor.translateAlternateColorCodes('&', message);
  }
  
  public String getMenuName() {
    return color("|&m-----&r &3&n&oPARTY &r&m-----&r|");
  }
  
  public int getSlots() {
    return 54;
  }
  
  public void handleMenu(InventoryClickEvent e) {
    Player p = (Player)e.getWhoClicked();
    ArrayList<String> members = new ArrayList<>(this.api.PartyMembers(this.api.getParty(p)));
    Material mat = e.getCurrentItem().getType();
    if (mat.equals(Material.PLAYER_HEAD)) {
      Player pl = Bukkit.getPlayer((String)e.getCurrentItem().getItemMeta().getPersistentDataContainer()
          .get(new NamespacedKey((Plugin)PartyUp.get(), "uuid"), PersistentDataType.STRING));
      p.teleport(pl.getLocation());
      p.sendMessage(color(this.api.prefix + "&aTeleported to party member: " + pl.getName()));
      p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 8.0F, 1.0F);
    } else if (mat.equals(Material.BARRIER)) {
      p.closeInventory();
    } else if (mat.equals(Material.DARK_OAK_BUTTON)) {
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
        if (this.index + 1 < members.size()) {
          this.page++;
          open();
        } else {
          p.sendMessage(ChatColor.GRAY + "You are on the last page.");
          p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 8.0F, 1.0F);
        } 
      } 
    } 
    if (mat.equals(Material.TOTEM_OF_UNDYING)) {
      Manager view = PartyUp.getMenuView(p);
      view.setPlayerFetch(p);
      (new PartyInfo(view)).open();
      p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 8.0F, 1.0F);
    } 
  }
  
  public void setMenuItems() {
    ItemStack back = makeItem(Material.TOTEM_OF_UNDYING, color("&a&oGo back."), new String[] { "" });
    this.inventory.setItem(45, back);
    addMenuBorder();
    ArrayList<String> members = new ArrayList<>(this.api.PartyMembers(this.api.getParty(this.manager.getPlayerFetch())));
    if (members != null && !members.isEmpty()) {
      for (int i = 0; i < getMaxItemsPerPage(); i++) {
        this.index = getMaxItemsPerPage() * this.page + i;
        if (this.index >= members.size())
          break; 
        if (members.get(this.index) != null) {
          ItemStack playerItem = new ItemStack(Material.PLAYER_HEAD, 1);
          ItemMeta playerMeta = playerItem.getItemMeta();
          if (((String)members.get(this.index)).equals(this.manager.getPlayerFetch().getName())) {
            playerMeta.setDisplayName(color("&a&o&n&lYOU&b: &f&o" + (String)members.get(this.index)));
          } else {
            playerMeta.setDisplayName(color("&3&o&nParty Member&b: &f&o" + (String)members.get(this.index)));
          } 
          playerMeta.getPersistentDataContainer().set(new NamespacedKey((Plugin)PartyUp.get(), "uuid"), PersistentDataType.STRING, members
              .get(this.index));
          playerItem.setItemMeta(playerMeta);
          this.inventory.addItem(new ItemStack[] { playerItem });
        } 
      } 
      setFillerGlass();
    } 
  }
}

