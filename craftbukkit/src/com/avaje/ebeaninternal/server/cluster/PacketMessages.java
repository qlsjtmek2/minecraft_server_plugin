// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster;

import com.avaje.ebeaninternal.server.cluster.mcast.MessageResend;
import com.avaje.ebeaninternal.server.cluster.mcast.MessageAck;
import com.avaje.ebeaninternal.server.cluster.mcast.MessageControl;
import java.io.DataInput;
import java.util.List;
import java.io.IOException;
import com.avaje.ebeaninternal.server.cluster.mcast.Message;
import java.util.ArrayList;

public class PacketMessages extends Packet
{
    private final ArrayList<Message> messages;
    
    public static PacketMessages forWrite(final long packetId, final long timestamp, final String serverName) throws IOException {
        return new PacketMessages(true, packetId, timestamp, serverName);
    }
    
    public static PacketMessages forRead(final Packet header) throws IOException {
        return new PacketMessages(header);
    }
    
    private PacketMessages(final boolean write, final long packetId, final long timestamp, final String serverName) throws IOException {
        super(write, (short)1, packetId, timestamp, serverName);
        this.messages = null;
    }
    
    private PacketMessages(final Packet header) throws IOException {
        super(false, (short)1, header.packetId, header.timestamp, header.serverName);
        this.messages = new ArrayList<Message>();
    }
    
    public List<Message> getMessages() {
        return this.messages;
    }
    
    protected void readMessage(final DataInput dataInput, final int msgType) throws IOException {
        switch (msgType) {
            case 0: {
                this.messages.add(MessageControl.readBinaryMessage(dataInput));
                break;
            }
            case 8: {
                this.messages.add(MessageAck.readBinaryMessage(dataInput));
                break;
            }
            case 9: {
                this.messages.add(MessageResend.readBinaryMessage(dataInput));
                break;
            }
            default: {
                throw new RuntimeException("Invalid Transaction msgType " + msgType);
            }
        }
    }
}
