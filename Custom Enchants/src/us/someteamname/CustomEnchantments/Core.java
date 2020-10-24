package us.someteamname.CustomEnchantments;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin implements Listener {
    public static Core instance;

    public static Plugin pl;

    public static String prefix = ChatColor.BLUE.BOLD + "CustomEnchants >>";

    File languageYML;

    static FileConfiguration langConfig;

    File data;

    static FileConfiguration dataconfig;

    public static ArrayList<Enchant> enchants = new ArrayList<>();

    public static boolean done = false;

    public void onEnable() {

    }

    public void onDisable() {
        for (String p : EconManager.getBalanceMap().keySet())
            dataconfig.set("balance." + p, EconManager.getBalanceMap().get(p));
        saveYML(dataconfig, this.data);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("soulenchanter")) {
            if (sender instanceof Player) {
                Player player = (Player)sender;
                String strName = getConfig().getString("BasicBook.Name");
                String strName1 = getConfig().getString("AdvancedBook.Name");
                String strName2 = getConfig().getString("ExtremeBook.Name");
                String strName3 = getConfig().getString("OverlordsEssence.Name");
                Inventory inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GRAY + "Soul Merchant");
                inv.setItem(0,
                        Util.createItemStack(new ItemStack(Material.GLASS_PANE, 1, DyeColor.BLACK.getDyeData()),
                                ChatColor.translateAlternateColorCodes('&', ""), new String[0]));
                inv.setItem(1,
                        Util.createItemStack(new ItemStack(Material.GLASS_PANE, 1, DyeColor.BLACK.getDyeData()),
                                ChatColor.translateAlternateColorCodes('&', ""), new String[0]));
                inv.setItem(2,
                        Util.createItemStack(
                                new ItemStack(Material.GLASS_PANE, 1, DyeColor.PURPLE.getDyeData()),
                                ChatColor.translateAlternateColorCodes('&', ""), new String[0]));
                inv.setItem(3, Util.createItemStack(Material.BOOK, 1,
                        ChatColor.translateAlternateColorCodes('&', strName), translateList("BasicBook")));
                inv.setItem(4, Util.createItemStack(Material.BOOK, 1,
                        ChatColor.translateAlternateColorCodes('&', strName1), translateList("AdvancedBook")));
                inv.setItem(5, Util.createItemStack(Material.BOOK, 1,
                        ChatColor.translateAlternateColorCodes('&', strName2), translateList("ExtremeBook")));
                inv.setItem(6, Util.createItemStack(Material.NETHER_STAR, 1,
                        ChatColor.translateAlternateColorCodes('&', strName3), translateList("OverlordsEssence")));
                inv.setItem(7,
                        Util.createItemStack(new ItemStack(Material.GLASS_PANE, 1, DyeColor.BLACK.getDyeData()),
                                ChatColor.translateAlternateColorCodes('&', ""), new String[0]));
                inv.setItem(8,
                        Util.createItemStack(new ItemStack(Material.GLASS_PANE, 1, DyeColor.BLACK.getDyeData()),
                                ChatColor.translateAlternateColorCodes('&', ""), new String[0]));
                player.openInventory(inv);
            } else {
                sender.sendMessage("Ingame Command Only");
            }
        } else if (label.equalsIgnoreCase("enchantedbook")) {
            if (sender instanceof Player) {
                Player player = (Player)sender;
                if (!player.hasPermission("enchants.book")) {
                    player.sendMessage(getMessage("permDenied"));
                    return false;
                }
            }
            if (args.length == 1) {
                Player player = (Player)sender;
                if (args[0].equalsIgnoreCase("merchant") && player.hasPermission("enchants.merchant"))
                    player.performCommand("npc create &5Soul Merchant");
            }
            if (args.length == 3 &&
                    args[0].equalsIgnoreCase("give")) {
                Player player1 = Bukkit.getPlayer(args[1]);
                String strName00 = getConfig().getString("BasicBook.Tier");
                String strName11 = getConfig().getString("AdvancedBook.Tier");
                String strName22 = getConfig().getString("ExtremeBook.Tier");
                if (player1 != null) {
                    if (args[2].equalsIgnoreCase("essence")) {
                        Util.giveItem(player1, getRandomEnchantBook(4));
                        sender.sendMessage(getMessage("sentEssence").replace("{player}", player1.getName()));
                    }
                    if (args[2].equalsIgnoreCase("basic")) {
                        Util.giveItem(player1, getRandomEnchantBook(1));
                        sender.sendMessage(ChatColor.GREEN + "Successfully gave an " + strName00 +
                                " Enchanted Book to " + player1.getName());
                    } else if (args[2].equalsIgnoreCase("advanced")) {
                        Util.giveItem(player1, getRandomEnchantBook(2));
                        sender.sendMessage(ChatColor.GREEN + "Successfully gave an " + strName11 +
                                " Enchanted Book to " + player1.getName());
                    } else if (args[2].equalsIgnoreCase("extreme")) {
                        Util.giveItem(player1, getRandomEnchantBook(3));
                        sender.sendMessage(ChatColor.GREEN + "Successfully gave an " + strName22 +
                                " Enchanted Book to " + player1.getName());
                    } else if (args[2].equalsIgnoreCase("bottlebasic")) {
                        player1.getInventory().addItem(new ItemStack[] { createSoulBottleBasic() });
                        sender.sendMessage(ChatColor.GREEN + "Successfully gave an Basic Bottle O Souls to " +
                                player1.getName());
                    } else if (args[2].equalsIgnoreCase("bottleadvanced")) {
                        player1.getInventory().addItem(new ItemStack[] { createSoulBottleAdvanced() });
                        sender.sendMessage(ChatColor.GREEN + "Successfully gave an Advanced Bottle O Souls to " +
                                player1.getName());
                    } else if (args[2].equalsIgnoreCase("bottleextreme")) {
                        player1.getInventory().addItem(new ItemStack[] { createSoulBottleExtreme() });
                        sender.sendMessage(ChatColor.GREEN + "Successfully gave an Extreme Bottle O Souls to " +
                                player1.getName());
                    } else {
                        sender.sendMessage(ChatColor.RED +
                                "Usage: /enchantedbook give <player> <Basic, Advanced, Extreme, Essence>");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Player not Found!");
                }
            }
        } else if (label.equalsIgnoreCase("souls")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Usage: /souls <add/remove/set> <player> <amount>");
                sender.sendMessage(ChatColor.RED + "Usage: /souls <bal> <player>");
                return true;
            }
            if (args.length == 1)
                if (sender.hasPermission("enchants.souleconbalself") && args[0].equalsIgnoreCase("bal")) {
                    sender.sendMessage(ChatColor.GREEN + sender.getName() + " has " + ChatColor.RED +
                            EconManager.getBalance(sender.getName()) + ChatColor.GREEN + " souls!");
                } else {
                    sender.sendMessage(ChatColor.RED + "Usage: /souls <add/remove/set> <player> <amount>");
                    sender.sendMessage(ChatColor.RED + "Usage: /souls <bal> <player>");
                    return true;
                }
            if (args.length == 2)
                if (sender.hasPermission("enchants.souleconbal") && args[0].equalsIgnoreCase("bal")) {
                    if (!EconManager.hasAccount(args[1])) {
                        sender.sendMessage(ChatColor.RED + "Error: The player doesn't have an account!");
                        return true;
                    }
                    sender.sendMessage(ChatColor.GREEN + args[1] + " has " + ChatColor.RED +
                            EconManager.getBalance(args[1]) + ChatColor.GREEN + " souls!");
                } else {
                    sender.sendMessage(ChatColor.RED + "Usage: /souls <add/remove/set> <player> <amount>");
                    sender.sendMessage(ChatColor.RED + "Usage: /souls <bal> <player>");
                    return true;
                }
            if (args.length == 3)
                if (sender.hasPermission("enchants.souleconadd") && args[0].equalsIgnoreCase("add")) {
                    if (!EconManager.hasAccount(args[1])) {
                        sender.sendMessage(ChatColor.RED + "Error: The player doesn't have an account!");
                        return true;
                    }
                    double amount = 0.0D;
                    try {
                        amount = Double.parseDouble(args[2]);
                    } catch (Exception e) {
                        sender.sendMessage(ChatColor.RED + "You have to enter a valid number!");
                        return true;
                    }
                    if (EconManager.getBalance(args[1]).doubleValue() + amount <= 0.0D) {
                        sender.sendMessage(ChatColor.RED +
                                "Error: You cannot add a negative number from a player and/or set a players balance to a negative number!");
                        return true;
                    }
                    EconManager.setBalance(args[1], EconManager.getBalance(args[1]).doubleValue() + amount);
                    sender.sendMessage(ChatColor.GREEN + "You have given " + amount + " souls to " + args[1] + "!");
                    Player p = Bukkit.getPlayer(args[1]);
                    p.sendMessage(
                            ChatColor.GREEN + "You have received " + amount + "souls from " + sender.getName() + "!");
                } else if (sender.hasPermission("enchants.souleconremove") && args[0].equalsIgnoreCase("remove")) {
                    if (!EconManager.hasAccount(args[1])) {
                        sender.sendMessage(ChatColor.RED + "Error: The player doesn't have an account!");
                        return true;
                    }
                    double amount = 0.0D;
                    try {
                        amount = Double.parseDouble(args[2]);
                    } catch (Exception e) {
                        sender.sendMessage(ChatColor.RED + "You have to enter a valid number!");
                        return true;
                    }
                    if (EconManager.getBalance(args[1]).doubleValue() - amount <= 0.0D || amount <= 0.0D) {
                        sender.sendMessage(ChatColor.RED +
                                "Error: You cannot remove a negative number from a player and/or set a players balance to a negative number!");
                        return true;
                    }
                    EconManager.setBalance(args[1], EconManager.getBalance(args[1]).doubleValue() - amount);
                    sender.sendMessage(
                            ChatColor.GREEN + "You have removed " + amount + " of souls from " + args[1] + "!");
                    Player p = Bukkit.getPlayer(args[1]);
                    p.sendMessage(ChatColor.GREEN + "");
                } else if (sender.hasPermission("enchants.souleconpay") && args[0].equalsIgnoreCase("pay")) {
                    if (!EconManager.hasAccount(args[1]) || !EconManager.hasAccount(sender.getName())) {
                        sender.sendMessage(ChatColor.RED + "Error: The player doesn't have an account!");
                        return true;
                    }
                    double amount = 0.0D;
                    try {
                        amount = Double.parseDouble(args[2]);
                    } catch (Exception e) {
                        sender.sendMessage(ChatColor.RED + "You have to enter a valid number!");
                        return true;
                    }
                    if (amount <= 0.0D || sender.getName().equals(args[1])) {
                        sender.sendMessage(
                                ChatColor.RED + "Error: You cannot pay a player a negative number or yourself!");
                        return true;
                    }
                    EconManager.setBalance(sender.getName(), EconManager.getBalance(args[1]).doubleValue() - amount);
                    sender.sendMessage(
                            ChatColor.GREEN + "You have successfully paid " + amount + " souls to " + args[1] + "!");
                    Player p = Bukkit.getPlayer(args[1]);
                    p.sendMessage(ChatColor.GREEN + " souls have been paid " + amount + "to" + sender.getName() + "!");
                } else if (sender.hasPermission("enchants.souleconset") && args[0].equalsIgnoreCase("set")) {
                    if (!EconManager.hasAccount(args[1])) {
                        sender.sendMessage(ChatColor.RED + "Error: The player doesn't have an account!");
                        return true;
                    }
                    double amount = 0.0D;
                    try {
                        amount = Double.parseDouble(args[2]);
                    } catch (Exception e) {
                        sender.sendMessage(ChatColor.RED + "You have to enter a valid number!");
                        return true;
                    }
                    if (amount <= 0.0D) {
                        sender.sendMessage(
                                ChatColor.RED + "Error: You cannot set a player's balance to a negative number! " +
                                        args[1] + "'s balance was set to 0!");
                        EconManager.setBalance(args[1], 1.0D);
                        Player player = Bukkit.getPlayer(args[1]);
                        player.sendMessage(ChatColor.GREEN + "Your balance has been set to 1 by " + sender.getName() + "!");
                        return true;
                    }
                    EconManager.setBalance(args[1], amount);
                    sender.sendMessage(
                            ChatColor.GREEN + "You have set " + args[1] + "'s soul balance to " + amount + "!");
                    Player p = Bukkit.getPlayer(args[1]);
                    p.sendMessage(ChatColor.GREEN + "Your balance has been set to " + amount + " by " + sender.getName() +
                            "!");
                } else {
                    sender.sendMessage(ChatColor.RED + "Usage: /souls <add/remove/set/pay> <player> <amount>");
                    sender.sendMessage(ChatColor.RED + "Usage: /souls <bal> <player>");
                    return true;
                }
        }
        return true;
    }

    public static Enchant getRandomEnchant(int tier) {
        Enchant ench = enchants.get(Util.randInt(0, enchants.size() - 1));
        if (tier == ench.maxLevel)
            return ench;
        return getRandomEnchant(tier);
    }

    public static ItemStack getEnchantBook(Enchant ench, int tier) {
        return getEnchantBook(ench, tier, Util.randInt(1, 100), Util.randInt(1, 100));
    }

    public static ItemStack getEnchantBook(Enchant ench, int tier, int success, int destroy) {
        if (tier > 0) {
            ChatColor color = ChatColor.GREEN;
            if (tier == 2) {
                color = ChatColor.YELLOW;
            } else if (tier == 3) {
                color = ChatColor.RED;
            }
            int maxLevel = Util.randInt(1, ench.maxLevel2);
            ItemStack itemStack = Util.createItemStack(Material.BOOK, 1,
                    color + ench.name + " " + getNumeral(maxLevel), new String[] { ChatColor.GRAY + ench.desc,
                            ChatColor.GRAY + ench.desc2, ChatColor.GRAY + ench.desc3, "",
                            ChatColor.GREEN + "Success Rate: " + ChatColor.GRAY + success + "%",
                            ChatColor.RED + "Destroy Rate: " + ChatColor.GRAY + destroy + "%", "",
                            ChatColor.GRAY + "Drag and drop on item to enchant", ChatColor.GRAY + ench.getELocName() });
            return itemStack;
        }
        ItemStack stack = Util.createItemStack(Material.BOOK, 1,
                ChatColor.YELLOW + ench.name + " " + ench.getNumeral(tier), new String[] { ench.desc, ench.desc2, ench.desc3,
                        ChatColor.DARK_GRAY + "" + ChatColor.AQUA + ench.getELocName(),
                        ChatColor.GOLD + "Drag and drop this book on item to enchant." });
        return stack;
    }

    public ItemStack getRandomEnchantBook(int tier) {
        String strName = getConfig().getString("BasicBook.Name");
        String strName1 = getConfig().getString("AdvancedBook.Name");
        String strName2 = getConfig().getString("ExtremeBook.Name");
        String strName3 = getConfig().getString("OverlordsEssence.Name");
        if (tier == 1) {
            ItemStack itemStack = Util.createItemStack(Material.BOOK, 1,
                    ChatColor.translateAlternateColorCodes('&', strName), translateList("BasicBook"));
            return itemStack;
        }
        if (tier == 2) {
            ItemStack itemStack = Util.createItemStack(Material.BOOK, 1,
                    ChatColor.translateAlternateColorCodes('&', strName1), translateList("AdvancedBook"));
            return itemStack;
        }
        if (tier == 3) {
            ItemStack itemStack = Util.createItemStack(Material.BOOK, 1,
                    ChatColor.translateAlternateColorCodes('&', strName2), translateList("ExtremeBook"));
            return itemStack;
        }
        if (tier == 4) {
            ItemStack itemStack = Util.createItemStack(Material.NETHER_STAR, 1,
                    ChatColor.translateAlternateColorCodes('&', strName3), translateList("OverlordsEssence"));
            return itemStack;
        }
        ItemStack stack = Util.createItemStack(Material.BOOK, 1, ChatColor.translateAlternateColorCodes('&', strName),
                translateList("BasicBook"));
        return stack;
    }

    private void checkLang(String key, String value) {
        if (!langConfig.contains(key))
            langConfig.set(key, value);
    }

    public void saveYML(FileConfiguration ymlConfig, File ymlFile) {
        try {
            ymlConfig.save(ymlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getMessage(String key) {
        return ChatColor.translateAlternateColorCodes('&', langConfig.getString(key));
    }

    public ArrayList<String> translateList(String tier) {
        ArrayList<String> lore = new ArrayList<>();
        for (int i = 0; i < getConfig().getStringList(String.valueOf(tier) + ".Lore").size(); i++)
            lore.add(ChatColor.translateAlternateColorCodes('&',
                    getConfig().getStringList(String.valueOf(tier) + ".Lore").get(i)));
        return lore;
    }

    public ItemStack createSoulBottleBasic() {
        ItemStack localItemStack = Util.createItemStack(Material.POTION, 1,
                getConfig().getString("BasicBottle.Name"), translateList("BasicBottle.Lore"));
        return localItemStack;
    }

    public ItemStack createSoulBottleAdvanced() {
        ItemStack localItemStack = Util.createItemStack(Material.POTION, 1,
                getConfig().getString("AdvancedBottle.Name"), translateList("AdvancedBottle.Lore"));
        return localItemStack;
    }

    public ItemStack createSoulBottleExtreme() {
        ItemStack localItemStack = Util.createItemStack(Material.POTION, 1,
                getConfig().getString("ExtremeBottle.Name"), translateList("ExtremeBottle.Lore"));
        return localItemStack;
    }

    public static String getNumeral(int number) {
        switch (number) {
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
            case 7:
                return "VII";
            case 8:
                return "VIII";
            case 9:
                return "IX";
            case 10:
                return "X";
        }
        return "null";
    }
}
