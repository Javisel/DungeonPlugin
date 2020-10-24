package us.someteamname.CustomEnchantments.enchants;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;
import us.someteamname.CustomEnchantments.Enchant;
import us.someteamname.CustomEnchantments.Util;

public class Leggings implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void Shockwave(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player player = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();
            if (Enchant.hasEnchant("Shockwave", player.getInventory().getLeggings())) {
                int tier = Enchant.getEnchantTier("Shockwave", player.getInventory().getLeggings());
                int max = 80;
                if (tier == 2) {
                    max = 5;
                } else if (tier == 1) {
                    max = 10;
                }
                if (Util.randInt(1, max) == 1) {
                    damager.setVelocity(new Vector(-damager.getLocation().getDirection().getX() * 3.0D,
                            damager.getVelocity().getY(), -damager.getLocation().getDirection().getZ() * 3.0D));
                }
            }
        }
    }
}
