// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenStrongholdChestCorridor extends WorldGenStrongholdPiece
{
    private static final StructurePieceTreasure[] a;
    private final WorldGenStrongholdDoorType b;
    private boolean c;
    
    public WorldGenStrongholdChestCorridor(final int n, final Random random, final StructureBoundingBox e, final int f) {
        super(n);
        this.f = f;
        this.b = this.a(random);
        this.e = e;
    }
    
    public void a(final StructurePiece structurePiece, final List list, final Random random) {
        this.a((WorldGenStrongholdStart)structurePiece, list, random, 1, 1);
    }
    
    public static WorldGenStrongholdChestCorridor a(final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        final StructureBoundingBox a = StructureBoundingBox.a(n, n2, n3, -1, -1, 0, 5, 5, 7, n4);
        if (!WorldGenStrongholdPiece.a(a) || StructurePiece.a(list, a) != null) {
            return null;
        }
        return new WorldGenStrongholdChestCorridor(n5, random, a, n4);
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        if (this.a(world, structureBoundingBox)) {
            return false;
        }
        this.a(world, structureBoundingBox, 0, 0, 0, 4, 4, 6, true, random, WorldGenStrongholdPieces.e);
        this.a(world, random, structureBoundingBox, this.b, 1, 1, 0);
        this.a(world, random, structureBoundingBox, WorldGenStrongholdDoorType.a, 1, 1, 6);
        this.a(world, structureBoundingBox, 3, 1, 2, 3, 1, 4, Block.SMOOTH_BRICK.id, Block.SMOOTH_BRICK.id, false);
        this.a(world, Block.STEP.id, 5, 3, 1, 1, structureBoundingBox);
        this.a(world, Block.STEP.id, 5, 3, 1, 5, structureBoundingBox);
        this.a(world, Block.STEP.id, 5, 3, 2, 2, structureBoundingBox);
        this.a(world, Block.STEP.id, 5, 3, 2, 4, structureBoundingBox);
        for (int i = 2; i <= 4; ++i) {
            this.a(world, Block.STEP.id, 5, 2, 1, i, structureBoundingBox);
        }
        if (!this.c && structureBoundingBox.b(this.a(3, 3), this.a(2), this.b(3, 3))) {
            this.c = true;
            this.a(world, structureBoundingBox, random, 3, 2, 3, StructurePieceTreasure.a(WorldGenStrongholdChestCorridor.a, Item.ENCHANTED_BOOK.b(random)), 2 + random.nextInt(2));
        }
        return true;
    }
    
    static {
        a = new StructurePieceTreasure[] { new StructurePieceTreasure(Item.ENDER_PEARL.id, 0, 1, 1, 10), new StructurePieceTreasure(Item.DIAMOND.id, 0, 1, 3, 3), new StructurePieceTreasure(Item.IRON_INGOT.id, 0, 1, 5, 10), new StructurePieceTreasure(Item.GOLD_INGOT.id, 0, 1, 3, 5), new StructurePieceTreasure(Item.REDSTONE.id, 0, 4, 9, 5), new StructurePieceTreasure(Item.BREAD.id, 0, 1, 3, 15), new StructurePieceTreasure(Item.APPLE.id, 0, 1, 3, 15), new StructurePieceTreasure(Item.IRON_PICKAXE.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_SWORD.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_CHESTPLATE.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_HELMET.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_LEGGINGS.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_BOOTS.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.GOLDEN_APPLE.id, 0, 1, 1, 1) };
    }
}
