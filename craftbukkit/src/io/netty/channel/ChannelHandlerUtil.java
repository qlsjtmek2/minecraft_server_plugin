// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.buffer.BufType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.MessageBuf;
import io.netty.buffer.BufUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.Signal;

public final class ChannelHandlerUtil
{
    public static final Signal ABORT;
    private static final InternalLogger logger;
    
    public static <T> void handleInboundBufferUpdated(final ChannelHandlerContext ctx, final SingleInboundMessageHandler<T> handler) throws Exception {
        final MessageBuf<Object> in = ctx.inboundMessageBuffer();
        if (in.isEmpty() || !handler.beginMessageReceived(ctx)) {
            return;
        }
        final MessageBuf<Object> out = ctx.nextInboundMessageBuffer();
        final int oldOutSize = out.size();
        try {
            while (true) {
                final Object msg = in.poll();
                if (msg == null) {
                    break;
                }
                if (!handler.acceptInboundMessage(msg)) {
                    out.add(msg);
                }
                else {
                    final T imsg = (T)msg;
                    try {
                        handler.messageReceived(ctx, imsg);
                    }
                    finally {
                        BufUtil.release(imsg);
                    }
                }
            }
        }
        catch (Signal abort) {
            abort.expect(ChannelHandlerUtil.ABORT);
        }
        finally {
            if (oldOutSize != out.size()) {
                ctx.fireInboundBufferUpdated();
            }
            handler.endMessageReceived(ctx);
        }
    }
    
    public static <T> void handleFlush(final ChannelHandlerContext ctx, final ChannelPromise promise, final SingleOutboundMessageHandler<T> handler) throws Exception {
        handleFlush(ctx, promise, true, handler);
    }
    
    public static <T> void handleFlush(final ChannelHandlerContext ctx, final ChannelPromise promise, final boolean closeOnFailedFlush, final SingleOutboundMessageHandler<T> handler) throws Exception {
        final MessageBuf<Object> in = ctx.outboundMessageBuffer();
        final int inSize = in.size();
        if (inSize == 0) {
            ctx.flush(promise);
            return;
        }
        int processed = 0;
        try {
            if (!handler.beginFlush(ctx)) {
                throw new IncompleteFlushException("beginFlush(..) rejected the flush request by returning false. none of " + inSize + " message(s) fulshed.");
            }
            while (true) {
                final Object msg = in.poll();
                if (msg == null) {
                    break;
                }
                if (!handler.acceptOutboundMessage(msg)) {
                    addToNextOutboundBuffer(ctx, msg);
                    ++processed;
                }
                else {
                    final T imsg = (T)msg;
                    try {
                        handler.flush(ctx, imsg);
                        ++processed;
                    }
                    finally {
                        BufUtil.release(imsg);
                    }
                }
            }
        }
        catch (Throwable t) {
            IncompleteFlushException pfe;
            if (t instanceof IncompleteFlushException) {
                pfe = (IncompleteFlushException)t;
            }
            else {
                final String msg2 = processed + " out of " + inSize + " message(s) flushed";
                if (t instanceof Signal) {
                    final Signal abort = (Signal)t;
                    abort.expect(ChannelHandlerUtil.ABORT);
                    pfe = new IncompleteFlushException("aborted: " + msg2);
                }
                else {
                    pfe = new IncompleteFlushException(msg2, t);
                }
            }
            fail(ctx, promise, closeOnFailedFlush, pfe);
        }
        try {
            handler.endFlush(ctx);
        }
        catch (Throwable t) {
            if (promise.isDone()) {
                ChannelHandlerUtil.logger.warn("endFlush() raised a masked exception due to failed flush().", t);
            }
            else {
                fail(ctx, promise, closeOnFailedFlush, t);
            }
        }
        if (!promise.isDone()) {
            ctx.flush(promise);
        }
    }
    
    private static void fail(final ChannelHandlerContext ctx, final ChannelPromise promise, final boolean closeOnFailedFlush, final Throwable cause) {
        promise.setFailure(cause);
        if (closeOnFailedFlush) {
            ctx.close();
        }
    }
    
    public static ByteBuf allocate(final ChannelHandlerContext ctx) {
        switch (ctx.channel().config().getDefaultHandlerByteBufType()) {
            case DIRECT: {
                return ctx.alloc().directBuffer();
            }
            case PREFER_DIRECT: {
                return ctx.alloc().ioBuffer();
            }
            case HEAP: {
                return ctx.alloc().heapBuffer();
            }
            default: {
                throw new IllegalStateException();
            }
        }
    }
    
    public static ByteBuf allocate(final ChannelHandlerContext ctx, final int initialCapacity) {
        switch (ctx.channel().config().getDefaultHandlerByteBufType()) {
            case DIRECT: {
                return ctx.alloc().directBuffer(initialCapacity);
            }
            case PREFER_DIRECT: {
                return ctx.alloc().ioBuffer(initialCapacity);
            }
            case HEAP: {
                return ctx.alloc().heapBuffer(initialCapacity);
            }
            default: {
                throw new IllegalStateException();
            }
        }
    }
    
    public static ByteBuf allocate(final ChannelHandlerContext ctx, final int initialCapacity, final int maxCapacity) {
        switch (ctx.channel().config().getDefaultHandlerByteBufType()) {
            case DIRECT: {
                return ctx.alloc().directBuffer(initialCapacity, maxCapacity);
            }
            case PREFER_DIRECT: {
                return ctx.alloc().ioBuffer(initialCapacity, maxCapacity);
            }
            case HEAP: {
                return ctx.alloc().heapBuffer(initialCapacity, maxCapacity);
            }
            default: {
                throw new IllegalStateException();
            }
        }
    }
    
    public static boolean addToNextOutboundBuffer(final ChannelHandlerContext ctx, final Object msg) {
        if (msg instanceof ByteBuf && ctx.nextOutboundBufferType() == BufType.BYTE) {
            ctx.nextOutboundByteBuffer().writeBytes((ByteBuf)msg);
            return true;
        }
        return ctx.nextOutboundMessageBuffer().add(msg);
    }
    
    public static boolean addToNextInboundBuffer(final ChannelHandlerContext ctx, final Object msg) {
        if (msg instanceof ByteBuf && ctx.nextInboundBufferType() == BufType.BYTE) {
            ctx.nextInboundByteBuffer().writeBytes((ByteBuf)msg);
            return true;
        }
        return ctx.nextInboundMessageBuffer().add(msg);
    }
    
    static {
        ABORT = new Signal(ChannelHandlerUtil.class.getName() + ".ABORT");
        logger = InternalLoggerFactory.getInstance(ChannelHandlerUtil.class);
    }
    
    public interface SingleOutboundMessageHandler<T>
    {
        boolean acceptOutboundMessage(final Object p0) throws Exception;
        
        boolean beginFlush(final ChannelHandlerContext p0) throws Exception;
        
        void flush(final ChannelHandlerContext p0, final T p1) throws Exception;
        
        void endFlush(final ChannelHandlerContext p0) throws Exception;
    }
    
    public interface SingleInboundMessageHandler<T>
    {
        boolean acceptInboundMessage(final Object p0) throws Exception;
        
        boolean beginMessageReceived(final ChannelHandlerContext p0) throws Exception;
        
        void messageReceived(final ChannelHandlerContext p0, final T p1) throws Exception;
        
        void endMessageReceived(final ChannelHandlerContext p0) throws Exception;
    }
}
