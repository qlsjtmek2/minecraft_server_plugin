// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene.cluster;

import java.net.Socket;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.util.logging.Level;
import java.io.IOException;
import com.avaje.ebeaninternal.server.lib.thread.ThreadPoolManager;
import com.avaje.ebeaninternal.server.cluster.ClusterManager;
import com.avaje.ebeaninternal.server.lib.thread.ThreadPool;
import java.net.ServerSocket;
import java.util.logging.Logger;
import com.avaje.ebeaninternal.server.cluster.LuceneClusterListener;

public class SLuceneClusterSocketListener implements Runnable, LuceneClusterListener
{
    private static final Logger logger;
    private final int port;
    private final int listenTimeout = 60000;
    private final ServerSocket serverListenSocket;
    private final Thread listenerThread;
    private final ThreadPool threadPool;
    private final ClusterManager clusterManager;
    private boolean doingShutdown;
    private boolean isActive;
    
    public SLuceneClusterSocketListener(final ClusterManager clusterManager, final int port) {
        this.clusterManager = clusterManager;
        this.threadPool = ThreadPoolManager.getThreadPool("EbeanClusterLuceneListener");
        this.port = port;
        try {
            (this.serverListenSocket = new ServerSocket(port)).setSoTimeout(60000);
            this.listenerThread = new Thread(this, "EbeanClusterLuceneListener");
        }
        catch (IOException e) {
            final String msg = "Error starting cluster socket listener on port " + port;
            throw new RuntimeException(msg, e);
        }
    }
    
    public int getPort() {
        return this.port;
    }
    
    public void startup() {
        this.listenerThread.setDaemon(true);
        this.listenerThread.start();
        final String msg = "Cluster Lucene Listening address[todo] port[" + this.port + "]";
        SLuceneClusterSocketListener.logger.info(msg);
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
            SLuceneClusterSocketListener.logger.log(Level.SEVERE, null, e);
        }
    }
    
    public void run() {
        while (!this.doingShutdown) {
            try {
                synchronized (this.listenerThread) {
                    final Socket clientSocket = this.serverListenSocket.accept();
                    this.isActive = true;
                    final Runnable request = new SLuceneClusterSocketRequest(this.clusterManager, clientSocket);
                    this.threadPool.assign(request, true);
                    this.isActive = false;
                }
            }
            catch (SocketException e) {
                if (this.doingShutdown) {
                    final String msg = "doingShutdown and accept threw:" + e.getMessage();
                    SLuceneClusterSocketListener.logger.info(msg);
                }
                else {
                    SLuceneClusterSocketListener.logger.log(Level.SEVERE, null, e);
                }
            }
            catch (InterruptedIOException e2) {
                SLuceneClusterSocketListener.logger.fine("Possibly expected due to accept timeout?" + e2.getMessage());
            }
            catch (IOException e3) {
                SLuceneClusterSocketListener.logger.log(Level.SEVERE, null, e3);
            }
        }
    }
    
    static {
        logger = Logger.getLogger(SLuceneClusterSocketListener.class.getName());
    }
}
