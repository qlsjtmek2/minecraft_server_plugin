// 
// Decompiled by Procyon v0.5.30
// 

package org.spigotmc.netty;

import java.io.OutputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import java.io.DataOutputStream;
import io.netty.buffer.ByteBuf;
import net.minecraft.server.v1_5_R3.Packet;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet>
{
    private ByteBuf outBuf;
    private DataOutputStream dataOut;
    private final NettyNetworkManager networkManager;
    
    public PacketEncoder(final NettyNetworkManager networkManager) {
        this.networkManager = networkManager;
    }
    
    public void encode(final ChannelHandlerContext ctx, final Packet msg, final ByteBuf out) throws Exception {
        if (this.outBuf == null) {
            this.outBuf = ctx.alloc().buffer();
        }
        if (this.dataOut == null) {
            this.dataOut = new DataOutputStream(new ByteBufOutputStream(this.outBuf));
        }
        out.writeByte(msg.n());
        msg.a(this.dataOut);
        this.networkManager.addWrittenBytes(this.outBuf.readableBytes());
        out.writeBytes(this.outBuf);
        this.outBuf.discardSomeReadBytes();
    }
    
    public void freeOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        if (this.outBuf != null) {
            this.outBuf.release();
            this.outBuf = null;
        }
    }
}
