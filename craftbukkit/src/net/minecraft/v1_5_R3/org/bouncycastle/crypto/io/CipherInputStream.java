// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.v1_5_R3.org.bouncycastle.crypto.io;

import java.io.IOException;
import java.io.InputStream;
import net.minecraft.v1_5_R3.org.bouncycastle.crypto.StreamCipher;
import net.minecraft.v1_5_R3.org.bouncycastle.crypto.BufferedBlockCipher;
import java.io.FilterInputStream;

public class CipherInputStream extends FilterInputStream
{
    private BufferedBlockCipher a;
    private StreamCipher b;
    private byte[] c;
    private byte[] d;
    private int e;
    private int f;
    private boolean g;
    
    public CipherInputStream(final InputStream inputStream, final BufferedBlockCipher a) {
        super(inputStream);
        this.a = a;
        this.c = new byte[a.b(2048)];
        this.d = new byte[2048];
    }
    
    private int a() {
        int available = super.available();
        if (available <= 0) {
            available = 1;
        }
        int f;
        if (available > this.d.length) {
            f = super.read(this.d, 0, this.d.length);
        }
        else {
            f = super.read(this.d, 0, available);
        }
        if (f < 0) {
            if (this.g) {
                return -1;
            }
            try {
                if (this.a != null) {
                    this.f = this.a.a(this.c, 0);
                }
                else {
                    this.f = 0;
                }
            }
            catch (Exception ex) {
                throw new IOException("error processing stream: " + ex.toString());
            }
            this.e = 0;
            this.g = true;
            if (this.e == this.f) {
                return -1;
            }
        }
        else {
            this.e = 0;
            try {
                if (this.a != null) {
                    this.f = this.a.a(this.d, 0, f, this.c, 0);
                }
                else {
                    this.b.a(this.d, 0, f, this.c, 0);
                    this.f = f;
                }
            }
            catch (Exception ex2) {
                throw new IOException("error processing stream: " + ex2.toString());
            }
            if (this.f == 0) {
                return this.a();
            }
        }
        return this.f;
    }
    
    public int read() {
        if (this.e == this.f && this.a() < 0) {
            return -1;
        }
        return this.c[this.e++] & 0xFF;
    }
    
    public int read(final byte[] array) {
        return this.read(array, 0, array.length);
    }
    
    public int read(final byte[] array, final int n, final int n2) {
        if (this.e == this.f && this.a() < 0) {
            return -1;
        }
        final int n3 = this.f - this.e;
        if (n2 > n3) {
            System.arraycopy(this.c, this.e, array, n, n3);
            this.e = this.f;
            return n3;
        }
        System.arraycopy(this.c, this.e, array, n, n2);
        this.e += n2;
        return n2;
    }
    
    public long skip(final long n) {
        if (n <= 0L) {
            return 0L;
        }
        final int n2 = this.f - this.e;
        if (n > n2) {
            this.e = this.f;
            return n2;
        }
        this.e += (int)n;
        return (int)n;
    }
    
    public int available() {
        return this.f - this.e;
    }
    
    public void close() {
        super.close();
    }
    
    public boolean markSupported() {
        return false;
    }
}
