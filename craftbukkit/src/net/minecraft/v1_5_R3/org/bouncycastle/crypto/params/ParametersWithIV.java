// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.v1_5_R3.org.bouncycastle.crypto.params;

import net.minecraft.v1_5_R3.org.bouncycastle.crypto.CipherParameters;

public class ParametersWithIV implements CipherParameters
{
    private byte[] a;
    private CipherParameters b;
    
    public ParametersWithIV(final CipherParameters b, final byte[] array, final int n, final int n2) {
        this.a = new byte[n2];
        this.b = b;
        System.arraycopy(array, n, this.a, 0, n2);
    }
    
    public byte[] a() {
        return this.a;
    }
    
    public CipherParameters b() {
        return this.b;
    }
}
