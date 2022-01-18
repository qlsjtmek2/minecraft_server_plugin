// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.File;
import java.io.FilenameFilter;

class ChunkFilenameFilter implements FilenameFilter
{
    final /* synthetic */ WorldLoaderServer a;
    
    ChunkFilenameFilter(final WorldLoaderServer a) {
        this.a = a;
    }
    
    public boolean accept(final File file, final String s) {
        return s.endsWith(".mcr");
    }
}
