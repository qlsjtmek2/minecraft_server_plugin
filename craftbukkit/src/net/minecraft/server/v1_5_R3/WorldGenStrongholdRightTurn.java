// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class WorldGenStrongholdRightTurn extends WorldGenStrongholdLeftTurn
{
    public void a(final StructurePiece structurePiece, final List list, final Random random) {
        if (this.f == 2 || this.f == 3) {
            this.c((WorldGenStrongholdStart)structurePiece, list, random, 1, 1);
        }
        else {
            this.b((WorldGenStrongholdStart)structurePiece, list, random, 1, 1);
        }
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        if (this.a(world, structureBoundingBox)) {
            return false;
        }
        this.a(world, structureBoundingBox, 0, 0, 0, 4, 4, 4, true, random, WorldGenStrongholdPieces.e);
        this.a(world, random, structureBoundingBox, this.a, 1, 1, 0);
        if (this.f == 2 || this.f == 3) {
            this.a(world, structureBoundingBox, 4, 1, 1, 4, 3, 3, 0, 0, false);
        }
        else {
            this.a(world, structureBoundingBox, 0, 1, 1, 0, 3, 3, 0, 0, false);
        }
        return true;
    }
}
