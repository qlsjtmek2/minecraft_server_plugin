// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import java.util.Random;

public class BlockVine extends Block
{
    public BlockVine(final int i) {
        super(i, Material.REPLACEABLE_PLANT);
        this.b(true);
        this.a(CreativeModeTab.c);
    }
    
    public void g() {
        this.a(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public int d() {
        return 20;
    }
    
    public boolean c() {
        return false;
    }
    
    public boolean b() {
        return false;
    }
    
    public void updateShape(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        final int l = iblockaccess.getData(i, j, k);
        float f = 1.0f;
        float f2 = 1.0f;
        float f3 = 1.0f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        boolean flag = l > 0;
        if ((l & 0x2) != 0x0) {
            f4 = Math.max(f4, 0.0625f);
            f = 0.0f;
            f2 = 0.0f;
            f5 = 1.0f;
            f3 = 0.0f;
            f6 = 1.0f;
            flag = true;
        }
        if ((l & 0x8) != 0x0) {
            f = Math.min(f, 0.9375f);
            f4 = 1.0f;
            f2 = 0.0f;
            f5 = 1.0f;
            f3 = 0.0f;
            f6 = 1.0f;
            flag = true;
        }
        if ((l & 0x4) != 0x0) {
            f6 = Math.max(f6, 0.0625f);
            f3 = 0.0f;
            f = 0.0f;
            f4 = 1.0f;
            f2 = 0.0f;
            f5 = 1.0f;
            flag = true;
        }
        if ((l & 0x1) != 0x0) {
            f3 = Math.min(f3, 0.9375f);
            f6 = 1.0f;
            f = 0.0f;
            f4 = 1.0f;
            f2 = 0.0f;
            f5 = 1.0f;
            flag = true;
        }
        if (!flag && this.d(iblockaccess.getTypeId(i, j + 1, k))) {
            f2 = Math.min(f2, 0.9375f);
            f5 = 1.0f;
            f = 0.0f;
            f4 = 1.0f;
            f3 = 0.0f;
            f6 = 1.0f;
        }
        this.a(f, f2, f3, f4, f5, f6);
    }
    
    public AxisAlignedBB b(final World world, final int i, final int j, final int k) {
        return null;
    }
    
    public boolean canPlace(final World world, final int i, final int j, final int k, final int l) {
        switch (l) {
            case 1: {
                return this.d(world.getTypeId(i, j + 1, k));
            }
            case 2: {
                return this.d(world.getTypeId(i, j, k + 1));
            }
            case 3: {
                return this.d(world.getTypeId(i, j, k - 1));
            }
            case 4: {
                return this.d(world.getTypeId(i + 1, j, k));
            }
            case 5: {
                return this.d(world.getTypeId(i - 1, j, k));
            }
            default: {
                return false;
            }
        }
    }
    
    private boolean d(final int i) {
        if (i == 0) {
            return false;
        }
        final Block block = Block.byId[i];
        return block.b() && block.material.isSolid();
    }
    
    private boolean k(final World world, final int i, final int j, final int k) {
        int i2;
        final int l = i2 = world.getData(i, j, k);
        if (l > 0) {
            for (int j2 = 0; j2 <= 3; ++j2) {
                final int k2 = 1 << j2;
                if ((l & k2) != 0x0 && !this.d(world.getTypeId(i + Direction.a[j2], j, k + Direction.b[j2])) && (world.getTypeId(i, j + 1, k) != this.id || (world.getData(i, j + 1, k) & k2) == 0x0)) {
                    i2 &= ~k2;
                }
            }
        }
        if (i2 == 0 && !this.d(world.getTypeId(i, j + 1, k))) {
            return false;
        }
        if (i2 != l) {
            world.setData(i, j, k, i2, 2);
        }
        return true;
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        if (!world.isStatic && !this.k(world, i, j, k)) {
            this.c(world, i, j, k, world.getData(i, j, k), 0);
            world.setAir(i, j, k);
        }
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
        if (!world.isStatic && world.random.nextInt(4) == 0) {
            final byte b0 = 4;
            int l = 5;
            boolean flag = false;
        Label_0121:
            for (int i2 = i - b0; i2 <= i + b0; ++i2) {
                for (int j2 = k - b0; j2 <= k + b0; ++j2) {
                    for (int k2 = j - 1; k2 <= j + 1; ++k2) {
                        if (world.getTypeId(i2, k2, j2) == this.id && --l <= 0) {
                            flag = true;
                            break Label_0121;
                        }
                    }
                }
            }
            int i2 = world.getData(i, j, k);
            int j2 = world.random.nextInt(6);
            int k2 = Direction.e[j2];
            if (j2 == 1 && j < 255 && world.isEmpty(i, j + 1, k)) {
                if (flag) {
                    return;
                }
                int l2 = world.random.nextInt(16) & i2;
                if (l2 > 0) {
                    for (int i3 = 0; i3 <= 3; ++i3) {
                        if (!this.d(world.getTypeId(i + Direction.a[i3], j + 1, k + Direction.b[i3]))) {
                            l2 &= ~(1 << i3);
                        }
                    }
                    if (l2 > 0) {
                        final org.bukkit.block.Block source = world.getWorld().getBlockAt(i, j, k);
                        final org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j + 1, k);
                        CraftEventFactory.handleBlockSpreadEvent(block, source, this.id, l2);
                    }
                }
            }
            else if (j2 >= 2 && j2 <= 5 && (i2 & 1 << k2) == 0x0) {
                if (flag) {
                    return;
                }
                final int l2 = world.getTypeId(i + Direction.a[k2], j, k + Direction.b[k2]);
                if (l2 != 0 && Block.byId[l2] != null) {
                    if (Block.byId[l2].material.k() && Block.byId[l2].b()) {
                        world.setData(i, j, k, i2 | 1 << k2, 2);
                    }
                }
                else {
                    final int i3 = k2 + 1 & 0x3;
                    final int j3 = k2 + 3 & 0x3;
                    final org.bukkit.block.Block source2 = world.getWorld().getBlockAt(i, j, k);
                    org.bukkit.block.Block block2 = world.getWorld().getBlockAt(i + Direction.a[k2], j, k + Direction.b[k2]);
                    if ((i2 & 1 << i3) != 0x0 && this.d(world.getTypeId(i + Direction.a[k2] + Direction.a[i3], j, k + Direction.b[k2] + Direction.b[i3]))) {
                        CraftEventFactory.handleBlockSpreadEvent(block2, source2, this.id, 1 << i3);
                    }
                    else if ((i2 & 1 << j3) != 0x0 && this.d(world.getTypeId(i + Direction.a[k2] + Direction.a[j3], j, k + Direction.b[k2] + Direction.b[j3]))) {
                        CraftEventFactory.handleBlockSpreadEvent(block2, source2, this.id, 1 << j3);
                    }
                    else if ((i2 & 1 << i3) != 0x0 && world.isEmpty(i + Direction.a[k2] + Direction.a[i3], j, k + Direction.b[k2] + Direction.b[i3]) && this.d(world.getTypeId(i + Direction.a[i3], j, k + Direction.b[i3]))) {
                        block2 = world.getWorld().getBlockAt(i + Direction.a[k2] + Direction.a[i3], j, k + Direction.b[k2] + Direction.b[i3]);
                        CraftEventFactory.handleBlockSpreadEvent(block2, source2, this.id, 1 << (k2 + 2 & 0x3));
                    }
                    else if ((i2 & 1 << j3) != 0x0 && world.isEmpty(i + Direction.a[k2] + Direction.a[j3], j, k + Direction.b[k2] + Direction.b[j3]) && this.d(world.getTypeId(i + Direction.a[j3], j, k + Direction.b[j3]))) {
                        block2 = world.getWorld().getBlockAt(i + Direction.a[k2] + Direction.a[j3], j, k + Direction.b[k2] + Direction.b[j3]);
                        CraftEventFactory.handleBlockSpreadEvent(block2, source2, this.id, 1 << (k2 + 2 & 0x3));
                    }
                    else if (this.d(world.getTypeId(i + Direction.a[k2], j + 1, k + Direction.b[k2]))) {
                        CraftEventFactory.handleBlockSpreadEvent(block2, source2, this.id, 0);
                    }
                }
            }
            else if (j > 1) {
                final int l2 = world.getTypeId(i, j - 1, k);
                if (l2 == 0) {
                    final int i3 = world.random.nextInt(16) & i2;
                    if (i3 > 0) {
                        final org.bukkit.block.Block source2 = world.getWorld().getBlockAt(i, j, k);
                        final org.bukkit.block.Block block2 = world.getWorld().getBlockAt(i, j - 1, k);
                        CraftEventFactory.handleBlockSpreadEvent(block2, source2, this.id, i3);
                    }
                }
                else if (l2 == this.id) {
                    final int i3 = world.random.nextInt(16) & i2;
                    final int j3 = world.getData(i, j - 1, k);
                    if (j3 != (j3 | i3)) {
                        world.setData(i, j - 1, k, j3 | i3, 2);
                    }
                }
            }
        }
    }
    
    public int getPlacedData(final World world, final int i, final int j, final int k, final int l, final float f, final float f1, final float f2, final int i1) {
        byte b0 = 0;
        switch (l) {
            case 2: {
                b0 = 1;
                break;
            }
            case 3: {
                b0 = 4;
                break;
            }
            case 4: {
                b0 = 8;
                break;
            }
            case 5: {
                b0 = 2;
                break;
            }
        }
        return (b0 != 0) ? b0 : i1;
    }
    
    public int getDropType(final int i, final Random random, final int j) {
        return 0;
    }
    
    public int a(final Random random) {
        return 0;
    }
    
    public void a(final World world, final EntityHuman entityhuman, final int i, final int j, final int k, final int l) {
        if (!world.isStatic && entityhuman.cd() != null && entityhuman.cd().id == Item.SHEARS.id) {
            entityhuman.a(StatisticList.C[this.id], 1);
            this.b(world, i, j, k, new ItemStack(Block.VINE, 1, 0));
        }
        else {
            super.a(world, entityhuman, i, j, k, l);
        }
    }
}
