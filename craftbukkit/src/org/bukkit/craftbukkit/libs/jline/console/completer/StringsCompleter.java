// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.jline.console.completer;

import java.util.Iterator;
import java.util.List;
import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;
import java.util.SortedSet;

public class StringsCompleter implements Completer
{
    private final SortedSet<String> strings;
    
    public StringsCompleter() {
        this.strings = new TreeSet<String>();
    }
    
    public StringsCompleter(final Collection<String> strings) {
        this.strings = new TreeSet<String>();
        assert strings != null;
        this.getStrings().addAll(strings);
    }
    
    public StringsCompleter(final String... strings) {
        this(Arrays.asList(strings));
    }
    
    public Collection<String> getStrings() {
        return this.strings;
    }
    
    public int complete(final String buffer, final int cursor, final List<CharSequence> candidates) {
        assert candidates != null;
        if (buffer == null) {
            candidates.addAll(this.strings);
        }
        else {
            for (final String match : this.strings.tailSet(buffer)) {
                if (!match.startsWith(buffer)) {
                    break;
                }
                candidates.add(match);
            }
        }
        if (candidates.size() == 1) {
            candidates.set(0, (Object)candidates.get(0) + " ");
        }
        return candidates.isEmpty() ? -1 : 0;
    }
}
