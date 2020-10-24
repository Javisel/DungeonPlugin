package us.someteamname.CustomEnchantments.enchants;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import us.someteamname.CustomEnchantments.Enchant;

public class Helmet implements Listener {
	  @EventHandler(priority = EventPriority.LOWEST)
	  public void Medicate(EntityDamageByEntityEvent event) {
	    if (event.getEntity() instanceof Player) {
	      Player player = (Player)event.getEntity();
	      if (!(event.getDamager() instanceof Player) && 
	        Enchant.hasEnchant("Medicate", player.getInventory().getHelmet()) && 
	        player.getHealth() + 1.0D <= player.getMaxHealth()) {
	        int tier = Enchant.getEnchantTier("Medicate", player.getInventory().getHelmet());
	        if (tier == 2) {
	          player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 1));
	        } else if (tier == 1) {
	          player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 0));
	        } 
	      } 
	    } 
	  }
	  
	  @EventHandler(priority = EventPriority.LOWEST)
	  public void Radiate(PlayerMoveEvent event) {
	    Player player = event.getPlayer();
	    if (Enchant.hasEnchant("Radiate", player.getInventory().getHelmet())) {
	      int tier = Enchant.getEnchantTier("Radiate", player.getInventory().getHelmet());
	      if (tier == 2) {
	        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 9999, 1));
	      } else if (tier == 1) {
	        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 9999, 0));
	      } 
	    } 
	  }
	}