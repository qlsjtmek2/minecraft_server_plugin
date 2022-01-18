// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;
import java.util.List;

public class WorldGenMineshaftPieces
{
    private static final StructurePieceTreasure[] a;
    
    private static StructurePiece a(final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        final int nextInt = random.nextInt(100);
        if (nextInt >= 80) {
            final StructureBoundingBox a = WorldGenMineshaftCross.a(list, random, n, n2, n3, n4);
            if (a != null) {
                return new WorldGenMineshaftCross(n5, random, a, n4);
            }
        }
        else if (nextInt >= 70) {
            final StructureBoundingBox a2 = WorldGenMineshaftStairs.a(list, random, n, n2, n3, n4);
            if (a2 != null) {
                return new WorldGenMineshaftStairs(n5, random, a2, n4);
            }
        }
        else {
            final StructureBoundingBox a3 = WorldGenMineshaftCorridor.a(list, random, n, n2, n3, n4);
            if (a3 != null) {
                return new WorldGenMineshaftCorridor(n5, random, a3, n4);
            }
        }
        return null;
    }
    
    private static StructurePiece b(final StructurePiece structurePiece, final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        if (n5 > 8) {
            return null;
        }
        if (Math.abs(n - structurePiece.b().a) > 80 || Math.abs(n3 - structurePiece.b().c) > 80) {
            return null;
        }
        final StructurePiece a = a(list, random, n, n2, n3, n4, n5 + 1);
        if (a != null) {
            list.add(a);
            a.a(structurePiece, list, random);
        }
        return a;
    }
    
    static {
        a = new StructurePieceTreasure[] { new StructurePieceTreasure(Item.IRON_INGOT.id, 0, 1, 5, 10), new StructurePieceTreasure(Item.GOLD_INGOT.id, 0, 1, 3, 5), new StructurePieceTreasure(Item.REDSTONE.id, 0, 4, 9, 5), new StructurePieceTreasure(Item.INK_SACK.id, 4, 4, 9, 5), new StructurePieceTreasure(Item.DIAMOND.id, 0, 1, 2, 3), new StructurePieceTreasure(Item.COAL.id, 0, 3, 8, 10), new StructurePieceTreasure(Item.BREAD.id, 0, 1, 3, 15), new StructurePieceTreasure(Item.IRON_PICKAXE.id, 0, 1, 1, 1), new StructurePieceTreasure(Block.RAILS.id, 0, 4, 8, 1), new StructurePieceTreasure(Item.MELON_SEEDS.id, 0, 2, 4, 10), new StructurePieceTreasure(Item.PUMPKIN_SEEDS.id, 0, 2, 4, 10) };
    }
}
