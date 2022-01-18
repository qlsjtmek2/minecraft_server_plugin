// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.ByteArrayOutputStream;

class ChunkBuffer extends ByteArrayOutputStream
{
    private int b;
    private int c;
    final /* synthetic */ RegionFile a;
    
    public ChunkBuffer(final RegionFile a, final int b, final int c) {
        this.a = a;
        super(8096);
        this.b = b;
        this.c = c;
    }
    
    public void close() {
        this.a.a(this.b, this.c, this.buf, this.count);
    }
}
