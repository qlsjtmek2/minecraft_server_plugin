// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.joptsimple;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.bukkit.craftbukkit.libs.joptsimple.internal.Classes;
import java.util.List;
import org.bukkit.craftbukkit.libs.joptsimple.internal.Strings;
import java.util.Iterator;
import java.util.Set;
import java.util.Collection;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.Map;
import org.bukkit.craftbukkit.libs.joptsimple.internal.ColumnarData;

class HelpFormatter implements OptionSpecVisitor
{
    private final ColumnarData grid;
    
    HelpFormatter() {
        this.grid = new ColumnarData(new String[] { "Option", "Description" });
    }
    
    String format(final Map<String, AbstractOptionSpec<?>> options) {
        if (options.isEmpty()) {
            return "No options specified";
        }
        this.grid.clear();
        final Comparator<AbstractOptionSpec<?>> comparator = new Comparator<AbstractOptionSpec<?>>() {
            public int compare(final AbstractOptionSpec<?> first, final AbstractOptionSpec<?> second) {
                return first.options().iterator().next().compareTo((String)second.options().iterator().next());
            }
        };
        final Set<AbstractOptionSpec<?>> sorted = new TreeSet<AbstractOptionSpec<?>>(comparator);
        sorted.addAll(options.values());
        for (final AbstractOptionSpec<?> each : sorted) {
            each.accept(this);
        }
        return this.grid.format();
    }
    
    void addHelpLineFor(final AbstractOptionSpec<?> spec, final String additionalInfo) {
        this.grid.addRow(this.createOptionDisplay(spec) + additionalInfo, this.createDescriptionDisplay(spec));
    }
    
    public void visit(final NoArgumentOptionSpec spec) {
        this.addHelpLineFor(spec, "");
    }
    
    public void visit(final RequiredArgumentOptionSpec<?> spec) {
        this.visit(spec, '<', '>');
    }
    
    public void visit(final OptionalArgumentOptionSpec<?> spec) {
        this.visit(spec, '[', ']');
    }
    
    public void visit(final AlternativeLongOptionSpec spec) {
        this.addHelpLineFor(spec, ' ' + Strings.surround(spec.argumentDescription(), '<', '>'));
    }
    
    private void visit(final ArgumentAcceptingOptionSpec<?> spec, final char begin, final char end) {
        final String argDescription = spec.argumentDescription();
        final String typeIndicator = typeIndicator(spec);
        final StringBuilder collector = new StringBuilder();
        if (typeIndicator.length() > 0) {
            collector.append(typeIndicator);
            if (argDescription.length() > 0) {
                collector.append(": ").append(argDescription);
            }
        }
        else if (argDescription.length() > 0) {
            collector.append(argDescription);
        }
        final String helpLine = (collector.length() == 0) ? "" : (' ' + Strings.surround(collector.toString(), begin, end));
        this.addHelpLineFor(spec, helpLine);
    }
    
    private String createOptionDisplay(final AbstractOptionSpec<?> spec) {
        final StringBuilder buffer = new StringBuilder();
        final Iterator<String> iter = spec.options().iterator();
        while (iter.hasNext()) {
            final String option = iter.next();
            buffer.append((option.length() > 1) ? "--" : ParserRules.HYPHEN);
            buffer.append(option);
            if (iter.hasNext()) {
                buffer.append(", ");
            }
        }
        return buffer.toString();
    }
    
    private String createDescriptionDisplay(final AbstractOptionSpec<?> spec) {
        final List<?> defaultValues = spec.defaultValues();
        if (defaultValues.isEmpty()) {
            return spec.description();
        }
        final String defaultValuesDisplay = this.createDefaultValuesDisplay(defaultValues);
        return spec.description() + ' ' + Strings.surround("default: " + defaultValuesDisplay, '(', ')');
    }
    
    private String createDefaultValuesDisplay(final List<?> defaultValues) {
        return (defaultValues.size() == 1) ? defaultValues.get(0).toString() : defaultValues.toString();
    }
    
    private static String typeIndicator(final ArgumentAcceptingOptionSpec<?> spec) {
        final String indicator = spec.typeIndicator();
        return (indicator == null || String.class.getName().equals(indicator)) ? "" : Classes.shortNameOf(indicator);
    }
}
