// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster.mcast;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class IncomingPacketsProcessed
{
    private final ConcurrentHashMap<String, GotAllPoint> mapByMember;
    private final int maxResendIncoming;
    
    public IncomingPacketsProcessed(final int maxResendIncoming) {
        this.mapByMember = new ConcurrentHashMap<String, GotAllPoint>();
        this.maxResendIncoming = maxResendIncoming;
    }
    
    public void removeMember(final String memberKey) {
        this.mapByMember.remove(memberKey);
    }
    
    public boolean isProcessPacket(final String memberKey, final long packetId) {
        final GotAllPoint memberPackets = this.getMemberPackets(memberKey);
        return memberPackets.processPacket(packetId);
    }
    
    public AckResendMessages getAckResendMessages(final IncomingPacketsLastAck lastAck) {
        final AckResendMessages response = new AckResendMessages();
        for (final GotAllPoint member : this.mapByMember.values()) {
            final MessageAck lastAckMessage = lastAck.getLastAck(member.getMemberKey());
            member.addAckResendMessages(response, lastAckMessage);
        }
        return response;
    }
    
    private GotAllPoint getMemberPackets(final String memberKey) {
        GotAllPoint memberGotAllPoint = this.mapByMember.get(memberKey);
        if (memberGotAllPoint == null) {
            memberGotAllPoint = new GotAllPoint(memberKey, this.maxResendIncoming);
            this.mapByMember.put(memberKey, memberGotAllPoint);
        }
        return memberGotAllPoint;
    }
    
    public static class GotAllPoint
    {
        private static final Logger logger;
        private final String memberKey;
        private final int maxResendIncoming;
        private long gotAllPoint;
        private long gotMaxPoint;
        private ArrayList<Long> outOfOrderList;
        private HashMap<Long, Integer> resendCountMap;
        private static final Integer ONE;
        
        public GotAllPoint(final String memberKey, final int maxResendIncoming) {
            this.outOfOrderList = new ArrayList<Long>();
            this.resendCountMap = new HashMap<Long, Integer>();
            this.memberKey = memberKey;
            this.maxResendIncoming = maxResendIncoming;
        }
        
        public void addAckResendMessages(final AckResendMessages response, final MessageAck lastAckMessage) {
            synchronized (this) {
                if (lastAckMessage == null || lastAckMessage.getGotAllPacketId() < this.gotAllPoint) {
                    response.add(new MessageAck(this.memberKey, this.gotAllPoint));
                }
                if (this.getMissingPacketCount() > 0) {
                    final List<Long> missingPackets = this.getMissingPackets();
                    response.add(new MessageResend(this.memberKey, missingPackets));
                }
            }
        }
        
        public String getMemberKey() {
            return this.memberKey;
        }
        
        public long getGotAllPoint() {
            synchronized (this) {
                return this.gotAllPoint;
            }
        }
        
        public long getGotMaxPoint() {
            synchronized (this) {
                return this.gotMaxPoint;
            }
        }
        
        private int getMissingPacketCount() {
            if (this.gotMaxPoint <= this.gotAllPoint) {
                if (!this.resendCountMap.isEmpty()) {
                    this.resendCountMap.clear();
                }
                return 0;
            }
            return (int)(this.gotMaxPoint - this.gotAllPoint) - this.outOfOrderList.size();
        }
        
        public List<Long> getMissingPackets() {
            synchronized (this) {
                final ArrayList<Long> missingList = new ArrayList<Long>();
                boolean lostPacket = false;
                for (long i = this.gotAllPoint + 1L; i < this.gotMaxPoint; ++i) {
                    final Long packetId = i;
                    if (!this.outOfOrderList.contains(packetId)) {
                        if (this.incrementResendCount(packetId)) {
                            missingList.add(packetId);
                        }
                        else {
                            lostPacket = true;
                        }
                    }
                }
                if (lostPacket) {
                    this.checkOutOfOrderList();
                }
                return missingList;
            }
        }
        
        private boolean incrementResendCount(final Long packetId) {
            Integer resendCount = this.resendCountMap.get(packetId);
            if (resendCount != null) {
                final int i = resendCount + 1;
                if (i > this.maxResendIncoming) {
                    GotAllPoint.logger.warning("Exceeded maxResendIncoming[" + this.maxResendIncoming + "] for packet[" + packetId + "]. Giving up on requesting it.");
                    this.resendCountMap.remove(packetId);
                    this.outOfOrderList.add(packetId);
                    return false;
                }
                resendCount = i;
                this.resendCountMap.put(packetId, resendCount);
            }
            else {
                this.resendCountMap.put(packetId, GotAllPoint.ONE);
            }
            return true;
        }
        
        public boolean processPacket(final long packetId) {
            synchronized (this) {
                if (this.gotAllPoint == 0L) {
                    this.gotAllPoint = packetId;
                    return true;
                }
                if (packetId <= this.gotAllPoint) {
                    return false;
                }
                if (!this.resendCountMap.isEmpty()) {
                    this.resendCountMap.remove(packetId);
                }
                if (packetId == this.gotAllPoint + 1L) {
                    this.gotAllPoint = packetId;
                }
                else {
                    if (packetId > this.gotMaxPoint) {
                        this.gotMaxPoint = packetId;
                    }
                    this.outOfOrderList.add(packetId);
                }
                this.checkOutOfOrderList();
                return true;
            }
        }
        
        private void checkOutOfOrderList() {
            if (this.outOfOrderList.size() == 0) {
                return;
            }
            boolean continueCheck;
            do {
                continueCheck = false;
                final long nextPoint = this.gotAllPoint + 1L;
                final Iterator<Long> it = this.outOfOrderList.iterator();
                while (it.hasNext()) {
                    final Long id = it.next();
                    if (id == nextPoint) {
                        it.remove();
                        this.gotAllPoint = nextPoint;
                        continueCheck = true;
                        break;
                    }
                }
            } while (continueCheck);
        }
        
        static {
            logger = Logger.getLogger(GotAllPoint.class.getName());
            ONE = 1;
        }
    }
}
