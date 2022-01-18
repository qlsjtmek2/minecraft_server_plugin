// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Random;

public class WorldGenVillageButcher extends WorldGenVillagePiece
{
    private int a;
    
    public WorldGenVillageButcher(final WorldGenVillageStartPiece worldGenVillageStartPiece, final int n, final Random random, final StructureBoundingBox e, final int f) {
        super(worldGenVillageStartPiece, n);
        this.a = -1;
        this.f = f;
        this.e = e;
    }
    
    public static WorldGenVillageButcher a(final WorldGenVillageStartPiece worldGenVillageStartPiece, final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        final StructureBoundingBox a = StructureBoundingBox.a(n, n2, n3, 0, 0, 0, 9, 7, 11, n4);
        if (!WorldGenVillagePiece.a(a) || StructurePiece.a(list, a) != null) {
            return null;
        }
        return new WorldGenVillageButcher(worldGenVillageStartPiece, n5, random, a, n4);
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        if (this.a < 0) {
            this.a = this.b(world, structureBoundingBox);
            if (this.a < 0) {
                return true;
            }
            this.e.a(0, this.a - this.e.e + 7 - 1, 0);
        }
        this.a(world, structureBoundingBox, 1, 1, 1, 7, 4, 4, 0, 0, false);
        this.a(world, structureBoundingBox, 2, 1, 6, 8, 4, 10, 0, 0, false);
        this.a(world, structureBoundingBox, 2, 0, 6, 8, 0, 10, Block.DIRT.id, Block.DIRT.id, false);
        this.a(world, Block.COBBLESTONE.id, 0, 6, 0, 6, structureBoundingBox);
        this.a(world, structureBoundingBox, 2, 1, 6, 2, 1, 10, Block.FENCE.id, Block.FENCE.id, false);
        this.a(world, structureBoundingBox, 8, 1, 6, 8, 1, 10, Block.FENCE.id, Block.FENCE.id, false);
        this.a(world, structureBoundingBox, 3, 1, 10, 7, 1, 10, Block.FENCE.id, Block.FENCE.id, false);
        this.a(world, structureBoundingBox, 1, 0, 1, 7, 0, 4, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 0, 0, 0, 0, 3, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 8, 0, 0, 8, 3, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 1, 0, 0, 7, 1, 0, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 1, 0, 5, 7, 1, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 1, 2, 0, 7, 3, 0, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 1, 2, 5, 7, 3, 5, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 0, 4, 1, 8, 4, 1, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 0, 4, 4, 8, 4, 4, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 0, 5, 2, 8, 5, 3, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, Block.WOOD.id, 0, 0, 4, 2, structureBoundingBox);
        this.a(world, Block.WOOD.id, 0, 0, 4, 3, structureBoundingBox);
        this.a(world, Block.WOOD.id, 0, 8, 4, 2, structureBoundingBox);
        this.a(world, Block.WOOD.id, 0, 8, 4, 3, structureBoundingBox);
        final int c = this.c(Block.WOOD_STAIRS.id, 3);
        final int c2 = this.c(Block.WOOD_STAIRS.id, 2);
        for (int i = -1; i <= 2; ++i) {
            for (int j = 0; j <= 8; ++j) {
                this.a(world, Block.WOOD_STAIRS.id, c, j, 4 + i, i, structureBoundingBox);
                this.a(world, Block.WOOD_STAIRS.id, c2, j, 4 + i, 5 - i, structureBoundingBox);
            }
        }
        this.a(world, Block.LOG.id, 0, 0, 2, 1, structureBoundingBox);
        this.a(world, Block.LOG.id, 0, 0, 2, 4, structureBoundingBox);
        this.a(world, Block.LOG.id, 0, 8, 2, 1, structureBoundingBox);
        this.a(world, Block.LOG.id, 0, 8, 2, 4, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 0, 2, 2, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 0, 2, 3, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 8, 2, 2, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 8, 2, 3, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 2, 2, 5, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 3, 2, 5, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 5, 2, 0, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 6, 2, 5, structureBoundingBox);
        this.a(world, Block.FENCE.id, 0, 2, 1, 3, structureBoundingBox);
        this.a(world, Block.WOOD_PLATE.id, 0, 2, 2, 3, structureBoundingBox);
        this.a(world, Block.WOOD.id, 0, 1, 1, 4, structureBoundingBox);
        this.a(world, Block.WOOD_STAIRS.id, this.c(Block.WOOD_STAIRS.id, 3), 2, 1, 4, structureBoundingBox);
        this.a(world, Block.WOOD_STAIRS.id, this.c(Block.WOOD_STAIRS.id, 1), 1, 1, 3, structureBoundingBox);
        this.a(world, structureBoundingBox, 5, 0, 1, 7, 0, 3, Block.DOUBLE_STEP.id, Block.DOUBLE_STEP.id, false);
        this.a(world, Block.DOUBLE_STEP.id, 0, 6, 1, 1, structureBoundingBox);
        this.a(world, Block.DOUBLE_STEP.id, 0, 6, 1, 2, structureBoundingBox);
        this.a(world, 0, 0, 2, 1, 0, structureBoundingBox);
        this.a(world, 0, 0, 2, 2, 0, structureBoundingBox);
        this.a(world, Block.TORCH.id, 0, 2, 3, 1, structureBoundingBox);
        this.a(world, structureBoundingBox, random, 2, 1, 0, this.c(Block.WOODEN_DOOR.id, 1));
        if (this.a(world, 2, 0, -1, structureBoundingBox) == 0 && this.a(world, 2, -1, -1, structureBoundingBox) != 0) {
            this.a(world, Block.COBBLESTONE_STAIRS.id, this.c(Block.COBBLESTONE_STAIRS.id, 3), 2, 0, -1, structureBoundingBox);
        }
        this.a(world, 0, 0, 6, 1, 5, structureBoundingBox);
        this.a(world, 0, 0, 6, 2, 5, structureBoundingBox);
        this.a(world, Block.TORCH.id, 0, 6, 3, 4, structureBoundingBox);
        this.a(world, structureBoundingBox, random, 6, 1, 5, this.c(Block.WOODEN_DOOR.id, 1));
        for (int k = 0; k < 5; ++k) {
            for (int l = 0; l < 9; ++l) {
                this.b(world, l, 7, k, structureBoundingBox);
                this.b(world, Block.COBBLESTONE.id, 0, l, -1, k, structureBoundingBox);
            }
        }
        this.a(world, structureBoundingBox, 4, 1, 2, 2);
        return true;
    }
    
    protected int b(final int n) {
        if (n == 0) {
            return 4;
        }
        return 0;
    }
}
