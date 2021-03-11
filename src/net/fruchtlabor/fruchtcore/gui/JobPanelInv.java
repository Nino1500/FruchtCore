package net.fruchtlabor.fruchtcore.gui;

import com.gamingmesh.jobs.CMILib.ItemManager;
import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.Job;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.Consumer;

public class JobPanelInv implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("jobpanel")
            .provider(new JobPanelInv())
            .size(3,9)
            .title("JobPanel")
            .build();

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        inventoryContents.fillBorders(ClickableItem.empty(new ItemStack(Material.GRAY_STAINED_GLASS_PANE)));

        List<Job> list = Jobs.getJobs();
        for (int i = 0; i < list.size(); i++) {

            String name = list.get(i).getName();
            ItemStack itemStack = list.get(i).getGuiItem();
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(name);
            itemStack.setItemMeta(itemMeta);

            int finalI1 = i;
            inventoryContents.add(ClickableItem.of(itemStack, new Consumer<InventoryClickEvent>() {
                @Override
                public void accept(InventoryClickEvent inventoryClickEvent) {
                    JobSpecificInv.setjob(list.get(finalI1));
                    JobSpecificInv.SpecificInv.open(player);
                }
            }));
        }
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {
    }

}
