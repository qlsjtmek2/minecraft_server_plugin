// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.v1_5_R3.org.bouncycastle.crypto.params;

import net.minecraft.v1_5_R3.org.bouncycastle.crypto.CipherParameters;

public class KeyParameter implements CipherParameters
{
    private byte[] a;
    
    public KeyParameter(final byte[] array) {
        this(array, 0, array.length);
    }
    
    public KeyParameter(final byte[] array, final int n, final int n2) {
        System.arraycopy(array, n, this.a = new byte[n2], 0, n2);
    }
    
    public byte[] a() {
        return this.a;
    }
}
