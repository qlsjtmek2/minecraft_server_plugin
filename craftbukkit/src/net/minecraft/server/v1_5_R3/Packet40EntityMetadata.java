// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.util.List;

public class Packet40EntityMetadata extends Packet
{
    public int a;
    private List b;
    
    public Packet40EntityMetadata() {
    }
    
    public Packet40EntityMetadata(final int a, final DataWatcher dataWatcher, final boolean b) {
        this.a = a;
        if (b) {
            this.b = dataWatcher.c();
        }
        else {
            this.b = dataWatcher.b();
        }
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readInt();
        this.b = DataWatcher.a(dataInputStream);
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeInt(this.a);
        DataWatcher.a(this.b, dataOutputStream);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 5;
    }
}
