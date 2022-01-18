// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBufHolder;
import io.netty.util.CharsetUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class CloseWebSocketFrame extends WebSocketFrame
{
    private static final byte[] EMTPY_REASON;
    
    public CloseWebSocketFrame() {
        super(Unpooled.buffer(0));
    }
    
    public CloseWebSocketFrame(final int statusCode, final String reasonText) {
        this(true, 0, statusCode, reasonText);
    }
    
    public CloseWebSocketFrame(final boolean finalFragment, final int rsv) {
        this(finalFragment, rsv, null);
    }
    
    public CloseWebSocketFrame(final boolean finalFragment, final int rsv, final int statusCode, final String reasonText) {
        super(finalFragment, rsv, newBinaryData(statusCode, reasonText));
    }
    
    private static ByteBuf newBinaryData(final int statusCode, final String reasonText) {
        byte[] reasonBytes = CloseWebSocketFrame.EMTPY_REASON;
        if (reasonText != null) {
            reasonBytes = reasonText.getBytes(CharsetUtil.UTF_8);
        }
        final ByteBuf binaryData = Unpooled.buffer(2 + reasonBytes.length);
        binaryData.writeShort(statusCode);
        if (reasonBytes.length > 0) {
            binaryData.writeBytes(reasonBytes);
        }
        binaryData.readerIndex(0);
        return binaryData;
    }
    
    public CloseWebSocketFrame(final boolean finalFragment, final int rsv, final ByteBuf binaryData) {
        super(finalFragment, rsv, binaryData);
    }
    
    public int statusCode() {
        final ByteBuf binaryData = this.data();
        if (binaryData == null || binaryData.capacity() == 0) {
            return -1;
        }
        binaryData.readerIndex(0);
        final int statusCode = binaryData.readShort();
        binaryData.readerIndex(0);
        return statusCode;
    }
    
    public String reasonText() {
        final ByteBuf binaryData = this.data();
        if (binaryData == null || binaryData.capacity() <= 2) {
            return "";
        }
        binaryData.readerIndex(2);
        final String reasonText = binaryData.toString(CharsetUtil.UTF_8);
        binaryData.readerIndex(0);
        return reasonText;
    }
    
    @Override
    public CloseWebSocketFrame copy() {
        return new CloseWebSocketFrame(this.isFinalFragment(), this.rsv(), this.data().copy());
    }
    
    static {
        EMTPY_REASON = new byte[0];
    }
}
