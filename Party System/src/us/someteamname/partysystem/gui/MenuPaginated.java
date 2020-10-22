package us.someteamname.partysystem.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public abstract class MenuPaginated extends Menu {
  protected int page = 0;
  
  protected int maxItemsPerPage = 28;
  
  protected int index = 0;
  
  public MenuPaginated(Manager manager) {
    super(manager);
  }
  
  public void addMenuBorder() {
    this.inventory.setItem(48, makeItem(Material.DARK_OAK_BUTTON, ChatColor.GREEN + "Left", new String[0]));
    this.inventory.setItem(49, makeItem(Material.BARRIER, ChatColor.DARK_RED + "Close", new String[0]));
    this.inventory.setItem(50, makeItem(Material.DARK_OAK_BUTTON, ChatColor.GREEN + "Right", new String[0]));
    int i;
    for (i = 0; i < 10; i++) {
      if (this.inventory.getItem(i) == null)
        this.inventory.setItem(i, this.FILLER_GLASS); 
    } 
    this.inventory.setItem(17, this.FILLER_GLASS);
    this.inventory.setItem(18, this.FILLER_GLASS);
    this.inventory.setItem(26, this.FILLER_GLASS);
    this.inventory.setItem(27, this.FILLER_GLASS);
    this.inventory.setItem(35, this.FILLER_GLASS);
    this.inventory.setItem(36, this.FILLER_GLASS);
    for (i = 44; i < 54; i++) {
      if (this.inventory.getItem(i) == null)
        this.inventory.setItem(i, this.FILLER_GLASS); 
    } 
  }
  
  public void setFillerGlass() {
    for (int i = 0; i < getSlots(); i++) {
      if (this.inventory.getItem(i) == null)
        this.inventory.setItem(i, this.FILLER_GLASS_LIGHT); 
    } 
  }
  
  public int getMaxItemsPerPage() {
    return this.maxItemsPerPage;
  }
}

