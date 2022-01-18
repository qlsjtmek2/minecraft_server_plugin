// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.Cancellable;
import org.bukkit.plugin.PluginManager;
import java.util.Iterator;
import java.util.List;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.event.block.Action;
import java.util.Random;

public class BlockTripwire extends Block
{
    public BlockTripwire(final int i) {
        super(i, Material.ORIENTABLE);
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.15625f, 1.0f);
        this.b(true);
    }
    
    public int a(final World world) {
        return 10;
    }
    
    public AxisAlignedBB b(final World world, final int i, final int j, final int k) {
        return null;
    }
    
    public boolean c() {
        return false;
    }
    
    public boolean b() {
        return false;
    }
    
    public int d() {
        return 30;
    }
    
    public int getDropType(final int i, final Random random, final int j) {
        return Item.STRING.id;
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        final int i2 = world.getData(i, j, k);
        final boolean flag = (i2 & 0x2) == 0x2;
        final boolean flag2 = !world.w(i, j - 1, k);
        if (flag != flag2) {
            this.c(world, i, j, k, i2, 0);
            world.setAir(i, j, k);
        }
    }
    
    public void updateShape(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        final int l = iblockaccess.getData(i, j, k);
        final boolean flag = (l & 0x4) == 0x4;
        final boolean flag2 = (l & 0x2) == 0x2;
        if (!flag2) {
            this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.09375f, 1.0f);
        }
        else if (!flag) {
            this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
        else {
            this.a(0.0f, 0.0625f, 0.0f, 1.0f, 0.15625f, 1.0f);
        }
    }
    
    public void onPlace(final World world, final int i, final int j, final int k) {
        final int l = world.w(i, j - 1, k) ? 0 : 2;
        world.setData(i, j, k, l, 3);
        this.d(world, i, j, k, l);
    }
    
    public void remove(final World world, final int i, final int j, final int k, final int l, final int i1) {
        this.d(world, i, j, k, i1 | 0x1);
    }
    
    public void a(final World world, final int i, final int j, final int k, final int l, final EntityHuman entityhuman) {
        if (!world.isStatic && entityhuman.cd() != null && entityhuman.cd().id == Item.SHEARS.id) {
            world.setData(i, j, k, l | 0x8, 4);
        }
    }
    
    private void d(final World world, final int i, final int j, final int k, final int l) {
        for (int i2 = 0; i2 < 2; ++i2) {
            for (int j2 = 1; j2 < 42; ++j2) {
                final int k2 = i + Direction.a[i2] * j2;
                final int l2 = k + Direction.b[i2] * j2;
                final int i3 = world.getTypeId(k2, j, l2);
                if (i3 == Block.TRIPWIRE_SOURCE.id) {
                    final int j3 = world.getData(k2, j, l2) & 0x3;
                    if (j3 == Direction.f[i2]) {
                        Block.TRIPWIRE_SOURCE.a(world, k2, j, l2, i3, world.getData(k2, j, l2), true, j2, l);
                    }
                    break;
                }
                if (i3 != Block.TRIPWIRE.id) {
                    break;
                }
            }
        }
    }
    
    public void a(final World world, final int i, final int j, final int k, final Entity entity) {
        if (!world.isStatic && (world.getData(i, j, k) & 0x1) != 0x1) {
            this.k(world, i, j, k);
        }
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
        if (!world.isStatic && (world.getData(i, j, k) & 0x1) == 0x1) {
            this.k(world, i, j, k);
        }
    }
    
    private void k(final World world, final int i, final int j, final int k) {
        int l = world.getData(i, j, k);
        final boolean flag = (l & 0x1) == 0x1;
        boolean flag2 = false;
        final List list = world.getEntities(null, AxisAlignedBB.a().a(i + this.minX, j + this.minY, k + this.minZ, i + this.maxX, j + this.maxY, k + this.maxZ));
        if (!list.isEmpty()) {
            for (final Entity entity : list) {
                if (!entity.at()) {
                    flag2 = true;
                    break;
                }
            }
        }
        if (flag != flag2 && flag2 && (world.getData(i, j, k) & 0x4) == 0x4) {
            final org.bukkit.World bworld = world.getWorld();
            final PluginManager manager = world.getServer().getPluginManager();
            final org.bukkit.block.Block block = bworld.getBlockAt(i, j, k);
            boolean allowed = false;
            for (final Object object : list) {
                if (object != null) {
                    Cancellable cancellable;
                    if (object instanceof EntityHuman) {
                        cancellable = CraftEventFactory.callPlayerInteractEvent((EntityHuman)object, Action.PHYSICAL, i, j, k, -1, null);
                    }
                    else {
                        if (!(object instanceof Entity)) {
                            continue;
                        }
                        cancellable = new EntityInteractEvent(((Entity)object).getBukkitEntity(), block);
                        manager.callEvent((Event)cancellable);
                    }
                    if (!cancellable.isCancelled()) {
                        allowed = true;
                        break;
                    }
                    continue;
                }
            }
            if (!allowed) {
                return;
            }
        }
        if (flag2 && !flag) {
            l |= 0x1;
        }
        if (!flag2 && flag) {
            l &= 0xFFFFFFFE;
        }
        if (flag2 != flag) {
            world.setData(i, j, k, l, 3);
            this.d(world, i, j, k, l);
        }
        if (flag2) {
            world.a(i, j, k, this.id, this.a(world));
        }
    }
}
