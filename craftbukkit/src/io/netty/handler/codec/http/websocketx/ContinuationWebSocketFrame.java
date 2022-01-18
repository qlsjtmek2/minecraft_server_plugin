// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBufHolder;
import io.netty.util.CharsetUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ContinuationWebSocketFrame extends WebSocketFrame
{
    private String aggregatedText;
    
    public ContinuationWebSocketFrame() {
        super(Unpooled.buffer(0));
    }
    
    public ContinuationWebSocketFrame(final ByteBuf binaryData) {
        super(binaryData);
    }
    
    public ContinuationWebSocketFrame(final boolean finalFragment, final int rsv, final ByteBuf binaryData) {
        super(finalFragment, rsv, binaryData);
    }
    
    public ContinuationWebSocketFrame(final boolean finalFragment, final int rsv, final ByteBuf binaryData, final String aggregatedText) {
        super(finalFragment, rsv, binaryData);
        this.aggregatedText = aggregatedText;
    }
    
    public ContinuationWebSocketFrame(final boolean finalFragment, final int rsv, final String text) {
        this(finalFragment, rsv, fromText(text), null);
    }
    
    public String text() {
        return this.data().toString(CharsetUtil.UTF_8);
    }
    
    private static ByteBuf fromText(final String text) {
        if (text == null || text.isEmpty()) {
            return Unpooled.EMPTY_BUFFER;
        }
        return Unpooled.copiedBuffer(text, CharsetUtil.UTF_8);
    }
    
    public String aggregatedText() {
        return this.aggregatedText;
    }
    
    @Override
    public ContinuationWebSocketFrame copy() {
        return new ContinuationWebSocketFrame(this.isFinalFragment(), this.rsv(), this.data().copy(), this.aggregatedText());
    }
}
