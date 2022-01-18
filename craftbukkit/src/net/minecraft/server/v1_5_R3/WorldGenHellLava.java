// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class WorldGenHellLava extends WorldGenerator
{
    private int a;
    private boolean b;
    
    public WorldGenHellLava(final int a, final boolean b) {
        this.b = false;
        this.a = a;
        this.b = b;
    }
    
    public boolean a(final World world, final Random random, final int i, final int n, final int k) {
        if (world.getTypeId(i, n + 1, k) != Block.NETHERRACK.id) {
            return false;
        }
        if (world.getTypeId(i, n, k) != 0 && world.getTypeId(i, n, k) != Block.NETHERRACK.id) {
            return false;
        }
        int n2 = 0;
        if (world.getTypeId(i - 1, n, k) == Block.NETHERRACK.id) {
            ++n2;
        }
        if (world.getTypeId(i + 1, n, k) == Block.NETHERRACK.id) {
            ++n2;
        }
        if (world.getTypeId(i, n, k - 1) == Block.NETHERRACK.id) {
            ++n2;
        }
        if (world.getTypeId(i, n, k + 1) == Block.NETHERRACK.id) {
            ++n2;
        }
        if (world.getTypeId(i, n - 1, k) == Block.NETHERRACK.id) {
            ++n2;
        }
        int n3 = 0;
        if (world.isEmpty(i - 1, n, k)) {
            ++n3;
        }
        if (world.isEmpty(i + 1, n, k)) {
            ++n3;
        }
        if (world.isEmpty(i, n, k - 1)) {
            ++n3;
        }
        if (world.isEmpty(i, n, k + 1)) {
            ++n3;
        }
        if (world.isEmpty(i, n - 1, k)) {
            ++n3;
        }
        if ((!this.b && n2 == 4 && n3 == 1) || n2 == 5) {
            world.setTypeIdAndData(i, n, k, this.a, 0, 2);
            world.d = true;
            Block.byId[this.a].a(world, i, n, k, random);
            world.d = false;
        }
        return true;
    }
}
