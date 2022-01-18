// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.sctp.oio;

import io.netty.buffer.BufType;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelFuture;
import java.net.InetAddress;
import java.util.Collections;
import java.util.LinkedHashSet;
import com.sun.nio.sctp.Association;
import java.net.SocketAddress;
import com.sun.nio.sctp.MessageInfo;
import java.nio.ByteBuffer;
import io.netty.buffer.ByteBuf;
import java.util.Iterator;
import java.util.Set;
import io.netty.util.internal.PlatformDependent;
import io.netty.channel.sctp.SctpMessage;
import java.nio.channels.SelectionKey;
import io.netty.buffer.MessageBuf;
import io.netty.channel.sctp.SctpServerChannel;
import java.net.InetSocketAddress;
import io.netty.channel.sctp.SctpNotificationHandler;
import io.netty.channel.sctp.DefaultSctpChannelConfig;
import io.netty.channel.Channel;
import java.io.IOException;
import io.netty.channel.ChannelException;
import com.sun.nio.sctp.NotificationHandler;
import java.nio.channels.Selector;
import io.netty.channel.sctp.SctpChannelConfig;
import io.netty.channel.ChannelMetadata;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.channel.sctp.SctpChannel;
import io.netty.channel.oio.AbstractOioMessageChannel;

public class OioSctpChannel extends AbstractOioMessageChannel implements SctpChannel
{
    private static final InternalLogger logger;
    private static final ChannelMetadata METADATA;
    private final com.sun.nio.sctp.SctpChannel ch;
    private final SctpChannelConfig config;
    private final Selector readSelector;
    private final Selector writeSelector;
    private final Selector connectSelector;
    private final NotificationHandler<?> notificationHandler;
    
    private static com.sun.nio.sctp.SctpChannel openChannel() {
        try {
            return com.sun.nio.sctp.SctpChannel.open();
        }
        catch (IOException e) {
            throw new ChannelException("Failed to open a sctp channel.", e);
        }
    }
    
    public OioSctpChannel() {
        this(openChannel());
    }
    
    public OioSctpChannel(final com.sun.nio.sctp.SctpChannel ch) {
        this(null, null, ch);
    }
    
    public OioSctpChannel(final Channel parent, final Integer id, final com.sun.nio.sctp.SctpChannel ch) {
        super(parent, id);
        this.ch = ch;
        boolean success = false;
        try {
            ch.configureBlocking(false);
            this.readSelector = Selector.open();
            this.writeSelector = Selector.open();
            this.connectSelector = Selector.open();
            ch.register(this.readSelector, 1);
            ch.register(this.writeSelector, 4);
            ch.register(this.connectSelector, 8);
            this.config = new DefaultSctpChannelConfig(this, ch);
            this.notificationHandler = new SctpNotificationHandler(this);
            success = true;
        }
        catch (Exception e) {
            throw new ChannelException("failed to initialize a sctp channel", e);
        }
        finally {
            if (!success) {
                try {
                    ch.close();
                }
                catch (IOException e2) {
                    OioSctpChannel.logger.warn("Failed to close a sctp channel.", e2);
                }
            }
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
        return OioSctpChannel.METADATA;
    }
    
    @Override
    public SctpChannelConfig config() {
        return this.config;
    }
    
    @Override
    public boolean isOpen() {
        return this.ch.isOpen();
    }
    
    @Override
    protected int doReadMessages(final MessageBuf<Object> buf) throws Exception {
        if (!this.readSelector.isOpen()) {
            return 0;
        }
        int readMessages = 0;
        final int selectedKeys = this.readSelector.select(1000L);
        final boolean keysSelected = selectedKeys > 0;
        if (!keysSelected) {
            return readMessages;
        }
        final Set<SelectionKey> reableKeys = this.readSelector.selectedKeys();
        try {
            for (final SelectionKey ignored : reableKeys) {
                final ByteBuf buffer = this.alloc().directBuffer(this.config().getReceiveBufferSize());
                boolean free = true;
                try {
                    final ByteBuffer data = buffer.nioBuffer(buffer.writerIndex(), buffer.writableBytes());
                    final MessageInfo messageInfo = this.ch.receive(data, null, this.notificationHandler);
                    if (messageInfo == null) {
                        return readMessages;
                    }
                    data.flip();
                    buf.add(new SctpMessage(messageInfo, buffer.writerIndex(buffer.writerIndex() + data.remaining())));
                    free = false;
                    ++readMessages;
                }
                catch (Throwable cause) {
                    PlatformDependent.throwException(cause);
                }
                finally {
                    if (free) {
                        buffer.release();
                    }
                }
            }
        }
        finally {
            reableKeys.clear();
        }
        return readMessages;
    }
    
    @Override
    protected void doWriteMessages(final MessageBuf<Object> buf) throws Exception {
        if (!this.writeSelector.isOpen()) {
            return;
        }
        final int selectedKeys = this.writeSelector.select(1000L);
        if (selectedKeys > 0) {
            final Set<SelectionKey> writableKeys = this.writeSelector.selectedKeys();
            for (final SelectionKey ignored : writableKeys) {
                final SctpMessage packet = buf.poll();
                if (packet == null) {
                    return;
                }
                try {
                    final ByteBuf data = packet.data();
                    final int dataLen = data.readableBytes();
                    ByteBuffer nioData;
                    if (data.nioBufferCount() != -1) {
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
                    this.ch.send(nioData, mi);
                }
                finally {
                    packet.release();
                }
            }
            writableKeys.clear();
        }
    }
    
    @Override
    public Association association() {
        try {
            return this.ch.association();
        }
        catch (IOException e) {
            return null;
        }
    }
    
    @Override
    public boolean isActive() {
        return this.isOpen() && this.association() != null;
    }
    
    @Override
    protected SocketAddress localAddress0() {
        try {
            final Iterator<SocketAddress> i = this.ch.getAllLocalAddresses().iterator();
            if (i.hasNext()) {
                return i.next();
            }
        }
        catch (IOException ex) {}
        return null;
    }
    
    @Override
    public Set<InetSocketAddress> allLocalAddresses() {
        try {
            final Set<SocketAddress> allLocalAddresses = this.ch.getAllLocalAddresses();
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
    protected SocketAddress remoteAddress0() {
        try {
            final Iterator<SocketAddress> i = this.ch.getRemoteAddresses().iterator();
            if (i.hasNext()) {
                return i.next();
            }
        }
        catch (IOException ex) {}
        return null;
    }
    
    @Override
    public Set<InetSocketAddress> allRemoteAddresses() {
        try {
            final Set<SocketAddress> allLocalAddresses = this.ch.getRemoteAddresses();
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
    protected void doBind(final SocketAddress localAddress) throws Exception {
        this.ch.bind(localAddress);
    }
    
    @Override
    protected void doConnect(final SocketAddress remoteAddress, final SocketAddress localAddress) throws Exception {
        if (localAddress != null) {
            this.ch.bind(localAddress);
        }
        boolean success = false;
        try {
            this.ch.connect(remoteAddress);
            boolean finishConnect = false;
            while (!finishConnect) {
                if (this.connectSelector.select(1000L) >= 0) {
                    final Set<SelectionKey> selectionKeys = this.connectSelector.selectedKeys();
                    for (final SelectionKey key : selectionKeys) {
                        if (key.isConnectable()) {
                            selectionKeys.clear();
                            finishConnect = true;
                            break;
                        }
                    }
                    selectionKeys.clear();
                }
            }
            success = this.ch.finishConnect();
        }
        finally {
            if (!success) {
                this.doClose();
            }
        }
    }
    
    @Override
    protected void doDisconnect() throws Exception {
        this.doClose();
    }
    
    @Override
    protected void doClose() throws Exception {
        closeSelector("read", this.readSelector);
        closeSelector("write", this.writeSelector);
        closeSelector("connect", this.connectSelector);
        this.ch.close();
    }
    
    private static void closeSelector(final String selectorName, final Selector selector) {
        try {
            selector.close();
        }
        catch (IOException e) {
            OioSctpChannel.logger.warn("Failed to close a " + selectorName + " selector.", e);
        }
    }
    
    @Override
    public ChannelFuture bindAddress(final InetAddress localAddress) {
        return this.bindAddress(localAddress, this.newPromise());
    }
    
    @Override
    public ChannelFuture bindAddress(final InetAddress localAddress, final ChannelPromise promise) {
        if (this.eventLoop().inEventLoop()) {
            try {
                this.ch.bindAddress(localAddress);
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
                    OioSctpChannel.this.bindAddress(localAddress, promise);
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
                this.ch.unbindAddress(localAddress);
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
                    OioSctpChannel.this.unbindAddress(localAddress, promise);
                }
            });
        }
        return promise;
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(OioSctpChannel.class);
        METADATA = new ChannelMetadata(BufType.MESSAGE, false);
    }
}
