// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

class WorldGenJungleTemplePiece extends StructurePieceBlockSelector
{
    public void a(final Random random, final int n, final int n2, final int n3, final boolean b) {
        if (random.nextFloat() < 0.4f) {
            this.a = Block.COBBLESTONE.id;
        }
        else {
            this.a = Block.MOSSY_COBBLESTONE.id;
        }
    }
}
