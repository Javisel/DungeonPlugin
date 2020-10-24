package us.someteamname.CustomEnchantments;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Enchant {
    public String name;

    public String desc;

    public String desc2;

    public String desc3;

    public int maxLevel;

    public ArrayList<Eloc> locations = new ArrayList<>();

    public int maxLevel2;

    public Enchant(String name, String desc, String desc1, String desc2, int maxLevel, int maxLevel2, Eloc... eLocs) {
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        this.desc = ChatColor.translateAlternateColorCodes('&', desc);
        this.desc2 = ChatColor.translateAlternateColorCodes('&', desc1);
        this.desc3 = ChatColor.translateAlternateColorCodes('&', desc2);
        this.maxLevel = maxLevel;
        this.maxLevel2 = maxLevel2;
        byte b;
        int i;
        Eloc[] arrayOfEloc;
        for (i = (arrayOfEloc = eLocs).length, b = 0; b < i; ) {
            Eloc loc = arrayOfEloc[b];
            this.locations.add(loc);
            b++;
        }
    }

    public String getELocName() {
        if (this.locations.size() == 4 && this.locations.contains(Eloc.BOOTS) && this.locations.contains(Eloc.LEGGINGS) &&
                this.locations.contains(Eloc.CHESTPLATE) && this.locations.contains(Eloc.BOOTS) && this.locations.contains(Eloc.SHIELD))
            return "Any Armor";
        if (this.locations.size() > 1) {
            String enchants = "";
            for (Eloc loc : this.locations)
                enchants = String.valueOf(enchants) + ", " + loc.name;
            char[] chars = enchants.toCharArray();
            chars[0] = ' ';
            enchants = "";
            for (int i = 2; i < chars.length; i++)
                enchants = String.valueOf(enchants) + chars[i];
            return enchants;
        }
        return ((Eloc)this.locations.get(0)).name;
    }

    public String getNumeral(int tier) {
        switch (tier) {
            case 1:
                return "I";
            case 2:
                return "II";
            case 3:
                return "III";
            case 4:
                return "IV";
            case 5:
                return "V";
            case 6:
                return "VI";
        }
        return "";
    }

    public static boolean hasEnchant(String enchant, ItemStack stack) {
        if (stack != null && stack.getType() != Material.AIR &&
                stack.hasItemMeta() && stack.getItemMeta().hasLore()) {
            ItemMeta meta = stack.getItemMeta();
            for (String line : meta.getLore()) {
                line = ChatColor.stripColor(line);
                if (line.equalsIgnoreCase(String.valueOf(enchant) + " I"))
                    return true;
                if (line.equalsIgnoreCase(String.valueOf(enchant) + " II"))
                    return true;
                if (line.equalsIgnoreCase(String.valueOf(enchant) + " III"))
                    return true;
                if (line.equalsIgnoreCase(String.valueOf(enchant) + " IV"))
                    return true;
                if (line.equalsIgnoreCase(String.valueOf(enchant) + " V"))
                    return true;
                if (line.equalsIgnoreCase(String.valueOf(enchant) + " VI"))
                    return true;
            }
        }
        return false;
    }

    public static int getEnchantTier(String enchant, ItemStack stack) {
        if (stack != null && stack.getType() != Material.AIR &&
                stack.hasItemMeta() && stack.getItemMeta().hasLore()) {
            ItemMeta meta = stack.getItemMeta();
            for (String line : meta.getLore()) {
                line = ChatColor.stripColor(line);
                if (line.equalsIgnoreCase(String.valueOf(enchant) + " I"))
                    return 1;
                if (line.equalsIgnoreCase(String.valueOf(enchant) + " II"))
                    return 2;
                if (line.equalsIgnoreCase(String.valueOf(enchant) + " III"))
                    return 3;
                if (line.equalsIgnoreCase(String.valueOf(enchant) + " IV"))
                    return 4;
                if (line.equalsIgnoreCase(String.valueOf(enchant) + " V"))
                    return 5;
                if (line.equalsIgnoreCase(String.valueOf(enchant) + " VI"))
                    return 6;
            }
        }
        return 0;
    }
}
