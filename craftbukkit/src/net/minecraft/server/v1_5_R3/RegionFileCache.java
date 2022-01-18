// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.HashMap;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.util.Iterator;
import java.io.IOException;
import java.io.File;
import java.util.Map;

public class RegionFileCache
{
    public static final Map a;
    
    public static synchronized RegionFile a(final File file1, final int i, final int j) {
        final File file2 = new File(file1, "region");
        final File file3 = new File(file2, "r." + (i >> 5) + "." + (j >> 5) + ".mca");
        final RegionFile regionfile = RegionFileCache.a.get(file3);
        if (regionfile != null) {
            return regionfile;
        }
        if (!file2.exists()) {
            file2.mkdirs();
        }
        if (RegionFileCache.a.size() >= 256) {
            a();
        }
        final RegionFile regionfile2 = new RegionFile(file3);
        RegionFileCache.a.put(file3, regionfile2);
        return regionfile2;
    }
    
    public static synchronized void a() {
        for (final RegionFile regionfile : RegionFileCache.a.values()) {
            try {
                if (regionfile == null) {
                    continue;
                }
                regionfile.c();
            }
            catch (IOException ioexception) {
                ioexception.printStackTrace();
            }
        }
        RegionFileCache.a.clear();
    }
    
    public static DataInputStream c(final File file1, final int i, final int j) {
        final RegionFile regionfile = a(file1, i, j);
        return regionfile.a(i & 0x1F, j & 0x1F);
    }
    
    public static DataOutputStream d(final File file1, final int i, final int j) {
        final RegionFile regionfile = a(file1, i, j);
        return regionfile.b(i & 0x1F, j & 0x1F);
    }
    
    static {
        a = new HashMap();
    }
}
