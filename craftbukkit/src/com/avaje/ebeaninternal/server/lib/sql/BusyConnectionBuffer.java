// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.sql;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

class BusyConnectionBuffer
{
    private PooledConnection[] slots;
    private int growBy;
    private int size;
    private int pos;
    
    protected BusyConnectionBuffer(final int capacity, final int growBy) {
        this.pos = -1;
        this.slots = new PooledConnection[capacity];
        this.growBy = growBy;
    }
    
    private void setCapacity(final int newCapacity) {
        if (newCapacity > this.slots.length) {
            final PooledConnection[] current = this.slots;
            System.arraycopy(current, 0, this.slots = new PooledConnection[newCapacity], 0, current.length);
        }
    }
    
    public String toString() {
        return Arrays.toString(this.slots);
    }
    
    protected int getCapacity() {
        return this.slots.length;
    }
    
    protected int size() {
        return this.size;
    }
    
    protected boolean isEmpty() {
        return this.size == 0;
    }
    
    protected int add(final PooledConnection pc) {
        if (this.size == this.slots.length) {
            this.setCapacity(this.slots.length + this.growBy);
        }
        ++this.size;
        final int slot = this.nextEmptySlot();
        pc.setSlotId(slot);
        this.slots[slot] = pc;
        return this.size;
    }
    
    protected boolean remove(final PooledConnection pc) {
        --this.size;
        final int slotId = pc.getSlotId();
        if (this.slots[slotId] != pc) {
            return false;
        }
        this.slots[slotId] = null;
        return true;
    }
    
    protected List<PooledConnection> getShallowCopy() {
        final ArrayList<PooledConnection> tmp = new ArrayList<PooledConnection>();
        for (int i = 0; i < this.slots.length; ++i) {
            if (this.slots[i] != null) {
                tmp.add(this.slots[i]);
            }
        }
        return Collections.unmodifiableList((List<? extends PooledConnection>)tmp);
    }
    
    private int nextEmptySlot() {
        while (++this.pos < this.slots.length) {
            if (this.slots[this.pos] == null) {
                return this.pos;
            }
        }
        this.pos = -1;
        while (++this.pos < this.slots.length) {
            if (this.slots[this.pos] == null) {
                return this.pos;
            }
        }
        throw new RuntimeException("No Empty Slot Found?");
    }
}
