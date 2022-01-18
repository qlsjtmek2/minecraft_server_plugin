// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.util;

import java.io.File;
import java.io.FilenameFilter;

public class DatFileFilter implements FilenameFilter
{
    public boolean accept(final File dir, final String name) {
        return name.endsWith(".dat");
    }
}
