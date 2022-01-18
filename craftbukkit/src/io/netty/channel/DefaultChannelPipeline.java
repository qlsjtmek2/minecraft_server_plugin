// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import io.netty.buffer.BufType;
import io.netty.buffer.ReferenceCounted;
import io.netty.buffer.Buf;
import io.netty.buffer.Unpooled;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.SocketAddress;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.MessageBuf;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import io.netty.util.internal.PlatformDependent;
import java.util.NoSuchElementException;
import java.util.concurrent.Future;
import java.util.IdentityHashMap;
import java.util.HashMap;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;
import java.util.Map;
import java.util.WeakHashMap;
import io.netty.util.internal.logging.InternalLogger;

final class DefaultChannelPipeline implements ChannelPipeline
{
    static final InternalLogger logger;
    private static final WeakHashMap<Class<?>, String>[] nameCaches;
    final Channel channel;
    final DefaultChannelHandlerContext head;
    final DefaultChannelHandlerContext tail;
    private final Map<String, DefaultChannelHandlerContext> name2ctx;
    private boolean firedChannelActive;
    private boolean fireInboundBufferUpdatedOnActivation;
    final Map<EventExecutorGroup, EventExecutor> childExecutors;
    private boolean inboundShutdown;
    private boolean outboundShutdown;
    
    public DefaultChannelPipeline(final Channel channel) {
        this.name2ctx = new HashMap<String, DefaultChannelHandlerContext>(4);
        this.childExecutors = new IdentityHashMap<EventExecutorGroup, EventExecutor>();
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        this.channel = channel;
        final TailHandler tailHandler = new TailHandler();
        this.tail = new DefaultChannelHandlerContext(this, this.generateName(tailHandler), tailHandler);
        HeadHandler headHandler = null;
        switch (channel.metadata().bufferType()) {
            case BYTE: {
                headHandler = new ByteHeadHandler(channel.unsafe());
                break;
            }
            case MESSAGE: {
                headHandler = new MessageHeadHandler(channel.unsafe());
                break;
            }
            default: {
                throw new Error("unknown buffer type: " + channel.metadata().bufferType());
            }
        }
        this.head = new DefaultChannelHandlerContext(this, this.generateName(headHandler), headHandler);
        this.head.next = this.tail;
        this.tail.prev = this.head;
    }
    
    @Override
    public Channel channel() {
        return this.channel;
    }
    
    @Override
    public ChannelPipeline addFirst(final String name, final ChannelHandler handler) {
        return this.addFirst(null, name, handler);
    }
    
    @Override
    public ChannelPipeline addFirst(final EventExecutorGroup group, final String name, final ChannelHandler handler) {
        final DefaultChannelHandlerContext newCtx;
        synchronized (this) {
            this.checkDuplicateName(name);
            newCtx = new DefaultChannelHandlerContext(this, group, name, handler);
            if (!newCtx.channel().isRegistered() || newCtx.executor().inEventLoop()) {
                this.addFirst0(name, newCtx);
                return this;
            }
        }
        executeOnEventLoop(newCtx, new Runnable() {
            @Override
            public void run() {
                synchronized (DefaultChannelPipeline.this) {
                    DefaultChannelPipeline.this.checkDuplicateName(name);
                    DefaultChannelPipeline.this.addFirst0(name, newCtx);
                }
            }
        });
        return this;
    }
    
    private void addFirst0(final String name, final DefaultChannelHandlerContext newCtx) {
        final DefaultChannelHandlerContext nextCtx = this.head.next;
        newCtx.prev = this.head;
        newCtx.next = nextCtx;
        callBeforeAdd(newCtx);
        this.head.next = newCtx;
        nextCtx.prev = newCtx;
        this.name2ctx.put(name, newCtx);
        this.callAfterAdd(newCtx);
    }
    
    @Override
    public ChannelPipeline addLast(final String name, final ChannelHandler handler) {
        return this.addLast(null, name, handler);
    }
    
    @Override
    public ChannelPipeline addLast(final EventExecutorGroup group, final String name, final ChannelHandler handler) {
        final DefaultChannelHandlerContext newCtx;
        synchronized (this) {
            this.checkDuplicateName(name);
            newCtx = new DefaultChannelHandlerContext(this, group, name, handler);
            if (!newCtx.channel().isRegistered() || newCtx.executor().inEventLoop()) {
                this.addLast0(name, newCtx);
                return this;
            }
        }
        executeOnEventLoop(newCtx, new Runnable() {
            @Override
            public void run() {
                synchronized (DefaultChannelPipeline.this) {
                    DefaultChannelPipeline.this.checkDuplicateName(name);
                    DefaultChannelPipeline.this.addLast0(name, newCtx);
                }
            }
        });
        return this;
    }
    
    private void addLast0(final String name, final DefaultChannelHandlerContext newCtx) {
        final DefaultChannelHandlerContext prev = this.tail.prev;
        newCtx.prev = prev;
        newCtx.next = this.tail;
        callBeforeAdd(newCtx);
        prev.next = newCtx;
        this.tail.prev = newCtx;
        this.name2ctx.put(name, newCtx);
        this.callAfterAdd(newCtx);
    }
    
    @Override
    public ChannelPipeline addBefore(final String baseName, final String name, final ChannelHandler handler) {
        return this.addBefore(null, baseName, name, handler);
    }
    
    @Override
    public ChannelPipeline addBefore(final EventExecutorGroup group, final String baseName, final String name, final ChannelHandler handler) {
        final DefaultChannelHandlerContext ctx;
        final DefaultChannelHandlerContext newCtx;
        synchronized (this) {
            ctx = this.getContextOrDie(baseName);
            this.checkDuplicateName(name);
            newCtx = new DefaultChannelHandlerContext(this, group, name, handler);
            if (!newCtx.channel().isRegistered() || newCtx.executor().inEventLoop()) {
                this.addBefore0(name, ctx, newCtx);
                return this;
            }
        }
        executeOnEventLoop(newCtx, new Runnable() {
            @Override
            public void run() {
                synchronized (DefaultChannelPipeline.this) {
                    DefaultChannelPipeline.this.checkDuplicateName(name);
                    DefaultChannelPipeline.this.addBefore0(name, ctx, newCtx);
                }
            }
        });
        return this;
    }
    
    private void addBefore0(final String name, final DefaultChannelHandlerContext ctx, final DefaultChannelHandlerContext newCtx) {
        newCtx.prev = ctx.prev;
        newCtx.next = ctx;
        callBeforeAdd(newCtx);
        ctx.prev.next = newCtx;
        ctx.prev = newCtx;
        this.name2ctx.put(name, newCtx);
        this.callAfterAdd(newCtx);
    }
    
    @Override
    public ChannelPipeline addAfter(final String baseName, final String name, final ChannelHandler handler) {
        return this.addAfter(null, baseName, name, handler);
    }
    
    @Override
    public ChannelPipeline addAfter(final EventExecutorGroup group, final String baseName, final String name, final ChannelHandler handler) {
        final DefaultChannelHandlerContext ctx;
        final DefaultChannelHandlerContext newCtx;
        synchronized (this) {
            ctx = this.getContextOrDie(baseName);
            this.checkDuplicateName(name);
            newCtx = new DefaultChannelHandlerContext(this, group, name, handler);
            if (!newCtx.channel().isRegistered() || newCtx.executor().inEventLoop()) {
                this.addAfter0(name, ctx, newCtx);
                return this;
            }
        }
        executeOnEventLoop(newCtx, new Runnable() {
            @Override
            public void run() {
                synchronized (DefaultChannelPipeline.this) {
                    DefaultChannelPipeline.this.checkDuplicateName(name);
                    DefaultChannelPipeline.this.addAfter0(name, ctx, newCtx);
                }
            }
        });
        return this;
    }
    
    private void addAfter0(final String name, final DefaultChannelHandlerContext ctx, final DefaultChannelHandlerContext newCtx) {
        this.checkDuplicateName(name);
        newCtx.prev = ctx;
        newCtx.next = ctx.next;
        callBeforeAdd(newCtx);
        ctx.next.prev = newCtx;
        ctx.next = newCtx;
        this.name2ctx.put(name, newCtx);
        this.callAfterAdd(newCtx);
    }
    
    @Override
    public ChannelPipeline addFirst(final ChannelHandler... handlers) {
        return this.addFirst((EventExecutorGroup)null, handlers);
    }
    
    @Override
    public ChannelPipeline addFirst(final EventExecutorGroup executor, final ChannelHandler... handlers) {
        if (handlers == null) {
            throw new NullPointerException("handlers");
        }
        if (handlers.length == 0 || handlers[0] == null) {
            return this;
        }
        int size;
        for (size = 1; size < handlers.length && handlers[size] != null; ++size) {}
        for (int i = size - 1; i >= 0; --i) {
            final ChannelHandler h = handlers[i];
            this.addFirst(executor, this.generateName(h), h);
        }
        return this;
    }
    
    @Override
    public ChannelPipeline addLast(final ChannelHandler... handlers) {
        return this.addLast((EventExecutorGroup)null, handlers);
    }
    
    @Override
    public ChannelPipeline addLast(final EventExecutorGroup executor, final ChannelHandler... handlers) {
        if (handlers == null) {
            throw new NullPointerException("handlers");
        }
        for (final ChannelHandler h : handlers) {
            if (h == null) {
                break;
            }
            this.addLast(executor, this.generateName(h), h);
        }
        return this;
    }
    
    private String generateName(final ChannelHandler handler) {
        final WeakHashMap<Class<?>, String> cache = DefaultChannelPipeline.nameCaches[(int)(Thread.currentThread().getId() % DefaultChannelPipeline.nameCaches.length)];
        final Class<?> handlerType = handler.getClass();
        String name;
        synchronized (cache) {
            name = cache.get(handlerType);
            if (name == null) {
                final Package pkg = handlerType.getPackage();
                if (pkg != null) {
                    name = handlerType.getName().substring(pkg.getName().length() + 1);
                }
                else {
                    name = handlerType.getName();
                }
                name += "#0";
                cache.put(handlerType, name);
            }
        }
        synchronized (this) {
            if (this.name2ctx.containsKey(name)) {
                final String baseName = name.substring(0, name.length() - 1);
                int i = 1;
                String newName;
                while (true) {
                    newName = baseName + i;
                    if (!this.name2ctx.containsKey(newName)) {
                        break;
                    }
                    ++i;
                }
                name = newName;
            }
        }
        return name;
    }
    
    @Override
    public ChannelPipeline remove(final ChannelHandler handler) {
        this.remove(this.getContextOrDie(handler), false);
        return this;
    }
    
    @Override
    public ChannelPipeline removeAndForward(final ChannelHandler handler) {
        this.remove(this.getContextOrDie(handler), true);
        return this;
    }
    
    @Override
    public ChannelHandler remove(final String name) {
        return this.remove(this.getContextOrDie(name), false).handler();
    }
    
    @Override
    public ChannelHandler removeAndForward(final String name) {
        return this.remove(this.getContextOrDie(name), true).handler();
    }
    
    @Override
    public <T extends ChannelHandler> T remove(final Class<T> handlerType) {
        return (T)this.remove(this.getContextOrDie(handlerType), false).handler();
    }
    
    private DefaultChannelHandlerContext remove(final DefaultChannelHandlerContext ctx, final boolean forward) {
        assert ctx != this.head && ctx != this.tail;
        final Future<?> future;
        synchronized (this) {
            if (!ctx.channel().isRegistered() || ctx.executor().inEventLoop()) {
                this.remove0(ctx, forward);
                return ctx;
            }
            future = ctx.executor().submit((Runnable)new Runnable() {
                @Override
                public void run() {
                    synchronized (DefaultChannelPipeline.this) {
                        DefaultChannelPipeline.this.remove0(ctx, forward);
                    }
                }
            });
        }
        waitForFuture(future);
        return ctx;
    }
    
    @Override
    public <T extends ChannelHandler> T removeAndForward(final Class<T> handlerType) {
        return (T)this.remove(this.getContextOrDie(handlerType), true).handler();
    }
    
    private void remove0(final DefaultChannelHandlerContext ctx, final boolean forward) {
        callBeforeRemove(ctx);
        final DefaultChannelHandlerContext prev = ctx.prev;
        final DefaultChannelHandlerContext next = ctx.next;
        prev.next = next;
        next.prev = prev;
        this.name2ctx.remove(ctx.name());
        callAfterRemove(ctx, prev, next, forward);
    }
    
    @Override
    public ChannelHandler removeFirst() {
        if (this.head.next == this.tail) {
            throw new NoSuchElementException();
        }
        return this.remove(this.head.next, false).handler();
    }
    
    @Override
    public ChannelHandler removeLast() {
        if (this.head.next == this.tail) {
            throw new NoSuchElementException();
        }
        return this.remove(this.tail.prev, false).handler();
    }
    
    @Override
    public ChannelPipeline replace(final ChannelHandler oldHandler, final String newName, final ChannelHandler newHandler) {
        this.replace(this.getContextOrDie(oldHandler), newName, newHandler, false);
        return this;
    }
    
    @Override
    public ChannelPipeline replaceAndForward(final ChannelHandler oldHandler, final String newName, final ChannelHandler newHandler) {
        this.replace(this.getContextOrDie(oldHandler), newName, newHandler, true);
        return this;
    }
    
    @Override
    public ChannelHandler replace(final String oldName, final String newName, final ChannelHandler newHandler) {
        return this.replace(this.getContextOrDie(oldName), newName, newHandler, false);
    }
    
    @Override
    public ChannelHandler replaceAndForward(final String oldName, final String newName, final ChannelHandler newHandler) {
        return this.replace(this.getContextOrDie(oldName), newName, newHandler, true);
    }
    
    @Override
    public <T extends ChannelHandler> T replace(final Class<T> oldHandlerType, final String newName, final ChannelHandler newHandler) {
        return (T)this.replace(this.getContextOrDie(oldHandlerType), newName, newHandler, false);
    }
    
    private ChannelHandler replace(final DefaultChannelHandlerContext ctx, final String newName, final ChannelHandler newHandler, final boolean forward) {
        assert ctx != this.head && ctx != this.tail;
        final Future<?> future;
        synchronized (this) {
            final boolean sameName = ctx.name().equals(newName);
            if (!sameName) {
                this.checkDuplicateName(newName);
            }
            final DefaultChannelHandlerContext newCtx = new DefaultChannelHandlerContext(this, ctx.executor, newName, newHandler);
            if (!newCtx.channel().isRegistered() || newCtx.executor().inEventLoop()) {
                this.replace0(ctx, newName, newCtx, forward);
                return ctx.handler();
            }
            future = newCtx.executor().submit((Runnable)new Runnable() {
                @Override
                public void run() {
                    synchronized (DefaultChannelPipeline.this) {
                        DefaultChannelPipeline.this.replace0(ctx, newName, newCtx, forward);
                    }
                }
            });
        }
        waitForFuture(future);
        return ctx.handler();
    }
    
    @Override
    public <T extends ChannelHandler> T replaceAndForward(final Class<T> oldHandlerType, final String newName, final ChannelHandler newHandler) {
        return (T)this.replace(this.getContextOrDie(oldHandlerType), newName, newHandler, true);
    }
    
    private void replace0(final DefaultChannelHandlerContext ctx, final String newName, final DefaultChannelHandlerContext newCtx, final boolean forward) {
        final boolean sameName = ctx.name().equals(newName);
        final DefaultChannelHandlerContext prev = ctx.prev;
        final DefaultChannelHandlerContext next = ctx.next;
        newCtx.prev = prev;
        newCtx.next = next;
        callBeforeRemove(ctx);
        callBeforeAdd(newCtx);
        prev.next = newCtx;
        next.prev = newCtx;
        if (!sameName) {
            this.name2ctx.remove(ctx.name());
        }
        this.name2ctx.put(newName, newCtx);
        ChannelPipelineException removeException = null;
        ChannelPipelineException addException = null;
        boolean removed = false;
        try {
            callAfterRemove(ctx, newCtx, newCtx, forward);
            removed = true;
        }
        catch (ChannelPipelineException e) {
            removeException = e;
        }
        boolean added = false;
        try {
            this.callAfterAdd(newCtx);
            added = true;
        }
        catch (ChannelPipelineException e2) {
            addException = e2;
        }
        if (!removed && !added) {
            DefaultChannelPipeline.logger.warn(removeException.getMessage(), removeException);
            DefaultChannelPipeline.logger.warn(addException.getMessage(), addException);
            throw new ChannelPipelineException("Both " + ctx.handler().getClass().getName() + ".afterRemove() and " + newCtx.handler().getClass().getName() + ".afterAdd() failed; see logs.");
        }
        if (!removed) {
            throw removeException;
        }
        if (!added) {
            throw addException;
        }
    }
    
    private static void callBeforeAdd(final ChannelHandlerContext ctx) {
        final ChannelHandler handler = ctx.handler();
        if (handler instanceof ChannelHandlerAdapter) {
            final ChannelHandlerAdapter h = (ChannelHandlerAdapter)handler;
            if (!h.isSharable() && h.added) {
                throw new ChannelPipelineException(h.getClass().getName() + " is not a @Sharable handler, so can't be added or removed multiple times.");
            }
            h.added = true;
        }
        try {
            handler.beforeAdd(ctx);
        }
        catch (Throwable t) {
            throw new ChannelPipelineException(handler.getClass().getName() + ".beforeAdd() has thrown an exception; not adding.", t);
        }
    }
    
    private void callAfterAdd(final ChannelHandlerContext ctx) {
        try {
            ctx.handler().afterAdd(ctx);
        }
        catch (Throwable t3) {
            boolean removed = false;
            try {
                this.remove((DefaultChannelHandlerContext)ctx, false);
                removed = true;
            }
            catch (Throwable t2) {
                if (DefaultChannelPipeline.logger.isWarnEnabled()) {
                    DefaultChannelPipeline.logger.warn("Failed to remove a handler: " + ctx.name(), t2);
                }
            }
            if (removed) {
                throw new ChannelPipelineException(ctx.handler().getClass().getName() + ".afterAdd() has thrown an exception; removed.", t3);
            }
            throw new ChannelPipelineException(ctx.handler().getClass().getName() + ".afterAdd() has thrown an exception; also failed to remove.", t3);
        }
    }
    
    private static void callBeforeRemove(final ChannelHandlerContext ctx) {
        try {
            ctx.handler().beforeRemove(ctx);
        }
        catch (Throwable t) {
            throw new ChannelPipelineException(ctx.handler().getClass().getName() + ".beforeRemove() has thrown an exception; not removing.", t);
        }
    }
    
    private static void callAfterRemove(final DefaultChannelHandlerContext ctx, final DefaultChannelHandlerContext ctxPrev, final DefaultChannelHandlerContext ctxNext, final boolean forward) {
        final ChannelHandler handler = ctx.handler();
        try {
            handler.afterRemove(ctx);
        }
        catch (Throwable t) {
            throw new ChannelPipelineException(ctx.handler().getClass().getName() + ".afterRemove() has thrown an exception.", t);
        }
        if (forward) {
            ctx.forwardBufferContent(ctxPrev, ctxNext);
        }
        else {
            ctx.clearBuffer();
        }
        ctx.setRemoved();
    }
    
    private static void executeOnEventLoop(final DefaultChannelHandlerContext ctx, final Runnable r) {
        waitForFuture(ctx.executor().submit(r));
    }
    
    private static void waitForFuture(final Future<?> future) {
        try {
            future.get();
        }
        catch (ExecutionException ex) {
            PlatformDependent.throwException(ex.getCause());
        }
        catch (InterruptedException ex2) {
            Thread.currentThread().interrupt();
        }
    }
    
    @Override
    public ChannelHandler first() {
        final DefaultChannelHandlerContext first = this.head.next;
        if (first == null) {
            return null;
        }
        return first.handler();
    }
    
    @Override
    public ChannelHandlerContext firstContext() {
        return this.head.next;
    }
    
    @Override
    public ChannelHandler last() {
        final DefaultChannelHandlerContext last = this.tail.prev;
        if (last == this.head) {
            return null;
        }
        return last.handler();
    }
    
    @Override
    public ChannelHandlerContext lastContext() {
        final DefaultChannelHandlerContext last = this.tail.prev;
        if (last == this.head) {
            return null;
        }
        return last;
    }
    
    @Override
    public ChannelHandler get(final String name) {
        final ChannelHandlerContext ctx = this.context(name);
        if (ctx == null) {
            return null;
        }
        return ctx.handler();
    }
    
    @Override
    public <T extends ChannelHandler> T get(final Class<T> handlerType) {
        final ChannelHandlerContext ctx = this.context(handlerType);
        if (ctx == null) {
            return null;
        }
        return (T)ctx.handler();
    }
    
    @Override
    public ChannelHandlerContext context(final String name) {
        if (name == null) {
            throw new NullPointerException("name");
        }
        synchronized (this) {
            return this.name2ctx.get(name);
        }
    }
    
    @Override
    public ChannelHandlerContext context(final ChannelHandler handler) {
        if (handler == null) {
            throw new NullPointerException("handler");
        }
        for (DefaultChannelHandlerContext ctx = this.head.next; ctx != null; ctx = ctx.next) {
            if (ctx.handler() == handler) {
                return ctx;
            }
        }
        return null;
    }
    
    @Override
    public ChannelHandlerContext context(final Class<? extends ChannelHandler> handlerType) {
        if (handlerType == null) {
            throw new NullPointerException("handlerType");
        }
        for (DefaultChannelHandlerContext ctx = this.head.next; ctx != null; ctx = ctx.next) {
            if (handlerType.isAssignableFrom(ctx.handler().getClass())) {
                return ctx;
            }
        }
        return null;
    }
    
    @Override
    public List<String> names() {
        final List<String> list = new ArrayList<String>();
        for (DefaultChannelHandlerContext ctx = this.head.next; ctx != null; ctx = ctx.next) {
            list.add(ctx.name());
        }
        return list;
    }
    
    @Override
    public Map<String, ChannelHandler> toMap() {
        final Map<String, ChannelHandler> map = new LinkedHashMap<String, ChannelHandler>();
        for (DefaultChannelHandlerContext ctx = this.head.next; ctx != this.tail; ctx = ctx.next) {
            map.put(ctx.name(), ctx.handler());
        }
        return map;
    }
    
    @Override
    public Iterator<Map.Entry<String, ChannelHandler>> iterator() {
        return this.toMap().entrySet().iterator();
    }
    
    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        buf.append(this.getClass().getSimpleName());
        buf.append('{');
        DefaultChannelHandlerContext ctx = this.head.next;
        while (true) {
            while (ctx != this.tail) {
                buf.append('(');
                buf.append(ctx.name());
                buf.append(" = ");
                buf.append(ctx.handler().getClass().getName());
                buf.append(')');
                ctx = ctx.next;
                if (ctx == this.tail) {
                    buf.append('}');
                    return buf.toString();
                }
                buf.append(", ");
            }
            continue;
        }
    }
    
    @Override
    public <T> MessageBuf<T> inboundMessageBuffer() {
        return (MessageBuf<T>)this.head.nextInboundMessageBuffer();
    }
    
    @Override
    public ByteBuf inboundByteBuffer() {
        return this.head.nextInboundByteBuffer();
    }
    
    @Override
    public <T> MessageBuf<T> outboundMessageBuffer() {
        return (MessageBuf<T>)this.tail.nextOutboundMessageBuffer();
    }
    
    @Override
    public ByteBuf outboundByteBuffer() {
        return this.tail.nextOutboundByteBuffer();
    }
    
    boolean isInboundShutdown() {
        return this.inboundShutdown;
    }
    
    boolean isOutboundShutdown() {
        return this.outboundShutdown;
    }
    
    void shutdownInbound() {
        this.inboundShutdown = true;
    }
    
    void shutdownOutbound() {
        this.outboundShutdown = true;
    }
    
    @Override
    public ChannelPipeline fireChannelRegistered() {
        this.head.initHeadHandler();
        this.head.fireChannelRegistered();
        return this;
    }
    
    @Override
    public ChannelPipeline fireChannelUnregistered() {
        this.head.fireChannelUnregistered();
        if (!this.channel.isOpen()) {
            this.head.invokeFreeInboundBuffer();
        }
        return this;
    }
    
    @Override
    public ChannelPipeline fireChannelActive() {
        this.firedChannelActive = true;
        this.head.initHeadHandler();
        this.head.fireChannelActive();
        if (this.channel.config().isAutoRead()) {
            this.channel.read();
        }
        if (this.fireInboundBufferUpdatedOnActivation) {
            this.fireInboundBufferUpdatedOnActivation = false;
            this.head.fireInboundBufferUpdated();
        }
        return this;
    }
    
    @Override
    public ChannelPipeline fireChannelInactive() {
        this.head.fireChannelInactive();
        return this;
    }
    
    @Override
    public ChannelPipeline fireExceptionCaught(final Throwable cause) {
        this.head.fireExceptionCaught(cause);
        return this;
    }
    
    @Override
    public ChannelPipeline fireUserEventTriggered(final Object event) {
        this.head.fireUserEventTriggered(event);
        return this;
    }
    
    @Override
    public ChannelPipeline fireInboundBufferUpdated() {
        if (!this.firedChannelActive) {
            this.fireInboundBufferUpdatedOnActivation = true;
            return this;
        }
        this.head.fireInboundBufferUpdated();
        return this;
    }
    
    @Override
    public ChannelPipeline fireChannelReadSuspended() {
        this.head.fireChannelReadSuspended();
        if (this.channel.config().isAutoRead()) {
            this.read();
        }
        return this;
    }
    
    @Override
    public ChannelFuture bind(final SocketAddress localAddress) {
        return this.tail.bind(localAddress);
    }
    
    @Override
    public ChannelFuture connect(final SocketAddress remoteAddress) {
        return this.tail.connect(remoteAddress);
    }
    
    @Override
    public ChannelFuture connect(final SocketAddress remoteAddress, final SocketAddress localAddress) {
        return this.tail.connect(remoteAddress, localAddress);
    }
    
    @Override
    public ChannelFuture disconnect() {
        return this.tail.disconnect();
    }
    
    @Override
    public ChannelFuture close() {
        return this.tail.close();
    }
    
    @Override
    public ChannelFuture deregister() {
        return this.tail.deregister();
    }
    
    @Override
    public ChannelFuture flush() {
        return this.tail.flush();
    }
    
    @Override
    public ChannelFuture write(final Object message) {
        return this.tail.write(message);
    }
    
    @Override
    public ChannelFuture bind(final SocketAddress localAddress, final ChannelPromise promise) {
        return this.tail.bind(localAddress, promise);
    }
    
    @Override
    public ChannelFuture connect(final SocketAddress remoteAddress, final ChannelPromise promise) {
        return this.tail.connect(remoteAddress, promise);
    }
    
    @Override
    public ChannelFuture connect(final SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise promise) {
        return this.tail.connect(remoteAddress, localAddress, promise);
    }
    
    @Override
    public ChannelFuture disconnect(final ChannelPromise promise) {
        return this.tail.disconnect(promise);
    }
    
    @Override
    public ChannelFuture close(final ChannelPromise promise) {
        return this.tail.close(promise);
    }
    
    @Override
    public ChannelFuture deregister(final ChannelPromise promise) {
        return this.tail.deregister(promise);
    }
    
    @Override
    public void read() {
        this.tail.read();
    }
    
    @Override
    public ChannelFuture flush(final ChannelPromise promise) {
        return this.tail.flush(promise);
    }
    
    @Override
    public ChannelFuture sendFile(final FileRegion region) {
        return this.tail.sendFile(region);
    }
    
    @Override
    public ChannelFuture sendFile(final FileRegion region, final ChannelPromise promise) {
        return this.tail.sendFile(region, promise);
    }
    
    @Override
    public ChannelFuture write(final Object message, final ChannelPromise promise) {
        return this.tail.write(message, promise);
    }
    
    private void checkDuplicateName(final String name) {
        if (this.name2ctx.containsKey(name)) {
            throw new IllegalArgumentException("Duplicate handler name: " + name);
        }
    }
    
    private DefaultChannelHandlerContext getContextOrDie(final String name) {
        final DefaultChannelHandlerContext ctx = (DefaultChannelHandlerContext)this.context(name);
        if (ctx == null) {
            throw new NoSuchElementException(name);
        }
        return ctx;
    }
    
    private DefaultChannelHandlerContext getContextOrDie(final ChannelHandler handler) {
        final DefaultChannelHandlerContext ctx = (DefaultChannelHandlerContext)this.context(handler);
        if (ctx == null) {
            throw new NoSuchElementException(handler.getClass().getName());
        }
        return ctx;
    }
    
    private DefaultChannelHandlerContext getContextOrDie(final Class<? extends ChannelHandler> handlerType) {
        final DefaultChannelHandlerContext ctx = (DefaultChannelHandlerContext)this.context(handlerType);
        if (ctx == null) {
            throw new NoSuchElementException(handlerType.getName());
        }
        return ctx;
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(DefaultChannelPipeline.class);
        nameCaches = new WeakHashMap[Runtime.getRuntime().availableProcessors()];
        for (int i = 0; i < DefaultChannelPipeline.nameCaches.length; ++i) {
            DefaultChannelPipeline.nameCaches[i] = new WeakHashMap<Class<?>, String>();
        }
    }
    
    static final class TailHandler implements ChannelInboundHandler
    {
        final ByteBuf byteSink;
        final MessageBuf<Object> msgSink;
        
        TailHandler() {
            this.byteSink = Unpooled.buffer(0);
            this.msgSink = Unpooled.messageBuffer(0);
        }
        
        @Override
        public void channelRegistered(final ChannelHandlerContext ctx) throws Exception {
        }
        
        @Override
        public void channelUnregistered(final ChannelHandlerContext ctx) throws Exception {
        }
        
        @Override
        public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        }
        
        @Override
        public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
        }
        
        @Override
        public void channelReadSuspended(final ChannelHandlerContext ctx) throws Exception {
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
        public void userEventTriggered(final ChannelHandlerContext ctx, final Object evt) throws Exception {
        }
        
        @Override
        public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
            DefaultChannelPipeline.logger.warn("An exceptionCaught() event was fired, and it reached at the tail of the pipeline. It usually means the last handler in the pipeline did not handle the exception.", cause);
        }
        
        @Override
        public Buf newInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
            throw new Error();
        }
        
        @Override
        public void freeInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
            this.byteSink.release();
            this.msgSink.release();
        }
        
        @Override
        public void inboundBufferUpdated(final ChannelHandlerContext ctx) throws Exception {
            final int byteSinkSize = this.byteSink.readableBytes();
            if (byteSinkSize != 0) {
                this.byteSink.clear();
                DefaultChannelPipeline.logger.warn("Discarded {} inbound byte(s) that reached at the tail of the pipeline. Please check your pipeline configuration.", (Object)byteSinkSize);
            }
            final int msgSinkSize = this.msgSink.size();
            if (msgSinkSize != 0) {
                final MessageBuf<Object> in = this.msgSink;
                while (true) {
                    final Object m = in.poll();
                    if (m == null) {
                        break;
                    }
                    if (m instanceof ReferenceCounted) {
                        ((ReferenceCounted)m).release();
                    }
                    DefaultChannelPipeline.logger.debug("Discarded inbound message {} that reached at the tail of the pipeline. Please check your pipeline configuration.", m);
                }
                DefaultChannelPipeline.logger.warn("Discarded {} inbound message(s) that reached at the tail of the pipeline. Please check your pipeline configuration.", (Object)msgSinkSize);
            }
        }
    }
    
    abstract static class HeadHandler implements ChannelOutboundHandler
    {
        protected final Channel.Unsafe unsafe;
        ByteBuf byteSink;
        MessageBuf<Object> msgSink;
        boolean initialized;
        
        protected HeadHandler(final Channel.Unsafe unsafe) {
            this.unsafe = unsafe;
        }
        
        void init(final ChannelHandlerContext ctx) {
            assert !this.initialized;
            switch (ctx.channel().metadata().bufferType()) {
                case BYTE: {
                    this.byteSink = ctx.alloc().ioBuffer();
                    this.msgSink = Unpooled.messageBuffer(0);
                    break;
                }
                case MESSAGE: {
                    this.byteSink = Unpooled.buffer(0);
                    this.msgSink = Unpooled.messageBuffer();
                    break;
                }
                default: {
                    throw new Error();
                }
            }
        }
        
        @Override
        public final void beforeAdd(final ChannelHandlerContext ctx) throws Exception {
        }
        
        @Override
        public final void afterAdd(final ChannelHandlerContext ctx) throws Exception {
        }
        
        @Override
        public final void beforeRemove(final ChannelHandlerContext ctx) throws Exception {
        }
        
        @Override
        public final void afterRemove(final ChannelHandlerContext ctx) throws Exception {
        }
        
        @Override
        public final void bind(final ChannelHandlerContext ctx, final SocketAddress localAddress, final ChannelPromise promise) throws Exception {
            this.unsafe.bind(localAddress, promise);
        }
        
        @Override
        public final void connect(final ChannelHandlerContext ctx, final SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise promise) throws Exception {
            this.unsafe.connect(remoteAddress, localAddress, promise);
        }
        
        @Override
        public final void disconnect(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
            this.unsafe.disconnect(promise);
        }
        
        @Override
        public final void close(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
            this.unsafe.close(promise);
        }
        
        @Override
        public final void deregister(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
            this.unsafe.deregister(promise);
        }
        
        @Override
        public final void read(final ChannelHandlerContext ctx) {
            this.unsafe.beginRead();
        }
        
        @Override
        public final void sendFile(final ChannelHandlerContext ctx, final FileRegion region, final ChannelPromise promise) throws Exception {
            this.unsafe.sendFile(region, promise);
        }
        
        @Override
        public final Buf newOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
            throw new Error();
        }
        
        @Override
        public final void freeOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
            this.msgSink.release();
            this.byteSink.release();
        }
        
        @Override
        public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
            ctx.fireExceptionCaught(cause);
        }
    }
    
    private static final class ByteHeadHandler extends HeadHandler
    {
        private ByteHeadHandler(final Channel.Unsafe unsafe) {
            super(unsafe);
        }
        
        @Override
        public void flush(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
            int discardedMessages = 0;
            final MessageBuf<Object> in = this.msgSink;
            while (true) {
                final Object m = in.poll();
                if (m == null) {
                    break;
                }
                if (m instanceof ByteBuf) {
                    final ByteBuf src = (ByteBuf)m;
                    this.byteSink.writeBytes(src, src.readerIndex(), src.readableBytes());
                }
                else {
                    DefaultChannelPipeline.logger.debug("Discarded outbound message {} that reached at the head of the pipeline. Please check your pipeline configuration.", m);
                    ++discardedMessages;
                }
                if (!(m instanceof ReferenceCounted)) {
                    continue;
                }
                ((ReferenceCounted)m).release();
            }
            if (discardedMessages != 0) {
                DefaultChannelPipeline.logger.warn("Discarded {} outbound message(s) that reached at the head of the pipeline. Please check your pipeline configuration.", (Object)discardedMessages);
            }
            this.unsafe.flush(promise);
        }
    }
    
    private static final class MessageHeadHandler extends HeadHandler
    {
        private MessageHeadHandler(final Channel.Unsafe unsafe) {
            super(unsafe);
        }
        
        @Override
        public void flush(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
            final int byteSinkSize = this.byteSink.readableBytes();
            if (byteSinkSize != 0) {
                this.byteSink.clear();
                DefaultChannelPipeline.logger.warn("Discarded {} outbound byte(s) that reached at the head of the pipeline. Please check your pipeline configuration.", (Object)byteSinkSize);
            }
            this.unsafe.flush(promise);
        }
    }
}
