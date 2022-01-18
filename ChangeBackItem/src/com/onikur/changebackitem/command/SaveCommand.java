// 
// Decompiled by Procyon v0.5.30
// 

package com.onikur.changebackitem.command;

import org.bukkit.command.CommandException;
import com.onikur.changebackitem.util.Msg;
import com.onikur.changebackitem.ChangeBackItem;
import org.bukkit.command.CommandSender;
import com.onikur.changebackitem.config.PhraseConfig;

public class SaveCommand extends ExTCommand
{
    public SaveCommand() {
        super("save", "save", PhraseConfig.Help__Cmd_Save.val(), "changebackitem.save", 0, false);
    }
    
    @Override
    public void action(final CommandSender sender, final String[] args, final CommandHandler handler) throws CommandException {
        ChangeBackItem.get().getBlockStorage().saveData();
        Msg.send(sender, PhraseConfig.Data_Has_Been_Saved.val());
    }
}
