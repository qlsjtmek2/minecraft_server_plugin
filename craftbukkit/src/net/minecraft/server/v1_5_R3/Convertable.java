// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public interface Convertable
{
    IDataManager a(final String p0, final boolean p1);
    
    void d();
    
    boolean e(final String p0);
    
    boolean isConvertable(final String p0);
    
    boolean convert(final String p0, final IProgressUpdate p1);
}
