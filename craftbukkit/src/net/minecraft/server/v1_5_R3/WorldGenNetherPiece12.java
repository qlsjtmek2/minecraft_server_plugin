// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Random;

public class WorldGenNetherPiece12 extends WorldGenNetherPiece
{
    private boolean a;
    
    public WorldGenNetherPiece12(final int n, final Random random, final StructureBoundingBox e, final int f) {
        super(n);
        this.f = f;
        this.e = e;
    }
    
    public static WorldGenNetherPiece12 a(final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        final StructureBoundingBox a = StructureBoundingBox.a(n, n2, n3, -2, 0, 0, 7, 8, 9, n4);
        if (!WorldGenNetherPiece.a(a) || StructurePiece.a(list, a) != null) {
            return null;
        }
        return new WorldGenNetherPiece12(n5, random, a, n4);
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        this.a(world, structureBoundingBox, 0, 2, 0, 6, 7, 7, 0, 0, false);
        this.a(world, structureBoundingBox, 1, 0, 0, 5, 1, 7, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 1, 2, 1, 5, 2, 7, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 1, 3, 2, 5, 3, 7, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 1, 4, 3, 5, 4, 7, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 1, 2, 0, 1, 4, 2, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 5, 2, 0, 5, 4, 2, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 1, 5, 2, 1, 5, 3, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 5, 5, 2, 5, 5, 3, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 0, 5, 3, 0, 5, 8, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 6, 5, 3, 6, 5, 8, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, structureBoundingBox, 1, 5, 8, 5, 5, 8, Block.NETHER_BRICK.id, Block.NETHER_BRICK.id, false);
        this.a(world, Block.NETHER_FENCE.id, 0, 1, 6, 3, structureBoundingBox);
        this.a(world, Block.NETHER_FENCE.id, 0, 5, 6, 3, structureBoundingBox);
        this.a(world, structureBoundingBox, 0, 6, 3, 0, 6, 8, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        this.a(world, structureBoundingBox, 6, 6, 3, 6, 6, 8, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        this.a(world, structureBoundingBox, 1, 6, 8, 5, 7, 8, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        this.a(world, structureBoundingBox, 2, 8, 8, 4, 8, 8, Block.NETHER_FENCE.id, Block.NETHER_FENCE.id, false);
        if (!this.a) {
            final int a = this.a(5);
            final int a2 = this.a(3, 5);
            final int b = this.b(3, 5);
            if (structureBoundingBox.b(a2, a, b)) {
                this.a = true;
                world.setTypeIdAndData(a2, a, b, Block.MOB_SPAWNER.id, 0, 2);
                final TileEntityMobSpawner tileEntityMobSpawner = (TileEntityMobSpawner)world.getTileEntity(a2, a, b);
                if (tileEntityMobSpawner != null) {
                    tileEntityMobSpawner.a().a("Blaze");
                }
            }
        }
        for (int i = 0; i <= 6; ++i) {
            for (int j = 0; j <= 6; ++j) {
                this.b(world, Block.NETHER_BRICK.id, 0, i, -1, j, structureBoundingBox);
            }
        }
        return true;
    }
}
