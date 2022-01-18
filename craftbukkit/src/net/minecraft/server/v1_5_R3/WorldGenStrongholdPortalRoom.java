// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenStrongholdPortalRoom extends WorldGenStrongholdPiece
{
    private boolean a;
    
    public WorldGenStrongholdPortalRoom(final int n, final Random random, final StructureBoundingBox e, final int f) {
        super(n);
        this.f = f;
        this.e = e;
    }
    
    public void a(final StructurePiece structurePiece, final List list, final Random random) {
        if (structurePiece != null) {
            ((WorldGenStrongholdStart)structurePiece).b = this;
        }
    }
    
    public static WorldGenStrongholdPortalRoom a(final List list, final Random random, final int n, final int n2, final int n3, final int n4, final int n5) {
        final StructureBoundingBox a = StructureBoundingBox.a(n, n2, n3, -4, -1, 0, 11, 8, 16, n4);
        if (!WorldGenStrongholdPiece.a(a) || StructurePiece.a(list, a) != null) {
            return null;
        }
        return new WorldGenStrongholdPortalRoom(n5, random, a, n4);
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        this.a(world, structureBoundingBox, 0, 0, 0, 10, 7, 15, false, random, WorldGenStrongholdPieces.e);
        this.a(world, random, structureBoundingBox, WorldGenStrongholdDoorType.c, 4, 1, 0);
        final int n = 6;
        this.a(world, structureBoundingBox, 1, n, 1, 1, n, 14, false, random, WorldGenStrongholdPieces.e);
        this.a(world, structureBoundingBox, 9, n, 1, 9, n, 14, false, random, WorldGenStrongholdPieces.e);
        this.a(world, structureBoundingBox, 2, n, 1, 8, n, 2, false, random, WorldGenStrongholdPieces.e);
        this.a(world, structureBoundingBox, 2, n, 14, 8, n, 14, false, random, WorldGenStrongholdPieces.e);
        this.a(world, structureBoundingBox, 1, 1, 1, 2, 1, 4, false, random, WorldGenStrongholdPieces.e);
        this.a(world, structureBoundingBox, 8, 1, 1, 9, 1, 4, false, random, WorldGenStrongholdPieces.e);
        this.a(world, structureBoundingBox, 1, 1, 1, 1, 1, 3, Block.LAVA.id, Block.LAVA.id, false);
        this.a(world, structureBoundingBox, 9, 1, 1, 9, 1, 3, Block.LAVA.id, Block.LAVA.id, false);
        this.a(world, structureBoundingBox, 3, 1, 8, 7, 1, 12, false, random, WorldGenStrongholdPieces.e);
        this.a(world, structureBoundingBox, 4, 1, 9, 6, 1, 11, Block.LAVA.id, Block.LAVA.id, false);
        for (int i = 3; i < 14; i += 2) {
            this.a(world, structureBoundingBox, 0, 3, i, 0, 4, i, Block.IRON_FENCE.id, Block.IRON_FENCE.id, false);
            this.a(world, structureBoundingBox, 10, 3, i, 10, 4, i, Block.IRON_FENCE.id, Block.IRON_FENCE.id, false);
        }
        for (int j = 2; j < 9; j += 2) {
            this.a(world, structureBoundingBox, j, 3, 15, j, 4, 15, Block.IRON_FENCE.id, Block.IRON_FENCE.id, false);
        }
        final int c = this.c(Block.STONE_STAIRS.id, 3);
        this.a(world, structureBoundingBox, 4, 1, 5, 6, 1, 7, false, random, WorldGenStrongholdPieces.e);
        this.a(world, structureBoundingBox, 4, 2, 6, 6, 2, 7, false, random, WorldGenStrongholdPieces.e);
        this.a(world, structureBoundingBox, 4, 3, 7, 6, 3, 7, false, random, WorldGenStrongholdPieces.e);
        for (int k = 4; k <= 6; ++k) {
            this.a(world, Block.STONE_STAIRS.id, c, k, 1, 4, structureBoundingBox);
            this.a(world, Block.STONE_STAIRS.id, c, k, 2, 5, structureBoundingBox);
            this.a(world, Block.STONE_STAIRS.id, c, k, 3, 6, structureBoundingBox);
        }
        int n2 = 2;
        int n3 = 0;
        int n4 = 3;
        int n5 = 1;
        switch (this.f) {
            case 0: {
                n2 = 0;
                n3 = 2;
                break;
            }
            case 3: {
                n2 = 3;
                n3 = 1;
                n4 = 0;
                n5 = 2;
                break;
            }
            case 1: {
                n2 = 1;
                n3 = 3;
                n4 = 0;
                n5 = 2;
                break;
            }
        }
        this.a(world, Block.ENDER_PORTAL_FRAME.id, n2 + ((random.nextFloat() > 0.9f) ? 4 : 0), 4, 3, 8, structureBoundingBox);
        this.a(world, Block.ENDER_PORTAL_FRAME.id, n2 + ((random.nextFloat() > 0.9f) ? 4 : 0), 5, 3, 8, structureBoundingBox);
        this.a(world, Block.ENDER_PORTAL_FRAME.id, n2 + ((random.nextFloat() > 0.9f) ? 4 : 0), 6, 3, 8, structureBoundingBox);
        this.a(world, Block.ENDER_PORTAL_FRAME.id, n3 + ((random.nextFloat() > 0.9f) ? 4 : 0), 4, 3, 12, structureBoundingBox);
        this.a(world, Block.ENDER_PORTAL_FRAME.id, n3 + ((random.nextFloat() > 0.9f) ? 4 : 0), 5, 3, 12, structureBoundingBox);
        this.a(world, Block.ENDER_PORTAL_FRAME.id, n3 + ((random.nextFloat() > 0.9f) ? 4 : 0), 6, 3, 12, structureBoundingBox);
        this.a(world, Block.ENDER_PORTAL_FRAME.id, n4 + ((random.nextFloat() > 0.9f) ? 4 : 0), 3, 3, 9, structureBoundingBox);
        this.a(world, Block.ENDER_PORTAL_FRAME.id, n4 + ((random.nextFloat() > 0.9f) ? 4 : 0), 3, 3, 10, structureBoundingBox);
        this.a(world, Block.ENDER_PORTAL_FRAME.id, n4 + ((random.nextFloat() > 0.9f) ? 4 : 0), 3, 3, 11, structureBoundingBox);
        this.a(world, Block.ENDER_PORTAL_FRAME.id, n5 + ((random.nextFloat() > 0.9f) ? 4 : 0), 7, 3, 9, structureBoundingBox);
        this.a(world, Block.ENDER_PORTAL_FRAME.id, n5 + ((random.nextFloat() > 0.9f) ? 4 : 0), 7, 3, 10, structureBoundingBox);
        this.a(world, Block.ENDER_PORTAL_FRAME.id, n5 + ((random.nextFloat() > 0.9f) ? 4 : 0), 7, 3, 11, structureBoundingBox);
        if (!this.a) {
            final int a = this.a(3);
            final int a2 = this.a(5, 6);
            final int b = this.b(5, 6);
            if (structureBoundingBox.b(a2, a, b)) {
                this.a = true;
                world.setTypeIdAndData(a2, a, b, Block.MOB_SPAWNER.id, 0, 2);
                final TileEntityMobSpawner tileEntityMobSpawner = (TileEntityMobSpawner)world.getTileEntity(a2, a, b);
                if (tileEntityMobSpawner != null) {
                    tileEntityMobSpawner.a().a("Silverfish");
                }
            }
        }
        return true;
    }
}
