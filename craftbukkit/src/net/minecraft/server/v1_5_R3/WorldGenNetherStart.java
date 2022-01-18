// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class WorldGenNetherStart extends StructureStart
{
    public WorldGenNetherStart(final World world, final Random random, final int n, final int n2) {
        final WorldGenNetherPiece15 worldGenNetherPiece15 = new WorldGenNetherPiece15(random, (n << 4) + 2, (n2 << 4) + 2);
        this.a.add(worldGenNetherPiece15);
        worldGenNetherPiece15.a(worldGenNetherPiece15, this.a, random);
        final ArrayList d = worldGenNetherPiece15.d;
        while (!d.isEmpty()) {
            d.remove(random.nextInt(d.size())).a(worldGenNetherPiece15, this.a, random);
        }
        this.c();
        this.a(world, random, 48, 70);
    }
}
