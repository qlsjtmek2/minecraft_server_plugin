// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

abstract class WorldGenStrongholdPiece extends StructurePiece
{
    protected WorldGenStrongholdPiece(final int n) {
        super(n);
    }
    
    protected void a(final World world, final Random random, final StructureBoundingBox structureBoundingBox, final WorldGenStrongholdDoorType worldGenStrongholdDoorType, final int n, final int n2, final int n3) {
        switch (WorldGenStrongholdPieceWeight3.a[worldGenStrongholdDoorType.ordinal()]) {
            default: {
                this.a(world, structureBoundingBox, n, n2, n3, n + 3 - 1, n2 + 3 - 1, n3, 0, 0, false);
                break;
            }
            case 2: {
                this.a(world, Block.SMOOTH_BRICK.id, 0, n, n2, n3, structureBoundingBox);
                this.a(world, Block.SMOOTH_BRICK.id, 0, n, n2 + 1, n3, structureBoundingBox);
                this.a(world, Block.SMOOTH_BRICK.id, 0, n, n2 + 2, n3, structureBoundingBox);
                this.a(world, Block.SMOOTH_BRICK.id, 0, n + 1, n2 + 2, n3, structureBoundingBox);
                this.a(world, Block.SMOOTH_BRICK.id, 0, n + 2, n2 + 2, n3, structureBoundingBox);
                this.a(world, Block.SMOOTH_BRICK.id, 0, n + 2, n2 + 1, n3, structureBoundingBox);
                this.a(world, Block.SMOOTH_BRICK.id, 0, n + 2, n2, n3, structureBoundingBox);
                this.a(world, Block.WOODEN_DOOR.id, 0, n + 1, n2, n3, structureBoundingBox);
                this.a(world, Block.WOODEN_DOOR.id, 8, n + 1, n2 + 1, n3, structureBoundingBox);
                break;
            }
            case 3: {
                this.a(world, 0, 0, n + 1, n2, n3, structureBoundingBox);
                this.a(world, 0, 0, n + 1, n2 + 1, n3, structureBoundingBox);
                this.a(world, Block.IRON_FENCE.id, 0, n, n2, n3, structureBoundingBox);
                this.a(world, Block.IRON_FENCE.id, 0, n, n2 + 1, n3, structureBoundingBox);
                this.a(world, Block.IRON_FENCE.id, 0, n, n2 + 2, n3, structureBoundingBox);
                this.a(world, Block.IRON_FENCE.id, 0, n + 1, n2 + 2, n3, structureBoundingBox);
                this.a(world, Block.IRON_FENCE.id, 0, n + 2, n2 + 2, n3, structureBoundingBox);
                this.a(world, Block.IRON_FENCE.id, 0, n + 2, n2 + 1, n3, structureBoundingBox);
                this.a(world, Block.IRON_FENCE.id, 0, n + 2, n2, n3, structureBoundingBox);
                break;
            }
            case 4: {
                this.a(world, Block.SMOOTH_BRICK.id, 0, n, n2, n3, structureBoundingBox);
                this.a(world, Block.SMOOTH_BRICK.id, 0, n, n2 + 1, n3, structureBoundingBox);
                this.a(world, Block.SMOOTH_BRICK.id, 0, n, n2 + 2, n3, structureBoundingBox);
                this.a(world, Block.SMOOTH_BRICK.id, 0, n + 1, n2 + 2, n3, structureBoundingBox);
                this.a(world, Block.SMOOTH_BRICK.id, 0, n + 2, n2 + 2, n3, structureBoundingBox);
                this.a(world, Block.SMOOTH_BRICK.id, 0, n + 2, n2 + 1, n3, structureBoundingBox);
                this.a(world, Block.SMOOTH_BRICK.id, 0, n + 2, n2, n3, structureBoundingBox);
                this.a(world, Block.IRON_DOOR_BLOCK.id, 0, n + 1, n2, n3, structureBoundingBox);
                this.a(world, Block.IRON_DOOR_BLOCK.id, 8, n + 1, n2 + 1, n3, structureBoundingBox);
                this.a(world, Block.STONE_BUTTON.id, this.c(Block.STONE_BUTTON.id, 4), n + 2, n2 + 1, n3 + 1, structureBoundingBox);
                this.a(world, Block.STONE_BUTTON.id, this.c(Block.STONE_BUTTON.id, 3), n + 2, n2 + 1, n3 - 1, structureBoundingBox);
                break;
            }
        }
    }
    
    protected WorldGenStrongholdDoorType a(final Random random) {
        switch (random.nextInt(5)) {
            default: {
                return WorldGenStrongholdDoorType.a;
            }
            case 2: {
                return WorldGenStrongholdDoorType.b;
            }
            case 3: {
                return WorldGenStrongholdDoorType.c;
            }
            case 4: {
                return WorldGenStrongholdDoorType.d;
            }
        }
    }
    
    protected StructurePiece a(final WorldGenStrongholdStart worldGenStrongholdStart, final List list, final Random random, final int n, final int n2) {
        switch (this.f) {
            case 2: {
                return c(worldGenStrongholdStart, list, random, this.e.a + n, this.e.b + n2, this.e.c - 1, this.f, this.c());
            }
            case 0: {
                return c(worldGenStrongholdStart, list, random, this.e.a + n, this.e.b + n2, this.e.f + 1, this.f, this.c());
            }
            case 1: {
                return c(worldGenStrongholdStart, list, random, this.e.a - 1, this.e.b + n2, this.e.c + n, this.f, this.c());
            }
            case 3: {
                return c(worldGenStrongholdStart, list, random, this.e.d + 1, this.e.b + n2, this.e.c + n, this.f, this.c());
            }
            default: {
                return null;
            }
        }
    }
    
    protected StructurePiece b(final WorldGenStrongholdStart worldGenStrongholdStart, final List list, final Random random, final int n, final int n2) {
        switch (this.f) {
            case 2: {
                return c(worldGenStrongholdStart, list, random, this.e.a - 1, this.e.b + n, this.e.c + n2, 1, this.c());
            }
            case 0: {
                return c(worldGenStrongholdStart, list, random, this.e.a - 1, this.e.b + n, this.e.c + n2, 1, this.c());
            }
            case 1: {
                return c(worldGenStrongholdStart, list, random, this.e.a + n2, this.e.b + n, this.e.c - 1, 2, this.c());
            }
            case 3: {
                return c(worldGenStrongholdStart, list, random, this.e.a + n2, this.e.b + n, this.e.c - 1, 2, this.c());
            }
            default: {
                return null;
            }
        }
    }
    
    protected StructurePiece c(final WorldGenStrongholdStart worldGenStrongholdStart, final List list, final Random random, final int n, final int n2) {
        switch (this.f) {
            case 2: {
                return c(worldGenStrongholdStart, list, random, this.e.d + 1, this.e.b + n, this.e.c + n2, 3, this.c());
            }
            case 0: {
                return c(worldGenStrongholdStart, list, random, this.e.d + 1, this.e.b + n, this.e.c + n2, 3, this.c());
            }
            case 1: {
                return c(worldGenStrongholdStart, list, random, this.e.a + n2, this.e.b + n, this.e.f + 1, 0, this.c());
            }
            case 3: {
                return c(worldGenStrongholdStart, list, random, this.e.a + n2, this.e.b + n, this.e.f + 1, 0, this.c());
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
