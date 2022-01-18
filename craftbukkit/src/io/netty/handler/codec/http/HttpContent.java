// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.http;

import io.netty.buffer.ByteBufHolder;

public interface HttpContent extends HttpObject, ByteBufHolder
{
    HttpContent copy();
    
    HttpContent retain();
    
    HttpContent retain(final int p0);
}
