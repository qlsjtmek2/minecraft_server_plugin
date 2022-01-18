// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class OldChunkLoader
{
    public static OldChunk a(final NBTTagCompound nbttagcompound) {
        final int i = nbttagcompound.getInt("xPos");
        final int j = nbttagcompound.getInt("zPos");
        final OldChunk oldchunk = new OldChunk(i, j);
        oldchunk.g = nbttagcompound.getByteArray("Blocks");
        oldchunk.f = new OldNibbleArray(nbttagcompound.getByteArray("Data"), 7);
        oldchunk.e = new OldNibbleArray(nbttagcompound.getByteArray("SkyLight"), 7);
        oldchunk.d = new OldNibbleArray(nbttagcompound.getByteArray("BlockLight"), 7);
        oldchunk.c = nbttagcompound.getByteArray("HeightMap");
        oldchunk.b = nbttagcompound.getBoolean("TerrainPopulated");
        oldchunk.h = nbttagcompound.getList("Entities");
        oldchunk.i = nbttagcompound.getList("TileEntities");
        oldchunk.j = nbttagcompound.getList("TileTicks");
        try {
            oldchunk.a = nbttagcompound.getLong("LastUpdate");
        }
        catch (ClassCastException classcastexception) {
            oldchunk.a = nbttagcompound.getInt("LastUpdate");
        }
        return oldchunk;
    }
    
    public static void a(final OldChunk oldchunk, final NBTTagCompound nbttagcompound, final WorldChunkManager worldchunkmanager) {
        nbttagcompound.setInt("xPos", oldchunk.k);
        nbttagcompound.setInt("zPos", oldchunk.l);
        nbttagcompound.setLong("LastUpdate", oldchunk.a);
        final int[] aint = new int[oldchunk.c.length];
        for (int i = 0; i < oldchunk.c.length; ++i) {
            aint[i] = oldchunk.c[i];
        }
        nbttagcompound.setIntArray("HeightMap", aint);
        nbttagcompound.setBoolean("TerrainPopulated", oldchunk.b);
        final NBTTagList nbttaglist = new NBTTagList("Sections");
        for (int k = 0; k < 8; ++k) {
            boolean flag = true;
            for (int j = 0; j < 16 && flag; ++j) {
                for (int l = 0; l < 16 && flag; ++l) {
                    for (int i2 = 0; i2 < 16; ++i2) {
                        final int j2 = j << 11 | i2 << 7 | l + (k << 4);
                        final byte b0 = oldchunk.g[j2];
                        if (b0 != 0) {
                            flag = false;
                            break;
                        }
                    }
                }
            }
            if (!flag) {
                final byte[] abyte = new byte[4096];
                final NibbleArray nibblearray = new NibbleArray(abyte.length, 4);
                final NibbleArray nibblearray2 = new NibbleArray(abyte.length, 4);
                final NibbleArray nibblearray3 = new NibbleArray(abyte.length, 4);
                for (int k2 = 0; k2 < 16; ++k2) {
                    for (int l2 = 0; l2 < 16; ++l2) {
                        for (int i3 = 0; i3 < 16; ++i3) {
                            final int j3 = k2 << 11 | i3 << 7 | l2 + (k << 4);
                            final byte b2 = oldchunk.g[j3];
                            abyte[l2 << 8 | i3 << 4 | k2] = (byte)(b2 & 0xFF);
                            nibblearray.a(k2, l2, i3, oldchunk.f.a(k2, l2 + (k << 4), i3));
                            nibblearray2.a(k2, l2, i3, oldchunk.e.a(k2, l2 + (k << 4), i3));
                            nibblearray3.a(k2, l2, i3, oldchunk.d.a(k2, l2 + (k << 4), i3));
                        }
                    }
                }
                final NBTTagCompound nbttagcompound2 = new NBTTagCompound();
                nbttagcompound2.setByte("Y", (byte)(k & 0xFF));
                nbttagcompound2.setByteArray("Blocks", abyte);
                nbttagcompound2.setByteArray("Data", nibblearray.getValueArray());
                nbttagcompound2.setByteArray("SkyLight", nibblearray2.getValueArray());
                nbttagcompound2.setByteArray("BlockLight", nibblearray3.getValueArray());
                nbttaglist.add(nbttagcompound2);
            }
        }
        nbttagcompound.set("Sections", nbttaglist);
        final byte[] abyte2 = new byte[256];
        for (int k3 = 0; k3 < 16; ++k3) {
            for (int j = 0; j < 16; ++j) {
                abyte2[j << 4 | k3] = (byte)(worldchunkmanager.getBiome(oldchunk.k << 4 | k3, oldchunk.l << 4 | j).id & 0xFF);
            }
        }
        nbttagcompound.setByteArray("Biomes", abyte2);
        nbttagcompound.set("Entities", oldchunk.h);
        nbttagcompound.set("TileEntities", oldchunk.i);
        if (oldchunk.j != null) {
            nbttagcompound.set("TileTicks", oldchunk.j);
        }
    }
}
