// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class Packet53BlockChange extends Packet
{
    public int a;
    public int b;
    public int c;
    public int material;
    public int data;
    
    public Packet53BlockChange() {
        this.lowPriority = true;
    }
    
    public Packet53BlockChange(final int i, final int j, final int k, final World world) {
        this.lowPriority = true;
        this.a = i;
        this.b = j;
        this.c = k;
        this.material = world.getTypeId(i, j, k);
        this.data = world.getData(i, j, k);
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readInt();
        this.b = dataInputStream.read();
        this.c = dataInputStream.readInt();
        this.material = dataInputStream.readShort();
        this.data = dataInputStream.read();
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeInt(this.a);
        dataOutputStream.write(this.b);
        dataOutputStream.writeInt(this.c);
        dataOutputStream.writeShort(this.material);
        dataOutputStream.write(this.data);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 11;
    }
}
