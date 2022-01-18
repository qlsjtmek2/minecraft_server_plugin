// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.group;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.AbstractEventExecutor;
import io.netty.channel.FileRegion;
import io.netty.buffer.BufUtil;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.channel.ServerChannel;
import io.netty.util.concurrent.Future;
import io.netty.channel.ChannelFuture;
import io.netty.util.internal.PlatformDependent;
import io.netty.channel.ChannelFutureListener;
import java.util.concurrent.ConcurrentMap;
import io.netty.util.concurrent.EventExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import io.netty.channel.Channel;
import java.util.AbstractSet;

public class DefaultChannelGroup extends AbstractSet<Channel> implements ChannelGroup
{
    private static final AtomicInteger nextId;
    private static final ImmediateEventExecutor DEFAULT_EXECUTOR;
    private final String name;
    private final EventExecutor executor;
    private final ConcurrentMap<Integer, Channel> serverChannels;
    private final ConcurrentMap<Integer, Channel> nonServerChannels;
    private final ChannelFutureListener remover;
    
    public DefaultChannelGroup() {
        this("group-0x" + Integer.toHexString(DefaultChannelGroup.nextId.incrementAndGet()));
    }
    
    public DefaultChannelGroup(final EventExecutor executor) {
        this("group-0x" + Integer.toHexString(DefaultChannelGroup.nextId.incrementAndGet()), executor);
    }
    
    public DefaultChannelGroup(final String name) {
        this(name, DefaultChannelGroup.DEFAULT_EXECUTOR);
    }
    
    public DefaultChannelGroup(final String name, final EventExecutor executor) {
        this.serverChannels = PlatformDependent.newConcurrentHashMap();
        this.nonServerChannels = PlatformDependent.newConcurrentHashMap();
        this.remover = new ChannelFutureListener() {
            @Override
            public void operationComplete(final ChannelFuture future) throws Exception {
                DefaultChannelGroup.this.remove(future.channel());
            }
        };
        if (name == null) {
            throw new NullPointerException("name");
        }
        this.name = name;
        this.executor = executor;
    }
    
    @Override
    public String name() {
        return this.name;
    }
    
    @Override
    public boolean isEmpty() {
        return this.nonServerChannels.isEmpty() && this.serverChannels.isEmpty();
    }
    
    @Override
    public int size() {
        return this.nonServerChannels.size() + this.serverChannels.size();
    }
    
    @Override
    public Channel find(final Integer id) {
        final Channel c = this.nonServerChannels.get(id);
        if (c != null) {
            return c;
        }
        return this.serverChannels.get(id);
    }
    
    @Override
    public boolean contains(final Object o) {
        if (o instanceof Integer) {
            return this.nonServerChannels.containsKey(o) || this.serverChannels.containsKey(o);
        }
        if (!(o instanceof Channel)) {
            return false;
        }
        final Channel c = (Channel)o;
        if (o instanceof ServerChannel) {
            return this.serverChannels.containsKey(c.id());
        }
        return this.nonServerChannels.containsKey(c.id());
    }
    
    @Override
    public boolean add(final Channel channel) {
        final ConcurrentMap<Integer, Channel> map = (channel instanceof ServerChannel) ? this.serverChannels : this.nonServerChannels;
        final boolean added = map.putIfAbsent(channel.id(), channel) == null;
        if (added) {
            channel.closeFuture().addListener((GenericFutureListener<? extends Future<Void>>)this.remover);
        }
        return added;
    }
    
    @Override
    public boolean remove(final Object o) {
        Channel c = null;
        if (o instanceof Integer) {
            c = this.nonServerChannels.remove(o);
            if (c == null) {
                c = this.serverChannels.remove(o);
            }
        }
        else if (o instanceof Channel) {
            c = (Channel)o;
            if (c instanceof ServerChannel) {
                c = this.serverChannels.remove(c.id());
            }
            else {
                c = this.nonServerChannels.remove(c.id());
            }
        }
        if (c == null) {
            return false;
        }
        c.closeFuture().removeListener((GenericFutureListener<? extends Future<Void>>)this.remover);
        return true;
    }
    
    @Override
    public void clear() {
        this.nonServerChannels.clear();
        this.serverChannels.clear();
    }
    
    @Override
    public Iterator<Channel> iterator() {
        return new CombinedIterator<Channel>(this.serverChannels.values().iterator(), this.nonServerChannels.values().iterator());
    }
    
    @Override
    public Object[] toArray() {
        final Collection<Channel> channels = new ArrayList<Channel>(this.size());
        channels.addAll(this.serverChannels.values());
        channels.addAll(this.nonServerChannels.values());
        return channels.toArray();
    }
    
    @Override
    public <T> T[] toArray(final T[] a) {
        final Collection<Channel> channels = new ArrayList<Channel>(this.size());
        channels.addAll(this.serverChannels.values());
        channels.addAll(this.nonServerChannels.values());
        return channels.toArray(a);
    }
    
    @Override
    public ChannelGroupFuture close() {
        final Map<Integer, ChannelFuture> futures = new LinkedHashMap<Integer, ChannelFuture>(this.size());
        for (final Channel c : this.serverChannels.values()) {
            futures.put(c.id(), c.close().awaitUninterruptibly());
        }
        for (final Channel c : this.nonServerChannels.values()) {
            futures.put(c.id(), c.close());
        }
        return new DefaultChannelGroupFuture(this, futures, this.executor);
    }
    
    @Override
    public ChannelGroupFuture disconnect() {
        final Map<Integer, ChannelFuture> futures = new LinkedHashMap<Integer, ChannelFuture>(this.size());
        for (final Channel c : this.serverChannels.values()) {
            futures.put(c.id(), c.disconnect());
        }
        for (final Channel c : this.nonServerChannels.values()) {
            futures.put(c.id(), c.disconnect());
        }
        return new DefaultChannelGroupFuture(this, futures, this.executor);
    }
    
    @Override
    public ChannelGroupFuture write(final Object message) {
        if (message == null) {
            throw new NullPointerException("message");
        }
        final Map<Integer, ChannelFuture> futures = new LinkedHashMap<Integer, ChannelFuture>(this.size());
        for (final Channel c : this.nonServerChannels.values()) {
            BufUtil.retain(message);
            futures.put(c.id(), c.write(message));
        }
        BufUtil.release(message);
        return new DefaultChannelGroupFuture(this, futures, this.executor);
    }
    
    @Override
    public ChannelGroupFuture sendFile(final FileRegion region) {
        if (region == null) {
            throw new NullPointerException("region");
        }
        final Map<Integer, ChannelFuture> futures = new LinkedHashMap<Integer, ChannelFuture>(this.size());
        for (final Channel c : this.nonServerChannels.values()) {
            BufUtil.retain(region);
            futures.put(c.id(), c.sendFile(region));
        }
        BufUtil.release(region);
        return new DefaultChannelGroupFuture(this, futures, this.executor);
    }
    
    @Override
    public ChannelGroupFuture flush() {
        final Map<Integer, ChannelFuture> futures = new LinkedHashMap<Integer, ChannelFuture>(this.size());
        for (final Channel c : this.nonServerChannels.values()) {
            futures.put(c.id(), c.flush());
        }
        return new DefaultChannelGroupFuture(this, futures, this.executor);
    }
    
    @Override
    public ChannelGroupFuture deregister() {
        final Map<Integer, ChannelFuture> futures = new LinkedHashMap<Integer, ChannelFuture>(this.size());
        for (final Channel c : this.serverChannels.values()) {
            futures.put(c.id(), c.deregister());
        }
        for (final Channel c : this.nonServerChannels.values()) {
            futures.put(c.id(), c.deregister());
        }
        return new DefaultChannelGroupFuture(this, futures, this.executor);
    }
    
    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o;
    }
    
    @Override
    public int compareTo(final ChannelGroup o) {
        final int v = this.name().compareTo(o.name());
        if (v != 0) {
            return v;
        }
        return System.identityHashCode(this) - System.identityHashCode(o);
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(name: " + this.name() + ", size: " + this.size() + ')';
    }
    
    static {
        nextId = new AtomicInteger();
        DEFAULT_EXECUTOR = new ImmediateEventExecutor();
    }
    
    static final class ImmediateEventExecutor extends AbstractEventExecutor
    {
        @Override
        public EventExecutorGroup parent() {
            return null;
        }
        
        @Override
        public boolean inEventLoop() {
            return true;
        }
        
        @Override
        public boolean inEventLoop(final Thread thread) {
            return true;
        }
        
        @Override
        public void shutdown() {
        }
        
        @Override
        public boolean isShutdown() {
            return false;
        }
        
        @Override
        public boolean isTerminated() {
            return false;
        }
        
        @Override
        public boolean awaitTermination(final long timeout, final TimeUnit unit) {
            return false;
        }
        
        @Override
        public List<Runnable> shutdownNow() {
            return Collections.emptyList();
        }
        
        @Override
        public void execute(final Runnable command) {
            if (command == null) {
                throw new NullPointerException("command");
            }
            command.run();
        }
    }
}
