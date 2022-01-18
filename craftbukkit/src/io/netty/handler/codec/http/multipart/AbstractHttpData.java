// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.ReferenceCounted;
import java.io.IOException;
import io.netty.channel.ChannelException;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpConstants;
import java.nio.charset.Charset;
import io.netty.buffer.AbstractReferenceCounted;

public abstract class AbstractHttpData extends AbstractReferenceCounted implements HttpData
{
    protected final String name;
    protected long definedSize;
    protected long size;
    protected Charset charset;
    protected boolean completed;
    
    protected AbstractHttpData(String name, final Charset charset, final long size) {
        this.charset = HttpConstants.DEFAULT_CHARSET;
        if (name == null) {
            throw new NullPointerException("name");
        }
        name = name.trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("empty name");
        }
        int i = 0;
        while (i < name.length()) {
            final char c = name.charAt(i);
            if (c > '\u007f') {
                throw new IllegalArgumentException("name contains non-ascii character: " + name);
            }
            switch (c) {
                case '\t':
                case '\n':
                case '\u000b':
                case '\f':
                case '\r':
                case ' ':
                case ',':
                case ';':
                case '=': {
                    throw new IllegalArgumentException("name contains one of the following prohibited characters: =,; \\t\\r\\n\\v\\f: " + name);
                }
                default: {
                    ++i;
                    continue;
                }
            }
        }
        this.name = name;
        if (charset != null) {
            this.setCharset(charset);
        }
        this.definedSize = size;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public boolean isCompleted() {
        return this.completed;
    }
    
    @Override
    public Charset getCharset() {
        return this.charset;
    }
    
    @Override
    public void setCharset(final Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        this.charset = charset;
    }
    
    @Override
    public long length() {
        return this.size;
    }
    
    @Override
    public ByteBuf data() {
        try {
            return this.getByteBuf();
        }
        catch (IOException e) {
            throw new ChannelException(e);
        }
    }
    
    @Override
    protected void deallocate() {
        this.delete();
    }
    
    @Override
    public HttpData retain() {
        super.retain();
        return this;
    }
    
    @Override
    public HttpData retain(final int increment) {
        super.retain(increment);
        return this;
    }
}
