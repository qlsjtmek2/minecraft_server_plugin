// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import org.bukkit.block.BlockState;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.Location;
import org.bukkit.BlockChangeDelegate;
import org.bukkit.TreeType;
import org.bukkit.craftbukkit.v1_5_R3.util.StructureGrowDelegate;
import org.bukkit.entity.Player;
import java.util.Random;

public class BlockSapling extends BlockFlower
{
    public static final String[] a;
    private static final String[] b;
    
    protected BlockSapling(final int i) {
        super(i);
        final float f = 0.4f;
        this.a(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, f * 2.0f, 0.5f + f);
        this.a(CreativeModeTab.c);
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
        if (!world.isStatic) {
            super.a(world, i, j, k, random);
            if (world.getLightLevel(i, j + 1, k) >= 9 && random.nextInt(Math.max(2, (int)(world.growthOdds / world.getWorld().aggregateTicks / world.getWorld().treeGrowthModifier * 7.0f + 0.5f))) == 0) {
                this.grow(world, i, j, k, random, false, null, null);
            }
        }
    }
    
    public void grow(final World world, final int i, final int j, final int k, final Random random, final boolean bonemeal, final Player player, final ItemStack itemstack) {
        final int l = world.getData(i, j, k);
        if ((l & 0x8) == 0x0) {
            world.setData(i, j, k, l | 0x8, 4);
        }
        else {
            this.d(world, i, j, k, random, bonemeal, player, itemstack);
        }
    }
    
    public void d(final World world, final int i, final int j, final int k, final Random random, final boolean bonemeal, final Player player, final ItemStack itemstack) {
        final int l = world.getData(i, j, k) & 0x3;
        final Object object = null;
        int i2 = 0;
        int j2 = 0;
        boolean flag = false;
        final StructureGrowDelegate delegate = new StructureGrowDelegate(world);
        TreeType treeType = null;
        TreeGenerator gen = null;
        boolean grownTree = false;
        if (l == 1) {
            treeType = TreeType.REDWOOD;
            gen = new WorldGenTaiga2(false);
        }
        else if (l == 2) {
            treeType = TreeType.BIRCH;
            gen = new WorldGenForest(false);
        }
        else if (l == 3) {
            for (i2 = 0; i2 >= -1; --i2) {
                for (j2 = 0; j2 >= -1; --j2) {
                    if (this.d(world, i + i2, j, k + j2, 3) && this.d(world, i + i2 + 1, j, k + j2, 3) && this.d(world, i + i2, j, k + j2 + 1, 3) && this.d(world, i + i2 + 1, j, k + j2 + 1, 3)) {
                        treeType = TreeType.JUNGLE;
                        gen = new WorldGenMegaTree(false, 10 + random.nextInt(20), 3, 3);
                        flag = true;
                        break;
                    }
                }
                if (gen != null) {
                    break;
                }
            }
            if (gen == null) {
                j2 = 0;
                i2 = 0;
                treeType = TreeType.SMALL_JUNGLE;
                gen = new WorldGenTrees(false, 4 + random.nextInt(7), 3, 3, false);
            }
        }
        else {
            treeType = TreeType.TREE;
            gen = new WorldGenTrees(false);
            if (random.nextInt(10) == 0) {
                treeType = TreeType.BIG_TREE;
                gen = new WorldGenBigTree(false);
            }
        }
        if (flag) {
            world.setTypeIdAndData(i + i2, j, k + j2, 0, 0, 4);
            world.setTypeIdAndData(i + i2 + 1, j, k + j2, 0, 0, 4);
            world.setTypeIdAndData(i + i2, j, k + j2 + 1, 0, 0, 4);
            world.setTypeIdAndData(i + i2 + 1, j, k + j2 + 1, 0, 0, 4);
        }
        else {
            world.setTypeIdAndData(i, j, k, 0, 0, 4);
        }
        grownTree = gen.generate(delegate, random, i + i2, j, k + j2);
        if (grownTree) {
            final Location location = new Location(world.getWorld(), i, j, k);
            final StructureGrowEvent event = new StructureGrowEvent(location, treeType, bonemeal, player, delegate.getBlocks());
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                grownTree = false;
            }
            else {
                for (final BlockState state : event.getBlocks()) {
                    state.update(true);
                }
                if (event.isFromBonemeal() && itemstack != null) {
                    --itemstack.count;
                }
            }
        }
        else if (bonemeal && itemstack != null) {
            --itemstack.count;
        }
        if (!grownTree) {
            if (flag) {
                world.setTypeIdAndData(i + i2, j, k + j2, this.id, l, 4);
                world.setTypeIdAndData(i + i2 + 1, j, k + j2, this.id, l, 4);
                world.setTypeIdAndData(i + i2, j, k + j2 + 1, this.id, l, 4);
                world.setTypeIdAndData(i + i2 + 1, j, k + j2 + 1, this.id, l, 4);
            }
            else {
                world.setTypeIdAndData(i, j, k, this.id, l, 4);
            }
        }
    }
    
    public boolean d(final World world, final int i, final int j, final int k, final int l) {
        return world.getTypeId(i, j, k) == this.id && (world.getData(i, j, k) & 0x3) == l;
    }
    
    public int getDropData(final int i) {
        return i & 0x3;
    }
    
    static {
        a = new String[] { "oak", "spruce", "birch", "jungle" };
        b = new String[] { "sapling", "sapling_spruce", "sapling_birch", "sapling_jungle" };
    }
    
    public interface TreeGenerator
    {
        boolean a(final World p0, final Random p1, final int p2, final int p3, final int p4);
        
        boolean generate(final BlockChangeDelegate p0, final Random p1, final int p2, final int p3, final int p4);
    }
}
