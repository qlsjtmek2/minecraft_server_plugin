// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

class WorldGenStrongholdStones extends StructurePieceBlockSelector
{
    public void a(final Random random, final int n, final int n2, final int n3, final boolean b) {
        if (b) {
            this.a = Block.SMOOTH_BRICK.id;
            final float nextFloat = random.nextFloat();
            if (nextFloat < 0.2f) {
                this.b = 2;
            }
            else if (nextFloat < 0.5f) {
                this.b = 1;
            }
            else if (nextFloat < 0.55f) {
                this.a = Block.MONSTER_EGGS.id;
                this.b = 2;
            }
            else {
                this.b = 0;
            }
        }
        else {
            this.a = 0;
            this.b = 0;
        }
    }
}
