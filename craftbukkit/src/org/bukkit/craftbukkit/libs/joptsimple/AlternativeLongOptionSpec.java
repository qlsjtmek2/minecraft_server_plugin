// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.joptsimple;

import java.util.Collection;
import java.util.Collections;

class AlternativeLongOptionSpec extends ArgumentAcceptingOptionSpec<String>
{
    AlternativeLongOptionSpec() {
        super(Collections.singletonList("W"), true, "Alternative form of long options");
        this.describedAs("opt=value");
    }
    
    protected void detectOptionArgument(final OptionParser parser, final ArgumentList arguments, final OptionSet detectedOptions) {
        if (!arguments.hasMore()) {
            throw new OptionMissingRequiredArgumentException(this.options());
        }
        arguments.treatNextAsLongOption();
    }
    
    void accept(final OptionSpecVisitor visitor) {
        visitor.visit(this);
    }
}
