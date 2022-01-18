// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Random;

public class WorldGenVillageHouse extends WorldGenVillagePiece
{
    private int a;
    private final boolean b;
    
    public WorldGenVillageHouse(final WorldGenVillageStartPiece worldGenVillageStartPiece, final int n, final Random random, final StructureBoundingBox e, final int f) {
        super(worldGenVillageStartPiece, n);
        this.a = -1;
        this.f = f;
        this.e = e;
        this.b = random.nextBoolean();
    }
    
    public static WorldGenVillageHouse a(final WorldGenVillageStartPiece worldGenVillageStartPiece, final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        final StructureBoundingBox a = StructureBoundingBox.a(n, n2, n3, 0, 0, 0, 5, 6, 5, n4);
        if (StructurePiece.a(list, a) != null) {
            return null;
        }
        return new WorldGenVillageHouse(worldGenVillageStartPiece, n5, random, a, n4);
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        if (this.a < 0) {
            this.a = this.b(world, structureBoundingBox);
            if (this.a < 0) {
                return true;
            }
            this.e.a(0, this.a - this.e.e + 6 - 1, 0);
        }
        this.a(world, structureBoundingBox, 0, 0, 0, 4, 0, 4, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 0, 4, 0, 4, 4, 4, Block.LOG.id, Block.LOG.id, false);
        this.a(world, structureBoundingBox, 1, 4, 1, 3, 4, 3, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, Block.COBBLESTONE.id, 0, 0, 1, 0, structureBoundingBox);
        this.a(world, Block.COBBLESTONE.id, 0, 0, 2, 0, structureBoundingBox);
        this.a(world, Block.COBBLESTONE.id, 0, 0, 3, 0, structureBoundingBox);
        this.a(world, Block.COBBLESTONE.id, 0, 4, 1, 0, structureBoundingBox);
        this.a(world, Block.COBBLESTONE.id, 0, 4, 2, 0, structureBoundingBox);
        this.a(world, Block.COBBLESTONE.id, 0, 4, 3, 0, structureBoundingBox);
        this.a(world, Block.COBBLESTONE.id, 0, 0, 1, 4, structureBoundingBox);
        this.a(world, Block.COBBLESTONE.id, 0, 0, 2, 4, structureBoundingBox);
        this.a(world, Block.COBBLESTONE.id, 0, 0, 3, 4, structureBoundingBox);
        this.a(world, Block.COBBLESTONE.id, 0, 4, 1, 4, structureBoundingBox);
        this.a(world, Block.COBBLESTONE.id, 0, 4, 2, 4, structureBoundingBox);
        this.a(world, Block.COBBLESTONE.id, 0, 4, 3, 4, structureBoundingBox);
        this.a(world, structureBoundingBox, 0, 1, 1, 0, 3, 3, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 4, 1, 1, 4, 3, 3, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 1, 1, 4, 3, 3, 4, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, Block.THIN_GLASS.id, 0, 0, 2, 2, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 2, 2, 4, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 4, 2, 2, structureBoundingBox);
        this.a(world, Block.WOOD.id, 0, 1, 1, 0, structureBoundingBox);
        this.a(world, Block.WOOD.id, 0, 1, 2, 0, structureBoundingBox);
        this.a(world, Block.WOOD.id, 0, 1, 3, 0, structureBoundingBox);
        this.a(world, Block.WOOD.id, 0, 2, 3, 0, structureBoundingBox);
        this.a(world, Block.WOOD.id, 0, 3, 3, 0, structureBoundingBox);
        this.a(world, Block.WOOD.id, 0, 3, 2, 0, structureBoundingBox);
        this.a(world, Block.WOOD.id, 0, 3, 1, 0, structureBoundingBox);
        if (this.a(world, 2, 0, -1, structureBoundingBox) == 0 && this.a(world, 2, -1, -1, structureBoundingBox) != 0) {
            this.a(world, Block.COBBLESTONE_STAIRS.id, this.c(Block.COBBLESTONE_STAIRS.id, 3), 2, 0, -1, structureBoundingBox);
        }
        this.a(world, structureBoundingBox, 1, 1, 1, 3, 3, 3, 0, 0, false);
        if (this.b) {
            this.a(world, Block.FENCE.id, 0, 0, 5, 0, structureBoundingBox);
            this.a(world, Block.FENCE.id, 0, 1, 5, 0, structureBoundingBox);
            this.a(world, Block.FENCE.id, 0, 2, 5, 0, structureBoundingBox);
            this.a(world, Block.FENCE.id, 0, 3, 5, 0, structureBoundingBox);
            this.a(world, Block.FENCE.id, 0, 4, 5, 0, structureBoundingBox);
            this.a(world, Block.FENCE.id, 0, 0, 5, 4, structureBoundingBox);
            this.a(world, Block.FENCE.id, 0, 1, 5, 4, structureBoundingBox);
            this.a(world, Block.FENCE.id, 0, 2, 5, 4, structureBoundingBox);
            this.a(world, Block.FENCE.id, 0, 3, 5, 4, structureBoundingBox);
            this.a(world, Block.FENCE.id, 0, 4, 5, 4, structureBoundingBox);
            this.a(world, Block.FENCE.id, 0, 4, 5, 1, structureBoundingBox);
            this.a(world, Block.FENCE.id, 0, 4, 5, 2, structureBoundingBox);
            this.a(world, Block.FENCE.id, 0, 4, 5, 3, structureBoundingBox);
            this.a(world, Block.FENCE.id, 0, 0, 5, 1, structureBoundingBox);
            this.a(world, Block.FENCE.id, 0, 0, 5, 2, structureBoundingBox);
            this.a(world, Block.FENCE.id, 0, 0, 5, 3, structureBoundingBox);
        }
        if (this.b) {
            final int c = this.c(Block.LADDER.id, 3);
            this.a(world, Block.LADDER.id, c, 3, 1, 3, structureBoundingBox);
            this.a(world, Block.LADDER.id, c, 3, 2, 3, structureBoundingBox);
            this.a(world, Block.LADDER.id, c, 3, 3, 3, structureBoundingBox);
            this.a(world, Block.LADDER.id, c, 3, 4, 3, structureBoundingBox);
        }
        this.a(world, Block.TORCH.id, 0, 2, 3, 1, structureBoundingBox);
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                this.b(world, j, 6, i, structureBoundingBox);
                this.b(world, Block.COBBLESTONE.id, 0, j, -1, i, structureBoundingBox);
            }
        }
        this.a(world, structureBoundingBox, 1, 1, 2, 1);
        return true;
    }
}
