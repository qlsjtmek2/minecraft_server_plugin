// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class PingWebSocketFrame extends WebSocketFrame
{
    public PingWebSocketFrame() {
        super(true, 0, Unpooled.buffer(0));
    }
    
    public PingWebSocketFrame(final ByteBuf binaryData) {
        super(binaryData);
    }
    
    public PingWebSocketFrame(final boolean finalFragment, final int rsv, final ByteBuf binaryData) {
        super(finalFragment, rsv, binaryData);
    }
    
    @Override
    public PingWebSocketFrame copy() {
        return new PingWebSocketFrame(this.isFinalFragment(), this.rsv(), this.data().copy());
    }
}
