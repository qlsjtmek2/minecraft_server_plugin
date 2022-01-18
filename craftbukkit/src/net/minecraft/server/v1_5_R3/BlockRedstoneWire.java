// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockRedstoneEvent;
import java.util.Collection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BlockRedstoneWire extends Block
{
    private boolean a;
    private Set b;
    
    public BlockRedstoneWire(final int i) {
        super(i, Material.ORIENTABLE);
        this.a = true;
        this.b = new HashSet();
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);
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
        return 5;
    }
    
    public boolean canPlace(final World world, final int i, final int j, final int k) {
        return world.w(i, j - 1, k) || world.getTypeId(i, j - 1, k) == Block.GLOWSTONE.id;
    }
    
    private void k(final World world, final int i, final int j, final int k) {
        this.a(world, i, j, k, i, j, k);
        final ArrayList arraylist = new ArrayList(this.b);
        this.b.clear();
        for (int l = 0; l < arraylist.size(); ++l) {
            final ChunkPosition chunkposition = arraylist.get(l);
            world.applyPhysics(chunkposition.x, chunkposition.y, chunkposition.z, this.id);
        }
    }
    
    private void a(final World world, final int i, final int j, final int k, final int l, final int i1, final int j1) {
        final int k2 = world.getData(i, j, k);
        final byte b0 = 0;
        int l2 = this.getPower(world, l, i1, j1, b0);
        this.a = false;
        final int i2 = world.getHighestNeighborSignal(i, j, k);
        this.a = true;
        if (i2 > 0 && i2 > l2 - 1) {
            l2 = i2;
        }
        int j2 = 0;
        for (int k3 = 0; k3 < 4; ++k3) {
            int l3 = i;
            int i3 = k;
            if (k3 == 0) {
                l3 = i - 1;
            }
            if (k3 == 1) {
                ++l3;
            }
            if (k3 == 2) {
                i3 = k - 1;
            }
            if (k3 == 3) {
                ++i3;
            }
            if (l3 != l || i3 != j1) {
                j2 = this.getPower(world, l3, j, i3, j2);
            }
            if (world.u(l3, j, i3) && !world.u(i, j + 1, k)) {
                if ((l3 != l || i3 != j1) && j >= i1) {
                    j2 = this.getPower(world, l3, j + 1, i3, j2);
                }
            }
            else if (!world.u(l3, j, i3) && (l3 != l || i3 != j1) && j <= i1) {
                j2 = this.getPower(world, l3, j - 1, i3, j2);
            }
        }
        if (j2 > l2) {
            l2 = j2 - 1;
        }
        else if (l2 > 0) {
            --l2;
        }
        else {
            l2 = 0;
        }
        if (i2 > l2 - 1) {
            l2 = i2;
        }
        if (k2 != l2) {
            final BlockRedstoneEvent event = new BlockRedstoneEvent(world.getWorld().getBlockAt(i, j, k), k2, l2);
            world.getServer().getPluginManager().callEvent(event);
            l2 = event.getNewCurrent();
        }
        if (k2 != l2) {
            world.setData(i, j, k, l2, 2);
            this.b.add(new ChunkPosition(i, j, k));
            this.b.add(new ChunkPosition(i - 1, j, k));
            this.b.add(new ChunkPosition(i + 1, j, k));
            this.b.add(new ChunkPosition(i, j - 1, k));
            this.b.add(new ChunkPosition(i, j + 1, k));
            this.b.add(new ChunkPosition(i, j, k - 1));
            this.b.add(new ChunkPosition(i, j, k + 1));
        }
    }
    
    private void m(final World world, final int i, final int j, final int k) {
        if (world.getTypeId(i, j, k) == this.id) {
            world.applyPhysics(i, j, k, this.id);
            world.applyPhysics(i - 1, j, k, this.id);
            world.applyPhysics(i + 1, j, k, this.id);
            world.applyPhysics(i, j, k - 1, this.id);
            world.applyPhysics(i, j, k + 1, this.id);
            world.applyPhysics(i, j - 1, k, this.id);
            world.applyPhysics(i, j + 1, k, this.id);
        }
    }
    
    public void onPlace(final World world, final int i, final int j, final int k) {
        super.onPlace(world, i, j, k);
        if (!world.isStatic) {
            this.k(world, i, j, k);
            world.applyPhysics(i, j + 1, k, this.id);
            world.applyPhysics(i, j - 1, k, this.id);
            this.m(world, i - 1, j, k);
            this.m(world, i + 1, j, k);
            this.m(world, i, j, k - 1);
            this.m(world, i, j, k + 1);
            if (world.u(i - 1, j, k)) {
                this.m(world, i - 1, j + 1, k);
            }
            else {
                this.m(world, i - 1, j - 1, k);
            }
            if (world.u(i + 1, j, k)) {
                this.m(world, i + 1, j + 1, k);
            }
            else {
                this.m(world, i + 1, j - 1, k);
            }
            if (world.u(i, j, k - 1)) {
                this.m(world, i, j + 1, k - 1);
            }
            else {
                this.m(world, i, j - 1, k - 1);
            }
            if (world.u(i, j, k + 1)) {
                this.m(world, i, j + 1, k + 1);
            }
            else {
                this.m(world, i, j - 1, k + 1);
            }
        }
    }
    
    public void remove(final World world, final int i, final int j, final int k, final int l, final int i1) {
        super.remove(world, i, j, k, l, i1);
        if (!world.isStatic) {
            world.applyPhysics(i, j + 1, k, this.id);
            world.applyPhysics(i, j - 1, k, this.id);
            world.applyPhysics(i + 1, j, k, this.id);
            world.applyPhysics(i - 1, j, k, this.id);
            world.applyPhysics(i, j, k + 1, this.id);
            world.applyPhysics(i, j, k - 1, this.id);
            this.k(world, i, j, k);
            this.m(world, i - 1, j, k);
            this.m(world, i + 1, j, k);
            this.m(world, i, j, k - 1);
            this.m(world, i, j, k + 1);
            if (world.u(i - 1, j, k)) {
                this.m(world, i - 1, j + 1, k);
            }
            else {
                this.m(world, i - 1, j - 1, k);
            }
            if (world.u(i + 1, j, k)) {
                this.m(world, i + 1, j + 1, k);
            }
            else {
                this.m(world, i + 1, j - 1, k);
            }
            if (world.u(i, j, k - 1)) {
                this.m(world, i, j + 1, k - 1);
            }
            else {
                this.m(world, i, j - 1, k - 1);
            }
            if (world.u(i, j, k + 1)) {
                this.m(world, i, j + 1, k + 1);
            }
            else {
                this.m(world, i, j - 1, k + 1);
            }
        }
    }
    
    public int getPower(final World world, final int i, final int j, final int k, final int l) {
        if (world.getTypeId(i, j, k) != this.id) {
            return l;
        }
        final int i2 = world.getData(i, j, k);
        return (i2 > l) ? i2 : l;
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        if (!world.isStatic) {
            final boolean flag = this.canPlace(world, i, j, k);
            if (flag) {
                this.k(world, i, j, k);
            }
            else {
                this.c(world, i, j, k, 0, 0);
                world.setAir(i, j, k);
            }
            super.doPhysics(world, i, j, k, l);
        }
    }
    
    public int getDropType(final int i, final Random random, final int j) {
        return Item.REDSTONE.id;
    }
    
    public int c(final IBlockAccess iblockaccess, final int i, final int j, final int k, final int l) {
        return this.a ? this.b(iblockaccess, i, j, k, l) : 0;
    }
    
    public int b(final IBlockAccess iblockaccess, final int i, final int j, final int k, final int l) {
        if (!this.a) {
            return 0;
        }
        final int i2 = iblockaccess.getData(i, j, k);
        if (i2 == 0) {
            return 0;
        }
        if (l == 1) {
            return i2;
        }
        boolean flag = g(iblockaccess, i - 1, j, k, 1) || (!iblockaccess.u(i - 1, j, k) && g(iblockaccess, i - 1, j - 1, k, -1));
        boolean flag2 = g(iblockaccess, i + 1, j, k, 3) || (!iblockaccess.u(i + 1, j, k) && g(iblockaccess, i + 1, j - 1, k, -1));
        boolean flag3 = g(iblockaccess, i, j, k - 1, 2) || (!iblockaccess.u(i, j, k - 1) && g(iblockaccess, i, j - 1, k - 1, -1));
        boolean flag4 = g(iblockaccess, i, j, k + 1, 0) || (!iblockaccess.u(i, j, k + 1) && g(iblockaccess, i, j - 1, k + 1, -1));
        if (!iblockaccess.u(i, j + 1, k)) {
            if (iblockaccess.u(i - 1, j, k) && g(iblockaccess, i - 1, j + 1, k, -1)) {
                flag = true;
            }
            if (iblockaccess.u(i + 1, j, k) && g(iblockaccess, i + 1, j + 1, k, -1)) {
                flag2 = true;
            }
            if (iblockaccess.u(i, j, k - 1) && g(iblockaccess, i, j + 1, k - 1, -1)) {
                flag3 = true;
            }
            if (iblockaccess.u(i, j, k + 1) && g(iblockaccess, i, j + 1, k + 1, -1)) {
                flag4 = true;
            }
        }
        return (!flag3 && !flag2 && !flag && !flag4 && l >= 2 && l <= 5) ? i2 : ((l == 2 && flag3 && !flag && !flag2) ? i2 : ((l == 3 && flag4 && !flag && !flag2) ? i2 : ((l == 4 && flag && !flag3 && !flag4) ? i2 : ((l == 5 && flag2 && !flag3 && !flag4) ? i2 : 0))));
    }
    
    public boolean isPowerSource() {
        return this.a;
    }
    
    public static boolean f(final IBlockAccess iblockaccess, final int i, final int j, final int k, final int l) {
        final int i2 = iblockaccess.getTypeId(i, j, k);
        if (i2 == Block.REDSTONE_WIRE.id) {
            return true;
        }
        if (i2 == 0) {
            return false;
        }
        if (!Block.DIODE_OFF.g(i2)) {
            return Block.byId[i2].isPowerSource() && l != -1;
        }
        final int j2 = iblockaccess.getData(i, j, k);
        return l == (j2 & 0x3) || l == Direction.f[j2 & 0x3];
    }
    
    public static boolean g(final IBlockAccess iblockaccess, final int i, final int j, final int k, final int l) {
        if (f(iblockaccess, i, j, k, l)) {
            return true;
        }
        final int i2 = iblockaccess.getTypeId(i, j, k);
        if (i2 == Block.DIODE_ON.id) {
            final int j2 = iblockaccess.getData(i, j, k);
            return l == (j2 & 0x3);
        }
        return false;
    }
}
