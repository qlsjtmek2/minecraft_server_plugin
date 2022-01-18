// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Random;

public class WorldGenNetherPiece4 extends WorldGenNetherPiece
{
    public WorldGenNetherPiece4(final int n, final Random random, final StructureBoundingBox e, final int f) {
        super(n);
        this.f = f;
        this.e = e;
    }
    
    public void a(final StructurePiece structurePiece, final List list, final Random random) {
        this.a((WorldGenNetherPiece15)structurePiece, list, random, 1, 0, true);
    }
    
    public static WorldGenNetherPiece4 a(final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        final StructureBoundingBox a = StructureBoundingBox.a(n, n2, n3, -1, -7, 0, 5, 14, 10, n4);
        if (!WorldGenNetherPiece.a(a) || StructurePiece.a(list, a) != null) {
            return null;
        }
        return new WorldGenNetherPiece4(n5, random, a, n4);
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        final int c = this.c(Block.NETHER_BRICK_STAIRS.id, 2);
        for (int i = 0; i <= 9; ++i) {
            final int max = Math.max(1, 7 - i);
            final int min = Math.min(Math.max(max + 5, 14 - i), 13);
            final int n = i;
            this.a(world, structureBoundingBox, 0, 0, n, 4, max, n, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
            this.a(world, structureBoundingBox, 1, max + 1, n, 3, min - 1, n, 0, 0, false);
            if (i <= 6) {
                this.a(world, Block.NETHER_BRICK_STAIRS.id, c, 1, max + 1, n, structureBoundingBox);
                this.a(world, Block.NETHER_BRICK_STAIRS.id, c, 2, max + 1, n, structureBoundingBox);
                this.a(world, Block.NETHER_BRICK_STAIRS.id, c, 3, max + 1, n, structureBoundingBox);
            }
            this.a(world, structureBoundingBox, 0, min, n, 4, min, n, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
            this.a(world, structureBoundingBox, 0, max + 1, n, 0, min - 1, n, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
            this.a(world, structureBoundingBox, 4, max + 1, n, 4, min - 1, n, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
            if ((i & 0x1) == 0x0) {
                this.a(world, structureBoundingBox, 0, max + 2, n, 0, max + 3, n, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
                this.a(world, structureBoundingBox, 4, max + 2, n, 4, max + 3, n, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
            }
            for (int j = 0; j <= 4; ++j) {
                this.b(world, Block.NETHER_BRICK.id, 0, j, -1, n, structureBoundingBox);
            }
        }
        return true;
    }
}
