// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class WorldGenStronghold2Start extends StructureStart
{
    public WorldGenStronghold2Start(final World world, final Random random, final int n, final int n2) {
        WorldGenStrongholdPieces.a();
        final WorldGenStrongholdStart worldGenStrongholdStart = new WorldGenStrongholdStart(0, random, (n << 4) + 2, (n2 << 4) + 2);
        this.a.add(worldGenStrongholdStart);
        worldGenStrongholdStart.a(worldGenStrongholdStart, this.a, random);
        final ArrayList c = worldGenStrongholdStart.c;
        while (!c.isEmpty()) {
            c.remove(random.nextInt(c.size())).a(worldGenStrongholdStart, this.a, random);
        }
        this.c();
        this.a(world, random, 10);
    }
}
