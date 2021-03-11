package net.fruchtlabor.fruchtcore;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.JobProgression;
import com.gamingmesh.jobs.container.JobsPlayer;
import com.mojang.datafixers.types.templates.Check;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import net.fruchtlabor.fruchtcore.gui.JobPanel;
import net.fruchtlabor.fruchtcore.jobsStuff.LevelUp;
import net.fruchtlabor.fruchtcore.listeners.*;
import net.fruchtlabor.fruchtcore.othercommands.*;
import net.fruchtlabor.fruchtcore.perks.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;

public class FruchtCore extends JavaPlugin {

    public static CoreProtectAPI api = null;
    //public static HashMap<Location, String> overfishmap = new HashMap<>();
    public static HashMap<List<Location>, String> overfishradius = new HashMap<>();

    @Override
    public void onEnable() {
        super.onEnable();

        this.getServer().getPluginManager().registerEvents(new DenyJobCommand(), this);
        this.getServer().getPluginManager().registerEvents(new LevelUp(), this);
        this.getServer().getPluginManager().registerEvents(new FishListener(), this);
        this.getServer().getPluginManager().registerEvents(new Holzfaeller_breaking(), this);
        this.getServer().getPluginManager().registerEvents(new MinerBreaking_listener(), this);
        this.getServer().getPluginManager().registerEvents(new Abenteurer_breaking(), this);
        this.getServer().getPluginManager().registerEvents(new FarmerBreakAndKill() , this);
        this.getServer().getPluginManager().registerEvents(new DenyVillager(), this);
        this.getServer().getPluginManager().registerEvents(new OnJoin(), this);
        this.getServer().getPluginManager().registerEvents(new CheckOverFishRadius(), this);

        this.getCommand("job").setExecutor(new JobPanel());
        this.getCommand("team").setExecutor(new Team());

        FruchtRestart fruchtRestart = new FruchtRestart();
        fruchtRestart.setPl(this);
        this.getCommand("fcr").setExecutor(fruchtRestart);

        api = getCoreProtect();
        if (api != null){
            api.testAPI();
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                overfishradius.clear();
            }
        }, 0L, 216000L);

    }

    public static boolean playerHasJob(String job, Player player){
        JobsPlayer jobsPlayer = Jobs.getPlayerManager().getJobsPlayer(player);
        for (JobProgression one : jobsPlayer.progression){
            if(one.getJob().getName().equalsIgnoreCase(job)){
                return true;
            }
        }
        return false;
    }
    public static int playerlevel(String job, Player player){
        JobsPlayer jobsPlayer = Jobs.getPlayerManager().getJobsPlayer(player);
        for (JobProgression one : jobsPlayer.progression){
            if(one.getJob().getName().equalsIgnoreCase(job)){
                return one.getLevel();
            }
        }
        return 0;
    }
    public static int playerNumOfJobs(Player player){
        return Jobs.getPlayerManager().getJobsPlayer(player).progression.size();
    }

    private CoreProtectAPI getCoreProtect(){
        Plugin plugin = getServer().getPluginManager().getPlugin("CoreProtect");
        if (!(plugin instanceof CoreProtect)) {
            return null;
        }
        CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
        if (!CoreProtect.isEnabled()) {
            return null;
        }
        if (CoreProtect.APIVersion() < 6) {
            return null;
        }
        return CoreProtect;
    }
}
