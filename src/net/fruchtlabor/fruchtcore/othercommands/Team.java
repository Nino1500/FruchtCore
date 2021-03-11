package net.fruchtlabor.fruchtcore.othercommands;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Team implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("team") && commandSender.hasPermission("fruchtcore.team")){
            if(strings.length>=1){
                StringBuilder msg = new StringBuilder();
                for (String string : strings) {
                    msg.append(" ");
                    msg.append(string);
                }
                String msg2 = ChatColor.DARK_RED+"[Team] "+ChatColor.GOLD+commandSender.getName()+ChatColor.DARK_GRAY+" > "+ChatColor.GRAY+msg.toString();
                for (Player player : Bukkit.getOnlinePlayers()){
                    if(player.hasPermission("fruchtcore.team")){
                        player.sendMessage(msg2);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
