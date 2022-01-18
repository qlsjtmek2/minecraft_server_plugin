// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.v1_5_R3.org.bouncycastle.crypto.modes;

import net.minecraft.v1_5_R3.org.bouncycastle.crypto.DataLengthException;
import net.minecraft.v1_5_R3.org.bouncycastle.crypto.params.ParametersWithIV;
import net.minecraft.v1_5_R3.org.bouncycastle.crypto.CipherParameters;
import net.minecraft.v1_5_R3.org.bouncycastle.crypto.BlockCipher;

public class CFBBlockCipher implements BlockCipher
{
    private byte[] a;
    private byte[] b;
    private byte[] c;
    private int d;
    private BlockCipher e;
    private boolean f;
    
    public CFBBlockCipher(final BlockCipher e, final int n) {
        this.e = null;
        this.e = e;
        this.d = n / 8;
        this.a = new byte[e.b()];
        this.b = new byte[e.b()];
        this.c = new byte[e.b()];
    }
    
    public void a(final boolean f, final CipherParameters cipherParameters) {
        this.f = f;
        if (cipherParameters instanceof ParametersWithIV) {
            final ParametersWithIV parametersWithIV = (ParametersWithIV)cipherParameters;
            final byte[] a = parametersWithIV.a();
            if (a.length < this.a.length) {
                System.arraycopy(a, 0, this.a, this.a.length - a.length, a.length);
                for (int i = 0; i < this.a.length - a.length; ++i) {
                    this.a[i] = 0;
                }
            }
            else {
                System.arraycopy(a, 0, this.a, 0, this.a.length);
            }
            this.c();
            if (parametersWithIV.b() != null) {
                this.e.a(true, parametersWithIV.b());
            }
        }
        else {
            this.c();
            this.e.a(true, cipherParameters);
        }
    }
    
    public String a() {
        return this.e.a() + "/CFB" + this.d * 8;
    }
    
    public int b() {
        return this.d;
    }
    
    public int a(final byte[] array, final int n, final byte[] array2, final int n2) {
        return this.f ? this.b(array, n, array2, n2) : this.c(array, n, array2, n2);
    }
    
    public int b(final byte[] array, final int n, final byte[] array2, final int n2) {
        if (n + this.d > array.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (n2 + this.d > array2.length) {
            throw new DataLengthException("output buffer too short");
        }
        this.e.a(this.b, 0, this.c, 0);
        for (int i = 0; i < this.d; ++i) {
            array2[n2 + i] = (byte)(this.c[i] ^ array[n + i]);
        }
        System.arraycopy(this.b, this.d, this.b, 0, this.b.length - this.d);
        System.arraycopy(array2, n2, this.b, this.b.length - this.d, this.d);
        return this.d;
    }
    
    public int c(final byte[] array, final int n, final byte[] array2, final int n2) {
        if (n + this.d > array.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (n2 + this.d > array2.length) {
            throw new DataLengthException("output buffer too short");
        }
        this.e.a(this.b, 0, this.c, 0);
        System.arraycopy(this.b, this.d, this.b, 0, this.b.length - this.d);
        System.arraycopy(array, n, this.b, this.b.length - this.d, this.d);
        for (int i = 0; i < this.d; ++i) {
            array2[n2 + i] = (byte)(this.c[i] ^ array[n + i]);
        }
        return this.d;
    }
    
    public void c() {
        System.arraycopy(this.a, 0, this.b, 0, this.a.length);
        this.e.c();
    }
}
