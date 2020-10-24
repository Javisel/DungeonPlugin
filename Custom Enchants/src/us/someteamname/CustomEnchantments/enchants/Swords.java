package us.someteamname.CustomEnchantments.enchants;
import java.util.Random;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import us.someteamname.CustomEnchantments.Enchant;
import us.someteamname.CustomEnchantments.Util;

public class Swords implements Listener {
  @EventHandler(priority = EventPriority.LOWEST)
  public void Retaliate(EntityDamageByEntityEvent event) {
    if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
      Player damager = (Player)event.getDamager();
      Player target = (Player)event.getEntity();
      if (Enchant.hasEnchant("Retaliate", damager.getItemInHand())) {
        int tier = Enchant.getEnchantTier("Retaliate", damager.getItemInHand());
        int max = 80;
        if (tier == 4) {
          max = 20;
        } else if (tier == 3) {
          max = 24;
        } else if (tier == 2) {
          max = 26;
        } else if (tier == 1) {
          max = 28;
        } 
        if (Util.randInt(1, max) == 1)
          if (Util.randInt(1, 2) == 1) {
            damager.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 2));
          } else {
            target.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 200, 3));
          }  
      } 
    } 
  }
  
  @EventHandler(priority = EventPriority.LOWEST)
  public void CorrosiveCoating(EntityDamageByEntityEvent event) {
    if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
      Player player = (Player)event.getEntity();
      Player damager = (Player)event.getDamager();
      if (Enchant.hasEnchant("Corrosive Coating", player.getInventory().getChestplate())) {
        int tier = Enchant.getEnchantTier("Corrosive Coating", player.getInventory().getChestplate());
        int percent = 2;
        if (tier == 4) {
          percent = 4;
        } else if (tier == 3) {
          percent = 3;
        } else if (tier == 2) {
          percent = 2;
        } else if (tier == 1) {
          percent = 1;
        } 
        if (damager.getInventory().getItemInHand().getType().name().endsWith("SWORD") || damager.getInventory()
          .getItemInHand().getType().name().endsWith("AXE") || 
          damager.getInventory().getItemInHand().getType().name().endsWith("HOE") || 
          damager.getInventory().getItemInHand().getType().name().endsWith("SPADE")) {
          ItemStack hand_item = damager.getItemInHand();
          hand_item.setDurability((short)(hand_item.getDurability() + percent));
          damager.setItemInHand(hand_item);
        } 
      } 
    } 
  }
  
  @EventHandler(priority = EventPriority.LOWEST)
  public void Annihilate(EntityDamageByEntityEvent event) {
    if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
      Player damager = (Player)event.getEntity();
      Player player = (Player)event.getDamager();
      if (Enchant.hasEnchant("Annihilate", player.getInventory().getItemInHand())) {
        int tier = Enchant.getEnchantTier("Annihilate", player.getInventory().getItemInHand());
        int percent = 2;
        if (tier == 5) {
          percent = 5;
        } else if (tier == 4) {
          percent = 4;
        } else if (tier == 3) {
          percent = 3;
        } else if (tier == 2) {
          percent = 2;
        } else if (tier == 1) {
          percent = 1;
        } 
        if (damager.getInventory().getHelmet() != null) {
          ItemStack hand_item = damager.getInventory().getHelmet();
          hand_item.setDurability((short)(hand_item.getDurability() + percent));
          damager.getInventory().setHelmet(hand_item);
        } else if (damager.getInventory().getChestplate() != null) {
          ItemStack hand_item = damager.getInventory().getChestplate();
          hand_item.setDurability((short)(hand_item.getDurability() + percent));
          damager.getInventory().setChestplate(hand_item);
        } else if (damager.getInventory().getLeggings() != null) {
          ItemStack hand_item = damager.getInventory().getLeggings();
          hand_item.setDurability((short)(hand_item.getDurability() + percent));
          damager.getInventory().setLeggings(hand_item);
        } else if (damager.getInventory().getBoots() != null) {
          ItemStack hand_item = damager.getInventory().getBoots();
          hand_item.setDurability((short)(hand_item.getDurability() + percent));
          damager.getInventory().setBoots(hand_item);
        } 
      } 
    } 
  }
  
  @EventHandler(priority = EventPriority.LOWEST)
  public void CripplingSwing(EntityDamageByEntityEvent event) {
    if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
      Player damager = (Player)event.getDamager();
      Player target = (Player)event.getEntity();
      if (Enchant.hasEnchant("Crippling Swing", damager.getItemInHand())) {
        int tier = Enchant.getEnchantTier("Crippling Swing", damager.getItemInHand());
        int max = 80;
        if (tier == 6) {
          max = 20;
        } else if (tier == 5) {
          max = 22;
        } else if (tier == 4) {
          max = 24;
        } else if (tier == 3) {
          max = 26;
        } else if (tier == 2) {
          max = 28;
        } else if (tier == 1) {
          max = 30;
        } 
        if (Util.randInt(1, max) == 1)
          target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 3)); 
      } 
    } 
  }
  
  @EventHandler
  public void Blast(EntityDeathEvent event) {
    if (event.getEntity().getKiller() != null) {
      Player killer = event.getEntity().getKiller();
      Enchant.hasEnchant("Blast", killer.getItemInHand());
    } 
  }
  
  @EventHandler(priority = EventPriority.LOWEST)
  public void DualStrike(EntityDamageByEntityEvent event) {
    if (event.getDamager() instanceof Player) {
      Player damager = (Player)event.getDamager();
      if (Enchant.hasEnchant("Dual Strike", damager.getItemInHand())) {
        int tier = Enchant.getEnchantTier("Dual Strike", damager.getItemInHand());
        int max = 80;
        if (tier == 4) {
          max = 12;
        } else if (tier == 3) {
          max = 14;
        } else if (tier == 2) {
          max = 16;
        } else if (tier == 1) {
          max = 18;
        } 
        if (Util.randInt(1, max) == 1)
          event.setDamage(event.getDamage() * 2.0D); 
      } 
    } 
  }
  
  @EventHandler(priority = EventPriority.LOWEST)
  public void AquaticBlade(EntityDamageByEntityEvent event) {
    if (event.getDamager() instanceof Player) {
      Player damager = (Player)event.getDamager();
      if (Enchant.hasEnchant("Aquatic Blade", damager.getItemInHand())) {
        int tier = Enchant.getEnchantTier("Aquatic Blade", damager.getItemInHand());
        if (tier == 1 && (
          event.getEntity() instanceof org.bukkit.entity.Blaze || event.getEntity() instanceof org.bukkit.entity.Ghast || 
          event.getEntity() instanceof org.bukkit.entity.PigZombie || event.getEntity() instanceof org.bukkit.entity.MagmaCube))
          event.setDamage(event.getDamage() * 2.0D); 
      } 
    } 
  }
  
  @EventHandler(priority = EventPriority.LOWEST)
  public void TaintedWeb(EntityDamageByEntityEvent event) {
    if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
      LivingEntity player = (LivingEntity)event.getEntity();
      Player damager = (Player)event.getDamager();
      if (Enchant.hasEnchant("Tainted Web", damager.getItemInHand())) {
        int tier = Enchant.getEnchantTier("Tainted Web", damager.getItemInHand());
        int percent = 2;
        if (tier == 3) {
          percent = 8;
        } else if (tier == 2) {
          percent = 6;
        } else if (tier == 1) {
          percent = 4;
        } 
        if (Util.randInt(1, 100) <= percent) {
          if (player.hasPotionEffect(PotionEffectType.SLOW))
            player.removePotionEffect(PotionEffectType.SLOW); 
          if (player.hasPotionEffect(PotionEffectType.SPEED))
            player.removePotionEffect(PotionEffectType.SPEED); 
          if (player.hasPotionEffect(PotionEffectType.JUMP))
            player.removePotionEffect(PotionEffectType.JUMP); 
          player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 5));
          player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, -1));
          player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 80, 250));
        } 
      } 
    } 
  }
  
  @EventHandler(priority = EventPriority.LOWEST)
  public void CorruptAura(EntityDamageByEntityEvent event) {
    if (event.getEntity() instanceof Player) {
      Player damager = (Player)event.getEntity();
      if (Enchant.hasEnchant("Corrupt Aura", damager.getItemInHand())) {
        int tier = Enchant.getEnchantTier("Corrupt Aura", damager.getItemInHand());
        int percent = 2;
        if (tier == 5) {
          percent = 10;
        } else if (tier == 4) {
          percent = 8;
        } else if (tier == 3) {
          percent = 6;
        } else if (tier == 2) {
          percent = 4;
        } else if (tier == 1) {
          percent = 2;
        } 
        if (Util.randInt(1, 100) <= percent) {
          Random random = new Random();
          int PositivePotions = random.nextInt(6) + 1;
          if (PositivePotions == 1 && damager.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE))
            damager.removePotionEffect(PotionEffectType.INCREASE_DAMAGE); 
          if (PositivePotions == 2 && damager.hasPotionEffect(PotionEffectType.SPEED))
            damager.removePotionEffect(PotionEffectType.SPEED); 
          if (PositivePotions == 3 && damager.hasPotionEffect(PotionEffectType.INVISIBILITY))
            damager.removePotionEffect(PotionEffectType.INVISIBILITY); 
          if (PositivePotions == 4 && damager.hasPotionEffect(PotionEffectType.FAST_DIGGING))
            damager.removePotionEffect(PotionEffectType.FAST_DIGGING); 
          if (PositivePotions == 5 && damager.hasPotionEffect(PotionEffectType.REGENERATION))
            damager.removePotionEffect(PotionEffectType.REGENERATION); 
          if (PositivePotions == 6 && damager.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE))
            damager.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE); 
        } 
      } 
    } 
  }
  
  @EventHandler(priority = EventPriority.LOWEST)
  public void Leech(EntityDamageByEntityEvent event) {
    if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
      LivingEntity player = (LivingEntity)event.getEntity();
      Player damager = (Player)event.getDamager();
      if (Enchant.hasEnchant("Leech", damager.getItemInHand())) {
        int tier = Enchant.getEnchantTier("Leech", damager.getItemInHand());
        int max = 80;
        if (tier == 3) {
          max = 5;
        } else if (tier == 2) {
          max = 10;
        } else if (tier == 1) {
          max = 20;
        } 
        if (Util.randInt(1, max) == 1) {
          damager.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 40, 4));
          player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 40, 4));
        } 
      } 
    } 
  }
  
  @EventHandler(priority = EventPriority.LOWEST)
  public void BlindingStrike(EntityDamageByEntityEvent event) {
    if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
      LivingEntity player = (LivingEntity)event.getEntity();
      Player damager = (Player)event.getDamager();
      if (Enchant.hasEnchant("Blinding Strike", damager.getItemInHand())) {
        int tier = Enchant.getEnchantTier("Blinding Strike", damager.getItemInHand());
        int max = 80;
        if (tier == 3) {
          max = 5;
        } else if (tier == 2) {
          max = 10;
        } else if (tier == 1) {
          max = 20;
        } 
        if (Util.randInt(1, max) == 1)
          player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * Util.randInt(3, 10), 0)); 
      } 
    } 
  }
}