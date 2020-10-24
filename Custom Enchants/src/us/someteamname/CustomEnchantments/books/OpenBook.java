package us.someteamname.CustomEnchantments.books;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import us.someteamname.CustomEnchantments.Core;
import us.someteamname.CustomEnchantments.Eloc;
import us.someteamname.CustomEnchantments.Util;

import java.util.ArrayList;

public class OpenBook implements Listener {
    Core plugin;

    public OpenBook(Core plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClickApply(InventoryClickEvent event) {
        String strName = this.plugin.getConfig().getString("OverlordsEssence.Name");
        ItemStack stack = event.getCurrentItem();
        ItemStack hand = event.getCursor();
        Player player = (Player)event.getWhoClicked();
        if (hand != null && hand.hasItemMeta() && hand.getItemMeta().hasDisplayName())
            if (!hand.getItemMeta().getDisplayName().startsWith((strName) + ChatColor.YELLOW) &&
                    !hand.getItemMeta().getDisplayName().startsWith((strName) + ChatColor.RED) &&
                    !hand.getItemMeta().getDisplayName().startsWith((strName) + ChatColor.GREEN) && !hand.getItemMeta()
                    .getDisplayName().startsWith(ChatColor.GOLD + "ENCHANT")) {
                if (hand.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', strName))) {
                    boolean valid = false;
                    byte b;
                    int i;
                    Eloc[] arrayOfEloc;
                    for (i = (arrayOfEloc = Eloc.values()).length, b = 0; b < i; ) {
                        Eloc eloc = arrayOfEloc[b];
                        for (Material mat : eloc.valid) {
                            if (stack.getType() == mat)
                                valid = true;
                        }
                        b++;
                    }
                    if (valid) {
                        ArrayList<String> lore = new ArrayList<>();
                        boolean prot = false;
                        if (stack.hasItemMeta() && stack.getItemMeta().hasLore())
                            for (String line : stack.getItemMeta().getLore()) {
                                lore.add(line);
                                if (ChatColor.stripColor(line).equalsIgnoreCase("PROTECTED"))
                                    prot = true;
                            }
                        if (!prot) {
                            lore.add(ChatColor.WHITE.BOLD + "PROTECTED");
                            ItemMeta meta = stack.getItemMeta();
                            meta.setLore(lore);
                            stack.setItemMeta(meta);
                            if (hand.getAmount() == 1) {
                                event.setCursor(null);
                            } else {
                                hand.setAmount(hand.getAmount() - 1);
                            }
                            event.setCancelled(true);
                            player.updateInventory();
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                        }
                    }
                }
            } else {
        if (hand != null && hand.getType() != Material.AIR) {
            Enchant ench = null;
            String name1 = ChatColor.stripColor(hand.getItemMeta().getDisplayName());
            if (hand.getItemMeta().getDisplayName()
                    .startsWith(ChatColor.GOLD + "ENCHANT"))
                name1 = ChatColor.stripColor(hand.getItemMeta().getDisplayName().split(" ")[1]);
            String tierS = "I";
            if (name1.endsWith(" I")) {
                name1 = name1.replace(" I", "");
                tierS = "I";
            } else if (name1.endsWith(" II")) {
                name1 = name1.replace(" II", "");
                tierS = "II";
            } else if (name1.endsWith(" III")) {
                name1 = name1.replace(" III", "");
                tierS = "III";
            } else if (name1.endsWith(" IV")) {
                name1 = name1.replace(" IV", "");
                tierS = "IV";
            } else if (name1.endsWith(" V")) {
                name1 = name1.replace(" V", "");
                tierS = "V";
            }
            for (us.someteamname.CustomEnchantments.Enchant Enchant : Core.enchants) {
                if (Enchant.name.equalsIgnoreCase(name1))
                    ench = Enchant;
            }
            if (ench != null)
                for (Eloc loc : ench.locations) {
                    for (Material mat : loc.valid) {
                        if (stack.getType() == mat) {
                            event.setCancelled(true);
                            int success = 0;
                            int destroy = 0;
                            for (String lore : hand.getItemMeta().getLore()) {
                                if (lore.startsWith(ChatColor.GREEN + "Success Rate: ")) {
                                    success = Integer.parseInt(
                                            ChatColor.stripColor(lore.split(ChatColor.GREEN + "Success Rate: ")[1]
                                                    .replace("%", "")));
                                    continue;
                                }
                                if (lore.startsWith(ChatColor.RED + "Destroy Rate: "))
                                    destroy = Integer.parseInt(
                                            ChatColor.stripColor(lore.split(ChatColor.RED + "Destroy Rate: ")[1]
                                                    .replace("%", "")));
                            }
                            if (success == 0 || destroy == 0)
                                success = 100;
                            boolean enchant = false;
                            if (Util.randInt(1, 2) == 1) {
                                if (Util.randInt(1, 100) <= success)
                                    enchant = true;
                            } else if (Util.randInt(1, 100) > destroy) {
                                enchant = true;
                            }
                            if (enchant) {
                                ItemMeta meta = stack.getItemMeta();
                                ArrayList<String> lore = new ArrayList<>();
                                if (meta.hasLore()) {
                                    boolean prot = false;
                                    for (String line : meta.getLore()) {
                                        if (ChatColor.translateAlternateColorCodes('&', line)
                                                .contains(String.valueOf(name1) + " "))
                                            return;
                                        if (ChatColor.stripColor(line).equalsIgnoreCase("PROTECTED")) {
                                            prot = true;
                                            continue;
                                        }
                                        lore.add(line);
                                    }
                                    if (prot)
                                        lore.add(ChatColor.WHITE.BOLD + "PROTECTED");
                                }
                                lore.add(ChatColor.GRAY + name1 + " " + tierS);
                                meta.setLore(lore);
                                stack.setItemMeta(meta);
                                if (hand.getAmount() == 1) {
                                    event.setCursor(null);
                                } else {
                                    hand.setAmount(hand.getAmount() - 1);
                                }
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                                player.sendMessage(
                                        ChatColor.translateAlternateColorCodes('&', Core.getMessage("success"))
                                                .replace("{enchant}", String.valueOf(name1) + " " + tierS));
                                continue;
                            }
                            if (stack.hasItemMeta() && stack.getItemMeta().hasLore() &&
                                    stack.getItemMeta().getLore()
                                            .contains(ChatColor.WHITE.BOLD + "PROTECTED")) {
                                ArrayList<String> lore = new ArrayList<>();
                                for (String line : stack.getItemMeta().getLore()) {
                                    if (!ChatColor.stripColor(line).equalsIgnoreCase("PROTECTED"))
                                        lore.add(line);
                                }
                                ItemMeta meta = stack.getItemMeta();
                                meta.setLore(lore);
                                stack.setItemMeta(meta);
                                player.sendMessage(Core.getMessage("overlordsEssence")
                                        .replace("{enchant}", String.valueOf(name1) + " " + tierS));
                                return;
                            }
                            if (hand.getAmount() == 1) {
                                event.setCursor(null);
                            } else {
                                hand.setAmount(hand.getAmount() - 1);
                            }
                            player.sendMessage(
                                    ChatColor.translateAlternateColorCodes('&', Core.getMessage("destroy"))
                                            .replace("{enchant}", String.valueOf(name1) + " " + tierS));
                        }
                    }
                }
        }
    }
    }
}
