// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.Event;
import org.bukkit.event.block.LeavesDecayEvent;
import java.util.Random;

public class BlockLeaves extends BlockTransparant
{
    public static final String[] a;
    public static final String[][] b;
    private IIcon[][] cR;
    int[] c;
    
    protected BlockLeaves(final int i) {
        super(i, Material.LEAVES, false);
        this.cR = new IIcon[2][];
        this.b(true);
        this.a(CreativeModeTab.c);
    }
    
    public void remove(final World world, final int i, final int j, final int k, final int l, final int i1) {
        final byte b0 = 1;
        final int j2 = b0 + 1;
        if (world.e(i - j2, j - j2, k - j2, i + j2, j + j2, k + j2)) {
            for (int k2 = -b0; k2 <= b0; ++k2) {
                for (int l2 = -b0; l2 <= b0; ++l2) {
                    for (int i2 = -b0; i2 <= b0; ++i2) {
                        final int j3 = world.getTypeId(i + k2, j + l2, k + i2);
                        if (j3 == Block.LEAVES.id) {
                            final int k3 = world.getData(i + k2, j + l2, k + i2);
                            world.setData(i + k2, j + l2, k + i2, k3 | 0x8, 4);
                        }
                    }
                }
            }
        }
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
        if (!world.isStatic) {
            final int l = world.getData(i, j, k);
            if ((l & 0x8) != 0x0 && (l & 0x4) == 0x0) {
                final byte b0 = 4;
                final int i2 = b0 + 1;
                final byte b2 = 32;
                final int j2 = b2 * b2;
                final int k2 = b2 / 2;
                if (this.c == null) {
                    this.c = new int[b2 * b2 * b2];
                }
                if (world.e(i - i2, j - i2, k - i2, i + i2, j + i2, k + i2)) {
                    for (int l2 = -b0; l2 <= b0; ++l2) {
                        for (int i3 = -b0; i3 <= b0; ++i3) {
                            for (int j3 = -b0; j3 <= b0; ++j3) {
                                final int k3 = world.getTypeId(i + l2, j + i3, k + j3);
                                if (k3 == Block.LOG.id) {
                                    this.c[(l2 + k2) * j2 + (i3 + k2) * b2 + j3 + k2] = 0;
                                }
                                else if (k3 == Block.LEAVES.id) {
                                    this.c[(l2 + k2) * j2 + (i3 + k2) * b2 + j3 + k2] = -2;
                                }
                                else {
                                    this.c[(l2 + k2) * j2 + (i3 + k2) * b2 + j3 + k2] = -1;
                                }
                            }
                        }
                    }
                    for (int l2 = 1; l2 <= 4; ++l2) {
                        for (int i3 = -b0; i3 <= b0; ++i3) {
                            for (int j3 = -b0; j3 <= b0; ++j3) {
                                for (int k3 = -b0; k3 <= b0; ++k3) {
                                    if (this.c[(i3 + k2) * j2 + (j3 + k2) * b2 + k3 + k2] == l2 - 1) {
                                        if (this.c[(i3 + k2 - 1) * j2 + (j3 + k2) * b2 + k3 + k2] == -2) {
                                            this.c[(i3 + k2 - 1) * j2 + (j3 + k2) * b2 + k3 + k2] = l2;
                                        }
                                        if (this.c[(i3 + k2 + 1) * j2 + (j3 + k2) * b2 + k3 + k2] == -2) {
                                            this.c[(i3 + k2 + 1) * j2 + (j3 + k2) * b2 + k3 + k2] = l2;
                                        }
                                        if (this.c[(i3 + k2) * j2 + (j3 + k2 - 1) * b2 + k3 + k2] == -2) {
                                            this.c[(i3 + k2) * j2 + (j3 + k2 - 1) * b2 + k3 + k2] = l2;
                                        }
                                        if (this.c[(i3 + k2) * j2 + (j3 + k2 + 1) * b2 + k3 + k2] == -2) {
                                            this.c[(i3 + k2) * j2 + (j3 + k2 + 1) * b2 + k3 + k2] = l2;
                                        }
                                        if (this.c[(i3 + k2) * j2 + (j3 + k2) * b2 + (k3 + k2 - 1)] == -2) {
                                            this.c[(i3 + k2) * j2 + (j3 + k2) * b2 + (k3 + k2 - 1)] = l2;
                                        }
                                        if (this.c[(i3 + k2) * j2 + (j3 + k2) * b2 + k3 + k2 + 1] == -2) {
                                            this.c[(i3 + k2) * j2 + (j3 + k2) * b2 + k3 + k2 + 1] = l2;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                int l2 = this.c[k2 * j2 + k2 * b2 + k2];
                if (l2 >= 0) {
                    world.setData(i, j, k, l & 0xFFFFFFF7, 4);
                }
                else {
                    this.k(world, i, j, k);
                }
            }
        }
    }
    
    private void k(final World world, final int i, final int j, final int k) {
        final LeavesDecayEvent event = new LeavesDecayEvent(world.getWorld().getBlockAt(i, j, k));
        world.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }
        this.c(world, i, j, k, world.getData(i, j, k), 0);
        world.setAir(i, j, k);
    }
    
    public int a(final Random random) {
        return (random.nextInt(20) == 0) ? 1 : 0;
    }
    
    public int getDropType(final int i, final Random random, final int j) {
        return Block.SAPLING.id;
    }
    
    public void dropNaturally(final World world, final int i, final int j, final int k, final int l, final float f, final int i1) {
        if (!world.isStatic) {
            int j2 = 20;
            if ((l & 0x3) == 0x3) {
                j2 = 40;
            }
            if (i1 > 0) {
                j2 -= 2 << i1;
                if (j2 < 10) {
                    j2 = 10;
                }
            }
            if (world.random.nextInt(j2) == 0) {
                final int k2 = this.getDropType(l, world.random, i1);
                this.b(world, i, j, k, new ItemStack(k2, 1, this.getDropData(l)));
            }
            j2 = 200;
            if (i1 > 0) {
                j2 -= 10 << i1;
                if (j2 < 40) {
                    j2 = 40;
                }
            }
            if ((l & 0x3) == 0x0 && world.random.nextInt(j2) == 0) {
                this.b(world, i, j, k, new ItemStack(Item.APPLE, 1, 0));
            }
        }
    }
    
    public void a(final World world, final EntityHuman entityhuman, final int i, final int j, final int k, final int l) {
        if (!world.isStatic && entityhuman.cd() != null && entityhuman.cd().id == Item.SHEARS.id) {
            entityhuman.a(StatisticList.C[this.id], 1);
            this.b(world, i, j, k, new ItemStack(Block.LEAVES.id, 1, l & 0x3));
        }
        else {
            super.a(world, entityhuman, i, j, k, l);
        }
    }
    
    public int getDropData(final int i) {
        return i & 0x3;
    }
    
    public boolean c() {
        return !this.d;
    }
    
    protected ItemStack c_(final int i) {
        return new ItemStack(this.id, 1, i & 0x3);
    }
    
    static {
        a = new String[] { "oak", "spruce", "birch", "jungle" };
        b = new String[][] { { "leaves", "leaves_spruce", "leaves", "leaves_jungle" }, { "leaves_opaque", "leaves_spruce_opaque", "leaves_opaque", "leaves_jungle_opaque" } };
    }
}
