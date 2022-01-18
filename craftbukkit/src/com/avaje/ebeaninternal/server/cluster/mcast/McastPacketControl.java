// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster.mcast;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import com.avaje.ebeaninternal.server.cluster.PacketMessages;
import java.io.DataInput;
import com.avaje.ebeaninternal.server.cluster.Packet;
import java.util.HashSet;
import java.util.logging.Logger;

public class McastPacketControl
{
    private static final Logger logger;
    private final String localSenderHostPort;
    private final McastClusterManager owner;
    private final HashSet<String> groupMembers;
    private final OutgoingPacketsAcked outgoingPacketsAcked;
    private final IncomingPacketsProcessed incomingPacketsProcessed;
    
    public McastPacketControl(final McastClusterManager owner, final String localSenderHostPort, final int maxResendIncoming) {
        this.groupMembers = new HashSet<String>();
        this.outgoingPacketsAcked = new OutgoingPacketsAcked();
        this.owner = owner;
        this.localSenderHostPort = localSenderHostPort;
        this.incomingPacketsProcessed = new IncomingPacketsProcessed(maxResendIncoming);
    }
    
    protected void onListenerTimeout() {
        if (this.groupMembers.size() == 0) {
            this.owner.fromListenerTimeoutNoMembers();
        }
    }
    
    protected void processMessagesPacket(final String senderHostPort, final Packet header, final DataInput dataInput, final long totalPacketsReceived, final long totalBytesReceived, final long totalTransEventsReceived) throws IOException {
        final PacketMessages packetMessages = PacketMessages.forRead(header);
        packetMessages.read(dataInput);
        final List<Message> messages = packetMessages.getMessages();
        if (McastPacketControl.logger.isLoggable(Level.FINER)) {
            McastPacketControl.logger.finer("INCOMING Messages " + messages);
        }
        MessageControl control = null;
        MessageAck ack = null;
        MessageResend resend = null;
        for (int i = 0; i < messages.size(); ++i) {
            final Message message = messages.get(i);
            if (message.isControlMessage()) {
                control = (MessageControl)message;
            }
            else if (this.localSenderHostPort.equals(message.getToHostPort())) {
                if (message instanceof MessageAck) {
                    ack = (MessageAck)message;
                }
                else if (message instanceof MessageResend) {
                    resend = (MessageResend)message;
                }
                else {
                    McastPacketControl.logger.log(Level.SEVERE, "Expecting a MessageAck or MessageResend but got a " + message.getClass().getName());
                }
            }
        }
        if (control != null) {
            if (control.getControlType() == 2) {
                this.groupMembers.remove(senderHostPort);
                McastPacketControl.logger.info("Cluster member leaving [" + senderHostPort + "] " + this.groupMembers.size() + " other members left");
                this.outgoingPacketsAcked.removeMember(senderHostPort);
                this.incomingPacketsProcessed.removeMember(senderHostPort);
            }
            else {
                this.groupMembers.add(senderHostPort);
            }
        }
        long newMin = 0L;
        if (ack != null) {
            newMin = this.outgoingPacketsAcked.receivedAck(senderHostPort, ack);
        }
        if (newMin > 0L || control != null || resend != null) {
            final int groupSize = this.groupMembers.size();
            this.owner.fromListener(newMin, control, resend, groupSize, totalPacketsReceived, totalBytesReceived, totalTransEventsReceived);
        }
    }
    
    public boolean isProcessPacket(final String memberKey, final long packetId) {
        return this.incomingPacketsProcessed.isProcessPacket(memberKey, packetId);
    }
    
    public AckResendMessages getAckResendMessages(final IncomingPacketsLastAck lastAck) {
        return this.incomingPacketsProcessed.getAckResendMessages(lastAck);
    }
    
    static {
        logger = Logger.getLogger(McastPacketControl.class.getName());
    }
}
