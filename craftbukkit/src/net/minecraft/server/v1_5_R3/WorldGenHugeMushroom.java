// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import org.bukkit.block.BlockState;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import org.bukkit.material.MaterialData;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.BlockChangeDelegate;
import java.util.Random;

public class WorldGenHugeMushroom extends WorldGenerator implements BlockSapling.TreeGenerator
{
    private int a;
    
    public WorldGenHugeMushroom(final int i) {
        super(true);
        this.a = -1;
        this.a = i;
    }
    
    public WorldGenHugeMushroom() {
        super(false);
        this.a = -1;
    }
    
    public boolean a(final World world, final Random random, final int i, final int j, final int k) {
        return this.grow((BlockChangeDelegate)world, random, i, j, k, null, null, null);
    }
    
    public boolean generate(final BlockChangeDelegate world, final Random random, final int i, final int j, final int k) {
        return this.grow(world, random, i, j, k, null, null, null);
    }
    
    public boolean grow(final BlockChangeDelegate world, final Random random, final int i, final int j, final int k, final StructureGrowEvent event, final ItemStack itemstack, final CraftWorld bukkitWorld) {
        int l = random.nextInt(2);
        if (this.a >= 0) {
            l = this.a;
        }
        final int i2 = random.nextInt(3) + 4;
        boolean flag = true;
        if (j < 1 || j + i2 + 1 >= 256) {
            return false;
        }
        for (int j2 = j; j2 <= j + 1 + i2; ++j2) {
            byte b0 = 3;
            if (j2 <= j + 3) {
                b0 = 0;
            }
            for (int k2 = i - b0; k2 <= i + b0 && flag; ++k2) {
                for (int l2 = k - b0; l2 <= k + b0 && flag; ++l2) {
                    if (j2 >= 0 && j2 < 256) {
                        final int i3 = world.getTypeId(k2, j2, l2);
                        if (i3 != 0 && i3 != Block.LEAVES.id) {
                            flag = false;
                        }
                    }
                    else {
                        flag = false;
                    }
                }
            }
        }
        if (!flag) {
            return false;
        }
        int j2 = world.getTypeId(i, j - 1, k);
        if (j2 != Block.DIRT.id && j2 != Block.GRASS.id && j2 != Block.MYCEL.id) {
            return false;
        }
        if (event == null) {
            this.setTypeAndData(world, i, j - 1, k, Block.DIRT.id, 0);
        }
        else {
            final BlockState dirtState = bukkitWorld.getBlockAt(i, j - 1, k).getState();
            dirtState.setTypeId(Block.DIRT.id);
            event.getBlocks().add(dirtState);
        }
        int j3 = j + i2;
        if (l == 1) {
            j3 = j + i2 - 3;
        }
        for (int k2 = j3; k2 <= j + i2; ++k2) {
            int l2 = 1;
            if (k2 < j + i2) {
                ++l2;
            }
            if (l == 0) {
                l2 = 3;
            }
            for (int i3 = i - l2; i3 <= i + l2; ++i3) {
                for (int k3 = k - l2; k3 <= k + l2; ++k3) {
                    int l3 = 5;
                    if (i3 == i - l2) {
                        --l3;
                    }
                    if (i3 == i + l2) {
                        ++l3;
                    }
                    if (k3 == k - l2) {
                        l3 -= 3;
                    }
                    if (k3 == k + l2) {
                        l3 += 3;
                    }
                    if (l == 0 || k2 < j + i2) {
                        if (i3 == i - l2 || i3 == i + l2) {
                            if (k3 == k - l2) {
                                continue;
                            }
                            if (k3 == k + l2) {
                                continue;
                            }
                        }
                        if (i3 == i - (l2 - 1) && k3 == k - l2) {
                            l3 = 1;
                        }
                        if (i3 == i - l2 && k3 == k - (l2 - 1)) {
                            l3 = 1;
                        }
                        if (i3 == i + (l2 - 1) && k3 == k - l2) {
                            l3 = 3;
                        }
                        if (i3 == i + l2 && k3 == k - (l2 - 1)) {
                            l3 = 3;
                        }
                        if (i3 == i - (l2 - 1) && k3 == k + l2) {
                            l3 = 7;
                        }
                        if (i3 == i - l2 && k3 == k + (l2 - 1)) {
                            l3 = 7;
                        }
                        if (i3 == i + (l2 - 1) && k3 == k + l2) {
                            l3 = 9;
                        }
                        if (i3 == i + l2 && k3 == k + (l2 - 1)) {
                            l3 = 9;
                        }
                    }
                    if (l3 == 5 && k2 < j + i2) {
                        l3 = 0;
                    }
                    if ((l3 != 0 || j >= j + i2 - 1) && !Block.s[world.getTypeId(i3, k2, k3)]) {
                        if (event == null) {
                            this.setTypeAndData(world, i3, k2, k3, Block.BIG_MUSHROOM_1.id + l, l3);
                        }
                        else {
                            final BlockState state = bukkitWorld.getBlockAt(i3, k2, k3).getState();
                            state.setTypeId(Block.BIG_MUSHROOM_1.id + l);
                            state.setData(new MaterialData(Block.BIG_MUSHROOM_1.id + l, (byte)l3));
                            event.getBlocks().add(state);
                        }
                    }
                }
            }
        }
        for (int k2 = 0; k2 < i2; ++k2) {
            final int l2 = world.getTypeId(i, j + k2, k);
            if (!Block.s[l2]) {
                if (event == null) {
                    this.setTypeAndData(world, i, j + k2, k, Block.BIG_MUSHROOM_1.id + l, 10);
                }
                else {
                    final BlockState state2 = bukkitWorld.getBlockAt(i, j + k2, k).getState();
                    state2.setTypeId(Block.BIG_MUSHROOM_1.id + l);
                    state2.setData(new MaterialData(Block.BIG_MUSHROOM_1.id + l, (byte)10));
                    event.getBlocks().add(state2);
                }
            }
        }
        if (event != null) {
            Bukkit.getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
                for (final BlockState state3 : event.getBlocks()) {
                    state3.update(true);
                }
            }
        }
        return true;
    }
}
