// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Random;

public class WorldGenStrongholdCorridor extends WorldGenStrongholdPiece
{
    private final int a;
    
    public WorldGenStrongholdCorridor(final int n, final Random random, final StructureBoundingBox e, final int f) {
        super(n);
        this.f = f;
        this.e = e;
        this.a = ((f == 2 || f == 0) ? e.d() : e.b());
    }
    
    public static StructureBoundingBox a(final List list, final Random random, final int n, final int n2, final int n3, final int n4) {
        final StructureBoundingBox a = StructureBoundingBox.a(n, n2, n3, -1, -1, 0, 5, 5, 4, n4);
        final StructurePiece a2 = StructurePiece.a(list, a);
        if (a2 == null) {
            return null;
        }
        if (a2.b().b == a.b) {
            for (int i = 3; i >= 1; --i) {
                if (!a2.b().a(StructureBoundingBox.a(n, n2, n3, -1, -1, 0, 5, 5, i - 1, n4))) {
                    return StructureBoundingBox.a(n, n2, n3, -1, -1, 0, 5, 5, i, n4);
                }
            }
        }
        return null;
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        if (this.a(world, structureBoundingBox)) {
            return false;
        }
        for (int i = 0; i < this.a; ++i) {
            this.a(world, Block.SMOOTH_BRICK.id, 0, 0, 0, i, structureBoundingBox);
            this.a(world, Block.SMOOTH_BRICK.id, 0, 1, 0, i, structureBoundingBox);
            this.a(world, Block.SMOOTH_BRICK.id, 0, 2, 0, i, structureBoundingBox);
            this.a(world, Block.SMOOTH_BRICK.id, 0, 3, 0, i, structureBoundingBox);
            this.a(world, Block.SMOOTH_BRICK.id, 0, 4, 0, i, structureBoundingBox);
            for (int j = 1; j <= 3; ++j) {
                this.a(world, Block.SMOOTH_BRICK.id, 0, 0, j, i, structureBoundingBox);
                this.a(world, 0, 0, 1, j, i, structureBoundingBox);
                this.a(world, 0, 0, 2, j, i, structureBoundingBox);
                this.a(world, 0, 0, 3, j, i, structureBoundingBox);
                this.a(world, Block.SMOOTH_BRICK.id, 0, 4, j, i, structureBoundingBox);
            }
            this.a(world, Block.SMOOTH_BRICK.id, 0, 0, 4, i, structureBoundingBox);
            this.a(world, Block.SMOOTH_BRICK.id, 0, 1, 4, i, structureBoundingBox);
            this.a(world, Block.SMOOTH_BRICK.id, 0, 2, 4, i, structureBoundingBox);
            this.a(world, Block.SMOOTH_BRICK.id, 0, 3, 4, i, structureBoundingBox);
            this.a(world, Block.SMOOTH_BRICK.id, 0, 4, 4, i, structureBoundingBox);
        }
        return true;
    }
}
