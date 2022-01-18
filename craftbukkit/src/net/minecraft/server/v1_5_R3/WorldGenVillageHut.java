// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Random;

public class WorldGenVillageHut extends WorldGenVillagePiece
{
    private int a;
    private final boolean b;
    private final int c;
    
    public WorldGenVillageHut(final WorldGenVillageStartPiece worldGenVillageStartPiece, final int n, final Random random, final StructureBoundingBox e, final int f) {
        super(worldGenVillageStartPiece, n);
        this.a = -1;
        this.f = f;
        this.e = e;
        this.b = random.nextBoolean();
        this.c = random.nextInt(3);
    }
    
    public static WorldGenVillageHut a(final WorldGenVillageStartPiece worldGenVillageStartPiece, final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        final StructureBoundingBox a = StructureBoundingBox.a(n, n2, n3, 0, 0, 0, 4, 6, 5, n4);
        if (!WorldGenVillagePiece.a(a) || StructurePiece.a(list, a) != null) {
            return null;
        }
        return new WorldGenVillageHut(worldGenVillageStartPiece, n5, random, a, n4);
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        if (this.a < 0) {
            this.a = this.b(world, structureBoundingBox);
            if (this.a < 0) {
                return true;
            }
            this.e.a(0, this.a - this.e.e + 6 - 1, 0);
        }
        this.a(world, structureBoundingBox, 1, 1, 1, 3, 5, 4, 0, 0, false);
        this.a(world, structureBoundingBox, 0, 0, 0, 3, 0, 4, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 1, 0, 1, 2, 0, 3, Block.DIRT.id, Block.DIRT.id, false);
        if (this.b) {
            this.a(world, structureBoundingBox, 1, 4, 1, 2, 4, 3, Block.LOG.id, Block.LOG.id, false);
        }
        else {
            this.a(world, structureBoundingBox, 1, 5, 1, 2, 5, 3, Block.LOG.id, Block.LOG.id, false);
        }
        this.a(world, Block.LOG.id, 0, 1, 4, 0, structureBoundingBox);
        this.a(world, Block.LOG.id, 0, 2, 4, 0, structureBoundingBox);
        this.a(world, Block.LOG.id, 0, 1, 4, 4, structureBoundingBox);
        this.a(world, Block.LOG.id, 0, 2, 4, 4, structureBoundingBox);
        this.a(world, Block.LOG.id, 0, 0, 4, 1, structureBoundingBox);
        this.a(world, Block.LOG.id, 0, 0, 4, 2, structureBoundingBox);
        this.a(world, Block.LOG.id, 0, 0, 4, 3, structureBoundingBox);
        this.a(world, Block.LOG.id, 0, 3, 4, 1, structureBoundingBox);
        this.a(world, Block.LOG.id, 0, 3, 4, 2, structureBoundingBox);
        this.a(world, Block.LOG.id, 0, 3, 4, 3, structureBoundingBox);
        this.a(world, structureBoundingBox, 0, 1, 0, 0, 3, 0, Block.LOG.id, Block.LOG.id, false);
        this.a(world, structureBoundingBox, 3, 1, 0, 3, 3, 0, Block.LOG.id, Block.LOG.id, false);
        this.a(world, structureBoundingBox, 0, 1, 4, 0, 3, 4, Block.LOG.id, Block.LOG.id, false);
        this.a(world, structureBoundingBox, 3, 1, 4, 3, 3, 4, Block.LOG.id, Block.LOG.id, false);
        this.a(world, structureBoundingBox, 0, 1, 1, 0, 3, 3, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 3, 1, 1, 3, 3, 3, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 1, 1, 0, 2, 3, 0, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 1, 1, 4, 2, 3, 4, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, Block.THIN_GLASS.id, 0, 0, 2, 2, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 3, 2, 2, structureBoundingBox);
        if (this.c > 0) {
            this.a(world, Block.FENCE.id, 0, this.c, 1, 3, structureBoundingBox);
            this.a(world, Block.WOOD_PLATE.id, 0, this.c, 2, 3, structureBoundingBox);
        }
        this.a(world, 0, 0, 1, 1, 0, structureBoundingBox);
        this.a(world, 0, 0, 1, 2, 0, structureBoundingBox);
        this.a(world, structureBoundingBox, random, 1, 1, 0, this.c(Block.WOODEN_DOOR.id, 1));
        if (this.a(world, 1, 0, -1, structureBoundingBox) == 0 && this.a(world, 1, -1, -1, structureBoundingBox) != 0) {
            this.a(world, Block.COBBLESTONE_STAIRS.id, this.c(Block.COBBLESTONE_STAIRS.id, 3), 1, 0, -1, structureBoundingBox);
        }
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 4; ++j) {
                this.b(world, j, 6, i, structureBoundingBox);
                this.b(world, Block.COBBLESTONE.id, 0, j, -1, i, structureBoundingBox);
            }
        }
        this.a(world, structureBoundingBox, 1, 1, 2, 1);
        return true;
    }
}
