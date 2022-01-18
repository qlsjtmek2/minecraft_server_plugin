// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Random;

public class WorldGenVillageBlacksmith extends WorldGenVillagePiece
{
    private static final StructurePieceTreasure[] a;
    private int b;
    private boolean c;
    
    public WorldGenVillageBlacksmith(final WorldGenVillageStartPiece worldGenVillageStartPiece, final int n, final Random random, final StructureBoundingBox e, final int f) {
        super(worldGenVillageStartPiece, n);
        this.b = -1;
        this.f = f;
        this.e = e;
    }
    
    public static WorldGenVillageBlacksmith a(final WorldGenVillageStartPiece worldGenVillageStartPiece, final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        final StructureBoundingBox a = StructureBoundingBox.a(n, n2, n3, 0, 0, 0, 10, 6, 7, n4);
        if (!WorldGenVillagePiece.a(a) || StructurePiece.a(list, a) != null) {
            return null;
        }
        return new WorldGenVillageBlacksmith(worldGenVillageStartPiece, n5, random, a, n4);
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        if (this.b < 0) {
            this.b = this.b(world, structureBoundingBox);
            if (this.b < 0) {
                return true;
            }
            this.e.a(0, this.b - this.e.e + 6 - 1, 0);
        }
        this.a(world, structureBoundingBox, 0, 1, 0, 9, 4, 6, 0, 0, false);
        this.a(world, structureBoundingBox, 0, 0, 0, 9, 0, 6, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 0, 4, 0, 9, 4, 6, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, structureBoundingBox, 0, 5, 0, 9, 5, 6, Block.STEP.id, Block.STEP.id, false);
        this.a(world, structureBoundingBox, 1, 5, 1, 8, 5, 5, 0, 0, false);
        this.a(world, structureBoundingBox, 1, 1, 0, 2, 3, 0, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 0, 1, 0, 0, 4, 0, Block.LOG.id, Block.LOG.id, false);
        this.a(world, structureBoundingBox, 3, 1, 0, 3, 4, 0, Block.LOG.id, Block.LOG.id, false);
        this.a(world, structureBoundingBox, 0, 1, 6, 0, 4, 6, Block.LOG.id, Block.LOG.id, false);
        this.a(world, Block.WOOD.id, 0, 3, 3, 1, structureBoundingBox);
        this.a(world, structureBoundingBox, 3, 1, 2, 3, 3, 2, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 4, 1, 3, 5, 3, 3, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 0, 1, 1, 0, 3, 5, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 1, 1, 6, 5, 3, 6, Block.WOOD.id, Block.WOOD.id, false);
        this.a(world, structureBoundingBox, 5, 1, 0, 5, 3, 0, Block.FENCE.id, Block.FENCE.id, false);
        this.a(world, structureBoundingBox, 9, 1, 0, 9, 3, 0, Block.FENCE.id, Block.FENCE.id, false);
        this.a(world, structureBoundingBox, 6, 1, 4, 9, 4, 6, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        this.a(world, Block.LAVA.id, 0, 7, 1, 5, structureBoundingBox);
        this.a(world, Block.LAVA.id, 0, 8, 1, 5, structureBoundingBox);
        this.a(world, Block.IRON_FENCE.id, 0, 9, 2, 5, structureBoundingBox);
        this.a(world, Block.IRON_FENCE.id, 0, 9, 2, 4, structureBoundingBox);
        this.a(world, structureBoundingBox, 7, 2, 4, 8, 2, 5, 0, 0, false);
        this.a(world, Block.COBBLESTONE.id, 0, 6, 1, 3, structureBoundingBox);
        this.a(world, Block.FURNACE.id, 0, 6, 2, 3, structureBoundingBox);
        this.a(world, Block.FURNACE.id, 0, 6, 3, 3, structureBoundingBox);
        this.a(world, Block.DOUBLE_STEP.id, 0, 8, 1, 1, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 0, 2, 2, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 0, 2, 4, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 2, 2, 6, structureBoundingBox);
        this.a(world, Block.THIN_GLASS.id, 0, 4, 2, 6, structureBoundingBox);
        this.a(world, Block.FENCE.id, 0, 2, 1, 4, structureBoundingBox);
        this.a(world, Block.WOOD_PLATE.id, 0, 2, 2, 4, structureBoundingBox);
        this.a(world, Block.WOOD.id, 0, 1, 1, 5, structureBoundingBox);
        this.a(world, Block.WOOD_STAIRS.id, this.c(Block.WOOD_STAIRS.id, 3), 2, 1, 5, structureBoundingBox);
        this.a(world, Block.WOOD_STAIRS.id, this.c(Block.WOOD_STAIRS.id, 1), 1, 1, 4, structureBoundingBox);
        if (!this.c && structureBoundingBox.b(this.a(5, 5), this.a(1), this.b(5, 5))) {
            this.c = true;
            this.a(world, structureBoundingBox, random, 5, 1, 5, WorldGenVillageBlacksmith.a, 3 + random.nextInt(6));
        }
        for (int i = 6; i <= 8; ++i) {
            if (this.a(world, i, 0, -1, structureBoundingBox) == 0 && this.a(world, i, -1, -1, structureBoundingBox) != 0) {
                this.a(world, Block.COBBLESTONE_STAIRS.id, this.c(Block.COBBLESTONE_STAIRS.id, 3), i, 0, -1, structureBoundingBox);
            }
        }
        for (int j = 0; j < 7; ++j) {
            for (int k = 0; k < 10; ++k) {
                this.b(world, k, 6, j, structureBoundingBox);
                this.b(world, Block.COBBLESTONE.id, 0, k, -1, j, structureBoundingBox);
            }
        }
        this.a(world, structureBoundingBox, 7, 1, 1, 1);
        return true;
    }
    
    protected int b(final int n) {
        return 3;
    }
    
    static {
        a = new StructurePieceTreasure[] { new StructurePieceTreasure(Item.DIAMOND.id, 0, 1, 3, 3), new StructurePieceTreasure(Item.IRON_INGOT.id, 0, 1, 5, 10), new StructurePieceTreasure(Item.GOLD_INGOT.id, 0, 1, 3, 5), new StructurePieceTreasure(Item.BREAD.id, 0, 1, 3, 15), new StructurePieceTreasure(Item.APPLE.id, 0, 1, 3, 15), new StructurePieceTreasure(Item.IRON_PICKAXE.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_SWORD.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_CHESTPLATE.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_HELMET.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_LEGGINGS.id, 0, 1, 1, 5), new StructurePieceTreasure(Item.IRON_BOOTS.id, 0, 1, 1, 5), new StructurePieceTreasure(Block.OBSIDIAN.id, 0, 3, 7, 5), new StructurePieceTreasure(Block.SAPLING.id, 0, 3, 7, 5) };
    }
}
