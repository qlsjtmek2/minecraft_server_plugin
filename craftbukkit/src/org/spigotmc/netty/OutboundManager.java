// 
// Decompiled by Procyon v0.5.30
// 

package org.spigotmc.netty;

import net.minecraft.server.v1_5_R3.PendingConnection;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOperationHandlerAdapter;

class OutboundManager extends ChannelOperationHandlerAdapter
{
    private static final int FLUSH_TIME = 2;
    public long lastFlush;
    private final NettyNetworkManager manager;
    
    OutboundManager(final NettyNetworkManager manager) {
        this.manager = manager;
    }
    
    public void flush(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
        if (this.manager.connection instanceof PendingConnection || System.currentTimeMillis() - this.lastFlush > 2L) {
            this.lastFlush = System.currentTimeMillis();
            ctx.flush(promise);
        }
    }
}
