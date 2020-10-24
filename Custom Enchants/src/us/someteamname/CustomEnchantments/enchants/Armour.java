package us.someteamname.CustomEnchantments.enchants;

import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import us.someteamname.CustomEnchantments.Enchant;
import us.someteamname.CustomEnchantments.Util;

public class Armour implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void Juggernaut(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            if (Enchant.hasEnchant("Juggernaut", player.getInventory().getChestplate()) ||
                    Enchant.hasEnchant("Juggernaut", player.getInventory().getBoots()) ||
                    Enchant.hasEnchant("Juggernaut", player.getInventory().getHelmet()) ||
                    Enchant.hasEnchant("Juggernaut", player.getInventory().getLeggings())) {
                int tier = 0;
                if (Enchant.hasEnchant("Juggernaut", player.getInventory().getHelmet())) {
                    tier = Enchant.getEnchantTier("Juggernaut", player.getInventory().getHelmet());
                } else if (Enchant.hasEnchant("Juggernaut", player.getInventory().getChestplate())) {
                    tier = Enchant.getEnchantTier("Juggernaut", player.getInventory().getChestplate());
                } else if (Enchant.hasEnchant("Juggernaut", player.getInventory().getLeggings())) {
                    tier = Enchant.getEnchantTier("Juggernaut", player.getInventory().getLeggings());
                } else if (Enchant.hasEnchant("Juggernaut", player.getInventory().getBoots())) {
                    tier = Enchant.getEnchantTier("Juggernaut", player.getInventory().getBoots());
                }
                int max = 80;
                if (tier == 3) {
                    max = 15;
                } else if (tier == 2) {
                    max = 30;
                } else if (tier == 1) {
                    max = 45;
                }
                if (Util.randInt(1, max) == 1)
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 1));
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void Resurrection(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            if ((Enchant.hasEnchant("Resurrection", player.getInventory().getChestplate()) ||
                    Enchant.hasEnchant("Resurrection", player.getInventory().getBoots()) ||
                    Enchant.hasEnchant("Resurrection", player.getInventory().getHelmet()) ||
                    Enchant.hasEnchant("Resurrection", player.getInventory().getLeggings())) &&
                    player.getHealth() <= 12.0D) {
                int tier = Enchant.getEnchantTier("Resurrection", player.getInventory().getChestplate());
                int max = 5;
                if (tier == 3) {
                    max = 6;
                } else if (tier == 2) {
                    max = 8;
                } else if (tier == 1) {
                    max = 10;
                }
                if (Util.randInt(1, max) == 1) {
                    player.getWorld().playEffect(player.getLocation().add(0.0D, 1.0D, 0.0D), Effect.POTION_BREAK, 1);
                    player.setHealth(20.0D);
                }
            }
        }
    }
}
