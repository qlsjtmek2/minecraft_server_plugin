// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Comparator;

public class PlayerDistanceComparator implements Comparator
{
    private final ChunkCoordinates a;
    
    public PlayerDistanceComparator(final ChunkCoordinates a) {
        this.a = a;
    }
    
    public int a(final EntityPlayer entityPlayer, final EntityPlayer entityPlayer2) {
        final double e = entityPlayer.e(this.a.x, this.a.y, this.a.z);
        final double e2 = entityPlayer2.e(this.a.x, this.a.y, this.a.z);
        if (e < e2) {
            return -1;
        }
        if (e > e2) {
            return 1;
        }
        return 0;
    }
}
