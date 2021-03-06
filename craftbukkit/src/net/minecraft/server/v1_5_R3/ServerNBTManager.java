// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.File;

public class ServerNBTManager extends WorldNBTStorage
{
    public ServerNBTManager(final File file1, final String s, final boolean flag) {
        super(file1, s, flag);
    }
    
    public IChunkLoader createChunkLoader(final WorldProvider worldProvider) {
        final File directory = this.getDirectory();
        if (worldProvider instanceof WorldProviderHell) {
            final File file1 = new File(directory, "DIM-1");
            file1.mkdirs();
            return new ChunkRegionLoader(file1);
        }
        if (worldProvider instanceof WorldProviderTheEnd) {
            final File file2 = new File(directory, "DIM1");
            file2.mkdirs();
            return new ChunkRegionLoader(file2);
        }
        return new ChunkRegionLoader(directory);
    }
    
    public void saveWorldData(final WorldData worlddata, final NBTTagCompound nbttagcompound) {
        worlddata.e(19133);
        super.saveWorldData(worlddata, nbttagcompound);
    }
    
    public void a() {
        try {
            FileIOThread.a.a();
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        RegionFileCache.a();
    }
}
