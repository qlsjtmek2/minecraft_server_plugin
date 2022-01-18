// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster.socket;

import com.avaje.ebeaninternal.server.cluster.Packet;
import com.avaje.ebeaninternal.server.cluster.DataHolder;
import java.io.Serializable;

public class SocketClusterMessage implements Serializable
{
    private static final long serialVersionUID = 2993350408394934473L;
    private final String registerHost;
    private final boolean register;
    private final DataHolder dataHolder;
    
    public static SocketClusterMessage register(final String registerHost, final boolean register) {
        return new SocketClusterMessage(registerHost, register);
    }
    
    public static SocketClusterMessage transEvent(final DataHolder transEvent) {
        return new SocketClusterMessage(transEvent);
    }
    
    public static SocketClusterMessage packet(final Packet packet) {
        final DataHolder d = new DataHolder(packet.getBytes());
        return new SocketClusterMessage(d);
    }
    
    private SocketClusterMessage(final String registerHost, final boolean register) {
        this.registerHost = registerHost;
        this.register = register;
        this.dataHolder = null;
    }
    
    private SocketClusterMessage(final DataHolder dataHolder) {
        this.dataHolder = dataHolder;
        this.registerHost = null;
        this.register = false;
    }
    
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        if (this.registerHost != null) {
            sb.append("register ");
            sb.append(this.register);
            sb.append(" ");
            sb.append(this.registerHost);
        }
        else {
            sb.append("transEvent ");
        }
        return sb.toString();
    }
    
    public boolean isRegisterEvent() {
        return this.registerHost != null;
    }
    
    public String getRegisterHost() {
        return this.registerHost;
    }
    
    public boolean isRegister() {
        return this.register;
    }
    
    public DataHolder getDataHolder() {
        return this.dataHolder;
    }
}
