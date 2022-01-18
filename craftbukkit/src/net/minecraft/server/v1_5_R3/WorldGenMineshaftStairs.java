// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Random;

public class WorldGenMineshaftStairs extends StructurePiece
{
    public WorldGenMineshaftStairs(final int n, final Random random, final StructureBoundingBox e, final int f) {
        super(n);
        this.f = f;
        this.e = e;
    }
    
    public static StructureBoundingBox a(final List list, final Random random, final int n, final int n2, final int n3, final int n4) {
        final StructureBoundingBox structureBoundingBox = new StructureBoundingBox(n, n2 - 5, n3, n, n2 + 2, n3);
        switch (n4) {
            case 2: {
                structureBoundingBox.d = n + 2;
                structureBoundingBox.c = n3 - 8;
                break;
            }
            case 0: {
                structureBoundingBox.d = n + 2;
                structureBoundingBox.f = n3 + 8;
                break;
            }
            case 1: {
                structureBoundingBox.a = n - 8;
                structureBoundingBox.f = n3 + 2;
                break;
            }
            case 3: {
                structureBoundingBox.d = n + 8;
                structureBoundingBox.f = n3 + 2;
                break;
            }
        }
        if (StructurePiece.a(list, structureBoundingBox) != null) {
            return null;
        }
        return structureBoundingBox;
    }
    
    public void a(final StructurePiece structurePiece, final List list, final Random random) {
        final int c = this.c();
        switch (this.f) {
            case 2: {
                b(structurePiece, list, random, this.e.a, this.e.b, this.e.c - 1, 2, c);
                break;
            }
            case 0: {
                b(structurePiece, list, random, this.e.a, this.e.b, this.e.f + 1, 0, c);
                break;
            }
            case 1: {
                b(structurePiece, list, random, this.e.a - 1, this.e.b, this.e.c, 1, c);
                break;
            }
            case 3: {
                b(structurePiece, list, random, this.e.d + 1, this.e.b, this.e.c, 3, c);
                break;
            }
        }
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        if (this.a(world, structureBoundingBox)) {
            return false;
        }
        this.a(world, structureBoundingBox, 0, 5, 0, 2, 7, 1, 0, 0, false);
        this.a(world, structureBoundingBox, 0, 0, 7, 2, 2, 8, 0, 0, false);
        for (int i = 0; i < 5; ++i) {
            this.a(world, structureBoundingBox, 0, 5 - i - ((i < 4) ? 1 : 0), 2 + i, 2, 7 - i, 2 + i, 0, 0, false);
        }
        return true;
    }
}
