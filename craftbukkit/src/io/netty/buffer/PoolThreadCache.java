// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.buffer;

import java.nio.ByteBuffer;

final class PoolThreadCache
{
    final PoolArena<byte[]> heapArena;
    final PoolArena<ByteBuffer> directArena;
    
    PoolThreadCache(final PoolArena<byte[]> heapArena, final PoolArena<ByteBuffer> directArena) {
        this.heapArena = heapArena;
        this.directArena = directArena;
    }
}
