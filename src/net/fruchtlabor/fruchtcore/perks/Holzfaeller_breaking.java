package net.fruchtlabor.fruchtcore.perks;

import com.gamingmesh.jobs.api.JobsExpGainEvent;
import com.gamingmesh.jobs.api.JobsPaymentEvent;
import net.fruchtlabor.fruchtcore.FruchtCore;
import net.fruchtlabor.fruchtcore.jobsStuff.Perk;
import net.fruchtlabor.fruchtcore.jobsStuff.PerkManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class Holzfaeller_breaking implements Listener {

    @EventHandler
    public void onBreak(JobsExpGainEvent event){
        if(event.getBlock() == null){
            return;
        }
        Player player = event.getPlayer().getPlayer();

        if(FruchtCore.playerHasJob("Holzfaeller", player) && trees().contains(event.getBlock().getType()) && player != null && event.getExp() > 0.0){

            PerkManager perkManager = new PerkManager();
            List<Perk> list = perkManager.getHolzFaePerks();

            for (Perk perk : list) {
                if (perk.getLevel() == 10 && player.hasPermission(perk.getPerm())) {
                    double chance = 0.05;
                    if(player.hasPermission("perks.Holzfaeller.50")){
                        chance = 0.1;
                    }
                    if(trees().contains(event.getBlock().getType())){
                        if(chance(chance)){
                            player.getLocation().getWorld().dropItemNaturally(player.getLocation(), new ItemStack(event.getBlock().getType()));
                            (event.getBlock().getWorld().spawn(event.getBlock().getLocation(), ExperienceOrb.class)).setExperience(4);
                        }
                    }
                }
                if(perk.getLevel() == 50 && player.hasPermission(perk.getPerm())){
                    if(chance(0.01)){
                        if(chance(0.5)){
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cr give to " + player + " holzfaellercrate 1");
                        }
                    }
                }
                if(perk.getLevel() == 75 && player.hasPermission(perk.getPerm())){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 700, 0));
                }
                if(perk.getLevel() == 100 && player.hasPermission(perk.getPerm())){
                    if(trees().contains(event.getBlock().getType()) && !player.isSneaking() && chance(0.1)){
                        Set<Block> treeBlocks = getTree(event.getBlock(), trees());
                        for (Block treeBlock : treeBlocks){
                            treeBlock.breakNaturally();
                        }
                    }
                }
            }
        }
    }

    public List<Material> trees(){
        List<Material> list = new ArrayList<>();
        list.add(Material.ACACIA_LOG);
        list.add(Material.BIRCH_LOG);
        list.add(Material.DARK_OAK_LOG);
        list.add(Material.JUNGLE_LOG);
        list.add(Material.OAK_LOG);
        list.add(Material.SPRUCE_LOG);
        list.add(Material.ACACIA_WOOD);
        list.add(Material.OAK_WOOD);
        list.add(Material.BIRCH_WOOD);
        list.add(Material.DARK_OAK_WOOD);
        list.add(Material.JUNGLE_WOOD);
        list.add(Material.SPRUCE_WOOD);
        return list;
    }

    public boolean chance(double chance){
        Random random = new Random();
        if(random.nextDouble() < chance){
            return true;
        }
        return false;
    }

    public Set<Block> getTree(Block start, List<Material> allowedMaterials) {
        return getNearbyBlocks(start, allowedMaterials, new HashSet<Block>());
    }

    private Set<Block> getNearbyBlocks(Block start, List<Material> allowedMaterials, HashSet<Block> blocks) {
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                for (int z = -1; z < 2; z++) {
                    Block block = start.getLocation().clone().add(x, y, z).getBlock();
                    if (block != null && !blocks.contains(block) && allowedMaterials.contains(block.getType())) {
                        blocks.add(block);
                        blocks.addAll(getNearbyBlocks(block, allowedMaterials, blocks));
                    }
                }
            }
        }
        return blocks;
    }

}
