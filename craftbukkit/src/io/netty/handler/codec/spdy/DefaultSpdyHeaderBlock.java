// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.spdy;

import java.util.Iterator;
import io.netty.util.internal.StringUtil;
import java.util.Map;

public class DefaultSpdyHeaderBlock implements SpdyHeaderBlock
{
    private boolean invalid;
    private final SpdyHeaders headers;
    
    protected DefaultSpdyHeaderBlock() {
        this.headers = new DefaultSpdyHeaders();
    }
    
    @Override
    public boolean isInvalid() {
        return this.invalid;
    }
    
    @Override
    public SpdyHeaderBlock setInvalid() {
        this.invalid = true;
        return this;
    }
    
    @Override
    public SpdyHeaders headers() {
        return this.headers;
    }
    
    protected void appendHeaders(final StringBuilder buf) {
        for (final Map.Entry<String, String> e : this.headers().entries()) {
            buf.append("    ");
            buf.append(e.getKey());
            buf.append(": ");
            buf.append(e.getValue());
            buf.append(StringUtil.NEWLINE);
        }
    }
}
