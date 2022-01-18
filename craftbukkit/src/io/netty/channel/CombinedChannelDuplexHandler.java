// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import java.net.SocketAddress;

public class CombinedChannelDuplexHandler extends ChannelDuplexHandler
{
    private ChannelStateHandler stateHandler;
    private ChannelOperationHandler operationHandler;
    
    protected CombinedChannelDuplexHandler() {
    }
    
    public CombinedChannelDuplexHandler(final ChannelStateHandler stateHandler, final ChannelOperationHandler operationHandler) {
        this.init(stateHandler, operationHandler);
    }
    
    protected final void init(final ChannelStateHandler stateHandler, final ChannelOperationHandler operationHandler) {
        this.validate(stateHandler, operationHandler);
        this.stateHandler = stateHandler;
        this.operationHandler = operationHandler;
    }
    
    private void validate(final ChannelStateHandler stateHandler, final ChannelOperationHandler operationHandler) {
        if (this.stateHandler != null) {
            throw new IllegalStateException("init() can not be invoked if " + CombinedChannelDuplexHandler.class.getSimpleName() + " was constructed with non-default constructor.");
        }
        if (stateHandler == null) {
            throw new NullPointerException("stateHandler");
        }
        if (operationHandler == null) {
            throw new NullPointerException("operationHandler");
        }
        if (stateHandler instanceof ChannelOperationHandler) {
            throw new IllegalArgumentException("stateHandler must not implement " + ChannelOperationHandler.class.getSimpleName() + " to get combined.");
        }
        if (operationHandler instanceof ChannelStateHandler) {
            throw new IllegalArgumentException("operationHandler must not implement " + ChannelStateHandler.class.getSimpleName() + " to get combined.");
        }
        if (stateHandler instanceof ChannelInboundByteHandler && !(this instanceof ChannelInboundByteHandler)) {
            throw new IllegalStateException(this.getClass().getSimpleName() + " must implement " + ChannelInboundByteHandler.class.getSimpleName() + " if stateHandler implements " + ChannelInboundByteHandler.class.getSimpleName());
        }
        if (stateHandler instanceof ChannelInboundMessageHandler && !(this instanceof ChannelInboundMessageHandler)) {
            throw new IllegalStateException(this.getClass().getSimpleName() + " must implement " + ChannelInboundMessageHandler.class.getSimpleName() + " if stateHandler implements " + ChannelInboundMessageHandler.class.getSimpleName());
        }
        if (operationHandler instanceof ChannelOutboundByteHandler && !(this instanceof ChannelOutboundByteHandler)) {
            throw new IllegalStateException(this.getClass().getSimpleName() + " must implement " + ChannelOutboundByteHandler.class.getSimpleName() + " if operationHandler implements " + ChannelOutboundByteHandler.class.getSimpleName());
        }
        if (operationHandler instanceof ChannelOutboundMessageHandler && !(this instanceof ChannelOutboundMessageHandler)) {
            throw new IllegalStateException(this.getClass().getSimpleName() + " must implement " + ChannelOutboundMessageHandler.class.getSimpleName() + " if operationHandler implements " + ChannelOutboundMessageHandler.class.getSimpleName());
        }
    }
    
    protected final ChannelStateHandler stateHandler() {
        return this.stateHandler;
    }
    
    protected final ChannelOperationHandler operationHandler() {
        return this.operationHandler;
    }
    
    @Override
    public void beforeAdd(final ChannelHandlerContext ctx) throws Exception {
        if (this.stateHandler == null) {
            throw new IllegalStateException("init() must be invoked before being added to a " + ChannelPipeline.class.getSimpleName() + " if " + CombinedChannelDuplexHandler.class.getSimpleName() + " was constructed with the default constructor.");
        }
        try {
            this.stateHandler.beforeAdd(ctx);
        }
        finally {
            this.operationHandler.beforeAdd(ctx);
        }
    }
    
    @Override
    public void afterAdd(final ChannelHandlerContext ctx) throws Exception {
        try {
            this.stateHandler.afterAdd(ctx);
        }
        finally {
            this.operationHandler.afterAdd(ctx);
        }
    }
    
    @Override
    public void beforeRemove(final ChannelHandlerContext ctx) throws Exception {
        try {
            this.stateHandler.beforeRemove(ctx);
        }
        finally {
            this.operationHandler.beforeRemove(ctx);
        }
    }
    
    @Override
    public void afterRemove(final ChannelHandlerContext ctx) throws Exception {
        try {
            this.stateHandler.afterRemove(ctx);
        }
        finally {
            this.operationHandler.afterRemove(ctx);
        }
    }
    
    @Override
    public void channelRegistered(final ChannelHandlerContext ctx) throws Exception {
        this.stateHandler.channelRegistered(ctx);
    }
    
    @Override
    public void channelUnregistered(final ChannelHandlerContext ctx) throws Exception {
        this.stateHandler.channelUnregistered(ctx);
    }
    
    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        this.stateHandler.channelActive(ctx);
    }
    
    @Override
    public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
        this.stateHandler.channelInactive(ctx);
    }
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
        this.stateHandler.exceptionCaught(ctx, cause);
    }
    
    @Override
    public void userEventTriggered(final ChannelHandlerContext ctx, final Object evt) throws Exception {
        this.stateHandler.userEventTriggered(ctx, evt);
    }
    
    @Override
    public void inboundBufferUpdated(final ChannelHandlerContext ctx) throws Exception {
        this.stateHandler.inboundBufferUpdated(ctx);
        if (this.stateHandler instanceof ChannelInboundByteHandler) {
            ((ChannelInboundByteHandler)this.stateHandler).discardInboundReadBytes(ctx);
        }
    }
    
    @Override
    public void bind(final ChannelHandlerContext ctx, final SocketAddress localAddress, final ChannelPromise promise) throws Exception {
        this.operationHandler.bind(ctx, localAddress, promise);
    }
    
    @Override
    public void connect(final ChannelHandlerContext ctx, final SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise promise) throws Exception {
        this.operationHandler.connect(ctx, remoteAddress, localAddress, promise);
    }
    
    @Override
    public void disconnect(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
        this.operationHandler.disconnect(ctx, promise);
    }
    
    @Override
    public void close(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
        this.operationHandler.close(ctx, promise);
    }
    
    @Override
    public void deregister(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
        this.operationHandler.deregister(ctx, promise);
    }
    
    @Override
    public void read(final ChannelHandlerContext ctx) {
        this.operationHandler.read(ctx);
    }
    
    @Override
    public void flush(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
        this.operationHandler.flush(ctx, promise);
    }
    
    @Override
    public void sendFile(final ChannelHandlerContext ctx, final FileRegion region, final ChannelPromise promise) throws Exception {
        this.operationHandler.sendFile(ctx, region, promise);
    }
}
