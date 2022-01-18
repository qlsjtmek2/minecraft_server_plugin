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
import com.jcraft.jzlib.JZlib;
import com.jcraft.jzlib.ZStream;
import io.netty.channel.ChannelHandlerContext;
import java.util.concurrent.atomic.AtomicBoolean;
import com.jcraft.jzlib.Deflater;

public class JZlibEncoder extends ZlibEncoder
{
    private static final byte[] EMPTY_ARRAY;
    private final Deflater z;
    private final AtomicBoolean finished;
    private volatile ChannelHandlerContext ctx;
    
    public JZlibEncoder() {
        this(6);
    }
    
    public JZlibEncoder(final int compressionLevel) {
        this(ZlibWrapper.ZLIB, compressionLevel);
    }
    
    public JZlibEncoder(final ZlibWrapper wrapper) {
        this(wrapper, 6);
    }
    
    public JZlibEncoder(final ZlibWrapper wrapper, final int compressionLevel) {
        this(wrapper, compressionLevel, 15, 8);
    }
    
    public JZlibEncoder(final ZlibWrapper wrapper, final int compressionLevel, final int windowBits, final int memLevel) {
        this.z = new Deflater();
        this.finished = new AtomicBoolean();
        if (compressionLevel < 0 || compressionLevel > 9) {
            throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
        }
        if (windowBits < 9 || windowBits > 15) {
            throw new IllegalArgumentException("windowBits: " + windowBits + " (expected: 9-15)");
        }
        if (memLevel < 1 || memLevel > 9) {
            throw new IllegalArgumentException("memLevel: " + memLevel + " (expected: 1-9)");
        }
        if (wrapper == null) {
            throw new NullPointerException("wrapper");
        }
        if (wrapper == ZlibWrapper.ZLIB_OR_NONE) {
            throw new IllegalArgumentException("wrapper '" + ZlibWrapper.ZLIB_OR_NONE + "' is not " + "allowed for compression.");
        }
        synchronized (this.z) {
            final int resultCode = this.z.init(compressionLevel, windowBits, memLevel, ZlibUtil.convertWrapperType(wrapper));
            if (resultCode != 0) {
                ZlibUtil.fail((ZStream)this.z, "initialization failure", resultCode);
            }
        }
    }
    
    public JZlibEncoder(final byte[] dictionary) {
        this(6, dictionary);
    }
    
    public JZlibEncoder(final int compressionLevel, final byte[] dictionary) {
        this(compressionLevel, 15, 8, dictionary);
    }
    
    public JZlibEncoder(final int compressionLevel, final int windowBits, final int memLevel, final byte[] dictionary) {
        this.z = new Deflater();
        this.finished = new AtomicBoolean();
        if (compressionLevel < 0 || compressionLevel > 9) {
            throw new IllegalArgumentException("compressionLevel: " + compressionLevel + " (expected: 0-9)");
        }
        if (windowBits < 9 || windowBits > 15) {
            throw new IllegalArgumentException("windowBits: " + windowBits + " (expected: 9-15)");
        }
        if (memLevel < 1 || memLevel > 9) {
            throw new IllegalArgumentException("memLevel: " + memLevel + " (expected: 1-9)");
        }
        if (dictionary == null) {
            throw new NullPointerException("dictionary");
        }
        synchronized (this.z) {
            int resultCode = this.z.deflateInit(compressionLevel, windowBits, memLevel, JZlib.W_ZLIB);
            if (resultCode != 0) {
                ZlibUtil.fail((ZStream)this.z, "initialization failure", resultCode);
            }
            else {
                resultCode = this.z.deflateSetDictionary(dictionary, dictionary.length);
                if (resultCode != 0) {
                    ZlibUtil.fail((ZStream)this.z, "failed to set the dictionary", resultCode);
                }
            }
        }
    }
    
    @Override
    public ChannelFuture close() {
        return this.close(this.ctx().channel().newPromise());
    }
    
    @Override
    public ChannelFuture close(final ChannelPromise promise) {
        return this.finishEncode(this.ctx(), promise);
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
            return;
        }
        synchronized (this.z) {
            try {
                final int inputLength = in.readableBytes();
                final boolean inHasArray = in.hasArray();
                this.z.avail_in = inputLength;
                if (inHasArray) {
                    this.z.next_in = in.array();
                    this.z.next_in_index = in.arrayOffset() + in.readerIndex();
                }
                else {
                    final byte[] array = new byte[inputLength];
                    in.readBytes(array);
                    this.z.next_in = array;
                    this.z.next_in_index = 0;
                }
                final int oldNextInIndex = this.z.next_in_index;
                final int maxOutputLength = (int)Math.ceil(inputLength * 1.001) + 12;
                final boolean outHasArray = out.hasArray();
                this.z.avail_out = maxOutputLength;
                if (outHasArray) {
                    out.ensureWritable(maxOutputLength);
                    this.z.next_out = out.array();
                    this.z.next_out_index = out.arrayOffset() + out.writerIndex();
                }
                else {
                    this.z.next_out = new byte[maxOutputLength];
                    this.z.next_out_index = 0;
                }
                final int oldNextOutIndex = this.z.next_out_index;
                int resultCode;
                try {
                    resultCode = this.z.deflate(2);
                }
                finally {
                    if (inHasArray) {
                        in.skipBytes(this.z.next_in_index - oldNextInIndex);
                    }
                }
                if (resultCode != 0) {
                    ZlibUtil.fail((ZStream)this.z, "compression failure", resultCode);
                }
                final int outputLength = this.z.next_out_index - oldNextOutIndex;
                if (outputLength > 0) {
                    if (outHasArray) {
                        out.writerIndex(out.writerIndex() + outputLength);
                    }
                    else {
                        out.writeBytes(this.z.next_out, 0, outputLength);
                    }
                }
            }
            finally {
                this.z.next_in = null;
                this.z.next_out = null;
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
    
    private ChannelFuture finishEncode(final ChannelHandlerContext ctx, final ChannelPromise future) {
        if (!this.finished.compareAndSet(false, true)) {
            future.setSuccess();
            return future;
        }
        ByteBuf footer;
        synchronized (this.z) {
            try {
                this.z.next_in = JZlibEncoder.EMPTY_ARRAY;
                this.z.next_in_index = 0;
                this.z.avail_in = 0;
                final byte[] out = new byte[32];
                this.z.next_out = out;
                this.z.next_out_index = 0;
                this.z.avail_out = out.length;
                final int resultCode = this.z.deflate(4);
                if (resultCode != 0 && resultCode != 1) {
                    future.setFailure((Throwable)ZlibUtil.exception((ZStream)this.z, "compression failure", resultCode));
                    return future;
                }
                if (this.z.next_out_index != 0) {
                    footer = Unpooled.wrappedBuffer(out, 0, this.z.next_out_index);
                }
                else {
                    footer = Unpooled.EMPTY_BUFFER;
                }
            }
            finally {
                this.z.deflateEnd();
                this.z.next_in = null;
                this.z.next_out = null;
            }
        }
        ctx.write(footer, future);
        return future;
    }
    
    @Override
    public void beforeAdd(final ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }
    
    static {
        EMPTY_ARRAY = new byte[0];
    }
}
