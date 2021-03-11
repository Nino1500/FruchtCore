package net.fruchtlabor.fruchtcore.othercommands;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class FruchtRestart implements CommandExecutor {

    Plugin plugin;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("fcr") && commandSender.hasPermission("fc.restart")){
            if(strings.length>=1){
                int time = Integer.parseInt(strings[0]);
                if(time > 0){
                    int counter = (time*60)/3;
                    final int[] sec = {time * 60};
                    Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            if(sec[0] > 0){
                                sendMsg(ChatColor.GOLD+"Restart in "+ChatColor.RED+ sec[0] +ChatColor.GOLD+" Sekunden!");
                                if(sec[0] > 0 && sec[0] - counter <= 0){
                                    sendMsg(ChatColor.GOLD+"Gleich kommt der Restart!");
                                }
                            }else{
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");
                            }
                            sec[0] = sec[0] - counter;
                        }
                    }, 0L, counter*20L);
                    return true;
                }else{
                    commandSender.sendMessage("Zeit kleiner 0 ? verarscht du mich?");
                }
            }
        }
        return false;
    }
    public void sendMsg(String msg){
        for (Player player : Bukkit.getOnlinePlayers()){
            player.sendMessage(msg);
        }
    }
    public void setPl(Plugin pl){
        plugin = pl;
    }
}
