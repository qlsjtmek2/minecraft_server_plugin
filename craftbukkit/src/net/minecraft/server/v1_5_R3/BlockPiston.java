// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.craftbukkit.v1_5_R3.block.CraftBlock;

public class BlockPiston extends Block
{
    private final boolean a;
    
    public BlockPiston(final int i, final boolean flag) {
        super(i, Material.PISTON);
        this.a = flag;
        this.a(BlockPiston.j);
        this.c(0.5f);
        this.a(CreativeModeTab.d);
    }
    
    public int d() {
        return 16;
    }
    
    public boolean c() {
        return false;
    }
    
    public boolean interact(final World world, final int i, final int j, final int k, final EntityHuman entityhuman, final int l, final float f, final float f1, final float f2) {
        return false;
    }
    
    public void postPlace(final World world, final int i, final int j, final int k, final EntityLiving entityliving, final ItemStack itemstack) {
        final int l = a(world, i, j, k, entityliving);
        world.setData(i, j, k, l, 2);
        if (!world.isStatic) {
            this.k(world, i, j, k);
        }
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        if (!world.isStatic) {
            this.k(world, i, j, k);
        }
    }
    
    public void onPlace(final World world, final int i, final int j, final int k) {
        if (!world.isStatic && world.getTileEntity(i, j, k) == null) {
            this.k(world, i, j, k);
        }
    }
    
    private void k(final World world, final int i, final int j, final int k) {
        final int l = world.getData(i, j, k);
        final int i2 = d(l);
        if (i2 != 7) {
            final boolean flag = this.d(world, i, j, k, i2);
            if (flag && !e(l)) {
                final int length = e(world, i, j, k, i2);
                if (length >= 0) {
                    final org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
                    final BlockPistonExtendEvent event = new BlockPistonExtendEvent(block, length, CraftBlock.notchToBlockFace(i2));
                    world.getServer().getPluginManager().callEvent(event);
                    if (event.isCancelled()) {
                        return;
                    }
                    world.playNote(i, j, k, this.id, 0, i2);
                }
            }
            else if (!flag && e(l)) {
                final org.bukkit.block.Block block2 = world.getWorld().getBlockAt(i, j, k);
                final BlockPistonRetractEvent event2 = new BlockPistonRetractEvent(block2, CraftBlock.notchToBlockFace(i2));
                world.getServer().getPluginManager().callEvent(event2);
                if (event2.isCancelled()) {
                    return;
                }
                world.setData(i, j, k, i2, 2);
                world.playNote(i, j, k, this.id, 1, i2);
            }
        }
    }
    
    private boolean d(final World world, final int i, final int j, final int k, final int l) {
        return (l != 0 && world.isBlockFacePowered(i, j - 1, k, 0)) || (l != 1 && world.isBlockFacePowered(i, j + 1, k, 1)) || (l != 2 && world.isBlockFacePowered(i, j, k - 1, 2)) || (l != 3 && world.isBlockFacePowered(i, j, k + 1, 3)) || (l != 5 && world.isBlockFacePowered(i + 1, j, k, 5)) || (l != 4 && world.isBlockFacePowered(i - 1, j, k, 4)) || world.isBlockFacePowered(i, j, k, 0) || world.isBlockFacePowered(i, j + 2, k, 1) || world.isBlockFacePowered(i, j + 1, k - 1, 2) || world.isBlockFacePowered(i, j + 1, k + 1, 3) || world.isBlockFacePowered(i - 1, j + 1, k, 4) || world.isBlockFacePowered(i + 1, j + 1, k, 5);
    }
    
    public boolean b(final World world, int i, int j, int k, final int l, final int i1) {
        if (!world.isStatic) {
            final boolean flag = this.d(world, i, j, k, i1);
            if (flag && l == 1) {
                world.setData(i, j, k, i1 | 0x8, 2);
                return false;
            }
            if (!flag && l == 0) {
                return false;
            }
        }
        if (l == 0) {
            if (!this.f(world, i, j, k, i1)) {
                return false;
            }
            world.setData(i, j, k, i1 | 0x8, 2);
            world.makeSound(i + 0.5, j + 0.5, k + 0.5, "tile.piston.out", 0.5f, world.random.nextFloat() * 0.25f + 0.6f);
        }
        else if (l == 1) {
            final TileEntity tileentity = world.getTileEntity(i + Facing.b[i1], j + Facing.c[i1], k + Facing.d[i1]);
            if (tileentity instanceof TileEntityPiston) {
                ((TileEntityPiston)tileentity).f();
            }
            world.setTypeIdAndData(i, j, k, Block.PISTON_MOVING.id, i1, 3);
            world.setTileEntity(i, j, k, BlockPistonMoving.a(this.id, i1, i1, false, true));
            if (this.a) {
                final int j2 = i + Facing.b[i1] * 2;
                final int k2 = j + Facing.c[i1] * 2;
                final int l2 = k + Facing.d[i1] * 2;
                int i2 = world.getTypeId(j2, k2, l2);
                int j3 = world.getData(j2, k2, l2);
                boolean flag2 = false;
                if (i2 == Block.PISTON_MOVING.id) {
                    final TileEntity tileentity2 = world.getTileEntity(j2, k2, l2);
                    if (tileentity2 instanceof TileEntityPiston) {
                        final TileEntityPiston tileentitypiston = (TileEntityPiston)tileentity2;
                        if (tileentitypiston.c() == i1 && tileentitypiston.b()) {
                            tileentitypiston.f();
                            i2 = tileentitypiston.a();
                            j3 = tileentitypiston.p();
                            flag2 = true;
                        }
                    }
                }
                if (!flag2 && i2 > 0 && a(i2, world, j2, k2, l2, false) && (Block.byId[i2].h() == 0 || i2 == Block.PISTON.id || i2 == Block.PISTON_STICKY.id)) {
                    i += Facing.b[i1];
                    j += Facing.c[i1];
                    k += Facing.d[i1];
                    world.setTypeIdAndData(i, j, k, Block.PISTON_MOVING.id, j3, 3);
                    world.setTileEntity(i, j, k, BlockPistonMoving.a(i2, j3, i1, false, false));
                    world.setAir(j2, k2, l2);
                }
                else if (!flag2) {
                    world.setAir(i + Facing.b[i1], j + Facing.c[i1], k + Facing.d[i1]);
                }
            }
            else {
                world.setAir(i + Facing.b[i1], j + Facing.c[i1], k + Facing.d[i1]);
            }
            world.makeSound(i + 0.5, j + 0.5, k + 0.5, "tile.piston.in", 0.5f, world.random.nextFloat() * 0.15f + 0.6f);
        }
        return true;
    }
    
    public void updateShape(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        final int l = iblockaccess.getData(i, j, k);
        if (e(l)) {
            switch (d(l)) {
                case 0: {
                    this.a(0.0f, 0.25f, 0.0f, 1.0f, 1.0f, 1.0f);
                    break;
                }
                case 1: {
                    this.a(0.0f, 0.0f, 0.0f, 1.0f, 0.75f, 1.0f);
                    break;
                }
                case 2: {
                    this.a(0.0f, 0.0f, 0.25f, 1.0f, 1.0f, 1.0f);
                    break;
                }
                case 3: {
                    this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.75f);
                    break;
                }
                case 4: {
                    this.a(0.25f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                    break;
                }
                case 5: {
                    this.a(0.0f, 0.0f, 0.0f, 0.75f, 1.0f, 1.0f);
                    break;
                }
            }
        }
        else {
            this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    public void g() {
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public void a(final World world, final int i, final int j, final int k, final AxisAlignedBB axisalignedbb, final List list, final Entity entity) {
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        super.a(world, i, j, k, axisalignedbb, list, entity);
    }
    
    public AxisAlignedBB b(final World world, final int i, final int j, final int k) {
        this.updateShape(world, i, j, k);
        return super.b(world, i, j, k);
    }
    
    public boolean b() {
        return false;
    }
    
    public static int d(final int i) {
        if ((i & 0x7) >= Facing.OPPOSITE_FACING.length) {
            return 7;
        }
        return i & 0x7;
    }
    
    public static boolean e(final int i) {
        return (i & 0x8) != 0x0;
    }
    
    public static int a(final World world, final int i, final int j, final int k, final EntityLiving entityliving) {
        if (MathHelper.abs((float)entityliving.locX - i) < 2.0f && MathHelper.abs((float)entityliving.locZ - k) < 2.0f) {
            final double d0 = entityliving.locY + 1.82 - entityliving.height;
            if (d0 - j > 2.0) {
                return 1;
            }
            if (j - d0 > 0.0) {
                return 0;
            }
        }
        final int l = MathHelper.floor(entityliving.yaw * 4.0f / 360.0f + 0.5) & 0x3;
        return (l == 0) ? 2 : ((l == 1) ? 5 : ((l == 2) ? 3 : ((l == 3) ? 4 : 0)));
    }
    
    private static boolean a(final int i, final World world, final int j, final int k, final int l, final boolean flag) {
        if (i == Block.OBSIDIAN.id) {
            return false;
        }
        if (i != Block.PISTON.id && i != Block.PISTON_STICKY.id) {
            if (Block.byId[i].l(world, j, k, l) == -1.0f) {
                return false;
            }
            if (Block.byId[i].h() == 2) {
                return false;
            }
            if (Block.byId[i].h() == 1) {
                return flag;
            }
        }
        else if (e(world.getData(j, k, l))) {
            return false;
        }
        return !(Block.byId[i] instanceof IContainer);
    }
    
    private static int e(final World world, final int i, final int j, final int k, final int l) {
        int i2 = i + Facing.b[l];
        int j2 = j + Facing.c[l];
        int k2 = k + Facing.d[l];
        int l2;
        for (l2 = 0; l2 < 13; ++l2) {
            if (j2 <= 0 || j2 >= 255) {
                return -1;
            }
            final int i3 = world.getTypeId(i2, j2, k2);
            if (i3 == 0) {
                break;
            }
            if (!a(i3, world, i2, j2, k2, true)) {
                return -1;
            }
            if (Block.byId[i3].h() == 1) {
                break;
            }
            if (l2 == 12) {
                return -1;
            }
            i2 += Facing.b[l];
            j2 += Facing.c[l];
            k2 += Facing.d[l];
        }
        return l2;
    }
    
    private boolean f(final World world, final int i, final int j, final int k, final int l) {
        int i2 = i + Facing.b[l];
        int j2 = j + Facing.c[l];
        int k2 = k + Facing.d[l];
        for (int l2 = 0; l2 < 13; ++l2) {
            if (j2 <= 0 || j2 >= 255) {
                return false;
            }
            final int i3 = world.getTypeId(i2, j2, k2);
            if (i3 == 0) {
                break;
            }
            if (!a(i3, world, i2, j2, k2, true)) {
                return false;
            }
            if (Block.byId[i3].h() == 1) {
                Block.byId[i3].c(world, i2, j2, k2, world.getData(i2, j2, k2), 0);
                world.setAir(i2, j2, k2);
                break;
            }
            if (l2 == 12) {
                return false;
            }
            i2 += Facing.b[l];
            j2 += Facing.c[l];
            k2 += Facing.d[l];
        }
        int l2 = i2;
        final int i3 = j2;
        final int j3 = k2;
        int k3 = 0;
        final int[] aint = new int[13];
        while (i2 != i || j2 != j || k2 != k) {
            final int l3 = i2 - Facing.b[l];
            final int i4 = j2 - Facing.c[l];
            final int j4 = k2 - Facing.d[l];
            final int k4 = world.getTypeId(l3, i4, j4);
            final int l4 = world.getData(l3, i4, j4);
            if (k4 == this.id && l3 == i && i4 == j && j4 == k) {
                world.setTypeIdAndData(i2, j2, k2, Block.PISTON_MOVING.id, l | (this.a ? 8 : 0), 4);
                world.setTileEntity(i2, j2, k2, BlockPistonMoving.a(Block.PISTON_EXTENSION.id, l | (this.a ? 8 : 0), l, true, false));
            }
            else {
                world.setTypeIdAndData(i2, j2, k2, Block.PISTON_MOVING.id, l4, 4);
                world.setTileEntity(i2, j2, k2, BlockPistonMoving.a(k4, l4, l, true, false));
            }
            aint[k3++] = k4;
            i2 = l3;
            j2 = i4;
            k2 = j4;
        }
        i2 = l2;
        j2 = i3;
        k2 = j3;
        k3 = 0;
        while (i2 != i || j2 != j || k2 != k) {
            final int l3 = i2 - Facing.b[l];
            final int i4 = j2 - Facing.c[l];
            final int j4 = k2 - Facing.d[l];
            world.applyPhysics(l3, i4, j4, aint[k3++]);
            i2 = l3;
            j2 = i4;
            k2 = j4;
        }
        return true;
    }
}
