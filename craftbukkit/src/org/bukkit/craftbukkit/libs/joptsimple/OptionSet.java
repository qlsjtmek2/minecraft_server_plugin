// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.joptsimple;

import java.util.Iterator;
import java.util.Collections;
import org.bukkit.craftbukkit.libs.joptsimple.internal.Objects;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptionSet
{
    private final Map<String, AbstractOptionSpec<?>> detectedOptions;
    private final Map<AbstractOptionSpec<?>, List<String>> optionsToArguments;
    private final List<String> nonOptionArguments;
    private final Map<String, List<?>> defaultValues;
    
    OptionSet(final Map<String, List<?>> defaults) {
        this.detectedOptions = new HashMap<String, AbstractOptionSpec<?>>();
        this.optionsToArguments = new IdentityHashMap<AbstractOptionSpec<?>, List<String>>();
        this.nonOptionArguments = new ArrayList<String>();
        this.defaultValues = new HashMap<String, List<?>>(defaults);
    }
    
    public boolean has(final String option) {
        return this.detectedOptions.containsKey(option);
    }
    
    public boolean has(final OptionSpec<?> option) {
        return this.optionsToArguments.containsKey(option);
    }
    
    public boolean hasArgument(final String option) {
        final AbstractOptionSpec<?> spec = this.detectedOptions.get(option);
        return spec != null && this.hasArgument(spec);
    }
    
    public boolean hasArgument(final OptionSpec<?> option) {
        Objects.ensureNotNull(option);
        final List<String> values = this.optionsToArguments.get(option);
        return values != null && !values.isEmpty();
    }
    
    public Object valueOf(final String option) {
        Objects.ensureNotNull(option);
        final AbstractOptionSpec<?> spec = this.detectedOptions.get(option);
        if (spec == null) {
            final List<?> defaults = this.defaultValuesFor(option);
            return defaults.isEmpty() ? null : defaults.get(0);
        }
        return this.valueOf(spec);
    }
    
    public <V> V valueOf(final OptionSpec<V> option) {
        Objects.ensureNotNull(option);
        final List<V> values = this.valuesOf(option);
        switch (values.size()) {
            case 0: {
                return null;
            }
            case 1: {
                return values.get(0);
            }
            default: {
                throw new MultipleArgumentsForOptionException(option.options());
            }
        }
    }
    
    public List<?> valuesOf(final String option) {
        Objects.ensureNotNull(option);
        final AbstractOptionSpec<?> spec = this.detectedOptions.get(option);
        return (spec == null) ? this.defaultValuesFor(option) : this.valuesOf(spec);
    }
    
    public <V> List<V> valuesOf(final OptionSpec<V> option) {
        Objects.ensureNotNull(option);
        final List<String> values = this.optionsToArguments.get(option);
        if (values == null || values.isEmpty()) {
            return (List<V>)this.defaultValueFor((OptionSpec<Object>)option);
        }
        final AbstractOptionSpec<V> spec = (AbstractOptionSpec<V>)(AbstractOptionSpec)option;
        final List<V> convertedValues = new ArrayList<V>();
        for (final String each : values) {
            convertedValues.add(spec.convert(each));
        }
        return Collections.unmodifiableList((List<? extends V>)convertedValues);
    }
    
    public List<String> nonOptionArguments() {
        return Collections.unmodifiableList((List<? extends String>)this.nonOptionArguments);
    }
    
    void add(final AbstractOptionSpec<?> option) {
        this.addWithArgument(option, null);
    }
    
    void addWithArgument(final AbstractOptionSpec<?> option, final String argument) {
        for (final String each : option.options()) {
            this.detectedOptions.put(each, option);
        }
        List<String> optionArguments = this.optionsToArguments.get(option);
        if (optionArguments == null) {
            optionArguments = new ArrayList<String>();
            this.optionsToArguments.put(option, optionArguments);
        }
        if (argument != null) {
            optionArguments.add(argument);
        }
    }
    
    void addNonOptionArgument(final String argument) {
        this.nonOptionArguments.add(argument);
    }
    
    public boolean equals(final Object that) {
        if (this == that) {
            return true;
        }
        if (that == null || !this.getClass().equals(that.getClass())) {
            return false;
        }
        final OptionSet other = (OptionSet)that;
        final Map<AbstractOptionSpec<?>, List<String>> thisOptionsToArguments = new HashMap<AbstractOptionSpec<?>, List<String>>(this.optionsToArguments);
        final Map<AbstractOptionSpec<?>, List<String>> otherOptionsToArguments = new HashMap<AbstractOptionSpec<?>, List<String>>(other.optionsToArguments);
        return this.detectedOptions.equals(other.detectedOptions) && thisOptionsToArguments.equals(otherOptionsToArguments) && this.nonOptionArguments.equals(other.nonOptionArguments());
    }
    
    public int hashCode() {
        final Map<AbstractOptionSpec<?>, List<String>> thisOptionsToArguments = new HashMap<AbstractOptionSpec<?>, List<String>>(this.optionsToArguments);
        return this.detectedOptions.hashCode() ^ thisOptionsToArguments.hashCode() ^ this.nonOptionArguments.hashCode();
    }
    
    private <V> List<V> defaultValuesFor(final String option) {
        if (this.defaultValues.containsKey(option)) {
            final List<V> defaults = (List<V>)this.defaultValues.get(option);
            return defaults;
        }
        return Collections.emptyList();
    }
    
    private <V> List<V> defaultValueFor(final OptionSpec<V> option) {
        return this.defaultValuesFor(option.options().iterator().next());
    }
}
