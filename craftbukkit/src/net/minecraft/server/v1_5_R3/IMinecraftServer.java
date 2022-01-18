// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public interface IMinecraftServer
{
    int a(final String p0, final int p1);
    
    String a(final String p0, final String p1);
    
    void a(final String p0, final Object p1);
    
    void a();
    
    String b_();
    
    String u();
    
    int v();
    
    String w();
    
    String getVersion();
    
    int y();
    
    int z();
    
    String[] getPlayers();
    
    String J();
    
    String getPlugins();
    
    String h(final String p0);
    
    boolean isDebugging();
    
    void info(final String p0);
    
    void warning(final String p0);
    
    void i(final String p0);
    
    void j(final String p0);
}
