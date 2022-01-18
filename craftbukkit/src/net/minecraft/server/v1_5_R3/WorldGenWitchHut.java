// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class WorldGenWitchHut extends WorldGenScatteredPiece
{
    private boolean h;
    
    public WorldGenWitchHut(final Random random, final int n, final int n2) {
        super(random, n, 64, n2, 7, 5, 9);
    }
    
    public boolean a(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        if (!this.a(world, structureBoundingBox, 0)) {
            return false;
        }
        this.a(world, structureBoundingBox, 1, 1, 1, 5, 1, 7, Block.WOOD.id, 1, Block.WOOD.id, 1, false);
        this.a(world, structureBoundingBox, 1, 4, 2, 5, 4, 7, Block.WOOD.id, 1, Block.WOOD.id, 1, false);
        this.a(world, structureBoundingBox, 2, 1, 0, 4, 1, 0, Block.WOOD.id, 1, Block.WOOD.id, 1, false);
        this.a(world, structureBoundingBox, 2, 2, 2, 3, 3, 2, Block.WOOD.id, 1, Block.WOOD.id, 1, false);
        this.a(world, structureBoundingBox, 1, 2, 3, 1, 3, 6, Block.WOOD.id, 1, Block.WOOD.id, 1, false);
        this.a(world, structureBoundingBox, 5, 2, 3, 5, 3, 6, Block.WOOD.id, 1, Block.WOOD.id, 1, false);
        this.a(world, structureBoundingBox, 2, 2, 7, 4, 3, 7, Block.WOOD.id, 1, Block.WOOD.id, 1, false);
        this.a(world, structureBoundingBox, 1, 0, 2, 1, 3, 2, Block.LOG.id, Block.LOG.id, false);
        this.a(world, structureBoundingBox, 5, 0, 2, 5, 3, 2, Block.LOG.id, Block.LOG.id, false);
        this.a(world, structureBoundingBox, 1, 0, 7, 1, 3, 7, Block.LOG.id, Block.LOG.id, false);
        this.a(world, structureBoundingBox, 5, 0, 7, 5, 3, 7, Block.LOG.id, Block.LOG.id, false);
        this.a(world, Block.FENCE.id, 0, 2, 3, 2, structureBoundingBox);
        this.a(world, Block.FENCE.id, 0, 3, 3, 7, structureBoundingBox);
        this.a(world, 0, 0, 1, 3, 4, structureBoundingBox);
        this.a(world, 0, 0, 5, 3, 4, structureBoundingBox);
        this.a(world, 0, 0, 5, 3, 5, structureBoundingBox);
        this.a(world, Block.FLOWER_POT.id, 7, 1, 3, 5, structureBoundingBox);
        this.a(world, Block.WORKBENCH.id, 0, 3, 2, 6, structureBoundingBox);
        this.a(world, Block.CAULDRON.id, 0, 4, 2, 6, structureBoundingBox);
        this.a(world, Block.FENCE.id, 0, 1, 2, 1, structureBoundingBox);
        this.a(world, Block.FENCE.id, 0, 5, 2, 1, structureBoundingBox);
        final int c = this.c(Block.WOOD_STAIRS.id, 3);
        final int c2 = this.c(Block.WOOD_STAIRS.id, 1);
        final int c3 = this.c(Block.WOOD_STAIRS.id, 0);
        final int c4 = this.c(Block.WOOD_STAIRS.id, 2);
        this.a(world, structureBoundingBox, 0, 4, 1, 6, 4, 1, Block.SPRUCE_WOOD_STAIRS.id, c, Block.SPRUCE_WOOD_STAIRS.id, c, false);
        this.a(world, structureBoundingBox, 0, 4, 2, 0, 4, 7, Block.SPRUCE_WOOD_STAIRS.id, c3, Block.SPRUCE_WOOD_STAIRS.id, c3, false);
        this.a(world, structureBoundingBox, 6, 4, 2, 6, 4, 7, Block.SPRUCE_WOOD_STAIRS.id, c2, Block.SPRUCE_WOOD_STAIRS.id, c2, false);
        this.a(world, structureBoundingBox, 0, 4, 8, 6, 4, 8, Block.SPRUCE_WOOD_STAIRS.id, c4, Block.SPRUCE_WOOD_STAIRS.id, c4, false);
        for (int i = 2; i <= 7; i += 5) {
            for (int j = 1; j <= 5; j += 4) {
                this.b(world, Block.LOG.id, 0, j, -1, i, structureBoundingBox);
            }
        }
        if (!this.h) {
            final int a = this.a(2, 5);
            final int a2 = this.a(2);
            final int b = this.b(2, 5);
            if (structureBoundingBox.b(a, a2, b)) {
                this.h = true;
                final EntityWitch entity = new EntityWitch(world);
                entity.setPositionRotation(a + 0.5, a2, b + 0.5, 0.0f, 0.0f);
                entity.bJ();
                world.addEntity(entity);
            }
        }
        return true;
    }
}
