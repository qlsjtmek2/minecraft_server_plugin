// 
// Decompiled by Procyon v0.5.30
// 

package com.onikur.changebackitem.command;

import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.onikur.changebackitem.ChangeBackItem;
import com.onikur.changebackitem.config.PhraseConfig;
import com.onikur.changebackitem.util.Msg;

public class CommandHandler implements CommandExecutor
{
    public static final String BASE_COMMAND = "changebackitem";
    public static final String BASE_COMMAND_PERMISSION = "changebackitem.";
    private Map<String, ExTCommand> commands;
    
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (args.length != 0) {
            final String subCmd = args[0].toLowerCase();
            if (this.commands.containsKey(subCmd)) {
                final ExTCommand excmd = this.commands.get(subCmd);
                if (!(sender instanceof Player) && excmd.getOnlyPlayerUse()) {
                    Msg.send(sender, PhraseConfig.Only_Player_Use.val());
                    return true;
                }
                if (!excmd.hasPermission(sender)) {
                    Msg.send(sender, PhraseConfig.No_Permission.val());
                    return true;
                }
                if (args.length < excmd.getMinArgs()) {
                    Msg.send(sender, PhraseConfig.Args_Incorrect.val() + " &4/" + "changebackitem" + " " + excmd.getCmdHelp());
                    return true;
                }
                excmd.action(sender, args, this);
            }
            else {
                Msg.send(sender, PhraseConfig.Command_Not_Find.val());
            }
            return true;
        }
        if (!sender.hasPermission("changebackitem.plugininfo")) {
            Msg.send(sender, PhraseConfig.No_Permission.val());
            return true;
        }
        this.cmd_info(sender);
        return true;
    }
    
    private void cmd_info(final CommandSender sender) {
        Msg.send(sender, "&b\u2592\u2592\u2592\u2592\u2592 " + ChangeBackItem.get().getDescription().getName() + " \u2592\u2592\u2592\u2592\u2592");
        Msg.send(sender, "&fVersion : " + ChangeBackItem.get().getDescription().getVersion());
        Msg.send(sender, "&fProject Developer : Onikur");
        Msg.send(sender, "&aType \"/changebackitem help\" for help.");
    }
    
    public Map<String, ExTCommand> getAllCommand() {
        return this.commands;
    }
}
