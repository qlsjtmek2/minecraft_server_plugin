// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.block.BlockState;
import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import java.util.Random;

public class BlockFire extends Block
{
    private int[] a;
    private int[] b;
    
    protected BlockFire(final int i) {
        super(i, Material.FIRE);
        this.a = new int[256];
        this.b = new int[256];
        this.b(true);
    }
    
    public void s_() {
        this.a(Block.WOOD.id, 5, 20);
        this.a(Block.WOOD_DOUBLE_STEP.id, 5, 20);
        this.a(Block.WOOD_STEP.id, 5, 20);
        this.a(Block.FENCE.id, 5, 20);
        this.a(Block.WOOD_STAIRS.id, 5, 20);
        this.a(Block.BIRCH_WOOD_STAIRS.id, 5, 20);
        this.a(Block.SPRUCE_WOOD_STAIRS.id, 5, 20);
        this.a(Block.JUNGLE_WOOD_STAIRS.id, 5, 20);
        this.a(Block.LOG.id, 5, 5);
        this.a(Block.LEAVES.id, 30, 60);
        this.a(Block.BOOKSHELF.id, 30, 20);
        this.a(Block.TNT.id, 15, 100);
        this.a(Block.LONG_GRASS.id, 60, 100);
        this.a(Block.WOOL.id, 30, 60);
        this.a(Block.VINE.id, 15, 100);
    }
    
    private void a(final int i, final int j, final int k) {
        this.a[i] = j;
        this.b[i] = k;
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
        return 3;
    }
    
    public int a(final Random random) {
        return 0;
    }
    
    public int a(final World world) {
        return 30;
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
        if (world.getGameRules().getBoolean("doFireTick")) {
            boolean flag = world.getTypeId(i, j - 1, k) == Block.NETHERRACK.id;
            if (world.worldProvider instanceof WorldProviderTheEnd && world.getTypeId(i, j - 1, k) == Block.BEDROCK.id) {
                flag = true;
            }
            if (!this.canPlace(world, i, j, k)) {
                this.fireExtinguished(world, i, j, k);
            }
            if (!flag && world.P() && (world.F(i, j, k) || world.F(i - 1, j, k) || world.F(i + 1, j, k) || world.F(i, j, k - 1) || world.F(i, j, k + 1))) {
                this.fireExtinguished(world, i, j, k);
            }
            else {
                final int l = world.getData(i, j, k);
                if (l < 15) {
                    world.setData(i, j, k, l + random.nextInt(3) / 2, 4);
                }
                world.a(i, j, k, this.id, this.a(world) + random.nextInt(10));
                if (!flag && !this.k(world, i, j, k)) {
                    if (!world.w(i, j - 1, k) || l > 3) {
                        world.setAir(i, j, k);
                    }
                }
                else if (!flag && !this.d(world, i, j - 1, k) && l == 15 && random.nextInt(4) == 0) {
                    this.fireExtinguished(world, i, j, k);
                }
                else {
                    final boolean flag2 = world.G(i, j, k);
                    byte b0 = 0;
                    if (flag2) {
                        b0 = -50;
                    }
                    this.a(world, i + 1, j, k, 300 + b0, random, l);
                    this.a(world, i - 1, j, k, 300 + b0, random, l);
                    this.a(world, i, j - 1, k, 250 + b0, random, l);
                    this.a(world, i, j + 1, k, 250 + b0, random, l);
                    this.a(world, i, j, k - 1, 300 + b0, random, l);
                    this.a(world, i, j, k + 1, 300 + b0, random, l);
                    for (int i2 = i - 1; i2 <= i + 1; ++i2) {
                        for (int j2 = k - 1; j2 <= k + 1; ++j2) {
                            for (int k2 = j - 1; k2 <= j + 4; ++k2) {
                                if (i2 != i || k2 != j || j2 != k) {
                                    int l2 = 100;
                                    if (k2 > j + 1) {
                                        l2 += (k2 - (j + 1)) * 100;
                                    }
                                    final int i3 = this.m(world, i2, k2, j2);
                                    if (i3 > 0) {
                                        int j3 = (i3 + 40 + world.difficulty * 7) / (l + 30);
                                        if (flag2) {
                                            j3 /= 2;
                                        }
                                        if (j3 > 0 && random.nextInt(l2) <= j3 && (!world.P() || !world.F(i2, k2, j2)) && !world.F(i2 - 1, k2, k) && !world.F(i2 + 1, k2, j2) && !world.F(i2, k2, j2 - 1) && !world.F(i2, k2, j2 + 1)) {
                                            int k3 = l + random.nextInt(5) / 4;
                                            if (k3 > 15) {
                                                k3 = 15;
                                            }
                                            if (world.getTypeId(i2, k2, j2) != Block.FIRE.id) {
                                                if (!CraftEventFactory.callBlockIgniteEvent(world, i2, k2, j2, i, j, k).isCancelled()) {
                                                    final Server server = world.getServer();
                                                    final org.bukkit.World bworld = world.getWorld();
                                                    final BlockState blockState = bworld.getBlockAt(i2, k2, j2).getState();
                                                    blockState.setTypeId(this.id);
                                                    blockState.setData(new MaterialData(this.id, (byte)k3));
                                                    final BlockSpreadEvent spreadEvent = new BlockSpreadEvent(blockState.getBlock(), bworld.getBlockAt(i, j, k), blockState);
                                                    server.getPluginManager().callEvent(spreadEvent);
                                                    if (!spreadEvent.isCancelled()) {
                                                        blockState.update(true);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public boolean l() {
        return false;
    }
    
    private void a(final World world, final int i, final int j, final int k, final int l, final Random random, final int i1) {
        final int j2 = this.b[world.getTypeId(i, j, k)];
        if (random.nextInt(l) < j2) {
            final boolean flag = world.getTypeId(i, j, k) == Block.TNT.id;
            final org.bukkit.block.Block theBlock = world.getWorld().getBlockAt(i, j, k);
            final BlockBurnEvent event = new BlockBurnEvent(theBlock);
            world.getServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return;
            }
            if (random.nextInt(i1 + 10) < 5 && !world.F(i, j, k)) {
                int k2 = i1 + random.nextInt(5) / 4;
                if (k2 > 15) {
                    k2 = 15;
                }
                world.setTypeIdAndData(i, j, k, this.id, k2, 3);
            }
            else {
                world.setAir(i, j, k);
            }
            if (flag) {
                Block.TNT.postBreak(world, i, j, k, 1);
            }
        }
    }
    
    private boolean k(final World world, final int i, final int j, final int k) {
        return this.d(world, i + 1, j, k) || this.d(world, i - 1, j, k) || this.d(world, i, j - 1, k) || this.d(world, i, j + 1, k) || this.d(world, i, j, k - 1) || this.d(world, i, j, k + 1);
    }
    
    private int m(final World world, final int i, final int j, final int k) {
        final byte b0 = 0;
        if (!world.isEmpty(i, j, k)) {
            return 0;
        }
        int l = this.d(world, i + 1, j, k, b0);
        l = this.d(world, i - 1, j, k, l);
        l = this.d(world, i, j - 1, k, l);
        l = this.d(world, i, j + 1, k, l);
        l = this.d(world, i, j, k - 1, l);
        l = this.d(world, i, j, k + 1, l);
        return l;
    }
    
    public boolean m() {
        return false;
    }
    
    public boolean d(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        return this.a[iblockaccess.getTypeId(i, j, k)] > 0;
    }
    
    public int d(final World world, final int i, final int j, final int k, final int l) {
        final int i2 = this.a[world.getTypeId(i, j, k)];
        return (i2 > l) ? i2 : l;
    }
    
    public boolean canPlace(final World world, final int i, final int j, final int k) {
        return world.w(i, j - 1, k) || this.k(world, i, j, k);
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        if (!world.w(i, j - 1, k) && !this.k(world, i, j, k)) {
            this.fireExtinguished(world, i, j, k);
        }
    }
    
    public void onPlace(final World world, final int i, final int j, final int k) {
        if (world.worldProvider.dimension > 0 || world.getTypeId(i, j - 1, k) != Block.OBSIDIAN.id || !Block.PORTAL.n_(world, i, j, k)) {
            if (!world.w(i, j - 1, k) && !this.k(world, i, j, k)) {
                this.fireExtinguished(world, i, j, k);
            }
            else {
                world.a(i, j, k, this.id, this.a(world) + world.random.nextInt(10));
            }
        }
    }
    
    private void fireExtinguished(final World world, final int x, final int y, final int z) {
        if (!CraftEventFactory.callBlockFadeEvent(world.getWorld().getBlockAt(x, y, z), 0).isCancelled()) {
            world.setAir(x, y, z);
        }
    }
}
