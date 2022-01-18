// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.Event;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.craftbukkit.v1_5_R3.util.BlockStateListPopulator;

public class BlockPumpkin extends BlockDirectional
{
    private boolean a;
    
    protected BlockPumpkin(final int i, final boolean flag) {
        super(i, Material.PUMPKIN);
        this.b(true);
        this.a = flag;
        this.a(CreativeModeTab.b);
    }
    
    public void onPlace(final World world, final int i, final int j, final int k) {
        super.onPlace(world, i, j, k);
        if (world.getTypeId(i, j - 1, k) == Block.SNOW_BLOCK.id && world.getTypeId(i, j - 2, k) == Block.SNOW_BLOCK.id) {
            if (!world.isStatic) {
                final BlockStateListPopulator blockList = new BlockStateListPopulator(world.getWorld());
                blockList.setTypeId(i, j, k, 0);
                blockList.setTypeId(i, j - 1, k, 0);
                blockList.setTypeId(i, j - 2, k, 0);
                final EntitySnowman entitysnowman = new EntitySnowman(world);
                entitysnowman.setPositionRotation(i + 0.5, j - 1.95, k + 0.5, 0.0f, 0.0f);
                if (world.addEntity(entitysnowman, CreatureSpawnEvent.SpawnReason.BUILD_SNOWMAN)) {
                    blockList.updateList();
                }
            }
            for (int l = 0; l < 120; ++l) {
                world.addParticle("snowshovel", i + world.random.nextDouble(), j - 2 + world.random.nextDouble() * 2.5, k + world.random.nextDouble(), 0.0, 0.0, 0.0);
            }
        }
        else if (world.getTypeId(i, j - 1, k) == Block.IRON_BLOCK.id && world.getTypeId(i, j - 2, k) == Block.IRON_BLOCK.id) {
            final boolean flag = world.getTypeId(i - 1, j - 1, k) == Block.IRON_BLOCK.id && world.getTypeId(i + 1, j - 1, k) == Block.IRON_BLOCK.id;
            final boolean flag2 = world.getTypeId(i, j - 1, k - 1) == Block.IRON_BLOCK.id && world.getTypeId(i, j - 1, k + 1) == Block.IRON_BLOCK.id;
            if (flag || flag2) {
                final BlockStateListPopulator blockList2 = new BlockStateListPopulator(world.getWorld());
                blockList2.setTypeId(i, j, k, 0);
                blockList2.setTypeId(i, j - 1, k, 0);
                blockList2.setTypeId(i, j - 2, k, 0);
                if (flag) {
                    blockList2.setTypeId(i - 1, j - 1, k, 0);
                    blockList2.setTypeId(i + 1, j - 1, k, 0);
                }
                else {
                    blockList2.setTypeId(i, j - 1, k - 1, 0);
                    blockList2.setTypeId(i, j - 1, k + 1, 0);
                }
                final EntityIronGolem entityirongolem = new EntityIronGolem(world);
                entityirongolem.setPlayerCreated(true);
                entityirongolem.setPositionRotation(i + 0.5, j - 1.95, k + 0.5, 0.0f, 0.0f);
                if (world.addEntity(entityirongolem, CreatureSpawnEvent.SpawnReason.BUILD_IRONGOLEM)) {
                    for (int i2 = 0; i2 < 120; ++i2) {
                        world.addParticle("snowballpoof", i + world.random.nextDouble(), j - 2 + world.random.nextDouble() * 3.9, k + world.random.nextDouble(), 0.0, 0.0, 0.0);
                    }
                    blockList2.updateList();
                }
            }
        }
    }
    
    public boolean canPlace(final World world, final int i, final int j, final int k) {
        final int l = world.getTypeId(i, j, k);
        return (l == 0 || Block.byId[l].material.isReplaceable()) && world.w(i, j - 1, k);
    }
    
    public void postPlace(final World world, final int i, final int j, final int k, final EntityLiving entityliving, final ItemStack itemstack) {
        final int l = MathHelper.floor(entityliving.yaw * 4.0f / 360.0f + 2.5) & 0x3;
        world.setData(i, j, k, l, 2);
    }
    
    public void doPhysics(final World world, final int i, final int j, final int k, final int l) {
        if (Block.byId[l] != null && Block.byId[l].isPowerSource()) {
            final org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
            final int power = block.getBlockPower();
            final BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, power, power);
            world.getServer().getPluginManager().callEvent(eventRedstone);
        }
    }
}
