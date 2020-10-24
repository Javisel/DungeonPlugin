package us.someteamname.CustomEnchantments;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class Events implements Listener {
    Core plugin;

    public Events(Core plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (EconManager.hasAccount(event.getPlayer().getName()))
            return;
        EconManager.setBalance(event.getPlayer().getName(), 10.0D);
    }

    @EventHandler
    private void playerInteractEntityEvent(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getCustomName() == null)
            return;
        if (event.getRightClicked().getCustomName()
                .equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&5Soul Merchant")))
            event.getPlayer().performCommand("soulenchanter");
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() instanceof Player) {
            int d = event.getDroppedExp();
            Player p = event.getEntity().getKiller();
            if (this.plugin.getConfig().getInt("Exp.isEnabled") > 1 &&
                    this.plugin.getConfig().getInt("MessageToggle.UncoverSoulMessage") > 1)
                EconManager.setBalance(p.getName(), EconManager.getBalance(p.getName()).doubleValue() + d);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getItemInHand() != null &&
                player.getItemInHand().hasItemMeta() &&
                player.getItemInHand().getItemMeta().hasDisplayName()) {
            ItemStack hand = player.getItemInHand();
            int hand1 = player.getInventory().firstEmpty();
            openBook(hand, player, hand1, "Basic", 1);
            openBook(hand, player, hand1, "Advanced", 2);
            openBook(hand, player, hand1, "Extreme", 3);
        }
    }

    public void openBook(ItemStack hand, Player player, int hand1, String tier, int tiern) {

    }
}