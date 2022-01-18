// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.joptsimple;

import java.util.Collection;

public class OptionSpecBuilder extends NoArgumentOptionSpec
{
    private final OptionParser parser;
    
    OptionSpecBuilder(final OptionParser parser, final Collection<String> options, final String description) {
        super(options, description);
        this.parser = parser;
        this.attachToParser();
    }
    
    private void attachToParser() {
        this.parser.recognize(this);
    }
    
    public ArgumentAcceptingOptionSpec<String> withRequiredArg() {
        final ArgumentAcceptingOptionSpec<String> newSpec = new RequiredArgumentOptionSpec<String>(this.options(), this.description());
        this.parser.recognize(newSpec);
        return newSpec;
    }
    
    public ArgumentAcceptingOptionSpec<String> withOptionalArg() {
        final ArgumentAcceptingOptionSpec<String> newSpec = new OptionalArgumentOptionSpec<String>(this.options(), this.description());
        this.parser.recognize(newSpec);
        return newSpec;
    }
}
