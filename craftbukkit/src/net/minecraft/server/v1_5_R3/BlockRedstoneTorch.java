// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.HashMap;
import org.bukkit.plugin.PluginManager;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockRedstoneEvent;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class BlockRedstoneTorch extends BlockTorch
{
    private boolean isOn;
    private static Map b;
    
    private boolean a(final World world, final int i, final int j, final int k, final boolean flag) {
        if (!BlockRedstoneTorch.b.containsKey(world)) {
            BlockRedstoneTorch.b.put(world, new ArrayList());
        }
        final List list = BlockRedstoneTorch.b.get(world);
        if (flag) {
            list.add(new RedstoneUpdateInfo(i, j, k, world.getTime()));
        }
        int l = 0;
        for (int i2 = 0; i2 < list.size(); ++i2) {
            final RedstoneUpdateInfo redstoneupdateinfo = list.get(i2);
            if (redstoneupdateinfo.a == i && redstoneupdateinfo.b == j && redstoneupdateinfo.c == k && ++l >= 8) {
                return true;
            }
        }
        return false;
    }
    
    protected BlockRedstoneTorch(final int i, final boolean flag) {
        super(i);
        this.isOn = false;
        this.isOn = flag;
        this.b(true);
        this.a((CreativeModeTab)null);
    }
    
    public int a(final World world) {
        return 2;
    }
    
    public void onPlace(final World world, final int i, final int j, final int k) {
        if (world.getData(i, j, k) == 0) {
            super.onPlace(world, i, j, k);
        }
        if (this.isOn) {
            world.applyPhysics(i, j - 1, k, this.id);
            world.applyPhysics(i, j + 1, k, this.id);
            world.applyPhysics(i - 1, j, k, this.id);
            world.applyPhysics(i + 1, j, k, this.id);
            world.applyPhysics(i, j, k - 1, this.id);
            world.applyPhysics(i, j, k + 1, this.id);
        }
    }
    
    public void remove(final World world, final int i, final int j, final int k, final int l, final int i1) {
        if (this.isOn) {
            world.applyPhysics(i, j - 1, k, this.id);
            world.applyPhysics(i, j + 1, k, this.id);
            world.applyPhysics(i - 1, j, k, this.id);
            world.applyPhysics(i + 1, j, k, this.id);
            world.applyPhysics(i, j, k - 1, this.id);
            world.applyPhysics(i, j, k + 1, this.id);
        }
    }
    
    public int b(final IBlockAccess iblockaccess, final int i, final int j, final int k, final int l) {
        if (!this.isOn) {
            return 0;
        }
        final int i2 = iblockaccess.getData(i, j, k);
        return (i2 == 5 && l == 1) ? 0 : ((i2 == 3 && l == 3) ? 0 : ((i2 == 4 && l == 2) ? 0 : ((i2 == 1 && l == 5) ? 0 : ((i2 == 2 && l == 4) ? 0 : 15))));
    }
    
    private boolean m(final World world, final int i, final int j, final int k) {
        final int l = world.getData(i, j, k);
        return (l == 5 && world.isBlockFacePowered(i, j - 1, k, 0)) || (l == 3 && world.isBlockFacePowered(i, j, k - 1, 2)) || (l == 4 && world.isBlockFacePowered(i, j, k + 1, 3)) || (l == 1 && world.isBlockFacePowered(i - 1, j, k, 4)) || (l == 2 && world.isBlockFacePowered(i + 1, j, k, 5));
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
        final boolean flag = this.m(world, i, j, k);
        final List list = BlockRedstoneTorch.b.get(world);
        while (list != null && !list.isEmpty() && world.getTime() - list.get(0).d > 60L) {
            list.remove(0);
        }
        final PluginManager manager = world.getServer().getPluginManager();
        final org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
        final int oldCurrent = this.isOn ? 15 : 0;
        final BlockRedstoneEvent event = new BlockRedstoneEvent(block, oldCurrent, oldCurrent);
        if (this.isOn) {
            if (flag) {
                if (oldCurrent != 0) {
                    event.setNewCurrent(0);
                    manager.callEvent(event);
                    if (event.getNewCurrent() != 0) {
                        return;
                    }
                }
                world.setTypeIdAndData(i, j, k, Block.REDSTONE_TORCH_OFF.id, world.getData(i, j, k), 3);
                if (this.a(world, i, j, k, true)) {
                    world.makeSound(i + 0.5f, j + 0.5f, k + 0.5f, "random.fizz", 0.5f, 2.6f + (world.random.nextFloat() - world.random.nextFloat()) * 0.8f);
                    for (int l = 0; l < 5; ++l) {
                        final double d0 = i + random.nextDouble() * 0.6 + 0.2;
                        final double d2 = j + random.nextDouble() * 0.6 + 0.2;
                        final double d3 = k + random.nextDouble() * 0.6 + 0.2;
                        world.addParticle("smoke", d0, d2, d3, 0.0, 0.0, 0.0);
                    }
                }
            }
        }
        else if (!flag && !this.a(world, i, j, k, false)) {
            if (oldCurrent != 15) {
                event.setNewCurrent(15);
                manager.callEvent(event);
                if (event.getNewCurrent() != 15) {
                    return;
                }
            }
            world.setTypeIdAndData(i, j, k, Block.REDSTONE_TORCH_ON.id, world.getData(i, j, k), 3);
        }
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        if (!this.d(world, i, j, k, l)) {
            final boolean flag = this.m(world, i, j, k);
            if ((this.isOn && flag) || (!this.isOn && !flag)) {
                world.a(i, j, k, this.id, this.a(world));
            }
        }
    }
    
    public int c(final IBlockAccess iblockaccess, final int i, final int j, final int k, final int l) {
        return (l == 0) ? this.b(iblockaccess, i, j, k, l) : 0;
    }
    
    public int getDropType(final int i, final Random random, final int j) {
        return Block.REDSTONE_TORCH_ON.id;
    }
    
    public boolean isPowerSource() {
        return true;
    }
    
    public boolean i(final int i) {
        return i == Block.REDSTONE_TORCH_OFF.id || i == Block.REDSTONE_TORCH_ON.id;
    }
    
    static {
        BlockRedstoneTorch.b = new HashMap();
    }
}
