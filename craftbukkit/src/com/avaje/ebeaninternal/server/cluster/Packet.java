// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster;

import java.io.OutputStream;
import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;

public class Packet
{
    public static final short TYPE_MESSAGES = 1;
    public static final short TYPE_TRANSEVENT = 2;
    protected short packetType;
    protected long packetId;
    protected long timestamp;
    protected String serverName;
    protected ByteArrayOutputStream buffer;
    protected DataOutputStream dataOut;
    protected byte[] bytes;
    private int messageCount;
    private int resendCount;
    
    public static Packet forWrite(final short packetType, final long packetId, final long timestamp, final String serverName) throws IOException {
        return new Packet(true, packetType, packetId, timestamp, serverName);
    }
    
    public static Packet readHeader(final DataInput dataInput) throws IOException {
        final short packetType = dataInput.readShort();
        final long packetId = dataInput.readLong();
        final long timestamp = dataInput.readLong();
        final String serverName = dataInput.readUTF();
        return new Packet(false, packetType, packetId, timestamp, serverName);
    }
    
    protected Packet(final boolean write, final short packetType, final long packetId, final long timestamp, final String serverName) throws IOException {
        this.packetType = packetType;
        this.packetId = packetId;
        this.timestamp = timestamp;
        this.serverName = serverName;
        if (write) {
            this.buffer = new ByteArrayOutputStream();
            this.dataOut = new DataOutputStream(this.buffer);
            this.writeHeader();
        }
        else {
            this.buffer = null;
            this.dataOut = null;
        }
    }
    
    private void writeHeader() throws IOException {
        this.dataOut.writeShort(this.packetType);
        this.dataOut.writeLong(this.packetId);
        this.dataOut.writeLong(this.timestamp);
        this.dataOut.writeUTF(this.serverName);
    }
    
    public int incrementResendCount() {
        return this.resendCount++;
    }
    
    public short getPacketType() {
        return this.packetType;
    }
    
    public long getPacketId() {
        return this.packetId;
    }
    
    public long getTimestamp() {
        return this.timestamp;
    }
    
    public String getServerName() {
        return this.serverName;
    }
    
    public void writeEof() throws IOException {
        this.dataOut.writeBoolean(false);
    }
    
    public void read(final DataInput dataInput) throws IOException {
        for (boolean more = dataInput.readBoolean(); more; more = dataInput.readBoolean()) {
            final int msgType = dataInput.readInt();
            this.readMessage(dataInput, msgType);
        }
    }
    
    protected void readMessage(final DataInput dataInput, final int msgType) throws IOException {
    }
    
    public boolean writeBinaryMessage(final BinaryMessage msg, final int maxPacketSize) throws IOException {
        final byte[] bytes = msg.getByteArray();
        if (this.messageCount > 0 && bytes.length + this.buffer.size() > maxPacketSize) {
            this.dataOut.writeBoolean(false);
            return false;
        }
        ++this.messageCount;
        this.dataOut.writeBoolean(true);
        this.dataOut.write(bytes);
        return true;
    }
    
    public int getSize() {
        return this.getBytes().length;
    }
    
    public byte[] getBytes() {
        if (this.bytes == null) {
            this.bytes = this.buffer.toByteArray();
            this.buffer = null;
            this.dataOut = null;
        }
        return this.bytes;
    }
}
