// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class WorldGenVillageStart extends StructureStart
{
    private boolean c;
    
    public WorldGenVillageStart(final World world, final Random random, final int n, final int n2, final int n3) {
        this.c = false;
        final WorldGenVillageStartPiece worldGenVillageStartPiece = new WorldGenVillageStartPiece(world.getWorldChunkManager(), 0, random, (n << 4) + 2, (n2 << 4) + 2, WorldGenVillagePieces.a(random, n3), n3);
        this.a.add(worldGenVillageStartPiece);
        worldGenVillageStartPiece.a(worldGenVillageStartPiece, this.a, random);
        final ArrayList j = worldGenVillageStartPiece.j;
        final ArrayList i = worldGenVillageStartPiece.i;
        while (!j.isEmpty() || !i.isEmpty()) {
            if (j.isEmpty()) {
                i.remove(random.nextInt(i.size())).a(worldGenVillageStartPiece, this.a, random);
            }
            else {
                j.remove(random.nextInt(j.size())).a(worldGenVillageStartPiece, this.a, random);
            }
        }
        this.c();
        int n4 = 0;
        final Iterator iterator = this.a.iterator();
        while (iterator.hasNext()) {
            if (!(iterator.next() instanceof WorldGenVillageRoadPiece)) {
                ++n4;
            }
        }
        this.c = (n4 > 2);
    }
    
    public boolean d() {
        return this.c;
    }
}
