// 
// Decompiled by Procyon v0.5.30
// 

package org.spigotmc.netty;

import javax.crypto.ShortBufferException;
import io.netty.buffer.ByteBuf;
import javax.crypto.Cipher;

class CipherBase
{
    private final Cipher cipher;
    private ThreadLocal<byte[]> heapInLocal;
    private ThreadLocal<byte[]> heapOutLocal;
    
    protected CipherBase(final Cipher cipher) {
        this.heapInLocal = new EmptyByteThreadLocal();
        this.heapOutLocal = new EmptyByteThreadLocal();
        this.cipher = cipher;
    }
    
    protected void cipher(final ByteBuf in, final ByteBuf out) throws ShortBufferException {
        byte[] heapIn = this.heapInLocal.get();
        final int readableBytes = in.readableBytes();
        if (heapIn.length < readableBytes) {
            heapIn = new byte[readableBytes];
            this.heapInLocal.set(heapIn);
        }
        in.readBytes(heapIn, 0, readableBytes);
        byte[] heapOut = this.heapOutLocal.get();
        final int outputSize = this.cipher.getOutputSize(readableBytes);
        if (heapOut.length < outputSize) {
            heapOut = new byte[outputSize];
            this.heapOutLocal.set(heapOut);
        }
        out.writeBytes(heapOut, 0, this.cipher.update(heapIn, 0, readableBytes, heapOut));
    }
    
    private static class EmptyByteThreadLocal extends ThreadLocal<byte[]>
    {
        protected byte[] initialValue() {
            return new byte[0];
        }
    }
}
