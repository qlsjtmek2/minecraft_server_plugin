// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import java.util.Random;

public class BlockStationary extends BlockFluids
{
    protected BlockStationary(final int i, final Material material) {
        super(i, material);
        this.b(false);
        if (material == Material.LAVA) {
            this.b(true);
        }
    }
    
    public boolean b(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        return this.material != Material.LAVA;
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        super.doPhysics(world, i, j, k, l);
        if (world.getTypeId(i, j, k) == this.id) {
            this.k(world, i, j, k);
        }
    }
    
    private void k(final World world, final int i, final int j, final int k) {
        final int l = world.getData(i, j, k);
        world.setTypeIdAndData(i, j, k, this.id - 1, l, 2);
        world.a(i, j, k, this.id - 1, this.a(world));
    }
    
    public void a(final World world, int i, int j, int k, final Random random) {
        if (this.material == Material.LAVA) {
            final int l = random.nextInt(3);
            final int x = i;
            final int y = j;
            final int z = k;
            for (int i2 = 0; i2 < l; ++i2) {
                i += random.nextInt(3) - 1;
                ++j;
                k += random.nextInt(3) - 1;
                final int j2 = world.getTypeId(i, j, k);
                if (j2 == 0) {
                    if (this.m(world, i - 1, j, k) || this.m(world, i + 1, j, k) || this.m(world, i, j, k - 1) || this.m(world, i, j, k + 1) || this.m(world, i, j - 1, k) || this.m(world, i, j + 1, k)) {
                        if (world.getTypeId(i, j, k) == Block.FIRE.id || !CraftEventFactory.callBlockIgniteEvent(world, i, j, k, x, y, z).isCancelled()) {
                            world.setTypeIdUpdate(i, j, k, Block.FIRE.id);
                            return;
                        }
                    }
                }
                else if (Block.byId[j2].material.isSolid()) {
                    return;
                }
            }
            if (l == 0) {
                final int i2 = i;
                final int j2 = k;
                for (int k2 = 0; k2 < 3; ++k2) {
                    i = i2 + random.nextInt(3) - 1;
                    k = j2 + random.nextInt(3) - 1;
                    if (world.isEmpty(i, j + 1, k) && this.m(world, i, j, k)) {
                        if (world.getTypeId(i, j + 1, k) == Block.FIRE.id || !CraftEventFactory.callBlockIgniteEvent(world, i, j + 1, k, x, y, z).isCancelled()) {
                            world.setTypeIdUpdate(i, j + 1, k, Block.FIRE.id);
                        }
                    }
                }
            }
        }
    }
    
    private boolean m(final World world, final int i, final int j, final int k) {
        return world.getMaterial(i, j, k).isBurnable();
    }
}
