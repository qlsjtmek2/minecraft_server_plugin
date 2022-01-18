// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public interface IMojangStatistics
{
    void a(final MojangStatisticsGenerator p0);
    
    void b(final MojangStatisticsGenerator p0);
    
    boolean getSnooperEnabled();
    
    IConsoleLogManager getLogger();
}
