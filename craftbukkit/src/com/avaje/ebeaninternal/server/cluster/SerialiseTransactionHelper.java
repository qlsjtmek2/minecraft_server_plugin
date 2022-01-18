// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster;

import java.io.DataInput;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
import com.avaje.ebeaninternal.api.SpiEbeanServer;

public abstract class SerialiseTransactionHelper
{
    private final PacketWriter packetWriter;
    
    public SerialiseTransactionHelper() {
        this.packetWriter = new PacketWriter(Integer.MAX_VALUE);
    }
    
    public abstract SpiEbeanServer getEbeanServer(final String p0);
    
    public DataHolder createDataHolder(final RemoteTransactionEvent transEvent) throws IOException {
        final List<Packet> packetList = this.packetWriter.write(transEvent);
        if (packetList.size() != 1) {
            throw new RuntimeException("Always expecting 1 Packet but got " + packetList.size());
        }
        final byte[] data = packetList.get(0).getBytes();
        return new DataHolder(data);
    }
    
    public RemoteTransactionEvent read(final DataHolder dataHolder) throws IOException {
        final ByteArrayInputStream bi = new ByteArrayInputStream(dataHolder.getData());
        final DataInputStream dataInput = new DataInputStream(bi);
        final Packet header = Packet.readHeader(dataInput);
        final SpiEbeanServer server = this.getEbeanServer(header.getServerName());
        final PacketTransactionEvent tranEventPacket = PacketTransactionEvent.forRead(header, server);
        tranEventPacket.read(dataInput);
        return tranEventPacket.getEvent();
    }
}
