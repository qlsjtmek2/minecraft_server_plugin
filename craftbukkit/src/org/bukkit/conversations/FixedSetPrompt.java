// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.conversations;

import java.util.Collection;
import org.apache.commons.lang.StringUtils;
import java.util.Arrays;
import java.util.List;

public abstract class FixedSetPrompt extends ValidatingPrompt
{
    protected List<String> fixedSet;
    
    public FixedSetPrompt(final String... fixedSet) {
        this.fixedSet = Arrays.asList(fixedSet);
    }
    
    private FixedSetPrompt() {
    }
    
    protected boolean isInputValid(final ConversationContext context, final String input) {
        return this.fixedSet.contains(input);
    }
    
    protected String formatFixedSet() {
        return "[" + StringUtils.join(this.fixedSet, ", ") + "]";
    }
}
