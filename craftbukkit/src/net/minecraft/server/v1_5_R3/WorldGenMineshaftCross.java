// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Random;

public class WorldGenMineshaftCross extends StructurePiece
{
    private final int a;
    private final boolean b;
    
    public WorldGenMineshaftCross(final int n, final Random random, final StructureBoundingBox e, final int a) {
        super(n);
        this.a = a;
        this.e = e;
        this.b = (e.c() > 3);
    }
    
    public static StructureBoundingBox a(final List list, final Random random, final int n, final int n2, final int n3, final int n4) {
        final StructureBoundingBox structureBoundingBox = new StructureBoundingBox(n, n2, n3, n, n2 + 2, n3);
        if (random.nextInt(4) == 0) {
            final StructureBoundingBox structureBoundingBox2 = structureBoundingBox;
            structureBoundingBox2.e += 4;
        }
        switch (n4) {
            case 2: {
                structureBoundingBox.a = n - 1;
                structureBoundingBox.d = n + 3;
                structureBoundingBox.c = n3 - 4;
                break;
            }
            case 0: {
                structureBoundingBox.a = n - 1;
                structureBoundingBox.d = n + 3;
                structureBoundingBox.f = n3 + 4;
                break;
            }
            case 1: {
                structureBoundingBox.a = n - 4;
                structureBoundingBox.c = n3 - 1;
                structureBoundingBox.f = n3 + 3;
                break;
            }
            case 3: {
                structureBoundingBox.d = n + 4;
                structureBoundingBox.c = n3 - 1;
                structureBoundingBox.f = n3 + 3;
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
        switch (this.a) {
            case 2: {
                b(structurePiece, list, random, this.e.a + 1, this.e.b, this.e.c - 1, 2, c);
                b(structurePiece, list, random, this.e.a - 1, this.e.b, this.e.c + 1, 1, c);
                b(structurePiece, list, random, this.e.d + 1, this.e.b, this.e.c + 1, 3, c);
                break;
            }
            case 0: {
                b(structurePiece, list, random, this.e.a + 1, this.e.b, this.e.f + 1, 0, c);
                b(structurePiece, list, random, this.e.a - 1, this.e.b, this.e.c + 1, 1, c);
                b(structurePiece, list, random, this.e.d + 1, this.e.b, this.e.c + 1, 3, c);
                break;
            }
            case 1: {
                b(structurePiece, list, random, this.e.a + 1, this.e.b, this.e.c - 1, 2, c);
                b(structurePiece, list, random, this.e.a + 1, this.e.b, this.e.f + 1, 0, c);
                b(structurePiece, list, random, this.e.a - 1, this.e.b, this.e.c + 1, 1, c);
                break;
            }
            case 3: {
                b(structurePiece, list, random, this.e.a + 1, this.e.b, this.e.c - 1, 2, c);
                b(structurePiece, list, random, this.e.a + 1, this.e.b, this.e.f + 1, 0, c);
                b(structurePiece, list, random, this.e.d + 1, this.e.b, this.e.c + 1, 3, c);
                break;
            }
        }
        if (this.b) {
            if (random.nextBoolean()) {
                b(structurePiece, list, random, this.e.a + 1, this.e.b + 3 + 1, this.e.c - 1, 2, c);
            }
            if (random.nextBoolean()) {
                b(structurePiece, list, random, this.e.a - 1, this.e.b + 3 + 1, this.e.c + 1, 1, c);
            }
            if (random.nextBoolean()) {
                b(structurePiece, list, random, this.e.d + 1, this.e.b + 3 + 1, this.e.c + 1, 3, c);
            }
            if (random.nextBoolean()) {
                b(structurePiece, list, random, this.e.a + 1, this.e.b + 3 + 1, this.e.f + 1, 0, c);
            }
        }
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        if (this.a(world, structureBoundingBox)) {
            return false;
        }
        if (this.b) {
            this.a(world, structureBoundingBox, this.e.a + 1, this.e.b, this.e.c, this.e.d - 1, this.e.b + 3 - 1, this.e.f, 0, 0, false);
            this.a(world, structureBoundingBox, this.e.a, this.e.b, this.e.c + 1, this.e.d, this.e.b + 3 - 1, this.e.f - 1, 0, 0, false);
            this.a(world, structureBoundingBox, this.e.a + 1, this.e.e - 2, this.e.c, this.e.d - 1, this.e.e, this.e.f, 0, 0, false);
            this.a(world, structureBoundingBox, this.e.a, this.e.e - 2, this.e.c + 1, this.e.d, this.e.e, this.e.f - 1, 0, 0, false);
            this.a(world, structureBoundingBox, this.e.a + 1, this.e.b + 3, this.e.c + 1, this.e.d - 1, this.e.b + 3, this.e.f - 1, 0, 0, false);
        }
        else {
            this.a(world, structureBoundingBox, this.e.a + 1, this.e.b, this.e.c, this.e.d - 1, this.e.e, this.e.f, 0, 0, false);
            this.a(world, structureBoundingBox, this.e.a, this.e.b, this.e.c + 1, this.e.d, this.e.e, this.e.f - 1, 0, 0, false);
        }
        this.a(world, structureBoundingBox, this.e.a + 1, this.e.b, this.e.c + 1, this.e.a + 1, this.e.e, this.e.c + 1, Block.WOOD.id, 0, false);
        this.a(world, structureBoundingBox, this.e.a + 1, this.e.b, this.e.f - 1, this.e.a + 1, this.e.e, this.e.f - 1, Block.WOOD.id, 0, false);
        this.a(world, structureBoundingBox, this.e.d - 1, this.e.b, this.e.c + 1, this.e.d - 1, this.e.e, this.e.c + 1, Block.WOOD.id, 0, false);
        this.a(world, structureBoundingBox, this.e.d - 1, this.e.b, this.e.f - 1, this.e.d - 1, this.e.e, this.e.f - 1, Block.WOOD.id, 0, false);
        for (int i = this.e.a; i <= this.e.d; ++i) {
            for (int j = this.e.c; j <= this.e.f; ++j) {
                if (this.a(world, i, this.e.b - 1, j, structureBoundingBox) == 0) {
                    this.a(world, Block.WOOD.id, 0, i, this.e.b - 1, j, structureBoundingBox);
                }
            }
        }
        return true;
    }
}
