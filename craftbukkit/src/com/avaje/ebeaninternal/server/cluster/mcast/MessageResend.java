// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster.mcast;

import java.io.DataOutputStream;
import com.avaje.ebeaninternal.server.cluster.BinaryMessage;
import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
import java.io.IOException;
import java.io.DataInput;
import java.util.ArrayList;
import java.util.List;

public class MessageResend implements Message
{
    private final String toHostPort;
    private final List<Long> resendPacketIds;
    
    public MessageResend(final String toHostPort, final List<Long> resendPacketIds) {
        this.toHostPort = toHostPort;
        this.resendPacketIds = resendPacketIds;
    }
    
    public MessageResend(final String toHostPort) {
        this(toHostPort, new ArrayList<Long>(4));
    }
    
    public String toString() {
        return "Resend " + this.toHostPort + " " + this.resendPacketIds;
    }
    
    public boolean isControlMessage() {
        return false;
    }
    
    public String getToHostPort() {
        return this.toHostPort;
    }
    
    public void add(final long packetId) {
        this.resendPacketIds.add(packetId);
    }
    
    public List<Long> getResendPacketIds() {
        return this.resendPacketIds;
    }
    
    public static MessageResend readBinaryMessage(final DataInput dataInput) throws IOException {
        final String hostPort = dataInput.readUTF();
        final MessageResend msg = new MessageResend(hostPort);
        for (int numberOfPacketIds = dataInput.readInt(), i = 0; i < numberOfPacketIds; ++i) {
            final long packetId = dataInput.readLong();
            msg.add(packetId);
        }
        return msg;
    }
    
    public void writeBinaryMessage(final BinaryMessageList msgList) throws IOException {
        final BinaryMessage m = new BinaryMessage(this.toHostPort.length() * 2 + 20);
        final DataOutputStream os = m.getOs();
        os.writeInt(9);
        os.writeUTF(this.toHostPort);
        os.writeInt(this.resendPacketIds.size());
        for (int i = 0; i < this.resendPacketIds.size(); ++i) {
            final Long packetId = this.resendPacketIds.get(i);
            os.writeLong(packetId);
        }
        os.flush();
        msgList.add(m);
    }
}
