// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.joptsimple;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

abstract class AbstractOptionSpec<V> implements OptionSpec<V>
{
    private final List<String> options;
    private final String description;
    
    protected AbstractOptionSpec(final String option) {
        this(Collections.singletonList(option), "");
    }
    
    protected AbstractOptionSpec(final Collection<String> options, final String description) {
        this.options = new ArrayList<String>();
        this.arrangeOptions(options);
        this.description = description;
    }
    
    public final Collection<String> options() {
        return Collections.unmodifiableCollection((Collection<? extends String>)this.options);
    }
    
    public final List<V> values(final OptionSet detectedOptions) {
        return detectedOptions.valuesOf((OptionSpec<V>)this);
    }
    
    public final V value(final OptionSet detectedOptions) {
        return detectedOptions.valueOf((OptionSpec<V>)this);
    }
    
    abstract List<V> defaultValues();
    
    String description() {
        return this.description;
    }
    
    protected abstract V convert(final String p0);
    
    abstract void handleOption(final OptionParser p0, final ArgumentList p1, final OptionSet p2, final String p3);
    
    abstract boolean acceptsArguments();
    
    abstract boolean requiresArgument();
    
    abstract void accept(final OptionSpecVisitor p0);
    
    private void arrangeOptions(final Collection<String> unarranged) {
        if (unarranged.size() == 1) {
            this.options.addAll(unarranged);
            return;
        }
        final List<String> shortOptions = new ArrayList<String>();
        final List<String> longOptions = new ArrayList<String>();
        for (final String each : unarranged) {
            if (each.length() == 1) {
                shortOptions.add(each);
            }
            else {
                longOptions.add(each);
            }
        }
        Collections.sort(shortOptions);
        Collections.sort(longOptions);
        this.options.addAll(shortOptions);
        this.options.addAll(longOptions);
    }
    
    public boolean equals(final Object that) {
        if (!(that instanceof AbstractOptionSpec)) {
            return false;
        }
        final AbstractOptionSpec<?> other = (AbstractOptionSpec<?>)that;
        return this.options.equals(other.options);
    }
    
    public int hashCode() {
        return this.options.hashCode();
    }
    
    public String toString() {
        return this.options.toString();
    }
}
