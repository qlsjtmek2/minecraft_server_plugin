// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import java.util.Random;

public class BlockIce extends BlockHalfTransparant
{
    public BlockIce(final int i) {
        super(i, "ice", Material.ICE, false);
        this.frictionFactor = 0.98f;
        this.b(true);
        this.a(CreativeModeTab.b);
    }
    
    public void a(final World world, final EntityHuman entityhuman, final int i, final int j, final int k, final int l) {
        entityhuman.a(StatisticList.C[this.id], 1);
        entityhuman.j(0.025f);
        if (this.r_() && EnchantmentManager.hasSilkTouchEnchantment(entityhuman)) {
            final ItemStack itemstack = this.c_(l);
            if (itemstack != null) {
                this.b(world, i, j, k, itemstack);
            }
        }
        else {
            if (world.worldProvider.e) {
                world.setAir(i, j, k);
                return;
            }
            final int i2 = EnchantmentManager.getBonusBlockLootEnchantmentLevel(entityhuman);
            this.c(world, i, j, k, l, i2);
            final Material material = world.getMaterial(i, j - 1, k);
            if (material.isSolid() || material.isLiquid()) {
                world.setTypeIdUpdate(i, j, k, Block.WATER.id);
            }
        }
    }
    
    public int a(final Random random) {
        return 0;
    }
    
    public void a(final World world, final int i, final int j, final int k, final Random random) {
        if (world.b(EnumSkyBlock.BLOCK, i, j, k) > 11 - Block.lightBlock[this.id]) {
            if (CraftEventFactory.callBlockFadeEvent(world.getWorld().getBlockAt(i, j, k), Block.STATIONARY_WATER.id).isCancelled()) {
                return;
            }
            if (world.worldProvider.e) {
                world.setAir(i, j, k);
                return;
            }
            this.c(world, i, j, k, world.getData(i, j, k), 0);
            world.setTypeIdUpdate(i, j, k, Block.STATIONARY_WATER.id);
        }
    }
    
    public int h() {
        return 0;
    }
}
