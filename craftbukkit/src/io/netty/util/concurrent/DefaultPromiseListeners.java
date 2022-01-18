// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.util.concurrent;

import java.util.EventListener;
import java.util.Arrays;

final class DefaultPromiseListeners
{
    private GenericFutureListener<? extends Future<?>>[] listeners;
    private int size;
    
    DefaultPromiseListeners(final GenericFutureListener<? extends Future<?>> firstListener, final GenericFutureListener<? extends Future<?>> secondListener) {
        this.listeners = (GenericFutureListener<? extends Future<?>>[])new GenericFutureListener[] { firstListener, secondListener };
        this.size = 2;
    }
    
    void add(final GenericFutureListener<? extends Future<?>> l) {
        GenericFutureListener<? extends Future<?>>[] listeners = this.listeners;
        final int size = this.size;
        if (size == listeners.length) {
            listeners = (this.listeners = Arrays.copyOf(listeners, size << 1));
        }
        listeners[size] = l;
        this.size = size + 1;
    }
    
    void remove(final EventListener l) {
        final EventListener[] listeners = this.listeners;
        for (int size = this.size, i = 0; i < size; ++i) {
            if (listeners[i] == l) {
                final int listenersToMove = size - i - 1;
                if (listenersToMove > 0) {
                    System.arraycopy(listeners, i + 1, listeners, i, listenersToMove);
                }
                listeners[--size] = null;
                this.size = size;
                return;
            }
        }
    }
    
    GenericFutureListener<? extends Future<?>>[] listeners() {
        return this.listeners;
    }
    
    int size() {
        return this.size;
    }
}
