// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Collections;
import java.io.FilenameFilter;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.DataOutput;
import java.io.DataInput;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import java.io.File;

public class WorldLoaderServer extends WorldLoader
{
    public WorldLoaderServer(final File file) {
        super(file);
    }
    
    protected int c() {
        return 19133;
    }
    
    public void d() {
        RegionFileCache.a();
    }
    
    public IDataManager a(final String s, final boolean b) {
        return new ServerNBTManager(this.a, s, b);
    }
    
    public boolean isConvertable(final String s) {
        final WorldData c = this.c(s);
        return c != null && c.l() != this.c();
    }
    
    public boolean convert(final String s, final IProgressUpdate progressUpdate) {
        progressUpdate.a(0);
        final ArrayList list = new ArrayList();
        final ArrayList list2 = new ArrayList();
        final ArrayList list3 = new ArrayList();
        final File file = new File(this.a, s);
        final File file2 = new File(file, "DIM-1");
        final File file3 = new File(file, "DIM1");
        MinecraftServer.getServer().getLogger().info("Scanning folders...");
        this.a(file, list);
        if (file2.exists()) {
            this.a(file2, list2);
        }
        if (file3.exists()) {
            this.a(file3, list3);
        }
        final int n = list.size() + list2.size() + list3.size();
        MinecraftServer.getServer().getLogger().info("Total conversion count is " + n);
        final WorldData c = this.c(s);
        WorldChunkManager worldChunkManager;
        if (c.getType() == WorldType.FLAT) {
            worldChunkManager = new WorldChunkManagerHell(BiomeBase.PLAINS, 0.5f, 0.5f);
        }
        else {
            worldChunkManager = new WorldChunkManager(c.getSeed(), c.getType());
        }
        this.a(new File(file, "region"), list, worldChunkManager, 0, n, progressUpdate);
        this.a(new File(file2, "region"), list2, new WorldChunkManagerHell(BiomeBase.HELL, 1.0f, 0.0f), list.size(), n, progressUpdate);
        this.a(new File(file3, "region"), list3, new WorldChunkManagerHell(BiomeBase.SKY, 0.5f, 0.0f), list.size() + list2.size(), n, progressUpdate);
        c.e(19133);
        if (c.getType() == WorldType.NORMAL_1_1) {
            c.setType(WorldType.NORMAL);
        }
        this.g(s);
        this.a(s, false).saveWorldData(c);
        return true;
    }
    
    private void g(final String s) {
        final File file = new File(this.a, s);
        if (!file.exists()) {
            System.out.println("Warning: Unable to create level.dat_mcr backup");
            return;
        }
        final File file2 = new File(file, "level.dat");
        if (!file2.exists()) {
            System.out.println("Warning: Unable to create level.dat_mcr backup");
            return;
        }
        if (!file2.renameTo(new File(file, "level.dat_mcr"))) {
            System.out.println("Warning: Unable to create level.dat_mcr backup");
        }
    }
    
    private void a(final File file, final Iterable iterable, final WorldChunkManager worldChunkManager, int n, final int n2, final IProgressUpdate progressUpdate) {
        final Iterator<File> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            this.a(file, iterator.next(), worldChunkManager, n, n2, progressUpdate);
            ++n;
            progressUpdate.a((int)Math.round(100.0 * n / n2));
        }
    }
    
    private void a(final File file, final File file2, final WorldChunkManager worldchunkmanager, final int n, final int n2, final IProgressUpdate progressUpdate) {
        try {
            final String name = file2.getName();
            final RegionFile regionFile = new RegionFile(file2);
            final RegionFile regionFile2 = new RegionFile(new File(file, name.substring(0, name.length() - ".mcr".length()) + ".mca"));
            for (int i = 0; i < 32; ++i) {
                for (int j = 0; j < 32; ++j) {
                    if (regionFile.c(i, j) && !regionFile2.c(i, j)) {
                        final DataInputStream a = regionFile.a(i, j);
                        if (a == null) {
                            MinecraftServer.getServer().getLogger().warning("Failed to fetch input stream");
                        }
                        else {
                            final NBTTagCompound a2 = NBTCompressedStreamTools.a((DataInput)a);
                            a.close();
                            final OldChunk a3 = OldChunkLoader.a(a2.getCompound("Level"));
                            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                            final NBTTagCompound nbttagcompound = new NBTTagCompound();
                            nbtTagCompound.set("Level", nbttagcompound);
                            OldChunkLoader.a(a3, nbttagcompound, worldchunkmanager);
                            final DataOutputStream b = regionFile2.b(i, j);
                            NBTCompressedStreamTools.a(nbtTagCompound, (DataOutput)b);
                            b.close();
                        }
                    }
                }
                final int n3 = (int)Math.round(100.0 * (n * 1024) / (n2 * 1024));
                final int n4 = (int)Math.round(100.0 * ((i + 1) * 32 + n * 1024) / (n2 * 1024));
                if (n4 > n3) {
                    progressUpdate.a(n4);
                }
            }
            regionFile.c();
            regionFile2.c();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void a(final File file, final Collection collection) {
        final File[] listFiles = new File(file, "region").listFiles(new ChunkFilenameFilter(this));
        if (listFiles != null) {
            Collections.addAll(collection, listFiles);
        }
    }
}
