// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.socket;

import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.BufUtil;
import io.netty.buffer.ByteBuf;
import java.net.InetSocketAddress;
import io.netty.buffer.DefaultByteBufHolder;

public final class DatagramPacket extends DefaultByteBufHolder
{
    private final InetSocketAddress remoteAddress;
    
    public DatagramPacket(final ByteBuf data, final InetSocketAddress remoteAddress) {
        super(data);
        if (remoteAddress == null) {
            throw new NullPointerException("remoteAddress");
        }
        this.remoteAddress = remoteAddress;
    }
    
    public InetSocketAddress remoteAddress() {
        return this.remoteAddress;
    }
    
    @Override
    public DatagramPacket copy() {
        return new DatagramPacket(this.data().copy(), this.remoteAddress());
    }
    
    @Override
    public String toString() {
        if (this.refCnt() == 0) {
            return "DatagramPacket{remoteAddress=" + this.remoteAddress().toString() + ", data=(FREED)}";
        }
        return "DatagramPacket{remoteAddress=" + this.remoteAddress().toString() + ", data=" + BufUtil.hexDump(this.data()) + '}';
    }
}
