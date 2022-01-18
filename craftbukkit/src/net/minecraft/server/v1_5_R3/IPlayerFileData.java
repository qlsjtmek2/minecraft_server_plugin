// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public interface IPlayerFileData
{
    void save(final EntityHuman p0);
    
    NBTTagCompound load(final EntityHuman p0);
    
    String[] getSeenPlayers();
}
