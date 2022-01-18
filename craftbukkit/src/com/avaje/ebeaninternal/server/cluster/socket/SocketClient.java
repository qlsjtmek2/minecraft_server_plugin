// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster.socket;

import java.util.logging.Level;
import java.net.SocketAddress;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

class SocketClient
{
    private static final Logger logger;
    private final InetSocketAddress address;
    private final String hostPort;
    private boolean online;
    private Socket socket;
    private OutputStream os;
    private ObjectOutputStream oos;
    
    public SocketClient(final InetSocketAddress address) {
        this.address = address;
        this.hostPort = address.getHostName() + ":" + address.getPort();
    }
    
    public String getHostPort() {
        return this.hostPort;
    }
    
    public int getPort() {
        return this.address.getPort();
    }
    
    public boolean isOnline() {
        return this.online;
    }
    
    public void setOnline(final boolean online) throws IOException {
        if (online) {
            this.setOnline();
        }
        else {
            this.disconnect();
        }
    }
    
    private void setOnline() throws IOException {
        this.connect();
        this.online = true;
    }
    
    public void reconnect() throws IOException {
        this.disconnect();
        this.connect();
    }
    
    private void connect() throws IOException {
        if (this.socket != null) {
            throw new IllegalStateException("Already got a socket connection?");
        }
        final Socket s = new Socket();
        s.setKeepAlive(true);
        s.connect(this.address);
        this.socket = s;
        this.os = this.socket.getOutputStream();
    }
    
    public void disconnect() {
        this.online = false;
        if (this.socket != null) {
            try {
                this.socket.close();
            }
            catch (IOException e) {
                final String msg = "Error disconnecting from Cluster member " + this.hostPort;
                SocketClient.logger.log(Level.INFO, msg, e);
            }
            this.os = null;
            this.oos = null;
            this.socket = null;
        }
    }
    
    public boolean register(final SocketClusterMessage registerMsg) {
        try {
            this.setOnline();
            this.send(registerMsg);
            return true;
        }
        catch (IOException e) {
            this.disconnect();
            return false;
        }
    }
    
    public boolean send(final SocketClusterMessage msg) throws IOException {
        if (this.online) {
            this.writeObject(msg);
            return true;
        }
        return false;
    }
    
    private void writeObject(final Object object) throws IOException {
        if (this.oos == null) {
            this.oos = new ObjectOutputStream(this.os);
        }
        this.oos.writeObject(object);
        this.oos.flush();
    }
    
    static {
        logger = Logger.getLogger(SocketClient.class.getName());
    }
}
