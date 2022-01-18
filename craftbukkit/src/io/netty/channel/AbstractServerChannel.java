// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import io.netty.buffer.BufType;
import java.net.SocketAddress;
import io.netty.buffer.MessageBuf;
import io.netty.buffer.ByteBuf;

public abstract class AbstractServerChannel extends AbstractChannel implements ServerChannel
{
    private static final ChannelMetadata METADATA;
    
    protected AbstractServerChannel(final Integer id) {
        super(null, id);
    }
    
    @Override
    public ByteBuf outboundByteBuffer() {
        throw new NoSuchBufferException(String.format("%s does not have an outbound buffer.", ServerChannel.class.getSimpleName()));
    }
    
    @Override
    public <T> MessageBuf<T> outboundMessageBuffer() {
        throw new NoSuchBufferException(String.format("%s does not have an outbound buffer.", ServerChannel.class.getSimpleName()));
    }
    
    @Override
    public ChannelMetadata metadata() {
        return AbstractServerChannel.METADATA;
    }
    
    @Override
    public SocketAddress remoteAddress() {
        return null;
    }
    
    @Override
    protected SocketAddress remoteAddress0() {
        return null;
    }
    
    @Override
    protected void doDisconnect() throws Exception {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected boolean isFlushPending() {
        return false;
    }
    
    @Override
    protected AbstractUnsafe newUnsafe() {
        return new DefaultServerUnsafe();
    }
    
    static {
        METADATA = new ChannelMetadata(BufType.MESSAGE, false);
    }
    
    private final class DefaultServerUnsafe extends AbstractUnsafe
    {
        @Override
        public void flush(final ChannelPromise future) {
            if (AbstractServerChannel.this.eventLoop().inEventLoop()) {
                this.reject(future);
            }
            else {
                AbstractServerChannel.this.eventLoop().execute(new Runnable() {
                    @Override
                    public void run() {
                        DefaultServerUnsafe.this.flush(future);
                    }
                });
            }
        }
        
        @Override
        public void connect(final SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise future) {
            if (AbstractServerChannel.this.eventLoop().inEventLoop()) {
                this.reject(future);
            }
            else {
                AbstractServerChannel.this.eventLoop().execute(new Runnable() {
                    @Override
                    public void run() {
                        DefaultServerUnsafe.this.connect(remoteAddress, localAddress, future);
                    }
                });
            }
        }
        
        private void reject(final ChannelPromise future) {
            final Exception cause = new UnsupportedOperationException();
            future.setFailure((Throwable)cause);
        }
    }
}
