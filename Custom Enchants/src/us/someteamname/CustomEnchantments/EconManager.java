package us.someteamname.CustomEnchantments;

import java.util.HashMap;

public class EconManager {
    private static Core plugin;

    public EconManager(Core instance) {
        plugin = instance;
    }

    public static HashMap<String, Double> bal = new HashMap<>();

    public static void setBalance(String player, double amount) {
        bal.put(player, Double.valueOf(amount));
    }

    public static Double getBalance(String player) {
        return bal.get(player);
    }

    public static boolean hasAccount(String player) {
        return bal.containsKey(player);
    }

    public static HashMap<String, Double> getBalanceMap() {
        return bal;
    }

    public static Core getPlugin() {
        return plugin;
    }
}