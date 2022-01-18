// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.sql;

import java.util.ArrayList;
import java.util.List;

class FreeConnectionBuffer
{
    private PooledConnection[] conns;
    private int removeIndex;
    private int addIndex;
    private int size;
    
    protected FreeConnectionBuffer(final int capacity) {
        this.conns = new PooledConnection[capacity];
    }
    
    protected int getCapacity() {
        return this.conns.length;
    }
    
    protected int size() {
        return this.size;
    }
    
    protected boolean isEmpty() {
        return this.size == 0;
    }
    
    protected void add(final PooledConnection pc) {
        this.conns[this.addIndex] = pc;
        this.addIndex = this.inc(this.addIndex);
        ++this.size;
    }
    
    protected PooledConnection remove() {
        final PooledConnection[] items = this.conns;
        final PooledConnection pc = items[this.removeIndex];
        items[this.removeIndex] = null;
        this.removeIndex = this.inc(this.removeIndex);
        --this.size;
        return pc;
    }
    
    protected List<PooledConnection> getShallowCopy() {
        final List<PooledConnection> copy = new ArrayList<PooledConnection>(this.conns.length);
        for (int i = 0; i < this.conns.length; ++i) {
            if (this.conns[i] != null) {
                copy.add(this.conns[i]);
            }
        }
        return copy;
    }
    
    protected void setShallowCopy(final List<PooledConnection> copy) {
        this.removeIndex = 0;
        this.addIndex = 0;
        this.size = 0;
        for (int i = 0; i < this.conns.length; ++i) {
            this.conns[i] = null;
        }
        for (int i = 0; i < copy.size(); ++i) {
            this.add(copy.get(i));
        }
    }
    
    protected void setCapacity(final int newCapacity) {
        if (newCapacity > this.conns.length) {
            final List<PooledConnection> copy = this.getShallowCopy();
            this.removeIndex = 0;
            this.addIndex = 0;
            this.size = 0;
            this.conns = new PooledConnection[newCapacity];
            for (int i = 0; i < copy.size(); ++i) {
                this.add(copy.get(i));
            }
        }
    }
    
    private final int inc(int i) {
        return (++i == this.conns.length) ? 0 : i;
    }
}
