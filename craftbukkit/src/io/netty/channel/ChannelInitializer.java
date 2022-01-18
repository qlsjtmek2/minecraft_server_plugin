// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.InternalLogger;

@ChannelHandler.Sharable
public abstract class ChannelInitializer<C extends Channel> extends ChannelStateHandlerAdapter
{
    private static final InternalLogger logger;
    
    protected abstract void initChannel(final C p0) throws Exception;
    
    @Override
    public final void channelRegistered(final ChannelHandlerContext ctx) throws Exception {
        boolean removed = false;
        boolean success = false;
        try {
            this.initChannel(ctx.channel());
            ctx.pipeline().remove(this);
            removed = true;
            ctx.fireChannelRegistered();
            success = true;
        }
        catch (Throwable t) {
            ChannelInitializer.logger.warn("Failed to initialize a channel. Closing: " + ctx.channel(), t);
        }
        finally {
            if (!removed) {
                ctx.pipeline().remove(this);
            }
            if (!success) {
                ctx.close();
            }
        }
    }
    
    @Override
    public void inboundBufferUpdated(final ChannelHandlerContext ctx) throws Exception {
        ctx.fireInboundBufferUpdated();
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(ChannelInitializer.class);
    }
}
