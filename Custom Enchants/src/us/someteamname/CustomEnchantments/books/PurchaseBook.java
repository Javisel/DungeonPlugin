package us.someteamname.CustomEnchantments.books;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import us.someteamname.CustomEnchantments.Core;
import us.someteamname.CustomEnchantments.EconManager;

public class PurchaseBook implements Listener {
    Core plugin;

    public PurchaseBook(Core plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack stack = event.getCurrentItem();
        Player player = (Player)event.getWhoClicked();
        if (event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GRAY + "Soul Merchant")) {
            event.setCancelled(true);
            if (stack != null && stack.hasItemMeta() && stack.getItemMeta().hasDisplayName())
                if (event.getSlot() == 3) {
                    handlePurchase(player, 1, "Basic", false);
                } else if (event.getSlot() == 4) {
                    handlePurchase(player, 2, "Advanced", false);
                } else if (event.getSlot() == 5) {
                    handlePurchase(player, 3, "Extreme", false);
                } else if (event.getSlot() == 6) {
                    handlePurchase(player, 4, "Essence", true);
                }
        }
    }

    public void handlePurchase(Player player, int tier, String configname, boolean essence) {
        if (this.plugin.getConfig().getInt("Exp.isEnabled") == 1) {
            if (spendLevels(player, this.plugin.getConfig().getInt("Exp." + configname))) {
                player.getInventory().addItem(new ItemStack[] { this.plugin.getRandomEnchantBook(tier) });
                if (!essence) {
                    player.sendMessage(String.valueOf(Core.prefix) + Core.getMessage("purchased").replace("{tier}",
                            this.plugin.getConfig().getString(String.valueOf(configname) + "Book.Tier")));
                } else {
                    player.sendMessage(String.valueOf(Core.prefix) + Core.getMessage("purchaseEssence"));
                }
                return;
            }
            return;
        }
        if (EconManager.getBalance(player.getName()).doubleValue() >= this.plugin.getConfig().getInt("Souls." + configname)) {
            EconManager.setBalance(player.getName(),
                    EconManager.getBalance(player.getName()).doubleValue() - this.plugin.getConfig().getInt("Souls." + configname));
            player.getInventory().addItem(new ItemStack[] { this.plugin.getRandomEnchantBook(tier) });
            if (!essence) {
                player.sendMessage(String.valueOf(Core.prefix) + Core.getMessage("purchased").replace("{tier}",
                        this.plugin.getConfig().getString(String.valueOf(configname) + "Book.Tier")));
            } else {
                player.sendMessage(String.valueOf(Core.prefix) + Core.getMessage("purchaseEssence"));
            }
        } else {
            player.sendMessage(ChatColor.RED + "You don't have enough souls!");
        }
    }

    private boolean spendLevels(Player player, int levels) {
        if (player.getLevel() >= levels) {
            player.setLevel(player.getLevel() - levels);
            return true;
        }
        player.closeInventory();
        player.sendMessage(String.valueOf(Core.prefix) + Core.getMessage("noLevels"));
        return false;
    }
}