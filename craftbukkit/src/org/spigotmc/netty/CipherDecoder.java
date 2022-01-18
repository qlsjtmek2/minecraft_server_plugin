// 
// Decompiled by Procyon v0.5.30
// 

package org.spigotmc.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import javax.crypto.Cipher;
import io.netty.handler.codec.ByteToByteDecoder;

class CipherDecoder extends ByteToByteDecoder
{
    private final CipherBase cipher;
    
    public CipherDecoder(final Cipher cipher) {
        this.cipher = new CipherBase(cipher);
    }
    
    protected void decode(final ChannelHandlerContext ctx, final ByteBuf in, final ByteBuf out) throws Exception {
        this.cipher.cipher(in, out);
    }
}
