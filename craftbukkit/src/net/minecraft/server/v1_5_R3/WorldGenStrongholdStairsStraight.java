// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenStrongholdStairsStraight extends WorldGenStrongholdPiece
{
    private final WorldGenStrongholdDoorType a;
    
    public WorldGenStrongholdStairsStraight(final int n, final Random random, final StructureBoundingBox e, final int f) {
        super(n);
        this.f = f;
        this.a = this.a(random);
        this.e = e;
    }
    
    public void a(final StructurePiece structurePiece, final List list, final Random random) {
        this.a((WorldGenStrongholdStart)structurePiece, list, random, 1, 1);
    }
    
    public static WorldGenStrongholdStairsStraight a(final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        final StructureBoundingBox a = StructureBoundingBox.a(n, n2, n3, -1, -7, 0, 5, 11, 8, n4);
        if (!WorldGenStrongholdPiece.a(a) || StructurePiece.a(list, a) != null) {
            return null;
        }
        return new WorldGenStrongholdStairsStraight(n5, random, a, n4);
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        if (this.a(world, structureBoundingBox)) {
            return false;
        }
        this.a(world, structureBoundingBox, 0, 0, 0, 4, 10, 7, true, random, WorldGenStrongholdPieces.e);
        this.a(world, random, structureBoundingBox, this.a, 1, 7, 0);
        this.a(world, random, structureBoundingBox, WorldGenStrongholdDoorType.a, 1, 1, 7);
        final int c = this.c(Block.COBBLESTONE_STAIRS.id, 2);
        for (int i = 0; i < 6; ++i) {
            this.a(world, Block.COBBLESTONE_STAIRS.id, c, 1, 6 - i, 1 + i, structureBoundingBox);
            this.a(world, Block.COBBLESTONE_STAIRS.id, c, 2, 6 - i, 1 + i, structureBoundingBox);
            this.a(world, Block.COBBLESTONE_STAIRS.id, c, 3, 6 - i, 1 + i, structureBoundingBox);
            if (i < 5) {
                this.a(world, Block.SMOOTH_BRICK.id, 0, 1, 5 - i, 1 + i, structureBoundingBox);
                this.a(world, Block.SMOOTH_BRICK.id, 0, 2, 5 - i, 1 + i, structureBoundingBox);
                this.a(world, Block.SMOOTH_BRICK.id, 0, 3, 5 - i, 1 + i, structureBoundingBox);
            }
        }
        return true;
    }
}
