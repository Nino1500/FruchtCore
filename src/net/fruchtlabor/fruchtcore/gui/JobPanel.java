package net.fruchtlabor.fruchtcore.gui;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JobPanel implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            if(command.getName().equalsIgnoreCase("job") || command.getName().equalsIgnoreCase("jobs")){
                JobPanelInv.INVENTORY.open(player);
                return true;
            }
        }
        return false;
    }
}

