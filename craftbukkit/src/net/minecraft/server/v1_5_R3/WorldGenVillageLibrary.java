// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Random;

public class WorldGenVillageLibrary extends WorldGenVillagePiece
{
    private int a;
    
    public WorldGenVillageLibrary(final WorldGenVillageStartPiece worldGenVillageStartPiece, final int n, final Random random, final StructureBoundingBox e, final int f) {
        super(worldGenVillageStartPiece, n);
        this.a = -1;
        this.f = f;
        this.e = e;
    }
    
    public static WorldGenVillageLibrary a(final WorldGenVillageStartPiece worldGenVillageStartPiece, final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        final StructureBoundingBox a = StructureBoundingBox.a(n, n2, n3, 0, 0, 0, 9, 9, 6, n4);
        if (!WorldGenVillagePiece.a(a) || StructurePiece.a(list, a) != null) {
            return null;
        }
        return new WorldGenVillageLibrary(worldGenVillageStartPiece, n5, random, a, n4);
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        if (this.a < 0) {
            this.a = this.b(world, structureBoundingBox);
            if (this.a < 0) {
                return true;
            }
            this.e.a(0, this.a - this.e.e + 9 - 1, 0);
        }
        this.a(world, structureBoundingBox, 1, 1, 1, 7, 5, 4, 0, 0, false);
        this.a(world, structureBoundingBox, 0, 0, 0, 8, 0, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 0, 5, 0, 8, 5, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 0, 6, 1, 8, 6, 4, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 0, 7, 2, 8, 7, 3, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        final int c = this.c(Block.WOOD_STAIRS.id, 3);
        final int c2 = this.c(Block.WOOD_STAIRS.id, 2);
        for (int i = -1; i <= 2; ++i) {
            for (int j = 0; j <= 8; ++j) {
                this.a(world, Block.WOOD_STAIRS.id, c, j, 6 + i, i, structureBoundingBox);
                this.a(world, Block.WOOD_STAIRS.id, c2, j, 6 + i, 5 - i, structureBoundingBox);
            }
        }
        this.a(world, structureBoundingBox, 0, 1, 0, 0, 1, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 1, 1, 5, 8, 1, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 8, 1, 0, 8, 1, 4, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 2, 1, 0, 7, 1, 0, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 0, 2, 0, 0, 4, 0, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 0, 2, 5, 0, 4, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 8, 2, 5, 8, 4, 5, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 8, 2, 0, 8, 4, 0, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 0, 2, 1, 0, 4, 4, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 1, 2, 5, 7, 4, 5, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 8, 2, 1, 8, 4, 4, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 1, 2, 0, 7, 4, 0, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, Block.THIN_GLASS.id, 0, 4, 2, 0, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 5, 2, 0, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 6, 2, 0, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 4, 3, 0, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 5, 3, 0, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 6, 3, 0, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 0, 2, 2, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 0, 2, 3, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 0, 3, 2, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 0, 3, 3, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 8, 2, 2, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 8, 2, 3, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 8, 3, 2, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 8, 3, 3, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 2, 2, 5, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 3, 2, 5, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 5, 2, 5, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 6, 2, 5, structureBoundingBox);
        this.a(world, structureBoundingBox, 1, 4, 1, 7, 4, 1, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 1, 4, 4, 7, 4, 4, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 1, 3, 4, 7, 3, 4, Block.BOOKSHELF.id, Block.BOOKSHELF.id, false);
        this.a(world, Block.WOOD.id, 0, 7, 1, 4, structureBoundingBox);
        this.a(world, Block.WOOD_STAIRS.id, this.c(Block.WOOD_STAIRS.id, 0), 7, 1, 3, structureBoundingBox);
        final int c3 = this.c(Block.WOOD_STAIRS.id, 3);
        this.a(world, Block.WOOD_STAIRS.id, c3, 6, 1, 4, structureBoundingBox);
        this.a(world, Block.WOOD_STAIRS.id, c3, 5, 1, 4, structureBoundingBox);
        this.a(world, Block.WOOD_STAIRS.id, c3, 4, 1, 4, structureBoundingBox);
        this.a(world, Block.WOOD_STAIRS.id, c3, 3, 1, 4, structureBoundingBox);
        this.a(world, Block.FENCE.id, 0, 6, 1, 3, structureBoundingBox);
        this.a(world, Block.WOOD_PLATE.id, 0, 6, 2, 3, structureBoundingBox);
        this.a(world, Block.FENCE.id, 0, 4, 1, 3, structureBoundingBox);
        this.a(world, Block.WOOD_PLATE.id, 0, 4, 2, 3, structureBoundingBox);
        this.a(world, Block.WORKBENCH.id, 0, 7, 1, 1, structureBoundingBox);
        this.a(world, 0, 0, 1, 1, 0, structureBoundingBox);
        this.a(world, 0, 0, 1, 2, 0, structureBoundingBox);
        this.a(world, structureBoundingBox, random, 1, 1, 0, this.c(Block.WOODEN_DOOR.id, 1));
        if (this.a(world, 1, 0, -1, structureBoundingBox) == 0 && this.a(world, 1, -1, -1, structureBoundingBox) != 0) {
            this.a(world, Block.COBBLESTONE_STAIRS.id, this.c(Block.COBBLESTONE_STAIRS.id, 3), 1, 0, -1, structureBoundingBox);
        }
        for (int k = 0; k < 6; ++k) {
            for (int l = 0; l < 9; ++l) {
                this.b(world, l, 9, k, structureBoundingBox);
                this.b(world, Block.COBBLESTONE.id, 0, l, -1, k, structureBoundingBox);
            }
        }
        this.a(world, structureBoundingBox, 2, 1, 2, 1);
        return true;
    }
    
    protected int b(final int n) {
        return 1;
    }
}
