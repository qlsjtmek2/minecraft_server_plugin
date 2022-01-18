// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster;

import java.util.ArrayList;
import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
import java.io.IOException;
import com.avaje.ebeaninternal.server.cluster.mcast.Message;
import java.util.List;

public class PacketWriter
{
    private final PacketIdGenerator idGenerator;
    private final PacketBuilder messagesPacketBuilder;
    private final PacketBuilder transEventPacketBuilder;
    
    public PacketWriter(final int maxPacketSize) {
        this.idGenerator = new PacketIdGenerator();
        this.messagesPacketBuilder = new PacketBuilder(maxPacketSize, this.idGenerator, (PacketFactory)new MessagesPacketFactory());
        this.transEventPacketBuilder = new PacketBuilder(maxPacketSize, this.idGenerator, (PacketFactory)new TransPacketFactory());
    }
    
    public long currentPacketId() {
        return this.idGenerator.currentPacketId();
    }
    
    public List<Packet> write(final boolean requiresAck, final List<? extends Message> messages) throws IOException {
        final BinaryMessageList binaryMsgList = new BinaryMessageList();
        for (int i = 0; i < messages.size(); ++i) {
            final Message message = (Message)messages.get(i);
            message.writeBinaryMessage(binaryMsgList);
        }
        return this.messagesPacketBuilder.write(requiresAck, binaryMsgList, "");
    }
    
    public List<Packet> write(final RemoteTransactionEvent transEvent) throws IOException {
        final BinaryMessageList messageList = new BinaryMessageList();
        transEvent.writeBinaryMessage(messageList);
        return this.transEventPacketBuilder.write(true, messageList, transEvent.getServerName());
    }
    
    private static class PacketIdGenerator
    {
        long packetIdCounter;
        
        public long nextPacketId() {
            return ++this.packetIdCounter;
        }
        
        public long currentPacketId() {
            return this.packetIdCounter;
        }
    }
    
    private static class TransPacketFactory implements PacketFactory
    {
        public Packet createPacket(final long packetId, final long timestamp, final String serverName) throws IOException {
            return PacketTransactionEvent.forWrite(packetId, timestamp, serverName);
        }
    }
    
    private static class MessagesPacketFactory implements PacketFactory
    {
        public Packet createPacket(final long packetId, final long timestamp, final String serverName) throws IOException {
            return PacketMessages.forWrite(packetId, timestamp, serverName);
        }
    }
    
    private static class PacketBuilder
    {
        private final PacketIdGenerator idGenerator;
        private final PacketFactory packetFactory;
        private final int maxPacketSize;
        
        private PacketBuilder(final int maxPacketSize, final PacketIdGenerator idGenerator, final PacketFactory packetFactory) {
            this.maxPacketSize = maxPacketSize;
            this.idGenerator = idGenerator;
            this.packetFactory = packetFactory;
        }
        
        private List<Packet> write(final boolean requiresAck, final BinaryMessageList messageList, final String serverName) throws IOException {
            final List<BinaryMessage> list = messageList.getList();
            final ArrayList<Packet> packets = new ArrayList<Packet>(1);
            final long timestamp = System.currentTimeMillis();
            long packetId = requiresAck ? this.idGenerator.nextPacketId() : 0L;
            Packet p = this.packetFactory.createPacket(packetId, timestamp, serverName);
            packets.add(p);
            for (int i = 0; i < list.size(); ++i) {
                final BinaryMessage binMsg = list.get(i);
                if (!p.writeBinaryMessage(binMsg, this.maxPacketSize)) {
                    packetId = (requiresAck ? this.idGenerator.nextPacketId() : 0L);
                    p = this.packetFactory.createPacket(packetId, timestamp, serverName);
                    packets.add(p);
                    p.writeBinaryMessage(binMsg, this.maxPacketSize);
                }
            }
            p.writeEof();
            return packets;
        }
    }
    
    interface PacketFactory
    {
        Packet createPacket(final long p0, final long p1, final String p2) throws IOException;
    }
}
