// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Random;

public class WorldGenVillageLight extends WorldGenVillagePiece
{
    private int a;
    
    public WorldGenVillageLight(final WorldGenVillageStartPiece worldGenVillageStartPiece, final int n, final Random random, final StructureBoundingBox e, final int f) {
        super(worldGenVillageStartPiece, n);
        this.a = -1;
        this.f = f;
        this.e = e;
    }
    
    public static StructureBoundingBox a(final WorldGenVillageStartPiece worldGenVillageStartPiece, final List list, final Random random, final int n, final int n2, final int n3, final int n4) {
        final StructureBoundingBox a = StructureBoundingBox.a(n, n2, n3, 0, 0, 0, 3, 4, 2, n4);
        if (StructurePiece.a(list, a) != null) {
            return null;
        }
        return a;
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        if (this.a < 0) {
            this.a = this.b(world, structureBoundingBox);
            if (this.a < 0) {
                return true;
            }
            this.e.a(0, this.a - this.e.e + 4 - 1, 0);
        }
        this.a(world, structureBoundingBox, 0, 0, 0, 2, 3, 1, 0, 0, false);
        this.a(world, Block.FENCE.id, 0, 1, 0, 0, structureBoundingBox);
        this.a(world, Block.FENCE.id, 0, 1, 1, 0, structureBoundingBox);
        this.a(world, Block.FENCE.id, 0, 1, 2, 0, structureBoundingBox);
        this.a(world, Block.WOOL.id, 15, 1, 3, 0, structureBoundingBox);
        this.a(world, Block.TORCH.id, 0, 0, 3, 0, structureBoundingBox);
        this.a(world, Block.TORCH.id, 0, 1, 3, 1, structureBoundingBox);
        this.a(world, Block.TORCH.id, 0, 2, 3, 0, structureBoundingBox);
        this.a(world, Block.TORCH.id, 0, 1, 3, -1, structureBoundingBox);
        return true;
    }
}
