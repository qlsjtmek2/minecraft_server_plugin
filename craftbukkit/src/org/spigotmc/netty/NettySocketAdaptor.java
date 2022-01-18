// 
// Decompiled by Procyon v0.5.30
// 

package org.spigotmc.netty;

import java.io.OutputStream;
import java.net.SocketException;
import io.netty.channel.ChannelOption;
import java.io.InputStream;
import java.net.InetAddress;
import java.io.IOException;
import java.net.SocketAddress;
import io.netty.channel.socket.SocketChannel;
import java.net.Socket;

public class NettySocketAdaptor extends Socket
{
    private final SocketChannel ch;
    
    private NettySocketAdaptor(final SocketChannel ch) {
        this.ch = ch;
    }
    
    public static NettySocketAdaptor adapt(final SocketChannel ch) {
        return new NettySocketAdaptor(ch);
    }
    
    public void bind(final SocketAddress bindpoint) throws IOException {
        this.ch.bind(bindpoint).syncUninterruptibly();
    }
    
    public synchronized void close() throws IOException {
        this.ch.close().syncUninterruptibly();
    }
    
    public void connect(final SocketAddress endpoint) throws IOException {
        this.ch.connect(endpoint).syncUninterruptibly();
    }
    
    public void connect(final SocketAddress endpoint, final int timeout) throws IOException {
        this.ch.config().setConnectTimeoutMillis(timeout);
        this.ch.connect(endpoint).syncUninterruptibly();
    }
    
    public boolean equals(final Object obj) {
        return obj instanceof NettySocketAdaptor && this.ch.equals(((NettySocketAdaptor)obj).ch);
    }
    
    public java.nio.channels.SocketChannel getChannel() {
        throw new UnsupportedOperationException("Operation not supported on Channel wrapper.");
    }
    
    public InetAddress getInetAddress() {
        return this.ch.remoteAddress().getAddress();
    }
    
    public InputStream getInputStream() throws IOException {
        throw new UnsupportedOperationException("Operation not supported on Channel wrapper.");
    }
    
    public boolean getKeepAlive() throws SocketException {
        return this.ch.config().getOption(ChannelOption.SO_KEEPALIVE);
    }
    
    public InetAddress getLocalAddress() {
        return this.ch.localAddress().getAddress();
    }
    
    public int getLocalPort() {
        return this.ch.localAddress().getPort();
    }
    
    public SocketAddress getLocalSocketAddress() {
        return this.ch.localAddress();
    }
    
    public boolean getOOBInline() throws SocketException {
        throw new UnsupportedOperationException("Operation not supported on Channel wrapper.");
    }
    
    public OutputStream getOutputStream() throws IOException {
        throw new UnsupportedOperationException("Operation not supported on Channel wrapper.");
    }
    
    public int getPort() {
        return this.ch.remoteAddress().getPort();
    }
    
    public synchronized int getReceiveBufferSize() throws SocketException {
        return this.ch.config().getOption(ChannelOption.SO_RCVBUF);
    }
    
    public SocketAddress getRemoteSocketAddress() {
        return this.ch.remoteAddress();
    }
    
    public boolean getReuseAddress() throws SocketException {
        return this.ch.config().getOption(ChannelOption.SO_REUSEADDR);
    }
    
    public synchronized int getSendBufferSize() throws SocketException {
        return this.ch.config().getOption(ChannelOption.SO_SNDBUF);
    }
    
    public int getSoLinger() throws SocketException {
        return this.ch.config().getOption(ChannelOption.SO_LINGER);
    }
    
    public synchronized int getSoTimeout() throws SocketException {
        throw new UnsupportedOperationException("Operation not supported on Channel wrapper.");
    }
    
    public boolean getTcpNoDelay() throws SocketException {
        return this.ch.config().getOption(ChannelOption.TCP_NODELAY);
    }
    
    public int getTrafficClass() throws SocketException {
        return this.ch.config().getOption(ChannelOption.IP_TOS);
    }
    
    public int hashCode() {
        return this.ch.hashCode();
    }
    
    public boolean isBound() {
        return this.ch.localAddress() != null;
    }
    
    public boolean isClosed() {
        return !this.ch.isOpen();
    }
    
    public boolean isConnected() {
        return this.ch.isActive();
    }
    
    public boolean isInputShutdown() {
        return this.ch.isInputShutdown();
    }
    
    public boolean isOutputShutdown() {
        return this.ch.isOutputShutdown();
    }
    
    public void sendUrgentData(final int data) throws IOException {
        throw new UnsupportedOperationException("Operation not supported on Channel wrapper.");
    }
    
    public void setKeepAlive(final boolean on) throws SocketException {
        this.ch.config().setOption(ChannelOption.SO_KEEPALIVE, on);
    }
    
    public void setOOBInline(final boolean on) throws SocketException {
        throw new UnsupportedOperationException("Operation not supported on Channel wrapper.");
    }
    
    public void setPerformancePreferences(final int connectionTime, final int latency, final int bandwidth) {
        throw new UnsupportedOperationException("Operation not supported on Channel wrapper.");
    }
    
    public synchronized void setReceiveBufferSize(final int size) throws SocketException {
        this.ch.config().setOption(ChannelOption.SO_RCVBUF, size);
    }
    
    public void setReuseAddress(final boolean on) throws SocketException {
        this.ch.config().setOption(ChannelOption.SO_REUSEADDR, on);
    }
    
    public synchronized void setSendBufferSize(final int size) throws SocketException {
        this.ch.config().setOption(ChannelOption.SO_SNDBUF, size);
    }
    
    public void setSoLinger(final boolean on, final int linger) throws SocketException {
        this.ch.config().setOption(ChannelOption.SO_LINGER, linger);
    }
    
    public synchronized void setSoTimeout(final int timeout) throws SocketException {
        throw new UnsupportedOperationException("Operation not supported on Channel wrapper.");
    }
    
    public void setTcpNoDelay(final boolean on) throws SocketException {
        this.ch.config().setOption(ChannelOption.TCP_NODELAY, on);
    }
    
    public void setTrafficClass(final int tc) throws SocketException {
        this.ch.config().setOption(ChannelOption.IP_TOS, tc);
    }
    
    public void shutdownInput() throws IOException {
        throw new UnsupportedOperationException("Operation not supported on Channel wrapper.");
    }
    
    public void shutdownOutput() throws IOException {
        this.ch.shutdownOutput().syncUninterruptibly();
    }
    
    public String toString() {
        return this.ch.toString();
    }
}
