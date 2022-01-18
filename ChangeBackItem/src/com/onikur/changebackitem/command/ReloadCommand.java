// 
// Decompiled by Procyon v0.5.30
// 

package com.onikur.changebackitem.command;

import org.bukkit.command.CommandException;
import com.onikur.changebackitem.util.Msg;
import com.onikur.changebackitem.ChangeBackItem;
import org.bukkit.command.CommandSender;
import com.onikur.changebackitem.config.PhraseConfig;

public class ReloadCommand extends ExTCommand
{
    public ReloadCommand() {
        super("reload", "reload", PhraseConfig.Help__Cmd_Reload.val(), "changebackitem.reload", 0, false);
    }
    
    @Override
    public void action(final CommandSender sender, final String[] args, final CommandHandler handler) throws CommandException {
        ChangeBackItem.get().reloadAllConfig();
        Msg.send(sender, PhraseConfig.Plugin_Reloaded_All.val());
    }
}
