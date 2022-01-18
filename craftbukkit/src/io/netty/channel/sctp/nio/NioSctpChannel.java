// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.sctp.nio;

import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.buffer.BufType;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelFuture;
import java.net.InetAddress;
import java.nio.channels.SelectionKey;
import com.sun.nio.sctp.MessageInfo;
import java.nio.ByteBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.PlatformDependent;
import io.netty.channel.sctp.SctpMessage;
import io.netty.buffer.MessageBuf;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Collections;
import java.net.SocketAddress;
import java.util.LinkedHashSet;
import java.util.Set;
import com.sun.nio.sctp.Association;
import io.netty.channel.sctp.SctpServerChannel;
import java.net.InetSocketAddress;
import io.netty.channel.sctp.SctpNotificationHandler;
import io.netty.channel.sctp.DefaultSctpChannelConfig;
import java.nio.channels.SelectableChannel;
import io.netty.channel.Channel;
import java.io.IOException;
import io.netty.channel.ChannelException;
import com.sun.nio.sctp.NotificationHandler;
import io.netty.channel.sctp.SctpChannelConfig;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.sctp.SctpChannel;
import io.netty.channel.nio.AbstractNioMessageChannel;

public class NioSctpChannel extends AbstractNioMessageChannel implements SctpChannel
{
    private static final ChannelMetadata METADATA;
    private static final InternalLogger logger;
    private final SctpChannelConfig config;
    private final NotificationHandler notificationHandler;
    
    private static com.sun.nio.sctp.SctpChannel newSctpChannel() {
        try {
            return com.sun.nio.sctp.SctpChannel.open();
        }
        catch (IOException e) {
            throw new ChannelException("Failed to open a sctp channel.", e);
        }
    }
    
    public NioSctpChannel() {
        this(newSctpChannel());
    }
    
    public NioSctpChannel(final com.sun.nio.sctp.SctpChannel sctpChannel) {
        this(null, null, sctpChannel);
    }
    
    public NioSctpChannel(final Channel parent, final Integer id, final com.sun.nio.sctp.SctpChannel sctpChannel) {
        super(parent, id, sctpChannel, 1);
        try {
            sctpChannel.configureBlocking(false);
            this.config = new DefaultSctpChannelConfig(this, sctpChannel);
            this.notificationHandler = new SctpNotificationHandler(this);
        }
        catch (IOException e3) {
            try {
                sctpChannel.close();
            }
            catch (IOException e2) {
                if (NioSctpChannel.logger.isWarnEnabled()) {
                    NioSctpChannel.logger.warn("Failed to close a partially initialized sctp channel.", e2);
                }
            }
            throw new ChannelException("Failed to enter non-blocking mode.", e3);
        }
    }
    
    @Override
    public InetSocketAddress localAddress() {
        return (InetSocketAddress)super.localAddress();
    }
    
    @Override
    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress)super.remoteAddress();
    }
    
    @Override
    public SctpServerChannel parent() {
        return (SctpServerChannel)super.parent();
    }
    
    @Override
    public ChannelMetadata metadata() {
        return NioSctpChannel.METADATA;
    }
    
    @Override
    public Association association() {
        try {
            return this.javaChannel().association();
        }
        catch (IOException e) {
            return null;
        }
    }
    
    @Override
    public Set<InetSocketAddress> allLocalAddresses() {
        try {
            final Set<SocketAddress> allLocalAddresses = this.javaChannel().getAllLocalAddresses();
            final Set<InetSocketAddress> addresses = new LinkedHashSet<InetSocketAddress>(allLocalAddresses.size());
            for (final SocketAddress socketAddress : allLocalAddresses) {
                addresses.add((InetSocketAddress)socketAddress);
            }
            return addresses;
        }
        catch (Throwable t) {
            return Collections.emptySet();
        }
    }
    
    @Override
    public SctpChannelConfig config() {
        return this.config;
    }
    
    @Override
    public Set<InetSocketAddress> allRemoteAddresses() {
        try {
            final Set<SocketAddress> allLocalAddresses = this.javaChannel().getRemoteAddresses();
            final Set<InetSocketAddress> addresses = new HashSet<InetSocketAddress>(allLocalAddresses.size());
            for (final SocketAddress socketAddress : allLocalAddresses) {
                addresses.add((InetSocketAddress)socketAddress);
            }
            return addresses;
        }
        catch (Throwable t) {
            return Collections.emptySet();
        }
    }
    
    @Override
    protected com.sun.nio.sctp.SctpChannel javaChannel() {
        return (com.sun.nio.sctp.SctpChannel)super.javaChannel();
    }
    
    @Override
    public boolean isActive() {
        final com.sun.nio.sctp.SctpChannel ch = this.javaChannel();
        return ch.isOpen() && this.association() != null;
    }
    
    @Override
    protected SocketAddress localAddress0() {
        try {
            final Iterator<SocketAddress> i = this.javaChannel().getAllLocalAddresses().iterator();
            if (i.hasNext()) {
                return i.next();
            }
        }
        catch (IOException ex) {}
        return null;
    }
    
    @Override
    protected SocketAddress remoteAddress0() {
        try {
            final Iterator<SocketAddress> i = this.javaChannel().getRemoteAddresses().iterator();
            if (i.hasNext()) {
                return i.next();
            }
        }
        catch (IOException ex) {}
        return null;
    }
    
    @Override
    protected void doBind(final SocketAddress localAddress) throws Exception {
        this.javaChannel().bind(localAddress);
    }
    
    @Override
    protected boolean doConnect(final SocketAddress remoteAddress, final SocketAddress localAddress) throws Exception {
        if (localAddress != null) {
            this.javaChannel().bind(localAddress);
        }
        boolean success = false;
        try {
            final boolean connected = this.javaChannel().connect(remoteAddress);
            if (!connected) {
                this.selectionKey().interestOps(8);
            }
            success = true;
            return connected;
        }
        finally {
            if (!success) {
                this.doClose();
            }
        }
    }
    
    @Override
    protected void doFinishConnect() throws Exception {
        if (!this.javaChannel().finishConnect()) {
            throw new Error();
        }
    }
    
    @Override
    protected void doDisconnect() throws Exception {
        this.doClose();
    }
    
    @Override
    protected void doClose() throws Exception {
        this.javaChannel().close();
    }
    
    @Override
    protected int doReadMessages(final MessageBuf<Object> buf) throws Exception {
        final com.sun.nio.sctp.SctpChannel ch = this.javaChannel();
        final ByteBuf buffer = this.alloc().directBuffer(this.config().getReceiveBufferSize());
        boolean free = true;
        try {
            final ByteBuffer data = buffer.nioBuffer(buffer.writerIndex(), buffer.writableBytes());
            final MessageInfo messageInfo = ch.receive(data, null, (NotificationHandler<Object>)this.notificationHandler);
            if (messageInfo == null) {
                return 0;
            }
            data.flip();
            buf.add(new SctpMessage(messageInfo, buffer.writerIndex(buffer.writerIndex() + data.remaining())));
            free = false;
            return 1;
        }
        catch (Throwable cause) {
            PlatformDependent.throwException(cause);
            return -1;
        }
        finally {
            if (free) {
                buffer.release();
            }
        }
    }
    
    @Override
    protected int doWriteMessages(final MessageBuf<Object> buf, final boolean lastSpin) throws Exception {
        final SctpMessage packet = buf.peek();
        final ByteBuf data = packet.data();
        final int dataLen = data.readableBytes();
        ByteBuffer nioData;
        if (data.nioBufferCount() == 1) {
            nioData = data.nioBuffer();
        }
        else {
            nioData = ByteBuffer.allocate(dataLen);
            data.getBytes(data.readerIndex(), nioData);
            nioData.flip();
        }
        final MessageInfo mi = MessageInfo.createOutgoing(this.association(), null, packet.streamIdentifier());
        mi.payloadProtocolID(packet.protocolIdentifier());
        mi.streamNumber(packet.streamIdentifier());
        final int writtenBytes = this.javaChannel().send(nioData, mi);
        final SelectionKey key = this.selectionKey();
        final int interestOps = key.interestOps();
        if (writtenBytes <= 0 && dataLen > 0) {
            if (lastSpin && (interestOps & 0x4) == 0x0) {
                key.interestOps(interestOps | 0x4);
            }
            return 0;
        }
        buf.remove();
        packet.release();
        if (buf.isEmpty() && (interestOps & 0x4) != 0x0) {
            key.interestOps(interestOps & 0xFFFFFFFB);
        }
        return 1;
    }
    
    @Override
    public ChannelFuture bindAddress(final InetAddress localAddress) {
        return this.bindAddress(localAddress, this.newPromise());
    }
    
    @Override
    public ChannelFuture bindAddress(final InetAddress localAddress, final ChannelPromise promise) {
        if (this.eventLoop().inEventLoop()) {
            try {
                this.javaChannel().bindAddress(localAddress);
                promise.setSuccess();
            }
            catch (Throwable t) {
                promise.setFailure(t);
            }
        }
        else {
            this.eventLoop().execute(new Runnable() {
                @Override
                public void run() {
                    NioSctpChannel.this.bindAddress(localAddress, promise);
                }
            });
        }
        return promise;
    }
    
    @Override
    public ChannelFuture unbindAddress(final InetAddress localAddress) {
        return this.unbindAddress(localAddress, this.newPromise());
    }
    
    @Override
    public ChannelFuture unbindAddress(final InetAddress localAddress, final ChannelPromise promise) {
        if (this.eventLoop().inEventLoop()) {
            try {
                this.javaChannel().unbindAddress(localAddress);
                promise.setSuccess();
            }
            catch (Throwable t) {
                promise.setFailure(t);
            }
        }
        else {
            this.eventLoop().execute(new Runnable() {
                @Override
                public void run() {
                    NioSctpChannel.this.unbindAddress(localAddress, promise);
                }
            });
        }
        return promise;
    }
    
    static {
        METADATA = new ChannelMetadata(BufType.MESSAGE, false);
        logger = InternalLoggerFactory.getInstance(NioSctpChannel.class);
    }
}
