// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster.mcast;

import java.io.DataOutputStream;
import com.avaje.ebeaninternal.server.cluster.BinaryMessage;
import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
import java.io.IOException;
import java.io.DataInput;

public class MessageControl implements Message
{
    public static final short TYPE_JOIN = 1;
    public static final short TYPE_LEAVE = 2;
    public static final short TYPE_PING = 3;
    public static final short TYPE_JOINRESPONSE = 7;
    public static final short TYPE_PINGRESPONSE = 8;
    private final short controlType;
    private final String fromHostPort;
    
    public static MessageControl readBinaryMessage(final DataInput dataInput) throws IOException {
        final short controlType = dataInput.readShort();
        final String hostPort = dataInput.readUTF();
        return new MessageControl(controlType, hostPort);
    }
    
    public MessageControl(final short controlType, final String helloFromHostPort) {
        this.controlType = controlType;
        this.fromHostPort = helloFromHostPort;
    }
    
    public String toString() {
        switch (this.controlType) {
            case 1: {
                return "Join " + this.fromHostPort;
            }
            case 2: {
                return "Leave " + this.fromHostPort;
            }
            case 3: {
                return "Ping " + this.fromHostPort;
            }
            case 8: {
                return "PingResponse " + this.fromHostPort;
            }
            default: {
                throw new RuntimeException("Invalid controlType " + this.controlType);
            }
        }
    }
    
    public boolean isControlMessage() {
        return true;
    }
    
    public short getControlType() {
        return this.controlType;
    }
    
    public String getToHostPort() {
        return "*";
    }
    
    public String getFromHostPort() {
        return this.fromHostPort;
    }
    
    public void writeBinaryMessage(final BinaryMessageList msgList) throws IOException {
        final BinaryMessage m = new BinaryMessage(this.fromHostPort.length() * 2 + 10);
        final DataOutputStream os = m.getOs();
        os.writeInt(0);
        os.writeShort(this.controlType);
        os.writeUTF(this.fromHostPort);
        os.flush();
        msgList.add(m);
    }
}
