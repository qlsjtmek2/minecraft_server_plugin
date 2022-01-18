// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster.mcast;

public class McastStatus
{
    private final long totalTxnEventsSent;
    private final long totalTxnEventsReceived;
    private final long totalPacketsSent;
    private final long totalPacketsResent;
    private final long totalPacketsReceived;
    private final long totalBytesSent;
    private final long totalBytesResent;
    private final long totalBytesReceived;
    private final int currentGroupSize;
    private final int outgoingPacketsCacheSize;
    private final long currentPacketId;
    private final long minAckedPacketId;
    private final String lastOutgoingAcks;
    
    public String getSummary() {
        final StringBuilder sb = new StringBuilder(80);
        sb.append("txnOut:").append(this.totalTxnEventsSent).append("; ");
        sb.append("txnIn:").append(this.totalTxnEventsReceived).append("; ");
        sb.append("outPackets:").append(this.totalPacketsSent).append("; ");
        sb.append("outBytes:").append(this.totalBytesSent).append("; ");
        sb.append("inPackets:").append(this.totalPacketsReceived).append("; ");
        sb.append("inBytes:").append(this.totalBytesReceived).append("; ");
        sb.append("resentPackets:").append(this.totalPacketsResent).append("; ");
        sb.append("resentBytes:").append(this.totalBytesResent).append("; ");
        sb.append("groupSize:").append(this.currentGroupSize).append("; ");
        sb.append("cache:").append(this.outgoingPacketsCacheSize).append("; ");
        sb.append("currentPacket:").append(this.currentPacketId).append("; ");
        sb.append("minAckedPacket:").append(this.minAckedPacketId).append("; ");
        sb.append("lastAck:").append(this.lastOutgoingAcks).append("; ");
        return sb.toString();
    }
    
    public McastStatus(final int currentGroupSize, final int outgoingPacketsCacheSize, final long currentPacketId, final long minAckedPacketId, final String lastOutgoingAcks, final long totalTransEventsSent, final long totalTransEventsReceived, final long totalPacketsSent, final long totalPacketsResent, final long totalPacketsReceived, final long totalBytesSent, final long totalBytesResent, final long totalBytesReceived) {
        this.currentGroupSize = currentGroupSize;
        this.outgoingPacketsCacheSize = outgoingPacketsCacheSize;
        this.currentPacketId = currentPacketId;
        this.minAckedPacketId = minAckedPacketId;
        this.lastOutgoingAcks = lastOutgoingAcks;
        this.totalTxnEventsSent = totalTransEventsSent;
        this.totalTxnEventsReceived = totalTransEventsReceived;
        this.totalPacketsSent = totalPacketsSent;
        this.totalPacketsResent = totalPacketsResent;
        this.totalPacketsReceived = totalPacketsReceived;
        this.totalBytesSent = totalBytesSent;
        this.totalBytesResent = totalBytesResent;
        this.totalBytesReceived = totalBytesReceived;
    }
    
    public long getTotalTxnEventsReceived() {
        return this.totalTxnEventsReceived;
    }
    
    public long getTotalPacketsReceived() {
        return this.totalPacketsReceived;
    }
    
    public long getTotalBytesSent() {
        return this.totalBytesSent;
    }
    
    public long getTotalBytesResent() {
        return this.totalBytesResent;
    }
    
    public long getTotalBytesReceived() {
        return this.totalBytesReceived;
    }
    
    public String getLastOutgoingAcks() {
        return this.lastOutgoingAcks;
    }
    
    public int getOutgoingPacketsCacheSize() {
        return this.outgoingPacketsCacheSize;
    }
    
    public long getCurrentPacketId() {
        return this.currentPacketId;
    }
    
    public long getMinAckedPacketId() {
        return this.minAckedPacketId;
    }
    
    public long getTotalTxnEventsSent() {
        return this.totalTxnEventsSent;
    }
    
    public long getTotalPacketsSent() {
        return this.totalPacketsSent;
    }
    
    public long getTotalPacketsResent() {
        return this.totalPacketsResent;
    }
    
    public long getCurrentGroupSize() {
        return this.currentGroupSize;
    }
}
