package us.someteamname.CustomEnchantments.enchants;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import us.someteamname.CustomEnchantments.Enchant;
import us.someteamname.CustomEnchantments.Util;

public class Chestplate implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void SpikedCoating(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player player = (Player)event.getEntity();
            Player damager = (Player)event.getDamager();
            if (Enchant.hasEnchant("Spiked Coating", player.getInventory().getChestplate())) {
                int tier = Enchant.getEnchantTier("Spiked Coating", player.getInventory().getChestplate());
                int percent = 2;
                if (tier == 2) {
                    percent = 100;
                } else if (tier == 1) {
                    percent = 50;
                } else if (Util.randInt(1, 100) <= percent) {
                    damager.damage(1.0D);
                }
            }
        }
    }
}
