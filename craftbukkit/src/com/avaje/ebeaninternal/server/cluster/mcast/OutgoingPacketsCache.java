// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster.mcast;

import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import com.avaje.ebeaninternal.server.cluster.Packet;
import java.util.Map;

public class OutgoingPacketsCache
{
    private final Map<Long, Packet> packetMap;
    
    public OutgoingPacketsCache() {
        this.packetMap = new TreeMap<Long, Packet>();
    }
    
    public int size() {
        return this.packetMap.size();
    }
    
    public Packet getPacket(final Long packetId) {
        return this.packetMap.get(packetId);
    }
    
    public String toString() {
        return this.packetMap.keySet().toString();
    }
    
    public void remove(final Packet packet) {
        this.packetMap.remove(packet.getPacketId());
    }
    
    public void registerPackets(final List<Packet> packets) {
        for (int i = 0; i < packets.size(); ++i) {
            final Packet p = packets.get(i);
            this.packetMap.put(p.getPacketId(), p);
        }
    }
    
    public int trimAll() {
        final int size = this.packetMap.size();
        this.packetMap.clear();
        return size;
    }
    
    public void trimAcknowledgedMessages(final long minAcked) {
        final Iterator<Long> it = this.packetMap.keySet().iterator();
        while (it.hasNext()) {
            final Long pktId = it.next();
            if (minAcked >= pktId) {
                it.remove();
            }
        }
    }
}
