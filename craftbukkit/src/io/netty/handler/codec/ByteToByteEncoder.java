// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec;

import java.nio.ByteBuffer;
import java.io.EOFException;
import java.io.IOException;
import java.nio.channels.WritableByteChannel;
import io.netty.channel.FileRegion;
import io.netty.channel.IncompleteFlushException;
import io.netty.channel.ChannelPromise;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundByteHandlerAdapter;

public abstract class ByteToByteEncoder extends ChannelOutboundByteHandlerAdapter
{
    @Override
    protected void flush(final ChannelHandlerContext ctx, final ByteBuf in, final ChannelPromise promise) throws Exception {
        final ByteBuf out = ctx.nextOutboundByteBuffer();
        boolean encoded = false;
        while (in.isReadable()) {
            final int oldInSize = in.readableBytes();
            try {
                this.encode(ctx, in, out);
                encoded = true;
            }
            catch (Throwable t) {
                Throwable cause;
                if (t instanceof CodecException) {
                    cause = t;
                }
                else {
                    cause = new EncoderException(t);
                }
                if (encoded) {
                    cause = new IncompleteFlushException("unable to encode all bytes", cause);
                }
                in.discardSomeReadBytes();
                promise.setFailure(cause);
                return;
            }
            if (oldInSize == in.readableBytes()) {
                break;
            }
        }
        ctx.flush(promise);
    }
    
    @Override
    public void sendFile(final ChannelHandlerContext ctx, final FileRegion region, final ChannelPromise promise) throws Exception {
        long written = 0L;
        try {
            while (true) {
                final long localWritten = region.transferTo(new BufferChannel(ctx.outboundByteBuffer()), written);
                if (localWritten == -1L) {
                    checkEOF(region, written);
                    this.flush(ctx, promise);
                    break;
                }
                written += localWritten;
                if (written >= region.count()) {
                    this.flush(ctx, promise);
                    break;
                }
            }
        }
        catch (IOException e) {
            promise.setFailure((Throwable)new EncoderException(e));
        }
        finally {
            region.release();
        }
    }
    
    private static void checkEOF(final FileRegion region, final long writtenBytes) throws IOException {
        if (writtenBytes < region.count()) {
            throw new EOFException("Expected to be able to write " + region.count() + " bytes, but only wrote " + writtenBytes);
        }
    }
    
    protected abstract void encode(final ChannelHandlerContext p0, final ByteBuf p1, final ByteBuf p2) throws Exception;
    
    private static final class BufferChannel implements WritableByteChannel
    {
        private final ByteBuf buffer;
        
        BufferChannel(final ByteBuf buffer) {
            this.buffer = buffer;
        }
        
        @Override
        public int write(final ByteBuffer src) {
            final int bytes = src.remaining();
            this.buffer.writeBytes(src);
            return bytes;
        }
        
        @Override
        public boolean isOpen() {
            return this.buffer.refCnt() > 0;
        }
        
        @Override
        public void close() {
        }
    }
}
