// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

abstract class WorldGenScatteredPiece extends StructurePiece
{
    protected final int a;
    protected final int b;
    protected final int c;
    protected int d;
    
    protected WorldGenScatteredPiece(final Random random, final int n, final int n2, final int n3, final int a, final int b, final int c) {
        super(0);
        this.d = -1;
        this.a = a;
        this.b = b;
        this.c = c;
        switch (this.f = random.nextInt(4)) {
            case 0:
            case 2: {
                this.e = new StructureBoundingBox(n, n2, n3, n + a - 1, n2 + b - 1, n3 + c - 1);
                break;
            }
            default: {
                this.e = new StructureBoundingBox(n, n2, n3, n + c - 1, n2 + b - 1, n3 + a - 1);
                break;
            }
        }
    }
    
    protected boolean a(final World world, final StructureBoundingBox structureBoundingBox, final int n) {
        if (this.d >= 0) {
            return true;
        }
        int n2 = 0;
        int n3 = 0;
        for (int i = this.e.c; i <= this.e.f; ++i) {
            for (int j = this.e.a; j <= this.e.d; ++j) {
                if (structureBoundingBox.b(j, 64, i)) {
                    n2 += Math.max(world.i(j, i), world.worldProvider.getSeaLevel());
                    ++n3;
                }
            }
        }
        if (n3 == 0) {
            return false;
        }
        this.d = n2 / n3;
        this.e.a(0, this.d - this.e.b + n, 0);
        return true;
    }
}
