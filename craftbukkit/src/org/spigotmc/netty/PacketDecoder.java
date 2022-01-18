// 
// Decompiled by Procyon v0.5.30
// 

package org.spigotmc.netty;

import java.io.EOFException;
import java.io.IOException;
import net.minecraft.server.v1_5_R3.MinecraftServer;
import java.io.InputStream;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.server.v1_5_R3.Packet;
import java.io.DataInputStream;
import io.netty.handler.codec.ReplayingDecoder;

public class PacketDecoder extends ReplayingDecoder<ReadState>
{
    private DataInputStream input;
    private Packet packet;
    
    public PacketDecoder() {
        super(ReadState.HEADER);
    }
    
    protected Packet decode(final ChannelHandlerContext ctx, final ByteBuf in) throws Exception {
        if (this.input == null) {
            this.input = new DataInputStream(new ByteBufInputStream(in));
        }
        switch (this.state()) {
            case HEADER: {
                final short packetId = in.readUnsignedByte();
                this.packet = Packet.a(MinecraftServer.getServer().getLogger(), packetId);
                if (this.packet == null) {
                    throw new IOException("Bad packet id " + packetId);
                }
                this.checkpoint(ReadState.DATA);
            }
            case DATA: {
                try {
                    this.packet.a(this.input);
                }
                catch (EOFException ex) {
                    return null;
                }
                this.checkpoint(ReadState.HEADER);
                final Packet readPacket = this.packet;
                this.packet = null;
                return readPacket;
            }
            default: {
                throw new IllegalStateException();
            }
        }
    }
}
