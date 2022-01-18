// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;

public class WorldManager implements IWorldAccess
{
    private MinecraftServer server;
    public WorldServer world;
    
    public WorldManager(final MinecraftServer minecraftserver, final WorldServer worldserver) {
        this.server = minecraftserver;
        this.world = worldserver;
    }
    
    public void a(final String s, final double d0, final double d1, final double d2, final double d3, final double d4, final double d5) {
    }
    
    public void a(final Entity entity) {
        this.world.getTracker().track(entity);
    }
    
    public void b(final Entity entity) {
        this.world.getTracker().untrackEntity(entity);
    }
    
    public void a(final String s, final double d0, final double d1, final double d2, final float f, final float f1) {
        this.server.getPlayerList().sendPacketNearby(d0, d1, d2, (f > 1.0f) ? ((double)(16.0f * f)) : 16.0, this.world.dimension, new Packet62NamedSoundEffect(s, d0, d1, d2, f, f1));
    }
    
    public void a(final EntityHuman entityhuman, final String s, final double d0, final double d1, final double d2, final float f, final float f1) {
        this.server.getPlayerList().sendPacketNearby(entityhuman, d0, d1, d2, (f > 1.0f) ? ((double)(16.0f * f)) : 16.0, this.world.dimension, new Packet62NamedSoundEffect(s, d0, d1, d2, f, f1));
    }
    
    public void a(final int i, final int j, final int k, final int l, final int i1, final int j1) {
    }
    
    public void a(final int i, final int j, final int k) {
        this.world.getPlayerChunkMap().flagDirty(i, j, k);
    }
    
    public void b(final int i, final int j, final int k) {
    }
    
    public void a(final String s, final int i, final int j, final int k) {
    }
    
    public void a(final EntityHuman entityhuman, final int i, final int j, final int k, final int l, final int i1) {
        this.server.getPlayerList().sendPacketNearby(entityhuman, j, k, l, 64.0, this.world.dimension, new Packet61WorldEvent(i, j, k, l, i1, false));
    }
    
    public void a(final int i, final int j, final int k, final int l, final int i1) {
        this.server.getPlayerList().sendAll(new Packet61WorldEvent(i, j, k, l, i1, true));
    }
    
    public void b(final int i, final int j, final int k, final int l, final int i1) {
        for (final EntityPlayer entityplayer : this.server.getPlayerList().players) {
            if (entityplayer != null && entityplayer.world == this.world && entityplayer.id != i) {
                final double d0 = j - entityplayer.locX;
                final double d2 = k - entityplayer.locY;
                final double d3 = l - entityplayer.locZ;
                if (d0 * d0 + d2 * d2 + d3 * d3 >= 1024.0) {
                    continue;
                }
                entityplayer.playerConnection.sendPacket(new Packet55BlockBreakAnimation(i, j, k, l, i1));
            }
        }
    }
}
