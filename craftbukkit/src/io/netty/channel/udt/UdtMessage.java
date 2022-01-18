// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.udt;

import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.DefaultByteBufHolder;

public final class UdtMessage extends DefaultByteBufHolder
{
    public UdtMessage(final ByteBuf data) {
        super(data);
    }
    
    @Override
    public UdtMessage copy() {
        return new UdtMessage(this.data().copy());
    }
}
