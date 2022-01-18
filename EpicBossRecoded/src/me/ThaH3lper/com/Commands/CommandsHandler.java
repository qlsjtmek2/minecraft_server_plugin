// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Commands;

import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import me.ThaH3lper.com.EpicBoss;
import org.bukkit.command.CommandExecutor;

public class CommandsHandler implements CommandExecutor
{
    private CommandsConsole CC;
    private CommandsPlayer CP;
    private EpicBoss eb;
    
    public CommandsHandler(final EpicBoss boss) {
        this.eb = boss;
        this.CP = new CommandsPlayer(this.eb);
        this.CC = new CommandsConsole(this.eb);
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandlabel, final String[] args) {
        if (sender instanceof Player) {
            this.CP.Command(sender, cmd, commandlabel, args);
        }
        else {
            this.CC.Command(sender, cmd, commandlabel, args);
        }
        return false;
    }
}
