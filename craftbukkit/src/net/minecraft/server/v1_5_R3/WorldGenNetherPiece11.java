// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Random;

public class WorldGenNetherPiece11 extends WorldGenNetherPiece
{
    public WorldGenNetherPiece11(final int n, final Random random, final StructureBoundingBox e, final int f) {
        super(n);
        this.f = f;
        this.e = e;
    }
    
    public void a(final StructurePiece structurePiece, final List list, final Random random) {
        this.a((WorldGenNetherPiece15)structurePiece, list, random, 5, 3, true);
        this.a((WorldGenNetherPiece15)structurePiece, list, random, 5, 11, true);
    }
    
    public static WorldGenNetherPiece11 a(final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        final StructureBoundingBox a = StructureBoundingBox.a(n, n2, n3, -5, -3, 0, 13, 14, 13, n4);
        if (!WorldGenNetherPiece.a(a) || StructurePiece.a(list, a) != null) {
            return null;
        }
        return new WorldGenNetherPiece11(n5, random, a, n4);
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        this.a(world, structureBoundingBox, 0, 3, 0, 12, 4, 12, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 0, 5, 0, 12, 13, 12, 0, 0, false);
        this.a(world, structureBoundingBox, 0, 5, 0, 1, 12, 12, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 11, 5, 0, 12, 12, 12, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 2, 5, 11, 4, 12, 12, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 8, 5, 11, 10, 12, 12, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 5, 9, 11, 7, 12, 12, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 2, 5, 0, 4, 12, 1, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 8, 5, 0, 10, 12, 1, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 5, 9, 0, 7, 12, 1, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 2, 11, 2, 10, 12, 10, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        for (int i = 1; i <= 11; i += 2) {
            this.a(world, structureBoundingBox, i, 10, 0, i, 11, 0, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
            this.a(world, structureBoundingBox, i, 10, 12, i, 11, 12, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
            this.a(world, structureBoundingBox, 0, 10, i, 0, 11, i, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
            this.a(world, structureBoundingBox, 12, 10, i, 12, 11, i, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
            this.a(world, Block.NETHER_BRICK.id, 0, i, 13, 0, structureBoundingBox);
            this.a(world, Block.NETHER_BRICK.id, 0, i, 13, 12, structureBoundingBox);
            this.a(world, Block.NETHER_BRICK.id, 0, 0, 13, i, structureBoundingBox);
            this.a(world, Block.NETHER_BRICK.id, 0, 12, 13, i, structureBoundingBox);
            this.a(world, Block.NETHER_FENCE.id, 0, i + 1, 13, 0, structureBoundingBox);
            this.a(world, Block.NETHER_FENCE.id, 0, i + 1, 13, 12, structureBoundingBox);
            this.a(world, Block.NETHER_FENCE.id, 0, 0, 13, i + 1, structureBoundingBox);
            this.a(world, Block.NETHER_FENCE.id, 0, 12, 13, i + 1, structureBoundingBox);
        }
        this.a(world, Block.NETHER_FENCE.id, 0, 0, 13, 0, structureBoundingBox);
        this.a(world, Block.NETHER_FENCE.id, 0, 0, 13, 12, structureBoundingBox);
        this.a(world, Block.NETHER_FENCE.id, 0, 0, 13, 0, structureBoundingBox);
        this.a(world, Block.NETHER_FENCE.id, 0, 12, 13, 0, structureBoundingBox);
        for (int j = 3; j <= 9; j += 2) {
            this.a(world, structureBoundingBox, 1, 7, j, 1, 8, j, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
            this.a(world, structureBoundingBox, 11, 7, j, 11, 8, j, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        }
        final int c = this.c(Block.NETHER_BRICK_STAIRS.id, 3);
        for (int k = 0; k <= 6; ++k) {
            final int n = k + 4;
            for (int l = 5; l <= 7; ++l) {
                this.a(world, Block.NETHER_BRICK_STAIRS.id, c, l, 5 + k, n, structureBoundingBox);
            }
            if (n >= 5 && n <= 8) {
                this.a(world, structureBoundingBox, 5, 5, n, 7, k + 4, n, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
            }
            else if (n >= 9 && n <= 10) {
                this.a(world, structureBoundingBox, 5, 8, n, 7, k + 4, n, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
            }
            if (k >= 1) {
                this.a(world, structureBoundingBox, 5, 6 + k, n, 7, 9 + k, n, 0, 0, false);
            }
        }
        for (int n2 = 5; n2 <= 7; ++n2) {
            this.a(world, Block.NETHER_BRICK_STAIRS.id, c, n2, 12, 11, structureBoundingBox);
        }
        this.a(world, structureBoundingBox, 5, 6, 7, 5, 7, 7, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        this.a(world, structureBoundingBox, 7, 6, 7, 7, 7, 7, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        this.a(world, structureBoundingBox, 5, 13, 12, 7, 13, 12, 0, 0, false);
        this.a(world, structureBoundingBox, 2, 5, 2, 3, 5, 3, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 2, 5, 9, 3, 5, 10, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 2, 5, 4, 2, 5, 8, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 9, 5, 2, 10, 5, 3, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 9, 5, 9, 10, 5, 10, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 10, 5, 4, 10, 5, 8, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        final int c2 = this.c(Block.NETHER_BRICK_STAIRS.id, 0);
        final int c3 = this.c(Block.NETHER_BRICK_STAIRS.id, 1);
        this.a(world, Block.NETHER_BRICK_STAIRS.id, c3, 4, 5, 2, structureBoundingBox);
        this.a(world, Block.NETHER_BRICK_STAIRS.id, c3, 4, 5, 3, structureBoundingBox);
        this.a(world, Block.NETHER_BRICK_STAIRS.id, c3, 4, 5, 9, structureBoundingBox);
        this.a(world, Block.NETHER_BRICK_STAIRS.id, c3, 4, 5, 10, structureBoundingBox);
        this.a(world, Block.NETHER_BRICK_STAIRS.id, c2, 8, 5, 2, structureBoundingBox);
        this.a(world, Block.NETHER_BRICK_STAIRS.id, c2, 8, 5, 3, structureBoundingBox);
        this.a(world, Block.NETHER_BRICK_STAIRS.id, c2, 8, 5, 9, structureBoundingBox);
        this.a(world, Block.NETHER_BRICK_STAIRS.id, c2, 8, 5, 10, structureBoundingBox);
        this.a(world, structureBoundingBox, 3, 4, 4, 4, 4, 8, Block.SOUL_SAND.id, Block.SOUL_SAND.id, false);
        this.a(world, structureBoundingBox, 8, 4, 4, 9, 4, 8, Block.SOUL_SAND.id, Block.SOUL_SAND.id, false);
        this.a(world, structureBoundingBox, 3, 5, 4, 4, 5, 8, Block.NETHER_WART.id, Block.NETHER_WART.id, false);
        this.a(world, structureBoundingBox, 8, 5, 4, 9, 5, 8, Block.NETHER_WART.id, Block.NETHER_WART.id, false);
        this.a(world, structureBoundingBox, 4, 2, 0, 8, 2, 12, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 0, 2, 4, 12, 2, 8, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 4, 0, 0, 8, 1, 3, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 4, 0, 9, 8, 1, 12, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 0, 0, 4, 3, 1, 8, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 9, 0, 4, 12, 1, 8, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        for (int n3 = 4; n3 <= 8; ++n3) {
            for (int n4 = 0; n4 <= 2; ++n4) {
                this.b(world, Block.NETHER_BRICK.id, 0, n3, -1, n4, structureBoundingBox);
                this.b(world, Block.NETHER_BRICK.id, 0, n3, -1, 12 - n4, structureBoundingBox);
            }
        }
        for (int n5 = 0; n5 <= 2; ++n5) {
            for (int n6 = 4; n6 <= 8; ++n6) {
                this.b(world, Block.NETHER_BRICK.id, 0, n5, -1, n6, structureBoundingBox);
                this.b(world, Block.NETHER_BRICK.id, 0, 12 - n5, -1, n6, structureBoundingBox);
            }
        }
        return true;
    }
}
