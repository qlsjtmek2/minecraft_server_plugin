// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenStrongholdPrison extends WorldGenStrongholdPiece
{
    protected final WorldGenStrongholdDoorType a;
    
    public WorldGenStrongholdPrison(final int n, final Random random, final StructureBoundingBox e, final int f) {
        super(n);
        this.f = f;
        this.a = this.a(random);
        this.e = e;
    }
    
    public void a(final StructurePiece structurePiece, final List list, final Random random) {
        this.a((WorldGenStrongholdStart)structurePiece, list, random, 1, 1);
    }
    
    public static WorldGenStrongholdPrison a(final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        final StructureBoundingBox a = StructureBoundingBox.a(n, n2, n3, -1, -1, 0, 9, 5, 11, n4);
        if (!WorldGenStrongholdPiece.a(a) || StructurePiece.a(list, a) != null) {
            return null;
        }
        return new WorldGenStrongholdPrison(n5, random, a, n4);
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        if (this.a(world, structureBoundingBox)) {
            return false;
        }
        this.a(world, structureBoundingBox, 0, 0, 0, 8, 4, 10, true, random, WorldGenStrongholdPieces.e);
        this.a(world, random, structureBoundingBox, this.a, 1, 1, 0);
        this.a(world, structureBoundingBox, 1, 1, 10, 3, 3, 10, 0, 0, false);
        this.a(world, structureBoundingBox, 4, 1, 1, 4, 3, 1, false, random, WorldGenStrongholdPieces.e);
        this.a(world, structureBoundingBox, 4, 1, 3, 4, 3, 3, false, random, WorldGenStrongholdPieces.e);
        this.a(world, structureBoundingBox, 4, 1, 7, 4, 3, 7, false, random, WorldGenStrongholdPieces.e);
        this.a(world, structureBoundingBox, 4, 1, 9, 4, 3, 9, false, random, WorldGenStrongholdPieces.e);
        this.a(world, structureBoundingBox, 4, 1, 4, 4, 3, 6, Block.IRON_FENCE.id, Block.IRON_FENCE.id, false);
        this.a(world, structureBoundingBox, 5, 1, 5, 7, 3, 5, Block.IRON_FENCE.id, Block.IRON_FENCE.id, false);
        this.a(world, Block.IRON_FENCE.id, 0, 4, 3, 2, structureBoundingBox);
        this.a(world, Block.IRON_FENCE.id, 0, 4, 3, 8, structureBoundingBox);
        this.a(world, Block.IRON_DOOR_BLOCK.id, this.c(Block.IRON_DOOR_BLOCK.id, 3), 4, 1, 2, structureBoundingBox);
        this.a(world, Block.IRON_DOOR_BLOCK.id, this.c(Block.IRON_DOOR_BLOCK.id, 3) + 8, 4, 2, 2, structureBoundingBox);
        this.a(world, Block.IRON_DOOR_BLOCK.id, this.c(Block.IRON_DOOR_BLOCK.id, 3), 4, 1, 8, structureBoundingBox);
        this.a(world, Block.IRON_DOOR_BLOCK.id, this.c(Block.IRON_DOOR_BLOCK.id, 3) + 8, 4, 2, 8, structureBoundingBox);
        return true;
    }
}
