// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster.mcast;

import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import java.io.IOException;
import com.avaje.ebeaninternal.server.cluster.Packet;
import java.util.Iterator;
import java.util.Collection;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.List;
import java.net.UnknownHostException;
import java.net.InetAddress;
import com.avaje.ebean.config.GlobalProperties;
import java.util.ArrayList;
import com.avaje.ebeaninternal.server.cluster.PacketWriter;
import com.avaje.ebeaninternal.server.cluster.ClusterManager;
import java.util.logging.Logger;
import com.avaje.ebeaninternal.server.cluster.ClusterBroadcast;

public class McastClusterManager implements ClusterBroadcast, Runnable
{
    private static final Logger logger;
    private ClusterManager clusterManager;
    private final Thread managerThread;
    private final McastPacketControl packageControl;
    private final McastListener listener;
    private final McastSender localSender;
    private final String localSenderHostPort;
    private final PacketWriter packetWriter;
    private final ArrayList<MessageResend> resendMessages;
    private final ArrayList<MessageControl> controlMessages;
    private final OutgoingPacketsCache outgoingPacketsCache;
    private final IncomingPacketsLastAck incomingPacketsLastAck;
    private final int maxResendOutgoing;
    private long managerSleepMillis;
    private boolean sendWithNoMembers;
    private long minAcked;
    private long minAckedFromListener;
    private int currentGroupSize;
    private long lastSendTime;
    private int lastSendTimeFreqMillis;
    private long lastStatusTime;
    private int lastStatusTimeFreqMillis;
    private long totalTxnEventsSent;
    private long totalTxnEventsReceived;
    private long totalPacketsSent;
    private long totalBytesSent;
    private long totalPacketsResent;
    private long totalBytesResent;
    private long totalPacketsReceived;
    private long totalBytesReceived;
    
    public McastClusterManager() {
        this.resendMessages = new ArrayList<MessageResend>();
        this.controlMessages = new ArrayList<MessageControl>();
        this.outgoingPacketsCache = new OutgoingPacketsCache();
        this.incomingPacketsLastAck = new IncomingPacketsLastAck();
        this.currentGroupSize = -1;
        this.lastStatusTime = System.currentTimeMillis();
        this.managerSleepMillis = GlobalProperties.getInt("ebean.cluster.mcast.managerSleepMillis", 80);
        this.lastSendTimeFreqMillis = 1000 * GlobalProperties.getInt("ebean.cluster.mcast.pingFrequencySecs", 300);
        this.lastStatusTimeFreqMillis = 1000 * GlobalProperties.getInt("ebean.cluster.mcast.statusFrequencySecs", 600);
        this.maxResendOutgoing = GlobalProperties.getInt("ebean.cluster.mcast.maxResendOutgoing", 200);
        final int maxResendIncoming = GlobalProperties.getInt("ebean.cluster.mcast.maxResendIncoming", 50);
        final int port = GlobalProperties.getInt("ebean.cluster.mcast.listen.port", 0);
        final String addr = GlobalProperties.get("ebean.cluster.mcast.listen.address", null);
        final int sendPort = GlobalProperties.getInt("ebean.cluster.mcast.send.port", 0);
        final String sendAddr = GlobalProperties.get("ebean.cluster.mcast.send.address", null);
        final int maxSendPacketSize = GlobalProperties.getInt("ebean.cluster.mcast.send.maxPacketSize", 1500);
        this.sendWithNoMembers = GlobalProperties.getBoolean("ebean.cluster.mcast.send.sendWithNoMembers", true);
        final boolean disableLoopback = GlobalProperties.getBoolean("ebean.cluster.mcast.listen.disableLoopback", false);
        final int ttl = GlobalProperties.getInt("ebean.cluster.mcast.listen.ttl", -1);
        final int timeout = GlobalProperties.getInt("ebean.cluster.mcast.listen.timeout", 1000);
        final int bufferSize = GlobalProperties.getInt("ebean.cluster.mcast.listen.bufferSize", 65500);
        final String mcastAddr = GlobalProperties.get("ebean.cluster.mcast.listen.mcastAddress", null);
        InetAddress mcastAddress = null;
        if (mcastAddr != null) {
            try {
                mcastAddress = InetAddress.getByName(mcastAddr);
            }
            catch (UnknownHostException e) {
                final String msg = "Error getting Multicast InetAddress for " + mcastAddr;
                throw new RuntimeException(msg, e);
            }
        }
        if (port == 0 || addr == null) {
            final String msg2 = "One of these Multicast settings has not been set. ebean.cluster.mcast.listen.port=" + port + ", ebean.cluster.mcast.listen.address=" + addr;
            throw new IllegalArgumentException(msg2);
        }
        this.managerThread = new Thread(this, "EbeanClusterMcastManager");
        this.packetWriter = new PacketWriter(maxSendPacketSize);
        this.localSender = new McastSender(port, addr, sendPort, sendAddr);
        this.localSenderHostPort = this.localSender.getSenderHostPort();
        this.packageControl = new McastPacketControl(this, this.localSenderHostPort, maxResendIncoming);
        this.listener = new McastListener(this, this.packageControl, port, addr, bufferSize, timeout, this.localSenderHostPort, disableLoopback, ttl, mcastAddress);
    }
    
    protected void fromListenerTimeoutNoMembers() {
        synchronized (this.managerThread) {
            this.currentGroupSize = 0;
        }
    }
    
    protected void fromListener(final long newMinAcked, final MessageControl msgControl, final MessageResend msgResend, final int groupSize, final long totalPacketsReceived, final long totalBytesReceived, final long totalTxnEventsReceived) {
        synchronized (this.managerThread) {
            if (newMinAcked > this.minAckedFromListener) {
                this.minAckedFromListener = newMinAcked;
            }
            if (msgControl != null) {
                this.controlMessages.add(msgControl);
            }
            if (msgResend != null) {
                this.resendMessages.add(msgResend);
            }
            this.currentGroupSize = groupSize;
            this.totalPacketsReceived = totalPacketsReceived;
            this.totalBytesReceived = totalBytesReceived;
            this.totalTxnEventsReceived = totalTxnEventsReceived;
        }
    }
    
    public McastStatus getStatus(final boolean reset) {
        synchronized (this.managerThread) {
            final long currentPacketId = this.packetWriter.currentPacketId();
            final String lastAcks = this.incomingPacketsLastAck.toString();
            return new McastStatus(this.currentGroupSize, this.outgoingPacketsCache.size(), currentPacketId, this.minAcked, lastAcks, this.totalTxnEventsSent, this.totalTxnEventsReceived, this.totalPacketsSent, this.totalPacketsResent, this.totalPacketsReceived, this.totalBytesSent, this.totalBytesResent, this.totalBytesReceived);
        }
    }
    
    public void run() {
        while (true) {
            try {
                while (true) {
                    Thread.sleep(this.managerSleepMillis);
                    synchronized (this.managerThread) {
                        this.handleControlMessages();
                        this.handleResendMessages();
                        if (this.currentGroupSize == 0) {
                            final int trimmedCount = this.outgoingPacketsCache.trimAll();
                            if (trimmedCount > 0) {
                                McastClusterManager.logger.fine("Cluster has no other members. Trimmed " + trimmedCount);
                            }
                        }
                        else if (this.minAckedFromListener > this.minAcked) {
                            this.outgoingPacketsCache.trimAcknowledgedMessages(this.minAckedFromListener);
                            this.minAcked = this.minAckedFromListener;
                        }
                        final AckResendMessages ackResendMessages = this.packageControl.getAckResendMessages(this.incomingPacketsLastAck);
                        if (ackResendMessages.size() > 0 && this.sendMessages(false, ackResendMessages.getMessages())) {
                            this.incomingPacketsLastAck.updateLastAck(ackResendMessages);
                        }
                        if (this.lastSendTime < System.currentTimeMillis() - this.lastSendTimeFreqMillis) {
                            this.sendPing();
                        }
                        if (this.lastStatusTimeFreqMillis <= 0 || this.lastStatusTime >= System.currentTimeMillis() - this.lastStatusTimeFreqMillis) {
                            continue;
                        }
                        final McastStatus status = this.getStatus(false);
                        McastClusterManager.logger.info("Cluster Status: " + status.getSummary());
                        this.lastStatusTime = System.currentTimeMillis();
                    }
                }
            }
            catch (Exception e) {
                final String msg = "Error with Cluster Mcast Manager thread";
                McastClusterManager.logger.log(Level.SEVERE, msg, e);
                continue;
            }
            break;
        }
    }
    
    private void handleResendMessages() {
        if (this.resendMessages.size() > 0) {
            final TreeSet<Long> s = new TreeSet<Long>();
            for (int i = 0; i < this.resendMessages.size(); ++i) {
                final MessageResend resendMsg = this.resendMessages.get(i);
                s.addAll(resendMsg.getResendPacketIds());
            }
            this.totalPacketsResent += s.size();
            for (final Long resendPacketId : s) {
                final Packet packet = this.outgoingPacketsCache.getPacket(resendPacketId);
                if (packet == null) {
                    final String msg = "Cluster unable to resend packet[" + resendPacketId + "] as it is no longer in the outgoingPacketsCache";
                    McastClusterManager.logger.log(Level.SEVERE, msg);
                }
                else {
                    final int resendCount = packet.incrementResendCount();
                    if (resendCount <= this.maxResendOutgoing) {
                        this.resendPacket(packet);
                    }
                    else {
                        final String msg2 = "Cluster maxResendOutgoing [" + this.maxResendOutgoing + "] hit for packet " + resendPacketId + ". We will not try to send it anymore, removing it from the outgoingPacketsCache.";
                        McastClusterManager.logger.log(Level.SEVERE, msg2);
                        this.outgoingPacketsCache.remove(packet);
                    }
                }
            }
        }
    }
    
    private void resendPacket(final Packet packet) {
        try {
            ++this.totalPacketsResent;
            this.totalBytesResent += this.localSender.sendPacket(packet);
        }
        catch (IOException e) {
            final String msg = "Error trying to resend packet " + packet.getPacketId();
            McastClusterManager.logger.log(Level.SEVERE, msg, e);
        }
    }
    
    private void handleControlMessages() {
        boolean pingReponse = false;
        boolean joinReponse = false;
        for (int i = 0; i < this.controlMessages.size(); ++i) {
            final MessageControl message = this.controlMessages.get(i);
            final short type = message.getControlType();
            switch (type) {
                case 1: {
                    McastClusterManager.logger.info("Cluster member Joined [" + message.getFromHostPort() + "]");
                    joinReponse = true;
                    break;
                }
                case 7: {
                    McastClusterManager.logger.info("Cluster member Online [" + message.getFromHostPort() + "]");
                    break;
                }
                case 3: {
                    pingReponse = true;
                }
                case 2: {
                    this.incomingPacketsLastAck.remove(message.getFromHostPort());
                    break;
                }
            }
        }
        this.controlMessages.clear();
        if (joinReponse) {
            this.sendJoinResponse();
        }
        if (pingReponse) {
            this.sendPingResponse();
        }
    }
    
    public void shutdown() {
        this.sendLeave();
        this.listener.shutdown();
    }
    
    public void startup(final ClusterManager clusterManager) {
        this.clusterManager = clusterManager;
        this.listener.startListening();
        this.managerThread.setDaemon(true);
        this.managerThread.start();
        this.sendJoin();
    }
    
    protected SpiEbeanServer getEbeanServer(final String serverName) {
        return (SpiEbeanServer)this.clusterManager.getServer(serverName);
    }
    
    private void sendJoin() {
        this.sendControlMessage(true, (short)1);
    }
    
    private void sendLeave() {
        this.sendControlMessage(false, (short)2);
    }
    
    private void sendJoinResponse() {
        this.sendControlMessage(true, (short)7);
    }
    
    private void sendPingResponse() {
        this.sendControlMessage(true, (short)8);
    }
    
    private void sendPing() {
        this.sendControlMessage(true, (short)3);
    }
    
    private void sendControlMessage(final boolean requiresAck, final short controlType) {
        this.sendMessage(requiresAck, new MessageControl(controlType, this.localSenderHostPort));
    }
    
    private void sendMessage(final boolean requiresAck, final Message msg) {
        final ArrayList<Message> messages = new ArrayList<Message>(1);
        messages.add(msg);
        this.sendMessages(requiresAck, messages);
    }
    
    private boolean sendMessages(final boolean requiresAck, final List<? extends Message> messages) {
        synchronized (this.managerThread) {
            try {
                final List<Packet> packets = this.packetWriter.write(requiresAck, messages);
                this.sendPackets(requiresAck, packets);
                return true;
            }
            catch (IOException e) {
                final String msg = "Error sending Messages " + messages;
                McastClusterManager.logger.log(Level.SEVERE, msg, e);
                return false;
            }
        }
    }
    
    private boolean sendPackets(final boolean requiresAck, final List<Packet> packets) throws IOException {
        if (this.currentGroupSize == 0 && !this.sendWithNoMembers) {
            return false;
        }
        if (requiresAck) {
            this.outgoingPacketsCache.registerPackets(packets);
        }
        this.totalPacketsSent += packets.size();
        this.totalBytesSent += this.localSender.sendPackets(packets);
        this.lastSendTime = System.currentTimeMillis();
        return true;
    }
    
    public void broadcast(final RemoteTransactionEvent remoteTransEvent) {
        synchronized (this.managerThread) {
            try {
                final List<Packet> packets = this.packetWriter.write(remoteTransEvent);
                if (this.sendPackets(true, packets)) {
                    ++this.totalTxnEventsSent;
                }
            }
            catch (IOException e) {
                final String msg = "Error sending RemoteTransactionEvent " + remoteTransEvent;
                McastClusterManager.logger.log(Level.SEVERE, msg, e);
            }
        }
    }
    
    public void setManagerSleepMillis(final long managerSleepMillis) {
        synchronized (this.managerThread) {
            this.managerSleepMillis = managerSleepMillis;
        }
    }
    
    public long getManagerSleepMillis() {
        synchronized (this.managerThread) {
            return this.managerSleepMillis;
        }
    }
    
    static {
        logger = Logger.getLogger(McastClusterManager.class.getName());
    }
}
