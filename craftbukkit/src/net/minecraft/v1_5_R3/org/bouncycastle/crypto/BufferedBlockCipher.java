// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.v1_5_R3.org.bouncycastle.crypto;

public class BufferedBlockCipher
{
    protected byte[] a;
    protected int b;
    protected boolean c;
    protected BlockCipher d;
    protected boolean e;
    protected boolean f;
    
    protected BufferedBlockCipher() {
    }
    
    public BufferedBlockCipher(final BlockCipher d) {
        this.d = d;
        this.a = new byte[d.b()];
        this.b = 0;
        final String a = d.a();
        final int n = a.indexOf(47) + 1;
        this.f = (n > 0 && a.startsWith("PGP", n));
        if (this.f) {
            this.e = true;
        }
        else {
            this.e = (n > 0 && (a.startsWith("CFB", n) || a.startsWith("OFB", n) || a.startsWith("OpenPGP", n) || a.startsWith("SIC", n) || a.startsWith("GCTR", n)));
        }
    }
    
    public void a(final boolean c, final CipherParameters cipherParameters) {
        this.c = c;
        this.b();
        this.d.a(c, cipherParameters);
    }
    
    public int a() {
        return this.d.b();
    }
    
    public int a(final int n) {
        final int n2 = n + this.b;
        int n3;
        if (this.f) {
            n3 = n2 % this.a.length - (this.d.b() + 2);
        }
        else {
            n3 = n2 % this.a.length;
        }
        return n2 - n3;
    }
    
    public int b(final int n) {
        return n + this.b;
    }
    
    public int a(final byte[] array, int n, int i, final byte[] array2, final int n2) {
        if (i < 0) {
            throw new IllegalArgumentException("Can't have a negative input length!");
        }
        final int a = this.a();
        final int a2 = this.a(i);
        if (a2 > 0 && n2 + a2 > array2.length) {
            throw new DataLengthException("output buffer too short");
        }
        int n3 = 0;
        final int n4 = this.a.length - this.b;
        if (i > n4) {
            System.arraycopy(array, n, this.a, this.b, n4);
            n3 += this.d.a(this.a, 0, array2, n2);
            this.b = 0;
            for (i -= n4, n += n4; i > this.a.length; i -= a, n += a) {
                n3 += this.d.a(array, n, array2, n2 + n3);
            }
        }
        System.arraycopy(array, n, this.a, this.b, i);
        this.b += i;
        if (this.b == this.a.length) {
            n3 += this.d.a(this.a, 0, array2, n2 + n3);
            this.b = 0;
        }
        return n3;
    }
    
    public int a(final byte[] array, final int n) {
        try {
            int b = 0;
            if (n + this.b > array.length) {
                throw new DataLengthException("output buffer too short for doFinal()");
            }
            if (this.b != 0) {
                if (!this.e) {
                    throw new DataLengthException("data not block size aligned");
                }
                this.d.a(this.a, 0, this.a, 0);
                b = this.b;
                this.b = 0;
                System.arraycopy(this.a, 0, array, n, b);
            }
            return b;
        }
        finally {
            this.b();
        }
    }
    
    public void b() {
        for (int i = 0; i < this.a.length; ++i) {
            this.a[i] = 0;
        }
        this.b = 0;
        this.d.c();
    }
}
