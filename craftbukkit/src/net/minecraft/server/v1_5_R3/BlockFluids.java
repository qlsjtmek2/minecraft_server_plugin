// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public abstract class BlockFluids extends Block
{
    protected BlockFluids(final int i, final Material material) {
        super(i, material);
        final float n = 0.0f;
        final float n2 = 0.0f;
        this.a(0.0f + n2, 0.0f + n, 0.0f + n2, 1.0f + n2, 1.0f + n, 1.0f + n2);
        this.b(true);
    }
    
    public boolean b(final IBlockAccess blockAccess, final int n, final int n2, final int n3) {
        return this.material != Material.LAVA;
    }
    
    public static float d(int n) {
        if (n >= 8) {
            n = 0;
        }
        return (n + 1) / 9.0f;
    }
    
    protected int k_(final World world, final int n, final int n2, final int n3) {
        if (world.getMaterial(n, n2, n3) == this.material) {
            return world.getData(n, n2, n3);
        }
        return -1;
    }
    
    protected int d(final IBlockAccess blockAccess, final int n, final int n2, final int n3) {
        if (blockAccess.getMaterial(n, n2, n3) != this.material) {
            return -1;
        }
        int data = blockAccess.getData(n, n2, n3);
        if (data >= 8) {
            data = 0;
        }
        return data;
    }
    
    public boolean b() {
        return false;
    }
    
    public boolean c() {
        return false;
    }
    
    public boolean a(final int n, final boolean b) {
        return b && n == 0;
    }
    
    public boolean a_(final IBlockAccess iblockaccess, final int i, final int j, final int k, final int l) {
        final Material material = iblockaccess.getMaterial(i, j, k);
        return material != this.material && (l == 1 || (material != Material.ICE && super.a_(iblockaccess, i, j, k, l)));
    }
    
    public AxisAlignedBB b(final World world, final int n, final int n2, final int n3) {
        return null;
    }
    
    public int d() {
        return 4;
    }
    
    public int getDropType(final int n, final Random random, final int n2) {
        return 0;
    }
    
    public int a(final Random random) {
        return 0;
    }
    
    private Vec3D g(final IBlockAccess blockAccess, final int n, final int n2, final int n3) {
        Vec3D vec3D = blockAccess.getVec3DPool().create(0.0, 0.0, 0.0);
        final int d = this.d(blockAccess, n, n2, n3);
        for (int i = 0; i < 4; ++i) {
            int n4 = n;
            int n5 = n3;
            if (i == 0) {
                --n4;
            }
            if (i == 1) {
                --n5;
            }
            if (i == 2) {
                ++n4;
            }
            if (i == 3) {
                ++n5;
            }
            final int d2 = this.d(blockAccess, n4, n2, n5);
            if (d2 < 0) {
                if (!blockAccess.getMaterial(n4, n2, n5).isSolid()) {
                    final int d3 = this.d(blockAccess, n4, n2 - 1, n5);
                    if (d3 >= 0) {
                        final int n6 = d3 - (d - 8);
                        vec3D = vec3D.add((n4 - n) * n6, (n2 - n2) * n6, (n5 - n3) * n6);
                    }
                }
            }
            else if (d2 >= 0) {
                final int n7 = d2 - d;
                vec3D = vec3D.add((n4 - n) * n7, (n2 - n2) * n7, (n5 - n3) * n7);
            }
        }
        if (blockAccess.getData(n, n2, n3) >= 8) {
            int n8 = 0;
            if (n8 != 0 || this.a_(blockAccess, n, n2, n3 - 1, 2)) {
                n8 = 1;
            }
            if (n8 != 0 || this.a_(blockAccess, n, n2, n3 + 1, 3)) {
                n8 = 1;
            }
            if (n8 != 0 || this.a_(blockAccess, n - 1, n2, n3, 4)) {
                n8 = 1;
            }
            if (n8 != 0 || this.a_(blockAccess, n + 1, n2, n3, 5)) {
                n8 = 1;
            }
            if (n8 != 0 || this.a_(blockAccess, n, n2 + 1, n3 - 1, 2)) {
                n8 = 1;
            }
            if (n8 != 0 || this.a_(blockAccess, n, n2 + 1, n3 + 1, 3)) {
                n8 = 1;
            }
            if (n8 != 0 || this.a_(blockAccess, n - 1, n2 + 1, n3, 4)) {
                n8 = 1;
            }
            if (n8 != 0 || this.a_(blockAccess, n + 1, n2 + 1, n3, 5)) {
                n8 = 1;
            }
            if (n8 != 0) {
                vec3D = vec3D.a().add(0.0, -6.0, 0.0);
            }
        }
        return vec3D.a();
    }
    
    public void a(final World world, final int n, final int n2, final int n3, final Entity entity, final Vec3D vec3D) {
        final Vec3D g = this.g((IBlockAccess)world, n, n2, n3);
        vec3D.c += g.c;
        vec3D.d += g.d;
        vec3D.e += g.e;
    }
    
    public int a(final World world) {
        if (this.material == Material.WATER) {
            return 5;
        }
        if (this.material != Material.LAVA) {
            return 0;
        }
        if (world.worldProvider.f) {
            return 10;
        }
        return 30;
    }
    
    public void onPlace(final World world, final int n, final int n2, final int n3) {
        this.k(world, n, n2, n3);
    }
    
    public void doPhysics(final World world, final int n, final int n2, final int n3, final int n4) {
        this.k(world, n, n2, n3);
    }
    
    private void k(final World world, final int i, final int n, final int k) {
        if (world.getTypeId(i, n, k) != this.id) {
            return;
        }
        if (this.material == Material.LAVA) {
            int n2 = 0;
            if (n2 != 0 || world.getMaterial(i, n, k - 1) == Material.WATER) {
                n2 = 1;
            }
            if (n2 != 0 || world.getMaterial(i, n, k + 1) == Material.WATER) {
                n2 = 1;
            }
            if (n2 != 0 || world.getMaterial(i - 1, n, k) == Material.WATER) {
                n2 = 1;
            }
            if (n2 != 0 || world.getMaterial(i + 1, n, k) == Material.WATER) {
                n2 = 1;
            }
            if (n2 != 0 || world.getMaterial(i, n + 1, k) == Material.WATER) {
                n2 = 1;
            }
            if (n2 != 0) {
                final int data = world.getData(i, n, k);
                if (data == 0) {
                    world.setTypeIdUpdate(i, n, k, Block.OBSIDIAN.id);
                }
                else if (data <= 4) {
                    world.setTypeIdUpdate(i, n, k, Block.COBBLESTONE.id);
                }
                this.fizz(world, i, n, k);
            }
        }
    }
    
    protected void fizz(final World world, final int n, final int n2, final int n3) {
        world.makeSound(n + 0.5f, n2 + 0.5f, n3 + 0.5f, "random.fizz", 0.5f, 2.6f + (world.random.nextFloat() - world.random.nextFloat()) * 0.8f);
        for (int i = 0; i < 8; ++i) {
            world.addParticle("largesmoke", n + Math.random(), n2 + 1.2, n3 + Math.random(), 0.0, 0.0, 0.0);
        }
    }
}
