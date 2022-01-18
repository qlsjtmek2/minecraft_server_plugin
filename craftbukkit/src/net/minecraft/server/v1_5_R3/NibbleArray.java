// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Arrays;

public class NibbleArray
{
    private byte[] a;
    private final int b;
    private final int c;
    private byte trivialValue;
    private byte trivialByte;
    private int length;
    private static final int LEN2K = 2048;
    private static final byte[][] TrivLen2k;
    
    public void detectAndProcessTrivialArray() {
        this.trivialValue = (byte)(this.a[0] & 0xF);
        this.trivialByte = (byte)(this.trivialValue | this.trivialValue << 4);
        for (int i = 0; i < this.a.length; ++i) {
            if (this.a[i] != this.trivialByte) {
                return;
            }
        }
        this.length = this.a.length;
        this.a = null;
    }
    
    public void forceToNonTrivialArray() {
        if (this.a == null) {
            this.a = new byte[this.length];
            if (this.trivialByte != 0) {
                Arrays.fill(this.a, this.trivialByte);
            }
        }
    }
    
    public boolean isTrivialArray() {
        return this.a == null;
    }
    
    public int getTrivialArrayValue() {
        return this.trivialValue;
    }
    
    public int getByteLength() {
        if (this.a == null) {
            return this.length;
        }
        return this.a.length;
    }
    
    public byte[] getValueArray() {
        if (this.a != null) {
            return this.a;
        }
        byte[] rslt;
        if (this.length == 2048) {
            rslt = NibbleArray.TrivLen2k[this.trivialValue];
        }
        else {
            rslt = new byte[this.length];
            if (this.trivialByte != 0) {
                Arrays.fill(rslt, this.trivialByte);
            }
        }
        return rslt;
    }
    
    public int copyToByteArray(final byte[] dest, final int off) {
        if (this.a == null) {
            Arrays.fill(dest, off, off + this.length, this.trivialByte);
            return off + this.length;
        }
        System.arraycopy(this.a, 0, dest, off, this.a.length);
        return off + this.a.length;
    }
    
    public void resizeArray(final int len) {
        if (this.a == null) {
            this.length = len;
        }
        else if (this.a.length != len) {
            final byte[] newa = new byte[len];
            System.arraycopy(this.a, 0, newa, 0, (this.a.length > len) ? len : this.a.length);
            this.a = newa;
        }
    }
    
    public NibbleArray(final int i, final int j) {
        this.a = null;
        this.length = i >> 1;
        final boolean b = false;
        this.trivialValue = (byte)(b ? 1 : 0);
        this.trivialByte = (byte)(b ? 1 : 0);
        this.b = j;
        this.c = j + 4;
    }
    
    public NibbleArray(final byte[] abyte, final int i) {
        this.a = abyte;
        this.b = i;
        this.c = i + 4;
        this.detectAndProcessTrivialArray();
    }
    
    public int a(final int i, final int j, final int k) {
        if (this.a == null) {
            return this.trivialValue;
        }
        final int l = j << this.c | k << this.b | i;
        final int i2 = l >> 1;
        final int j2 = l & 0x1;
        return (j2 == 0) ? (this.a[i2] & 0xF) : (this.a[i2] >> 4 & 0xF);
    }
    
    public void a(final int i, final int j, final int k, final int l) {
        if (this.a == null) {
            if (l == this.trivialValue) {
                return;
            }
            this.a = new byte[this.length];
            if (this.trivialByte != 0) {
                Arrays.fill(this.a, this.trivialByte);
            }
        }
        final int i2 = j << this.c | k << this.b | i;
        final int j2 = i2 >> 1;
        final int k2 = i2 & 0x1;
        if (k2 == 0) {
            this.a[j2] = (byte)((this.a[j2] & 0xF0) | (l & 0xF));
        }
        else {
            this.a[j2] = (byte)((this.a[j2] & 0xF) | (l & 0xF) << 4);
        }
    }
    
    static {
        TrivLen2k = new byte[16][];
        for (int i = 0; i < 16; ++i) {
            Arrays.fill(NibbleArray.TrivLen2k[i] = new byte[2048], (byte)(i | i << 4));
        }
    }
}
