package us.someteamname.partysystem.gui.friend;

import java.util.ArrayList;
import java.util.List;

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
import us.someteamname.partysystem.util.Config;

public class FriendList extends MenuPaginated {
public FriendList(Manager manager) {
	super(manager);
 	}

 String color(String message) {
    return ChatColor.translateAlternateColorCodes('&', message);
  }
  
  public String getMenuName() {
    return color("&6&oFriend List |&m------");
  }
  
  public int getSlots() {
    return 54;
  }
  
  protected List<String> getFriends(Player target) {
    Config data = new Config(target.getUniqueId().toString());
    return data.getConfig().getStringList("friends-list");
  }
  
  public void handleMenu(InventoryClickEvent e) {
    Player p = (Player)e.getWhoClicked();
    ArrayList<String> friends = new ArrayList<>(getFriends(p));
    Material mat = e.getCurrentItem().getType();
    if (mat.equals(Material.END_CRYSTAL)) {
      Manager manager = PartyUp.getMenuView(p);
      manager.setPlayerEdit((String)e.getCurrentItem().getItemMeta().getPersistentDataContainer()
          .get(new NamespacedKey((Plugin)PartyUp.get(), "uuid"), PersistentDataType.STRING));
      (new FriendOptions(manager)).open();
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
        if (this.index + 1 < friends.size()) {
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
      (new FriendMenu(view)).open();
      p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 8.0F, 1.0F);
    } 
  }
  
  public void setMenuItems() {
    ItemStack back = makeItem(Material.TOTEM_OF_UNDYING, color("&a&oGo back."), new String[] { "" });
    this.inventory.setItem(45, back);
    addMenuBorder();
    ArrayList<String> friends = new ArrayList<>(getFriends(this.manager.getPlayerFetch()));
    if (friends != null && !friends.isEmpty()) {
      for (int i = 0; i < getMaxItemsPerPage(); i++) {
        this.index = getMaxItemsPerPage() * this.page + i;
        if (this.index >= friends.size())
          break; 
        if (friends.get(this.index) != null) {
          ItemStack playerItem = new ItemStack(Material.END_CRYSTAL, 1);
          ItemMeta playerMeta = playerItem.getItemMeta();
          playerMeta.setDisplayName(color("&e&lFriend: &6&o" + (String)friends.get(this.index)));
          playerMeta.getPersistentDataContainer().set(new NamespacedKey((Plugin)PartyUp.get(), "uuid"), PersistentDataType.STRING, friends
              .get(this.index));
          playerItem.setItemMeta(playerMeta);
          this.inventory.addItem(new ItemStack[] { playerItem });
        } 
      } 
      setFillerGlass();
    } 
  }
}

