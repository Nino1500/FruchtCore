package net.fruchtlabor.fruchtcore.gui;

import com.gamingmesh.jobs.container.Job;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;

public class JobSpecificInv implements InventoryProvider {

    private static Job jobx;

    public static final SmartInventory SpecificInv = SmartInventory.builder()
            .id("specificinv")
            .provider(new JobSpecificInv())
            .size(4,9)
            .title("Job")
            .build();

    @Override
    public void init(Player player, InventoryContents inventoryContents) {

        ItemStack perks = new ItemStack(Material.OAK_SIGN);
        ItemMeta meta = perks.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD+"Perks");
        perks.setItemMeta(meta);

        ItemStack join = new ItemStack(Material.GREEN_STAINED_GLASS);
        ItemMeta meta1 = join.getItemMeta();
        meta1.setDisplayName(ChatColor.DARK_GREEN+"Beitreten");
        join.setItemMeta(meta1);

        ItemStack leave = new ItemStack(Material.RED_STAINED_GLASS);
        ItemMeta meta3 = leave.getItemMeta();
        meta3.setDisplayName(ChatColor.RED+"Verlassen");
        leave.setItemMeta(meta3);

        ItemStack info = new ItemStack(Material.COMPASS);
        ItemMeta infometa = info.getItemMeta();
        infometa.setDisplayName(ChatColor.GREEN+"Info");
        info.setItemMeta(infometa);

        ItemStack top = new ItemStack(Material.CLOCK);
        ItemMeta topmeta = top.getItemMeta();
        topmeta.setDisplayName(ChatColor.GOLD+"Job-Platzierungen");
        top.setItemMeta(topmeta);

        inventoryContents.set(1, 2, ClickableItem.of(perks, new Consumer<InventoryClickEvent>() {
            @Override
            public void accept(InventoryClickEvent inventoryClickEvent) {
                PerkGui.setjob(jobx);
                PerkGui.PerkInv.open(player);
            }
        }));
        inventoryContents.set(1, 4, ClickableItem.of(join, new Consumer<InventoryClickEvent>() {
            @Override
            public void accept(InventoryClickEvent inventoryClickEvent) {
                //beitreten
                Bukkit.dispatchCommand(player, "jobs join "+jobx.getName());
            }
        }));
        inventoryContents.set(1, 6, ClickableItem.of(leave, new Consumer<InventoryClickEvent>() {
            @Override
            public void accept(InventoryClickEvent inventoryClickEvent) {
                //verlassen
                Bukkit.dispatchCommand(player, "jobs leave "+jobx.getName());
            }
        }));
        inventoryContents.set(2, 3, ClickableItem.of(info, new Consumer<InventoryClickEvent>() {
            @Override
            public void accept(InventoryClickEvent inventoryClickEvent) {
                Bukkit.dispatchCommand(player, "jobs info "+jobx.getName());
                player.closeInventory();
            }
        }));
        inventoryContents.set(2, 5, ClickableItem.of(top, new Consumer<InventoryClickEvent>() {
            @Override
            public void accept(InventoryClickEvent inventoryClickEvent) {
                Bukkit.dispatchCommand(player, "jobs top "+jobx.getName());
                player.closeInventory();
            }
        }));

        //DEKO SHIT
        inventoryContents.fillBorders(ClickableItem.empty(new ItemStack(Material.GRAY_STAINED_GLASS_PANE)));

        ItemStack back = new ItemStack(Material.BARRIER);
        ItemMeta backmeta = back.getItemMeta();
        backmeta.setDisplayName(ChatColor.RED+"Zurück");
        back.setItemMeta(backmeta);
        inventoryContents.set(3, 0, ClickableItem.of(back, new Consumer<InventoryClickEvent>() {
            @Override
            public void accept(InventoryClickEvent inventoryClickEvent) {
                JobPanelInv.INVENTORY.open(player);
            }
        }));
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }

    public static void setjob(Job job){
        jobx = job;
    }

}
