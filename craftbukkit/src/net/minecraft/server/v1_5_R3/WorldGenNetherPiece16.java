// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Random;

public class WorldGenNetherPiece16 extends WorldGenNetherPiece
{
    public WorldGenNetherPiece16(final int n, final Random random, final StructureBoundingBox e, final int f) {
        super(n);
        this.f = f;
        this.e = e;
    }
    
    public void a(final StructurePiece structurePiece, final List list, final Random random) {
        this.c((WorldGenNetherPiece15)structurePiece, list, random, 6, 2, false);
    }
    
    public static WorldGenNetherPiece16 a(final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        final StructureBoundingBox a = StructureBoundingBox.a(n, n2, n3, -2, 0, 0, 7, 11, 7, n4);
        if (!WorldGenNetherPiece.a(a) || StructurePiece.a(list, a) != null) {
            return null;
        }
        return new WorldGenNetherPiece16(n5, random, a, n4);
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        this.a(world, structureBoundingBox, 0, 0, 0, 6, 1, 6, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 0, 2, 0, 6, 10, 6, 0, 0, false);
        this.a(world, structureBoundingBox, 0, 2, 0, 1, 8, 0, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 5, 2, 0, 6, 8, 0, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 0, 2, 1, 0, 8, 6, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 6, 2, 1, 6, 8, 6, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 1, 2, 6, 5, 8, 6, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 0, 3, 2, 0, 5, 4, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        this.a(world, structureBoundingBox, 6, 3, 2, 6, 5, 2, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        this.a(world, structureBoundingBox, 6, 3, 4, 6, 5, 4, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        this.a(world, Block.NETHER_BRICK.id, 0, 5, 2, 5, structureBoundingBox);
        this.a(world, structureBoundingBox, 4, 2, 5, 4, 3, 5, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 3, 2, 5, 3, 4, 5, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 2, 2, 5, 2, 5, 5, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 1, 2, 5, 1, 6, 5, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 1, 7, 1, 5, 7, 4, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 6, 8, 2, 6, 8, 4, 0, 0, false);
        this.a(world, structureBoundingBox, 2, 6, 0, 4, 8, 0, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 2, 5, 0, 4, 5, 0, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        for (int i = 0; i <= 6; ++i) {
            for (int j = 0; j <= 6; ++j) {
                this.b(world, Block.NETHER_BRICK.id, 0, i, -1, j, structureBoundingBox);
            }
        }
        return true;
    }
}
