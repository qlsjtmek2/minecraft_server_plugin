// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import com.google.common.io.ByteArrayDataInput;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import com.google.common.io.ByteStreams;
import org.bukkit.craftbukkit.v1_5_R3.Spigot;
import java.net.InetAddress;
import org.bukkit.event.server.ServerListPingEvent;
import org.spigotmc.MultiplexingServerConnection;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import java.security.PrivateKey;
import java.util.Arrays;
import java.security.PublicKey;
import java.io.IOException;
import java.net.Socket;
import org.spigotmc.netty.NettyNetworkManager;
import javax.crypto.SecretKey;
import java.util.Random;

public class PendingConnection extends Connection
{
    private static Random random;
    private byte[] d;
    private final MinecraftServer server;
    public final INetworkManager networkManager;
    public boolean b;
    private int f;
    private String g;
    private volatile boolean h;
    private String loginKey;
    private boolean j;
    private SecretKey k;
    public String hostname;
    
    public PendingConnection(final MinecraftServer minecraftserver, final NettyNetworkManager networkManager) {
        this.b = false;
        this.f = 0;
        this.g = null;
        this.h = false;
        this.loginKey = Long.toString(PendingConnection.random.nextLong(), 16);
        this.j = false;
        this.k = null;
        this.hostname = "";
        this.server = minecraftserver;
        this.networkManager = networkManager;
    }
    
    public PendingConnection(final MinecraftServer minecraftserver, final Socket socket, final String s) throws IOException {
        this.b = false;
        this.f = 0;
        this.g = null;
        this.h = false;
        this.loginKey = Long.toString(PendingConnection.random.nextLong(), 16);
        this.j = false;
        this.k = null;
        this.hostname = "";
        this.server = minecraftserver;
        this.networkManager = new NetworkManager(minecraftserver.getLogger(), socket, s, this, minecraftserver.F().getPrivate());
    }
    
    public Socket getSocket() {
        return this.networkManager.getSocket();
    }
    
    public void c() {
        if (this.h) {
            this.d();
        }
        if (this.f++ == 600) {
            this.disconnect("Took too long to log in");
        }
        else {
            this.networkManager.b();
        }
    }
    
    public void disconnect(final String s) {
        try {
            this.server.getLogger().info("Disconnecting " + this.getName() + ": " + s);
            this.networkManager.queue(new Packet255KickDisconnect(s));
            this.networkManager.d();
            this.b = true;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    
    public void a(final Packet2Handshake packet2handshake) {
        this.hostname = ((packet2handshake.c == null) ? "" : (packet2handshake.c + ':' + packet2handshake.d));
        this.g = packet2handshake.f();
        if (!this.g.equals(StripColor.a(this.g))) {
            this.disconnect("Invalid username!");
        }
        else {
            final PublicKey publickey = this.server.F().getPublic();
            if (packet2handshake.d() != 61) {
                if (packet2handshake.d() > 61) {
                    this.disconnect("Outdated server!");
                }
                else {
                    this.disconnect("Outdated client!");
                }
            }
            else {
                this.loginKey = (this.server.getOnlineMode() ? Long.toString(PendingConnection.random.nextLong(), 16) : "-");
                this.d = new byte[4];
                PendingConnection.random.nextBytes(this.d);
                this.networkManager.queue(new Packet253KeyRequest(this.loginKey, publickey, this.d));
            }
        }
    }
    
    public void a(final Packet252KeyResponse packet252keyresponse) {
        final PrivateKey privatekey = this.server.F().getPrivate();
        this.k = packet252keyresponse.a(privatekey);
        if (!Arrays.equals(this.d, packet252keyresponse.b(privatekey))) {
            this.disconnect("Invalid client reply");
        }
        this.networkManager.queue(new Packet252KeyResponse());
    }
    
    public void a(final Packet205ClientCommand packet205clientcommand) {
        if (packet205clientcommand.a == 0) {
            if (this.server.getOnlineMode()) {
                if (this.j) {
                    this.disconnect("Duplicate login");
                    return;
                }
                this.j = true;
                new ThreadLoginVerifier(this, this.server.server).start();
            }
            else {
                this.h = true;
            }
        }
    }
    
    public void a(final Packet1Login packet1login) {
    }
    
    public void d() {
        final EntityPlayer s = this.server.getPlayerList().attemptLogin(this, this.g, this.hostname);
        if (s == null) {
            return;
        }
        final EntityPlayer entityplayer = this.server.getPlayerList().processLogin(s);
        if (entityplayer != null) {
            this.server.getPlayerList().a(this.networkManager, entityplayer);
        }
        this.b = true;
    }
    
    public void a(final String s, final Object[] aobject) {
        this.server.getLogger().info(this.getName() + " lost connection");
        this.b = true;
    }
    
    public void a(final Packet254GetInfo packet254getinfo) {
        if (this.networkManager.getSocket() == null) {
            return;
        }
        try {
            final PlayerList playerlist = this.server.getPlayerList();
            String s = null;
            final ServerListPingEvent pingEvent = CraftEventFactory.callServerListPingEvent(this.server.server, this.getSocket().getInetAddress(), this.server.getMotd(), playerlist.getPlayerCount(), playerlist.getMaxPlayers());
            final Object[] arr$;
            final Object[] list = arr$ = new Object[] { 1, 61, this.server.getVersion(), pingEvent.getMotd(), playerlist.getPlayerCount(), pingEvent.getMaxPlayers() };
            for (final Object object : arr$) {
                if (s == null) {
                    s = "§";
                }
                else {
                    s += "\u0000";
                }
                s += StringUtils.replace(object.toString(), "\u0000", "");
            }
            InetAddress inetaddress = null;
            if (this.networkManager.getSocket() != null) {
                inetaddress = this.networkManager.getSocket().getInetAddress();
            }
            this.networkManager.queue(new Packet255KickDisconnect(s));
            this.networkManager.d();
            if (inetaddress != null) {
                ((MultiplexingServerConnection)this.server.ae()).unThrottle(inetaddress);
            }
            this.b = true;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    
    public void onUnhandledPacket(final Packet packet) {
        this.disconnect("Protocol error");
    }
    
    public String getName() {
        return (this.g != null) ? (this.g + " [" + this.networkManager.getSocketAddress().toString() + "]") : this.networkManager.getSocketAddress().toString();
    }
    
    public boolean a() {
        return true;
    }
    
    static String a(final PendingConnection pendingconnection) {
        return pendingconnection.loginKey;
    }
    
    static MinecraftServer b(final PendingConnection pendingconnection) {
        return pendingconnection.server;
    }
    
    static SecretKey c(final PendingConnection pendingconnection) {
        return pendingconnection.k;
    }
    
    static String d(final PendingConnection pendingconnection) {
        return pendingconnection.g;
    }
    
    static boolean a(final PendingConnection pendingconnection, final boolean flag) {
        return pendingconnection.h = flag;
    }
    
    public void a(final Packet250CustomPayload pcp) {
        if (pcp.tag.equals("BungeeCord") && Spigot.bungeeIPs.contains(this.getSocket().getInetAddress().getHostAddress())) {
            final ByteArrayDataInput in = ByteStreams.newDataInput(pcp.data);
            final String subTag = in.readUTF();
            if (subTag.equals("Login")) {
                this.networkManager.setSocketAddress(new InetSocketAddress(in.readUTF(), in.readInt()));
            }
        }
    }
    
    static {
        PendingConnection.random = new Random();
    }
}
