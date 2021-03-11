package net.fruchtlabor.fruchtcore.jobsStuff;

import com.gamingmesh.jobs.container.ActionType;
import com.gamingmesh.jobs.container.Job;
import com.gamingmesh.jobs.container.JobInfo;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Perk {
    private String name;
    private Job job;
    private ActionType type;
    private int level;
    private String info;
    private Material gui_item_info;
    private String perm;

    public Material getGui_item_info() {
        return gui_item_info;
    }

    public Perk(String name, Job job, ActionType type, int level, String info, Material gui_item_info) {
        this.name = name;
        this.job = job;
        this.type = type;
        this.level = level;
        this.info = info;
        this.gui_item_info = gui_item_info;
        this.perm = "perks."+job.getName()+"."+level;
    }

    private ArrayList<ContentItem> constructList(int playerlevel, int numberOfJobs){
        ArrayList<ContentItem> list = new ArrayList<>();
        List<JobInfo> jobinfo = job.getJobInfo(type); //ActionType.Break or smth
        for (int i = 0; i < jobinfo.size(); i++){
            String spl[] = jobinfo.get(i).getName().split(":");
            try {
                Material material = Material.matchMaterial(spl[0]);
                double exp = jobinfo.get(i).getExperience(playerlevel, numberOfJobs,2);
                if(material == null){
                    Bukkit.getConsoleSender().sendMessage("ERROR material is NULL Perk.java");
                }else {
                    list.add(new ContentItem(new ItemStack(material), exp));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return list;
    }

    public String getName() {
        return name;
    }

    public Job getJob() {
        return job;
    }

    public ArrayList<ContentItem> getList(int playerlvl, int numberJobs) {
        return constructList(playerlvl, numberJobs);
    }

    public String getInfo() {
        return info;
    }

    public String getPerm() {
        return perm;
    }

    public int getLevel() {
        return level;
    }
}
