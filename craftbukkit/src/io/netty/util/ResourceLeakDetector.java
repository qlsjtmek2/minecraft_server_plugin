// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.util;

import java.lang.ref.PhantomReference;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.PlatformDependent;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.ConcurrentMap;
import java.lang.ref.ReferenceQueue;
import io.netty.util.internal.logging.InternalLogger;

public final class ResourceLeakDetector<T>
{
    private static final boolean ENABLED;
    private static final InternalLogger logger;
    private static final int DEFAULT_SAMPLING_INTERVAL = 113;
    private static final ResourceLeak NOOP;
    private final DefaultResourceLeak head;
    private final DefaultResourceLeak tail;
    private final ReferenceQueue<Object> refQueue;
    private final ConcurrentMap<Exception, Boolean> reportedLeaks;
    private final String resourceType;
    private final int samplingInterval;
    private final long maxActive;
    private long active;
    private final AtomicBoolean loggedTooManyActive;
    private long leakCheckCnt;
    
    public ResourceLeakDetector(final Class<?> resourceType) {
        this(resourceType.getSimpleName());
    }
    
    public ResourceLeakDetector(final String resourceType) {
        this(resourceType, 113, Long.MAX_VALUE);
    }
    
    public ResourceLeakDetector(final Class<?> resourceType, final int samplingInterval, final long maxActive) {
        this(resourceType.getSimpleName(), samplingInterval, maxActive);
    }
    
    public ResourceLeakDetector(final String resourceType, final int samplingInterval, final long maxActive) {
        this.head = new DefaultResourceLeak((Object)null);
        this.tail = new DefaultResourceLeak((Object)null);
        this.refQueue = new ReferenceQueue<Object>();
        this.reportedLeaks = PlatformDependent.newConcurrentHashMap();
        this.loggedTooManyActive = new AtomicBoolean();
        if (resourceType == null) {
            throw new NullPointerException("resourceType");
        }
        if (samplingInterval <= 0) {
            throw new IllegalArgumentException("samplingInterval: " + samplingInterval + " (expected: 1+)");
        }
        if (maxActive <= 0L) {
            throw new IllegalArgumentException("maxActive: " + maxActive + " (expected: 1+)");
        }
        this.resourceType = resourceType;
        this.samplingInterval = samplingInterval;
        this.maxActive = maxActive;
        this.head.next = this.tail;
        this.tail.prev = this.head;
    }
    
    public ResourceLeak open(final T obj) {
        if (!ResourceLeakDetector.ENABLED || this.leakCheckCnt++ % this.samplingInterval != 0L) {
            return ResourceLeakDetector.NOOP;
        }
        this.reportLeak();
        return new DefaultResourceLeak(obj);
    }
    
    private void reportLeak() {
        if (!ResourceLeakDetector.logger.isWarnEnabled()) {
            while (true) {
                final DefaultResourceLeak ref = (DefaultResourceLeak)this.refQueue.poll();
                if (ref == null) {
                    break;
                }
                ref.close();
            }
            return;
        }
        if (this.active * this.samplingInterval > this.maxActive && this.loggedTooManyActive.compareAndSet(false, true)) {
            ResourceLeakDetector.logger.warn("LEAK: You are creating too many " + this.resourceType + " instances.  " + this.resourceType + " is a shared resource that must be reused across the JVM," + "so that only a few instances are created.");
        }
        while (true) {
            final DefaultResourceLeak ref = (DefaultResourceLeak)this.refQueue.poll();
            if (ref == null) {
                break;
            }
            ref.clear();
            if (!ref.close()) {
                continue;
            }
            if (this.reportedLeaks.putIfAbsent(ref.exception, Boolean.TRUE) != null) {
                continue;
            }
            ResourceLeakDetector.logger.warn("LEAK: " + this.resourceType + " was GC'd before being released correctly.", ref.exception);
        }
    }
    
    static {
        ENABLED = SystemPropertyUtil.getBoolean("io.netty.resourceLeakDetection", false);
        logger = InternalLoggerFactory.getInstance(ResourceLeakDetector.class);
        if (ResourceLeakDetector.ENABLED) {
            ResourceLeakDetector.logger.debug("Resource leak detection enabled.");
        }
        NOOP = new ResourceLeak() {
            @Override
            public boolean close() {
                return false;
            }
        };
    }
    
    private final class DefaultResourceLeak extends PhantomReference<Object> implements ResourceLeak
    {
        private final ResourceLeakException exception;
        private final AtomicBoolean freed;
        private DefaultResourceLeak prev;
        private DefaultResourceLeak next;
        
        public DefaultResourceLeak(final Object referent) {
            super(referent, (referent != null) ? ResourceLeakDetector.this.refQueue : null);
            if (referent != null) {
                this.exception = new ResourceLeakException(referent.getClass().getName() + '@' + Integer.toHexString(System.identityHashCode(referent)));
                synchronized (ResourceLeakDetector.this.head) {
                    this.prev = ResourceLeakDetector.this.head;
                    this.next = ResourceLeakDetector.this.head.next;
                    ResourceLeakDetector.this.head.next.prev = this;
                    ResourceLeakDetector.this.head.next = this;
                    ResourceLeakDetector.this.active++;
                }
                this.freed = new AtomicBoolean();
            }
            else {
                this.exception = null;
                this.freed = new AtomicBoolean(true);
            }
        }
        
        @Override
        public boolean close() {
            if (this.freed.compareAndSet(false, true)) {
                synchronized (ResourceLeakDetector.this.head) {
                    ResourceLeakDetector.this.active--;
                    this.prev.next = this.next;
                    this.next.prev = this.prev;
                    this.prev = null;
                    this.next = null;
                }
                return true;
            }
            return false;
        }
    }
}
