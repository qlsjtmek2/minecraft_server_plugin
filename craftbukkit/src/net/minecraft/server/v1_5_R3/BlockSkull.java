// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.craftbukkit.v1_5_R3.util.BlockStateListPopulator;
import java.util.Random;

public class BlockSkull extends BlockContainer
{
    protected BlockSkull(final int i) {
        super(i, Material.ORIENTABLE);
        this.a(0.25f, 0.0f, 0.25f, 0.75f, 0.5f, 0.75f);
    }
    
    public int d() {
        return -1;
    }
    
    public boolean c() {
        return false;
    }
    
    public boolean b() {
        return false;
    }
    
    public void updateShape(final IBlockAccess iblockaccess, final int i, final int j, final int k) {
        final int l = iblockaccess.getData(i, j, k) & 0x7;
        switch (l) {
            default: {
                this.a(0.25f, 0.0f, 0.25f, 0.75f, 0.5f, 0.75f);
                break;
            }
            case 2: {
                this.a(0.25f, 0.25f, 0.5f, 0.75f, 0.75f, 1.0f);
                break;
            }
            case 3: {
                this.a(0.25f, 0.25f, 0.0f, 0.75f, 0.75f, 0.5f);
                break;
            }
            case 4: {
                this.a(0.5f, 0.25f, 0.25f, 1.0f, 0.75f, 0.75f);
                break;
            }
            case 5: {
                this.a(0.0f, 0.25f, 0.25f, 0.5f, 0.75f, 0.75f);
                break;
            }
        }
    }
    
    public AxisAlignedBB b(final World world, final int i, final int j, final int k) {
        this.updateShape(world, i, j, k);
        return super.b(world, i, j, k);
    }
    
    public void postPlace(final World world, final int i, final int j, final int k, final EntityLiving entityliving, final ItemStack itemstack) {
        final int l = MathHelper.floor(entityliving.yaw * 4.0f / 360.0f + 2.5) & 0x3;
        world.setData(i, j, k, l, 2);
    }
    
    public TileEntity b(final World world) {
        return new TileEntitySkull();
    }
    
    public int getDropData(final World world, final int i, final int j, final int k) {
        final TileEntity tileentity = world.getTileEntity(i, j, k);
        return (tileentity != null && tileentity instanceof TileEntitySkull) ? ((TileEntitySkull)tileentity).getSkullType() : super.getDropData(world, i, j, k);
    }
    
    public int getDropData(final int i) {
        return i;
    }
    
    public void dropNaturally(final World world, final int i, final int j, final int k, final int l, final float f, final int i1) {
        if (world.random.nextFloat() < f) {
            final ItemStack itemstack = new ItemStack(Item.SKULL.id, 1, this.getDropData(world, i, j, k));
            final TileEntitySkull tileentityskull = (TileEntitySkull)world.getTileEntity(i, j, k);
            if (tileentityskull.getSkullType() == 3 && tileentityskull.getExtraType() != null && tileentityskull.getExtraType().length() > 0) {
                itemstack.setTag(new NBTTagCompound());
                itemstack.getTag().setString("SkullOwner", tileentityskull.getExtraType());
            }
            this.b(world, i, j, k, itemstack);
        }
    }
    
    public void a(final World world, final int i, final int j, final int k, int l, final EntityHuman entityhuman) {
        if (entityhuman.abilities.canInstantlyBuild) {
            l |= 0x8;
            world.setData(i, j, k, l, 4);
        }
        super.a(world, i, j, k, l, entityhuman);
    }
    
    public void remove(final World world, final int i, final int j, final int k, final int l, final int i1) {
        if (!world.isStatic) {
            super.remove(world, i, j, k, l, i1);
        }
    }
    
    public int getDropType(final int i, final Random random, final int j) {
        return Item.SKULL.id;
    }
    
    public void a(final World world, final int i, final int j, final int k, final TileEntitySkull tileentityskull) {
        if (tileentityskull.getSkullType() == 1 && j >= 2 && world.difficulty > 0 && !world.isStatic) {
            final int l = Block.SOUL_SAND.id;
            for (int i2 = -2; i2 <= 0; ++i2) {
                if (world.getTypeId(i, j - 1, k + i2) == l && world.getTypeId(i, j - 1, k + i2 + 1) == l && world.getTypeId(i, j - 2, k + i2 + 1) == l && world.getTypeId(i, j - 1, k + i2 + 2) == l && this.d(world, i, j, k + i2, 1) && this.d(world, i, j, k + i2 + 1, 1) && this.d(world, i, j, k + i2 + 2, 1)) {
                    final BlockStateListPopulator blockList = new BlockStateListPopulator(world.getWorld());
                    world.setData(i, j, k + i2, 8, 2);
                    world.setData(i, j, k + i2 + 1, 8, 2);
                    world.setData(i, j, k + i2 + 2, 8, 2);
                    blockList.setTypeId(i, j, k + i2, 0);
                    blockList.setTypeId(i, j, k + i2 + 1, 0);
                    blockList.setTypeId(i, j, k + i2 + 2, 0);
                    blockList.setTypeId(i, j - 1, k + i2, 0);
                    blockList.setTypeId(i, j - 1, k + i2 + 1, 0);
                    blockList.setTypeId(i, j - 1, k + i2 + 2, 0);
                    blockList.setTypeId(i, j - 2, k + i2 + 1, 0);
                    if (!world.isStatic) {
                        final EntityWither entitywither = new EntityWither(world);
                        entitywither.setPositionRotation(i + 0.5, j - 1.45, k + i2 + 1.5, 90.0f, 0.0f);
                        entitywither.ay = 90.0f;
                        entitywither.m();
                        if (world.addEntity(entitywither, CreatureSpawnEvent.SpawnReason.BUILD_WITHER)) {
                            blockList.updateList();
                        }
                    }
                    for (int j2 = 0; j2 < 120; ++j2) {
                        world.addParticle("snowballpoof", i + world.random.nextDouble(), j - 2 + world.random.nextDouble() * 3.9, k + i2 + 1 + world.random.nextDouble(), 0.0, 0.0, 0.0);
                    }
                    return;
                }
            }
            for (int i2 = -2; i2 <= 0; ++i2) {
                if (world.getTypeId(i + i2, j - 1, k) == l && world.getTypeId(i + i2 + 1, j - 1, k) == l && world.getTypeId(i + i2 + 1, j - 2, k) == l && world.getTypeId(i + i2 + 2, j - 1, k) == l && this.d(world, i + i2, j, k, 1) && this.d(world, i + i2 + 1, j, k, 1) && this.d(world, i + i2 + 2, j, k, 1)) {
                    final BlockStateListPopulator blockList = new BlockStateListPopulator(world.getWorld());
                    world.setData(i + i2, j, k, 8, 2);
                    world.setData(i + i2 + 1, j, k, 8, 2);
                    world.setData(i + i2 + 2, j, k, 8, 2);
                    blockList.setTypeId(i + i2, j, k, 0);
                    blockList.setTypeId(i + i2 + 1, j, k, 0);
                    blockList.setTypeId(i + i2 + 2, j, k, 0);
                    blockList.setTypeId(i + i2, j - 1, k, 0);
                    blockList.setTypeId(i + i2 + 1, j - 1, k, 0);
                    blockList.setTypeId(i + i2 + 2, j - 1, k, 0);
                    blockList.setTypeId(i + i2 + 1, j - 2, k, 0);
                    if (!world.isStatic) {
                        final EntityWither entitywither = new EntityWither(world);
                        entitywither.setPositionRotation(i + i2 + 1.5, j - 1.45, k + 0.5, 0.0f, 0.0f);
                        entitywither.m();
                        if (world.addEntity(entitywither, CreatureSpawnEvent.SpawnReason.BUILD_WITHER)) {
                            blockList.updateList();
                        }
                    }
                    for (int j2 = 0; j2 < 120; ++j2) {
                        world.addParticle("snowballpoof", i + i2 + 1 + world.random.nextDouble(), j - 2 + world.random.nextDouble() * 3.9, k + world.random.nextDouble(), 0.0, 0.0, 0.0);
                    }
                    return;
                }
            }
        }
    }
    
    private boolean d(final World world, final int i, final int j, final int k, final int l) {
        if (world.getTypeId(i, j, k) != this.id) {
            return false;
        }
        final TileEntity tileentity = world.getTileEntity(i, j, k);
        return tileentity != null && tileentity instanceof TileEntitySkull && ((TileEntitySkull)tileentity).getSkullType() == l;
    }
}
