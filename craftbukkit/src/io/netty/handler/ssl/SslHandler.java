// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.ssl;

import io.netty.util.concurrent.Promise;
import io.netty.channel.Channel;
import io.netty.channel.DefaultChannelPromise;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.buffer.Buf;
import java.nio.channels.ClosedChannelException;
import io.netty.buffer.BufUtil;
import io.netty.util.internal.PlatformDependent;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;
import java.nio.ByteBuffer;
import io.netty.channel.ChannelFutureListener;
import javax.net.ssl.SSLEngineResult;
import java.io.EOFException;
import java.io.IOException;
import io.netty.channel.FileRegion;
import io.netty.channel.ChannelHandlerUtil;
import io.netty.buffer.ByteBuf;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.concurrent.ScheduledFuture;
import javax.net.ssl.SSLException;
import io.netty.channel.ChannelFuture;
import java.util.concurrent.TimeUnit;
import java.util.ArrayDeque;
import io.netty.util.concurrent.ImmediateExecutor;
import io.netty.channel.ChannelPromise;
import java.util.Queue;
import java.nio.channels.WritableByteChannel;
import io.netty.channel.ChannelFlushPromiseNotifier;
import java.util.concurrent.Executor;
import javax.net.ssl.SSLEngine;
import io.netty.channel.ChannelHandlerContext;
import java.util.regex.Pattern;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.channel.ChannelOutboundByteHandler;
import io.netty.channel.ChannelInboundByteHandler;
import io.netty.channel.ChannelDuplexHandler;

public class SslHandler extends ChannelDuplexHandler implements ChannelInboundByteHandler, ChannelOutboundByteHandler
{
    private static final InternalLogger logger;
    private static final Pattern IGNORABLE_CLASS_IN_STACK;
    private static final Pattern IGNORABLE_ERROR_MESSAGE;
    private volatile ChannelHandlerContext ctx;
    private final SSLEngine engine;
    private final Executor delegatedTaskExecutor;
    private final ChannelFlushPromiseNotifier flushFutureNotifier;
    private final boolean startTls;
    private boolean sentFirstMessage;
    private WritableByteChannel bufferChannel;
    private final Queue<ChannelPromise> handshakePromises;
    private final SSLEngineInboundCloseFuture sslCloseFuture;
    private final CloseNotifyListener closeNotifyWriteListener;
    private volatile long handshakeTimeoutMillis;
    private volatile long closeNotifyTimeoutMillis;
    
    public SslHandler(final SSLEngine engine) {
        this(engine, ImmediateExecutor.INSTANCE);
    }
    
    public SslHandler(final SSLEngine engine, final boolean startTls) {
        this(engine, startTls, ImmediateExecutor.INSTANCE);
    }
    
    public SslHandler(final SSLEngine engine, final Executor delegatedTaskExecutor) {
        this(engine, false, delegatedTaskExecutor);
    }
    
    public SslHandler(final SSLEngine engine, final boolean startTls, final Executor delegatedTaskExecutor) {
        this.flushFutureNotifier = new ChannelFlushPromiseNotifier(true);
        this.handshakePromises = new ArrayDeque<ChannelPromise>();
        this.sslCloseFuture = new SSLEngineInboundCloseFuture();
        this.closeNotifyWriteListener = new CloseNotifyListener();
        this.handshakeTimeoutMillis = 10000L;
        this.closeNotifyTimeoutMillis = 3000L;
        if (engine == null) {
            throw new NullPointerException("engine");
        }
        if (delegatedTaskExecutor == null) {
            throw new NullPointerException("delegatedTaskExecutor");
        }
        this.engine = engine;
        this.delegatedTaskExecutor = delegatedTaskExecutor;
        this.startTls = startTls;
    }
    
    public long getHandshakeTimeoutMillis() {
        return this.handshakeTimeoutMillis;
    }
    
    public void setHandshakeTimeout(final long handshakeTimeout, final TimeUnit unit) {
        if (unit == null) {
            throw new NullPointerException("unit");
        }
        this.setHandshakeTimeoutMillis(unit.toMillis(handshakeTimeout));
    }
    
    public void setHandshakeTimeoutMillis(final long handshakeTimeoutMillis) {
        if (handshakeTimeoutMillis < 0L) {
            throw new IllegalArgumentException("handshakeTimeoutMillis: " + handshakeTimeoutMillis + " (expected: >= 0)");
        }
        this.handshakeTimeoutMillis = handshakeTimeoutMillis;
    }
    
    public long getCloseNotifyTimeoutMillis() {
        return this.handshakeTimeoutMillis;
    }
    
    public void setCloseNotifyTimeout(final long closeNotifyTimeout, final TimeUnit unit) {
        if (unit == null) {
            throw new NullPointerException("unit");
        }
        this.setCloseNotifyTimeoutMillis(unit.toMillis(closeNotifyTimeout));
    }
    
    public void setCloseNotifyTimeoutMillis(final long closeNotifyTimeoutMillis) {
        if (closeNotifyTimeoutMillis < 0L) {
            throw new IllegalArgumentException("closeNotifyTimeoutMillis: " + closeNotifyTimeoutMillis + " (expected: >= 0)");
        }
        this.closeNotifyTimeoutMillis = closeNotifyTimeoutMillis;
    }
    
    public SSLEngine engine() {
        return this.engine;
    }
    
    public ChannelFuture handshake() {
        return this.handshake(this.ctx.newPromise());
    }
    
    public ChannelFuture handshake(final ChannelPromise promise) {
        final ChannelHandlerContext ctx = this.ctx;
        ScheduledFuture<?> timeoutFuture;
        if (this.handshakeTimeoutMillis > 0L) {
            timeoutFuture = ctx.executor().schedule((Runnable)new Runnable() {
                @Override
                public void run() {
                    if (promise.isDone()) {
                        return;
                    }
                    final SSLException e = new SSLException("handshake timed out");
                    if (promise.tryFailure(e)) {
                        ctx.fireExceptionCaught((Throwable)e);
                        ctx.close();
                    }
                }
            }, this.handshakeTimeoutMillis, TimeUnit.MILLISECONDS);
        }
        else {
            timeoutFuture = null;
        }
        ctx.executor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (timeoutFuture != null) {
                        timeoutFuture.cancel(false);
                    }
                    SslHandler.this.engine.beginHandshake();
                    SslHandler.this.handshakePromises.add(promise);
                    SslHandler.this.flush0(ctx, ctx.newPromise(), true);
                }
                catch (Exception e) {
                    if (promise.tryFailure(e)) {
                        ctx.fireExceptionCaught((Throwable)e);
                        ctx.close();
                    }
                }
            }
        });
        return promise;
    }
    
    public ChannelFuture close() {
        return this.close(this.ctx.newPromise());
    }
    
    public ChannelFuture close(final ChannelPromise future) {
        final ChannelHandlerContext ctx = this.ctx;
        ctx.executor().execute(new Runnable() {
            @Override
            public void run() {
                SslHandler.this.engine.closeOutbound();
                future.addListener((GenericFutureListener<? extends Future<Void>>)SslHandler.this.closeNotifyWriteListener);
                try {
                    SslHandler.this.flush(ctx, future);
                }
                catch (Exception e) {
                    if (!future.tryFailure(e)) {
                        SslHandler.logger.warn("flush() raised a masked exception.", e);
                    }
                }
            }
        });
        return future;
    }
    
    public ChannelFuture sslCloseFuture() {
        return this.sslCloseFuture;
    }
    
    @Override
    public ByteBuf newInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        return ChannelHandlerUtil.allocate(ctx);
    }
    
    @Override
    public void discardInboundReadBytes(final ChannelHandlerContext ctx) throws Exception {
        ctx.inboundByteBuffer().discardSomeReadBytes();
    }
    
    @Override
    public void freeInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        ctx.inboundByteBuffer().release();
    }
    
    @Override
    public ByteBuf newOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        return ChannelHandlerUtil.allocate(ctx);
    }
    
    @Override
    public void discardOutboundReadBytes(final ChannelHandlerContext ctx) throws Exception {
        ctx.outboundByteBuffer().discardSomeReadBytes();
    }
    
    @Override
    public void freeOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        ctx.outboundByteBuffer().release();
    }
    
    @Override
    public void disconnect(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
        this.closeOutboundAndChannel(ctx, promise, true);
    }
    
    @Override
    public void close(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
        this.closeOutboundAndChannel(ctx, promise, false);
    }
    
    @Override
    public void read(final ChannelHandlerContext ctx) {
        ctx.read();
    }
    
    @Override
    public final void sendFile(final ChannelHandlerContext ctx, final FileRegion region, final ChannelPromise promise) throws Exception {
        if (this.bufferChannel == null) {
            this.bufferChannel = new BufferChannel(ctx.outboundByteBuffer());
        }
        long written = 0L;
        try {
            while (true) {
                final long localWritten = region.transferTo(this.bufferChannel, written);
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
            promise.setFailure((Throwable)e);
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
    
    @Override
    public void flush(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
        this.flush0(ctx, promise, false);
    }
    
    private void flush0(final ChannelHandlerContext ctx, final ChannelPromise promise, final boolean internal) throws Exception {
        final ByteBuf in = ctx.outboundByteBuffer();
        final ByteBuf out = ctx.nextOutboundByteBuffer();
        if (!internal && this.startTls && !this.sentFirstMessage) {
            this.sentFirstMessage = true;
            out.writeBytes(in);
            ctx.flush(promise);
            return;
        }
        if (ctx.executor() == ctx.channel().eventLoop()) {
            this.flushFutureNotifier.add(promise, in.readableBytes());
        }
        else {
            synchronized (this.flushFutureNotifier) {
                this.flushFutureNotifier.add(promise, in.readableBytes());
            }
        }
        boolean unwrapLater = false;
        int bytesConsumed = 0;
        try {
            while (true) {
                final SSLEngineResult result = wrap(this.engine, in, out);
                bytesConsumed += result.bytesConsumed();
                if (result.getStatus() == SSLEngineResult.Status.CLOSED) {
                    if (in.isReadable()) {
                        in.clear();
                        final SSLException e = new SSLException("SSLEngine already closed");
                        promise.setFailure((Throwable)e);
                        ctx.fireExceptionCaught((Throwable)e);
                        this.flush0(ctx, bytesConsumed, e);
                        bytesConsumed = 0;
                        break;
                    }
                    break;
                }
                else {
                    switch (result.getHandshakeStatus()) {
                        case NEED_WRAP: {
                            ctx.flush();
                            continue;
                        }
                        case NEED_UNWRAP: {
                            if (ctx.inboundByteBuffer().isReadable()) {
                                unwrapLater = true;
                                break;
                            }
                            break;
                        }
                        case NEED_TASK: {
                            this.runDelegatedTasks();
                            continue;
                        }
                        case FINISHED: {
                            this.setHandshakeSuccess();
                            continue;
                        }
                        case NOT_HANDSHAKING: {
                            if (ctx.inboundByteBuffer().isReadable()) {
                                unwrapLater = true;
                                break;
                            }
                            break;
                        }
                        default: {
                            throw new IllegalStateException("Unknown handshake status: " + result.getHandshakeStatus());
                        }
                    }
                    if (result.bytesConsumed() == 0 && result.bytesProduced() == 0) {
                        break;
                    }
                    continue;
                }
            }
            if (unwrapLater) {
                this.inboundBufferUpdated(ctx);
            }
        }
        catch (SSLException e2) {
            this.setHandshakeFailure(e2);
            throw e2;
        }
        finally {
            this.flush0(ctx, bytesConsumed);
        }
    }
    
    private void flush0(final ChannelHandlerContext ctx, final int bytesConsumed) {
        ctx.flush(ctx.newPromise().addListener((GenericFutureListener<? extends Future<Void>>)new ChannelFutureListener() {
            @Override
            public void operationComplete(final ChannelFuture future) throws Exception {
                if (ctx.executor() == ctx.channel().eventLoop()) {
                    this.notifyFlushFutures(bytesConsumed, future);
                }
                else {
                    synchronized (SslHandler.this.flushFutureNotifier) {
                        this.notifyFlushFutures(bytesConsumed, future);
                    }
                }
            }
            
            private void notifyFlushFutures(final int bytesConsumed, final ChannelFuture future) {
                if (future.isSuccess()) {
                    SslHandler.this.flushFutureNotifier.increaseWriteCounter(bytesConsumed);
                    SslHandler.this.flushFutureNotifier.notifyFlushFutures();
                }
                else {
                    SslHandler.this.flushFutureNotifier.notifyFlushFutures(future.cause());
                }
            }
        }));
    }
    
    private void flush0(final ChannelHandlerContext ctx, final int bytesConsumed, final Throwable cause) {
        final ChannelFuture flushFuture = ctx.flush(ctx.newPromise().addListener((GenericFutureListener<? extends Future<Void>>)new ChannelFutureListener() {
            @Override
            public void operationComplete(final ChannelFuture future) throws Exception {
                if (ctx.executor() == ctx.channel().eventLoop()) {
                    this.notifyFlushFutures(bytesConsumed, cause, future);
                }
                else {
                    synchronized (SslHandler.this.flushFutureNotifier) {
                        this.notifyFlushFutures(bytesConsumed, cause, future);
                    }
                }
            }
            
            private void notifyFlushFutures(final int bytesConsumed, final Throwable cause, final ChannelFuture future) {
                SslHandler.this.flushFutureNotifier.increaseWriteCounter(bytesConsumed);
                if (future.isSuccess()) {
                    SslHandler.this.flushFutureNotifier.notifyFlushFutures(cause);
                }
                else {
                    SslHandler.this.flushFutureNotifier.notifyFlushFutures(cause, future.cause());
                }
            }
        }));
        this.safeClose(ctx, flushFuture, ctx.newPromise());
    }
    
    private static SSLEngineResult wrap(final SSLEngine engine, final ByteBuf in, final ByteBuf out) throws SSLException {
        final ByteBuffer in2 = in.nioBuffer();
        SSLEngineResult result;
        while (true) {
            final ByteBuffer out2 = out.nioBuffer(out.writerIndex(), out.writableBytes());
            result = engine.wrap(in2, out2);
            in.skipBytes(result.bytesConsumed());
            out.writerIndex(out.writerIndex() + result.bytesProduced());
            if (result.getStatus() != SSLEngineResult.Status.BUFFER_OVERFLOW) {
                break;
            }
            out.ensureWritable(engine.getSession().getPacketBufferSize());
        }
        return result;
    }
    
    @Override
    public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
        this.setHandshakeFailure(null);
        try {
            this.inboundBufferUpdated(ctx);
        }
        finally {
            ctx.fireChannelInactive();
        }
    }
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
        if (this.ignoreException(cause)) {
            if (SslHandler.logger.isDebugEnabled()) {
                SslHandler.logger.debug("Swallowing a harmless 'connection reset by peer / broken pipe' error that occurred while writing close_notify in response to the peer's close_notify", cause);
            }
            if (ctx.channel().isActive()) {
                ctx.close();
            }
        }
        else {
            ctx.fireExceptionCaught(cause);
        }
    }
    
    private boolean ignoreException(final Throwable t) {
        if (!(t instanceof SSLException) && t instanceof IOException && this.sslCloseFuture.isDone()) {
            final String message = String.valueOf(t.getMessage()).toLowerCase();
            if (SslHandler.IGNORABLE_ERROR_MESSAGE.matcher(message).matches()) {
                return true;
            }
            final StackTraceElement[] arr$;
            final StackTraceElement[] elements = arr$ = t.getStackTrace();
            for (final StackTraceElement element : arr$) {
                final String classname = element.getClassName();
                final String methodname = element.getMethodName();
                if (!classname.startsWith("io.netty.")) {
                    if ("read".equals(methodname)) {
                        if (SslHandler.IGNORABLE_CLASS_IN_STACK.matcher(classname).matches()) {
                            return true;
                        }
                        try {
                            final Class<?> clazz = this.getClass().getClassLoader().loadClass(classname);
                            if (SocketChannel.class.isAssignableFrom(clazz) || DatagramChannel.class.isAssignableFrom(clazz)) {
                                return true;
                            }
                            if (PlatformDependent.javaVersion() >= 7 && "com.sun.nio.sctp.SctpChannel".equals(clazz.getSuperclass().getName())) {
                                return true;
                            }
                        }
                        catch (ClassNotFoundException ex) {}
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean isEncrypted(final ByteBuf buffer) {
        return getEncryptedPacketLength(buffer) != -1;
    }
    
    private static int getEncryptedPacketLength(final ByteBuf buffer) {
        if (buffer.readableBytes() < 5) {
            throw new IllegalArgumentException("buffer must have at least 5 readable bytes");
        }
        int packetLength = 0;
        boolean tls = false;
        switch (buffer.getUnsignedByte(buffer.readerIndex())) {
            case 20:
            case 21:
            case 22:
            case 23: {
                tls = true;
                break;
            }
            default: {
                tls = false;
                break;
            }
        }
        if (tls) {
            final int majorVersion = buffer.getUnsignedByte(buffer.readerIndex() + 1);
            if (majorVersion == 3) {
                packetLength = (getShort(buffer, buffer.readerIndex() + 3) & 0xFFFF) + 5;
                if (packetLength <= 5) {
                    tls = false;
                }
            }
            else {
                tls = false;
            }
        }
        if (!tls) {
            boolean sslv2 = true;
            final int headerLength = ((buffer.getUnsignedByte(buffer.readerIndex()) & 0x80) != 0x0) ? 2 : 3;
            final int majorVersion2 = buffer.getUnsignedByte(buffer.readerIndex() + headerLength + 1);
            if (majorVersion2 == 2 || majorVersion2 == 3) {
                if (headerLength == 2) {
                    packetLength = (getShort(buffer, buffer.readerIndex()) & 0x7FFF) + 2;
                }
                else {
                    packetLength = (getShort(buffer, buffer.readerIndex()) & 0x3FFF) + 3;
                }
                if (packetLength <= headerLength) {
                    sslv2 = false;
                }
            }
            else {
                sslv2 = false;
            }
            if (!sslv2) {
                return -1;
            }
        }
        return packetLength;
    }
    
    @Override
    public void inboundBufferUpdated(final ChannelHandlerContext ctx) throws Exception {
        final ByteBuf in = ctx.inboundByteBuffer();
        if (in.readableBytes() < 5) {
            return;
        }
        final int packetLength = getEncryptedPacketLength(in);
        if (packetLength == -1) {
            final NotSslRecordException e = new NotSslRecordException("not an SSL/TLS record: " + BufUtil.hexDump(in));
            in.skipBytes(in.readableBytes());
            ctx.fireExceptionCaught((Throwable)e);
            this.setHandshakeFailure(e);
            return;
        }
        assert packetLength > 0;
        final ByteBuf out = ctx.nextInboundByteBuffer();
        boolean wrapLater = false;
        int bytesProduced = 0;
        try {
        Label_0323:
            while (true) {
                final SSLEngineResult result = unwrap(this.engine, in, out);
                bytesProduced += result.bytesProduced();
                switch (result.getStatus()) {
                    case CLOSED: {
                        this.sslCloseFuture.setClosed();
                        break;
                    }
                    case BUFFER_UNDERFLOW: {
                        break Label_0323;
                    }
                }
                switch (result.getHandshakeStatus()) {
                    case NEED_UNWRAP: {
                        break;
                    }
                    case NEED_WRAP: {
                        wrapLater = true;
                        break;
                    }
                    case NEED_TASK: {
                        this.runDelegatedTasks();
                        break;
                    }
                    case FINISHED: {
                        this.setHandshakeSuccess();
                        wrapLater = true;
                        continue;
                    }
                    case NOT_HANDSHAKING: {
                        break;
                    }
                    default: {
                        throw new IllegalStateException("Unknown handshake status: " + result.getHandshakeStatus());
                    }
                }
                if (result.bytesConsumed() == 0 && result.bytesProduced() == 0) {
                    break;
                }
            }
            if (wrapLater) {
                this.flush0(ctx, ctx.newPromise(), true);
            }
        }
        catch (SSLException e2) {
            this.setHandshakeFailure(e2);
            throw e2;
        }
        finally {
            if (bytesProduced > 0) {
                ctx.fireInboundBufferUpdated();
            }
        }
    }
    
    private static short getShort(final ByteBuf buf, final int offset) {
        return (short)(buf.getByte(offset) << 8 | (buf.getByte(offset + 1) & 0xFF));
    }
    
    private static SSLEngineResult unwrap(final SSLEngine engine, final ByteBuf in, final ByteBuf out) throws SSLException {
        final ByteBuffer in2 = in.nioBuffer();
        while (true) {
            final ByteBuffer out2 = out.nioBuffer(out.writerIndex(), out.writableBytes());
            final SSLEngineResult result = engine.unwrap(in2, out2);
            in.skipBytes(result.bytesConsumed());
            out.writerIndex(out.writerIndex() + result.bytesProduced());
            switch (result.getStatus()) {
                case BUFFER_OVERFLOW: {
                    out.ensureWritable(engine.getSession().getApplicationBufferSize());
                    continue;
                }
                default: {
                    return result;
                }
            }
        }
    }
    
    private void runDelegatedTasks() {
        while (true) {
            final Runnable task = this.engine.getDelegatedTask();
            if (task == null) {
                break;
            }
            this.delegatedTaskExecutor.execute(task);
        }
    }
    
    private void setHandshakeSuccess() {
        while (true) {
            final ChannelPromise p = this.handshakePromises.poll();
            if (p == null) {
                break;
            }
            p.setSuccess();
        }
    }
    
    private void setHandshakeFailure(Throwable cause) {
        this.engine.closeOutbound();
        final boolean disconnected = cause == null || cause instanceof ClosedChannelException;
        try {
            this.engine.closeInbound();
        }
        catch (SSLException e) {
            if (!disconnected) {
                SslHandler.logger.warn("SSLEngine.closeInbound() raised an exception after a handshake failure.", e);
            }
            else if (!this.closeNotifyWriteListener.done) {
                SslHandler.logger.warn("SSLEngine.closeInbound() raised an exception due to closed connection.", e);
            }
        }
        if (!this.handshakePromises.isEmpty()) {
            if (cause == null) {
                cause = new ClosedChannelException();
            }
            while (true) {
                final ChannelPromise p = this.handshakePromises.poll();
                if (p == null) {
                    break;
                }
                p.setFailure(cause);
            }
        }
        this.flush0(this.ctx, 0, cause);
    }
    
    private void closeOutboundAndChannel(final ChannelHandlerContext ctx, final ChannelPromise promise, final boolean disconnect) throws Exception {
        if (!ctx.channel().isActive()) {
            if (disconnect) {
                ctx.disconnect(promise);
            }
            else {
                ctx.close(promise);
            }
            return;
        }
        this.engine.closeOutbound();
        final ChannelPromise closeNotifyFuture = ctx.newPromise().addListener((GenericFutureListener<? extends Future<Void>>)this.closeNotifyWriteListener);
        this.flush0(ctx, closeNotifyFuture, true);
        this.safeClose(ctx, closeNotifyFuture, promise);
    }
    
    @Override
    public void beforeAdd(final ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }
    
    @Override
    public void afterAdd(final ChannelHandlerContext ctx) throws Exception {
        if (ctx.channel().isActive()) {
            this.handshake();
        }
    }
    
    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        if (!this.startTls && this.engine.getUseClientMode()) {
            this.handshake().addListener((GenericFutureListener<? extends Future<Void>>)new ChannelFutureListener() {
                @Override
                public void operationComplete(final ChannelFuture future) throws Exception {
                    if (!future.isSuccess()) {
                        ctx.pipeline().fireExceptionCaught(future.cause());
                        ctx.close();
                    }
                }
            });
        }
        ctx.fireChannelActive();
    }
    
    private void safeClose(final ChannelHandlerContext ctx, final ChannelFuture flushFuture, final ChannelPromise promise) {
        if (!ctx.channel().isActive()) {
            ctx.close(promise);
            return;
        }
        ScheduledFuture<?> timeoutFuture;
        if (this.closeNotifyTimeoutMillis > 0L) {
            timeoutFuture = ctx.executor().schedule((Runnable)new Runnable() {
                @Override
                public void run() {
                    SslHandler.logger.warn(ctx.channel() + " last write attempt timed out." + " Force-closing the connection.");
                    ctx.close(promise);
                }
            }, this.closeNotifyTimeoutMillis, TimeUnit.MILLISECONDS);
        }
        else {
            timeoutFuture = null;
        }
        flushFuture.addListener((GenericFutureListener<? extends Future<Void>>)new ChannelFutureListener() {
            @Override
            public void operationComplete(final ChannelFuture f) throws Exception {
                if (timeoutFuture != null) {
                    timeoutFuture.cancel(false);
                }
                if (ctx.channel().isActive()) {
                    ctx.close(promise);
                }
            }
        });
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(SslHandler.class);
        IGNORABLE_CLASS_IN_STACK = Pattern.compile("^.*(?:Socket|Datagram|Sctp)Channel.*$");
        IGNORABLE_ERROR_MESSAGE = Pattern.compile("^.*(?:connection.*reset|connection.*closed|broken.*pipe).*$", 2);
    }
    
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
    
    private static final class CloseNotifyListener implements ChannelFutureListener
    {
        volatile boolean done;
        
        @Override
        public void operationComplete(final ChannelFuture future) throws Exception {
            if (future.isSuccess()) {
                if (this.done) {
                    throw new IllegalStateException("notified twice");
                }
                this.done = true;
            }
        }
    }
    
    private final class SSLEngineInboundCloseFuture extends DefaultChannelPromise
    {
        public SSLEngineInboundCloseFuture() {
            super((Channel)null);
        }
        
        void setClosed() {
            super.trySuccess();
        }
        
        @Override
        public Channel channel() {
            if (SslHandler.this.ctx == null) {
                return null;
            }
            return SslHandler.this.ctx.channel();
        }
        
        @Override
        public boolean trySuccess() {
            return false;
        }
        
        @Override
        public boolean tryFailure(final Throwable cause) {
            return false;
        }
        
        @Override
        public ChannelPromise setSuccess() {
            throw new IllegalStateException();
        }
        
        @Override
        public ChannelPromise setFailure(final Throwable cause) {
            throw new IllegalStateException();
        }
    }
}
