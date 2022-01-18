// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import io.netty.buffer.PooledByteBufAllocator;
import java.util.Iterator;
import java.util.IdentityHashMap;
import java.util.Map;
import io.netty.buffer.ByteBufAllocator;

public class DefaultChannelConfig implements ChannelConfig
{
    private static final ByteBufAllocator DEFAULT_ALLOCATOR;
    private static final int DEFAULT_CONNECT_TIMEOUT = 30000;
    protected final Channel channel;
    private volatile ChannelHandlerByteBufType handlerByteBufType;
    private volatile ByteBufAllocator allocator;
    private volatile int connectTimeoutMillis;
    private volatile int writeSpinCount;
    private volatile boolean autoRead;
    
    public DefaultChannelConfig(final Channel channel) {
        this.handlerByteBufType = ChannelHandlerByteBufType.PREFER_DIRECT;
        this.allocator = DefaultChannelConfig.DEFAULT_ALLOCATOR;
        this.connectTimeoutMillis = 30000;
        this.writeSpinCount = 16;
        this.autoRead = true;
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        this.channel = channel;
    }
    
    @Override
    public Map<ChannelOption<?>, Object> getOptions() {
        return this.getOptions(null, ChannelOption.CONNECT_TIMEOUT_MILLIS, ChannelOption.WRITE_SPIN_COUNT, ChannelOption.ALLOCATOR, ChannelOption.AUTO_READ, ChannelOption.DEFAULT_HANDLER_BYTEBUF_TYPE);
    }
    
    protected Map<ChannelOption<?>, Object> getOptions(Map<ChannelOption<?>, Object> result, final ChannelOption<?>... options) {
        if (result == null) {
            result = new IdentityHashMap<ChannelOption<?>, Object>();
        }
        for (final ChannelOption<?> o : options) {
            result.put(o, this.getOption(o));
        }
        return result;
    }
    
    @Override
    public boolean setOptions(final Map<ChannelOption<?>, ?> options) {
        if (options == null) {
            throw new NullPointerException("options");
        }
        boolean setAllOptions = true;
        for (final Map.Entry<ChannelOption<?>, ?> e : options.entrySet()) {
            if (!this.setOption(e.getKey(), e.getValue())) {
                setAllOptions = false;
            }
        }
        return setAllOptions;
    }
    
    @Override
    public <T> T getOption(final ChannelOption<T> option) {
        if (option == null) {
            throw new NullPointerException("option");
        }
        if (option == ChannelOption.CONNECT_TIMEOUT_MILLIS) {
            return (T)this.getConnectTimeoutMillis();
        }
        if (option == ChannelOption.WRITE_SPIN_COUNT) {
            return (T)this.getWriteSpinCount();
        }
        if (option == ChannelOption.ALLOCATOR) {
            return (T)this.getAllocator();
        }
        if (option == ChannelOption.AUTO_READ) {
            return (T)this.isAutoRead();
        }
        if (option == DefaultChannelConfig.DEFAULT_ALLOCATOR) {
            return (T)this.getDefaultHandlerByteBufType();
        }
        return null;
    }
    
    @Override
    public <T> boolean setOption(final ChannelOption<T> option, final T value) {
        this.validate(option, value);
        if (option == ChannelOption.CONNECT_TIMEOUT_MILLIS) {
            this.setConnectTimeoutMillis((int)value);
        }
        else if (option == ChannelOption.WRITE_SPIN_COUNT) {
            this.setWriteSpinCount((int)value);
        }
        else if (option == ChannelOption.ALLOCATOR) {
            this.setAllocator((ByteBufAllocator)value);
        }
        else if (option == ChannelOption.AUTO_READ) {
            this.setAutoRead((boolean)value);
        }
        else {
            if (option != ChannelOption.DEFAULT_HANDLER_BYTEBUF_TYPE) {
                return false;
            }
            this.setDefaultHandlerByteBufType((ChannelHandlerByteBufType)value);
        }
        return true;
    }
    
    protected <T> void validate(final ChannelOption<T> option, final T value) {
        if (option == null) {
            throw new NullPointerException("option");
        }
        option.validate(value);
    }
    
    @Override
    public int getConnectTimeoutMillis() {
        return this.connectTimeoutMillis;
    }
    
    @Override
    public ChannelConfig setConnectTimeoutMillis(final int connectTimeoutMillis) {
        if (connectTimeoutMillis < 0) {
            throw new IllegalArgumentException(String.format("connectTimeoutMillis: %d (expected: >= 0)", connectTimeoutMillis));
        }
        this.connectTimeoutMillis = connectTimeoutMillis;
        return this;
    }
    
    @Override
    public int getWriteSpinCount() {
        return this.writeSpinCount;
    }
    
    @Override
    public ChannelConfig setWriteSpinCount(final int writeSpinCount) {
        if (writeSpinCount <= 0) {
            throw new IllegalArgumentException("writeSpinCount must be a positive integer.");
        }
        this.writeSpinCount = writeSpinCount;
        return this;
    }
    
    @Override
    public ByteBufAllocator getAllocator() {
        return this.allocator;
    }
    
    @Override
    public ChannelConfig setAllocator(final ByteBufAllocator allocator) {
        if (allocator == null) {
            throw new NullPointerException("allocator");
        }
        this.allocator = allocator;
        return this;
    }
    
    @Override
    public boolean isAutoRead() {
        return this.autoRead;
    }
    
    @Override
    public ChannelConfig setAutoRead(final boolean autoRead) {
        final boolean oldAutoRead = this.autoRead;
        this.autoRead = autoRead;
        if (autoRead && !oldAutoRead) {
            this.channel.read();
        }
        return this;
    }
    
    @Override
    public ChannelHandlerByteBufType getDefaultHandlerByteBufType() {
        return this.handlerByteBufType;
    }
    
    @Override
    public ChannelConfig setDefaultHandlerByteBufType(final ChannelHandlerByteBufType handlerByteBufType) {
        this.handlerByteBufType = handlerByteBufType;
        return this;
    }
    
    static {
        DEFAULT_ALLOCATOR = PooledByteBufAllocator.DEFAULT;
    }
}
