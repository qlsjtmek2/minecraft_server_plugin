// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.DataInputStream;

public class Packet3Chat extends Packet
{
    public static int a;
    public String message;
    private boolean c;
    
    public Packet3Chat() {
        this.c = true;
    }
    
    public Packet3Chat(final String s) {
        this(s, true);
    }
    
    public Packet3Chat(final String s, final boolean flag) {
        this.c = true;
        this.message = s;
        this.c = flag;
    }
    
    public void a(final DataInputStream datainputstream) throws IOException {
        this.message = Packet.a(datainputstream, Packet3Chat.a);
    }
    
    public void a(final DataOutputStream dataoutputstream) throws IOException {
        Packet.a(this.message, dataoutputstream);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 2 + this.message.length() * 2;
    }
    
    public boolean isServer() {
        return this.c;
    }
    
    public boolean a_() {
        return !this.message.startsWith("/");
    }
    
    static {
        Packet3Chat.a = 119;
    }
}
