// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.bootstrap;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import io.netty.channel.ChannelException;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Future;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelFuture;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.LinkedHashMap;
import io.netty.channel.ChannelHandler;
import io.netty.util.AttributeKey;
import io.netty.channel.ChannelOption;
import java.util.Map;
import java.net.SocketAddress;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.Channel;

abstract class AbstractBootstrap<B extends AbstractBootstrap<B, C>, C extends Channel> implements Cloneable
{
    private volatile EventLoopGroup group;
    private volatile ChannelFactory<? extends C> channelFactory;
    private volatile SocketAddress localAddress;
    private final Map<ChannelOption<?>, Object> options;
    private final Map<AttributeKey<?>, Object> attrs;
    private volatile ChannelHandler handler;
    
    AbstractBootstrap() {
        this.options = new LinkedHashMap<ChannelOption<?>, Object>();
        this.attrs = new LinkedHashMap<AttributeKey<?>, Object>();
    }
    
    AbstractBootstrap(final AbstractBootstrap<B, C> bootstrap) {
        this.options = new LinkedHashMap<ChannelOption<?>, Object>();
        this.attrs = new LinkedHashMap<AttributeKey<?>, Object>();
        this.group = bootstrap.group;
        this.channelFactory = bootstrap.channelFactory;
        this.handler = bootstrap.handler;
        this.localAddress = bootstrap.localAddress;
        synchronized (bootstrap.options) {
            this.options.putAll(bootstrap.options);
        }
        synchronized (bootstrap.attrs) {
            this.attrs.putAll(bootstrap.attrs);
        }
    }
    
    public B group(final EventLoopGroup group) {
        if (group == null) {
            throw new NullPointerException("group");
        }
        if (this.group != null) {
            throw new IllegalStateException("group set already");
        }
        this.group = group;
        return (B)this;
    }
    
    public B channel(final Class<? extends C> channelClass) {
        if (channelClass == null) {
            throw new NullPointerException("channelClass");
        }
        return this.channelFactory((ChannelFactory<? extends C>)new BootstrapChannelFactory<C>(channelClass));
    }
    
    public B channelFactory(final ChannelFactory<? extends C> channelFactory) {
        if (channelFactory == null) {
            throw new NullPointerException("channelFactory");
        }
        if (this.channelFactory != null) {
            throw new IllegalStateException("channelFactory set already");
        }
        this.channelFactory = channelFactory;
        return (B)this;
    }
    
    public B localAddress(final SocketAddress localAddress) {
        this.localAddress = localAddress;
        return (B)this;
    }
    
    public B localAddress(final int inetPort) {
        return this.localAddress(new InetSocketAddress(inetPort));
    }
    
    public B localAddress(final String inetHost, final int inetPort) {
        return this.localAddress(new InetSocketAddress(inetHost, inetPort));
    }
    
    public B localAddress(final InetAddress inetHost, final int inetPort) {
        return this.localAddress(new InetSocketAddress(inetHost, inetPort));
    }
    
    public <T> B option(final ChannelOption<T> option, final T value) {
        if (option == null) {
            throw new NullPointerException("option");
        }
        if (value == null) {
            synchronized (this.options) {
                this.options.remove(option);
            }
        }
        else {
            synchronized (this.options) {
                this.options.put(option, value);
            }
        }
        return (B)this;
    }
    
    public <T> B attr(final AttributeKey<T> key, final T value) {
        if (key == null) {
            throw new NullPointerException("key");
        }
        if (value == null) {
            synchronized (this.attrs) {
                this.attrs.remove(key);
            }
        }
        else {
            synchronized (this.attrs) {
                this.attrs.put(key, value);
            }
        }
        return (B)this;
    }
    
    public void shutdown() {
        if (this.group != null) {
            this.group.shutdown();
        }
    }
    
    public B validate() {
        if (this.group == null) {
            throw new IllegalStateException("group not set");
        }
        if (this.channelFactory == null) {
            throw new IllegalStateException("factory not set");
        }
        return (B)this;
    }
    
    public abstract B clone();
    
    public ChannelFuture bind() {
        this.validate();
        final SocketAddress localAddress = this.localAddress;
        if (localAddress == null) {
            throw new IllegalStateException("localAddress not set");
        }
        return this.doBind(localAddress);
    }
    
    public ChannelFuture bind(final int inetPort) {
        return this.bind(new InetSocketAddress(inetPort));
    }
    
    public ChannelFuture bind(final String inetHost, final int inetPort) {
        return this.bind(new InetSocketAddress(inetHost, inetPort));
    }
    
    public ChannelFuture bind(final InetAddress inetHost, final int inetPort) {
        return this.bind(new InetSocketAddress(inetHost, inetPort));
    }
    
    public ChannelFuture bind(final SocketAddress localAddress) {
        this.validate();
        if (localAddress == null) {
            throw new NullPointerException("localAddress");
        }
        return this.doBind(localAddress);
    }
    
    private ChannelFuture doBind(final SocketAddress localAddress) {
        final ChannelFuture regPromise = this.initAndRegister();
        final Channel channel = regPromise.channel();
        final ChannelPromise promise = channel.newPromise();
        if (regPromise.isDone()) {
            doBind0(regPromise, channel, localAddress, promise);
        }
        else {
            regPromise.addListener((GenericFutureListener<? extends Future<Void>>)new ChannelFutureListener() {
                @Override
                public void operationComplete(final ChannelFuture future) throws Exception {
                    doBind0(future, channel, localAddress, promise);
                }
            });
        }
        return promise;
    }
    
    final ChannelFuture initAndRegister() {
        final Channel channel = (Channel)this.channelFactory().newChannel();
        try {
            this.init(channel);
        }
        catch (Throwable t) {
            channel.unsafe().closeForcibly();
            return channel.newFailedFuture(t);
        }
        final ChannelPromise regPromise = channel.newPromise();
        this.group().register(channel, regPromise);
        if (regPromise.cause() != null) {
            if (channel.isRegistered()) {
                channel.close();
            }
            else {
                channel.unsafe().closeForcibly();
            }
        }
        return regPromise;
    }
    
    abstract void init(final Channel p0) throws Exception;
    
    private static void doBind0(final ChannelFuture regFuture, final Channel channel, final SocketAddress localAddress, final ChannelPromise promise) {
        channel.eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                if (regFuture.isSuccess()) {
                    channel.bind(localAddress, promise).addListener((GenericFutureListener<? extends Future<Void>>)ChannelFutureListener.CLOSE_ON_FAILURE);
                }
                else {
                    promise.setFailure(regFuture.cause());
                }
            }
        });
    }
    
    public B handler(final ChannelHandler handler) {
        if (handler == null) {
            throw new NullPointerException("handler");
        }
        this.handler = handler;
        return (B)this;
    }
    
    final SocketAddress localAddress() {
        return this.localAddress;
    }
    
    final ChannelFactory<? extends C> channelFactory() {
        return this.channelFactory;
    }
    
    final ChannelHandler handler() {
        return this.handler;
    }
    
    final EventLoopGroup group() {
        return this.group;
    }
    
    final Map<ChannelOption<?>, Object> options() {
        return this.options;
    }
    
    final Map<AttributeKey<?>, Object> attrs() {
        return this.attrs;
    }
    
    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        buf.append(this.getClass().getSimpleName());
        buf.append('(');
        if (this.group != null) {
            buf.append("group: ");
            buf.append(this.group.getClass().getSimpleName());
            buf.append(", ");
        }
        if (this.channelFactory != null) {
            buf.append("channelFactory: ");
            buf.append(this.channelFactory);
            buf.append(", ");
        }
        if (this.localAddress != null) {
            buf.append("localAddress: ");
            buf.append(this.localAddress);
            buf.append(", ");
        }
        synchronized (this.options) {
            if (!this.options.isEmpty()) {
                buf.append("options: ");
                buf.append(this.options);
                buf.append(", ");
            }
        }
        synchronized (this.attrs) {
            if (!this.attrs.isEmpty()) {
                buf.append("attrs: ");
                buf.append(this.attrs);
                buf.append(", ");
            }
        }
        if (this.handler != null) {
            buf.append("handler: ");
            buf.append(this.handler);
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
    
    private static final class BootstrapChannelFactory<T extends Channel> implements ChannelFactory<T>
    {
        private final Class<? extends T> clazz;
        
        BootstrapChannelFactory(final Class<? extends T> clazz) {
            this.clazz = clazz;
        }
        
        @Override
        public T newChannel() {
            try {
                return (T)this.clazz.newInstance();
            }
            catch (Throwable t) {
                throw new ChannelException("Unable to create Channel from class " + this.clazz, t);
            }
        }
        
        @Override
        public String toString() {
            return this.clazz.getSimpleName() + ".class";
        }
    }
}
