// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.ArrayList;
import java.util.List;

class PlayerChunk
{
    private final List b;
    private final ChunkCoordIntPair location;
    private short[] dirtyBlocks;
    private int dirtyCount;
    private int f;
    private boolean loaded;
    final PlayerChunkMap playerChunkMap;
    
    public PlayerChunk(final PlayerChunkMap playerchunkmap, final int i, final int j) {
        this.loaded = false;
        this.playerChunkMap = playerchunkmap;
        this.b = new ArrayList();
        this.dirtyBlocks = new short[64];
        this.dirtyCount = 0;
        this.location = new ChunkCoordIntPair(i, j);
        playerchunkmap.a().chunkProviderServer.getChunkAt(i, j, new Runnable() {
            public void run() {
                PlayerChunk.this.loaded = true;
            }
        });
    }
    
    public void a(final EntityPlayer entityplayer) {
        if (this.b.contains(entityplayer)) {
            throw new IllegalStateException("Failed to add player. " + entityplayer + " already is in chunk " + this.location.x + ", " + this.location.z);
        }
        this.b.add(entityplayer);
        if (this.loaded) {
            entityplayer.chunkCoordIntPairQueue.add(this.location);
        }
        else {
            this.playerChunkMap.a().chunkProviderServer.getChunkAt(this.location.x, this.location.z, new Runnable() {
                public void run() {
                    entityplayer.chunkCoordIntPairQueue.add(PlayerChunk.this.location);
                }
            });
        }
    }
    
    public void b(final EntityPlayer entityplayer) {
        if (this.b.contains(entityplayer)) {
            entityplayer.playerConnection.sendPacket(new Packet51MapChunk(this.location.x, this.location.z));
            this.b.remove(entityplayer);
            entityplayer.chunkCoordIntPairQueue.remove(this.location);
            if (this.b.isEmpty()) {
                final long i = this.location.x + 2147483647L | this.location.z + 2147483647L << 32;
                PlayerChunkMap.b(this.playerChunkMap).remove(i);
                if (this.dirtyCount > 0) {
                    PlayerChunkMap.c(this.playerChunkMap).remove(this);
                }
                this.playerChunkMap.a().chunkProviderServer.queueUnload(this.location.x, this.location.z);
            }
        }
    }
    
    public void a(final int i, final int j, final int k) {
        if (this.dirtyCount == 0) {
            PlayerChunkMap.c(this.playerChunkMap).add(this);
        }
        this.f |= 1 << (j >> 4);
        if (this.dirtyCount < 64) {
            final short short1 = (short)(i << 12 | k << 8 | j);
            for (int l = 0; l < this.dirtyCount; ++l) {
                if (this.dirtyBlocks[l] == short1) {
                    return;
                }
            }
            this.dirtyBlocks[this.dirtyCount++] = short1;
        }
    }
    
    public void sendAll(final Packet packet) {
        for (int i = 0; i < this.b.size(); ++i) {
            final EntityPlayer entityplayer = this.b.get(i);
            if (!entityplayer.chunkCoordIntPairQueue.contains(this.location)) {
                entityplayer.playerConnection.sendPacket(packet);
            }
        }
    }
    
    public void a() {
        if (this.dirtyCount != 0) {
            if (this.dirtyCount == 1) {
                final int i = this.location.x * 16 + (this.dirtyBlocks[0] >> 12 & 0xF);
                final int j = this.dirtyBlocks[0] & 0xFF;
                final int k = this.location.z * 16 + (this.dirtyBlocks[0] >> 8 & 0xF);
                this.sendAll(new Packet53BlockChange(i, j, k, PlayerChunkMap.a(this.playerChunkMap)));
                if (PlayerChunkMap.a(this.playerChunkMap).isTileEntity(i, j, k)) {
                    this.sendTileEntity(PlayerChunkMap.a(this.playerChunkMap).getTileEntity(i, j, k));
                }
            }
            else if (this.dirtyCount == 64) {
                final int i = this.location.x * 16;
                final int j = this.location.z * 16;
                this.sendAll(new Packet51MapChunk(PlayerChunkMap.a(this.playerChunkMap).getChunkAt(this.location.x, this.location.z), this.f == 65535, this.f));
                for (int k = 0; k < 16; ++k) {
                    if ((this.f & 1 << k) != 0x0) {
                        final int l = k << 4;
                        final List list = PlayerChunkMap.a(this.playerChunkMap).getTileEntities(i, l, j, i + 16, l + 16, j + 16);
                        for (int i2 = 0; i2 < list.size(); ++i2) {
                            this.sendTileEntity(list.get(i2));
                        }
                    }
                }
            }
            else {
                this.sendAll(new Packet52MultiBlockChange(this.location.x, this.location.z, this.dirtyBlocks, this.dirtyCount, PlayerChunkMap.a(this.playerChunkMap)));
                for (int i = 0; i < this.dirtyCount; ++i) {
                    final int j = this.location.x * 16 + (this.dirtyBlocks[i] >> 12 & 0xF);
                    final int k = this.dirtyBlocks[i] & 0xFF;
                    final int l = this.location.z * 16 + (this.dirtyBlocks[i] >> 8 & 0xF);
                    if (PlayerChunkMap.a(this.playerChunkMap).isTileEntity(j, k, l)) {
                        this.sendTileEntity(PlayerChunkMap.a(this.playerChunkMap).getTileEntity(j, k, l));
                    }
                }
            }
            this.dirtyCount = 0;
            this.f = 0;
        }
    }
    
    private void sendTileEntity(final TileEntity tileentity) {
        if (tileentity != null) {
            final Packet packet = tileentity.getUpdatePacket();
            if (packet != null) {
                this.sendAll(packet);
            }
        }
    }
    
    static ChunkCoordIntPair a(final PlayerChunk playerchunk) {
        return playerchunk.location;
    }
    
    static List b(final PlayerChunk playerchunk) {
        return playerchunk.b;
    }
}
