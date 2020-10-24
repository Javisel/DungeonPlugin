package us.someteamname.CustomEnchantments.enchants;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;
import us.someteamname.CustomEnchantments.Enchant;
import us.someteamname.CustomEnchantments.Util;

public class Shields implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void Shockwave(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player player = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();
            if (Enchant.hasEnchant("Shockwave", player.getInventory().getItemInOffHand())) {
                int tier = Enchant.getEnchantTier("Shockwave", player.getInventory().getItemInOffHand());
                int max = 80;
                if (tier == 3) {
                    max = 5;
                } else if (tier == 2) {
                    max = 10;
                } else if (tier == 1) {
                    max = 15;
                }
                if (player.isBlocking() && Util.randInt(1, max) == 1) {
                    damager.damage(max);
                }
            }
        }
    }
}