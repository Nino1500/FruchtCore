package net.fruchtlabor.fruchtcore.perks;

import com.gamingmesh.jobs.api.JobsExpGainEvent;
import net.fruchtlabor.fruchtcore.FruchtCore;
import net.fruchtlabor.fruchtcore.jobsStuff.ContentItem;
import net.fruchtlabor.fruchtcore.jobsStuff.Perk;
import net.fruchtlabor.fruchtcore.jobsStuff.PerkManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Abenteurer_breaking implements Listener {

    @EventHandler
    public void onBreak(JobsExpGainEvent event){
        if(event.getBlock() == null){
            return;
        }
        Player player = event.getPlayer().getPlayer();

        if(FruchtCore.playerHasJob("Abenteurer", player) && player != null){
            PerkManager perkManager = new PerkManager();
            List<Perk> list = perkManager.getAbenteurerPerks();

            for (Perk perk : list){

                if(perk.getLevel() == 10 && player.hasPermission(perk.getPerm())){
                    if(event.getBlock().getType() == Material.SAND && chance(0.05)){
                        event.getBlock().setType(Material.AIR);
                        event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.GLASS));
                        (event.getBlock().getWorld().spawn(event.getBlock().getLocation(), ExperienceOrb.class)).setExperience(4);
                    }
                }
                if(perk.getLevel() == 25 && player.hasPermission(perk.getPerm()) && chance(0.01)){
                    ArrayList<ContentItem> items = perk.getList(FruchtCore.playerlevel("Abenteurer", player), FruchtCore.playerNumOfJobs(player));
                    for (ContentItem item : items) {
                        if (item.getItem().getType() == event.getBlock().getType()) {
                            if (player.hasPermission("perks.Abenteurer.50")) {
                                event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), extraOtherStuff());
                            } else {
                                event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), extraDirtStuff());
                            }
                        }
                    }
                }
                if(perk.getLevel() == 50 && player.hasPermission(perk.getPerm())){
                    if(chance(0.01)){
                        if(chance(0.2)){
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cr give to " + player + " abenteurercrate 1");
                        }
                    }
                }
                if(perk.getLevel() == 75 && player.hasPermission(perk.getPerm()) && chance(0.05)){
                    for (ItemStack itemStack : event.getBlock().getDrops()){
                        itemStack.setAmount(itemStack.getAmount()*2);
                    }
                }
            }
        }
    }

    public ItemStack extraDirtStuff(){
        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.DIAMOND));
        items.add(new ItemStack(Material.GLOWSTONE_DUST));
        items.add(new ItemStack(Material.GLOWSTONE_DUST));
        items.add(new ItemStack(Material.REDSTONE));
        items.add(new ItemStack(Material.REDSTONE));
        items.add(new ItemStack(Material.EMERALD));
        items.add(new ItemStack(Material.GOLD_NUGGET));
        items.add(new ItemStack(Material.IRON_NUGGET));
        items.add(new ItemStack(Material.IRON_NUGGET));
        items.add(new ItemStack(Material.BONE));
        items.add(new ItemStack(Material.BONE));
        items.add(new ItemStack(Material.GUNPOWDER));
        items.add(new ItemStack(Material.EGG));
        items.add(new ItemStack(Material.EGG));
        items.add(new ItemStack(Material.SLIME_BALL));
        items.add(new ItemStack(Material.APPLE));
        items.add(new ItemStack(Material.APPLE));
        items.add(new ItemStack(Material.APPLE));
        items.add(new ItemStack(Material.COBWEB));
        items.add(new ItemStack(Material.GOLD_NUGGET));
        items.add(new ItemStack(Material.STICK));
        items.add(new ItemStack(Material.STICK));
        items.add(new ItemStack(Material.STICK));
        int idx = new Random().nextInt(items.size());
        return items.get(idx);
    }
    public ItemStack extraOtherStuff(){
        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.DIAMOND));
        items.add(new ItemStack(Material.EMERALD));
        items.add(new ItemStack(Material.GUNPOWDER));
        items.add(new ItemStack(Material.BONE));
        items.add(new ItemStack(Material.EGG));
        items.add(new ItemStack(Material.EGG));
        items.add(new ItemStack(Material.STICK));
        items.add(new ItemStack(Material.SLIME_BALL));
        items.add(new ItemStack(Material.SLIME_BALL));
        items.add(new ItemStack(Material.REDSTONE));
        items.add(new ItemStack(Material.QUARTZ));
        items.add(new ItemStack(Material.STICK));
        items.add(new ItemStack(Material.CLAY_BALL));
        items.add(new ItemStack(Material.GRASS_BLOCK));
        items.add(new ItemStack(Material.GOLD_NUGGET));
        items.add(new ItemStack(Material.GOLD_INGOT));
        items.add(new ItemStack(Material.IRON_NUGGET));
        items.add(new ItemStack(Material.IRON_INGOT));
        items.add(new ItemStack(Material.GLOWSTONE_DUST));
        items.add(new ItemStack(Material.GLOWSTONE_DUST));
        int idx = new Random().nextInt(items.size());
        return items.get(idx);
    }

    public boolean chance(double chance){
        Random random = new Random();
        if(random.nextDouble() < chance){
            return true;
        }
        return false;
    }
}
