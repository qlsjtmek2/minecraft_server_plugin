// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Random;

public class WorldGenVillageHouse2 extends WorldGenVillagePiece
{
    private int a;
    
    public WorldGenVillageHouse2(final WorldGenVillageStartPiece worldGenVillageStartPiece, final int n, final Random random, final StructureBoundingBox e, final int f) {
        super(worldGenVillageStartPiece, n);
        this.a = -1;
        this.f = f;
        this.e = e;
    }
    
    public static WorldGenVillageHouse2 a(final WorldGenVillageStartPiece worldGenVillageStartPiece, final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        final StructureBoundingBox a = StructureBoundingBox.a(n, n2, n3, 0, 0, 0, 9, 7, 12, n4);
        if (!WorldGenVillagePiece.a(a) || StructurePiece.a(list, a) != null) {
            return null;
        }
        return new WorldGenVillageHouse2(worldGenVillageStartPiece, n5, random, a, n4);
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
        this.a(world, structureBoundingBox, 2, 0, 5, 8, 0, 10, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 1, 0, 1, 7, 0, 4, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 0, 0, 0, 0, 3, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 8, 0, 0, 8, 3, 10, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 1, 0, 0, 7, 2, 0, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 1, 0, 5, 2, 1, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 2, 0, 6, 2, 3, 10, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 3, 0, 10, 7, 3, 10, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 1, 2, 0, 7, 3, 0, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 1, 2, 5, 2, 3, 5, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 0, 4, 1, 8, 4, 1, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 0, 4, 4, 3, 4, 4, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 0, 5, 2, 8, 5, 3, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, Block.WOOD.id, 0, 0, 4, 2, structureBoundingBox);
        this.a(world, Block.WOOD.id, 0, 0, 4, 3, structureBoundingBox);
        this.a(world, Block.WOOD.id, 0, 8, 4, 2, structureBoundingBox);
        this.a(world, Block.WOOD.id, 0, 8, 4, 3, structureBoundingBox);
        this.a(world, Block.WOOD.id, 0, 8, 4, 4, structureBoundingBox);
        final int c = this.c(Block.WOOD_STAIRS.id, 3);
        final int c2 = this.c(Block.WOOD_STAIRS.id, 2);
        for (int i = -1; i <= 2; ++i) {
            for (int j = 0; j <= 8; ++j) {
                this.a(world, Block.WOOD_STAIRS.id, c, j, 4 + i, i, structureBoundingBox);
                if ((i > -1 || j <= 1) && (i > 0 || j <= 3) && (i > 1 || j <= 4 || j >= 6)) {
                    this.a(world, Block.WOOD_STAIRS.id, c2, j, 4 + i, 5 - i, structureBoundingBox);
                }
            }
        }
        this.a(world, structureBoundingBox, 3, 4, 5, 3, 4, 10, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 7, 4, 2, 7, 4, 10, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 4, 5, 4, 4, 5, 10, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 6, 5, 4, 6, 5, 10, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 5, 6, 3, 5, 6, 10, Block.WOOD.id, Block.WOOD.id, false);
        final int c3 = this.c(Block.WOOD_STAIRS.id, 0);
        for (int k = 4; k >= 1; --k) {
            this.a(world, Block.WOOD.id, 0, k, 2 + k, 7 - k, structureBoundingBox);
            for (int l = 8 - k; l <= 10; ++l) {
                this.a(world, Block.WOOD_STAIRS.id, c3, k, 2 + k, l, structureBoundingBox);
            }
        }
        final int c4 = this.c(Block.WOOD_STAIRS.id, 1);
        this.a(world, Block.WOOD.id, 0, 6, 6, 3, structureBoundingBox);
        this.a(world, Block.WOOD.id, 0, 7, 5, 4, structureBoundingBox);
        this.a(world, Block.WOOD_STAIRS.id, c4, 6, 6, 4, structureBoundingBox);
        for (int n = 6; n <= 8; ++n) {
            for (int n2 = 5; n2 <= 10; ++n2) {
                this.a(world, Block.WOOD_STAIRS.id, c4, n, 12 - n, n2, structureBoundingBox);
            }
        }
        this.a(world, Block.LOG.id, 0, 0, 2, 1, structureBoundingBox);
        this.a(world, Block.LOG.id, 0, 0, 2, 4, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 0, 2, 2, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 0, 2, 3, structureBoundingBox);
        this.a(world, Block.LOG.id, 0, 4, 2, 0, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 5, 2, 0, structureBoundingBox);
        this.a(world, Block.LOG.id, 0, 6, 2, 0, structureBoundingBox);
        this.a(world, Block.LOG.id, 0, 8, 2, 1, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 8, 2, 2, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 8, 2, 3, structureBoundingBox);
        this.a(world, Block.LOG.id, 0, 8, 2, 4, structureBoundingBox);
        this.a(world, Block.WOOD.id, 0, 8, 2, 5, structureBoundingBox);
        this.a(world, Block.LOG.id, 0, 8, 2, 6, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 8, 2, 7, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 8, 2, 8, structureBoundingBox);
        this.a(world, Block.LOG.id, 0, 8, 2, 9, structureBoundingBox);
        this.a(world, Block.LOG.id, 0, 2, 2, 6, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 2, 2, 7, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 2, 2, 8, structureBoundingBox);
        this.a(world, Block.LOG.id, 0, 2, 2, 9, structureBoundingBox);
        this.a(world, Block.LOG.id, 0, 4, 4, 10, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 5, 4, 10, structureBoundingBox);
        this.a(world, Block.LOG.id, 0, 6, 4, 10, structureBoundingBox);
        this.a(world, Block.WOOD.id, 0, 5, 5, 10, structureBoundingBox);
        this.a(world, 0, 0, 2, 1, 0, structureBoundingBox);
        this.a(world, 0, 0, 2, 2, 0, structureBoundingBox);
        this.a(world, Block.TORCH.id, 0, 2, 3, 1, structureBoundingBox);
        this.a(world, structureBoundingBox, random, 2, 1, 0, this.c(Block.WOODEN_DOOR.id, 1));
        this.a(world, structureBoundingBox, 1, 0, -1, 3, 2, -1, 0, 0, false);
        if (this.a(world, 2, 0, -1, structureBoundingBox) == 0 && this.a(world, 2, -1, -1, structureBoundingBox) != 0) {
            this.a(world, Block.COBBLESTONE_STAIRS.id, this.c(Block.COBBLESTONE_STAIRS.id, 3), 2, 0, -1, structureBoundingBox);
        }
        for (int n3 = 0; n3 < 5; ++n3) {
            for (int n4 = 0; n4 < 9; ++n4) {
                this.b(world, n4, 7, n3, structureBoundingBox);
                this.b(world, Block.COBBLESTONE.id, 0, n4, -1, n3, structureBoundingBox);
            }
        }
        for (int n5 = 5; n5 < 11; ++n5) {
            for (int n6 = 2; n6 < 9; ++n6) {
                this.b(world, n6, 7, n5, structureBoundingBox);
                this.b(world, Block.COBBLESTONE.id, 0, n6, -1, n5, structureBoundingBox);
            }
        }
        this.a(world, structureBoundingBox, 4, 1, 2, 2);
        return true;
    }
}
