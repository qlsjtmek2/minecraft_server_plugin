// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class WorldGenPyramidPiece extends WorldGenScatteredPiece
{
    private boolean[] h;
    private static final StructurePieceTreasure[] i;
    
    public WorldGenPyramidPiece(final Random random, final int n, final int n2) {
        super(random, n, 64, n2, 21, 15, 21);
        this.h = new boolean[4];
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        this.a(world, structureBoundingBox, 0, -4, 0, this.a - 1, 0, this.c - 1, Block.SANDSTONE.id, Block.SANDSTONE.id, false);
        for (int i = 1; i <= 9; ++i) {
            this.a(world, structureBoundingBox, i, i, i, this.a - 1 - i, i, this.c - 1 - i, Block.SANDSTONE.id, Block.SANDSTONE.id, false);
            this.a(world, structureBoundingBox, i + 1, i, i + 1, this.a - 2 - i, i, this.c - 2 - i, 0, 0, false);
        }
        for (int j = 0; j < this.a; ++j) {
            for (int k = 0; k < this.c; ++k) {
                this.b(world, Block.SANDSTONE.id, 0, j, -5, k, structureBoundingBox);
            }
        }
        final int c = this.c(Block.SANDSTONE_STAIRS.id, 3);
        final int c2 = this.c(Block.SANDSTONE_STAIRS.id, 2);
        final int c3 = this.c(Block.SANDSTONE_STAIRS.id, 0);
        final int c4 = this.c(Block.SANDSTONE_STAIRS.id, 1);
        final int n = 1;
        final int n2 = 11;
        this.a(world, structureBoundingBox, 0, 0, 0, 4, 9, 4, Block.SANDSTONE.id, 0, false);
        this.a(world, structureBoundingBox, 1, 10, 1, 3, 10, 3, Block.SANDSTONE.id, Block.SANDSTONE.id, false);
        this.a(world, Block.SANDSTONE_STAIRS.id, c, 2, 10, 0, structureBoundingBox);
        this.a(world, Block.SANDSTONE_STAIRS.id, c2, 2, 10, 4, structureBoundingBox);
        this.a(world, Block.SANDSTONE_STAIRS.id, c3, 0, 10, 2, structureBoundingBox);
        this.a(world, Block.SANDSTONE_STAIRS.id, c4, 4, 10, 2, structureBoundingBox);
        this.a(world, structureBoundingBox, this.a - 5, 0, 0, this.a - 1, 9, 4, Block.SANDSTONE.id, 0, false);
        this.a(world, structureBoundingBox, this.a - 4, 10, 1, this.a - 2, 10, 3, Block.SANDSTONE.id, Block.SANDSTONE.id, false);
        this.a(world, Block.SANDSTONE_STAIRS.id, c, this.a - 3, 10, 0, structureBoundingBox);
        this.a(world, Block.SANDSTONE_STAIRS.id, c2, this.a - 3, 10, 4, structureBoundingBox);
        this.a(world, Block.SANDSTONE_STAIRS.id, c3, this.a - 5, 10, 2, structureBoundingBox);
        this.a(world, Block.SANDSTONE_STAIRS.id, c4, this.a - 1, 10, 2, structureBoundingBox);
        this.a(world, structureBoundingBox, 8, 0, 0, 12, 4, 4, Block.SANDSTONE.id, 0, false);
        this.a(world, structureBoundingBox, 9, 1, 0, 11, 3, 4, 0, 0, false);
        this.a(world, Block.SANDSTONE.id, 2, 9, 1, 1, structureBoundingBox);
        this.a(world, Block.SANDSTONE.id, 2, 9, 2, 1, structureBoundingBox);
        this.a(world, Block.SANDSTONE.id, 2, 9, 3, 1, structureBoundingBox);
        this.a(world, Block.SANDSTONE.id, 2, 10, 3, 1, structureBoundingBox);
        this.a(world, Block.SANDSTONE.id, 2, 11, 3, 1, structureBoundingBox);
        this.a(world, Block.SANDSTONE.id, 2, 11, 2, 1, structureBoundingBox);
        this.a(world, Block.SANDSTONE.id, 2, 11, 1, 1, structureBoundingBox);
        this.a(world, structureBoundingBox, 4, 1, 1, 8, 3, 3, Block.SANDSTONE.id, 0, false);
        this.a(world, structureBoundingBox, 4, 1, 2, 8, 2, 2, 0, 0, false);
        this.a(world, structureBoundingBox, 12, 1, 1, 16, 3, 3, Block.SANDSTONE.id, 0, false);
        this.a(world, structureBoundingBox, 12, 1, 2, 16, 2, 2, 0, 0, false);
        this.a(world, structureBoundingBox, 5, 4, 5, this.a - 6, 4, this.c - 6, Block.SANDSTONE.id, Block.SANDSTONE.id, false);
        this.a(world, structureBoundingBox, 9, 4, 9, 11, 4, 11, 0, 0, false);
        this.a(world, structureBoundingBox, 8, 1, 8, 8, 3, 8, Block.SANDSTONE.id, 2, Block.SANDSTONE.id, 2, false);
        this.a(world, structureBoundingBox, 12, 1, 8, 12, 3, 8, Block.SANDSTONE.id, 2, Block.SANDSTONE.id, 2, false);
        this.a(world, structureBoundingBox, 8, 1, 12, 8, 3, 12, Block.SANDSTONE.id, 2, Block.SANDSTONE.id, 2, false);
        this.a(world, structureBoundingBox, 12, 1, 12, 12, 3, 12, Block.SANDSTONE.id, 2, Block.SANDSTONE.id, 2, false);
        this.a(world, structureBoundingBox, 1, 1, 5, 4, 4, 11, Block.SANDSTONE.id, Block.SANDSTONE.id, false);
        this.a(world, structureBoundingBox, this.a - 5, 1, 5, this.a - 2, 4, 11, Block.SANDSTONE.id, Block.SANDSTONE.id, false);
        this.a(world, structureBoundingBox, 6, 7, 9, 6, 7, 11, Block.SANDSTONE.id, Block.SANDSTONE.id, false);
        this.a(world, structureBoundingBox, this.a - 7, 7, 9, this.a - 7, 7, 11, Block.SANDSTONE.id, Block.SANDSTONE.id, false);
        this.a(world, structureBoundingBox, 5, 5, 9, 5, 7, 11, Block.SANDSTONE.id, 2, Block.SANDSTONE.id, 2, false);
        this.a(world, structureBoundingBox, this.a - 6, 5, 9, this.a - 6, 7, 11, Block.SANDSTONE.id, 2, Block.SANDSTONE.id, 2, false);
        this.a(world, 0, 0, 5, 5, 10, structureBoundingBox);
        this.a(world, 0, 0, 5, 6, 10, structureBoundingBox);
        this.a(world, 0, 0, 6, 6, 10, structureBoundingBox);
        this.a(world, 0, 0, this.a - 6, 5, 10, structureBoundingBox);
        this.a(world, 0, 0, this.a - 6, 6, 10, structureBoundingBox);
        this.a(world, 0, 0, this.a - 7, 6, 10, structureBoundingBox);
        this.a(world, structureBoundingBox, 2, 4, 4, 2, 6, 4, 0, 0, false);
        this.a(world, structureBoundingBox, this.a - 3, 4, 4, this.a - 3, 6, 4, 0, 0, false);
        this.a(world, Block.SANDSTONE_STAIRS.id, c, 2, 4, 5, structureBoundingBox);
        this.a(world, Block.SANDSTONE_STAIRS.id, c, 2, 3, 4, structureBoundingBox);
        this.a(world, Block.SANDSTONE_STAIRS.id, c, this.a - 3, 4, 5, structureBoundingBox);
        this.a(world, Block.SANDSTONE_STAIRS.id, c, this.a - 3, 3, 4, structureBoundingBox);
        this.a(world, structureBoundingBox, 1, 1, 3, 2, 2, 3, Block.SANDSTONE.id, Block.SANDSTONE.id, false);
        this.a(world, structureBoundingBox, this.a - 3, 1, 3, this.a - 2, 2, 3, Block.SANDSTONE.id, Block.SANDSTONE.id, false);
        this.a(world, Block.SANDSTONE_STAIRS.id, 0, 1, 1, 2, structureBoundingBox);
        this.a(world, Block.SANDSTONE_STAIRS.id, 0, this.a - 2, 1, 2, structureBoundingBox);
        this.a(world, Block.STEP.id, 1, 1, 2, 2, structureBoundingBox);
        this.a(world, Block.STEP.id, 1, this.a - 2, 2, 2, structureBoundingBox);
        this.a(world, Block.SANDSTONE_STAIRS.id, c4, 2, 1, 2, structureBoundingBox);
        this.a(world, Block.SANDSTONE_STAIRS.id, c3, this.a - 3, 1, 2, structureBoundingBox);
        this.a(world, structureBoundingBox, 4, 3, 5, 4, 3, 18, Block.SANDSTONE.id, Block.SANDSTONE.id, false);
        this.a(world, structureBoundingBox, this.a - 5, 3, 5, this.a - 5, 3, 17, Block.SANDSTONE.id, Block.SANDSTONE.id, false);
        this.a(world, structureBoundingBox, 3, 1, 5, 4, 2, 16, 0, 0, false);
        this.a(world, structureBoundingBox, this.a - 6, 1, 5, this.a - 5, 2, 16, 0, 0, false);
        for (int l = 5; l <= 17; l += 2) {
            this.a(world, Block.SANDSTONE.id, 2, 4, 1, l, structureBoundingBox);
            this.a(world, Block.SANDSTONE.id, 1, 4, 2, l, structureBoundingBox);
            this.a(world, Block.SANDSTONE.id, 2, this.a - 5, 1, l, structureBoundingBox);
            this.a(world, Block.SANDSTONE.id, 1, this.a - 5, 2, l, structureBoundingBox);
        }
        this.a(world, Block.WOOL.id, n, 10, 0, 7, structureBoundingBox);
        this.a(world, Block.WOOL.id, n, 10, 0, 8, structureBoundingBox);
        this.a(world, Block.WOOL.id, n, 9, 0, 9, structureBoundingBox);
        this.a(world, Block.WOOL.id, n, 11, 0, 9, structureBoundingBox);
        this.a(world, Block.WOOL.id, n, 8, 0, 10, structureBoundingBox);
        this.a(world, Block.WOOL.id, n, 12, 0, 10, structureBoundingBox);
        this.a(world, Block.WOOL.id, n, 7, 0, 10, structureBoundingBox);
        this.a(world, Block.WOOL.id, n, 13, 0, 10, structureBoundingBox);
        this.a(world, Block.WOOL.id, n, 9, 0, 11, structureBoundingBox);
        this.a(world, Block.WOOL.id, n, 11, 0, 11, structureBoundingBox);
        this.a(world, Block.WOOL.id, n, 10, 0, 12, structureBoundingBox);
        this.a(world, Block.WOOL.id, n, 10, 0, 13, structureBoundingBox);
        this.a(world, Block.WOOL.id, n2, 10, 0, 10, structureBoundingBox);
        for (int n3 = 0; n3 <= this.a - 1; n3 += this.a - 1) {
            this.a(world, Block.SANDSTONE.id, 2, n3, 2, 1, structureBoundingBox);
            this.a(world, Block.WOOL.id, n, n3, 2, 2, structureBoundingBox);
            this.a(world, Block.SANDSTONE.id, 2, n3, 2, 3, structureBoundingBox);
            this.a(world, Block.SANDSTONE.id, 2, n3, 3, 1, structureBoundingBox);
            this.a(world, Block.WOOL.id, n, n3, 3, 2, structureBoundingBox);
            this.a(world, Block.SANDSTONE.id, 2, n3, 3, 3, structureBoundingBox);
            this.a(world, Block.WOOL.id, n, n3, 4, 1, structureBoundingBox);
            this.a(world, Block.SANDSTONE.id, 1, n3, 4, 2, structureBoundingBox);
            this.a(world, Block.WOOL.id, n, n3, 4, 3, structureBoundingBox);
            this.a(world, Block.SANDSTONE.id, 2, n3, 5, 1, structureBoundingBox);
            this.a(world, Block.WOOL.id, n, n3, 5, 2, structureBoundingBox);
            this.a(world, Block.SANDSTONE.id, 2, n3, 5, 3, structureBoundingBox);
            this.a(world, Block.WOOL.id, n, n3, 6, 1, structureBoundingBox);
            this.a(world, Block.SANDSTONE.id, 1, n3, 6, 2, structureBoundingBox);
            this.a(world, Block.WOOL.id, n, n3, 6, 3, structureBoundingBox);
            this.a(world, Block.WOOL.id, n, n3, 7, 1, structureBoundingBox);
            this.a(world, Block.WOOL.id, n, n3, 7, 2, structureBoundingBox);
            this.a(world, Block.WOOL.id, n, n3, 7, 3, structureBoundingBox);
            this.a(world, Block.SANDSTONE.id, 2, n3, 8, 1, structureBoundingBox);
            this.a(world, Block.SANDSTONE.id, 2, n3, 8, 2, structureBoundingBox);
            this.a(world, Block.SANDSTONE.id, 2, n3, 8, 3, structureBoundingBox);
        }
        for (int n4 = 2; n4 <= this.a - 3; n4 += this.a - 3 - 2) {
            this.a(world, Block.SANDSTONE.id, 2, n4 - 1, 2, 0, structureBoundingBox);
            this.a(world, Block.WOOL.id, n, n4, 2, 0, structureBoundingBox);
            this.a(world, Block.SANDSTONE.id, 2, n4 + 1, 2, 0, structureBoundingBox);
            this.a(world, Block.SANDSTONE.id, 2, n4 - 1, 3, 0, structureBoundingBox);
            this.a(world, Block.WOOL.id, n, n4, 3, 0, structureBoundingBox);
            this.a(world, Block.SANDSTONE.id, 2, n4 + 1, 3, 0, structureBoundingBox);
            this.a(world, Block.WOOL.id, n, n4 - 1, 4, 0, structureBoundingBox);
            this.a(world, Block.SANDSTONE.id, 1, n4, 4, 0, structureBoundingBox);
            this.a(world, Block.WOOL.id, n, n4 + 1, 4, 0, structureBoundingBox);
            this.a(world, Block.SANDSTONE.id, 2, n4 - 1, 5, 0, structureBoundingBox);
            this.a(world, Block.WOOL.id, n, n4, 5, 0, structureBoundingBox);
            this.a(world, Block.SANDSTONE.id, 2, n4 + 1, 5, 0, structureBoundingBox);
            this.a(world, Block.WOOL.id, n, n4 - 1, 6, 0, structureBoundingBox);
            this.a(world, Block.SANDSTONE.id, 1, n4, 6, 0, structureBoundingBox);
            this.a(world, Block.WOOL.id, n, n4 + 1, 6, 0, structureBoundingBox);
            this.a(world, Block.WOOL.id, n, n4 - 1, 7, 0, structureBoundingBox);
            this.a(world, Block.WOOL.id, n, n4, 7, 0, structureBoundingBox);
            this.a(world, Block.WOOL.id, n, n4 + 1, 7, 0, structureBoundingBox);
            this.a(world, Block.SANDSTONE.id, 2, n4 - 1, 8, 0, structureBoundingBox);
            this.a(world, Block.SANDSTONE.id, 2, n4, 8, 0, structureBoundingBox);
            this.a(world, Block.SANDSTONE.id, 2, n4 + 1, 8, 0, structureBoundingBox);
        }
        this.a(world, structureBoundingBox, 8, 4, 0, 12, 6, 0, Block.SANDSTONE.id, 2, Block.SANDSTONE.id, 2, false);
        this.a(world, 0, 0, 8, 6, 0, structureBoundingBox);
        this.a(world, 0, 0, 12, 6, 0, structureBoundingBox);
        this.a(world, Block.WOOL.id, n, 9, 5, 0, structureBoundingBox);
        this.a(world, Block.SANDSTONE.id, 1, 10, 5, 0, structureBoundingBox);
        this.a(world, Block.WOOL.id, n, 11, 5, 0, structureBoundingBox);
        this.a(world, structureBoundingBox, 8, -14, 8, 12, -11, 12, Block.SANDSTONE.id, 2, Block.SANDSTONE.id, 2, false);
        this.a(world, structureBoundingBox, 8, -10, 8, 12, -10, 12, Block.SANDSTONE.id, 1, Block.SANDSTONE.id, 1, false);
        this.a(world, structureBoundingBox, 8, -9, 8, 12, -9, 12, Block.SANDSTONE.id, 2, Block.SANDSTONE.id, 2, false);
        this.a(world, structureBoundingBox, 8, -8, 8, 12, -1, 12, Block.SANDSTONE.id, Block.SANDSTONE.id, false);
        this.a(world, structureBoundingBox, 9, -11, 9, 11, -1, 11, 0, 0, false);
        this.a(world, Block.STONE_PLATE.id, 0, 10, -11, 10, structureBoundingBox);
        this.a(world, structureBoundingBox, 9, -13, 9, 11, -13, 11, Block.TNT.id, 0, false);
        this.a(world, 0, 0, 8, -11, 10, structureBoundingBox);
        this.a(world, 0, 0, 8, -10, 10, structureBoundingBox);
        this.a(world, Block.SANDSTONE.id, 1, 7, -10, 10, structureBoundingBox);
        this.a(world, Block.SANDSTONE.id, 2, 7, -11, 10, structureBoundingBox);
        this.a(world, 0, 0, 12, -11, 10, structureBoundingBox);
        this.a(world, 0, 0, 12, -10, 10, structureBoundingBox);
        this.a(world, Block.SANDSTONE.id, 1, 13, -10, 10, structureBoundingBox);
        this.a(world, Block.SANDSTONE.id, 2, 13, -11, 10, structureBoundingBox);
        this.a(world, 0, 0, 10, -11, 8, structureBoundingBox);
        this.a(world, 0, 0, 10, -10, 8, structureBoundingBox);
        this.a(world, Block.SANDSTONE.id, 1, 10, -10, 7, structureBoundingBox);
        this.a(world, Block.SANDSTONE.id, 2, 10, -11, 7, structureBoundingBox);
        this.a(world, 0, 0, 10, -11, 12, structureBoundingBox);
        this.a(world, 0, 0, 10, -10, 12, structureBoundingBox);
        this.a(world, Block.SANDSTONE.id, 1, 10, -10, 13, structureBoundingBox);
        this.a(world, Block.SANDSTONE.id, 2, 10, -11, 13, structureBoundingBox);
        for (int n5 = 0; n5 < 4; ++n5) {
            if (!this.h[n5]) {
                this.h[n5] = this.a(world, structureBoundingBox, random, 10 + Direction.a[n5] * 2, -11, 10 + Direction.b[n5] * 2, StructurePieceTreasure.a(WorldGenPyramidPiece.i, Item.ENCHANTED_BOOK.b(random)), 2 + random.nextInt(5));
            }
        }
        return true;
    }
    
    static {
        i = new StructurePieceTreasure[] { new StructurePieceTreasure(Item.DIAMOND.id, 0, 1, 3, 3), new StructurePieceTreasure(Item.IRON_INGOT.id, 0, 1, 5, 10), new StructurePieceTreasure(Item.GOLD_INGOT.id, 0, 2, 7, 15), new StructurePieceTreasure(Item.EMERALD.id, 0, 1, 3, 2), new StructurePieceTreasure(Item.BONE.id, 0, 4, 6, 20), new StructurePieceTreasure(Item.ROTTEN_FLESH.id, 0, 3, 7, 16) };
    }
}
