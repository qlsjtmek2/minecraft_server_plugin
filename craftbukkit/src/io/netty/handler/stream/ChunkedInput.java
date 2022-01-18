// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.stream;

public interface ChunkedInput<B>
{
    boolean isEndOfInput() throws Exception;
    
    void close() throws Exception;
    
    boolean readChunk(final B p0) throws Exception;
}
