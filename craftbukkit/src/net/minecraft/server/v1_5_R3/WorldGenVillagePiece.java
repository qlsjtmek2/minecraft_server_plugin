// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

abstract class WorldGenVillagePiece extends StructurePiece
{
    private int a;
    protected WorldGenVillageStartPiece k;
    
    protected WorldGenVillagePiece(final WorldGenVillageStartPiece k, final int n) {
        super(n);
        this.k = k;
    }
    
    protected StructurePiece a(final WorldGenVillageStartPiece worldGenVillageStartPiece, final List list, final Random random, final int n, final int n2) {
        switch (this.f) {
            case 2: {
                return d(worldGenVillageStartPiece, list, random, this.e.a - 1, this.e.b + n, this.e.c + n2, 1, this.c());
            }
            case 0: {
                return d(worldGenVillageStartPiece, list, random, this.e.a - 1, this.e.b + n, this.e.c + n2, 1, this.c());
            }
            case 1: {
                return d(worldGenVillageStartPiece, list, random, this.e.a + n2, this.e.b + n, this.e.c - 1, 2, this.c());
            }
            case 3: {
                return d(worldGenVillageStartPiece, list, random, this.e.a + n2, this.e.b + n, this.e.c - 1, 2, this.c());
            }
            default: {
                return null;
            }
        }
    }
    
    protected StructurePiece b(final WorldGenVillageStartPiece worldGenVillageStartPiece, final List list, final Random random, final int n, final int n2) {
        switch (this.f) {
            case 2: {
                return d(worldGenVillageStartPiece, list, random, this.e.d + 1, this.e.b + n, this.e.c + n2, 3, this.c());
            }
            case 0: {
                return d(worldGenVillageStartPiece, list, random, this.e.d + 1, this.e.b + n, this.e.c + n2, 3, this.c());
            }
            case 1: {
                return d(worldGenVillageStartPiece, list, random, this.e.a + n2, this.e.b + n, this.e.f + 1, 0, this.c());
            }
            case 3: {
                return d(worldGenVillageStartPiece, list, random, this.e.a + n2, this.e.b + n, this.e.f + 1, 0, this.c());
            }
            default: {
                return null;
            }
        }
    }
    
    protected int b(final World world, final StructureBoundingBox structureBoundingBox) {
        int n = 0;
        int n2 = 0;
        for (int i = this.e.c; i <= this.e.f; ++i) {
            for (int j = this.e.a; j <= this.e.d; ++j) {
                if (structureBoundingBox.b(j, 64, i)) {
                    n += Math.max(world.i(j, i), world.worldProvider.getSeaLevel());
                    ++n2;
                }
            }
        }
        if (n2 == 0) {
            return -1;
        }
        return n / n2;
    }
    
    protected static boolean a(final StructureBoundingBox structureBoundingBox) {
        return structureBoundingBox != null && structureBoundingBox.b > 10;
    }
    
    protected void a(final World world, final StructureBoundingBox structureBoundingBox, final int n, final int n2, final int n3, final int n4) {
        if (this.a >= n4) {
            return;
        }
        for (int i = this.a; i < n4; ++i) {
            final int a = this.a(n + i, n3);
            final int a2 = this.a(n2);
            final int b = this.b(n + i, n3);
            if (!structureBoundingBox.b(a, a2, b)) {
                break;
            }
            ++this.a;
            final EntityVillager entity = new EntityVillager(world, this.b(i));
            entity.setPositionRotation(a + 0.5, a2, b + 0.5, 0.0f, 0.0f);
            world.addEntity(entity);
        }
    }
    
    protected int b(final int n) {
        return 0;
    }
    
    protected int d(final int n, final int n2) {
        if (this.k.b) {
            if (n == Block.LOG.id) {
                return Block.SANDSTONE.id;
            }
            if (n == Block.COBBLESTONE.id) {
                return Block.SANDSTONE.id;
            }
            if (n == Block.WOOD.id) {
                return Block.SANDSTONE.id;
            }
            if (n == Block.WOOD_STAIRS.id) {
                return Block.SANDSTONE_STAIRS.id;
            }
            if (n == Block.COBBLESTONE_STAIRS.id) {
                return Block.SANDSTONE_STAIRS.id;
            }
            if (n == Block.GRAVEL.id) {
                return Block.SANDSTONE.id;
            }
        }
        return n;
    }
    
    protected int e(final int n, final int n2) {
        if (this.k.b) {
            if (n == Block.LOG.id) {
                return 0;
            }
            if (n == Block.COBBLESTONE.id) {
                return 0;
            }
            if (n == Block.WOOD.id) {
                return 2;
            }
        }
        return n2;
    }
    
    protected void a(final World world, final int n, final int n2, final int n3, final int n4, final int n5, final StructureBoundingBox structureBoundingBox) {
        super.a(world, this.d(n, n2), this.e(n, n2), n3, n4, n5, structureBoundingBox);
    }
    
    protected void a(final World world, final StructureBoundingBox structureBoundingBox, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final boolean b) {
        super.a(world, structureBoundingBox, n, n2, n3, n4, n5, n6, this.d(n7, 0), this.e(n7, 0), this.d(n8, 0), this.e(n8, 0), b);
    }
    
    protected void b(final World world, final int n, final int n2, final int n3, final int n4, final int n5, final StructureBoundingBox structureBoundingBox) {
        super.b(world, this.d(n, n2), this.e(n, n2), n3, n4, n5, structureBoundingBox);
    }
}
