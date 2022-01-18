// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.help;

import org.bukkit.command.CommandSender;

public abstract class HelpTopic
{
    protected String name;
    protected String shortText;
    protected String fullText;
    protected String amendedPermission;
    
    public abstract boolean canSee(final CommandSender p0);
    
    public void amendCanSee(final String amendedPermission) {
        this.amendedPermission = amendedPermission;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getShortText() {
        return this.shortText;
    }
    
    public String getFullText(final CommandSender forWho) {
        return this.fullText;
    }
    
    public void amendTopic(final String amendedShortText, final String amendedFullText) {
        this.shortText = this.applyAmendment(this.shortText, amendedShortText);
        this.fullText = this.applyAmendment(this.fullText, amendedFullText);
    }
    
    protected String applyAmendment(final String baseText, final String amendment) {
        if (amendment == null) {
            return baseText;
        }
        return amendment.replaceAll("<text>", baseText);
    }
}
