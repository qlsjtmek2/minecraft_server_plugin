// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.block.BlockFace;
import java.util.Random;

public class BlockFlowing extends BlockFluids
{
    int a;
    boolean[] b;
    int[] c;
    
    protected BlockFlowing(final int i, final Material material) {
        super(i, material);
        this.a = 0;
        this.b = new boolean[4];
        this.c = new int[4];
    }
    
    private void k(final World world, final int i, final int j, final int k) {
        final int l = world.getData(i, j, k);
        world.setTypeIdAndData(i, j, k, this.id + 1, l, 2);
    }
    
    public boolean b(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        return this.material != Material.LAVA;
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
        final org.bukkit.World bworld = world.getWorld();
        final Server server = world.getServer();
        final org.bukkit.block.Block source = (bworld == null) ? null : bworld.getBlockAt(i, j, k);
        int l = this.k_(world, i, j, k);
        byte b0 = 1;
        if (this.material == Material.LAVA && !world.worldProvider.e) {
            b0 = 2;
        }
        boolean flag = true;
        if (l > 0) {
            final byte b2 = -100;
            this.a = 0;
            int j2 = this.d(world, i - 1, j, k, b2);
            j2 = this.d(world, i + 1, j, k, j2);
            j2 = this.d(world, i, j, k - 1, j2);
            j2 = this.d(world, i, j, k + 1, j2);
            int i2 = j2 + b0;
            if (i2 >= 8 || j2 < 0) {
                i2 = -1;
            }
            if (this.k_(world, i, j + 1, k) >= 0) {
                final int k2 = this.k_(world, i, j + 1, k);
                if (k2 >= 8) {
                    i2 = k2;
                }
                else {
                    i2 = k2 + 8;
                }
            }
            if (this.a >= 2 && this.material == Material.WATER) {
                if (world.getMaterial(i, j - 1, k).isBuildable()) {
                    i2 = 0;
                }
                else if (world.getMaterial(i, j - 1, k) == this.material && world.getData(i, j - 1, k) == 0) {
                    i2 = 0;
                }
            }
            if (this.material == Material.LAVA && l < 8 && i2 < 8 && i2 > l && random.nextInt(4) != 0) {
                i2 = l;
                flag = false;
            }
            if (i2 == l) {
                if (flag) {
                    this.k(world, i, j, k);
                }
            }
            else if ((l = i2) < 0) {
                world.setAir(i, j, k);
            }
            else {
                world.setData(i, j, k, i2, 2);
                world.a(i, j, k, this.id, this.a(world));
                world.applyPhysics(i, j, k, this.id);
            }
        }
        else {
            this.k(world, i, j, k);
        }
        if (this.o(world, i, j - 1, k)) {
            final BlockFromToEvent event = new BlockFromToEvent(source, BlockFace.DOWN);
            if (server != null) {
                server.getPluginManager().callEvent(event);
            }
            if (!event.isCancelled()) {
                if (this.material == Material.LAVA && world.getMaterial(i, j - 1, k) == Material.WATER) {
                    world.setTypeIdUpdate(i, j - 1, k, Block.STONE.id);
                    this.fizz(world, i, j - 1, k);
                    return;
                }
                if (l >= 8) {
                    this.flow(world, i, j - 1, k, l);
                }
                else {
                    this.flow(world, i, j - 1, k, l + 8);
                }
            }
        }
        else if (l >= 0 && (l == 0 || this.n(world, i, j - 1, k))) {
            final boolean[] aboolean = this.m(world, i, j, k);
            int i2 = l + b0;
            if (l >= 8) {
                i2 = 1;
            }
            if (i2 >= 8) {
                return;
            }
            final BlockFace[] faces = { BlockFace.WEST, BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH };
            int index = 0;
            for (final BlockFace currentFace : faces) {
                if (aboolean[index]) {
                    final BlockFromToEvent event2 = new BlockFromToEvent(source, currentFace);
                    if (server != null) {
                        server.getPluginManager().callEvent(event2);
                    }
                    if (!event2.isCancelled()) {
                        this.flow(world, i + currentFace.getModX(), j, k + currentFace.getModZ(), i2);
                    }
                }
                ++index;
            }
        }
    }
    
    private void flow(final World world, final int i, final int j, final int k, final int l) {
        if (this.o(world, i, j, k)) {
            final int i2 = world.getTypeId(i, j, k);
            if (i2 > 0) {
                if (this.material == Material.LAVA) {
                    this.fizz(world, i, j, k);
                }
                else {
                    Block.byId[i2].c(world, i, j, k, world.getData(i, j, k), 0);
                }
            }
            world.setTypeIdAndData(i, j, k, this.id, l, 3);
        }
    }
    
    private int d(final World world, final int i, final int j, final int k, final int l, final int i1) {
        int j2 = 1000;
        for (int k2 = 0; k2 < 4; ++k2) {
            if ((k2 != 0 || i1 != 1) && (k2 != 1 || i1 != 0) && (k2 != 2 || i1 != 3) && (k2 != 3 || i1 != 2)) {
                int l2 = i;
                int i2 = k;
                if (k2 == 0) {
                    l2 = i - 1;
                }
                if (k2 == 1) {
                    ++l2;
                }
                if (k2 == 2) {
                    i2 = k - 1;
                }
                if (k2 == 3) {
                    ++i2;
                }
                if (!this.n(world, l2, j, i2) && (world.getMaterial(l2, j, i2) != this.material || world.getData(l2, j, i2) != 0)) {
                    if (!this.n(world, l2, j - 1, i2)) {
                        return l;
                    }
                    if (l < 4) {
                        final int j3 = this.d(world, l2, j, i2, l + 1, k2);
                        if (j3 < j2) {
                            j2 = j3;
                        }
                    }
                }
            }
        }
        return j2;
    }
    
    private boolean[] m(final World world, final int i, final int j, final int k) {
        for (int l = 0; l < 4; ++l) {
            this.c[l] = 1000;
            int i2 = i;
            int j2 = k;
            if (l == 0) {
                i2 = i - 1;
            }
            if (l == 1) {
                ++i2;
            }
            if (l == 2) {
                j2 = k - 1;
            }
            if (l == 3) {
                ++j2;
            }
            if (!this.n(world, i2, j, j2) && (world.getMaterial(i2, j, j2) != this.material || world.getData(i2, j, j2) != 0)) {
                if (this.n(world, i2, j - 1, j2)) {
                    this.c[l] = this.d(world, i2, j, j2, 1, l);
                }
                else {
                    this.c[l] = 0;
                }
            }
        }
        int l = this.c[0];
        for (int i2 = 1; i2 < 4; ++i2) {
            if (this.c[i2] < l) {
                l = this.c[i2];
            }
        }
        for (int i2 = 0; i2 < 4; ++i2) {
            this.b[i2] = (this.c[i2] == l);
        }
        return this.b;
    }
    
    private boolean n(final World world, final int i, final int j, final int k) {
        final int l = world.getTypeId(i, j, k);
        if (l == Block.WOODEN_DOOR.id || l == Block.IRON_DOOR_BLOCK.id || l == Block.SIGN_POST.id || l == Block.LADDER.id || l == Block.SUGAR_CANE_BLOCK.id) {
            return true;
        }
        if (l == 0) {
            return false;
        }
        final Material material = Block.byId[l].material;
        return material == Material.PORTAL || material.isSolid();
    }
    
    protected int d(final World world, final int i, final int j, final int k, final int l) {
        int i2 = this.k_(world, i, j, k);
        if (i2 < 0) {
            return l;
        }
        if (i2 == 0) {
            ++this.a;
        }
        if (i2 >= 8) {
            i2 = 0;
        }
        return (l >= 0 && i2 >= l) ? l : i2;
    }
    
    private boolean o(final World world, final int i, final int j, final int k) {
        final Material material = world.getMaterial(i, j, k);
        return material != this.material && material != Material.LAVA && !this.n(world, i, j, k);
    }
    
    public void onPlace(final World world, final int i, final int j, final int k) {
        super.onPlace(world, i, j, k);
        if (world.getTypeId(i, j, k) == this.id) {
            world.a(i, j, k, this.id, this.a(world));
        }
    }
    
    public boolean l() {
        return false;
    }
}
