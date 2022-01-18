// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.InputStream;
import java.util.Iterator;
import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.net.SocketException;
import java.util.Collections;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.security.PrivateKey;
import javax.crypto.SecretKey;
import java.util.List;
import java.util.Queue;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.net.SocketAddress;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class NetworkManager implements INetworkManager
{
    public static AtomicInteger a;
    public static AtomicInteger b;
    private final Object h;
    private final IConsoleLogManager i;
    public Socket socket;
    private SocketAddress k;
    private volatile DataInputStream input;
    private volatile DataOutputStream output;
    private volatile boolean n;
    private volatile boolean o;
    private Queue inboundQueue;
    private List highPriorityQueue;
    private List lowPriorityQueue;
    private Connection connection;
    private boolean t;
    private Thread u;
    private Thread v;
    private String w;
    private Object[] x;
    private int y;
    private int z;
    public static int[] c;
    public static int[] d;
    public int e;
    boolean f;
    boolean g;
    private SecretKey A;
    private PrivateKey B;
    private int lowPriorityQueueDelay;
    
    public NetworkManager(final IConsoleLogManager iconsolelogmanager, final Socket socket, final String s, final Connection connection, final PrivateKey privatekey) throws IOException {
        this.h = new Object();
        this.n = true;
        this.o = false;
        this.inboundQueue = new ConcurrentLinkedQueue();
        this.highPriorityQueue = Collections.synchronizedList(new ArrayList<Object>());
        this.lowPriorityQueue = Collections.synchronizedList(new ArrayList<Object>());
        this.t = false;
        this.w = "";
        this.y = 0;
        this.z = 0;
        this.e = 0;
        this.f = false;
        this.g = false;
        this.A = null;
        this.B = null;
        this.lowPriorityQueueDelay = 50;
        this.B = privatekey;
        this.socket = socket;
        this.i = iconsolelogmanager;
        this.k = socket.getRemoteSocketAddress();
        this.connection = connection;
        try {
            socket.setSoTimeout(30000);
            socket.setTrafficClass(24);
        }
        catch (SocketException socketexception) {
            System.err.println(socketexception.getMessage());
        }
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream(), 5120));
        this.v = new NetworkReaderThread(this, s + " read thread");
        this.u = new NetworkWriterThread(this, s + " write thread");
        this.v.start();
        this.u.start();
    }
    
    public void a(final Connection connection) {
        this.connection = connection;
    }
    
    public void queue(final Packet packet) {
        if (!this.t) {
            final Object object = this.h;
            synchronized (this.h) {
                this.z += packet.a() + 1;
                this.highPriorityQueue.add(packet);
            }
        }
    }
    
    private boolean h() {
        boolean flag = false;
        try {
            if (this.e == 0 || (!this.highPriorityQueue.isEmpty() && System.currentTimeMillis() - this.highPriorityQueue.get(0).timestamp >= this.e)) {
                final Packet packet = this.a(false);
                if (packet != null) {
                    Packet.a(packet, this.output);
                    if (packet instanceof Packet252KeyResponse && !this.g) {
                        if (!this.connection.a()) {
                            this.A = ((Packet252KeyResponse)packet).d();
                        }
                        this.k();
                    }
                    final int[] aint = NetworkManager.d;
                    final int i = packet.n();
                    final int[] array = aint;
                    final int n = i;
                    array[n] += packet.a() + 1;
                    flag = true;
                }
            }
            if ((flag || this.lowPriorityQueueDelay-- <= 0) && !this.lowPriorityQueue.isEmpty() && (this.highPriorityQueue.isEmpty() || this.highPriorityQueue.get(0).timestamp > this.lowPriorityQueue.get(0).timestamp)) {
                final Packet packet = this.a(true);
                if (packet != null) {
                    Packet.a(packet, this.output);
                    final int[] aint = NetworkManager.d;
                    final int i = packet.n();
                    final int[] array2 = aint;
                    final int n2 = i;
                    array2[n2] += packet.a() + 1;
                    this.lowPriorityQueueDelay = 0;
                    flag = true;
                }
            }
            return flag;
        }
        catch (Exception exception) {
            if (!this.o) {
                this.a(exception);
            }
            return false;
        }
    }
    
    private Packet a(final boolean flag) {
        Packet packet = null;
        final List list = flag ? this.lowPriorityQueue : this.highPriorityQueue;
        final Object object = this.h;
        synchronized (this.h) {
            while (!list.isEmpty() && packet == null) {
                packet = list.remove(0);
                this.z -= packet.a() + 1;
                if (this.a(packet, flag)) {
                    packet = null;
                }
            }
            return packet;
        }
    }
    
    private boolean a(final Packet packet, final boolean flag) {
        if (!packet.e()) {
            return false;
        }
        final List list = flag ? this.lowPriorityQueue : this.highPriorityQueue;
        for (final Packet packet2 : list) {
            if (packet2.n() == packet.n()) {
                return packet.a(packet2);
            }
        }
        return false;
    }
    
    public void a() {
        if (this.v != null) {
            this.v.interrupt();
        }
        if (this.u != null) {
            this.u.interrupt();
        }
    }
    
    private boolean i() {
        boolean flag = false;
        try {
            final Packet packet = Packet.a(this.i, this.input, this.connection.a(), this.socket);
            if (packet != null) {
                if (packet instanceof Packet252KeyResponse && !this.f) {
                    if (this.connection.a()) {
                        this.A = ((Packet252KeyResponse)packet).a(this.B);
                    }
                    this.j();
                }
                final int[] aint = NetworkManager.c;
                final int i = packet.n();
                final int[] array = aint;
                final int n = i;
                array[n] += packet.a() + 1;
                if (!this.t) {
                    if (packet.a_() && this.connection.b()) {
                        this.y = 0;
                        packet.handle(this.connection);
                    }
                    else {
                        this.inboundQueue.add(packet);
                    }
                }
                flag = true;
            }
            else {
                this.a("disconnect.endOfStream", new Object[0]);
            }
            return flag;
        }
        catch (Exception exception) {
            if (!this.o) {
                this.a(exception);
            }
            return false;
        }
    }
    
    private void a(final Exception exception) {
        this.a("disconnect.genericReason", "Internal exception: " + exception.toString());
    }
    
    public void a(final String s, final Object... aobject) {
        if (this.n) {
            this.o = true;
            this.w = s;
            this.x = aobject;
            this.n = false;
            new NetworkMasterThread(this).start();
            try {
                this.input.close();
            }
            catch (Throwable t) {}
            try {
                this.output.close();
            }
            catch (Throwable t2) {}
            try {
                this.socket.close();
            }
            catch (Throwable t3) {}
            this.input = null;
            this.output = null;
            this.socket = null;
        }
    }
    
    public void b() {
        if (this.z > 2097152) {
            this.a("disconnect.overflow", new Object[0]);
        }
        if (this.inboundQueue.isEmpty()) {
            if (this.y++ == 1200) {
                this.a("disconnect.timeout", new Object[0]);
            }
        }
        else {
            this.y = 0;
        }
        int i = 1000;
        while (!this.inboundQueue.isEmpty() && i-- >= 0) {
            final Packet packet = this.inboundQueue.poll();
            if (this.connection instanceof PendingConnection) {
                if (((PendingConnection)this.connection).b) {
                    continue;
                }
            }
            else if (((PlayerConnection)this.connection).disconnected) {
                continue;
            }
            packet.handle(this.connection);
        }
        this.a();
        if (this.o && this.inboundQueue.isEmpty()) {
            this.connection.a(this.w, this.x);
        }
    }
    
    public SocketAddress getSocketAddress() {
        return this.k;
    }
    
    public void d() {
        if (!this.t) {
            this.a();
            this.t = true;
            this.v.interrupt();
            new NetworkMonitorThread(this).start();
        }
    }
    
    private void j() throws IOException {
        this.f = true;
        final InputStream inputstream = this.socket.getInputStream();
        this.input = new DataInputStream(MinecraftEncryption.a(this.A, inputstream));
    }
    
    private void k() throws IOException {
        this.output.flush();
        this.g = true;
        final BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(MinecraftEncryption.a(this.A, this.socket.getOutputStream()), 5120);
        this.output = new DataOutputStream(bufferedoutputstream);
    }
    
    public int e() {
        return this.lowPriorityQueue.size();
    }
    
    public Socket getSocket() {
        return this.socket;
    }
    
    static boolean a(final NetworkManager networkmanager) {
        return networkmanager.n;
    }
    
    static boolean b(final NetworkManager networkmanager) {
        return networkmanager.t;
    }
    
    static boolean c(final NetworkManager networkmanager) {
        return networkmanager.i();
    }
    
    static boolean d(final NetworkManager networkmanager) {
        return networkmanager.h();
    }
    
    static DataOutputStream e(final NetworkManager networkmanager) {
        return networkmanager.output;
    }
    
    static boolean f(final NetworkManager networkmanager) {
        return networkmanager.o;
    }
    
    static void a(final NetworkManager networkmanager, final Exception exception) {
        networkmanager.a(exception);
    }
    
    static Thread g(final NetworkManager networkmanager) {
        return networkmanager.v;
    }
    
    static Thread h(final NetworkManager networkmanager) {
        return networkmanager.u;
    }
    
    public void setSocketAddress(final SocketAddress address) {
        this.k = address;
    }
    
    static {
        NetworkManager.a = new AtomicInteger();
        NetworkManager.b = new AtomicInteger();
        NetworkManager.c = new int[256];
        NetworkManager.d = new int[256];
    }
}
