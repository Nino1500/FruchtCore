package net.fruchtlabor.fruchtcore.listeners;

import net.fruchtlabor.fruchtcore.FruchtCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckOverFishRadius implements Listener {

    HashMap<List<Location>, Integer> map = new HashMap<>();

    @EventHandler
    public void onFish_check(PlayerFishEvent event){

        Location location = event.getHook().getLocation().getBlock().getLocation();

        if(event.getState() == PlayerFishEvent.State.CAUGHT_FISH || event.getState() == PlayerFishEvent.State.CAUGHT_ENTITY){
            if(checkifExists(location)){
                event.getPlayer().sendMessage(ChatColor.DARK_GREEN+"< Gebiet überfischt >");
                event.setCancelled(true);
            }
        }
        if(event.getState() == PlayerFishEvent.State.BITE){
            List<Location> key = getRadius(location, 1);
            if(map.containsKey(key)){
                map.put(key, map.get(key)+1);
                if(map.get(key) >= 150){
                    FruchtCore.overfishradius.put(key, key.get(0).getWorld().getName());
                    map.remove(key);
                }
            }else{
                map.put(key, 1);
            }
        }

    }

    public boolean checkifExists(Location location){
        HashMap<List<Location>, String> map = FruchtCore.overfishradius;
        for (Map.Entry<List<Location>, String> entry : map.entrySet()){
            for (Location l : entry.getKey()){
                //DEBUG : Bukkit.getConsoleSender().sendMessage(l.toString()+" - "+location.toString()+"\n");
                if(l.toString().equalsIgnoreCase(location.toString())) return true;
            }
        }
        return false;
    }

    public List<Location> getRadius(Location location, int radius){
        List<Location> loc_rad = new ArrayList<>();
        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++){
            for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++){
                if(location.getWorld() == null) return null;
                loc_rad.add(location.getWorld().getBlockAt(x,location.getBlockY(),z).getLocation());
            }
        }
        return loc_rad;
    }
}
/*
public class CheckOverFishing implements Listener {

    HashMap<Location, Integer> map = new HashMap<>();

    @EventHandler
    public void check(PlayerFishEvent event){

        Location location = event.getHook().getLocation().getBlock().getLocation();

        if(FruchtCore.overfishmap.containsKey(location) && (event.getState() == PlayerFishEvent.State.CAUGHT_FISH || event.getState() == PlayerFishEvent.State.CAUGHT_ENTITY)){
            event.getPlayer().sendMessage(ChatColor.DARK_GREEN+"Dieses Gebiet ist überfischt!");
            event.setCancelled(true);
        }
        if(event.getState() == PlayerFishEvent.State.BITE){
            if(map.containsKey(location)){
                map.put(location, map.get(location)+1);
                if(map.get(location) >= 150){
                    if(location.getWorld() != null){
                        FruchtCore.overfishmap.put(location, location.getWorld().getName());
                        map.remove(location);
                    }
                }
            }else{
                map.put(location, 1);
            }
        }
    }
}
 */
