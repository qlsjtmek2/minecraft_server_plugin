// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.joptsimple;

import java.util.List;
import java.util.Collection;
import java.util.Collections;

class NoArgumentOptionSpec extends AbstractOptionSpec<Void>
{
    NoArgumentOptionSpec(final String option) {
        this(Collections.singletonList(option), "");
    }
    
    NoArgumentOptionSpec(final Collection<String> options, final String description) {
        super(options, description);
    }
    
    void handleOption(final OptionParser parser, final ArgumentList arguments, final OptionSet detectedOptions, final String detectedArgument) {
        detectedOptions.add(this);
    }
    
    boolean acceptsArguments() {
        return false;
    }
    
    boolean requiresArgument() {
        return false;
    }
    
    void accept(final OptionSpecVisitor visitor) {
        visitor.visit(this);
    }
    
    protected Void convert(final String argument) {
        return null;
    }
    
    List<Void> defaultValues() {
        return Collections.emptyList();
    }
}
