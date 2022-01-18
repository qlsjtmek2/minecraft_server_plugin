// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class BlockPoweredRail extends BlockMinecartTrackAbstract
{
    protected BlockPoweredRail(final int n) {
        super(n, true);
    }
    
    protected boolean a(final World world, int n, int n2, int n3, final int n4, final boolean b, final int n5) {
        if (n5 >= 8) {
            return false;
        }
        int n6 = n4 & 0x7;
        boolean b2 = true;
        switch (n6) {
            case 0: {
                if (b) {
                    ++n3;
                    break;
                }
                --n3;
                break;
            }
            case 1: {
                if (b) {
                    --n;
                    break;
                }
                ++n;
                break;
            }
            case 2: {
                if (b) {
                    --n;
                }
                else {
                    ++n;
                    ++n2;
                    b2 = false;
                }
                n6 = 1;
                break;
            }
            case 3: {
                if (b) {
                    --n;
                    ++n2;
                    b2 = false;
                }
                else {
                    ++n;
                }
                n6 = 1;
                break;
            }
            case 4: {
                if (b) {
                    ++n3;
                }
                else {
                    --n3;
                    ++n2;
                    b2 = false;
                }
                n6 = 0;
                break;
            }
            case 5: {
                if (b) {
                    ++n3;
                    ++n2;
                    b2 = false;
                }
                else {
                    --n3;
                }
                n6 = 0;
                break;
            }
        }
        return this.a(world, n, n2, n3, b, n5, n6) || (b2 && this.a(world, n, n2 - 1, n3, b, n5, n6));
    }
    
    protected boolean a(final World world, final int i, final int j, final int k, final boolean b, final int n, final int n2) {
        if (world.getTypeId(i, j, k) == this.id) {
            final int data = world.getData(i, j, k);
            final int n3 = data & 0x7;
            if (n2 == 1 && (n3 == 0 || n3 == 4 || n3 == 5)) {
                return false;
            }
            if (n2 == 0 && (n3 == 1 || n3 == 2 || n3 == 3)) {
                return false;
            }
            if ((data & 0x8) != 0x0) {
                return world.isBlockIndirectlyPowered(i, j, k) || this.a(world, i, j, k, data, b, n + 1);
            }
        }
        return false;
    }
    
    protected void a(final World world, final int i, final int j, final int k, final int n, final int l, final int n2) {
        final boolean b = world.isBlockIndirectlyPowered(i, j, k) || this.a(world, i, j, k, n, true, 0) || this.a(world, i, j, k, n, false, 0);
        boolean b2 = false;
        if (b && (n & 0x8) == 0x0) {
            world.setData(i, j, k, l | 0x8, 3);
            b2 = true;
        }
        else if (!b && (n & 0x8) != 0x0) {
            world.setData(i, j, k, l, 3);
            b2 = true;
        }
        if (b2) {
            world.applyPhysics(i, j - 1, k, this.id);
            if (l == 2 || l == 3 || l == 4 || l == 5) {
                world.applyPhysics(i, j + 1, k, this.id);
            }
        }
    }
}
