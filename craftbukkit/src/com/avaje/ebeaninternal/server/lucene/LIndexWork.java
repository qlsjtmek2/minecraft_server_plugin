// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

public final class LIndexWork
{
    private final WorkType workType;
    private final LIndexUpdateFuture future;
    private final IndexUpdates indexUpdates;
    
    public static LIndexWork newRebuild(final LIndexUpdateFuture future) {
        return new LIndexWork(WorkType.REBUILD, future, null);
    }
    
    public static LIndexWork newQueryUpdate(final LIndexUpdateFuture future) {
        return new LIndexWork(WorkType.QUERY_UPDATE, future, null);
    }
    
    public static LIndexWork newTxnUpdate(final LIndexUpdateFuture future, final IndexUpdates indexUpdates) {
        return new LIndexWork(WorkType.TXN_UPDATE, future, indexUpdates);
    }
    
    private LIndexWork(final WorkType workType, final LIndexUpdateFuture future, final IndexUpdates indexUpdates) {
        this.workType = workType;
        this.future = future;
        this.indexUpdates = indexUpdates;
    }
    
    public WorkType getWorkType() {
        return this.workType;
    }
    
    public IndexUpdates getIndexUpdates() {
        return this.indexUpdates;
    }
    
    public LIndexUpdateFuture getFuture() {
        return this.future;
    }
    
    public enum WorkType
    {
        TXN_UPDATE, 
        QUERY_UPDATE, 
        REBUILD;
    }
}
