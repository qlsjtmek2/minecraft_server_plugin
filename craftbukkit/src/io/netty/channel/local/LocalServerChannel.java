// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.local;

import io.netty.buffer.MessageBuf;
import io.netty.channel.ChannelPipeline;
import io.netty.util.concurrent.SingleThreadEventExecutor;
import java.net.SocketAddress;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.channel.EventLoop;
import io.netty.channel.Channel;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.ChannelConfig;
import io.netty.channel.AbstractServerChannel;

public class LocalServerChannel extends AbstractServerChannel
{
    private final ChannelConfig config;
    private final Runnable shutdownHook;
    private volatile int state;
    private volatile LocalAddress localAddress;
    private volatile boolean acceptInProgress;
    
    public LocalServerChannel() {
        this(null);
    }
    
    public LocalServerChannel(final Integer id) {
        super(id);
        this.config = new DefaultChannelConfig(this);
        this.shutdownHook = new Runnable() {
            @Override
            public void run() {
                LocalServerChannel.this.unsafe().close(LocalServerChannel.this.unsafe().voidFuture());
            }
        };
    }
    
    @Override
    public ChannelConfig config() {
        return this.config;
    }
    
    @Override
    public LocalAddress localAddress() {
        return (LocalAddress)super.localAddress();
    }
    
    @Override
    public LocalAddress remoteAddress() {
        return (LocalAddress)super.remoteAddress();
    }
    
    @Override
    public boolean isOpen() {
        return this.state < 2;
    }
    
    @Override
    public boolean isActive() {
        return this.state == 1;
    }
    
    @Override
    protected boolean isCompatible(final EventLoop loop) {
        return loop instanceof SingleThreadEventLoop;
    }
    
    @Override
    protected SocketAddress localAddress0() {
        return this.localAddress;
    }
    
    @Override
    protected Runnable doRegister() throws Exception {
        ((SingleThreadEventExecutor)this.eventLoop()).addShutdownHook(this.shutdownHook);
        return null;
    }
    
    @Override
    protected void doBind(final SocketAddress localAddress) throws Exception {
        this.localAddress = LocalChannelRegistry.register(this, this.localAddress, localAddress);
        this.state = 1;
    }
    
    @Override
    protected void doPreClose() throws Exception {
        if (this.state > 1) {
            return;
        }
        LocalChannelRegistry.unregister(this.localAddress);
        this.localAddress = null;
        this.state = 2;
    }
    
    @Override
    protected void doClose() throws Exception {
    }
    
    @Override
    protected Runnable doDeregister() throws Exception {
        ((SingleThreadEventExecutor)this.eventLoop()).removeShutdownHook(this.shutdownHook);
        return null;
    }
    
    @Override
    protected void doBeginRead() throws Exception {
        if (this.acceptInProgress) {
            return;
        }
        final ChannelPipeline pipeline = this.pipeline();
        final MessageBuf<Object> buf = pipeline.inboundMessageBuffer();
        if (buf.isEmpty()) {
            this.acceptInProgress = true;
            return;
        }
        pipeline.fireInboundBufferUpdated();
        pipeline.fireChannelReadSuspended();
    }
    
    LocalChannel serve(final LocalChannel peer) {
        final LocalChannel child = new LocalChannel(this, peer);
        this.serve0(child);
        return child;
    }
    
    private void serve0(final LocalChannel child) {
        if (this.eventLoop().inEventLoop()) {
            final ChannelPipeline pipeline = this.pipeline();
            pipeline.inboundMessageBuffer().add(child);
            if (this.acceptInProgress) {
                this.acceptInProgress = false;
                pipeline.fireInboundBufferUpdated();
                pipeline.fireChannelReadSuspended();
            }
        }
        else {
            this.eventLoop().execute(new Runnable() {
                @Override
                public void run() {
                    LocalServerChannel.this.serve0(child);
                }
            });
        }
    }
}
