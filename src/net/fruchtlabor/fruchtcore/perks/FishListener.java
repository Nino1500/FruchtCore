package net.fruchtlabor.fruchtcore.perks;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.JobProgression;
import javafx.concurrent.Worker;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.arcaniax.hdb.enums.CategoryEnum;
import me.arcaniax.hdb.object.head.Head;
import net.fruchtlabor.fruchtcore.FruchtCore;
import net.fruchtlabor.fruchtcore.jobsStuff.Perk;
import net.fruchtlabor.fruchtcore.jobsStuff.PerkManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class FishListener implements Listener {
    @EventHandler
    public void onFish(PlayerFishEvent event){

        if(event.getCaught() instanceof Item){
            ItemStack fished = ((Item) event.getCaught()).getItemStack();
            if(fished.getType() == Material.ENCHANTED_BOOK){
                if(fished.getItemMeta() == null) return;
                if(fished.getItemMeta().hasEnchant(Enchantment.MENDING)){
                    fished.setType(Material.DIAMOND);
                }
            }
        }

        Player player = event.getPlayer();

        if(FruchtCore.playerHasJob("Fischer", player)){

            PerkManager perkManager = new PerkManager();
            List<Perk> list = perkManager.getFischerPerks();

            for (Perk perk : list) {
                if (perk.getLevel() == 10 && player.hasPermission(perk.getPerm())) {
                    //player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1400, 0));
                    if (event.getCaught() != null && !isFish(event.getCaught().getType())) {
                        double e = Math.random() * (30 - 10) + 10;
                        addExperienceToJob(player.getUniqueId(), "Fischer", e);
                    }
                }
                if (event.getState() == PlayerFishEvent.State.CAUGHT_ENTITY || event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
                    if (perk.getLevel() == 25 && player.hasPermission(perk.getPerm()) && !player.hasPermission("perks.fischer.75")) {
                        if (chance(0.1)) {
                            player.getInventory().addItem(extra1());
                        }
                    }
                    if (perk.getLevel() == 50 && player.hasPermission(perk.getPerm())) {
                        if (chance(0.05)) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cr give to " + player + " fischcrate 1");
                        }
                    }
                    if (perk.getLevel() == 75 && player.hasPermission(perk.getPerm())) {
                        if (chance(0.1)) {
                            player.getInventory().addItem(extra2());
                        }
                    }
                    if (perk.getLevel() == 100 && player.hasPermission(perk.getPerm())) {
                        if (chance(0.01)) {
                            giveSkullOrMending(player);
                        }
                    }
                }
            }
            /*if(event.getState() == PlayerFishEvent.State.REEL_IN || event.getState() != PlayerFishEvent.State.FISHING){
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            }*/

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
    public boolean isFish(EntityType entityType){
        if(entityType == EntityType.COD){
            return true;
        }
        if(entityType == EntityType.PUFFERFISH){
            return true;
        }
        if(entityType == EntityType.SALMON){
            return true;
        }
        if(entityType == EntityType.TROPICAL_FISH){
            return true;
        }
        return false;
    }
    public ItemStack extra1(){
        List<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(Material.DIAMOND));
        list.add(new ItemStack(Material.EMERALD));
        list.add(new ItemStack(Material.BEEF));
        list.add(new ItemStack(Material.STICK));
        list.add(new ItemStack(Material.SLIME_BALL));
        list.add(new ItemStack(Material.SLIME_BALL));
        list.add(new ItemStack(Material.GLOWSTONE_DUST));
        int idx = new Random().nextInt(list.size());
        return list.get(idx);
    }
    public ItemStack extra2(){
        List<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(Material.DIAMOND, 3));
        list.add(new ItemStack(Material.EMERALD, 2));
        list.add(new ItemStack(Material.BEEF));
        list.add(new ItemStack(Material.SLIME_BALL, 2));
        list.add(new ItemStack(Material.SLIME_BALL));
        list.add(new ItemStack(Material.STICK));
        list.add(new ItemStack(Material.STICK));
        list.add(new ItemStack(Material.GOLD_BLOCK));
        list.add(new ItemStack(Material.IRON_BLOCK));
        list.add(new ItemStack(Material.COAL_BLOCK, 3));
        list.add(new ItemStack(Material.GLOWSTONE_DUST));
        int idx = new Random().nextInt(list.size());
        return list.get(idx);
    }
    public void giveSkullOrMending(Player player){
        List<ItemStack> list = new ArrayList<>();
        list.add(getSkull("4169"));
        list.add(getSkull("326"));
        list.add(getSkull("18787"));
        list.add(getSkull("40271"));
        list.add(getSkull("22740"));
        list.add(getSkull("25292"));
        list.add(getSkull("40846"));
        list.add(getSkull("316"));
        list.add(getSkull("24137"));
        list.add(getSkull("37262"));
        list.add(getSkull("37261"));
        list.add(getSkull("23639"));
        list.add(getSkull("324"));
        list.add(getSkull("6010"));
        list.add(getSkull("37548"));
        list.add(getSkull("6742"));
        list.add(getSkull("32798"));
        list.add(getSkull("39477"));
        list.add(getSkull("18018"));
        list.add(getSkull("22775"));
        list.add(getSkull("345"));
        list.add(getSkull("346"));
        list.add(getSkull("33881"));
        list.add(getSkull("12011"));
        list.add(getSkull("12008"));
        list.add(getSkull("2351"));
        list.add(getSkull("28457"));
        list.add(getSkull("28456"));
        list.add(getSkull("1154"));
        list.add(getSkull("29766"));
        list.add(getSkull("25201"));
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();
        meta.addStoredEnchant(Enchantment.MENDING, 1, true);
        book.setItemMeta(meta);
        list.add(book);
        int idx = new Random().nextInt(list.size());
        player.getInventory().addItem(list.get(idx));
    }
    public ItemStack getSkull(String id){
        HeadDatabaseAPI api = new HeadDatabaseAPI();
        List<Head> animals = api.getHeads(CategoryEnum.ANIMALS);
        List<Head> mobs = api.getHeads(CategoryEnum.MONSTERS);
        for (int i = 0; i < animals.size(); i++) {
            if(animals.get(i).id.equalsIgnoreCase(id)){
                return animals.get(i).getHead();
            }
        }
        for (int i = 0; i < mobs.size(); i++) {
            if(mobs.get(i).id.equalsIgnoreCase(id)){
                return mobs.get(i).getHead();
            }
        }
        return new ItemStack(Material.STICK);
    }
    public boolean chance(double chance){
        Random random = new Random();
        if(random.nextDouble() < chance){
            return true;
        }
        return false;
    }
}
