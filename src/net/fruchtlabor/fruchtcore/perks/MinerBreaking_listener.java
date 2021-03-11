package net.fruchtlabor.fruchtcore.perks;

import com.gamingmesh.jobs.api.JobsExpGainEvent;
import net.fruchtlabor.fruchtcore.FruchtCore;
import net.fruchtlabor.fruchtcore.jobsStuff.Perk;
import net.fruchtlabor.fruchtcore.jobsStuff.PerkManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class MinerBreaking_listener implements Listener {
    @EventHandler
    public void onbreakExp(JobsExpGainEvent event){ //no blockbreak event cause dupe placing which is prevented
        // by the jobsreborn plugin itself for a period of time
        if(event.getBlock() == null){
            return;
        }
        Player player = event.getPlayer().getPlayer();

        if(FruchtCore.playerHasJob("Minenarbeiter", player) && event.getExp() > 0.0 && player != null){ //only activated if exp is gained

            PerkManager perkManager = new PerkManager();
            List<Perk> list = perkManager.getMinerPerks();

            for (Perk perk : list){

                if(perk.getLevel() == 10 && player.hasPermission("Perks.Minenarbeiter.10")){ //works
                    if(chance(0.08) && event.getBlock().getType() == Material.STONE){
                        event.getBlock().setType(Material.AIR);
                        player.getLocation().getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.STONE));
                    }
                }
                if(perk.getLevel() == 25 && player.hasPermission(perk.getPerm()) && !player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH)){
                    if(player.hasPermission("perks.Minenarbeiter.75") && chance(0.04)){
                        for (ItemStack itemStack : event.getBlock().getDrops()){
                            itemStack.setAmount(itemStack.getAmount()*2);
                        }
                    }
                    if (event.getBlock().getType() == Material.IRON_ORE){
                        event.getBlock().setType(Material.AIR);
                        int am = 1;
                        if(player.hasPermission("perks.Minenarbeiter.75") && chance(0.1)){
                            am = 2;
                        }
                        event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT, am));
                    }
                    if(event.getBlock().getType() == Material.GOLD_ORE || event.getBlock().getType() == Material.NETHER_GOLD_ORE){
                        event.getBlock().setType(Material.AIR);
                        int am = 1;
                        if(player.hasPermission("perks.Minenarbeiter.75") && chance(0.08)){
                            am = 2;
                        }
                        event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT, am));
                    }
                }
                if(perk.getLevel() == 50 && player.hasPermission(perk.getPerm())){
                    List<Material> ores = isOre();
                    if(ores.contains(event.getBlock().getType())){
                        if(chance(0.05)){
                            event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), getRandomExtraOre());
                        }
                    }
                    if(chance(0.01)){
                        if(chance(0.2)){
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cr give to " + player + " minercrate 1");
                        }
                    }
                }
                if(perk.getLevel() == 100){
                    //Nothing yet
                }
            }
        }

    }

    public List<Material> isOre(){
        List<Material> list = new ArrayList<>();
        list.add(Material.COAL_ORE);
        list.add(Material.DIAMOND_ORE);
        list.add(Material.EMERALD_ORE);
        list.add(Material.GOLD_ORE);
        list.add(Material.IRON_ORE);
        list.add(Material.LAPIS_ORE);
        list.add(Material.REDSTONE_ORE);
        list.add(Material.NETHER_GOLD_ORE);
        return list;
    }

    public ItemStack getRandomExtraOre(){
        int nr = new Random().nextInt(2);
        while (nr == 0){
            nr = new Random().nextInt(2);
        }
        List<Material> ores = isOre();
        int idx = new Random().nextInt(ores.size());
        ItemStack itemStack = new ItemStack(ores.get(idx), nr);
        if(itemStack.getType() == Material.GOLD_ORE || itemStack.getType() == Material.NETHER_GOLD_ORE){
            itemStack.setType(Material.GOLD_INGOT);
        }
        if(itemStack.getType() == Material.IRON_ORE){
            itemStack.setType(Material.IRON_INGOT);
        }
        if(itemStack.getType() == Material.AIR){
            itemStack = getRandomExtraOre();
        }
        return itemStack;
    }

    public boolean chance(double chance){
        Random random = new Random();
        if(random.nextDouble() < chance){
            return true;
        }
        return false;
    }
}
