package net.fruchtlabor.fruchtcore.jobsStuff;

import org.bukkit.inventory.ItemStack;

public class ContentItem {
    private ItemStack item;
    private double exp;

    public ContentItem(ItemStack item, double exp) {
        this.item = item;
        this.exp = exp;
    }

    public ItemStack getItem() {
        return item;
    }

    public double getExp() {
        return exp;
    }
}
