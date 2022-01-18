// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import java.util.Random;

public class WorldGenVillageFarm2 extends WorldGenVillagePiece
{
    private int a;
    private int b;
    private int c;
    private int d;
    private int h;
    
    public WorldGenVillageFarm2(final WorldGenVillageStartPiece worldGenVillageStartPiece, final int n, final Random random, final StructureBoundingBox e, final int f) {
        super(worldGenVillageStartPiece, n);
        this.a = -1;
        this.f = f;
        this.e = e;
        this.b = this.a(random);
        this.c = this.a(random);
        this.d = this.a(random);
        this.h = this.a(random);
    }
    
    private int a(final Random random) {
        switch (random.nextInt(5)) {
            default: {
                return Block.CROPS.id;
            }
            case 0: {
                return Block.CARROTS.id;
            }
            case 1: {
                return Block.POTATOES.id;
            }
        }
    }
    
    public static WorldGenVillageFarm2 a(final WorldGenVillageStartPiece worldGenVillageStartPiece, final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        final StructureBoundingBox a = StructureBoundingBox.a(n, n2, n3, 0, 0, 0, 13, 4, 9, n4);
        if (!WorldGenVillagePiece.a(a) || StructurePiece.a(list, a) != null) {
            return null;
        }
        return new WorldGenVillageFarm2(worldGenVillageStartPiece, n5, random, a, n4);
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        if (this.a < 0) {
            this.a = this.b(world, structureBoundingBox);
            if (this.a < 0) {
                return true;
            }
            this.e.a(0, this.a - this.e.e + 4 - 1, 0);
        }
        this.a(world, structureBoundingBox, 0, 1, 0, 12, 4, 8, 0, 0, false);
        this.a(world, structureBoundingBox, 1, 0, 1, 2, 0, 7, Block.SOIL.id, Block.SOIL.id, false);
        this.a(world, structureBoundingBox, 4, 0, 1, 5, 0, 7, Block.SOIL.id, Block.SOIL.id, false);
        this.a(world, structureBoundingBox, 7, 0, 1, 8, 0, 7, Block.SOIL.id, Block.SOIL.id, false);
        this.a(world, structureBoundingBox, 10, 0, 1, 11, 0, 7, Block.SOIL.id, Block.SOIL.id, false);
        this.a(world, structureBoundingBox, 0, 0, 0, 0, 0, 8, Block.LOG.id, Block.LOG.id, false);
        this.a(world, structureBoundingBox, 6, 0, 0, 6, 0, 8, Block.LOG.id, Block.LOG.id, false);
        this.a(world, structureBoundingBox, 12, 0, 0, 12, 0, 8, Block.LOG.id, Block.LOG.id, false);
        this.a(world, structureBoundingBox, 1, 0, 0, 11, 0, 0, Block.LOG.id, Block.LOG.id, false);
        this.a(world, structureBoundingBox, 1, 0, 8, 11, 0, 8, Block.LOG.id, Block.LOG.id, false);
        this.a(world, structureBoundingBox, 3, 0, 1, 3, 0, 7, Block.WATER.id, Block.WATER.id, false);
        this.a(world, structureBoundingBox, 9, 0, 1, 9, 0, 7, Block.WATER.id, Block.WATER.id, false);
        for (int i = 1; i <= 7; ++i) {
            this.a(world, this.b, MathHelper.nextInt(random, 2, 7), 1, 1, i, structureBoundingBox);
            this.a(world, this.b, MathHelper.nextInt(random, 2, 7), 2, 1, i, structureBoundingBox);
            this.a(world, this.c, MathHelper.nextInt(random, 2, 7), 4, 1, i, structureBoundingBox);
            this.a(world, this.c, MathHelper.nextInt(random, 2, 7), 5, 1, i, structureBoundingBox);
            this.a(world, this.d, MathHelper.nextInt(random, 2, 7), 7, 1, i, structureBoundingBox);
            this.a(world, this.d, MathHelper.nextInt(random, 2, 7), 8, 1, i, structureBoundingBox);
            this.a(world, this.h, MathHelper.nextInt(random, 2, 7), 10, 1, i, structureBoundingBox);
            this.a(world, this.h, MathHelper.nextInt(random, 2, 7), 11, 1, i, structureBoundingBox);
        }
        for (int j = 0; j < 9; ++j) {
            for (int k = 0; k < 13; ++k) {
                this.b(world, k, 4, j, structureBoundingBox);
                this.b(world, Block.DIRT.id, 0, k, -1, j, structureBoundingBox);
            }
        }
        return true;
    }
}
