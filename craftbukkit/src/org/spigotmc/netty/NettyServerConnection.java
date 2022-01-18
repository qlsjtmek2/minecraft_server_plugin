// 
// Decompiled by Procyon v0.5.30
// 

package org.spigotmc.netty;

import io.netty.buffer.Buf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.MessageBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandler;
import io.netty.channel.ChannelStateHandlerAdapter;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.SocketAddress;
import io.netty.channel.ChannelPipeline;
import java.util.Iterator;
import java.util.LinkedHashMap;
import io.netty.util.AttributeKey;
import java.util.Map;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.channel.ServerChannel;
import io.netty.bootstrap.AbstractBootstrap;
import java.security.GeneralSecurityException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.Cipher;
import java.security.Key;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import java.net.InetSocketAddress;
import org.spigotmc.MultiplexingServerConnection;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.bootstrap.ServerBootstrap;
import java.net.InetAddress;
import net.minecraft.server.v1_5_R3.MinecraftServer;
import io.netty.channel.ChannelFuture;
import net.minecraft.server.v1_5_R3.ServerConnection;

public class NettyServerConnection extends ServerConnection
{
    private final ChannelFuture socket;
    
    public NettyServerConnection(final MinecraftServer ms, final InetAddress host, final int port) {
        super(ms);
        final int threads = Integer.getInteger("org.spigotmc.netty.threads", 3);
        this.socket = ((AbstractBootstrap<ServerBootstrap, C>)((AbstractBootstrap<ServerBootstrap, Channel>)new ServerBootstrap()).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer() {
            public void initChannel(final Channel ch) throws Exception {
                if (((MultiplexingServerConnection)ms.ae()).throttle(((InetSocketAddress)ch.remoteAddress()).getAddress())) {
                    ch.close();
                    return;
                }
                try {
                    ch.config().setOption(ChannelOption.IP_TOS, 24);
                }
                catch (ChannelException ex) {}
                final NettyNetworkManager networkManager = new NettyNetworkManager();
                ch.pipeline().addLast("flusher", new OutboundManager(networkManager)).addLast("timer", new ReadTimeoutHandler(30)).addLast("decoder", new PacketDecoder()).addLast("encoder", new PacketEncoder(networkManager)).addLast("manager", networkManager);
            }
        }).childOption(ChannelOption.TCP_NODELAY, false).group(new NioEventLoopGroup(threads, new ThreadFactoryBuilder().setNameFormat("Netty IO Thread - %1$d").build()))).localAddress(host, port).bind();
        MinecraftServer.getServer().getLogger().info("Using Netty NIO with " + threads + " threads for network connections.");
    }
    
    public void a() {
        this.socket.channel().close().syncUninterruptibly();
    }
    
    public static Cipher getCipher(final int opMode, final Key key) {
        try {
            final Cipher cip = Cipher.getInstance("AES/CFB8/NoPadding");
            cip.init(opMode, key, new IvParameterSpec(key.getEncoded()));
            return cip;
        }
        catch (GeneralSecurityException ex) {
            throw new RuntimeException(ex);
        }
    }
}
