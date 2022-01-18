// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.help;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.help.HelpTopic;

public class CustomHelpTopic extends HelpTopic
{
    private final String permissionNode;
    
    public CustomHelpTopic(final String name, final String shortText, final String fullText, final String permissionNode) {
        this.permissionNode = permissionNode;
        this.name = name;
        this.shortText = shortText;
        this.fullText = shortText + "\n" + fullText;
    }
    
    public boolean canSee(final CommandSender sender) {
        return sender instanceof ConsoleCommandSender || this.permissionNode.equals("") || sender.hasPermission(this.permissionNode);
    }
}
