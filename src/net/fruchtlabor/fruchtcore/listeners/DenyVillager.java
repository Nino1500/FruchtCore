package net.fruchtlabor.fruchtcore.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class DenyVillager implements Listener {
    @EventHandler
    public void onClick(PlayerInteractEntityEvent event){
        if(!event.getRightClicked().getWorld().getName().equalsIgnoreCase("world")){
            if(event.getRightClicked().getType().equals(EntityType.VILLAGER)){
                event.getPlayer().sendMessage(ChatColor.GOLD+"Mit Villagern wird am spawn getraded!");
                event.setCancelled(true);
            }
        }
    }
}
