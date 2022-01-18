// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class BinaryWebSocketFrame extends WebSocketFrame
{
    public BinaryWebSocketFrame() {
        super(Unpooled.buffer(0));
    }
    
    public BinaryWebSocketFrame(final ByteBuf binaryData) {
        super(binaryData);
    }
    
    public BinaryWebSocketFrame(final boolean finalFragment, final int rsv, final ByteBuf binaryData) {
        super(finalFragment, rsv, binaryData);
    }
    
    @Override
    public BinaryWebSocketFrame copy() {
        return new BinaryWebSocketFrame(this.isFinalFragment(), this.rsv(), this.data().copy());
    }
}
