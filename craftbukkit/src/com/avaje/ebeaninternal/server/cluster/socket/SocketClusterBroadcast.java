// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster.socket;

import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.cluster.SerialiseTransactionHelper;
import java.io.InterruptedIOException;
import com.avaje.ebeaninternal.server.cluster.DataHolder;
import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
import java.util.logging.Level;
import java.io.IOException;
import javax.persistence.PersistenceException;
import java.net.InetSocketAddress;
import com.avaje.ebeaninternal.server.lib.util.StringHelper;
import com.avaje.ebean.config.GlobalProperties;
import java.util.concurrent.atomic.AtomicInteger;
import com.avaje.ebeaninternal.server.cluster.ClusterManager;
import java.util.HashMap;
import java.util.logging.Logger;
import com.avaje.ebeaninternal.server.cluster.ClusterBroadcast;

public class SocketClusterBroadcast implements ClusterBroadcast
{
    private static final Logger logger;
    private final SocketClient local;
    private final HashMap<String, SocketClient> clientMap;
    private final SocketClusterListener listener;
    private SocketClient[] members;
    private ClusterManager clusterManager;
    private final TxnSerialiseHelper txnSerialiseHelper;
    private final AtomicInteger txnOutgoing;
    private final AtomicInteger txnIncoming;
    
    public SocketClusterBroadcast() {
        this.txnSerialiseHelper = new TxnSerialiseHelper();
        this.txnOutgoing = new AtomicInteger();
        this.txnIncoming = new AtomicInteger();
        final String localHostPort = GlobalProperties.get("ebean.cluster.local", null);
        final String members = GlobalProperties.get("ebean.cluster.members", null);
        SocketClusterBroadcast.logger.info("Clustering using Sockets local[" + localHostPort + "] members[" + members + "]");
        this.local = new SocketClient(this.parseFullName(localHostPort));
        this.clientMap = new HashMap<String, SocketClient>();
        final String[] memArray = StringHelper.delimitedToArray(members, ",", false);
        for (int i = 0; i < memArray.length; ++i) {
            final InetSocketAddress member = this.parseFullName(memArray[i]);
            final SocketClient client = new SocketClient(member);
            if (!this.local.getHostPort().equalsIgnoreCase(client.getHostPort())) {
                this.clientMap.put(client.getHostPort(), client);
            }
        }
        this.members = this.clientMap.values().toArray(new SocketClient[this.clientMap.size()]);
        this.listener = new SocketClusterListener(this, this.local.getPort());
    }
    
    public SocketClusterStatus getStatus() {
        int currentGroupSize = 0;
        for (int i = 0; i < this.members.length; ++i) {
            if (this.members[i].isOnline()) {
                ++currentGroupSize;
            }
        }
        final int txnIn = this.txnIncoming.get();
        final int txnOut = this.txnOutgoing.get();
        return new SocketClusterStatus(currentGroupSize, txnIn, txnOut);
    }
    
    public void startup(final ClusterManager clusterManager) {
        this.clusterManager = clusterManager;
        try {
            this.listener.startListening();
            this.register();
        }
        catch (IOException e) {
            throw new PersistenceException(e);
        }
    }
    
    public void shutdown() {
        this.deregister();
        this.listener.shutdown();
    }
    
    private void register() {
        final SocketClusterMessage h = SocketClusterMessage.register(this.local.getHostPort(), true);
        for (int i = 0; i < this.members.length; ++i) {
            final boolean online = this.members[i].register(h);
            final String msg = "Cluster Member [" + this.members[i].getHostPort() + "] online[" + online + "]";
            SocketClusterBroadcast.logger.info(msg);
        }
    }
    
    protected void setMemberOnline(final String fullName, final boolean online) throws IOException {
        synchronized (this.clientMap) {
            final String msg = "Cluster Member [" + fullName + "] online[" + online + "]";
            SocketClusterBroadcast.logger.info(msg);
            final SocketClient member = this.clientMap.get(fullName);
            member.setOnline(online);
        }
    }
    
    private void send(final SocketClient client, final SocketClusterMessage msg) {
        try {
            client.send(msg);
        }
        catch (Exception ex) {
            SocketClusterBroadcast.logger.log(Level.SEVERE, "Error sending message", ex);
            try {
                client.reconnect();
            }
            catch (IOException e) {
                SocketClusterBroadcast.logger.log(Level.SEVERE, "Error trying to reconnect", ex);
            }
        }
    }
    
    public void broadcast(final RemoteTransactionEvent remoteTransEvent) {
        try {
            this.txnOutgoing.incrementAndGet();
            final DataHolder dataHolder = this.txnSerialiseHelper.createDataHolder(remoteTransEvent);
            final SocketClusterMessage msg = SocketClusterMessage.transEvent(dataHolder);
            this.broadcast(msg);
        }
        catch (Exception e) {
            final String msg2 = "Error sending RemoteTransactionEvent " + remoteTransEvent + " to cluster members.";
            SocketClusterBroadcast.logger.log(Level.SEVERE, msg2, e);
        }
    }
    
    protected void broadcast(final SocketClusterMessage msg) {
        for (int i = 0; i < this.members.length; ++i) {
            this.send(this.members[i], msg);
        }
    }
    
    private void deregister() {
        final SocketClusterMessage h = SocketClusterMessage.register(this.local.getHostPort(), false);
        this.broadcast(h);
        for (int i = 0; i < this.members.length; ++i) {
            this.members[i].disconnect();
        }
    }
    
    protected boolean process(final SocketConnection request) throws IOException, ClassNotFoundException {
        try {
            final SocketClusterMessage h = (SocketClusterMessage)request.readObject();
            if (h.isRegisterEvent()) {
                this.setMemberOnline(h.getRegisterHost(), h.isRegister());
            }
            else {
                this.txnIncoming.incrementAndGet();
                final DataHolder dataHolder = h.getDataHolder();
                final RemoteTransactionEvent transEvent = this.txnSerialiseHelper.read(dataHolder);
                transEvent.run();
            }
            return h.isRegisterEvent() && !h.isRegister();
        }
        catch (InterruptedIOException e) {
            final String msg = "Timeout waiting for message";
            SocketClusterBroadcast.logger.log(Level.INFO, msg, e);
            try {
                request.disconnect();
            }
            catch (IOException ex) {
                SocketClusterBroadcast.logger.log(Level.INFO, "Error disconnecting after timeout", ex);
            }
            return true;
        }
    }
    
    private InetSocketAddress parseFullName(String hostAndPort) {
        try {
            hostAndPort = hostAndPort.trim();
            final int colonPos = hostAndPort.indexOf(":");
            if (colonPos == -1) {
                final String msg = "No colon \":\" in " + hostAndPort;
                throw new IllegalArgumentException(msg);
            }
            final String host = hostAndPort.substring(0, colonPos);
            final String sPort = hostAndPort.substring(colonPos + 1, hostAndPort.length());
            final int port = Integer.parseInt(sPort);
            return new InetSocketAddress(host, port);
        }
        catch (Exception ex) {
            throw new RuntimeException("Error parsing [" + hostAndPort + "] for the form [host:port]", ex);
        }
    }
    
    static {
        logger = Logger.getLogger(SocketClusterBroadcast.class.getName());
    }
    
    class TxnSerialiseHelper extends SerialiseTransactionHelper
    {
        public SpiEbeanServer getEbeanServer(final String serverName) {
            return (SpiEbeanServer)SocketClusterBroadcast.this.clusterManager.getServer(serverName);
        }
    }
}
