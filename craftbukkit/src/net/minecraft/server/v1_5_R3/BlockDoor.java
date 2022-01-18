// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockDoor extends Block
{
    private static final String[] a;
    private final int b;
    
    protected BlockDoor(final int i, final Material material) {
        super(i, material);
        if (material == Material.ORE) {
            this.b = 2;
        }
        else {
            this.b = 0;
        }
        final float f = 0.5f;
        final float f2 = 1.0f;
        this.a(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, f2, 0.5f + f);
    }
    
    public boolean c() {
        return false;
    }
    
    public boolean b(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        final int l = this.c_(iblockaccess, i, j, k);
        return (l & 0x4) != 0x0;
    }
    
    public boolean b() {
        return false;
    }
    
    public int d() {
        return 7;
    }
    
    public AxisAlignedBB b(final World world, final int i, final int j, final int k) {
        this.updateShape(world, i, j, k);
        return super.b(world, i, j, k);
    }
    
    public void updateShape(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        this.d(this.c_(iblockaccess, i, j, k));
    }
    
    public int d(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        return this.c_(iblockaccess, i, j, k) & 0x3;
    }
    
    public boolean b_(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        return (this.c_(iblockaccess, i, j, k) & 0x4) != 0x0;
    }
    
    private void d(final int i) {
        final float f = 0.1875f;
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 2.0f, 1.0f);
        final int j = i & 0x3;
        final boolean flag = (i & 0x4) != 0x0;
        final boolean flag2 = (i & 0x10) != 0x0;
        if (j == 0) {
            if (flag) {
                if (!flag2) {
                    this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, f);
                }
                else {
                    this.a(0.0f, 0.0f, 1.0f - f, 1.0f, 1.0f, 1.0f);
                }
            }
            else {
                this.a(0.0f, 0.0f, 0.0f, f, 1.0f, 1.0f);
            }
        }
        else if (j == 1) {
            if (flag) {
                if (!flag2) {
                    this.a(1.0f - f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                }
                else {
                    this.a(0.0f, 0.0f, 0.0f, f, 1.0f, 1.0f);
                }
            }
            else {
                this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, f);
            }
        }
        else if (j == 2) {
            if (flag) {
                if (!flag2) {
                    this.a(0.0f, 0.0f, 1.0f - f, 1.0f, 1.0f, 1.0f);
                }
                else {
                    this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, f);
                }
            }
            else {
                this.a(1.0f - f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            }
        }
        else if (j == 3) {
            if (flag) {
                if (!flag2) {
                    this.a(0.0f, 0.0f, 0.0f, f, 1.0f, 1.0f);
                }
                else {
                    this.a(1.0f - f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                }
            }
            else {
                this.a(0.0f, 0.0f, 1.0f - f, 1.0f, 1.0f, 1.0f);
            }
        }
    }
    
    public void attack(final World world, final int i, final int j, final int k, final EntityHuman entityhuman) {
    }
    
    public boolean interact(final World world, final int i, final int j, final int k, final EntityHuman entityhuman, final int l, final float f, final float f1, final float f2) {
        if (this.material == Material.ORE) {
            return true;
        }
        final int i2 = this.c_(world, i, j, k);
        int j2 = i2 & 0x7;
        j2 ^= 0x4;
        if ((i2 & 0x8) == 0x0) {
            world.setData(i, j, k, j2, 2);
            world.g(i, j, k, i, j, k);
        }
        else {
            world.setData(i, j - 1, k, j2, 2);
            world.g(i, j - 1, k, i, j, k);
        }
        world.a(entityhuman, 1003, i, j, k, 0);
        return true;
    }
    
    public void setDoor(final World world, final int i, final int j, final int k, final boolean flag) {
        final int l = this.c_(world, i, j, k);
        final boolean flag2 = (l & 0x4) != 0x0;
        if (flag2 != flag) {
            int i2 = l & 0x7;
            i2 ^= 0x4;
            if ((l & 0x8) == 0x0) {
                world.setData(i, j, k, i2, 2);
                world.g(i, j, k, i, j, k);
            }
            else {
                world.setData(i, j - 1, k, i2, 2);
                world.g(i, j - 1, k, i, j, k);
            }
            world.a(null, 1003, i, j, k, 0);
        }
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        final int i2 = world.getData(i, j, k);
        if ((i2 & 0x8) == 0x0) {
            boolean flag = false;
            if (world.getTypeId(i, j + 1, k) != this.id) {
                world.setAir(i, j, k);
                flag = true;
            }
            if (!world.w(i, j - 1, k)) {
                world.setAir(i, j, k);
                flag = true;
                if (world.getTypeId(i, j + 1, k) == this.id) {
                    world.setAir(i, j + 1, k);
                }
            }
            if (flag) {
                if (!world.isStatic) {
                    this.c(world, i, j, k, i2, 0);
                }
            }
            else if (l > 0 && Block.byId[l].isPowerSource()) {
                final org.bukkit.World bworld = world.getWorld();
                final org.bukkit.block.Block block = bworld.getBlockAt(i, j, k);
                final org.bukkit.block.Block blockTop = bworld.getBlockAt(i, j + 1, k);
                int power = block.getBlockPower();
                final int powerTop = blockTop.getBlockPower();
                if (powerTop > power) {
                    power = powerTop;
                }
                final int oldPower = ((world.getData(i, j, k) & 0x4) > 0) ? 15 : 0;
                if (oldPower == 0 ^ power == 0) {
                    final BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, oldPower, power);
                    world.getServer().getPluginManager().callEvent(eventRedstone);
                    this.setDoor(world, i, j, k, eventRedstone.getNewCurrent() > 0);
                }
            }
        }
        else if (world.getTypeId(i, j - 1, k) != this.id) {
            world.setAir(i, j, k);
        }
        else if (l > 0 && l != this.id) {
            this.doPhysics(world, i, j - 1, k, l);
        }
    }
    
    public int getDropType(final int i, final Random random, final int j) {
        return ((i & 0x8) != 0x0) ? 0 : ((this.material == Material.ORE) ? Item.IRON_DOOR.id : Item.WOOD_DOOR.id);
    }
    
    public MovingObjectPosition a(final World world, final int i, final int j, final int k, final Vec3D vec3d, final Vec3D vec3d1) {
        this.updateShape(world, i, j, k);
        return super.a(world, i, j, k, vec3d, vec3d1);
    }
    
    public boolean canPlace(final World world, final int i, final int j, final int k) {
        return j < 255 && (world.w(i, j - 1, k) && super.canPlace(world, i, j, k) && super.canPlace(world, i, j + 1, k));
    }
    
    public int h() {
        return 1;
    }
    
    public int c_(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        final int l = iblockaccess.getData(i, j, k);
        final boolean flag = (l & 0x8) != 0x0;
        int i2;
        int j2;
        if (flag) {
            i2 = iblockaccess.getData(i, j - 1, k);
            j2 = l;
        }
        else {
            i2 = l;
            j2 = iblockaccess.getData(i, j + 1, k);
        }
        final boolean flag2 = (j2 & 0x1) != 0x0;
        return (i2 & 0x7) | (flag ? 8 : 0) | (flag2 ? 16 : 0);
    }
    
    public void a(final World world, final int i, final int j, final int k, final int l, final EntityHuman entityhuman) {
        if (entityhuman.abilities.canInstantlyBuild && (l & 0x8) != 0x0 && world.getTypeId(i, j - 1, k) == this.id) {
            world.setAir(i, j - 1, k);
        }
    }
    
    static {
        a = new String[] { "doorWood_lower", "doorWood_upper", "doorIron_lower", "doorIron_upper" };
    }
}
