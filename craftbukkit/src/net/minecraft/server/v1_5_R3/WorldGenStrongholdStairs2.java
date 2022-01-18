// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenStrongholdStairs2 extends WorldGenStrongholdPiece
{
    private final boolean a;
    private final WorldGenStrongholdDoorType b;
    
    public WorldGenStrongholdStairs2(final int n, final Random random, final int n2, final int n3) {
        super(n);
        this.a = true;
        this.f = random.nextInt(4);
        this.b = WorldGenStrongholdDoorType.a;
        switch (this.f) {
            case 0:
            case 2: {
                this.e = new StructureBoundingBox(n2, 64, n3, n2 + 5 - 1, 74, n3 + 5 - 1);
                break;
            }
            default: {
                this.e = new StructureBoundingBox(n2, 64, n3, n2 + 5 - 1, 74, n3 + 5 - 1);
                break;
            }
        }
    }
    
    public WorldGenStrongholdStairs2(final int n, final Random random, final StructureBoundingBox e, final int f) {
        super(n);
        this.a = false;
        this.f = f;
        this.b = this.a(random);
        this.e = e;
    }
    
    public void a(final StructurePiece structurePiece, final List list, final Random random) {
        if (this.a) {
            WorldGenStrongholdPieces.d = WorldGenStrongholdCrossing.class;
        }
        this.a((WorldGenStrongholdStart)structurePiece, list, random, 1, 1);
    }
    
    public static WorldGenStrongholdStairs2 a(final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        final StructureBoundingBox a = StructureBoundingBox.a(n, n2, n3, -1, -7, 0, 5, 11, 5, n4);
        if (!WorldGenStrongholdPiece.a(a) || StructurePiece.a(list, a) != null) {
            return null;
        }
        return new WorldGenStrongholdStairs2(n5, random, a, n4);
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        if (this.a(world, structureBoundingBox)) {
            return false;
        }
        this.a(world, structureBoundingBox, 0, 0, 0, 4, 10, 4, true, random, WorldGenStrongholdPieces.e);
        this.a(world, random, structureBoundingBox, this.b, 1, 7, 0);
        this.a(world, random, structureBoundingBox, WorldGenStrongholdDoorType.a, 1, 1, 4);
        this.a(world, Block.SMOOTH_BRICK.id, 0, 2, 6, 1, structureBoundingBox);
        this.a(world, Block.SMOOTH_BRICK.id, 0, 1, 5, 1, structureBoundingBox);
        this.a(world, Block.STEP.id, 0, 1, 6, 1, structureBoundingBox);
        this.a(world, Block.SMOOTH_BRICK.id, 0, 1, 5, 2, structureBoundingBox);
        this.a(world, Block.SMOOTH_BRICK.id, 0, 1, 4, 3, structureBoundingBox);
        this.a(world, Block.STEP.id, 0, 1, 5, 3, structureBoundingBox);
        this.a(world, Block.SMOOTH_BRICK.id, 0, 2, 4, 3, structureBoundingBox);
        this.a(world, Block.SMOOTH_BRICK.id, 0, 3, 3, 3, structureBoundingBox);
        this.a(world, Block.STEP.id, 0, 3, 4, 3, structureBoundingBox);
        this.a(world, Block.SMOOTH_BRICK.id, 0, 3, 3, 2, structureBoundingBox);
        this.a(world, Block.SMOOTH_BRICK.id, 0, 3, 2, 1, structureBoundingBox);
        this.a(world, Block.STEP.id, 0, 3, 3, 1, structureBoundingBox);
        this.a(world, Block.SMOOTH_BRICK.id, 0, 2, 2, 1, structureBoundingBox);
        this.a(world, Block.SMOOTH_BRICK.id, 0, 1, 1, 1, structureBoundingBox);
        this.a(world, Block.STEP.id, 0, 1, 2, 1, structureBoundingBox);
        this.a(world, Block.SMOOTH_BRICK.id, 0, 1, 1, 2, structureBoundingBox);
        this.a(world, Block.STEP.id, 0, 1, 1, 3, structureBoundingBox);
        return true;
    }
}
