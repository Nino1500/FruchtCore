package net.fruchtlabor.fruchtcore.jobsStuff;

import com.gamingmesh.jobs.api.JobsLevelUpEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LevelUp implements Listener {
    @EventHandler
    public void onLvl(JobsLevelUpEvent event){

        String jobname = event.getJobName();

        Player player = event.getPlayer().getPlayer();

        int lvl = event.getLevel();

        if(lvl==10 || lvl==25 || lvl==50 || lvl==75 || lvl==100){
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user "+player.getName()+ " permission set perks."+jobname+"."+lvl);
            player.sendMessage(ChatColor.GREEN+" Du hast einen Perk für "+jobname+" freigschaltet! Nutze /job um in einzusehen!");
            if(lvl == 25 && jobname.equalsIgnoreCase("Holzfaeller")){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user "+player.getName()+" permission set backpack.use");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user "+player.getName()+" permission set backpack.size.2");
            }
        }
    }
}
