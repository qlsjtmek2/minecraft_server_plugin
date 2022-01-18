// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster.mcast;

import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;

public class OutgoingPacketsAcked
{
    private long minimumGotAllPacketId;
    private Map<String, GroupMemberAck> recievedByMap;
    
    public OutgoingPacketsAcked() {
        this.recievedByMap = new HashMap<String, GroupMemberAck>();
    }
    
    public int getGroupSize() {
        synchronized (this) {
            return this.recievedByMap.size();
        }
    }
    
    public long getMinimumGotAllPacketId() {
        synchronized (this) {
            return this.minimumGotAllPacketId;
        }
    }
    
    public void removeMember(final String groupMember) {
        synchronized (this) {
            this.recievedByMap.remove(groupMember);
            this.resetGotAllMin();
        }
    }
    
    private boolean resetGotAllMin() {
        long tempMin;
        if (this.recievedByMap.isEmpty()) {
            tempMin = Long.MAX_VALUE;
        }
        else {
            tempMin = Long.MAX_VALUE;
        }
        for (final GroupMemberAck groupMemAck : this.recievedByMap.values()) {
            final long memberMin = groupMemAck.getGotAllPacketId();
            if (memberMin < tempMin) {
                tempMin = memberMin;
            }
        }
        if (tempMin != this.minimumGotAllPacketId) {
            this.minimumGotAllPacketId = tempMin;
            return true;
        }
        return false;
    }
    
    public long receivedAck(final String groupMember, final MessageAck ack) {
        synchronized (this) {
            boolean checkMin = false;
            GroupMemberAck groupMemberAck = this.recievedByMap.get(groupMember);
            if (groupMemberAck == null) {
                groupMemberAck = new GroupMemberAck();
                groupMemberAck.setIfBigger(ack.getGotAllPacketId());
                this.recievedByMap.put(groupMember, groupMemberAck);
                checkMin = true;
            }
            else {
                checkMin = (groupMemberAck.getGotAllPacketId() == this.minimumGotAllPacketId);
                groupMemberAck.setIfBigger(ack.getGotAllPacketId());
            }
            boolean minChanged = false;
            if (checkMin || this.minimumGotAllPacketId == 0L) {
                minChanged = this.resetGotAllMin();
            }
            return minChanged ? this.minimumGotAllPacketId : 0L;
        }
    }
    
    private static class GroupMemberAck
    {
        private long gotAllPacketId;
        
        private long getGotAllPacketId() {
            return this.gotAllPacketId;
        }
        
        private void setIfBigger(final long newGotAll) {
            if (newGotAll > this.gotAllPacketId) {
                this.gotAllPacketId = newGotAll;
            }
        }
    }
}
