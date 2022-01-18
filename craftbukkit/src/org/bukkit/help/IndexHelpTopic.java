// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.help;

import org.bukkit.ChatColor;
import java.util.Iterator;
import org.bukkit.entity.Player;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.CommandSender;
import java.util.Collection;

public class IndexHelpTopic extends HelpTopic
{
    protected String permission;
    protected String preamble;
    protected Collection<HelpTopic> allTopics;
    
    public IndexHelpTopic(final String name, final String shortText, final String permission, final Collection<HelpTopic> topics) {
        this(name, shortText, permission, topics, null);
    }
    
    public IndexHelpTopic(final String name, final String shortText, final String permission, final Collection<HelpTopic> topics, final String preamble) {
        this.name = name;
        this.shortText = shortText;
        this.permission = permission;
        this.preamble = preamble;
        this.setTopicsCollection(topics);
    }
    
    protected void setTopicsCollection(final Collection<HelpTopic> topics) {
        this.allTopics = topics;
    }
    
    public boolean canSee(final CommandSender sender) {
        return sender instanceof ConsoleCommandSender || this.permission == null || sender.hasPermission(this.permission);
    }
    
    public void amendCanSee(final String amendedPermission) {
        this.permission = amendedPermission;
    }
    
    public String getFullText(final CommandSender sender) {
        final StringBuilder sb = new StringBuilder();
        if (this.preamble != null) {
            sb.append(this.buildPreamble(sender));
            sb.append("\n");
        }
        for (final HelpTopic topic : this.allTopics) {
            if (topic.canSee(sender)) {
                final String lineStr = this.buildIndexLine(sender, topic).replace("\n", ". ");
                if (sender instanceof Player && lineStr.length() > 55) {
                    sb.append(lineStr.substring(0, 52));
                    sb.append("...");
                }
                else {
                    sb.append(lineStr);
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }
    
    protected String buildPreamble(final CommandSender sender) {
        return ChatColor.GRAY + this.preamble;
    }
    
    protected String buildIndexLine(final CommandSender sender, final HelpTopic topic) {
        final StringBuilder line = new StringBuilder();
        line.append(ChatColor.GOLD);
        line.append(topic.getName());
        line.append(": ");
        line.append(ChatColor.WHITE);
        line.append(topic.getShortText());
        return line.toString();
    }
}
