package com.minecraftpluginteam.dungeonplugin.common;
import com.minecraftpluginteam.dungeonplugin.PluginMain;
import com.onarandombox.MultiverseCore.MultiverseCore;
import net.minecraft.server.v1_16_R2.*;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.block.EndGateway;
import org.bukkit.block.TileState;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityEnterBlockEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;


public class EventHandler implements Listener {






    //Handles player interaction with End Gateways, creating new worlds based on data stored in the gateway.
    @org.bukkit.event.EventHandler
    public static void onClickEndGateway(PlayerInteractEvent e) {



        if (e.getClickedBlock() != null  && e.getClickedBlock().getBlockData().getMaterial() == Material.END_GATEWAY && e.getItem() !=null && e.getItem().getType() == Material.DIAMOND) {





            if (e.getClickedBlock().getState() instanceof  TileState) {
                e.getPlayer().getServer().getConsoleSender().sendMessage("Is tile state");


                TileState tileState = (TileState) e.getClickedBlock().getState();


                NamespacedKey namespacedKey = new NamespacedKey(e.getPlayer().getServer().getPluginManager().getPlugin("DungeonPlugin"), "dungeonplugin");
                tileState.getPersistentDataContainer().set(namespacedKey, PersistentDataType.STRING, "STARLIGHTCROSS");



                tileState.update();
                e.getPlayer().getServer().getConsoleSender().sendMessage("Key: " + tileState.getPersistentDataContainer().get(namespacedKey, PersistentDataType.STRING));
            }
        }


    }







}
