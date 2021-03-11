package net.fruchtlabor.fruchtcore.perks;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.api.JobsExpGainEvent;
import com.gamingmesh.jobs.container.JobProgression;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.arcaniax.hdb.enums.CategoryEnum;
import me.arcaniax.hdb.object.head.Head;
import net.fruchtlabor.fruchtcore.FruchtCore;
import net.fruchtlabor.fruchtcore.jobsStuff.ContentItem;
import net.fruchtlabor.fruchtcore.jobsStuff.Perk;
import net.fruchtlabor.fruchtcore.jobsStuff.PerkManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class FarmerBreakAndKill implements Listener {

    @EventHandler
    public void onBreak(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(FruchtCore.playerHasJob("Farmer", player) &&
                isHoe(event.getPlayer().getInventory().getItemInMainHand().getType()) && event.getAction().equals(
                Action.RIGHT_CLICK_BLOCK
        )){
            PerkManager perkManager = new PerkManager();
            List<Perk> list = perkManager.getFarmerPerks();
            for (Perk perk : list){
                if(perk.getLevel() == 10 && player.hasPermission(perk.getPerm())){
                    ArrayList<ContentItem> items = perk.getList(FruchtCore.playerlevel("Farmer", player), FruchtCore.playerNumOfJobs(player));
                    Block block = event.getClickedBlock();
                    if(block == null){
                        return;
                    }
                    BlockData blockData = block.getBlockData();
                    if(blockData instanceof Ageable){
                        Ageable crop = (Ageable) block.getBlockData();
                        if(crop.getAge() == crop.getMaximumAge()){
                            for (ContentItem item : items) {
                                if(item.getItem().getType() == block.getType()){
                                    durability(player); // damage the hoe
                                    Location location = block.getLocation();
                                    if(player.hasPermission("perks.Farmer.10")){
                                        for (ItemStack itemStack : block.getDrops()){
                                            location.getWorld().dropItemNaturally(location, itemStack);
                                        }
                                    }
                                    if(player.hasPermission("perks.Farmer.50")) {
                                        if(chance(0.085)){
                                            for (ItemStack itemStack : block.getDrops()){
                                                location.getWorld().dropItemNaturally(location, itemStack);
                                            }
                                        }
                                        if(chance(0.01) && player.hasPermission("perks.Farmer.50")){
                                            if(chance(0.25)){
                                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cr give to " + player + " farmercrate 1");
                                            }
                                        }
                                        if(chance(0.05) && player.hasPermission("perks.Farmer.75")){
                                            location.getWorld().dropItemNaturally(location, getExtra());
                                        }
                                    }
                                    crop.setAge(0);
                                    block.setBlockData(crop);
                                    addExperienceToJob(player.getUniqueId(), "Farmer", item.getExp());
                                    break;
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    public ItemStack getExtra(){
        List<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(Material.STICK));
        list.add(new ItemStack(Material.STICK));
        list.add(new ItemStack(Material.STICK));
        list.add(new ItemStack(Material.APPLE));
        list.add(new ItemStack(Material.APPLE));
        list.add(new ItemStack(Material.APPLE));
        list.add(new ItemStack(Material.SLIME_BALL));
        list.add(new ItemStack(Material.DIAMOND));
        list.add(new ItemStack(Material.EMERALD));
        list.add(new ItemStack(Material.ENDER_PEARL));
        list.add(new ItemStack(Material.STICK));
        int idx = new Random().nextInt(list.size());
        return list.get(idx);
    }

    public boolean chance(double chance){
        Random random = new Random();
        if(random.nextDouble() < chance){
            return true;
        }
        return false;
    }

    @EventHandler
    public void onKill(JobsExpGainEvent event){
        if(event.getEntity() == null){
            return;
        }
        if(FruchtCore.playerHasJob("Farmer", event.getPlayer().getPlayer()) && event.getPlayer().getPlayer() != null){
            PerkManager perkManager = new PerkManager();
            List<Perk> list = perkManager.getFarmerPerks();
            for (Perk perk : list){
                if(perk.getLevel() == 100 && event.getPlayer().getPlayer().hasPermission(perk.getPerm())){
                    if(event.getLivingEntity() instanceof Animals || event.getLivingEntity() instanceof Monster){
                        if(chance(1.0)){
                            boolean animal = false;
                            if(event.getLivingEntity() instanceof Animals){
                                animal = true;
                            }
                            event.getPlayer().getPlayer().getLocation().getWorld().dropItemNaturally(event.getPlayer().getPlayer().getLocation(), getSkull(event.getLivingEntity().getName(), animal));
                        }
                    }
                }
                if(perk.getLevel() == 50 && event.getPlayer().getPlayer().hasPermission(perk.getPerm())){
                    if(chance(0.01)){
                        if(chance(0.15)){
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cr give to " + event.getPlayer().getName() + " farmercrate 1");
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onShear(PlayerShearEntityEvent event){
        Player player = event.getPlayer();
        if(event.getEntity() instanceof Sheep){
            if(chance(0.1) && player.hasPermission("perks.Farmer.25")){
                event.getItem().setAmount(event.getItem().getAmount()*2);
            }
        }

    }

    public void durability(Player player) {
        if (isHoe(player.getInventory().getItemInMainHand().getType())) {
            ItemStack item = player.getInventory().getItemInMainHand();
            Damageable meta = (Damageable) item.getItemMeta();
            meta.setDamage(1);
            item.setItemMeta((ItemMeta) meta);
            if (meta.getDamage()<2) {
                player.getInventory().getItemInMainHand().setType(Material.AIR);
                player.updateInventory();
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 0.7f);
            }
        }
    }

    public static void addExperienceToJob(UUID playeruid, String name, double exp){
        for (JobProgression OneJob : Jobs.getPlayerManager().getJobsPlayer(playeruid).getJobProgression()) {
            if(OneJob.getJob().getName().equalsIgnoreCase(name)){
                OneJob.addExperience(exp);
                return;
            }
        }
    }

    public boolean isHoe(Material material) {
        boolean isHoe;
        switch (material) {
            case WOODEN_HOE:
            case STONE_HOE:
            case IRON_HOE:
            case GOLDEN_HOE:
            case NETHERITE_HOE:
            case DIAMOND_HOE:
                isHoe = true;
                break;
            default:
                isHoe = false;
                break;
        }
        return isHoe;
    }

    public ItemStack getSkull(String id, boolean animal){
        HeadDatabaseAPI api = new HeadDatabaseAPI();
        List<Head> animals = api.getHeads(CategoryEnum.ANIMALS);
        List<Head> mobs = api.getHeads(CategoryEnum.MONSTERS);
        List<Head> list = new ArrayList<>();
        if(animal){
            for (Head head : animals) {
                if (head.name.contains(id)) {
                    list.add(head);
                }
            }
        }else{
            for (Head mob : mobs) {
                if (mob.name.contains(id)) {
                    list.add(mob);
                }
            }
        }
        int idx = new Random().nextInt(list.size());
        return list.get(idx).getHead();
    }
}
