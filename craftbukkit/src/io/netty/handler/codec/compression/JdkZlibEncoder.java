// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.compression;

import io.netty.buffer.Unpooled;
import java.util.concurrent.TimeUnit;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Future;
import io.netty.channel.ChannelFutureListener;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelFuture;
import java.util.zip.CRC32;
import io.netty.channel.ChannelHandlerContext;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.Deflater;

public class JdkZlibEncoder extends ZlibEncoder
{
    private final byte[] encodeBuf;
    private final Deflater deflater;
    private final AtomicBoolean finished;
    private volatile ChannelHandlerContext ctx;
    private final boolean gzip;
    private final CRC32 crc;
    private static final byte[] gzipHeader;
    private boolean writeHeader;
    
    public JdkZlibEncoder() {
        this(6);
    }
    
    public JdkZlibEncoder(final int compressionLevel) {
        this(ZlibWrapper.ZLIB, compressionLevel);
    }
    
    public JdkZlibEncoder(final ZlibWrapper wrapper) {
        this(wrapper, 6);
    }
    
    public JdkZlibEncoder(final ZlibWrapper wrapper, final int compressionLevel) {
        this.encodeBuf = new byte[8192];
        this.finished = new AtomicBoolean();
        this.crc = new CRC32();
        this.writeHeader = true;
        if (compressionLevel < 0 || compressionLevel > 9) {
            throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
        }
        if (wrapper == null) {
            throw new NullPointerException("wrapper");
        }
        if (wrapper == ZlibWrapper.ZLIB_OR_NONE) {
            throw new IllegalArgumentException("wrapper '" + ZlibWrapper.ZLIB_OR_NONE + "' is not " + "allowed for compression.");
        }
        this.gzip = (wrapper == ZlibWrapper.GZIP);
        this.deflater = new Deflater(compressionLevel, wrapper != ZlibWrapper.ZLIB);
    }
    
    public JdkZlibEncoder(final byte[] dictionary) {
        this(6, dictionary);
    }
    
    public JdkZlibEncoder(final int compressionLevel, final byte[] dictionary) {
        this.encodeBuf = new byte[8192];
        this.finished = new AtomicBoolean();
        this.crc = new CRC32();
        this.writeHeader = true;
        if (compressionLevel < 0 || compressionLevel > 9) {
            throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
        }
        if (dictionary == null) {
            throw new NullPointerException("dictionary");
        }
        this.gzip = false;
        (this.deflater = new Deflater(compressionLevel)).setDictionary(dictionary);
    }
    
    @Override
    public ChannelFuture close() {
        return this.close(this.ctx().newPromise());
    }
    
    @Override
    public ChannelFuture close(final ChannelPromise future) {
        return this.finishEncode(this.ctx(), future);
    }
    
    private ChannelHandlerContext ctx() {
        final ChannelHandlerContext ctx = this.ctx;
        if (ctx == null) {
            throw new IllegalStateException("not added to a pipeline");
        }
        return ctx;
    }
    
    @Override
    public boolean isClosed() {
        return this.finished.get();
    }
    
    @Override
    protected void encode(final ChannelHandlerContext ctx, final ByteBuf in, final ByteBuf out) throws Exception {
        if (this.finished.get()) {
            out.writeBytes(in);
            return;
        }
        final byte[] inAry = new byte[in.readableBytes()];
        in.readBytes(inAry);
        final int sizeEstimate = (int)Math.ceil(inAry.length * 1.001) + 12;
        out.ensureWritable(sizeEstimate);
        synchronized (this.deflater) {
            if (this.gzip) {
                this.crc.update(inAry);
                if (this.writeHeader) {
                    out.writeBytes(JdkZlibEncoder.gzipHeader);
                    this.writeHeader = false;
                }
            }
            this.deflater.setInput(inAry);
            while (!this.deflater.needsInput()) {
                final int numBytes = this.deflater.deflate(this.encodeBuf, 0, this.encodeBuf.length, 2);
                out.writeBytes(this.encodeBuf, 0, numBytes);
            }
        }
    }
    
    @Override
    public void close(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
        final ChannelFuture f = this.finishEncode(ctx, ctx.newPromise());
        f.addListener((GenericFutureListener<? extends Future<Void>>)new ChannelFutureListener() {
            @Override
            public void operationComplete(final ChannelFuture f) throws Exception {
                ctx.close(promise);
            }
        });
        if (!f.isDone()) {
            ctx.executor().schedule((Runnable)new Runnable() {
                @Override
                public void run() {
                    ctx.close(promise);
                }
            }, 10L, TimeUnit.SECONDS);
        }
    }
    
    private ChannelFuture finishEncode(final ChannelHandlerContext ctx, final ChannelPromise promise) {
        if (!this.finished.compareAndSet(false, true)) {
            promise.setSuccess();
            return promise;
        }
        final ByteBuf footer = Unpooled.buffer();
        synchronized (this.deflater) {
            this.deflater.finish();
            while (!this.deflater.finished()) {
                final int numBytes = this.deflater.deflate(this.encodeBuf, 0, this.encodeBuf.length);
                footer.writeBytes(this.encodeBuf, 0, numBytes);
            }
            if (this.gzip) {
                final int crcValue = (int)this.crc.getValue();
                final int uncBytes = this.deflater.getTotalIn();
                footer.writeByte(crcValue);
                footer.writeByte(crcValue >>> 8);
                footer.writeByte(crcValue >>> 16);
                footer.writeByte(crcValue >>> 24);
                footer.writeByte(uncBytes);
                footer.writeByte(uncBytes >>> 8);
                footer.writeByte(uncBytes >>> 16);
                footer.writeByte(uncBytes >>> 24);
            }
            this.deflater.end();
        }
        ctx.nextOutboundByteBuffer().writeBytes(footer);
        ctx.flush(promise);
        return promise;
    }
    
    @Override
    public void beforeAdd(final ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }
    
    static {
        gzipHeader = new byte[] { 31, -117, 8, 0, 0, 0, 0, 0, 0, 0 };
    }
}
