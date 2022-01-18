// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.block.BlockState;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockFadeEvent;
import java.util.Random;

public class BlockMycel extends Block
{
    protected BlockMycel(final int i) {
        super(i, Material.GRASS);
        this.b(true);
        this.a(CreativeModeTab.b);
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
        if (!world.isStatic) {
            if (world.getLightLevel(i, j + 1, k) < 4 && Block.lightBlock[world.getTypeId(i, j + 1, k)] > 2) {
                final org.bukkit.World bworld = world.getWorld();
                final BlockState blockState = bworld.getBlockAt(i, j, k).getState();
                blockState.setTypeId(Block.DIRT.id);
                final BlockFadeEvent event = new BlockFadeEvent(blockState.getBlock(), blockState);
                world.getServer().getPluginManager().callEvent(event);
                if (!event.isCancelled()) {
                    blockState.update(true);
                }
            }
            else if (world.getLightLevel(i, j + 1, k) >= 9) {
                for (int numGrowth = Math.min(4, Math.max(20, (int)(400.0f / world.growthOdds))), l = 0; l < numGrowth; ++l) {
                    final int i2 = i + random.nextInt(3) - 1;
                    final int j2 = j + random.nextInt(5) - 3;
                    final int k2 = k + random.nextInt(3) - 1;
                    final int l2 = world.getTypeId(i2, j2 + 1, k2);
                    if (world.getTypeId(i2, j2, k2) == Block.DIRT.id && world.getLightLevel(i2, j2 + 1, k2) >= 4 && Block.lightBlock[l2] <= 2) {
                        final org.bukkit.World bworld2 = world.getWorld();
                        final BlockState blockState2 = bworld2.getBlockAt(i2, j2, k2).getState();
                        blockState2.setTypeId(this.id);
                        final BlockSpreadEvent event2 = new BlockSpreadEvent(blockState2.getBlock(), bworld2.getBlockAt(i, j, k), blockState2);
                        world.getServer().getPluginManager().callEvent(event2);
                        if (!event2.isCancelled()) {
                            blockState2.update(true);
                        }
                    }
                }
            }
        }
    }
    
    public int getDropType(final int i, final Random random, final int j) {
        return Block.DIRT.getDropType(0, random, j);
    }
}
