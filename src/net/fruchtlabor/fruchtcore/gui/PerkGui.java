package net.fruchtlabor.fruchtcore.gui;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.Job;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import net.fruchtlabor.fruchtcore.jobsStuff.Perk;
import net.fruchtlabor.fruchtcore.jobsStuff.PerkManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PerkGui implements InventoryProvider {

    private static Job jobx;

    public static final SmartInventory PerkInv = SmartInventory.builder()
            .id("perkinv")
            .provider(new PerkGui())
            .size(3,9)
            .title("Perks")
            .build();

    @Override
    public void init(Player player, InventoryContents inventoryContents) {

        inventoryContents.fillBorders(ClickableItem.empty(new ItemStack(Material.GRAY_STAINED_GLASS_PANE)));
        ItemStack back = new ItemStack(Material.BARRIER);
        ItemMeta backmeta = back.getItemMeta();
        backmeta.setDisplayName(ChatColor.RED+"Zurück");
        back.setItemMeta(backmeta);
        inventoryContents.set(2, 0, ClickableItem.of(back, new Consumer<InventoryClickEvent>() {
            @Override
            public void accept(InventoryClickEvent inventoryClickEvent) {
                JobSpecificInv.setjob(jobx);
                JobSpecificInv.SpecificInv.open(player);
            }
        }));

        PerkManager perkManager = new PerkManager();
        List<Perk> list = new ArrayList<>();

        switch (jobx.getName()){
            case "Fischer":
                list = perkManager.getFischerPerks();
                break; case "Abenteurer":
                    list = perkManager.getAbenteurerPerks();
                    break;
                case "Minenarbeiter":
                    list = perkManager.getMinerPerks();
                    break;
                case "Farmer":
                    list = perkManager.getFarmerPerks();
                    break;
                case "Holzfaeller":
                    list = perkManager.getHolzFaePerks();
                    break;
        }
        for (Perk perk : list) {
            ItemStack itemStack = new ItemStack(perk.getGui_item_info());
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ChatColor.GRAY + perk.getName());
            List<String> strings = new ArrayList<>();
            strings.add(ChatColor.DARK_GREEN + perk.getInfo());
            strings.add(ChatColor.WHITE+"ab Level "+perk.getLevel());
            if (player.hasPermission(perk.getPerm())) {
                strings.add(ChatColor.GOLD + "Erlernt");
            }
            itemMeta.setLore(strings);
            itemStack.setItemMeta(itemMeta);
            inventoryContents.add(ClickableItem.empty(itemStack));
        }
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }

    public boolean hasPerm(Player player){
        return player.hasPermission("") || player.hasPermission("") || player.hasPermission("") || player.hasPermission("") || player.hasPermission("");
    }

    public static void setjob(Job job){
        jobx = job;
    }

}
