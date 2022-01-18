// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import java.lang.annotation.Annotation;

public abstract class ChannelHandlerAdapter implements ChannelHandler
{
    boolean added;
    
    final boolean isSharable() {
        return this.getClass().isAnnotationPresent(Sharable.class);
    }
    
    @Override
    public void beforeAdd(final ChannelHandlerContext ctx) throws Exception {
    }
    
    @Override
    public void afterAdd(final ChannelHandlerContext ctx) throws Exception {
    }
    
    @Override
    public void beforeRemove(final ChannelHandlerContext ctx) throws Exception {
    }
    
    @Override
    public void afterRemove(final ChannelHandlerContext ctx) throws Exception {
    }
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }
}
