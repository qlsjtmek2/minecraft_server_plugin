// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster.mcast;

import java.io.DataOutputStream;
import com.avaje.ebeaninternal.server.cluster.BinaryMessage;
import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
import java.io.IOException;
import java.io.DataInput;

public class MessageAck implements Message
{
    private final String toHostPort;
    private final long gotAllPacketId;
    
    public MessageAck(final String toHostPort, final long gotAllPacketId) {
        this.toHostPort = toHostPort;
        this.gotAllPacketId = gotAllPacketId;
    }
    
    public String toString() {
        return "Ack " + this.toHostPort + " " + this.gotAllPacketId;
    }
    
    public boolean isControlMessage() {
        return false;
    }
    
    public String getToHostPort() {
        return this.toHostPort;
    }
    
    public long getGotAllPacketId() {
        return this.gotAllPacketId;
    }
    
    public static MessageAck readBinaryMessage(final DataInput dataInput) throws IOException {
        final String hostPort = dataInput.readUTF();
        final long gotAllPacketId = dataInput.readLong();
        return new MessageAck(hostPort, gotAllPacketId);
    }
    
    public void writeBinaryMessage(final BinaryMessageList msgList) throws IOException {
        final BinaryMessage m = new BinaryMessage(this.toHostPort.length() * 2 + 20);
        final DataOutputStream os = m.getOs();
        os.writeInt(8);
        os.writeUTF(this.toHostPort);
        os.writeLong(this.gotAllPacketId);
        os.flush();
        msgList.add(m);
    }
}
