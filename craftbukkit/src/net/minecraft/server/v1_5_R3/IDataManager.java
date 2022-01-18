// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.UUID;
import java.io.File;

public interface IDataManager
{
    WorldData getWorldData();
    
    void checkSession() throws ExceptionWorldConflict;
    
    IChunkLoader createChunkLoader(final WorldProvider p0);
    
    void saveWorldData(final WorldData p0, final NBTTagCompound p1);
    
    void saveWorldData(final WorldData p0);
    
    IPlayerFileData getPlayerFileData();
    
    void a();
    
    File getDataFile(final String p0);
    
    String g();
    
    UUID getUUID();
}
