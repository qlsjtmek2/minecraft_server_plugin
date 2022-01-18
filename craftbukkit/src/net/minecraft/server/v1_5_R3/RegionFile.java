// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.zip.DeflaterOutputStream;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.util.zip.InflaterInputStream;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.io.RandomAccessFile;
import java.io.File;

public class RegionFile
{
    private static final byte[] a;
    private final File b;
    private RandomAccessFile c;
    private final int[] d;
    private final int[] e;
    private ArrayList f;
    private int g;
    private long h;
    
    public RegionFile(final File file1) {
        this.d = new int[1024];
        this.e = new int[1024];
        this.h = 0L;
        this.b = file1;
        this.g = 0;
        try {
            if (file1.exists()) {
                this.h = file1.lastModified();
            }
            this.c = new RandomAccessFile(file1, "rw");
            if (this.c.length() < 4096L) {
                for (int i = 0; i < 1024; ++i) {
                    this.c.writeInt(0);
                }
                for (int i = 0; i < 1024; ++i) {
                    this.c.writeInt(0);
                }
                this.g += 8192;
            }
            if ((this.c.length() & 0xFFFL) != 0x0L) {
                for (int i = 0; i < (this.c.length() & 0xFFFL); ++i) {
                    this.c.write(0);
                }
            }
            int i = (int)this.c.length() / 4096;
            this.f = new ArrayList(i);
            for (int j = 0; j < i; ++j) {
                this.f.add(true);
            }
            this.f.set(0, false);
            this.f.set(1, false);
            this.c.seek(0L);
            for (int j = 0; j < 1024; ++j) {
                final int k = this.c.readInt();
                this.d[j] = k;
                if (k != 0 && (k >> 8) + (k & 0xFF) <= this.f.size()) {
                    for (int l = 0; l < (k & 0xFF); ++l) {
                        this.f.set((k >> 8) + l, false);
                    }
                }
            }
            for (int j = 0; j < 1024; ++j) {
                final int k = this.c.readInt();
                this.e[j] = k;
            }
        }
        catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }
    
    public synchronized boolean chunkExists(final int i, final int j) {
        if (this.d(i, j)) {
            return false;
        }
        try {
            final int k = this.e(i, j);
            if (k == 0) {
                return false;
            }
            final int l = k >> 8;
            final int i2 = k & 0xFF;
            if (l + i2 > this.f.size()) {
                return false;
            }
            this.c.seek(l * 4096);
            final int j2 = this.c.readInt();
            if (j2 > 4096 * i2 || j2 <= 0) {
                return false;
            }
            final byte b0 = this.c.readByte();
            if (b0 == 1 || b0 == 2) {
                return true;
            }
        }
        catch (IOException ioexception) {
            return false;
        }
        return false;
    }
    
    public synchronized DataInputStream a(final int i, final int j) {
        if (this.d(i, j)) {
            return null;
        }
        try {
            final int k = this.e(i, j);
            if (k == 0) {
                return null;
            }
            final int l = k >> 8;
            final int i2 = k & 0xFF;
            if (l + i2 > this.f.size()) {
                return null;
            }
            this.c.seek(l * 4096);
            final int j2 = this.c.readInt();
            if (j2 > 4096 * i2) {
                return null;
            }
            if (j2 <= 0) {
                return null;
            }
            final byte b0 = this.c.readByte();
            if (b0 == 1) {
                final byte[] abyte = new byte[j2 - 1];
                this.c.read(abyte);
                return new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(abyte))));
            }
            if (b0 == 2) {
                final byte[] abyte = new byte[j2 - 1];
                this.c.read(abyte);
                return new DataInputStream(new BufferedInputStream(new InflaterInputStream(new ByteArrayInputStream(abyte))));
            }
            return null;
        }
        catch (IOException ioexception) {
            return null;
        }
    }
    
    public DataOutputStream b(final int i, final int j) {
        DataOutputStream dataOutputStream;
        if (this.d(i, j)) {
            dataOutputStream = null;
        }
        else {
            final DeflaterOutputStream deflaterOutputStream;
            dataOutputStream = new DataOutputStream(deflaterOutputStream);
            deflaterOutputStream = new DeflaterOutputStream(new ChunkBuffer(this, i, j));
        }
        return dataOutputStream;
    }
    
    protected synchronized void a(final int i, final int j, final byte[] abyte, final int k) {
        try {
            final int l = this.e(i, j);
            int i2 = l >> 8;
            final int j2 = l & 0xFF;
            final int k2 = (k + 5) / 4096 + 1;
            if (k2 >= 256) {
                return;
            }
            if (i2 != 0 && j2 == k2) {
                this.a(i2, abyte, k);
            }
            else {
                for (int l2 = 0; l2 < j2; ++l2) {
                    this.f.set(i2 + l2, true);
                }
                int l2 = this.f.indexOf(true);
                int i3 = 0;
                if (l2 != -1) {
                    for (int j3 = l2; j3 < this.f.size(); ++j3) {
                        if (i3 != 0) {
                            if (this.f.get(j3)) {
                                ++i3;
                            }
                            else {
                                i3 = 0;
                            }
                        }
                        else if (this.f.get(j3)) {
                            l2 = j3;
                            i3 = 1;
                        }
                        if (i3 >= k2) {
                            break;
                        }
                    }
                }
                if (i3 >= k2) {
                    i2 = l2;
                    this.a(i, j, l2 << 8 | k2);
                    for (int j3 = 0; j3 < k2; ++j3) {
                        this.f.set(i2 + j3, false);
                    }
                    this.a(i2, abyte, k);
                }
                else {
                    this.c.seek(this.c.length());
                    i2 = this.f.size();
                    for (int j3 = 0; j3 < k2; ++j3) {
                        this.c.write(RegionFile.a);
                        this.f.add(false);
                    }
                    this.g += 4096 * k2;
                    this.a(i2, abyte, k);
                    this.a(i, j, i2 << 8 | k2);
                }
            }
            this.b(i, j, (int)(System.currentTimeMillis() / 1000L));
        }
        catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }
    
    private void a(final int i, final byte[] abyte, final int j) throws IOException {
        this.c.seek(i * 4096);
        this.c.writeInt(j + 1);
        this.c.writeByte(2);
        this.c.write(abyte, 0, j);
    }
    
    private boolean d(final int i, final int j) {
        return i < 0 || i >= 32 || j < 0 || j >= 32;
    }
    
    private int e(final int i, final int j) {
        return this.d[i + j * 32];
    }
    
    public boolean c(final int i, final int j) {
        return this.e(i, j) != 0;
    }
    
    private void a(final int i, final int j, final int k) throws IOException {
        this.d[i + j * 32] = k;
        this.c.seek((i + j * 32) * 4);
        this.c.writeInt(k);
    }
    
    private void b(final int i, final int j, final int k) throws IOException {
        this.e[i + j * 32] = k;
        this.c.seek(4096 + (i + j * 32) * 4);
        this.c.writeInt(k);
    }
    
    public void c() throws IOException {
        if (this.c != null) {
            this.c.close();
        }
    }
    
    static {
        a = new byte[4096];
    }
}
