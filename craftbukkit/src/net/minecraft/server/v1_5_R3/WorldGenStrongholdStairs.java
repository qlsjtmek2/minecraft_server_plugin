// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenStrongholdStairs extends WorldGenStrongholdPiece
{
    private final WorldGenStrongholdDoorType a;
    private final boolean b;
    private final boolean c;
    
    public WorldGenStrongholdStairs(final int n, final Random random, final StructureBoundingBox e, final int f) {
        super(n);
        this.f = f;
        this.a = this.a(random);
        this.e = e;
        this.b = (random.nextInt(2) == 0);
        this.c = (random.nextInt(2) == 0);
    }
    
    public void a(final StructurePiece structurePiece, final List list, final Random random) {
        this.a((WorldGenStrongholdStart)structurePiece, list, random, 1, 1);
        if (this.b) {
            this.b((WorldGenStrongholdStart)structurePiece, list, random, 1, 2);
        }
        if (this.c) {
            this.c((WorldGenStrongholdStart)structurePiece, list, random, 1, 2);
        }
    }
    
    public static WorldGenStrongholdStairs a(final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        final StructureBoundingBox a = StructureBoundingBox.a(n, n2, n3, -1, -1, 0, 5, 5, 7, n4);
        if (!WorldGenStrongholdPiece.a(a) || StructurePiece.a(list, a) != null) {
            return null;
        }
        return new WorldGenStrongholdStairs(n5, random, a, n4);
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        if (this.a(world, structureBoundingBox)) {
            return false;
        }
        this.a(world, structureBoundingBox, 0, 0, 0, 4, 4, 6, true, random, WorldGenStrongholdPieces.e);
        this.a(world, random, structureBoundingBox, this.a, 1, 1, 0);
        this.a(world, random, structureBoundingBox, WorldGenStrongholdDoorType.a, 1, 1, 6);
        this.a(world, structureBoundingBox, random, 0.1f, 1, 2, 1, Block.TORCH.id, 0);
        this.a(world, structureBoundingBox, random, 0.1f, 3, 2, 1, Block.TORCH.id, 0);
        this.a(world, structureBoundingBox, random, 0.1f, 1, 2, 5, Block.TORCH.id, 0);
        this.a(world, structureBoundingBox, random, 0.1f, 3, 2, 5, Block.TORCH.id, 0);
        if (this.b) {
            this.a(world, structureBoundingBox, 0, 1, 2, 0, 3, 4, 0, 0, false);
        }
        if (this.c) {
            this.a(world, structureBoundingBox, 4, 1, 2, 4, 3, 4, 0, 0, false);
        }
        return true;
    }
}
