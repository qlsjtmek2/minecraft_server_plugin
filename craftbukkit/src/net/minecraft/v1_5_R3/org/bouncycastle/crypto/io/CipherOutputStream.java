// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.v1_5_R3.org.bouncycastle.crypto.io;

import java.io.IOException;
import java.io.OutputStream;
import net.minecraft.v1_5_R3.org.bouncycastle.crypto.StreamCipher;
import net.minecraft.v1_5_R3.org.bouncycastle.crypto.BufferedBlockCipher;
import java.io.FilterOutputStream;

public class CipherOutputStream extends FilterOutputStream
{
    private BufferedBlockCipher a;
    private StreamCipher b;
    private byte[] c;
    private byte[] d;
    
    public CipherOutputStream(final OutputStream outputStream, final BufferedBlockCipher a) {
        super(outputStream);
        this.c = new byte[1];
        this.a = a;
        this.d = new byte[a.a()];
    }
    
    public void write(final int n) {
        this.c[0] = (byte)n;
        if (this.a != null) {
            final int a = this.a.a(this.c, 0, 1, this.d, 0);
            if (a != 0) {
                this.out.write(this.d, 0, a);
            }
        }
        else {
            this.out.write(this.b.a((byte)n));
        }
    }
    
    public void write(final byte[] array) {
        this.write(array, 0, array.length);
    }
    
    public void write(final byte[] array, final int n, final int n2) {
        if (this.a != null) {
            final byte[] array2 = new byte[this.a.b(n2)];
            final int a = this.a.a(array, n, n2, array2, 0);
            if (a != 0) {
                this.out.write(array2, 0, a);
            }
        }
        else {
            final byte[] array3 = new byte[n2];
            this.b.a(array, n, n2, array3, 0);
            this.out.write(array3, 0, n2);
        }
    }
    
    public void flush() {
        super.flush();
    }
    
    public void close() {
        try {
            if (this.a != null) {
                final byte[] array = new byte[this.a.b(0)];
                final int a = this.a.a(array, 0);
                if (a != 0) {
                    this.out.write(array, 0, a);
                }
            }
        }
        catch (Exception ex) {
            throw new IOException("Error closing stream: " + ex.toString());
        }
        this.flush();
        super.close();
    }
}
