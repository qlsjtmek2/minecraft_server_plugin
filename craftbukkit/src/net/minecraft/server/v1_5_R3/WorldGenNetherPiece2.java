// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Random;

public class WorldGenNetherPiece2 extends WorldGenNetherPiece
{
    private int a;
    
    public WorldGenNetherPiece2(final int n, final Random random, final StructureBoundingBox e, final int f) {
        super(n);
        this.f = f;
        this.e = e;
        this.a = random.nextInt();
    }
    
    public static WorldGenNetherPiece2 a(final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        final StructureBoundingBox a = StructureBoundingBox.a(n, n2, n3, -1, -3, 0, 5, 10, 8, n4);
        if (!WorldGenNetherPiece.a(a) || StructurePiece.a(list, a) != null) {
            return null;
        }
        return new WorldGenNetherPiece2(n5, random, a, n4);
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        final Random random2 = new Random(this.a);
        for (int i = 0; i <= 4; ++i) {
            for (int j = 3; j <= 4; ++j) {
                this.a(world, structureBoundingBox, i, j, 0, i, j, random2.nextInt(8), Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
            }
        }
        this.a(world, structureBoundingBox, 0, 5, 0, 0, 5, random2.nextInt(8), Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 4, 5, 0, 4, 5, random2.nextInt(8), Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        for (int k = 0; k <= 4; ++k) {
            this.a(world, structureBoundingBox, k, 2, 0, k, 2, random2.nextInt(5), Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        }
        for (int l = 0; l <= 4; ++l) {
            for (int n = 0; n <= 1; ++n) {
                this.a(world, structureBoundingBox, l, n, 0, l, n, random2.nextInt(3), Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
            }
        }
        return true;
    }
}
