package us.someteamname.CustomEnchantments;

import java.util.ArrayList;
import org.bukkit.Material;

public enum Eloc {
    SWORDS("Sword", new Material[] { Material.WOODEN_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLDEN_SWORD, Material.DIAMOND_SWORD }),
    BOW("Bow", new Material[] { Material.BOW }),
    AXES("Axe", new Material[] { Material.WOODEN_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLDEN_AXE, Material.DIAMOND_AXE }),
    BOOTS("Boot", new Material[] { Material.LEATHER_BOOTS, Material.IRON_BOOTS, Material.GOLDEN_BOOTS, Material.DIAMOND_BOOTS }),
    LEGGINGS("Leggings", new Material[] { Material.LEATHER_LEGGINGS, Material.IRON_LEGGINGS, Material.GOLDEN_LEGGINGS, Material.DIAMOND_LEGGINGS }),
    CHESTPLATE("Chestplate", new Material[] { Material.LEATHER_CHESTPLATE, Material.IRON_CHESTPLATE, Material.GOLDEN_CHESTPLATE, Material.DIAMOND_CHESTPLATE }),
    HELMET("Helmet", new Material[] { Material.LEATHER_HELMET, Material.IRON_HELMET, Material.GOLDEN_HELMET, Material.DIAMOND_HELMET }),
    MASK("Mask", new Material[] { Material.PLAYER_HEAD }),
    SHIELD("Sheid",new Material[] {Material.SHIELD});


    public ArrayList<Material> valid = new ArrayList<>();

    public String name;

    Eloc(String name, Material... materials) {
        this.name = name;
        byte b;
        int i;
        Material[] arrayOfMaterial;
        for (i = (arrayOfMaterial = materials).length, b = 0; b < i; ) {
            Material mat = arrayOfMaterial[b];
            this.valid.add(mat);
            b++;
        }
    }
}

