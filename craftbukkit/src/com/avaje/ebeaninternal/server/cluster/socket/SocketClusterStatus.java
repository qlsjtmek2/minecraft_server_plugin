// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster.socket;

public class SocketClusterStatus
{
    private final int currentGroupSize;
    private final int txnIncoming;
    private final int txtOutgoing;
    
    public SocketClusterStatus(final int currentGroupSize, final int txnIncoming, final int txnOutgoing) {
        this.currentGroupSize = currentGroupSize;
        this.txnIncoming = txnIncoming;
        this.txtOutgoing = txnOutgoing;
    }
    
    public int getCurrentGroupSize() {
        return this.currentGroupSize;
    }
    
    public int getTxnIncoming() {
        return this.txnIncoming;
    }
    
    public int getTxtOutgoing() {
        return this.txtOutgoing;
    }
}
