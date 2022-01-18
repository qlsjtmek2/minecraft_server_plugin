// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster.socket;

import java.io.IOException;
import java.util.logging.Level;
import java.net.Socket;
import java.util.logging.Logger;

class RequestProcessor implements Runnable
{
    private static final Logger logger;
    private final Socket clientSocket;
    private final SocketClusterBroadcast owner;
    
    public RequestProcessor(final SocketClusterBroadcast owner, final Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.owner = owner;
    }
    
    public void run() {
        try {
            final SocketConnection sc = new SocketConnection(this.clientSocket);
            while (!this.owner.process(sc)) {}
            sc.disconnect();
        }
        catch (IOException e) {
            RequestProcessor.logger.log(Level.SEVERE, null, e);
        }
        catch (ClassNotFoundException e2) {
            RequestProcessor.logger.log(Level.SEVERE, null, e2);
        }
    }
    
    static {
        logger = Logger.getLogger(RequestProcessor.class.getName());
    }
}
