package us.someteamname.CustomEnchantments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Util {
    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt(max - min + 1) + min;
        return randomNum;
    }

    public static double randDouble(double min, double max) {
        Random r = new Random();
        return min + (max - min) * r.nextDouble();
    }

    public static ItemStack createItemStack(Material type, int amt, String name) {
        ItemStack stack = new ItemStack(type, amt);
        ItemMeta im = stack.getItemMeta();
        im.setDisplayName(name);
        stack.setItemMeta(im);
        return stack;
    }

    public static ItemStack createItemStack(Material type, int amt, String name, String... lores) {
        ItemStack stack = new ItemStack(type, amt);
        ItemMeta im = stack.getItemMeta();
        im.setDisplayName(name);
        ArrayList<String> lore = new ArrayList<>();
        byte b;
        int i;
        String[] arrayOfString;
        for (i = (arrayOfString = lores).length, b = 0; b < i; ) {
            String str = arrayOfString[b];
            lore.add(str);
            b++;
        }
        im.setLore(lore);
        stack.setItemMeta(im);
        return stack;
    }

    public static List<Arrow> getNearbyArrows(Player player, int distance) {
        List<Arrow> res = new ArrayList<>();
        int d2 = distance * distance;
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof Arrow) {
                    Arrow p = (Arrow)entity;
                    if (p.getWorld() == player.getWorld() &&
                            p.getLocation().distanceSquared(player.getLocation()) <= d2)
                        res.add(p);
                }
            }
        }
        return res;
    }

    public static ItemStack createItemStack(ItemStack stack, String name, String... lores) {
        ItemMeta im = stack.getItemMeta();
        im.setDisplayName(name);
        ArrayList<String> lore = new ArrayList<>();
        byte b;
        int i;
        String[] arrayOfString;
        for (i = (arrayOfString = lores).length, b = 0; b < i; ) {
            String str = arrayOfString[b];
            lore.add(str);
            b++;
        }
        im.setLore(lore);
        stack.setItemMeta(im);
        return stack;
    }

    public static ItemStack createItemStack(Material type, int amt, String name, ArrayList<String> lore) {
        ItemStack stack = new ItemStack(type, amt);
        ItemMeta im = stack.getItemMeta();
        im.setDisplayName(name);
        im.setLore(lore);
        stack.setItemMeta(im);
        return stack;
    }

    public static void giveItem(Player player, ItemStack stack) {
        HashMap<Integer, ItemStack> left = player.getInventory().addItem(new ItemStack[] { stack });
        for (Map.Entry<Integer, ItemStack> entry : left.entrySet())
            player.getWorld().dropItemNaturally(player.getLocation(), entry.getValue());
    }
}
