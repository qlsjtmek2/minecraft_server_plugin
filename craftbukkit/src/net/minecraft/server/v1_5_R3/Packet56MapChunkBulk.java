// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.DataOutputStream;
import java.util.zip.DataFormatException;
import java.io.IOException;
import java.util.zip.Inflater;
import java.io.DataInputStream;
import org.spigotmc.OrebfuscatorManager;
import java.util.List;
import java.util.zip.Deflater;

public class Packet56MapChunkBulk extends Packet
{
    private int[] c;
    private int[] d;
    public int[] a;
    public int[] b;
    private byte[] buffer;
    private byte[][] inflatedBuffers;
    private int size;
    private boolean h;
    private byte[] buildBuffer;
    static final ThreadLocal<Deflater> localDeflater;
    private World world;
    
    public Packet56MapChunkBulk() {
        this.buildBuffer = new byte[0];
    }
    
    public Packet56MapChunkBulk(final List list) {
        this.buildBuffer = new byte[0];
        final int i = list.size();
        this.c = new int[i];
        this.d = new int[i];
        this.a = new int[i];
        this.b = new int[i];
        this.inflatedBuffers = new byte[i][];
        this.h = (!list.isEmpty() && !list.get(0).world.worldProvider.f);
        int j = 0;
        for (int k = 0; k < i; ++k) {
            final Chunk chunk = list.get(k);
            final ChunkMap chunkmap = Packet51MapChunk.a(chunk, true, 65535);
            this.world = chunk.world;
            j += chunkmap.a.length;
            this.c[k] = chunk.x;
            this.d[k] = chunk.z;
            this.a[k] = chunkmap.b;
            this.b[k] = chunkmap.c;
            this.inflatedBuffers[k] = chunkmap.a;
        }
    }
    
    public void compress() {
        if (this.buffer != null) {
            return;
        }
        int finalBufferSize = 0;
        for (int i = 0; i < this.a.length; ++i) {
            OrebfuscatorManager.obfuscate(this.c[i], this.d[i], this.a[i], this.inflatedBuffers[i], this.world);
            finalBufferSize += this.inflatedBuffers[i].length;
        }
        this.buildBuffer = new byte[finalBufferSize];
        int bufferLocation = 0;
        for (int j = 0; j < this.a.length; ++j) {
            System.arraycopy(this.inflatedBuffers[j], 0, this.buildBuffer, bufferLocation, this.inflatedBuffers[j].length);
            bufferLocation += this.inflatedBuffers[j].length;
        }
        final Deflater deflater = Packet56MapChunkBulk.localDeflater.get();
        deflater.reset();
        deflater.setInput(this.buildBuffer);
        deflater.finish();
        this.buffer = new byte[this.buildBuffer.length + 100];
        this.size = deflater.deflate(this.buffer);
    }
    
    public void a(final DataInputStream datainputstream) throws IOException {
        final short short1 = datainputstream.readShort();
        this.size = datainputstream.readInt();
        this.h = datainputstream.readBoolean();
        this.c = new int[short1];
        this.d = new int[short1];
        this.a = new int[short1];
        this.b = new int[short1];
        this.inflatedBuffers = new byte[short1][];
        if (this.buildBuffer.length < this.size) {
            this.buildBuffer = new byte[this.size];
        }
        datainputstream.readFully(this.buildBuffer, 0, this.size);
        final byte[] abyte = new byte[196864 * short1];
        final Inflater inflater = new Inflater();
        inflater.setInput(this.buildBuffer, 0, this.size);
        try {
            inflater.inflate(abyte);
        }
        catch (DataFormatException dataformatexception) {
            throw new IOException("Bad compressed data format");
        }
        finally {
            inflater.end();
        }
        int i = 0;
        for (int j = 0; j < short1; ++j) {
            this.c[j] = datainputstream.readInt();
            this.d[j] = datainputstream.readInt();
            this.a[j] = datainputstream.readShort();
            this.b[j] = datainputstream.readShort();
            int k = 0;
            int l = 0;
            for (int i2 = 0; i2 < 16; ++i2) {
                k += (this.a[j] >> i2 & 0x1);
                l += (this.b[j] >> i2 & 0x1);
            }
            int i2 = 8192 * k + 256;
            i2 += 2048 * l;
            if (this.h) {
                i2 += 2048 * k;
            }
            System.arraycopy(abyte, i, this.inflatedBuffers[j] = new byte[i2], 0, i2);
            i += i2;
        }
    }
    
    public void a(final DataOutputStream dataoutputstream) throws IOException {
        this.compress();
        dataoutputstream.writeShort(this.c.length);
        dataoutputstream.writeInt(this.size);
        dataoutputstream.writeBoolean(this.h);
        dataoutputstream.write(this.buffer, 0, this.size);
        for (int i = 0; i < this.c.length; ++i) {
            dataoutputstream.writeInt(this.c[i]);
            dataoutputstream.writeInt(this.d[i]);
            dataoutputstream.writeShort((short)(this.a[i] & 0xFFFF));
            dataoutputstream.writeShort((short)(this.b[i] & 0xFFFF));
        }
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 6 + this.size + 12 * this.d();
    }
    
    public int d() {
        return this.c.length;
    }
    
    static {
        localDeflater = new ThreadLocal<Deflater>() {
            protected Deflater initialValue() {
                return new Deflater(4);
            }
        };
    }
}
