// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster.mcast;

import java.util.List;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.logging.Level;
import com.avaje.ebeaninternal.server.cluster.Packet;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Logger;

public class McastSender
{
    private static final Logger logger;
    private final int port;
    private final InetAddress inetAddress;
    private final DatagramSocket sock;
    private final InetSocketAddress sendAddr;
    private final String senderHostPort;
    
    public McastSender(final int port, final String address, final int sendPort, final String sendAddress) {
        try {
            this.port = port;
            this.inetAddress = InetAddress.getByName(address);
            InetAddress sendInetAddress = null;
            if (sendAddress != null) {
                sendInetAddress = InetAddress.getByName(sendAddress);
            }
            else {
                sendInetAddress = InetAddress.getLocalHost();
            }
            if (sendPort > 0) {
                this.sock = new DatagramSocket(sendPort, sendInetAddress);
            }
            else {
                this.sock = new DatagramSocket(new InetSocketAddress(sendInetAddress, 0));
            }
            final String msg = "Cluster Multicast Sender on[" + sendInetAddress.getHostAddress() + ":" + this.sock.getLocalPort() + "]";
            McastSender.logger.info(msg);
            this.sendAddr = new InetSocketAddress(sendInetAddress, this.sock.getLocalPort());
            this.senderHostPort = sendInetAddress.getHostAddress() + ":" + this.sock.getLocalPort();
        }
        catch (Exception e) {
            final String msg = "McastSender port:" + port + " sendPort:" + sendPort + " " + address;
            throw new RuntimeException(msg, e);
        }
    }
    
    public InetSocketAddress getAddress() {
        return this.sendAddr;
    }
    
    public String getSenderHostPort() {
        return this.senderHostPort;
    }
    
    public int sendPacket(final Packet packet) throws IOException {
        final byte[] pktBytes = packet.getBytes();
        if (McastSender.logger.isLoggable(Level.FINE)) {
            McastSender.logger.fine("OUTGOING packet: " + packet.getPacketId() + " size:" + pktBytes.length);
        }
        if (pktBytes.length > 65507) {
            McastSender.logger.warning("OUTGOING packet: " + packet.getPacketId() + " size:" + pktBytes.length + " likely to be truncated using UDP with a MAXIMUM length of 65507");
        }
        final DatagramPacket pack = new DatagramPacket(pktBytes, pktBytes.length, this.inetAddress, this.port);
        this.sock.send(pack);
        return pktBytes.length;
    }
    
    public int sendPackets(final List<Packet> packets) throws IOException {
        int totalBytes = 0;
        for (int i = 0; i < packets.size(); ++i) {
            totalBytes += this.sendPacket(packets.get(i));
        }
        return totalBytes;
    }
    
    static {
        logger = Logger.getLogger(McastSender.class.getName());
    }
}
