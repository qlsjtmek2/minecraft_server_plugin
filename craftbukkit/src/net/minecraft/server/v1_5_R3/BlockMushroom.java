// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.BlockChangeDelegate;
import java.util.List;
import org.bukkit.event.world.StructureGrowEvent;
import java.util.ArrayList;
import org.bukkit.TreeType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.block.BlockState;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockSpreadEvent;
import java.util.Random;

public class BlockMushroom extends BlockFlower
{
    private final String a;
    
    protected BlockMushroom(final int i, final String s) {
        super(i);
        this.a = s;
        final float f = 0.2f;
        this.a(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, f * 2.0f, 0.5f + f);
        this.b(true);
    }
    
    public void a(final World world, int i, int j, int k, final Random random) {
        final int sourceX = i;
        final int sourceY = j;
        final int sourceZ = k;
        if (random.nextInt(Math.max(1, (int)world.growthOdds / world.getWorld().mushroomGrowthModifier * 25)) == 0) {
            final byte b0 = 4;
            int l = 5;
            for (int i2 = i - b0; i2 <= i + b0; ++i2) {
                for (int j2 = k - b0; j2 <= k + b0; ++j2) {
                    for (int k2 = j - 1; k2 <= j + 1; ++k2) {
                        if (world.getTypeId(i2, k2, j2) == this.id && --l <= 0) {
                            return;
                        }
                    }
                }
            }
            int i2 = i + random.nextInt(3) - 1;
            int j2 = j + random.nextInt(2) - random.nextInt(2);
            int k2 = k + random.nextInt(3) - 1;
            for (int l2 = 0; l2 < 4; ++l2) {
                if (world.isEmpty(i2, j2, k2) && this.f(world, i2, j2, k2)) {
                    i = i2;
                    j = j2;
                    k = k2;
                }
                i2 = i + random.nextInt(3) - 1;
                j2 = j + random.nextInt(2) - random.nextInt(2);
                k2 = k + random.nextInt(3) - 1;
            }
            if (world.isEmpty(i2, j2, k2) && this.f(world, i2, j2, k2)) {
                final org.bukkit.World bworld = world.getWorld();
                final BlockState blockState = bworld.getBlockAt(i2, j2, k2).getState();
                blockState.setTypeId(this.id);
                final BlockSpreadEvent event = new BlockSpreadEvent(blockState.getBlock(), bworld.getBlockAt(sourceX, sourceY, sourceZ), blockState);
                world.getServer().getPluginManager().callEvent(event);
                if (!event.isCancelled()) {
                    blockState.update(true);
                }
            }
        }
    }
    
    public boolean canPlace(final World world, final int i, final int j, final int k) {
        return super.canPlace(world, i, j, k) && this.f(world, i, j, k);
    }
    
    protected boolean f_(final int i) {
        return Block.s[i];
    }
    
    public boolean f(final World world, final int i, final int j, final int k) {
        if (j >= 0 && j < 256) {
            final int l = world.getTypeId(i, j - 1, k);
            return l == Block.MYCEL.id || (world.m(i, j, k) < 13 && this.f_(l));
        }
        return false;
    }
    
    public boolean grow(final World world, final int i, final int j, final int k, final Random random, final boolean bonemeal, final Player player, final ItemStack itemstack) {
        final int l = world.getData(i, j, k);
        world.setAir(i, j, k);
        boolean grown = false;
        StructureGrowEvent event = null;
        final Location location = new Location(world.getWorld(), i, j, k);
        WorldGenHugeMushroom worldgenhugemushroom = null;
        if (this.id == Block.BROWN_MUSHROOM.id) {
            event = new StructureGrowEvent(location, TreeType.BROWN_MUSHROOM, bonemeal, player, new ArrayList<BlockState>());
            worldgenhugemushroom = new WorldGenHugeMushroom(0);
        }
        else if (this.id == Block.RED_MUSHROOM.id) {
            event = new StructureGrowEvent(location, TreeType.RED_MUSHROOM, bonemeal, player, new ArrayList<BlockState>());
            worldgenhugemushroom = new WorldGenHugeMushroom(1);
        }
        if (worldgenhugemushroom != null && event != null) {
            grown = worldgenhugemushroom.grow((BlockChangeDelegate)world, random, i, j, k, event, itemstack, world.getWorld());
            if (event.isFromBonemeal() && itemstack != null) {
                --itemstack.count;
            }
        }
        if (!grown || event.isCancelled()) {
            world.setTypeIdAndData(i, j, k, this.id, l, 3);
            return false;
        }
        return true;
    }
}
