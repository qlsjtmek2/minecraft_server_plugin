// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenVillageRoad extends WorldGenVillageRoadPiece
{
    private int a;
    
    public WorldGenVillageRoad(final WorldGenVillageStartPiece worldGenVillageStartPiece, final int n, final Random random, final StructureBoundingBox e, final int f) {
        super(worldGenVillageStartPiece, n);
        this.f = f;
        this.e = e;
        this.a = Math.max(e.b(), e.d());
    }
    
    public void a(final StructurePiece structurePiece, final List list, final Random random) {
        boolean b = false;
        for (int i = random.nextInt(5); i < this.a - 8; i += 2 + random.nextInt(5)) {
            final StructurePiece a = this.a((WorldGenVillageStartPiece)structurePiece, list, random, 0, i);
            if (a != null) {
                i += Math.max(a.e.b(), a.e.d());
                b = true;
            }
        }
        for (int j = random.nextInt(5); j < this.a - 8; j += 2 + random.nextInt(5)) {
            final StructurePiece b2 = this.b((WorldGenVillageStartPiece)structurePiece, list, random, 0, j);
            if (b2 != null) {
                j += Math.max(b2.e.b(), b2.e.d());
                b = true;
            }
        }
        if (b && random.nextInt(3) > 0) {
            switch (this.f) {
                case 2: {
                    e((WorldGenVillageStartPiece)structurePiece, list, random, this.e.a - 1, this.e.b, this.e.c, 1, this.c());
                    break;
                }
                case 0: {
                    e((WorldGenVillageStartPiece)structurePiece, list, random, this.e.a - 1, this.e.b, this.e.f - 2, 1, this.c());
                    break;
                }
                case 3: {
                    e((WorldGenVillageStartPiece)structurePiece, list, random, this.e.d - 2, this.e.b, this.e.c - 1, 2, this.c());
                    break;
                }
                case 1: {
                    e((WorldGenVillageStartPiece)structurePiece, list, random, this.e.a, this.e.b, this.e.c - 1, 2, this.c());
                    break;
                }
            }
        }
        if (b && random.nextInt(3) > 0) {
            switch (this.f) {
                case 2: {
                    e((WorldGenVillageStartPiece)structurePiece, list, random, this.e.d + 1, this.e.b, this.e.c, 3, this.c());
                    break;
                }
                case 0: {
                    e((WorldGenVillageStartPiece)structurePiece, list, random, this.e.d + 1, this.e.b, this.e.f - 2, 3, this.c());
                    break;
                }
                case 3: {
                    e((WorldGenVillageStartPiece)structurePiece, list, random, this.e.d - 2, this.e.b, this.e.f + 1, 0, this.c());
                    break;
                }
                case 1: {
                    e((WorldGenVillageStartPiece)structurePiece, list, random, this.e.a, this.e.b, this.e.f + 1, 0, this.c());
                    break;
                }
            }
        }
    }
    
    public static StructureBoundingBox a(final WorldGenVillageStartPiece worldGenVillageStartPiece, final List list, final Random random, final int n, final int n2, final int n3, final int n4) {
        for (int i = 7 * MathHelper.nextInt(random, 3, 5); i >= 7; i -= 7) {
            final StructureBoundingBox a = StructureBoundingBox.a(n, n2, n3, 0, 0, 0, 3, 3, i, n4);
            if (StructurePiece.a(list, a) == null) {
                return a;
            }
        }
        return null;
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        final int d = this.d(Block.GRAVEL.id, 0);
        for (int i = this.e.a; i <= this.e.d; ++i) {
            for (int j = this.e.c; j <= this.e.f; ++j) {
                if (structureBoundingBox.b(i, 64, j)) {
                    world.setTypeIdAndData(i, world.i(i, j) - 1, j, d, 0, 2);
                }
            }
        }
        return true;
    }
}
