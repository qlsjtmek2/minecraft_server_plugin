// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.conversations;

import java.util.regex.Pattern;

public abstract class RegexPrompt extends ValidatingPrompt
{
    private Pattern pattern;
    
    public RegexPrompt(final String regex) {
        this(Pattern.compile(regex));
    }
    
    public RegexPrompt(final Pattern pattern) {
        this.pattern = pattern;
    }
    
    private RegexPrompt() {
    }
    
    protected boolean isInputValid(final ConversationContext context, final String input) {
        return this.pattern.matcher(input).matches();
    }
}
