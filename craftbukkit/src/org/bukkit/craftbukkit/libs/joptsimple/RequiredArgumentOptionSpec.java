// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.joptsimple;

import java.util.Collection;

class RequiredArgumentOptionSpec<V> extends ArgumentAcceptingOptionSpec<V>
{
    RequiredArgumentOptionSpec(final String option) {
        super(option, true);
    }
    
    RequiredArgumentOptionSpec(final Collection<String> options, final String description) {
        super(options, true, description);
    }
    
    protected void detectOptionArgument(final OptionParser parser, final ArgumentList arguments, final OptionSet detectedOptions) {
        if (!arguments.hasMore()) {
            throw new OptionMissingRequiredArgumentException(this.options());
        }
        this.addArguments(detectedOptions, arguments.next());
    }
    
    void accept(final OptionSpecVisitor visitor) {
        visitor.visit(this);
    }
}
