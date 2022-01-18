// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public interface ICommandListener
{
    String getName();
    
    void sendMessage(final String p0);
    
    boolean a(final int p0, final String p1);
    
    String a(final String p0, final Object... p1);
    
    ChunkCoordinates b();
}
