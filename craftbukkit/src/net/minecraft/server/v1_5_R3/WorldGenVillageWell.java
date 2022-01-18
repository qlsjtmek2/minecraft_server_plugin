// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenVillageWell extends WorldGenVillagePiece
{
    private final boolean a;
    private int b;
    
    public WorldGenVillageWell(final WorldGenVillageStartPiece worldGenVillageStartPiece, final int n, final Random random, final int n2, final int n3) {
        super(worldGenVillageStartPiece, n);
        this.b = -1;
        this.a = true;
        switch (this.f = random.nextInt(4)) {
            case 0:
            case 2: {
                this.e = new StructureBoundingBox(n2, 64, n3, n2 + 6 - 1, 78, n3 + 6 - 1);
                break;
            }
            default: {
                this.e = new StructureBoundingBox(n2, 64, n3, n2 + 6 - 1, 78, n3 + 6 - 1);
                break;
            }
        }
    }
    
    public void a(final StructurePiece structurePiece, final List list, final Random random) {
        e((WorldGenVillageStartPiece)structurePiece, list, random, this.e.a - 1, this.e.e - 4, this.e.c + 1, 1, this.c());
        e((WorldGenVillageStartPiece)structurePiece, list, random, this.e.d + 1, this.e.e - 4, this.e.c + 1, 3, this.c());
        e((WorldGenVillageStartPiece)structurePiece, list, random, this.e.a + 1, this.e.e - 4, this.e.c - 1, 2, this.c());
        e((WorldGenVillageStartPiece)structurePiece, list, random, this.e.a + 1, this.e.e - 4, this.e.f + 1, 0, this.c());
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        if (this.b < 0) {
            this.b = this.b(world, structureBoundingBox);
            if (this.b < 0) {
                return true;
            }
            this.e.a(0, this.b - this.e.e + 3, 0);
        }
        this.a(world, structureBoundingBox, 1, 0, 1, 4, 12, 4, Block.COBBLESTONE.id, Block.WATER.id, false);
        this.a(world, 0, 0, 2, 12, 2, structureBoundingBox);
        this.a(world, 0, 0, 3, 12, 2, structureBoundingBox);
        this.a(world, 0, 0, 2, 12, 3, structureBoundingBox);
        this.a(world, 0, 0, 3, 12, 3, structureBoundingBox);
        this.a(world, Block.FENCE.id, 0, 1, 13, 1, structureBoundingBox);
        this.a(world, Block.FENCE.id, 0, 1, 14, 1, structureBoundingBox);
        this.a(world, Block.FENCE.id, 0, 4, 13, 1, structureBoundingBox);
        this.a(world, Block.FENCE.id, 0, 4, 14, 1, structureBoundingBox);
        this.a(world, Block.FENCE.id, 0, 1, 13, 4, structureBoundingBox);
        this.a(world, Block.FENCE.id, 0, 1, 14, 4, structureBoundingBox);
        this.a(world, Block.FENCE.id, 0, 4, 13, 4, structureBoundingBox);
        this.a(world, Block.FENCE.id, 0, 4, 14, 4, structureBoundingBox);
        this.a(world, structureBoundingBox, 1, 15, 1, 4, 15, 4, Block.COBBLESTONE.id, Block.COBBLESTONE.id, false);
        for (int i = 0; i <= 5; ++i) {
            for (int j = 0; j <= 5; ++j) {
                if (j == 0 || j == 5 || i == 0 || i == 5) {
                    this.a(world, Block.GRAVEL.id, 0, j, 11, i, structureBoundingBox);
                    this.b(world, j, 12, i, structureBoundingBox);
                }
            }
        }
        return true;
    }
}
