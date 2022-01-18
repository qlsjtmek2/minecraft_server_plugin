// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Collection;
import java.util.Comparator;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.ArrayList;
import java.util.Queue;
import java.util.List;

public class PlayerChunkMap
{
    private final WorldServer world;
    private final List managedPlayers;
    private final LongHashMap c;
    private final Queue d;
    private final int e;
    private final int[][] f;
    private boolean wasNotEmpty;
    
    public PlayerChunkMap(final WorldServer worldserver, final int i) {
        this.managedPlayers = new ArrayList();
        this.c = new LongHashMap();
        this.d = new ConcurrentLinkedQueue();
        this.f = new int[][] { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
        if (i > 15) {
            throw new IllegalArgumentException("Too big view radius!");
        }
        if (i < 1) {
            throw new IllegalArgumentException("Too small view radius!");
        }
        this.e = i;
        this.world = worldserver;
    }
    
    public WorldServer a() {
        return this.world;
    }
    
    public void flush() {
        final Iterator iterator = this.d.iterator();
        while (iterator.hasNext()) {
            final PlayerChunk playerinstance = iterator.next();
            playerinstance.a();
            iterator.remove();
        }
        if (this.managedPlayers.isEmpty()) {
            if (!this.wasNotEmpty) {
                return;
            }
            final WorldProvider worldprovider = this.world.worldProvider;
            if (!worldprovider.e()) {
                this.world.chunkProviderServer.a();
            }
            this.wasNotEmpty = false;
        }
        else {
            this.wasNotEmpty = true;
        }
    }
    
    private PlayerChunk a(final int i, final int j, final boolean flag) {
        final long k = i + 2147483647L | j + 2147483647L << 32;
        PlayerChunk playerchunk = (PlayerChunk)this.c.getEntry(k);
        if (playerchunk == null && flag) {
            playerchunk = new PlayerChunk(this, i, j);
            this.c.put(k, playerchunk);
        }
        return playerchunk;
    }
    
    public final boolean isChunkInUse(final int x, final int z) {
        final PlayerChunk pi = this.a(x, z, false);
        return pi != null && PlayerChunk.b(pi).size() > 0;
    }
    
    public void flagDirty(final int i, final int j, final int k) {
        final int l = i >> 4;
        final int i2 = k >> 4;
        final PlayerChunk playerchunk = this.a(l, i2, false);
        if (playerchunk != null) {
            playerchunk.a(i & 0xF, j, k & 0xF);
        }
    }
    
    public void addPlayer(final EntityPlayer entityplayer) {
        final int i = (int)entityplayer.locX >> 4;
        final int j = (int)entityplayer.locZ >> 4;
        entityplayer.d = entityplayer.locX;
        entityplayer.e = entityplayer.locZ;
        final List<ChunkCoordIntPair> chunkList = new LinkedList<ChunkCoordIntPair>();
        for (int k = i - this.e; k <= i + this.e; ++k) {
            for (int l = j - this.e; l <= j + this.e; ++l) {
                chunkList.add(new ChunkCoordIntPair(k, l));
            }
        }
        Collections.sort(chunkList, new ChunkCoordComparator(entityplayer));
        for (final ChunkCoordIntPair pair : chunkList) {
            this.a(pair.x, pair.z, true).a(entityplayer);
        }
        this.managedPlayers.add(entityplayer);
        this.b(entityplayer);
    }
    
    public void b(final EntityPlayer entityplayer) {
        final ArrayList arraylist = new ArrayList(entityplayer.chunkCoordIntPairQueue);
        int i = 0;
        final int j = this.e;
        final int k = (int)entityplayer.locX >> 4;
        final int l = (int)entityplayer.locZ >> 4;
        int i2 = 0;
        int j2 = 0;
        ChunkCoordIntPair chunkcoordintpair = PlayerChunk.a(this.a(k, l, true));
        entityplayer.chunkCoordIntPairQueue.clear();
        if (arraylist.contains(chunkcoordintpair)) {
            entityplayer.chunkCoordIntPairQueue.add(chunkcoordintpair);
        }
        for (int k2 = 1; k2 <= j * 2; ++k2) {
            for (int l2 = 0; l2 < 2; ++l2) {
                final int[] aint = this.f[i++ % 4];
                for (int i3 = 0; i3 < k2; ++i3) {
                    i2 += aint[0];
                    j2 += aint[1];
                    chunkcoordintpair = PlayerChunk.a(this.a(k + i2, l + j2, true));
                    if (arraylist.contains(chunkcoordintpair)) {
                        entityplayer.chunkCoordIntPairQueue.add(chunkcoordintpair);
                    }
                }
            }
        }
        i %= 4;
        for (int k2 = 0; k2 < j * 2; ++k2) {
            i2 += this.f[i][0];
            j2 += this.f[i][1];
            chunkcoordintpair = PlayerChunk.a(this.a(k + i2, l + j2, true));
            if (arraylist.contains(chunkcoordintpair)) {
                entityplayer.chunkCoordIntPairQueue.add(chunkcoordintpair);
            }
        }
    }
    
    public void removePlayer(final EntityPlayer entityplayer) {
        final int i = (int)entityplayer.d >> 4;
        final int j = (int)entityplayer.e >> 4;
        for (int k = i - this.e; k <= i + this.e; ++k) {
            for (int l = j - this.e; l <= j + this.e; ++l) {
                final PlayerChunk playerchunk = this.a(k, l, false);
                if (playerchunk != null) {
                    playerchunk.b(entityplayer);
                }
            }
        }
        this.managedPlayers.remove(entityplayer);
    }
    
    private boolean a(final int i, final int j, final int k, final int l, final int i1) {
        final int j2 = i - k;
        final int k2 = j - l;
        return j2 >= -i1 && j2 <= i1 && (k2 >= -i1 && k2 <= i1);
    }
    
    public void movePlayer(final EntityPlayer entityplayer) {
        final int i = (int)entityplayer.locX >> 4;
        final int j = (int)entityplayer.locZ >> 4;
        final double d0 = entityplayer.d - entityplayer.locX;
        final double d2 = entityplayer.e - entityplayer.locZ;
        final double d3 = d0 * d0 + d2 * d2;
        if (d3 >= 64.0) {
            final int k = (int)entityplayer.d >> 4;
            final int l = (int)entityplayer.e >> 4;
            final int i2 = this.e;
            final int j2 = i - k;
            final int k2 = j - l;
            final List<ChunkCoordIntPair> chunksToLoad = new LinkedList<ChunkCoordIntPair>();
            if (j2 != 0 || k2 != 0) {
                for (int l2 = i - i2; l2 <= i + i2; ++l2) {
                    for (int i3 = j - i2; i3 <= j + i2; ++i3) {
                        if (!this.a(l2, i3, k, l, i2)) {
                            chunksToLoad.add(new ChunkCoordIntPair(l2, i3));
                        }
                        if (!this.a(l2 - j2, i3 - k2, i, j, i2)) {
                            final PlayerChunk playerchunk = this.a(l2 - j2, i3 - k2, false);
                            if (playerchunk != null) {
                                playerchunk.b(entityplayer);
                            }
                        }
                    }
                }
                this.b(entityplayer);
                entityplayer.d = entityplayer.locX;
                entityplayer.e = entityplayer.locZ;
                Collections.sort(chunksToLoad, new ChunkCoordComparator(entityplayer));
                for (final ChunkCoordIntPair pair : chunksToLoad) {
                    this.a(pair.x, pair.z, true).a(entityplayer);
                }
                if (i2 > 1 || i2 < -1 || j2 > 1 || j2 < -1) {
                    Collections.sort((List<Object>)entityplayer.chunkCoordIntPairQueue, (Comparator<? super Object>)new ChunkCoordComparator(entityplayer));
                }
            }
        }
    }
    
    public boolean a(final EntityPlayer entityplayer, final int i, final int j) {
        final PlayerChunk playerchunk = this.a(i, j, false);
        return playerchunk != null && (PlayerChunk.b(playerchunk).contains(entityplayer) && !entityplayer.chunkCoordIntPairQueue.contains(PlayerChunk.a(playerchunk)));
    }
    
    public static int getFurthestViewableBlock(final int i) {
        return i * 16 - 16;
    }
    
    static WorldServer a(final PlayerChunkMap playerchunkmap) {
        return playerchunkmap.world;
    }
    
    static LongHashMap b(final PlayerChunkMap playerchunkmap) {
        return playerchunkmap.c;
    }
    
    static Queue c(final PlayerChunkMap playermanager) {
        return playermanager.d;
    }
    
    private static class ChunkCoordComparator implements Comparator<ChunkCoordIntPair>
    {
        private int x;
        private int z;
        
        public ChunkCoordComparator(final EntityPlayer entityplayer) {
            this.x = (int)entityplayer.locX >> 4;
            this.z = (int)entityplayer.locZ >> 4;
        }
        
        public int compare(final ChunkCoordIntPair a, final ChunkCoordIntPair b) {
            if (a.equals(b)) {
                return 0;
            }
            final int ax = a.x - this.x;
            final int az = a.z - this.z;
            final int bx = b.x - this.x;
            final int bz = b.z - this.z;
            final int result = (ax - bx) * (ax + bx) + (az - bz) * (az + bz);
            if (result != 0) {
                return result;
            }
            if (ax < 0) {
                if (bx < 0) {
                    return bz - az;
                }
                return -1;
            }
            else {
                if (bx < 0) {
                    return 1;
                }
                return az - bz;
            }
        }
    }
}
