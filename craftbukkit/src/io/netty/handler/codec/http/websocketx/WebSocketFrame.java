// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.DefaultByteBufHolder;

public abstract class WebSocketFrame extends DefaultByteBufHolder
{
    private final boolean finalFragment;
    private final int rsv;
    
    protected WebSocketFrame(final ByteBuf binaryData) {
        this(true, 0, binaryData);
    }
    
    protected WebSocketFrame(final boolean finalFragment, final int rsv, final ByteBuf binaryData) {
        super(binaryData);
        this.finalFragment = finalFragment;
        this.rsv = rsv;
    }
    
    public boolean isFinalFragment() {
        return this.finalFragment;
    }
    
    public int rsv() {
        return this.rsv;
    }
    
    @Override
    public abstract WebSocketFrame copy();
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(data: " + this.data().toString() + ')';
    }
}
