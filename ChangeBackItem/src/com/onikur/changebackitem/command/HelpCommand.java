// 
// Decompiled by Procyon v0.5.30
// 

package com.onikur.changebackitem.command;

import java.util.Map;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

import com.onikur.changebackitem.ChangeBackItem;
import com.onikur.changebackitem.config.PhraseConfig;
import com.onikur.changebackitem.util.Msg;

public class HelpCommand extends ExTCommand
{
    public HelpCommand() {
        super("help", "help", PhraseConfig.Help__Cmd_Help.val(), "changebackitem.help", 0, false);
    }
    
    @Override
    public void action(final CommandSender sender, final String[] args, final CommandHandler handler) throws CommandException {
        Msg.send(sender, "&a\u2592\u2592\u2592\u2592 &f" + ChangeBackItem.get().getDescription().getName() + " Help &a\u2592\u2592\u2592\u2592");
        Msg.send(sender, this.helpDescription("", PhraseConfig.Help__Cmd_PluginInfo.val()));
        final Map<String, ExTCommand> commands = handler.getAllCommand();
        for (final String key : commands.keySet()) {
            Msg.send(sender, this.helpDescription(commands.get(key).getCmdHelp(), commands.get(key).getDescription()));
        }
    }
    
    private String helpDescription(final String sub, final String des) {
        return "&f/changebackitem" + (sub.equalsIgnoreCase("") ? "" : (" " + sub)) + " &7" + des;
    }
}
