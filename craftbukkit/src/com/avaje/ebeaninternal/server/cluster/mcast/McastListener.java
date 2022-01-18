// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster.mcast;

import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.cluster.PacketTransactionEvent;
import java.net.SocketTimeoutException;
import java.io.DataInput;
import com.avaje.ebeaninternal.server.cluster.Packet;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.util.logging.Level;
import com.avaje.ebean.config.GlobalProperties;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Logger;

public class McastListener implements Runnable
{
    private static final Logger logger;
    private final McastClusterManager owner;
    private final McastPacketControl packetControl;
    private final MulticastSocket sock;
    private final Thread listenerThread;
    private final String localSenderHostPort;
    private final InetAddress group;
    private final boolean debugIgnore;
    private DatagramPacket pack;
    private byte[] receiveBuffer;
    private volatile boolean shutdown;
    private volatile boolean shutdownComplete;
    private long totalPacketsReceived;
    private long totalBytesReceived;
    private long totalTxnEventsReceived;
    
    public McastListener(final McastClusterManager owner, final McastPacketControl packetControl, final int port, final String address, final int bufferSize, final int timeout, final String localSenderHostPort, final boolean disableLoopback, final int ttl, final InetAddress mcastBindAddress) {
        this.debugIgnore = GlobalProperties.getBoolean("ebean.debug.mcast.ignore", false);
        this.owner = owner;
        this.packetControl = packetControl;
        this.localSenderHostPort = localSenderHostPort;
        this.receiveBuffer = new byte[bufferSize];
        this.listenerThread = new Thread(this, "EbeanClusterMcastListener");
        String msg = "Cluster Multicast Listening address[" + address + "] port[" + port + "] disableLoopback[" + disableLoopback + "]";
        if (ttl >= 0) {
            msg = msg + " ttl[" + ttl + "]";
        }
        if (mcastBindAddress != null) {
            msg = msg + " mcastBindAddress[" + mcastBindAddress + "]";
        }
        McastListener.logger.info(msg);
        try {
            this.group = InetAddress.getByName(address);
            (this.sock = new MulticastSocket(port)).setSoTimeout(timeout);
            if (disableLoopback) {
                this.sock.setLoopbackMode(disableLoopback);
            }
            if (mcastBindAddress != null) {
                this.sock.setInterface(mcastBindAddress);
            }
            if (ttl >= 0) {
                this.sock.setTimeToLive(ttl);
            }
            this.sock.setReuseAddress(true);
            this.pack = new DatagramPacket(this.receiveBuffer, this.receiveBuffer.length);
            this.sock.joinGroup(this.group);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public void startListening() {
        this.listenerThread.setDaemon(true);
        this.listenerThread.start();
        McastListener.logger.info("Cluster Multicast Listener up and joined Group");
    }
    
    public void shutdown() {
        this.shutdown = true;
        synchronized (this.listenerThread) {
            try {
                this.listenerThread.wait(20000L);
            }
            catch (InterruptedException e) {
                McastListener.logger.info("InterruptedException:" + e);
            }
        }
        if (!this.shutdownComplete) {
            final String msg = "WARNING: Shutdown of McastListener did not complete?";
            System.err.println(msg);
            McastListener.logger.warning(msg);
        }
        try {
            this.sock.leaveGroup(this.group);
        }
        catch (IOException e2) {
            e2.printStackTrace();
            final String msg2 = "Error leaving Multicast group";
            McastListener.logger.log(Level.INFO, msg2, e2);
        }
        try {
            this.sock.close();
        }
        catch (Exception e3) {
            e3.printStackTrace();
            final String msg2 = "Error closing Multicast socket";
            McastListener.logger.log(Level.INFO, msg2, e3);
        }
    }
    
    public void run() {
        while (!this.shutdown) {
            try {
                this.pack.setLength(this.receiveBuffer.length);
                this.sock.receive(this.pack);
                final InetSocketAddress senderAddr = (InetSocketAddress)this.pack.getSocketAddress();
                final String senderHostPort = senderAddr.getAddress().getHostAddress() + ":" + senderAddr.getPort();
                if (senderHostPort.equals(this.localSenderHostPort)) {
                    if (!this.debugIgnore && !McastListener.logger.isLoggable(Level.FINE)) {
                        continue;
                    }
                    McastListener.logger.info("Ignoring message as sent by localSender: " + this.localSenderHostPort);
                }
                else {
                    final byte[] data = this.pack.getData();
                    final ByteArrayInputStream bi = new ByteArrayInputStream(data);
                    final DataInputStream dataInput = new DataInputStream(bi);
                    ++this.totalPacketsReceived;
                    this.totalBytesReceived += this.pack.getLength();
                    final Packet header = Packet.readHeader(dataInput);
                    final long packetId = header.getPacketId();
                    final boolean ackMsg = packetId == 0L;
                    final boolean processThisPacket = ackMsg || this.packetControl.isProcessPacket(senderHostPort, header.getPacketId());
                    if (!processThisPacket) {
                        if (!this.debugIgnore && !McastListener.logger.isLoggable(Level.FINE)) {
                            continue;
                        }
                        McastListener.logger.info("Already processed packet: " + header.getPacketId() + " type:" + header.getPacketType() + " len:" + data.length);
                    }
                    else {
                        if (McastListener.logger.isLoggable(Level.FINER)) {
                            McastListener.logger.info("Incoming packet:" + header.getPacketId() + " type:" + header.getPacketType() + " len:" + data.length);
                        }
                        this.processPacket(senderHostPort, header, dataInput);
                    }
                }
            }
            catch (SocketTimeoutException e) {
                if (McastListener.logger.isLoggable(Level.FINE)) {
                    McastListener.logger.log(Level.FINE, "timeout", e);
                }
                this.packetControl.onListenerTimeout();
            }
            catch (IOException e2) {
                McastListener.logger.log(Level.INFO, "error ?", e2);
            }
        }
        this.shutdownComplete = true;
        synchronized (this.listenerThread) {
            this.listenerThread.notifyAll();
        }
    }
    
    protected void processPacket(final String senderHostPort, final Packet header, final DataInput dataInput) {
        try {
            switch (header.getPacketType()) {
                case 1: {
                    this.packetControl.processMessagesPacket(senderHostPort, header, dataInput, this.totalPacketsReceived, this.totalBytesReceived, this.totalTxnEventsReceived);
                    break;
                }
                case 2: {
                    ++this.totalTxnEventsReceived;
                    this.processTransactionEventPacket(header, dataInput);
                    break;
                }
                default: {
                    final String msg = "Unknown Packet type:" + header.getPacketType();
                    McastListener.logger.log(Level.SEVERE, msg);
                    break;
                }
            }
        }
        catch (IOException e) {
            final String msg2 = "Error reading Packet " + header.getPacketId() + " type:" + header.getPacketType();
            McastListener.logger.log(Level.SEVERE, msg2, e);
        }
    }
    
    private void processTransactionEventPacket(final Packet header, final DataInput dataInput) throws IOException {
        final SpiEbeanServer server = this.owner.getEbeanServer(header.getServerName());
        final PacketTransactionEvent tranEventPacket = PacketTransactionEvent.forRead(header, server);
        tranEventPacket.read(dataInput);
        server.remoteTransactionEvent(tranEventPacket.getEvent());
    }
    
    static {
        logger = Logger.getLogger(McastListener.class.getName());
    }
}
