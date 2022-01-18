// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.block.Block;
import java.util.List;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockRedstoneEvent;
import java.util.Random;

public class BlockMinecartDetector extends BlockMinecartTrackAbstract
{
    public BlockMinecartDetector(final int i) {
        super(i, true);
        this.b(true);
    }
    
    public int a(final World world) {
        return 20;
    }
    
    public boolean isPowerSource() {
        return true;
    }
    
    public void a(final World world, final int i, final int j, final int k, final Entity entity) {
        if (!world.isStatic) {
            final int l = world.getData(i, j, k);
            if ((l & 0x8) == 0x0) {
                this.d(world, i, j, k, l);
            }
        }
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
        if (!world.isStatic) {
            final int l = world.getData(i, j, k);
            if ((l & 0x8) != 0x0) {
                this.d(world, i, j, k, l);
            }
        }
    }
    
    public int b(final IBlockAccess iblockaccess, final int i, final int j, final int k, final int l) {
        return ((iblockaccess.getData(i, j, k) & 0x8) != 0x0) ? 15 : 0;
    }
    
    public int c(final IBlockAccess iblockaccess, final int i, final int j, final int k, final int l) {
        return ((iblockaccess.getData(i, j, k) & 0x8) == 0x0) ? 0 : ((l == 1) ? 15 : 0);
    }
    
    private void d(final World world, final int i, final int j, final int k, final int l) {
        final boolean flag = (l & 0x8) != 0x0;
        boolean flag2 = false;
        final float f = 0.125f;
        final List list = world.a(EntityMinecartAbstract.class, AxisAlignedBB.a().a(i + f, j, k + f, i + 1 - f, j + 1 - f, k + 1 - f));
        if (!list.isEmpty()) {
            flag2 = true;
        }
        if (flag != flag2) {
            final org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
            final BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, flag ? 15 : 0, flag2 ? 15 : 0);
            world.getServer().getPluginManager().callEvent(eventRedstone);
            flag2 = (eventRedstone.getNewCurrent() > 0);
        }
        if (flag2 && !flag) {
            world.setData(i, j, k, l | 0x8, 3);
            world.applyPhysics(i, j, k, this.id);
            world.applyPhysics(i, j - 1, k, this.id);
            world.g(i, j, k, i, j, k);
        }
        if (!flag2 && flag) {
            world.setData(i, j, k, l & 0x7, 3);
            world.applyPhysics(i, j, k, this.id);
            world.applyPhysics(i, j - 1, k, this.id);
            world.g(i, j, k, i, j, k);
        }
        if (flag2) {
            world.a(i, j, k, this.id, this.a(world));
        }
        world.m(i, j, k, this.id);
    }
    
    public void onPlace(final World world, final int i, final int j, final int k) {
        super.onPlace(world, i, j, k);
        this.d(world, i, j, k, world.getData(i, j, k));
    }
    
    public boolean q_() {
        return true;
    }
    
    public int b_(final World world, final int i, final int j, final int k, final int l) {
        if ((world.getData(i, j, k) & 0x8) > 0) {
            final float f = 0.125f;
            final List list = world.a(EntityMinecartAbstract.class, AxisAlignedBB.a().a(i + f, j, k + f, i + 1 - f, j + 1 - f, k + 1 - f), IEntitySelector.b);
            if (list.size() > 0) {
                return Container.b(list.get(0));
            }
        }
        return 0;
    }
}
