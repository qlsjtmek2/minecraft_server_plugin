// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.nio;

import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.Collection;
import java.util.ArrayList;
import java.nio.channels.CancelledKeyException;
import java.util.Set;
import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.nio.channels.SelectionKey;
import io.netty.channel.EventLoopException;
import java.nio.channels.SelectableChannel;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Queue;
import java.io.IOException;
import io.netty.channel.ChannelException;
import io.netty.channel.EventLoopGroup;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.nio.channels.spi.SelectorProvider;
import java.nio.channels.Selector;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.channel.SingleThreadEventLoop;

public final class NioEventLoop extends SingleThreadEventLoop
{
    private static final InternalLogger logger;
    private static final int CLEANUP_INTERVAL = 256;
    private static final int MIN_PREMATURE_SELECTOR_RETURNS = 3;
    private static final int SELECTOR_AUTO_REBUILD_THRESHOLD;
    Selector selector;
    private final SelectorProvider provider;
    private final AtomicBoolean wakenUp;
    private boolean oldWakenUp;
    private volatile int ioRatio;
    private int cancelledKeys;
    private boolean needsToSelectAgain;
    
    NioEventLoop(final NioEventLoopGroup parent, final ThreadFactory threadFactory, final SelectorProvider selectorProvider) {
        super(parent, threadFactory);
        this.wakenUp = new AtomicBoolean();
        this.ioRatio = 50;
        if (selectorProvider == null) {
            throw new NullPointerException("selectorProvider");
        }
        this.provider = selectorProvider;
        this.selector = this.openSelector();
    }
    
    private Selector openSelector() {
        try {
            return this.provider.openSelector();
        }
        catch (IOException e) {
            throw new ChannelException("failed to open a new selector", e);
        }
    }
    
    @Override
    protected Queue<Runnable> newTaskQueue() {
        return new ConcurrentLinkedQueue<Runnable>();
    }
    
    public void register(final SelectableChannel ch, final int interestOps, final NioTask<?> task) {
        if (ch == null) {
            throw new NullPointerException("ch");
        }
        if (interestOps == 0) {
            throw new IllegalArgumentException("interestOps must be non-zero.");
        }
        if ((interestOps & ~ch.validOps()) != 0x0) {
            throw new IllegalArgumentException("invalid interestOps: " + interestOps + "(validOps: " + ch.validOps() + ')');
        }
        if (task == null) {
            throw new NullPointerException("task");
        }
        if (this.isShutdown()) {
            throw new IllegalStateException("event loop shut down");
        }
        try {
            ch.register(this.selector, interestOps, task);
        }
        catch (Exception e) {
            throw new EventLoopException("failed to register a channel", e);
        }
    }
    
    void executeWhenWritable(final AbstractNioChannel channel, final NioTask<SelectableChannel> task) {
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        if (this.isShutdown()) {
            throw new IllegalStateException("event loop shut down");
        }
        final SelectionKey key = channel.selectionKey();
        channel.writableTasks.offer(task);
        final int interestOps = key.interestOps();
        if ((interestOps & 0x4) == 0x0) {
            key.interestOps(interestOps | 0x4);
        }
    }
    
    public int getIoRatio() {
        return this.ioRatio;
    }
    
    public void setIoRatio(final int ioRatio) {
        if (ioRatio <= 0 || ioRatio >= 100) {
            throw new IllegalArgumentException("ioRatio: " + ioRatio + " (expected: 0 < ioRatio < 100)");
        }
        this.ioRatio = ioRatio;
    }
    
    public void rebuildSelector() {
        if (!this.inEventLoop()) {
            this.execute(new Runnable() {
                @Override
                public void run() {
                    NioEventLoop.this.rebuildSelector();
                }
            });
            return;
        }
        final Selector oldSelector = this.selector;
        if (oldSelector == null) {
            return;
        }
        Selector newSelector;
        try {
            newSelector = this.openSelector();
        }
        catch (Exception e) {
            NioEventLoop.logger.warn("Failed to create a new Selector.", e);
            return;
        }
        int nChannels = 0;
        while (true) {
            try {
                for (final SelectionKey key : oldSelector.keys()) {
                    final Object a = key.attachment();
                    try {
                        if (key.channel().keyFor(newSelector) != null) {
                            continue;
                        }
                        final int interestOps = key.interestOps();
                        key.cancel();
                        key.channel().register(newSelector, interestOps, a);
                        ++nChannels;
                    }
                    catch (Exception e2) {
                        NioEventLoop.logger.warn("Failed to re-register a Channel to the new Selector.", e2);
                        if (a instanceof AbstractNioChannel) {
                            final AbstractNioChannel ch = (AbstractNioChannel)a;
                            ch.unsafe().close(ch.unsafe().voidFuture());
                        }
                        else {
                            final NioTask<SelectableChannel> task = (NioTask<SelectableChannel>)a;
                            invokeChannelUnregistered(task, key, e2);
                        }
                    }
                }
            }
            catch (ConcurrentModificationException e3) {
                continue;
            }
            break;
        }
        this.selector = newSelector;
        try {
            oldSelector.close();
        }
        catch (Throwable t) {
            if (NioEventLoop.logger.isWarnEnabled()) {
                NioEventLoop.logger.warn("Failed to close the old Selector.", t);
            }
        }
        NioEventLoop.logger.info("Migrated " + nChannels + " channel(s) to the new Selector.");
    }
    
    @Override
    protected void run() {
        while (true) {
            this.oldWakenUp = this.wakenUp.getAndSet(false);
            try {
                if (this.hasTasks()) {
                    this.selectNow();
                }
                else {
                    this.select();
                    if (this.wakenUp.get()) {
                        this.selector.wakeup();
                    }
                }
                this.cancelledKeys = 0;
                final long ioStartTime = System.nanoTime();
                this.processSelectedKeys();
                final long ioTime = System.nanoTime() - ioStartTime;
                final int ioRatio = this.ioRatio;
                this.runAllTasks(ioTime * (100 - ioRatio) / ioRatio);
                if (!this.isShutdown()) {
                    continue;
                }
                this.closeAll();
                if (this.confirmShutdown()) {
                    break;
                }
                continue;
            }
            catch (Throwable t) {
                NioEventLoop.logger.warn("Unexpected exception in the selector loop.", t);
                try {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException ex) {}
            }
        }
    }
    
    @Override
    protected void cleanup() {
        try {
            this.selector.close();
        }
        catch (IOException e) {
            NioEventLoop.logger.warn("Failed to close a selector.", e);
        }
    }
    
    void cancel(final SelectionKey key) {
        key.cancel();
        ++this.cancelledKeys;
        if (this.cancelledKeys >= 256) {
            this.cancelledKeys = 0;
            this.needsToSelectAgain = true;
        }
    }
    
    @Override
    protected Runnable pollTask() {
        final Runnable task = super.pollTask();
        if (this.needsToSelectAgain) {
            this.selectAgain();
        }
        return task;
    }
    
    private void processSelectedKeys() {
        this.needsToSelectAgain = false;
        Set<SelectionKey> selectedKeys = this.selector.selectedKeys();
        if (selectedKeys.isEmpty()) {
            return;
        }
        Iterator<SelectionKey> i = selectedKeys.iterator();
        while (true) {
            final SelectionKey k = i.next();
            final Object a = k.attachment();
            i.remove();
            if (a instanceof AbstractNioChannel) {
                processSelectedKey(k, (AbstractNioChannel)a);
            }
            else {
                final NioTask<SelectableChannel> task = (NioTask<SelectableChannel>)a;
                processSelectedKey(k, task);
            }
            if (!i.hasNext()) {
                break;
            }
            if (!this.needsToSelectAgain) {
                continue;
            }
            this.selectAgain();
            selectedKeys = this.selector.selectedKeys();
            if (selectedKeys.isEmpty()) {
                break;
            }
            i = selectedKeys.iterator();
        }
    }
    
    private static void processSelectedKey(final SelectionKey k, final AbstractNioChannel ch) {
        final AbstractNioChannel.NioUnsafe unsafe = ch.unsafe();
        if (!k.isValid()) {
            unsafe.close(unsafe.voidFuture());
            return;
        }
        int readyOps = -1;
        try {
            readyOps = k.readyOps();
            if ((readyOps & 0x11) != 0x0 || readyOps == 0) {
                unsafe.read();
                if (!ch.isOpen()) {
                    return;
                }
            }
            if ((readyOps & 0x4) != 0x0) {
                processWritable(k, ch);
            }
            if ((readyOps & 0x8) != 0x0) {
                int ops = k.interestOps();
                ops &= 0xFFFFFFF7;
                k.interestOps(ops);
                unsafe.finishConnect();
            }
        }
        catch (CancelledKeyException e) {
            if (readyOps != -1 && (readyOps & 0x4) != 0x0) {
                unregisterWritableTasks(ch);
            }
            unsafe.close(unsafe.voidFuture());
        }
    }
    
    private static void processWritable(final SelectionKey k, final AbstractNioChannel ch) {
        if (ch.writableTasks.isEmpty()) {
            ch.unsafe().flushNow();
        }
        else {
            while (true) {
                final NioTask<SelectableChannel> task = ch.writableTasks.poll();
                if (task == null) {
                    break;
                }
                processSelectedKey(ch.selectionKey(), task);
            }
            k.interestOps(k.interestOps() | 0x4);
        }
    }
    
    private static void unregisterWritableTasks(final AbstractNioChannel ch) {
        while (true) {
            final NioTask<SelectableChannel> task = ch.writableTasks.poll();
            if (task == null) {
                break;
            }
            invokeChannelUnregistered(task, ch.selectionKey(), null);
        }
    }
    
    private static void processSelectedKey(final SelectionKey k, final NioTask<SelectableChannel> task) {
        int state = 0;
        try {
            task.channelReady(k.channel(), k);
            state = 1;
        }
        catch (Exception e) {
            k.cancel();
            invokeChannelUnregistered(task, k, e);
            state = 2;
        }
        finally {
            switch (state) {
                case 0: {
                    k.cancel();
                    invokeChannelUnregistered(task, k, null);
                    break;
                }
                case 1: {
                    if (!k.isValid()) {
                        invokeChannelUnregistered(task, k, null);
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    private void closeAll() {
        this.selectAgain();
        final Set<SelectionKey> keys = this.selector.keys();
        final Collection<AbstractNioChannel> channels = new ArrayList<AbstractNioChannel>(keys.size());
        for (final SelectionKey k : keys) {
            final Object a = k.attachment();
            if (a instanceof AbstractNioChannel) {
                channels.add((AbstractNioChannel)a);
            }
            else {
                k.cancel();
                final NioTask<SelectableChannel> task = (NioTask<SelectableChannel>)a;
                invokeChannelUnregistered(task, k, null);
            }
        }
        for (final AbstractNioChannel ch : channels) {
            unregisterWritableTasks(ch);
            ch.unsafe().close(ch.unsafe().voidFuture());
        }
    }
    
    private static void invokeChannelUnregistered(final NioTask<SelectableChannel> task, final SelectionKey k, final Throwable cause) {
        try {
            task.channelUnregistered(k.channel(), cause);
        }
        catch (Exception e) {
            NioEventLoop.logger.warn("Unexpected exception while running NioTask.channelUnregistered()", e);
        }
    }
    
    @Override
    protected void wakeup(final boolean inEventLoop) {
        if (!inEventLoop && this.wakenUp.compareAndSet(false, true)) {
            this.selector.wakeup();
        }
    }
    
    void selectNow() throws IOException {
        try {
            this.selector.selectNow();
        }
        finally {
            if (this.wakenUp.get()) {
                this.selector.wakeup();
            }
        }
    }
    
    private void select() throws IOException {
        final Selector selector = this.selector;
        try {
            int selectCnt = 0;
            long currentTimeNanos = System.nanoTime();
            final long selectDeadLineNanos = currentTimeNanos + this.delayNanos(currentTimeNanos);
            while (true) {
                final long timeoutMillis = (selectDeadLineNanos - currentTimeNanos + 500000L) / 1000000L;
                if (timeoutMillis <= 0L) {
                    if (selectCnt == 0) {
                        selector.selectNow();
                        selectCnt = 1;
                        break;
                    }
                    break;
                }
                else {
                    final int selectedKeys = selector.select(timeoutMillis);
                    ++selectCnt;
                    if (selectedKeys != 0 || this.oldWakenUp || this.wakenUp.get()) {
                        break;
                    }
                    if (this.hasTasks()) {
                        break;
                    }
                    if (NioEventLoop.SELECTOR_AUTO_REBUILD_THRESHOLD > 0 && selectCnt >= NioEventLoop.SELECTOR_AUTO_REBUILD_THRESHOLD) {
                        NioEventLoop.logger.warn("Selector.select() returned prematurely {} times in a row; rebuilding selector.", (Object)selectCnt);
                        this.rebuildSelector();
                        selector.selectNow();
                        selectCnt = 1;
                        break;
                    }
                    currentTimeNanos = System.nanoTime();
                }
            }
            if (selectCnt > 3 && NioEventLoop.logger.isDebugEnabled()) {
                NioEventLoop.logger.debug("Selector.select() returned prematurely {} times in a row.", (Object)(selectCnt - 1));
            }
        }
        catch (CancelledKeyException e) {
            if (NioEventLoop.logger.isDebugEnabled()) {
                NioEventLoop.logger.debug(CancelledKeyException.class.getSimpleName() + " raised by a Selector - JDK bug?", e);
            }
        }
    }
    
    private void selectAgain() {
        this.needsToSelectAgain = false;
        try {
            this.selector.selectNow();
        }
        catch (Throwable t) {
            NioEventLoop.logger.warn("Failed to update SelectionKeys.", t);
        }
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(NioEventLoop.class);
        final String key = "sun.nio.ch.bugLevel";
        try {
            final String buglevel = System.getProperty(key);
            if (buglevel == null) {
                System.setProperty(key, "");
            }
        }
        catch (SecurityException e) {
            if (NioEventLoop.logger.isDebugEnabled()) {
                NioEventLoop.logger.debug("Unable to get/set System Property '" + key + '\'', e);
            }
        }
        int selectorAutoRebuildThreshold = SystemPropertyUtil.getInt("io.netty.selectorAutoRebuildThreshold", 512);
        if (selectorAutoRebuildThreshold < 3) {
            selectorAutoRebuildThreshold = 0;
        }
        SELECTOR_AUTO_REBUILD_THRESHOLD = selectorAutoRebuildThreshold;
        if (NioEventLoop.logger.isDebugEnabled()) {
            NioEventLoop.logger.debug("Selector auto-rebuild threshold: {}", (Object)NioEventLoop.SELECTOR_AUTO_REBUILD_THRESHOLD);
        }
    }
}
