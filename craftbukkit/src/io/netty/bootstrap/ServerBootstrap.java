// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.bootstrap;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import io.netty.buffer.Buf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.MessageBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandler;
import io.netty.channel.ChannelStateHandlerAdapter;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.InetAddress;
import io.netty.channel.ChannelFuture;
import java.net.SocketAddress;
import io.netty.channel.ChannelPipeline;
import java.util.Iterator;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.Channel;
import java.util.LinkedHashMap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.util.AttributeKey;
import io.netty.channel.ChannelOption;
import java.util.Map;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.channel.ServerChannel;

public final class ServerBootstrap extends AbstractBootstrap<ServerBootstrap, ServerChannel>
{
    private static final InternalLogger logger;
    private final Map<ChannelOption<?>, Object> childOptions;
    private final Map<AttributeKey<?>, Object> childAttrs;
    private volatile EventLoopGroup childGroup;
    private volatile ChannelHandler childHandler;
    
    public ServerBootstrap() {
        this.childOptions = new LinkedHashMap<ChannelOption<?>, Object>();
        this.childAttrs = new LinkedHashMap<AttributeKey<?>, Object>();
    }
    
    private ServerBootstrap(final ServerBootstrap bootstrap) {
        super(bootstrap);
        this.childOptions = new LinkedHashMap<ChannelOption<?>, Object>();
        this.childAttrs = new LinkedHashMap<AttributeKey<?>, Object>();
        this.childGroup = bootstrap.childGroup;
        this.childHandler = bootstrap.childHandler;
        synchronized (bootstrap.childOptions) {
            this.childOptions.putAll(bootstrap.childOptions);
        }
        synchronized (bootstrap.childAttrs) {
            this.childAttrs.putAll(bootstrap.childAttrs);
        }
    }
    
    @Override
    public ServerBootstrap group(final EventLoopGroup group) {
        return this.group(group, group);
    }
    
    public ServerBootstrap group(final EventLoopGroup parentGroup, final EventLoopGroup childGroup) {
        super.group(parentGroup);
        if (childGroup == null) {
            throw new NullPointerException("childGroup");
        }
        if (this.childGroup != null) {
            throw new IllegalStateException("childGroup set already");
        }
        this.childGroup = childGroup;
        return this;
    }
    
    public <T> ServerBootstrap childOption(final ChannelOption<T> childOption, final T value) {
        if (childOption == null) {
            throw new NullPointerException("childOption");
        }
        if (value == null) {
            synchronized (this.childOptions) {
                this.childOptions.remove(childOption);
            }
        }
        else {
            synchronized (this.childOptions) {
                this.childOptions.put(childOption, value);
            }
        }
        return this;
    }
    
    public <T> ServerBootstrap childAttr(final AttributeKey<T> childKey, final T value) {
        if (childKey == null) {
            throw new NullPointerException("childKey");
        }
        if (value == null) {
            this.childAttrs.remove(childKey);
        }
        else {
            this.childAttrs.put(childKey, value);
        }
        return this;
    }
    
    public ServerBootstrap childHandler(final ChannelHandler childHandler) {
        if (childHandler == null) {
            throw new NullPointerException("childHandler");
        }
        this.childHandler = childHandler;
        return this;
    }
    
    @Override
    void init(final Channel channel) throws Exception {
        final Map<ChannelOption<?>, Object> options = this.options();
        synchronized (options) {
            channel.config().setOptions(options);
        }
        final Map<AttributeKey<?>, Object> attrs = this.attrs();
        synchronized (attrs) {
            for (final Map.Entry<AttributeKey<?>, Object> e : attrs.entrySet()) {
                final AttributeKey<Object> key = e.getKey();
                channel.attr(key).set(e.getValue());
            }
        }
        final ChannelPipeline p = channel.pipeline();
        if (this.handler() != null) {
            p.addLast(this.handler());
        }
        final EventLoopGroup currentChildGroup = this.childGroup;
        final ChannelHandler currentChildHandler = this.childHandler;
        final Map.Entry<ChannelOption<?>, Object>[] currentChildOptions;
        synchronized (this.childOptions) {
            currentChildOptions = this.childOptions.entrySet().toArray(newOptionArray(this.childOptions.size()));
        }
        final Map.Entry<AttributeKey<?>, Object>[] currentChildAttrs;
        synchronized (this.childAttrs) {
            currentChildAttrs = this.childAttrs.entrySet().toArray(newAttrArray(this.childAttrs.size()));
        }
        p.addLast(new ChannelInitializer<Channel>() {
            public void initChannel(final Channel ch) throws Exception {
                ch.pipeline().addLast(new ServerBootstrapAcceptor(currentChildGroup, currentChildHandler, currentChildOptions, currentChildAttrs));
            }
        });
    }
    
    @Override
    public void shutdown() {
        super.shutdown();
        if (this.childGroup != null) {
            this.childGroup.shutdown();
        }
    }
    
    @Override
    public ServerBootstrap validate() {
        super.validate();
        if (this.childHandler == null) {
            throw new IllegalStateException("childHandler not set");
        }
        if (this.childGroup == null) {
            ServerBootstrap.logger.warn("childGroup is not set. Using parentGroup instead.");
            this.childGroup = this.group();
        }
        return this;
    }
    
    private static Map.Entry<ChannelOption<?>, Object>[] newOptionArray(final int size) {
        return (Map.Entry<ChannelOption<?>, Object>[])new Map.Entry[size];
    }
    
    private static Map.Entry<AttributeKey<?>, Object>[] newAttrArray(final int size) {
        return (Map.Entry<AttributeKey<?>, Object>[])new Map.Entry[size];
    }
    
    @Override
    public ServerBootstrap clone() {
        return new ServerBootstrap(this);
    }
    
    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder(super.toString());
        buf.setLength(buf.length() - 1);
        buf.append(", ");
        if (this.childGroup != null) {
            buf.append("childGroup: ");
            buf.append(this.childGroup.getClass().getSimpleName());
            buf.append(", ");
        }
        synchronized (this.childOptions) {
            if (!this.childOptions.isEmpty()) {
                buf.append("childOptions: ");
                buf.append(this.childOptions);
                buf.append(", ");
            }
        }
        synchronized (this.childAttrs) {
            if (!this.childAttrs.isEmpty()) {
                buf.append("childAttrs: ");
                buf.append(this.childAttrs);
                buf.append(", ");
            }
        }
        if (this.childHandler != null) {
            buf.append("childHandler: ");
            buf.append(this.childHandler);
            buf.append(", ");
        }
        if (buf.charAt(buf.length() - 1) == '(') {
            buf.append(')');
        }
        else {
            buf.setCharAt(buf.length() - 2, ')');
            buf.setLength(buf.length() - 1);
        }
        return buf.toString();
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(ServerBootstrap.class);
    }
    
    private static class ServerBootstrapAcceptor extends ChannelStateHandlerAdapter implements ChannelInboundMessageHandler<Channel>
    {
        private final EventLoopGroup childGroup;
        private final ChannelHandler childHandler;
        private final Map.Entry<ChannelOption<?>, Object>[] childOptions;
        private final Map.Entry<AttributeKey<?>, Object>[] childAttrs;
        
        ServerBootstrapAcceptor(final EventLoopGroup childGroup, final ChannelHandler childHandler, final Map.Entry<ChannelOption<?>, Object>[] childOptions, final Map.Entry<AttributeKey<?>, Object>[] childAttrs) {
            this.childGroup = childGroup;
            this.childHandler = childHandler;
            this.childOptions = childOptions;
            this.childAttrs = childAttrs;
        }
        
        @Override
        public MessageBuf<Channel> newInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
            return Unpooled.messageBuffer();
        }
        
        @Override
        public void inboundBufferUpdated(final ChannelHandlerContext ctx) {
            final MessageBuf<Channel> in = ctx.inboundMessageBuffer();
            while (true) {
                final Channel child = in.poll();
                if (child == null) {
                    break;
                }
                child.pipeline().addLast(this.childHandler);
                for (final Map.Entry<ChannelOption<?>, Object> e : this.childOptions) {
                    try {
                        if (!child.config().setOption(e.getKey(), e.getValue())) {
                            ServerBootstrap.logger.warn("Unknown channel option: " + e);
                        }
                    }
                    catch (Throwable t) {
                        ServerBootstrap.logger.warn("Failed to set a channel option: " + child, t);
                    }
                }
                for (final Map.Entry<AttributeKey<?>, Object> e2 : this.childAttrs) {
                    child.attr(e2.getKey()).set(e2.getValue());
                }
                try {
                    this.childGroup.register(child);
                }
                catch (Throwable t2) {
                    child.unsafe().closeForcibly();
                    ServerBootstrap.logger.warn("Failed to register an accepted channel: " + child, t2);
                }
            }
        }
        
        @Override
        public void freeInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
            ctx.inboundMessageBuffer().release();
        }
    }
}
