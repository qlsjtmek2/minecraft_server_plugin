// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster.socket;

import java.net.Socket;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.util.logging.Level;
import java.io.IOException;
import com.avaje.ebeaninternal.server.lib.thread.ThreadPoolManager;
import com.avaje.ebeaninternal.server.lib.thread.ThreadPool;
import java.net.ServerSocket;
import java.util.logging.Logger;

class SocketClusterListener implements Runnable
{
    private static final Logger logger;
    private final int port;
    private final int listenTimeout = 60000;
    private final ServerSocket serverListenSocket;
    private final Thread listenerThread;
    private final ThreadPool threadPool;
    private final SocketClusterBroadcast owner;
    boolean doingShutdown;
    boolean isActive;
    
    public SocketClusterListener(final SocketClusterBroadcast owner, final int port) {
        this.owner = owner;
        this.threadPool = ThreadPoolManager.getThreadPool("EbeanClusterMember");
        this.port = port;
        try {
            (this.serverListenSocket = new ServerSocket(port)).setSoTimeout(60000);
            this.listenerThread = new Thread(this, "EbeanClusterListener");
        }
        catch (IOException e) {
            final String msg = "Error starting cluster socket listener on port " + port;
            throw new RuntimeException(msg, e);
        }
    }
    
    public int getPort() {
        return this.port;
    }
    
    public void startListening() throws IOException {
        this.listenerThread.setDaemon(true);
        this.listenerThread.start();
    }
    
    public void shutdown() {
        this.doingShutdown = true;
        try {
            if (this.isActive) {
                synchronized (this.listenerThread) {
                    try {
                        this.listenerThread.wait(1000L);
                    }
                    catch (InterruptedException ex) {}
                }
            }
            this.listenerThread.interrupt();
            this.serverListenSocket.close();
        }
        catch (IOException e) {
            SocketClusterListener.logger.log(Level.SEVERE, null, e);
        }
    }
    
    public void run() {
        while (!this.doingShutdown) {
            try {
                synchronized (this.listenerThread) {
                    final Socket clientSocket = this.serverListenSocket.accept();
                    this.isActive = true;
                    final Runnable request = new RequestProcessor(this.owner, clientSocket);
                    this.threadPool.assign(request, true);
                    this.isActive = false;
                }
            }
            catch (SocketException e) {
                if (this.doingShutdown) {
                    final String msg = "doingShutdown and accept threw:" + e.getMessage();
                    SocketClusterListener.logger.info(msg);
                }
                else {
                    SocketClusterListener.logger.log(Level.SEVERE, null, e);
                }
            }
            catch (InterruptedIOException e2) {
                SocketClusterListener.logger.fine("Possibly expected due to accept timeout?" + e2.getMessage());
            }
            catch (IOException e3) {
                SocketClusterListener.logger.log(Level.SEVERE, null, e3);
            }
        }
    }
    
    static {
        logger = Logger.getLogger(SocketClusterListener.class.getName());
    }
}
