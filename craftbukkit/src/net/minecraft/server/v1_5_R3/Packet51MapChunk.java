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
import java.util.zip.Deflater;

public class Packet51MapChunk extends Packet
{
    public int a;
    public int b;
    public int c;
    public int d;
    private byte[] buffer;
    private byte[] inflatedBuffer;
    public boolean e;
    private int size;
    private static byte[] buildBuffer;
    private static final byte[] unloadSequence;
    
    public Packet51MapChunk() {
        this.lowPriority = true;
    }
    
    public Packet51MapChunk(final int x, final int z) {
        this.a = x;
        this.b = z;
        this.e = true;
        this.c = 0;
        this.d = 0;
        this.size = Packet51MapChunk.unloadSequence.length;
        this.buffer = Packet51MapChunk.unloadSequence;
    }
    
    public Packet51MapChunk(final Chunk chunk, final boolean flag, final int i) {
        this.lowPriority = true;
        this.a = chunk.x;
        this.b = chunk.z;
        this.e = flag;
        final ChunkMap chunkmap = a(chunk, flag, i);
        final Deflater deflater = new Deflater(4);
        this.d = chunkmap.c;
        this.c = chunkmap.b;
        OrebfuscatorManager.obfuscateSync(chunk.x, chunk.z, i, chunkmap.a, chunk.world);
        try {
            this.inflatedBuffer = chunkmap.a;
            deflater.setInput(chunkmap.a, 0, chunkmap.a.length);
            deflater.finish();
            this.buffer = new byte[chunkmap.a.length];
            this.size = deflater.deflate(this.buffer);
        }
        finally {
            deflater.end();
        }
    }
    
    public void a(final DataInputStream datainputstream) throws IOException {
        this.a = datainputstream.readInt();
        this.b = datainputstream.readInt();
        this.e = datainputstream.readBoolean();
        this.c = datainputstream.readShort();
        this.d = datainputstream.readShort();
        this.size = datainputstream.readInt();
        if (Packet51MapChunk.buildBuffer.length < this.size) {
            Packet51MapChunk.buildBuffer = new byte[this.size];
        }
        datainputstream.readFully(Packet51MapChunk.buildBuffer, 0, this.size);
        int i = 0;
        for (int j = 0; j < 16; ++j) {
            i += (this.c >> j & 0x1);
        }
        int j = 12288 * i;
        if (this.e) {
            j += 256;
        }
        this.inflatedBuffer = new byte[j];
        final Inflater inflater = new Inflater();
        inflater.setInput(Packet51MapChunk.buildBuffer, 0, this.size);
        try {
            inflater.inflate(this.inflatedBuffer);
        }
        catch (DataFormatException dataformatexception) {
            throw new IOException("Bad compressed data format");
        }
        finally {
            inflater.end();
        }
    }
    
    public void a(final DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.writeInt(this.a);
        dataoutputstream.writeInt(this.b);
        dataoutputstream.writeBoolean(this.e);
        dataoutputstream.writeShort((short)(this.c & 0xFFFF));
        dataoutputstream.writeShort((short)(this.d & 0xFFFF));
        dataoutputstream.writeInt(this.size);
        dataoutputstream.write(this.buffer, 0, this.size);
    }
    
    public void handle(final Connection connection) {
        connection.a(this);
    }
    
    public int a() {
        return 17 + this.size;
    }
    
    public static ChunkMap a(final Chunk chunk, final boolean flag, final int i) {
        int j = 0;
        final ChunkSection[] achunksection = chunk.i();
        int k = 0;
        final ChunkMap chunkmap = new ChunkMap();
        final byte[] abyte = Packet51MapChunk.buildBuffer;
        if (flag) {
            chunk.seenByPlayer = true;
        }
        for (int l = 0; l < achunksection.length; ++l) {
            if (achunksection[l] != null && (!flag || !achunksection[l].isEmpty()) && (i & 1 << l) != 0x0) {
                final ChunkMap chunkMap = chunkmap;
                chunkMap.b |= 1 << l;
                if (achunksection[l].getExtendedIdArray() != null) {
                    final ChunkMap chunkMap2 = chunkmap;
                    chunkMap2.c |= 1 << l;
                    ++k;
                }
            }
        }
        for (int l = 0; l < achunksection.length; ++l) {
            if (achunksection[l] != null && (!flag || !achunksection[l].isEmpty()) && (i & 1 << l) != 0x0) {
                final byte[] abyte2 = achunksection[l].getIdArray();
                System.arraycopy(abyte2, 0, abyte, j, abyte2.length);
                j += abyte2.length;
            }
        }
        for (int l = 0; l < achunksection.length; ++l) {
            if (achunksection[l] != null && (!flag || !achunksection[l].isEmpty()) && (i & 1 << l) != 0x0) {
                final NibbleArray nibblearray = achunksection[l].getDataArray();
                j = nibblearray.copyToByteArray(abyte, j);
            }
        }
        for (int l = 0; l < achunksection.length; ++l) {
            if (achunksection[l] != null && (!flag || !achunksection[l].isEmpty()) && (i & 1 << l) != 0x0) {
                final NibbleArray nibblearray = achunksection[l].getEmittedLightArray();
                j = nibblearray.copyToByteArray(abyte, j);
            }
        }
        if (!chunk.world.worldProvider.f) {
            for (int l = 0; l < achunksection.length; ++l) {
                if (achunksection[l] != null && (!flag || !achunksection[l].isEmpty()) && (i & 1 << l) != 0x0) {
                    final NibbleArray nibblearray = achunksection[l].getSkyLightArray();
                    j = nibblearray.copyToByteArray(abyte, j);
                }
            }
        }
        if (k > 0) {
            for (int l = 0; l < achunksection.length; ++l) {
                if (achunksection[l] != null && (!flag || !achunksection[l].isEmpty()) && achunksection[l].getExtendedIdArray() != null && (i & 1 << l) != 0x0) {
                    final NibbleArray nibblearray = achunksection[l].getExtendedIdArray();
                    j = nibblearray.copyToByteArray(abyte, j);
                }
            }
        }
        if (flag) {
            final byte[] abyte3 = chunk.m();
            System.arraycopy(abyte3, 0, abyte, j, abyte3.length);
            j += abyte3.length;
        }
        System.arraycopy(abyte, 0, chunkmap.a = new byte[j], 0, j);
        return chunkmap;
    }
    
    static {
        Packet51MapChunk.buildBuffer = new byte[196864];
        unloadSequence = new byte[] { 120, -100, 99, 100, 28, -39, 0, 0, -127, -128, 1, 1 };
    }
}
