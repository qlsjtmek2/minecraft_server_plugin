// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenStrongholdCrossing extends WorldGenStrongholdPiece
{
    protected final WorldGenStrongholdDoorType a;
    private boolean b;
    private boolean c;
    private boolean d;
    private boolean h;
    
    public WorldGenStrongholdCrossing(final int n, final Random random, final StructureBoundingBox e, final int f) {
        super(n);
        this.f = f;
        this.a = this.a(random);
        this.e = e;
        this.b = random.nextBoolean();
        this.c = random.nextBoolean();
        this.d = random.nextBoolean();
        this.h = (random.nextInt(3) > 0);
    }
    
    public void a(final StructurePiece structurePiece, final List list, final Random random) {
        int n = 3;
        int n2 = 5;
        if (this.f == 1 || this.f == 2) {
            n = 8 - n;
            n2 = 8 - n2;
        }
        this.a((WorldGenStrongholdStart)structurePiece, list, random, 5, 1);
        if (this.b) {
            this.b((WorldGenStrongholdStart)structurePiece, list, random, n, 1);
        }
        if (this.c) {
            this.b((WorldGenStrongholdStart)structurePiece, list, random, n2, 7);
        }
        if (this.d) {
            this.c((WorldGenStrongholdStart)structurePiece, list, random, n, 1);
        }
        if (this.h) {
            this.c((WorldGenStrongholdStart)structurePiece, list, random, n2, 7);
        }
    }
    
    public static WorldGenStrongholdCrossing a(final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        final StructureBoundingBox a = StructureBoundingBox.a(n, n2, n3, -4, -3, 0, 10, 9, 11, n4);
        if (!WorldGenStrongholdPiece.a(a) || StructurePiece.a(list, a) != null) {
            return null;
        }
        return new WorldGenStrongholdCrossing(n5, random, a, n4);
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        if (this.a(world, structureBoundingBox)) {
            return false;
        }
        this.a(world, structureBoundingBox, 0, 0, 0, 9, 8, 10, true, random, WorldGenStrongholdPieces.e);
        this.a(world, random, structureBoundingBox, this.a, 4, 3, 0);
        if (this.b) {
            this.a(world, structureBoundingBox, 0, 3, 1, 0, 5, 3, 0, 0, false);
        }
        if (this.d) {
            this.a(world, structureBoundingBox, 9, 3, 1, 9, 5, 3, 0, 0, false);
        }
        if (this.c) {
            this.a(world, structureBoundingBox, 0, 5, 7, 0, 7, 9, 0, 0, false);
        }
        if (this.h) {
            this.a(world, structureBoundingBox, 9, 5, 7, 9, 7, 9, 0, 0, false);
        }
        this.a(world, structureBoundingBox, 5, 1, 10, 7, 3, 10, 0, 0, false);
        this.a(world, structureBoundingBox, 1, 2, 1, 8, 2, 6, false, random, WorldGenStrongholdPieces.e);
        this.a(world, structureBoundingBox, 4, 1, 5, 4, 4, 9, false, random, WorldGenStrongholdPieces.e);
        this.a(world, structureBoundingBox, 8, 1, 5, 8, 4, 9, false, random, WorldGenStrongholdPieces.e);
        this.a(world, structureBoundingBox, 1, 4, 7, 3, 4, 9, false, random, WorldGenStrongholdPieces.e);
        this.a(world, structureBoundingBox, 1, 3, 5, 3, 3, 6, false, random, WorldGenStrongholdPieces.e);
        this.a(world, structureBoundingBox, 1, 3, 4, 3, 3, 4, Block.STEP.id, Block.STEP.id, false);
        this.a(world, structureBoundingBox, 1, 4, 6, 3, 4, 6, Block.STEP.id, Block.STEP.id, false);
        this.a(world, structureBoundingBox, 5, 1, 7, 7, 1, 8, false, random, WorldGenStrongholdPieces.e);
        this.a(world, structureBoundingBox, 5, 1, 9, 7, 1, 9, Block.STEP.id, Block.STEP.id, false);
        this.a(world, structureBoundingBox, 5, 2, 7, 7, 2, 7, Block.STEP.id, Block.STEP.id, false);
        this.a(world, structureBoundingBox, 4, 5, 7, 4, 5, 9, Block.STEP.id, Block.STEP.id, false);
        this.a(world, structureBoundingBox, 8, 5, 7, 8, 5, 9, Block.STEP.id, Block.STEP.id, false);
        this.a(world, structureBoundingBox, 5, 5, 7, 7, 5, 9, Block.DOUBLE_STEP.id, Block.DOUBLE_STEP.id, false);
        this.a(world, Block.TORCH.id, 0, 6, 5, 6, structureBoundingBox);
        return true;
    }
}
