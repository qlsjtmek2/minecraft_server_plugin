// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;
import java.util.Iterator;
import java.util.List;

abstract class WorldGenNetherPiece extends StructurePiece
{
    protected WorldGenNetherPiece(final int n) {
        super(n);
    }
    
    private int a(final List list) {
        boolean b = false;
        int n = 0;
        for (final WorldGenNetherPieceWeight worldGenNetherPieceWeight : list) {
            if (worldGenNetherPieceWeight.d > 0 && worldGenNetherPieceWeight.c < worldGenNetherPieceWeight.d) {
                b = true;
            }
            n += worldGenNetherPieceWeight.b;
        }
        return b ? n : -1;
    }
    
    private WorldGenNetherPiece a(final WorldGenNetherPiece15 worldGenNetherPiece15, final List list, final List list2, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        final int a = this.a(list);
        final boolean b = a > 0 && n5 <= 30;
        int n6 = 0;
        while (n6 < 5 && b) {
            ++n6;
            int nextInt = random.nextInt(a);
            for (final WorldGenNetherPieceWeight a2 : list) {
                nextInt -= a2.b;
                if (nextInt < 0) {
                    if (!a2.a(n5)) {
                        break;
                    }
                    if (a2 == worldGenNetherPiece15.a && !a2.e) {
                        break;
                    }
                    final WorldGenNetherPiece a3 = b(a2, list2, random, n, n2, n3, n4, n5);
                    if (a3 != null) {
                        final WorldGenNetherPieceWeight worldGenNetherPieceWeight = a2;
                        ++worldGenNetherPieceWeight.c;
                        worldGenNetherPiece15.a = a2;
                        if (!a2.a()) {
                            list.remove(a2);
                        }
                        return a3;
                    }
                    continue;
                }
            }
        }
        return WorldGenNetherPiece2.a(list2, random, n, n2, n3, n4, n5);
    }
    
    private StructurePiece a(final WorldGenNetherPiece15 worldGenNetherPiece15, final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5, final boolean b) {
        if (Math.abs(n - worldGenNetherPiece15.b().a) > 112 || Math.abs(n3 - worldGenNetherPiece15.b().c) > 112) {
            return WorldGenNetherPiece2.a(list, random, n, n2, n3, n4, n5);
        }
        List list2 = worldGenNetherPiece15.b;
        if (b) {
            list2 = worldGenNetherPiece15.c;
        }
        final WorldGenNetherPiece a = this.a(worldGenNetherPiece15, list2, list, random, n, n2, n3, n4, n5 + 1);
        if (a != null) {
            list.add(a);
            worldGenNetherPiece15.d.add(a);
        }
        return a;
    }
    
    protected StructurePiece a(final WorldGenNetherPiece15 worldGenNetherPiece15, final List list, final Random random, final int n, final int n2, final boolean b) {
        switch (this.f) {
            case 2: {
                return this.a(worldGenNetherPiece15, list, random, this.e.a + n, this.e.b + n2, this.e.c - 1, this.f, this.c(), b);
            }
            case 0: {
                return this.a(worldGenNetherPiece15, list, random, this.e.a + n, this.e.b + n2, this.e.f + 1, this.f, this.c(), b);
            }
            case 1: {
                return this.a(worldGenNetherPiece15, list, random, this.e.a - 1, this.e.b + n2, this.e.c + n, this.f, this.c(), b);
            }
            case 3: {
                return this.a(worldGenNetherPiece15, list, random, this.e.d + 1, this.e.b + n2, this.e.c + n, this.f, this.c(), b);
            }
            default: {
                return null;
            }
        }
    }
    
    protected StructurePiece b(final WorldGenNetherPiece15 worldGenNetherPiece15, final List list, final Random random, final int n, final int n2, final boolean b) {
        switch (this.f) {
            case 2: {
                return this.a(worldGenNetherPiece15, list, random, this.e.a - 1, this.e.b + n, this.e.c + n2, 1, this.c(), b);
            }
            case 0: {
                return this.a(worldGenNetherPiece15, list, random, this.e.a - 1, this.e.b + n, this.e.c + n2, 1, this.c(), b);
            }
            case 1: {
                return this.a(worldGenNetherPiece15, list, random, this.e.a + n2, this.e.b + n, this.e.c - 1, 2, this.c(), b);
            }
            case 3: {
                return this.a(worldGenNetherPiece15, list, random, this.e.a + n2, this.e.b + n, this.e.c - 1, 2, this.c(), b);
            }
            default: {
                return null;
            }
        }
    }
    
    protected StructurePiece c(final WorldGenNetherPiece15 worldGenNetherPiece15, final List list, final Random random, final int n, final int n2, final boolean b) {
        switch (this.f) {
            case 2: {
                return this.a(worldGenNetherPiece15, list, random, this.e.d + 1, this.e.b + n, this.e.c + n2, 3, this.c(), b);
            }
            case 0: {
                return this.a(worldGenNetherPiece15, list, random, this.e.d + 1, this.e.b + n, this.e.c + n2, 3, this.c(), b);
            }
            case 1: {
                return this.a(worldGenNetherPiece15, list, random, this.e.a + n2, this.e.b + n, this.e.f + 1, 0, this.c(), b);
            }
            case 3: {
                return this.a(worldGenNetherPiece15, list, random, this.e.a + n2, this.e.b + n, this.e.f + 1, 0, this.c(), b);
            }
            default: {
                return null;
            }
        }
    }
    
    protected static boolean a(final StructureBoundingBox structureBoundingBox) {
        return structureBoundingBox != null && structureBoundingBox.b > 10;
    }
}
