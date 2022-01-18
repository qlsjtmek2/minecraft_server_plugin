// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;

public class Packet52MultiBlockChange extends Packet
{
    public int a;
    public int b;
    public byte[] c;
    public int d;
    private static byte[] e;
    
    public Packet52MultiBlockChange() {
        this.lowPriority = true;
    }
    
    public Packet52MultiBlockChange(final int n, final int n2, final short[] array, final int d, final World world) {
        this.lowPriority = true;
        this.a = n;
        this.b = n2;
        this.d = d;
        final int n3 = 4 * d;
        final Chunk chunk = world.getChunkAt(n, n2);
        try {
            if (d >= 64) {
                this.m.info("ChunkTilesUpdatePacket compress " + d);
                if (Packet52MultiBlockChange.e.length < n3) {
                    Packet52MultiBlockChange.e = new byte[n3];
                }
            }
            else {
                final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(n3);
                final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                for (int i = 0; i < d; ++i) {
                    final int n4 = array[i] >> 12 & 0xF;
                    final int n5 = array[i] >> 8 & 0xF;
                    final short n6 = (short)(array[i] & 0xFF);
                    dataOutputStream.writeShort(array[i]);
                    dataOutputStream.writeShort((short)((chunk.getTypeId(n4, n6, n5) & 0xFFF) << 4 | (chunk.getData(n4, n6, n5) & 0xF)));
                }
                this.c = byteArrayOutputStream.toByteArray();
                if (this.c.length != n3) {
                    throw new RuntimeException("Expected length " + n3 + " doesn't match received length " + this.c.length);
                }
            }
        }
        catch (IOException ex) {
            this.m.severe("Couldn't create chunk packet", ex);
            this.c = null;
        }
    }
    
    public void a(final DataInputStream dataInputStream) {
        this.a = dataInputStream.readInt();
        this.b = dataInputStream.readInt();
        this.d = (dataInputStream.readShort() & 0xFFFF);
        final int int1 = dataInputStream.readInt();
        if (int1 > 0) {
            dataInputStream.readFully(this.c = new byte[int1]);
        }
    }
    
    public void a(final DataOutputStream dataOutputStream) {
        dataOutputStream.writeInt(this.a);
        dataOutputStream.writeInt(this.b);
        dataOutputStream.writeShort((short)this.d);
        if (this.c != null) {
            dataOutputStream.writeInt(this.c.length);
            dataOutputStream.write(this.c);
        }
        else {
            dataOutputStream.writeInt(0);
        }
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 10 + this.d * 4;
    }
    
    static {
        Packet52MultiBlockChange.e = new byte[0];
    }
}
