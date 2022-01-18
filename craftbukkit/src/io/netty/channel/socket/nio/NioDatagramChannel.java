// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.socket.nio;

import io.netty.buffer.BufType;
import io.netty.channel.ChannelConfig;
import java.util.Iterator;
import java.util.ArrayList;
import java.net.SocketException;
import java.net.NetworkInterface;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelFuture;
import java.nio.channels.SelectionKey;
import java.nio.ByteBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.socket.DatagramPacket;
import java.net.InetSocketAddress;
import io.netty.buffer.MessageBuf;
import java.net.SocketAddress;
import java.util.HashMap;
import java.nio.channels.SelectableChannel;
import io.netty.channel.Channel;
import io.netty.util.internal.PlatformDependent;
import io.netty.channel.socket.InternetProtocolFamily;
import java.io.IOException;
import io.netty.channel.ChannelException;
import java.nio.channels.MembershipKey;
import java.util.List;
import java.net.InetAddress;
import java.util.Map;
import io.netty.channel.socket.DatagramChannelConfig;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.nio.AbstractNioMessageChannel;

public final class NioDatagramChannel extends AbstractNioMessageChannel implements DatagramChannel
{
    private static final ChannelMetadata METADATA;
    private final DatagramChannelConfig config;
    private final Map<InetAddress, List<MembershipKey>> memberships;
    
    private static java.nio.channels.DatagramChannel newSocket() {
        try {
            return java.nio.channels.DatagramChannel.open();
        }
        catch (IOException e) {
            throw new ChannelException("Failed to open a socket.", e);
        }
    }
    
    private static java.nio.channels.DatagramChannel newSocket(final InternetProtocolFamily ipFamily) {
        if (ipFamily == null) {
            return newSocket();
        }
        if (PlatformDependent.javaVersion() < 7) {
            throw new UnsupportedOperationException();
        }
        try {
            return java.nio.channels.DatagramChannel.open(ProtocolFamilyConverter.convert(ipFamily));
        }
        catch (IOException e) {
            throw new ChannelException("Failed to open a socket.", e);
        }
    }
    
    public NioDatagramChannel() {
        this(newSocket());
    }
    
    public NioDatagramChannel(final InternetProtocolFamily ipFamily) {
        this(newSocket(ipFamily));
    }
    
    public NioDatagramChannel(final java.nio.channels.DatagramChannel socket) {
        this(null, socket);
    }
    
    public NioDatagramChannel(final Integer id, final java.nio.channels.DatagramChannel socket) {
        super(null, id, socket, 1);
        this.memberships = new HashMap<InetAddress, List<MembershipKey>>();
        this.config = new NioDatagramChannelConfig(this, socket);
    }
    
    @Override
    public ChannelMetadata metadata() {
        return NioDatagramChannel.METADATA;
    }
    
    @Override
    public DatagramChannelConfig config() {
        return this.config;
    }
    
    @Override
    public boolean isActive() {
        final java.nio.channels.DatagramChannel ch = this.javaChannel();
        return ch.isOpen() && ch.socket().isBound();
    }
    
    @Override
    public boolean isConnected() {
        return this.javaChannel().isConnected();
    }
    
    @Override
    protected java.nio.channels.DatagramChannel javaChannel() {
        return (java.nio.channels.DatagramChannel)super.javaChannel();
    }
    
    @Override
    protected SocketAddress localAddress0() {
        return this.javaChannel().socket().getLocalSocketAddress();
    }
    
    @Override
    protected SocketAddress remoteAddress0() {
        return this.javaChannel().socket().getRemoteSocketAddress();
    }
    
    @Override
    protected void doBind(final SocketAddress localAddress) throws Exception {
        this.javaChannel().socket().bind(localAddress);
    }
    
    @Override
    protected boolean doConnect(final SocketAddress remoteAddress, final SocketAddress localAddress) throws Exception {
        if (localAddress != null) {
            this.javaChannel().socket().bind(localAddress);
        }
        boolean success = false;
        try {
            this.javaChannel().connect(remoteAddress);
            success = true;
            return true;
        }
        finally {
            if (!success) {
                this.doClose();
            }
        }
    }
    
    @Override
    protected void doFinishConnect() throws Exception {
        throw new Error();
    }
    
    @Override
    protected void doDisconnect() throws Exception {
        this.javaChannel().disconnect();
    }
    
    @Override
    protected void doClose() throws Exception {
        this.javaChannel().close();
    }
    
    @Override
    protected int doReadMessages(final MessageBuf<Object> buf) throws Exception {
        final java.nio.channels.DatagramChannel ch = this.javaChannel();
        final ByteBuf buffer = this.alloc().directBuffer(this.config().getReceivePacketSize());
        boolean free = true;
        try {
            final ByteBuffer data = buffer.nioBuffer(buffer.writerIndex(), buffer.writableBytes());
            final InetSocketAddress remoteAddress = (InetSocketAddress)ch.receive(data);
            if (remoteAddress == null) {
                return 0;
            }
            buf.add(new DatagramPacket(buffer.writerIndex(buffer.writerIndex() + data.position()), remoteAddress));
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
        final DatagramPacket packet = buf.peek();
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
        final int writtenBytes = this.javaChannel().send(nioData, packet.remoteAddress());
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
    public InetSocketAddress localAddress() {
        return (InetSocketAddress)super.localAddress();
    }
    
    @Override
    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress)super.remoteAddress();
    }
    
    @Override
    public ChannelFuture joinGroup(final InetAddress multicastAddress) {
        return this.joinGroup(multicastAddress, this.newPromise());
    }
    
    @Override
    public ChannelFuture joinGroup(final InetAddress multicastAddress, final ChannelPromise promise) {
        try {
            return this.joinGroup(multicastAddress, NetworkInterface.getByInetAddress(this.localAddress().getAddress()), null, promise);
        }
        catch (SocketException e) {
            promise.setFailure((Throwable)e);
            return promise;
        }
    }
    
    @Override
    public ChannelFuture joinGroup(final InetSocketAddress multicastAddress, final NetworkInterface networkInterface) {
        return this.joinGroup(multicastAddress, networkInterface, this.newPromise());
    }
    
    @Override
    public ChannelFuture joinGroup(final InetSocketAddress multicastAddress, final NetworkInterface networkInterface, final ChannelPromise promise) {
        return this.joinGroup(multicastAddress.getAddress(), networkInterface, null, promise);
    }
    
    @Override
    public ChannelFuture joinGroup(final InetAddress multicastAddress, final NetworkInterface networkInterface, final InetAddress source) {
        return this.joinGroup(multicastAddress, networkInterface, source, this.newPromise());
    }
    
    @Override
    public ChannelFuture joinGroup(final InetAddress multicastAddress, final NetworkInterface networkInterface, final InetAddress source, final ChannelPromise promise) {
        if (PlatformDependent.javaVersion() < 7) {
            throw new UnsupportedOperationException();
        }
        if (multicastAddress == null) {
            throw new NullPointerException("multicastAddress");
        }
        if (networkInterface == null) {
            throw new NullPointerException("networkInterface");
        }
        try {
            MembershipKey key;
            if (source == null) {
                key = this.javaChannel().join(multicastAddress, networkInterface);
            }
            else {
                key = this.javaChannel().join(multicastAddress, networkInterface, source);
            }
            synchronized (this) {
                List<MembershipKey> keys = this.memberships.get(multicastAddress);
                if (keys == null) {
                    keys = new ArrayList<MembershipKey>();
                    this.memberships.put(multicastAddress, keys);
                }
                keys.add(key);
            }
            promise.setSuccess();
        }
        catch (Throwable e) {
            promise.setFailure(e);
        }
        return promise;
    }
    
    @Override
    public ChannelFuture leaveGroup(final InetAddress multicastAddress) {
        return this.leaveGroup(multicastAddress, this.newPromise());
    }
    
    @Override
    public ChannelFuture leaveGroup(final InetAddress multicastAddress, final ChannelPromise promise) {
        try {
            return this.leaveGroup(multicastAddress, NetworkInterface.getByInetAddress(this.localAddress().getAddress()), null, promise);
        }
        catch (SocketException e) {
            promise.setFailure((Throwable)e);
            return promise;
        }
    }
    
    @Override
    public ChannelFuture leaveGroup(final InetSocketAddress multicastAddress, final NetworkInterface networkInterface) {
        return this.leaveGroup(multicastAddress, networkInterface, this.newPromise());
    }
    
    @Override
    public ChannelFuture leaveGroup(final InetSocketAddress multicastAddress, final NetworkInterface networkInterface, final ChannelPromise promise) {
        return this.leaveGroup(multicastAddress.getAddress(), networkInterface, null, promise);
    }
    
    @Override
    public ChannelFuture leaveGroup(final InetAddress multicastAddress, final NetworkInterface networkInterface, final InetAddress source) {
        return this.leaveGroup(multicastAddress, networkInterface, source, this.newPromise());
    }
    
    @Override
    public ChannelFuture leaveGroup(final InetAddress multicastAddress, final NetworkInterface networkInterface, final InetAddress source, final ChannelPromise promise) {
        if (PlatformDependent.javaVersion() < 7) {
            throw new UnsupportedOperationException();
        }
        if (multicastAddress == null) {
            throw new NullPointerException("multicastAddress");
        }
        if (networkInterface == null) {
            throw new NullPointerException("networkInterface");
        }
        synchronized (this) {
            if (this.memberships != null) {
                final List<MembershipKey> keys = this.memberships.get(multicastAddress);
                if (keys != null) {
                    final Iterator<MembershipKey> keyIt = keys.iterator();
                    while (keyIt.hasNext()) {
                        final MembershipKey key = keyIt.next();
                        if (networkInterface.equals(key.networkInterface()) && ((source == null && key.sourceAddress() == null) || (source != null && source.equals(key.sourceAddress())))) {
                            key.drop();
                            keyIt.remove();
                        }
                    }
                    if (keys.isEmpty()) {
                        this.memberships.remove(multicastAddress);
                    }
                }
            }
        }
        promise.setSuccess();
        return promise;
    }
    
    @Override
    public ChannelFuture block(final InetAddress multicastAddress, final NetworkInterface networkInterface, final InetAddress sourceToBlock) {
        return this.block(multicastAddress, networkInterface, sourceToBlock, this.newPromise());
    }
    
    @Override
    public ChannelFuture block(final InetAddress multicastAddress, final NetworkInterface networkInterface, final InetAddress sourceToBlock, final ChannelPromise promise) {
        if (PlatformDependent.javaVersion() < 7) {
            throw new UnsupportedOperationException();
        }
        if (multicastAddress == null) {
            throw new NullPointerException("multicastAddress");
        }
        if (sourceToBlock == null) {
            throw new NullPointerException("sourceToBlock");
        }
        if (networkInterface == null) {
            throw new NullPointerException("networkInterface");
        }
        synchronized (this) {
            if (this.memberships != null) {
                final List<MembershipKey> keys = this.memberships.get(multicastAddress);
                for (final MembershipKey key : keys) {
                    if (networkInterface.equals(key.networkInterface())) {
                        try {
                            key.block(sourceToBlock);
                        }
                        catch (IOException e) {
                            promise.setFailure((Throwable)e);
                        }
                    }
                }
            }
        }
        promise.setSuccess();
        return promise;
    }
    
    @Override
    public ChannelFuture block(final InetAddress multicastAddress, final InetAddress sourceToBlock) {
        return this.block(multicastAddress, sourceToBlock, this.newPromise());
    }
    
    @Override
    public ChannelFuture block(final InetAddress multicastAddress, final InetAddress sourceToBlock, final ChannelPromise promise) {
        try {
            return this.block(multicastAddress, NetworkInterface.getByInetAddress(this.localAddress().getAddress()), sourceToBlock, promise);
        }
        catch (SocketException e) {
            promise.setFailure((Throwable)e);
            return promise;
        }
    }
    
    static {
        METADATA = new ChannelMetadata(BufType.MESSAGE, true);
    }
}
