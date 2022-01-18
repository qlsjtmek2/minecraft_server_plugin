// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.jline.console.history;

import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.LinkedList;

public class MemoryHistory implements History
{
    public static final int DEFAULT_MAX_SIZE = 500;
    private final LinkedList<CharSequence> items;
    private int maxSize;
    private boolean ignoreDuplicates;
    private boolean autoTrim;
    private int offset;
    private int index;
    
    public MemoryHistory() {
        this.items = new LinkedList<CharSequence>();
        this.maxSize = 500;
        this.ignoreDuplicates = true;
        this.autoTrim = false;
        this.offset = 0;
        this.index = 0;
    }
    
    public void setMaxSize(final int maxSize) {
        this.maxSize = maxSize;
        this.maybeResize();
    }
    
    public int getMaxSize() {
        return this.maxSize;
    }
    
    public boolean isIgnoreDuplicates() {
        return this.ignoreDuplicates;
    }
    
    public void setIgnoreDuplicates(final boolean flag) {
        this.ignoreDuplicates = flag;
    }
    
    public boolean isAutoTrim() {
        return this.autoTrim;
    }
    
    public void setAutoTrim(final boolean flag) {
        this.autoTrim = flag;
    }
    
    public int size() {
        return this.items.size();
    }
    
    public boolean isEmpty() {
        return this.items.isEmpty();
    }
    
    public int index() {
        return this.offset + this.index;
    }
    
    public void clear() {
        this.items.clear();
        this.offset = 0;
        this.index = 0;
    }
    
    public CharSequence get(final int index) {
        return this.items.get(index - this.offset);
    }
    
    public void add(CharSequence item) {
        assert item != null;
        if (this.isAutoTrim()) {
            item = String.valueOf(item).trim();
        }
        if (this.isIgnoreDuplicates() && !this.items.isEmpty() && item.equals(this.items.getLast())) {
            return;
        }
        this.internalAdd(item);
    }
    
    protected void internalAdd(final CharSequence item) {
        this.items.add(item);
        this.maybeResize();
    }
    
    public void replace(final CharSequence item) {
        this.items.removeLast();
        this.add(item);
    }
    
    private void maybeResize() {
        while (this.size() > this.getMaxSize()) {
            this.items.removeFirst();
            ++this.offset;
        }
        this.index = this.size();
    }
    
    public ListIterator<Entry> entries(final int index) {
        return new EntriesIterator(index - this.offset);
    }
    
    public ListIterator<Entry> entries() {
        return this.entries(this.offset);
    }
    
    public Iterator<Entry> iterator() {
        return this.entries();
    }
    
    public boolean moveToLast() {
        final int lastEntry = this.size() - 1;
        if (lastEntry >= 0 && lastEntry != this.index) {
            this.index = this.size() - 1;
            return true;
        }
        return false;
    }
    
    public boolean moveTo(int index) {
        index -= this.offset;
        if (index >= 0 && index < this.size()) {
            this.index = index;
            return true;
        }
        return false;
    }
    
    public boolean moveToFirst() {
        if (this.size() > 0 && this.index != 0) {
            this.index = 0;
            return true;
        }
        return false;
    }
    
    public void moveToEnd() {
        this.index = this.size();
    }
    
    public CharSequence current() {
        if (this.index >= this.size()) {
            return "";
        }
        return this.items.get(this.index);
    }
    
    public boolean previous() {
        if (this.index <= 0) {
            return false;
        }
        --this.index;
        return true;
    }
    
    public boolean next() {
        if (this.index >= this.size()) {
            return false;
        }
        ++this.index;
        return true;
    }
    
    private static class EntryImpl implements Entry
    {
        private final int index;
        private final CharSequence value;
        
        public EntryImpl(final int index, final CharSequence value) {
            this.index = index;
            this.value = value;
        }
        
        public int index() {
            return this.index;
        }
        
        public CharSequence value() {
            return this.value;
        }
        
        public String toString() {
            return String.format("%d: %s", this.index, this.value);
        }
    }
    
    private class EntriesIterator implements ListIterator<Entry>
    {
        private final ListIterator<CharSequence> source;
        
        private EntriesIterator(final int index) {
            this.source = MemoryHistory.this.items.listIterator(index);
        }
        
        public Entry next() {
            if (!this.source.hasNext()) {
                throw new NoSuchElementException();
            }
            return new EntryImpl(MemoryHistory.this.offset + this.source.nextIndex(), this.source.next());
        }
        
        public Entry previous() {
            if (!this.source.hasPrevious()) {
                throw new NoSuchElementException();
            }
            return new EntryImpl(MemoryHistory.this.offset + this.source.previousIndex(), this.source.previous());
        }
        
        public int nextIndex() {
            return MemoryHistory.this.offset + this.source.nextIndex();
        }
        
        public int previousIndex() {
            return MemoryHistory.this.offset + this.source.previousIndex();
        }
        
        public boolean hasNext() {
            return this.source.hasNext();
        }
        
        public boolean hasPrevious() {
            return this.source.hasPrevious();
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public void set(final Entry entry) {
            throw new UnsupportedOperationException();
        }
        
        public void add(final Entry entry) {
            throw new UnsupportedOperationException();
        }
    }
}
