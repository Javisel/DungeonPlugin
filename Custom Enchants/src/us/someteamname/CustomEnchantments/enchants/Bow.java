package us.someteamname.CustomEnchantments.enchants;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import us.someteamname.CustomEnchantments.Core;
import us.someteamname.CustomEnchantments.Enchant;
import us.someteamname.CustomEnchantments.Util;

public class Bow implements Listener {
    public ArrayList<Entity> successfulshooterse = new ArrayList<>();

    public ArrayList<Entity> successfulshooters = new ArrayList<>();

    public ArrayList<Entity> successfulshooterses = new ArrayList<>();

    public static ArrayList<Arrow> arrowinair = new ArrayList<>();

    @EventHandler
    public void Aimbot(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Mob) {
            final Player player = (Player)e.getEntity();
            if (Enchant.hasEnchant("Aimbot", player.getInventory().getItemInHand()) && e.getForce() == 1.0D) {
                final int tier = Enchant.getEnchantTier("Aimbot", player.getItemInHand());
                arrowinair.add((Arrow)e.getProjectile());
                Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Core.pl, new BukkitRunnable() {
                    public void run() {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            for (Arrow arrow : Util.getNearbyArrows(p, tier)) {
                                if (player != p && Bow.arrowinair.contains(arrow)) {
                                    arrow.remove();
                                    Bow.arrowinair.remove(arrow);
                                    p.damage(tier);
                                    break;
                                }
                            }
                        }
                    }
                } 20L, 20L);
            }
        }
    }

    @EventHandler
    public void AimbotShot(ProjectileHitEvent e) {
        if (e.getEntity() instanceof Arrow && arrowinair.contains(e.getEntity())) {
            Arrow arrow = (Arrow)e.getEntity();
            arrowinair.remove(arrow);
        }
    }

    @EventHandler
    public void SpectralShot(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player)e.getEntity();
            if (Enchant.hasEnchant("Spectral Shot", player.getInventory().getItemInHand()) && e.getForce() == 1.0D) {
                int tier = Enchant.getEnchantTier("Spectral Shot", player.getItemInHand());
                int max = 80;
                if (tier == 2) {
                    max = 10;
                } else if (tier == 1) {
                    max = 20;
                }
                if (Util.randInt(1, max) == 1)
                    this.successfulshooters.add(player);
            }
        }
    }

    @EventHandler
    public void SpectralShotShot(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Arrow) {
            ProjectileSource attacker = ((Arrow)e.getDamager()).getShooter();
            if (attacker instanceof Player && this.successfulshooters.contains(attacker)) {
                if (e.getEntity() instanceof Player) {
                    Player target = (Player)e.getEntity();
                    target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000, 2));
                    target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1000, 2));
                }
                this.successfulshooters.remove(attacker);
            }
        }
    }

    @EventHandler
    public void UnsteadyArrow(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player)e.getEntity();
            if (Enchant.hasEnchant("Unsteady Arrow", player.getInventory().getItemInHand()) && e.getForce() == 1.0D) {
                int tier = Enchant.getEnchantTier("Unsteady Arrow", player.getItemInHand());
                int max = 80;
                if (tier == 2)
                    max = 5;
                if (tier == 2)
                    max = 10;
                if (tier == 1)
                    max = 20;
                if (Util.randInt(1, max) == 1)
                    this.successfulshooterses.add(player);
            }
        }
    }

    @EventHandler
    public void UnsteadyArrowShot(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Arrow) {
            ProjectileSource attacker = ((Arrow)e.getDamager()).getShooter();
            if (attacker instanceof Player && this.successfulshooterses.contains(attacker)) {
                if (e.getEntity() instanceof Player) {
                    Player target = (Player)e.getEntity();
                    target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 1000, 2));
                }
                this.successfulshooterses.remove(attacker);
            }
        }
    }

    @EventHandler
    public void ElementalShot(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player)e.getEntity();
            if (Enchant.hasEnchant("Elemental Shot", player.getInventory().getItemInHand()) && e.getForce() == 1.0D) {
                int tier = Enchant.getEnchantTier("Elemental Shot", player.getItemInHand());
                int max = 80;
                if (tier == 3)
                    max = 5;
                if (tier == 2)
                    max = 10;
                if (tier == 1)
                    max = 20;
                if (Util.randInt(1, max) == 1)
                    this.successfulshooterse.add(player);
            }
        }
    }

    @EventHandler
    public void ElementalShotShot(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Arrow) {
            ProjectileSource attacker = ((Arrow)e.getDamager()).getShooter();
            if (attacker instanceof Player && this.successfulshooterse.contains(attacker)) {
                if (e.getEntity() instanceof Player) {
                    Player target = (Player)e.getEntity();
                    target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000, 2));
                    target.getWorld().strikeLightning(target.getLocation());
                    target.setFireTicks(0);
                    target.setFireTicks(1000);
                }
                this.successfulshooterse.remove(attacker);
            }
        }
    }
}
