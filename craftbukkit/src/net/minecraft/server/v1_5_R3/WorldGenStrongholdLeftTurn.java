// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenStrongholdLeftTurn extends WorldGenStrongholdPiece
{
    protected final WorldGenStrongholdDoorType a;
    
    public WorldGenStrongholdLeftTurn(final int n, final Random random, final StructureBoundingBox e, final int f) {
        super(n);
        this.f = f;
        this.a = this.a(random);
        this.e = e;
    }
    
    public void a(final StructurePiece structurePiece, final List list, final Random random) {
        if (this.f == 2 || this.f == 3) {
            this.b((WorldGenStrongholdStart)structurePiece, list, random, 1, 1);
        }
        else {
            this.c((WorldGenStrongholdStart)structurePiece, list, random, 1, 1);
        }
    }
    
    public static WorldGenStrongholdLeftTurn a(final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        final StructureBoundingBox a = StructureBoundingBox.a(n, n2, n3, -1, -1, 0, 5, 5, 5, n4);
        if (!WorldGenStrongholdPiece.a(a) || StructurePiece.a(list, a) != null) {
            return null;
        }
        return new WorldGenStrongholdLeftTurn(n5, random, a, n4);
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        if (this.a(world, structureBoundingBox)) {
            return false;
        }
        this.a(world, structureBoundingBox, 0, 0, 0, 4, 4, 4, true, random, WorldGenStrongholdPieces.e);
        this.a(world, random, structureBoundingBox, this.a, 1, 1, 0);
        if (this.f == 2 || this.f == 3) {
            this.a(world, structureBoundingBox, 0, 1, 1, 0, 3, 3, 0, 0, false);
        }
        else {
            this.a(world, structureBoundingBox, 4, 1, 1, 4, 3, 3, 0, 0, false);
        }
        return true;
    }
}
