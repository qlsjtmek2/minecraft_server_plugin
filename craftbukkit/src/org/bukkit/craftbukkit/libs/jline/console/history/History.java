// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.jline.console.history;

import java.util.Iterator;
import java.util.ListIterator;

public interface History extends Iterable<Entry>
{
    int size();
    
    boolean isEmpty();
    
    int index();
    
    void clear();
    
    CharSequence get(final int p0);
    
    void add(final CharSequence p0);
    
    void replace(final CharSequence p0);
    
    ListIterator<Entry> entries(final int p0);
    
    ListIterator<Entry> entries();
    
    Iterator<Entry> iterator();
    
    CharSequence current();
    
    boolean previous();
    
    boolean next();
    
    boolean moveToFirst();
    
    boolean moveToLast();
    
    boolean moveTo(final int p0);
    
    void moveToEnd();
    
    public interface Entry
    {
        int index();
        
        CharSequence value();
    }
}
