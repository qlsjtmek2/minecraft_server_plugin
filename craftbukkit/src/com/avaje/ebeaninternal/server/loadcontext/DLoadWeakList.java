// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.loadcontext;

import java.util.List;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class DLoadWeakList<T>
{
    protected final ArrayList<WeakReference<T>> list;
    protected int removedFromTop;
    
    protected DLoadWeakList() {
        this.list = new ArrayList<WeakReference<T>>();
    }
    
    public int add(final T e) {
        synchronized (this) {
            final int i = this.list.size();
            this.list.add(new WeakReference<T>(e));
            return i;
        }
    }
    
    public List<T> getNextBatch(final int batchSize) {
        synchronized (this) {
            return this.getBatch(0, batchSize);
        }
    }
    
    public List<T> getLoadBatch(final int position, final int batchSize) {
        synchronized (this) {
            if (batchSize < 1) {
                throw new RuntimeException("batchSize " + batchSize + " < 1 ??!!");
            }
            int relativePos = position - this.removedFromTop;
            if (relativePos - batchSize < 0) {
                relativePos = 0;
            }
            if (relativePos > 0 && relativePos + batchSize > this.list.size()) {
                relativePos = this.list.size() - batchSize;
                if (relativePos < 0) {
                    relativePos = 0;
                }
            }
            return this.getBatch(relativePos, batchSize);
        }
    }
    
    private List<T> getBatch(int relativePos, final int batchSize) {
        final ArrayList<T> batch = new ArrayList<T>();
        int count = 0;
        final boolean removeFromTop = relativePos == 0;
        while (count < batchSize) {
            if (this.list.isEmpty()) {
                break;
            }
            WeakReference<T> weakEntry;
            if (removeFromTop) {
                weakEntry = this.list.remove(relativePos);
                ++this.removedFromTop;
            }
            else {
                if (relativePos >= this.list.size()) {
                    break;
                }
                weakEntry = this.list.get(relativePos);
                this.list.set(relativePos, null);
                ++relativePos;
            }
            final T ebi = (weakEntry == null) ? null : weakEntry.get();
            if (ebi == null) {
                continue;
            }
            batch.add(ebi);
            ++count;
        }
        return batch;
    }
}
