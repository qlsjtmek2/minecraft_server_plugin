// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.oio;

import io.netty.channel.AbstractChannel;
import java.nio.channels.Channels;
import io.netty.channel.ChannelPromise;
import io.netty.channel.FileRegion;
import java.nio.channels.NotYetConnectedException;
import io.netty.buffer.ByteBuf;
import java.io.IOException;
import io.netty.channel.Channel;
import java.nio.channels.WritableByteChannel;
import java.io.OutputStream;
import java.io.InputStream;

public abstract class OioByteStreamChannel extends AbstractOioByteChannel
{
    private InputStream is;
    private OutputStream os;
    private WritableByteChannel outChannel;
    
    protected OioByteStreamChannel(final Channel parent, final Integer id) {
        super(parent, id);
    }
    
    protected final void activate(final InputStream is, final OutputStream os) {
        if (this.is != null) {
            throw new IllegalStateException("input was set already");
        }
        if (this.os != null) {
            throw new IllegalStateException("output was set already");
        }
        if (is == null) {
            throw new NullPointerException("is");
        }
        if (os == null) {
            throw new NullPointerException("os");
        }
        this.is = is;
        this.os = os;
    }
    
    @Override
    public boolean isActive() {
        return this.is != null && this.os != null;
    }
    
    @Override
    protected int available() {
        try {
            return this.is.available();
        }
        catch (IOException e) {
            return 0;
        }
    }
    
    @Override
    protected int doReadBytes(final ByteBuf buf) throws Exception {
        int length = this.available();
        if (length < 1) {
            length = 1;
        }
        if (length > buf.writableBytes()) {
            length = buf.writableBytes();
        }
        return buf.writeBytes(this.is, length);
    }
    
    @Override
    protected void doWriteBytes(final ByteBuf buf) throws Exception {
        final OutputStream os = this.os;
        if (os == null) {
            throw new NotYetConnectedException();
        }
        buf.readBytes(os, buf.readableBytes());
    }
    
    @Override
    protected void doFlushFileRegion(final FileRegion region, final ChannelPromise promise) throws Exception {
        final OutputStream os = this.os;
        if (os == null) {
            throw new NotYetConnectedException();
        }
        if (this.outChannel == null) {
            this.outChannel = Channels.newChannel(os);
        }
        long written = 0L;
        while (true) {
            final long localWritten = region.transferTo(this.outChannel, written);
            if (localWritten == -1L) {
                AbstractChannel.checkEOF(region, written);
                region.release();
                promise.setSuccess();
                return;
            }
            written += localWritten;
            if (written >= region.count()) {
                promise.setSuccess();
            }
        }
    }
    
    @Override
    protected void doClose() throws Exception {
        IOException ex = null;
        try {
            if (this.is != null) {
                this.is.close();
            }
        }
        catch (IOException e) {
            ex = e;
        }
        try {
            if (this.os != null) {
                this.os.close();
            }
        }
        catch (IOException e) {
            ex = e;
        }
        this.is = null;
        this.os = null;
        if (ex != null) {
            throw ex;
        }
    }
}
