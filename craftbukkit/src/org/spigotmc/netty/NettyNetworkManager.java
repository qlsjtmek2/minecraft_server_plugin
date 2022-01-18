// 
// Decompiled by Procyon v0.5.30
// 

package org.spigotmc.netty;

import java.util.concurrent.Executors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import net.minecraft.server.v1_5_R3.PlayerConnection;
import io.netty.channel.ChannelPromise;
import net.minecraft.server.v1_5_R3.Packet255KickDisconnect;
import javax.crypto.Cipher;
import io.netty.channel.ChannelHandler;
import java.security.Key;
import net.minecraft.server.v1_5_R3.Packet252KeyResponse;
import net.minecraft.server.v1_5_R3.PendingConnection;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.ChannelHandlerContext;
import java.util.AbstractList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.net.Socket;
import javax.crypto.SecretKey;
import net.minecraft.server.v1_5_R3.Connection;
import java.net.SocketAddress;
import io.netty.channel.Channel;
import java.util.List;
import java.util.Queue;
import org.spigotmc.MultiplexingServerConnection;
import java.security.PrivateKey;
import net.minecraft.server.v1_5_R3.MinecraftServer;
import java.util.concurrent.ExecutorService;
import net.minecraft.server.v1_5_R3.INetworkManager;
import net.minecraft.server.v1_5_R3.Packet;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;

public class NettyNetworkManager extends ChannelInboundMessageHandlerAdapter<Packet> implements INetworkManager
{
    private static final ExecutorService threadPool;
    private static final MinecraftServer server;
    private static final PrivateKey key;
    private static final MultiplexingServerConnection serverConnection;
    private final Queue<Packet> syncPackets;
    private final List<Packet> highPriorityQueue;
    private volatile boolean connected;
    private Channel channel;
    private SocketAddress address;
    Connection connection;
    private SecretKey secret;
    private String dcReason;
    private Object[] dcArgs;
    private Socket socketAdaptor;
    private long writtenBytes;
    
    public NettyNetworkManager() {
        this.syncPackets = new ConcurrentLinkedQueue<Packet>();
        this.highPriorityQueue = new AbstractList<Packet>() {
            public void add(final int index, final Packet element) {
            }
            
            public Packet get(final int index) {
                throw new UnsupportedOperationException();
            }
            
            public int size() {
                return 0;
            }
        };
    }
    
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        this.channel = ctx.channel();
        this.address = this.channel.remoteAddress();
        this.socketAdaptor = NettySocketAdaptor.adapt((SocketChannel)this.channel);
        this.connection = new PendingConnection(NettyNetworkManager.server, this);
        this.connected = true;
        NettyNetworkManager.serverConnection.register((PendingConnection)this.connection);
    }
    
    public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
        this.a("disconnect.endOfStream", new Object[0]);
    }
    
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
        this.a("disconnect.genericReason", "Internal exception: " + cause);
    }
    
    public void messageReceived(final ChannelHandlerContext ctx, final Packet msg) throws Exception {
        if (this.connected) {
            if (msg instanceof Packet252KeyResponse) {
                this.secret = ((Packet252KeyResponse)msg).a(NettyNetworkManager.key);
                final Cipher decrypt = NettyServerConnection.getCipher(2, this.secret);
                this.channel.pipeline().addBefore("decoder", "decrypt", new CipherDecoder(decrypt));
            }
            if (msg.a_()) {
                NettyNetworkManager.threadPool.submit(new Runnable() {
                    public void run() {
                        final Packet packet = PacketListener.callReceived(NettyNetworkManager.this, NettyNetworkManager.this.connection, msg);
                        if (packet != null) {
                            packet.handle(NettyNetworkManager.this.connection);
                        }
                    }
                });
            }
            else {
                this.syncPackets.add(msg);
            }
        }
    }
    
    public Socket getSocket() {
        return this.socketAdaptor;
    }
    
    public void a(final Connection nh) {
        this.connection = nh;
    }
    
    public void queue(Packet packet) {
        if (this.connected) {
            packet = PacketListener.callQueued(this, this.connection, packet);
            if (packet != null) {
                this.highPriorityQueue.add(packet);
                final ChannelPromise promise = this.channel.newPromise();
                if (packet instanceof Packet255KickDisconnect) {
                    this.channel.pipeline().get(OutboundManager.class).lastFlush = 0L;
                }
                this.channel.write(packet, promise);
                if (packet instanceof Packet252KeyResponse) {
                    final Cipher encrypt = NettyServerConnection.getCipher(1, this.secret);
                    this.channel.pipeline().addBefore("decoder", "encrypt", new CipherEncoder(encrypt));
                }
            }
        }
    }
    
    public void a() {
    }
    
    public void b() {
        int i = 1000;
        while (!this.syncPackets.isEmpty() && i >= 0) {
            Label_0059: {
                if (this.connection instanceof PendingConnection) {
                    if (((PendingConnection)this.connection).b) {
                        break Label_0059;
                    }
                }
                else if (((PlayerConnection)this.connection).disconnected) {
                    break Label_0059;
                }
                final Packet packet = PacketListener.callReceived(this, this.connection, this.syncPackets.poll());
                if (packet != null) {
                    packet.handle(this.connection);
                }
                --i;
                continue;
            }
            this.syncPackets.clear();
            break;
        }
        if (!this.connected && (this.dcReason != null || this.dcArgs != null)) {
            this.connection.a(this.dcReason, this.dcArgs);
        }
    }
    
    public SocketAddress getSocketAddress() {
        return this.address;
    }
    
    public void setSocketAddress(final SocketAddress address) {
        this.address = address;
    }
    
    public void d() {
        if (this.connected) {
            this.connected = false;
            this.channel.close();
        }
    }
    
    public int e() {
        return 0;
    }
    
    public void a(final String reason, final Object... arguments) {
        if (this.connected) {
            this.dcReason = reason;
            this.dcArgs = arguments;
            this.d();
        }
    }
    
    public long getWrittenBytes() {
        return this.writtenBytes;
    }
    
    public void addWrittenBytes(final int written) {
        this.writtenBytes += written;
    }
    
    static {
        threadPool = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat("Async Packet Handler - %1$d").build());
        server = MinecraftServer.getServer();
        key = NettyNetworkManager.server.F().getPrivate();
        serverConnection = (MultiplexingServerConnection)NettyNetworkManager.server.ae();
    }
}
