package net.fruchtlabor.fruchtcore.jobsStuff;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.ActionType;
import com.gamingmesh.jobs.container.Job;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class PerkManager {

    public PerkManager() {
    }

    public List<Perk> getMinerPerks(){ //fertig
        Job job = getJobbyname("Minenarbeiter");
        List<Perk> list = new ArrayList<>();
        list.add(new Perk("Cobble to Stone", job, ActionType.BREAK, 10, "Cobbel wird manchmal als Stein gedroppt", Material.WOODEN_PICKAXE));
        list.add(new Perk("Ofenmensch", job, ActionType.BREAK, 25, "Erz wird beim Abbau geschmolzen", Material.FURNACE));
        list.add(new Perk("Erzvariation & Schlüssel", job, ActionType.BREAK, 50, "Erze können weitere Erze droppen", Material.COAL_ORE));
        list.add(new Perk("Doppeldrop", job, ActionType.BREAK, 75, "Dropt alle Erze manchmal doppelt", Material.GLOWSTONE_DUST));
        list.add(new Perk("", job, ActionType.BREAK, 100, "kommt bald", Material.BARRIER));
        return list;
    }
    public List<Perk> getAbenteurerPerks(){ //fertig
        Job job = getJobbyname("Abenteurer");
        List<Perk> list = new ArrayList<>();
        list.add(new Perk("Sand to Glass", job, ActionType.BREAK, 10, "Sand wird manchmal als Glas gedropt + Exp", Material.GLASS));
        list.add(new Perk("Meistersammler", job, ActionType.BREAK, 25, "Verschiedene Extradropps", Material.CHEST));
        list.add(new Perk("Meistersammler II & Schlüssel", job, ActionType.BREAK, 50, "Qualität der Drops erhöht sich", Material.ENDER_CHEST));
        list.add(new Perk("Doppeldrop", job, ActionType.BREAK, 75, "Doppeldrop von Materialien", Material.GLOWSTONE_DUST));
        list.add(new Perk("", job, ActionType.BREAK, 100, "kommt bald", Material.BARRIER));
        return list;
    }
    public List<Perk> getFarmerPerks(){
        Job job = getJobbyname("Farmer"); //fertig
        List<Perk> list = new ArrayList<>();
        list.add(new Perk("Sensenmann", job, ActionType.BREAK, 10, "Rechtsklick mit Sense - baut ab & pflanzt nach", Material.DIAMOND_HOE));
        list.add(new Perk("MORE WOOL", job, ActionType.SHEAR, 25, "Doppeldrop von Wolle", Material.SHEARS));
        list.add(new Perk("Doppeldrop Crops & Schlüssel", job, ActionType.BREAK, 50, "Doppeldrop auf Crops", Material.WHEAT_SEEDS));
        list.add(new Perk("Items?", job, ActionType.BREAK, 75, "Crops können andere Items droppen", Material.STICK));
        list.add(new Perk("Headhunter", job, ActionType.KILL, 100, "Mobs & Tiere droppen manchmal Köpfe", Material.PLAYER_HEAD));
        return list;
    }
    public List<Perk> getFischerPerks(){ //fertig
        Job job = getJobbyname("Fischer");
        List<Perk> list = new ArrayList<>();
        list.add(new Perk("Kein Fisch?", job, ActionType.FISH, 10, "Items geben zw. 10 & 30 Exp", Material.GLASS_BOTTLE));
        list.add(new Perk("Super Fischer", job, ActionType.FISH, 25, "extra Loot", Material.FISHING_ROD));
        list.add(new Perk("My day good", job, ActionType.FISH, 50, "kann einen Schlüssel enthalten", Material.STRING));
        list.add(new Perk("Super Fischer II", job, ActionType.FISH, 75, "extra Loot II", Material.CHEST));
        list.add(new Perk("Oh my Head", job, ActionType.FISH, 100, "Chance auf Köpfe & Mending Bücher", Material.PLAYER_HEAD));
        return list;
    }
    public List<Perk> getHolzFaePerks(){ //fertig
        Job job = getJobbyname("Holzfaeller");
        List<Perk> list = new ArrayList<>();
        list.add(new Perk("Doppeldrop", job, ActionType.BREAK, 10, "Droppt Holzstämme zu 5% doppelt + Exp", Material.STONE_AXE)); //fertig
        list.add(new Perk("Rucksack I", job, ActionType.BREAK, 25, "öffnet einen Rucksack mit 2x9", Material.IRON_AXE)); //fertig
        list.add(new Perk("Doppeldrop II & Schlüssel", job, ActionType.BREAK, 50, "Droppt Holzstämme zu 10% doppelt", Material.GOLDEN_AXE)); //fertig
        list.add(new Perk("besseres Movement", job, ActionType.BREAK, 75, "Movementspeed beim Fällen", Material.DIAMOND_AXE));
        list.add(new Perk("Treefeller", job, ActionType.BREAK, 100, "lässt Bäume zusammenfallen (10%)", Material.NETHERITE_AXE)); //fertig
        return list;
    }

    public Job getJobbyname(String name){
       List<Job> list = Jobs.getJobs();
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getName().equalsIgnoreCase(name)){
                return list.get(i);
            }
        }
        return null;
    }
}
