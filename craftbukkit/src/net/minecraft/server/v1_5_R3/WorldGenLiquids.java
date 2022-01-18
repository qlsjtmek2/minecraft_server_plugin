// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class WorldGenLiquids extends WorldGenerator
{
    private int a;
    
    public WorldGenLiquids(final int a) {
        this.a = a;
    }
    
    public boolean a(final World world, final Random random, final int n, final int n2, final int n3) {
        if (world.getTypeId(n, n2 + 1, n3) != Block.STONE.id) {
            return false;
        }
        if (world.getTypeId(n, n2 - 1, n3) != Block.STONE.id) {
            return false;
        }
        if (world.getTypeId(n, n2, n3) != 0 && world.getTypeId(n, n2, n3) != Block.STONE.id) {
            return false;
        }
        int n4 = 0;
        if (world.getTypeId(n - 1, n2, n3) == Block.STONE.id) {
            ++n4;
        }
        if (world.getTypeId(n + 1, n2, n3) == Block.STONE.id) {
            ++n4;
        }
        if (world.getTypeId(n, n2, n3 - 1) == Block.STONE.id) {
            ++n4;
        }
        if (world.getTypeId(n, n2, n3 + 1) == Block.STONE.id) {
            ++n4;
        }
        int n5 = 0;
        if (world.isEmpty(n - 1, n2, n3)) {
            ++n5;
        }
        if (world.isEmpty(n + 1, n2, n3)) {
            ++n5;
        }
        if (world.isEmpty(n, n2, n3 - 1)) {
            ++n5;
        }
        if (world.isEmpty(n, n2, n3 + 1)) {
            ++n5;
        }
        if (n4 == 3 && n5 == 1) {
            world.setTypeIdAndData(n, n2, n3, this.a, 0, 2);
            world.d = true;
            Block.byId[this.a].a(world, n, n2, n3, random);
            world.d = false;
        }
        return true;
    }
}
