// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import java.io.IOException;
import java.nio.channels.WritableByteChannel;
import io.netty.buffer.ReferenceCounted;

public interface FileRegion extends ReferenceCounted
{
    long position();
    
    long count();
    
    long transferTo(final WritableByteChannel p0, final long p1) throws IOException;
}
