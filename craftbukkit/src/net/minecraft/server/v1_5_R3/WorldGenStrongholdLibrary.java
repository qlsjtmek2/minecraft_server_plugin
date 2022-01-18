// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenStrongholdLibrary extends WorldGenStrongholdPiece
{
    private static final StructurePieceTreasure[] b;
    protected final WorldGenStrongholdDoorType a;
    private final boolean c;
    
    public WorldGenStrongholdLibrary(final int n, final Random random, final StructureBoundingBox e, final int f) {
        super(n);
        this.f = f;
        this.a = this.a(random);
        this.e = e;
        this.c = (e.c() > 6);
    }
    
    public static WorldGenStrongholdLibrary a(final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        StructureBoundingBox structureBoundingBox = StructureBoundingBox.a(n, n2, n3, -4, -1, 0, 14, 11, 15, n4);
        if (!WorldGenStrongholdPiece.a(structureBoundingBox) || StructurePiece.a(list, structureBoundingBox) != null) {
            structureBoundingBox = StructureBoundingBox.a(n, n2, n3, -4, -1, 0, 14, 6, 15, n4);
            if (!WorldGenStrongholdPiece.a(structureBoundingBox) || StructurePiece.a(list, structureBoundingBox) != null) {
                return null;
            }
        }
        return new WorldGenStrongholdLibrary(n5, random, structureBoundingBox, n4);
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        if (this.a(world, structureBoundingBox)) {
            return false;
        }
        int n = 11;
        if (!this.c) {
            n = 6;
        }
        this.a(world, structureBoundingBox, 0, 0, 0, 13, n - 1, 14, true, random, WorldGenStrongholdPieces.e);
        this.a(world, random, structureBoundingBox, this.a, 4, 1, 0);
        this.a(world, structureBoundingBox, random, 0.07f, 2, 1, 1, 11, 4, 13, Block.WEB.id, Block.WEB.id, false);
        for (int i = 1; i <= 13; ++i) {
            if ((i - 1) % 4 == 0) {
                this.a(world, structureBoundingBox, 1, 1, i, 1, 4, i, Block.WOOD.id, Block.WOOD.id, false);
                this.a(world, structureBoundingBox, 12, 1, i, 12, 4, i, Block.WOOD.id, Block.WOOD.id, false);
                this.a(world, Block.TORCH.id, 0, 2, 3, i, structureBoundingBox);
                this.a(world, Block.TORCH.id, 0, 11, 3, i, structureBoundingBox);
                if (this.c) {
                    this.a(world, structureBoundingBox, 1, 6, i, 1, 9, i, Block.WOOD.id, Block.WOOD.id, false);
                    this.a(world, structureBoundingBox, 12, 6, i, 12, 9, i, Block.WOOD.id, Block.WOOD.id, false);
                }
            }
            else {
                this.a(world, structureBoundingBox, 1, 1, i, 1, 4, i, Block.BOOKSHELF.id, Block.BOOKSHELF.id, false);
                this.a(world, structureBoundingBox, 12, 1, i, 12, 4, i, Block.BOOKSHELF.id, Block.BOOKSHELF.id, false);
                if (this.c) {
                    this.a(world, structureBoundingBox, 1, 6, i, 1, 9, i, Block.BOOKSHELF.id, Block.BOOKSHELF.id, false);
                    this.a(world, structureBoundingBox, 12, 6, i, 12, 9, i, Block.BOOKSHELF.id, Block.BOOKSHELF.id, false);
                }
            }
        }
        for (int j = 3; j < 12; j += 2) {
            this.a(world, structureBoundingBox, 3, 1, j, 4, 3, j, Block.BOOKSHELF.id, Block.BOOKSHELF.id, false);
            this.a(world, structureBoundingBox, 6, 1, j, 7, 3, j, Block.BOOKSHELF.id, Block.BOOKSHELF.id, false);
            this.a(world, structureBoundingBox, 9, 1, j, 10, 3, j, Block.BOOKSHELF.id, Block.BOOKSHELF.id, false);
        }
        if (this.c) {
            this.a(world, structureBoundingBox, 1, 5, 1, 3, 5, 13, Block.WOOD.id, Block.WOOD.id, false);
            this.a(world, structureBoundingBox, 10, 5, 1, 12, 5, 13, Block.WOOD.id, Block.WOOD.id, false);
            this.a(world, structureBoundingBox, 4, 5, 1, 9, 5, 2, Block.WOOD.id, Block.WOOD.id, false);
            this.a(world, structureBoundingBox, 4, 5, 12, 9, 5, 13, Block.WOOD.id, Block.WOOD.id, false);
            this.a(world, Block.WOOD.id, 0, 9, 5, 11, structureBoundingBox);
            this.a(world, Block.WOOD.id, 0, 8, 5, 11, structureBoundingBox);
            this.a(world, Block.WOOD.id, 0, 9, 5, 10, structureBoundingBox);
            this.a(world, structureBoundingBox, 3, 6, 2, 3, 6, 12, Block.FENCE.id, Block.FENCE.id, false);
            this.a(world, structureBoundingBox, 10, 6, 2, 10, 6, 10, Block.FENCE.id, Block.FENCE.id, false);
            this.a(world, structureBoundingBox, 4, 6, 2, 9, 6, 2, Block.FENCE.id, Block.FENCE.id, false);
            this.a(world, structureBoundingBox, 4, 6, 12, 8, 6, 12, Block.FENCE.id, Block.FENCE.id, false);
            this.a(world, Block.FENCE.id, 0, 9, 6, 11, structureBoundingBox);
            this.a(world, Block.FENCE.id, 0, 8, 6, 11, structureBoundingBox);
            this.a(world, Block.FENCE.id, 0, 9, 6, 10, structureBoundingBox);
            final int c = this.c(Block.LADDER.id, 3);
            this.a(world, Block.LADDER.id, c, 10, 1, 13, structureBoundingBox);
            this.a(world, Block.LADDER.id, c, 10, 2, 13, structureBoundingBox);
            this.a(world, Block.LADDER.id, c, 10, 3, 13, structureBoundingBox);
            this.a(world, Block.LADDER.id, c, 10, 4, 13, structureBoundingBox);
            this.a(world, Block.LADDER.id, c, 10, 5, 13, structureBoundingBox);
            this.a(world, Block.LADDER.id, c, 10, 6, 13, structureBoundingBox);
            this.a(world, Block.LADDER.id, c, 10, 7, 13, structureBoundingBox);
            final int n2 = 7;
            final int n3 = 7;
            this.a(world, Block.FENCE.id, 0, n2 - 1, 9, n3, structureBoundingBox);
            this.a(world, Block.FENCE.id, 0, n2, 9, n3, structureBoundingBox);
            this.a(world, Block.FENCE.id, 0, n2 - 1, 8, n3, structureBoundingBox);
            this.a(world, Block.FENCE.id, 0, n2, 8, n3, structureBoundingBox);
            this.a(world, Block.FENCE.id, 0, n2 - 1, 7, n3, structureBoundingBox);
            this.a(world, Block.FENCE.id, 0, n2, 7, n3, structureBoundingBox);
            this.a(world, Block.FENCE.id, 0, n2 - 2, 7, n3, structureBoundingBox);
            this.a(world, Block.FENCE.id, 0, n2 + 1, 7, n3, structureBoundingBox);
            this.a(world, Block.FENCE.id, 0, n2 - 1, 7, n3 - 1, structureBoundingBox);
            this.a(world, Block.FENCE.id, 0, n2 - 1, 7, n3 + 1, structureBoundingBox);
            this.a(world, Block.FENCE.id, 0, n2, 7, n3 - 1, structureBoundingBox);
            this.a(world, Block.FENCE.id, 0, n2, 7, n3 + 1, structureBoundingBox);
            this.a(world, Block.TORCH.id, 0, n2 - 2, 8, n3, structureBoundingBox);
            this.a(world, Block.TORCH.id, 0, n2 + 1, 8, n3, structureBoundingBox);
            this.a(world, Block.TORCH.id, 0, n2 - 1, 8, n3 - 1, structureBoundingBox);
            this.a(world, Block.TORCH.id, 0, n2 - 1, 8, n3 + 1, structureBoundingBox);
            this.a(world, Block.TORCH.id, 0, n2, 8, n3 - 1, structureBoundingBox);
            this.a(world, Block.TORCH.id, 0, n2, 8, n3 + 1, structureBoundingBox);
        }
        this.a(world, structureBoundingBox, random, 3, 3, 5, StructurePieceTreasure.a(WorldGenStrongholdLibrary.b, Item.ENCHANTED_BOOK.a(random, 1, 5, 2)), 1 + random.nextInt(4));
        if (this.c) {
            this.a(world, 0, 0, 12, 9, 1, structureBoundingBox);
            this.a(world, structureBoundingBox, random, 12, 8, 1, StructurePieceTreasure.a(WorldGenStrongholdLibrary.b, Item.ENCHANTED_BOOK.a(random, 1, 5, 2)), 1 + random.nextInt(4));
        }
        return true;
    }
    
    static {
        b = new StructurePieceTreasure[] { new StructurePieceTreasure(Item.BOOK.id, 0, 1, 3, 20), new StructurePieceTreasure(Item.PAPER.id, 0, 2, 7, 20), new StructurePieceTreasure(Item.MAP_EMPTY.id, 0, 1, 1, 1), new StructurePieceTreasure(Item.COMPASS.id, 0, 1, 1, 1) };
    }
}
