package net.fruchtlabor.fruchtcore.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class DenyJobCommand implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event){
        if(event.getMessage().equalsIgnoreCase("/jobs") || event.getMessage().equalsIgnoreCase("/jobs ")){
            event.setCancelled(true);
        }
    }

}
