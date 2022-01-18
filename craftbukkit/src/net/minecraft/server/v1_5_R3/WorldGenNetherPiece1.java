// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Random;

public class WorldGenNetherPiece1 extends WorldGenNetherPiece
{
    public WorldGenNetherPiece1(final int n, final Random random, final StructureBoundingBox e, final int f) {
        super(n);
        this.f = f;
        this.e = e;
    }
    
    protected WorldGenNetherPiece1(final Random random, final int n, final int n2) {
        super(0);
        switch (this.f = random.nextInt(4)) {
            case 0:
            case 2: {
                this.e = new StructureBoundingBox(n, 64, n2, n + 19 - 1, 73, n2 + 19 - 1);
                break;
            }
            default: {
                this.e = new StructureBoundingBox(n, 64, n2, n + 19 - 1, 73, n2 + 19 - 1);
                break;
            }
        }
    }
    
    public void a(final StructurePiece structurePiece, final List list, final Random random) {
        this.a((WorldGenNetherPiece15)structurePiece, list, random, 8, 3, false);
        this.b((WorldGenNetherPiece15)structurePiece, list, random, 3, 8, false);
        this.c((WorldGenNetherPiece15)structurePiece, list, random, 3, 8, false);
    }
    
    public static WorldGenNetherPiece1 a(final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        final StructureBoundingBox a = StructureBoundingBox.a(n, n2, n3, -8, -3, 0, 19, 10, 19, n4);
        if (!WorldGenNetherPiece.a(a) || StructurePiece.a(list, a) != null) {
            return null;
        }
        return new WorldGenNetherPiece1(n5, random, a, n4);
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        this.a(world, structureBoundingBox, 7, 3, 0, 11, 4, 18, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 0, 3, 7, 18, 4, 11, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 8, 5, 0, 10, 7, 18, 0, 0, false);
        this.a(world, structureBoundingBox, 0, 5, 8, 18, 7, 10, 0, 0, false);
        this.a(world, structureBoundingBox, 7, 5, 0, 7, 5, 7, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 7, 5, 11, 7, 5, 18, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 11, 5, 0, 11, 5, 7, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 11, 5, 11, 11, 5, 18, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 0, 5, 7, 7, 5, 7, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 11, 5, 7, 18, 5, 7, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 0, 5, 11, 7, 5, 11, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 11, 5, 11, 18, 5, 11, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 7, 2, 0, 11, 2, 5, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 7, 2, 13, 11, 2, 18, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 7, 0, 0, 11, 1, 3, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 7, 0, 15, 11, 1, 18, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        for (int i = 7; i <= 11; ++i) {
            for (int j = 0; j <= 2; ++j) {
                this.b(world, Block.NETHER_BRICK.id, 0, i, -1, j, structureBoundingBox);
                this.b(world, Block.NETHER_BRICK.id, 0, i, -1, 18 - j, structureBoundingBox);
            }
        }
        this.a(world, structureBoundingBox, 0, 2, 7, 5, 2, 11, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 13, 2, 7, 18, 2, 11, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 0, 0, 7, 3, 1, 11, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 15, 0, 7, 18, 1, 11, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        for (int k = 0; k <= 2; ++k) {
            for (int l = 7; l <= 11; ++l) {
                this.b(world, Block.NETHER_BRICK.id, 0, k, -1, l, structureBoundingBox);
                this.b(world, Block.NETHER_BRICK.id, 0, 18 - k, -1, l, structureBoundingBox);
            }
        }
        return true;
    }
}
