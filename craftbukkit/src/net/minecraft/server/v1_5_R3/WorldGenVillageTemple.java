// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Random;

public class WorldGenVillageTemple extends WorldGenVillagePiece
{
    private int a;
    
    public WorldGenVillageTemple(final WorldGenVillageStartPiece worldGenVillageStartPiece, final int n, final Random random, final StructureBoundingBox e, final int f) {
        super(worldGenVillageStartPiece, n);
        this.a = -1;
        this.f = f;
        this.e = e;
    }
    
    public static WorldGenVillageTemple a(final WorldGenVillageStartPiece worldGenVillageStartPiece, final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        final StructureBoundingBox a = StructureBoundingBox.a(n, n2, n3, 0, 0, 0, 5, 12, 9, n4);
        if (!WorldGenVillagePiece.a(a) || StructurePiece.a(list, a) != null) {
            return null;
        }
        return new WorldGenVillageTemple(worldGenVillageStartPiece, n5, random, a, n4);
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        if (this.a < 0) {
            this.a = this.b(world, structureBoundingBox);
            if (this.a < 0) {
                return true;
            }
            this.e.a(0, this.a - this.e.e + 12 - 1, 0);
        }
        this.a(world, structureBoundingBox, 1, 1, 1, 3, 3, 7, 0, 0, false);
        this.a(world, structureBoundingBox, 1, 5, 1, 3, 9, 3, 0, 0, false);
        this.a(world, structureBoundingBox, 1, 0, 0, 3, 0, 8, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 1, 1, 0, 3, 10, 0, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 0, 1, 1, 0, 10, 3, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 4, 1, 1, 4, 10, 3, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 0, 0, 4, 0, 4, 7, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 4, 0, 4, 4, 4, 7, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 1, 1, 8, 3, 4, 8, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 1, 5, 4, 3, 10, 4, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 1, 5, 5, 3, 5, 7, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 0, 9, 0, 4, 9, 4, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 0, 4, 0, 4, 4, 4, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, Block.COBBLESTONE.id, 0, 0, 11, 2, structureBoundingBox);
        this.a(world, Block.COBBLESTONE.id, 0, 4, 11, 2, structureBoundingBox);
        this.a(world, Block.COBBLESTONE.id, 0, 2, 11, 0, structureBoundingBox);
        this.a(world, Block.COBBLESTONE.id, 0, 2, 11, 4, structureBoundingBox);
        this.a(world, Block.COBBLESTONE.id, 0, 1, 1, 6, structureBoundingBox);
        this.a(world, Block.COBBLESTONE.id, 0, 1, 1, 7, structureBoundingBox);
        this.a(world, Block.COBBLESTONE.id, 0, 2, 1, 7, structureBoundingBox);
        this.a(world, Block.COBBLESTONE.id, 0, 3, 1, 6, structureBoundingBox);
        this.a(world, Block.COBBLESTONE.id, 0, 3, 1, 7, structureBoundingBox);
        this.a(world, Block.COBBLESTONE_STAIRS.id, this.c(Block.COBBLESTONE_STAIRS.id, 3), 1, 1, 5, structureBoundingBox);
        this.a(world, Block.COBBLESTONE_STAIRS.id, this.c(Block.COBBLESTONE_STAIRS.id, 3), 2, 1, 6, structureBoundingBox);
        this.a(world, Block.COBBLESTONE_STAIRS.id, this.c(Block.COBBLESTONE_STAIRS.id, 3), 3, 1, 5, structureBoundingBox);
        this.a(world, Block.COBBLESTONE_STAIRS.id, this.c(Block.COBBLESTONE_STAIRS.id, 1), 1, 2, 7, structureBoundingBox);
        this.a(world, Block.COBBLESTONE_STAIRS.id, this.c(Block.COBBLESTONE_STAIRS.id, 0), 3, 2, 7, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 0, 2, 2, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 0, 3, 2, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 4, 2, 2, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 4, 3, 2, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 0, 6, 2, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 0, 7, 2, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 4, 6, 2, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 4, 7, 2, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 2, 6, 0, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 2, 7, 0, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 2, 6, 4, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 2, 7, 4, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 0, 3, 6, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 4, 3, 6, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 2, 3, 8, structureBoundingBox);
        this.a(world, Block.TORCH.id, 0, 2, 4, 7, structureBoundingBox);
        this.a(world, Block.TORCH.id, 0, 1, 4, 6, structureBoundingBox);
        this.a(world, Block.TORCH.id, 0, 3, 4, 6, structureBoundingBox);
        this.a(world, Block.TORCH.id, 0, 2, 4, 5, structureBoundingBox);
        final int c = this.c(Block.LADDER.id, 4);
        for (int i = 1; i <= 9; ++i) {
            this.a(world, Block.LADDER.id, c, 3, i, 3, structureBoundingBox);
        }
        this.a(world, 0, 0, 2, 1, 0, structureBoundingBox);
        this.a(world, 0, 0, 2, 2, 0, structureBoundingBox);
        this.a(world, structureBoundingBox, random, 2, 1, 0, this.c(Block.WOODEN_DOOR.id, 1));
        if (this.a(world, 2, 0, -1, structureBoundingBox) == 0 && this.a(world, 2, -1, -1, structureBoundingBox) != 0) {
            this.a(world, Block.COBBLESTONE_STAIRS.id, this.c(Block.COBBLESTONE_STAIRS.id, 3), 2, 0, -1, structureBoundingBox);
        }
        for (int j = 0; j < 9; ++j) {
            for (int k = 0; k < 5; ++k) {
                this.b(world, k, 12, j, structureBoundingBox);
                this.b(world, Block.COBBLESTONE.id, 0, k, -1, j, structureBoundingBox);
            }
        }
        this.a(world, structureBoundingBox, 2, 1, 2, 1);
        return true;
    }
    
    protected int b(final int n) {
        return 2;
    }
}
