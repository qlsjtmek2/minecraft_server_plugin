// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.socket.aio;

import java.nio.channels.ClosedChannelException;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import java.nio.channels.InterruptedByTimeoutException;
import io.netty.channel.aio.AioCompletionHandler;
import io.netty.buffer.BufType;
import io.netty.channel.ChannelFlushPromiseNotifier;
import io.netty.channel.socket.SocketChannelConfig;
import io.netty.channel.ChannelConfig;
import io.netty.channel.AbstractChannel;
import java.nio.channels.WritableByteChannel;
import io.netty.channel.FileRegion;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;
import io.netty.buffer.ByteBuf;
import io.netty.channel.aio.AioEventLoopGroup;
import java.net.SocketAddress;
import io.netty.channel.EventLoop;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.ServerSocketChannel;
import java.net.InetSocketAddress;
import java.nio.channels.NetworkChannel;
import java.nio.channels.AsynchronousChannel;
import io.netty.channel.Channel;
import java.io.IOException;
import io.netty.channel.ChannelException;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.CompletionHandler;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.aio.AbstractAioChannel;

public class AioSocketChannel extends AbstractAioChannel implements SocketChannel
{
    private static final ChannelMetadata METADATA;
    private static final CompletionHandler<Void, AioSocketChannel> CONNECT_HANDLER;
    private static final CompletionHandler<Integer, AioSocketChannel> WRITE_HANDLER;
    private static final CompletionHandler<Integer, AioSocketChannel> READ_HANDLER;
    private static final CompletionHandler<Long, AioSocketChannel> GATHERING_WRITE_HANDLER;
    private static final CompletionHandler<Long, AioSocketChannel> SCATTERING_READ_HANDLER;
    private final DefaultAioSocketChannelConfig config;
    private volatile boolean inputShutdown;
    private volatile boolean outputShutdown;
    private boolean readInProgress;
    private boolean inDoBeginRead;
    private boolean readAgain;
    private boolean writeInProgress;
    private boolean inDoFlushByteBuffer;
    
    private static AsynchronousSocketChannel newSocket(final AsynchronousChannelGroup group) {
        try {
            return AsynchronousSocketChannel.open(group);
        }
        catch (IOException e) {
            throw new ChannelException("Failed to open a socket.", e);
        }
    }
    
    public AioSocketChannel() {
        this(null, null, null);
    }
    
    AioSocketChannel(final AioServerSocketChannel parent, final Integer id, final AsynchronousSocketChannel ch) {
        super(parent, id, ch);
        this.config = new DefaultAioSocketChannelConfig(this, ch);
    }
    
    @Override
    public InetSocketAddress localAddress() {
        return (InetSocketAddress)super.localAddress();
    }
    
    @Override
    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress)super.remoteAddress();
    }
    
    @Override
    public ServerSocketChannel parent() {
        return (ServerSocketChannel)super.parent();
    }
    
    @Override
    public boolean isActive() {
        return this.ch != null && this.javaChannel().isOpen() && this.remoteAddress0() != null;
    }
    
    @Override
    protected AsynchronousSocketChannel javaChannel() {
        return (AsynchronousSocketChannel)super.javaChannel();
    }
    
    @Override
    public ChannelMetadata metadata() {
        return AioSocketChannel.METADATA;
    }
    
    @Override
    public boolean isInputShutdown() {
        return this.inputShutdown;
    }
    
    @Override
    public boolean isOutputShutdown() {
        return this.outputShutdown;
    }
    
    @Override
    public ChannelFuture shutdownOutput() {
        return this.shutdownOutput(this.newPromise());
    }
    
    @Override
    public ChannelFuture shutdownOutput(final ChannelPromise promise) {
        final EventLoop loop = this.eventLoop();
        if (loop.inEventLoop()) {
            try {
                this.javaChannel().shutdownOutput();
                this.outputShutdown = true;
                promise.setSuccess();
            }
            catch (Throwable t) {
                promise.setFailure(t);
            }
        }
        else {
            loop.execute(new Runnable() {
                @Override
                public void run() {
                    AioSocketChannel.this.shutdownOutput(promise);
                }
            });
        }
        return promise;
    }
    
    @Override
    protected void doConnect(final SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise promise) {
        if (localAddress != null) {
            try {
                this.javaChannel().bind(localAddress);
            }
            catch (IOException e) {
                promise.setFailure((Throwable)e);
                return;
            }
        }
        this.javaChannel().connect(remoteAddress, this, AioSocketChannel.CONNECT_HANDLER);
    }
    
    @Override
    protected InetSocketAddress localAddress0() {
        if (this.ch == null) {
            return null;
        }
        try {
            return (InetSocketAddress)this.javaChannel().getLocalAddress();
        }
        catch (IOException e) {
            return null;
        }
    }
    
    @Override
    protected InetSocketAddress remoteAddress0() {
        if (this.ch == null) {
            return null;
        }
        try {
            return (InetSocketAddress)this.javaChannel().getRemoteAddress();
        }
        catch (IOException e) {
            return null;
        }
    }
    
    @Override
    protected Runnable doRegister() throws Exception {
        super.doRegister();
        if (this.ch == null) {
            this.ch = newSocket(((AioEventLoopGroup)this.eventLoop().parent()).channelGroup());
            this.config.assign(this.javaChannel());
        }
        return null;
    }
    
    @Override
    protected void doBind(final SocketAddress localAddress) throws Exception {
        this.javaChannel().bind(localAddress);
    }
    
    @Override
    protected void doDisconnect() throws Exception {
        this.doClose();
    }
    
    @Override
    protected void doClose() throws Exception {
        this.javaChannel().close();
        this.inputShutdown = true;
        this.outputShutdown = true;
    }
    
    @Override
    protected boolean isFlushPending() {
        return false;
    }
    
    @Override
    protected void doFlushByteBuffer(final ByteBuf buf) throws Exception {
        if (this.inDoFlushByteBuffer || this.writeInProgress) {
            return;
        }
        this.inDoFlushByteBuffer = true;
        try {
            if (buf.isReadable()) {
                while (buf.refCnt() != 0) {
                    buf.discardReadBytes();
                    this.writeInProgress = true;
                    if (buf.nioBufferCount() == 1) {
                        this.javaChannel().write(buf.nioBuffer(), this.config.getWriteTimeout(), TimeUnit.MILLISECONDS, this, AioSocketChannel.WRITE_HANDLER);
                    }
                    else {
                        final ByteBuffer[] buffers = buf.nioBuffers(buf.readerIndex(), buf.readableBytes());
                        if (buffers.length == 1) {
                            this.javaChannel().write(buffers[0], this.config.getWriteTimeout(), TimeUnit.MILLISECONDS, this, AioSocketChannel.WRITE_HANDLER);
                        }
                        else {
                            this.javaChannel().write(buffers, 0, buffers.length, this.config.getWriteTimeout(), TimeUnit.MILLISECONDS, this, AioSocketChannel.GATHERING_WRITE_HANDLER);
                        }
                    }
                    if (this.writeInProgress) {
                        buf.suspendIntermediaryDeallocations();
                        break;
                    }
                    if (!buf.isReadable()) {
                        break;
                    }
                }
            }
            else {
                this.flushFutureNotifier.notifyFlushFutures();
            }
        }
        finally {
            this.inDoFlushByteBuffer = false;
        }
    }
    
    @Override
    protected void doFlushFileRegion(final FileRegion region, final ChannelPromise promise) throws Exception {
        region.transferTo(new WritableByteChannelAdapter(region, promise), 0L);
    }
    
    @Override
    protected void doBeginRead() {
        if (this.inDoBeginRead) {
            this.readAgain = true;
            return;
        }
        if (this.readInProgress || this.inputShutdown) {
            return;
        }
        this.inDoBeginRead = true;
        try {
            while (!this.inputShutdown) {
                final ByteBuf byteBuf = this.pipeline().inboundByteBuffer();
                AbstractChannel.expandReadBuffer(byteBuf);
                this.readInProgress = true;
                if (byteBuf.nioBufferCount() == 1) {
                    final ByteBuffer buffer = byteBuf.nioBuffer(byteBuf.writerIndex(), byteBuf.writableBytes());
                    this.javaChannel().read(buffer, this.config.getReadTimeout(), TimeUnit.MILLISECONDS, this, AioSocketChannel.READ_HANDLER);
                }
                else {
                    final ByteBuffer[] buffers = byteBuf.nioBuffers(byteBuf.writerIndex(), byteBuf.writableBytes());
                    if (buffers.length == 1) {
                        this.javaChannel().read(buffers[0], this.config.getReadTimeout(), TimeUnit.MILLISECONDS, this, AioSocketChannel.READ_HANDLER);
                    }
                    else {
                        this.javaChannel().read(buffers, 0, buffers.length, this.config.getReadTimeout(), TimeUnit.MILLISECONDS, this, AioSocketChannel.SCATTERING_READ_HANDLER);
                    }
                }
                if (!this.readInProgress) {
                    if (this.readAgain) {
                        this.readAgain = false;
                        continue;
                    }
                }
            }
        }
        finally {
            this.inDoBeginRead = false;
        }
    }
    
    @Override
    public AioSocketChannelConfig config() {
        return this.config;
    }
    
    static {
        METADATA = new ChannelMetadata(BufType.BYTE, false);
        CONNECT_HANDLER = new ConnectHandler();
        WRITE_HANDLER = new WriteHandler();
        READ_HANDLER = new ReadHandler();
        GATHERING_WRITE_HANDLER = new WriteHandler();
        SCATTERING_READ_HANDLER = new ReadHandler();
    }
    
    private static final class WriteHandler<T extends Number> extends AioCompletionHandler<T, AioSocketChannel>
    {
        @Override
        protected void completed0(final T result, final AioSocketChannel channel) {
            channel.writeInProgress = false;
            final ByteBuf buf = channel.unsafe().directOutboundContext().outboundByteBuffer();
            if (buf.refCnt() == 0) {
                return;
            }
            buf.resumeIntermediaryDeallocations();
            final int writtenBytes = result.intValue();
            if (writtenBytes > 0) {
                buf.readerIndex(buf.readerIndex() + writtenBytes);
            }
            if (channel.inDoFlushByteBuffer) {
                return;
            }
            final ChannelFlushPromiseNotifier notifier = channel.flushFutureNotifier;
            notifier.increaseWriteCounter(writtenBytes);
            notifier.notifyFlushFutures();
            if (!channel.isActive()) {
                return;
            }
            if (buf.isReadable()) {
                channel.unsafe().flushNow();
            }
        }
        
        @Override
        protected void failed0(final Throwable cause, final AioSocketChannel channel) {
            channel.writeInProgress = false;
            channel.flushFutureNotifier.notifyFlushFutures(cause);
            if (cause instanceof InterruptedByTimeoutException) {
                channel.unsafe().close(channel.unsafe().voidFuture());
            }
        }
    }
    
    private static final class ReadHandler<T extends Number> extends AioCompletionHandler<T, AioSocketChannel>
    {
        @Override
        protected void completed0(final T result, final AioSocketChannel channel) {
            channel.readInProgress = false;
            if (channel.inputShutdown) {
                return;
            }
            final ChannelPipeline pipeline = channel.pipeline();
            final ByteBuf byteBuf = pipeline.inboundByteBuffer();
            boolean closed = false;
            boolean read = false;
            boolean firedChannelReadSuspended = false;
            try {
                final int localReadAmount = result.intValue();
                if (localReadAmount > 0) {
                    byteBuf.writerIndex(byteBuf.writerIndex() + localReadAmount);
                    read = true;
                }
                else if (localReadAmount < 0) {
                    closed = true;
                }
            }
            catch (Throwable t) {
                if (read) {
                    read = false;
                    pipeline.fireInboundBufferUpdated();
                }
                if (!closed && channel.isOpen()) {
                    firedChannelReadSuspended = true;
                    pipeline.fireChannelReadSuspended();
                }
                pipeline.fireExceptionCaught(t);
            }
            finally {
                if (read) {
                    pipeline.fireInboundBufferUpdated();
                }
                if (closed || !channel.isOpen()) {
                    channel.inputShutdown = true;
                    if (channel.isOpen()) {
                        if (channel.config().isAllowHalfClosure()) {
                            pipeline.fireUserEventTriggered((Object)ChannelInputShutdownEvent.INSTANCE);
                        }
                        else {
                            channel.unsafe().close(channel.unsafe().voidFuture());
                        }
                    }
                }
                else if (!firedChannelReadSuspended) {
                    pipeline.fireChannelReadSuspended();
                }
            }
        }
        
        @Override
        protected void failed0(final Throwable t, final AioSocketChannel channel) {
            channel.readInProgress = false;
            if (t instanceof ClosedChannelException) {
                channel.inputShutdown = true;
                return;
            }
            channel.pipeline().fireExceptionCaught(t);
            if (t instanceof IOException || t instanceof InterruptedByTimeoutException) {
                channel.unsafe().close(channel.unsafe().voidFuture());
            }
        }
    }
    
    private static final class ConnectHandler extends AioCompletionHandler<Void, AioSocketChannel>
    {
        @Override
        protected void completed0(final Void result, final AioSocketChannel channel) {
            ((DefaultAioUnsafe)channel.unsafe()).connectSuccess();
        }
        
        @Override
        protected void failed0(final Throwable exc, final AioSocketChannel channel) {
            ((DefaultAioUnsafe)channel.unsafe()).connectFailed(exc);
        }
    }
    
    private final class WritableByteChannelAdapter implements WritableByteChannel
    {
        private final FileRegion region;
        private final ChannelPromise promise;
        private long written;
        
        public WritableByteChannelAdapter(final FileRegion region, final ChannelPromise promise) {
            this.region = region;
            this.promise = promise;
        }
        
        @Override
        public int write(final ByteBuffer src) {
            AioSocketChannel.this.javaChannel().write(src, AioSocketChannel.this, new AioCompletionHandler<Integer, Channel>() {
                public void completed0(final Integer result, final Channel attachment) {
                    try {
                        if (result == 0) {
                            AioSocketChannel.this.javaChannel().write(src, AioSocketChannel.this, this);
                            return;
                        }
                        if (result == -1) {
                            AbstractChannel.checkEOF(WritableByteChannelAdapter.this.region, WritableByteChannelAdapter.this.written);
                            WritableByteChannelAdapter.this.promise.setSuccess();
                            return;
                        }
                        WritableByteChannelAdapter.this.written += result;
                        if (WritableByteChannelAdapter.this.written >= WritableByteChannelAdapter.this.region.count()) {
                            WritableByteChannelAdapter.this.region.release();
                            WritableByteChannelAdapter.this.promise.setSuccess();
                            return;
                        }
                        if (src.hasRemaining()) {
                            AioSocketChannel.this.javaChannel().write(src, AioSocketChannel.this, this);
                        }
                        else {
                            WritableByteChannelAdapter.this.region.transferTo(WritableByteChannelAdapter.this, WritableByteChannelAdapter.this.written);
                        }
                    }
                    catch (Throwable cause) {
                        WritableByteChannelAdapter.this.region.release();
                        WritableByteChannelAdapter.this.promise.setFailure(cause);
                    }
                }
                
                public void failed0(final Throwable exc, final Channel attachment) {
                    WritableByteChannelAdapter.this.region.release();
                    WritableByteChannelAdapter.this.promise.setFailure(exc);
                }
            });
            return 0;
        }
        
        @Override
        public boolean isOpen() {
            return AioSocketChannel.this.javaChannel().isOpen();
        }
        
        @Override
        public void close() throws IOException {
            AioSocketChannel.this.javaChannel().close();
        }
    }
}
