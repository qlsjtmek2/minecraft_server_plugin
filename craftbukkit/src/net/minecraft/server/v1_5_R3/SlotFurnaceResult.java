// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SlotFurnaceResult extends Slot
{
    private EntityHuman a;
    private int b;
    
    public SlotFurnaceResult(final EntityHuman entityhuman, final IInventory iinventory, final int i, final int j, final int k) {
        super(iinventory, i, j, k);
        this.a = entityhuman;
    }
    
    public boolean isAllowed(final ItemStack itemstack) {
        return false;
    }
    
    public ItemStack a(final int i) {
        if (this.d()) {
            this.b += Math.min(i, this.getItem().count);
        }
        return super.a(i);
    }
    
    public void a(final EntityHuman entityhuman, final ItemStack itemstack) {
        this.b(itemstack);
        super.a(entityhuman, itemstack);
    }
    
    protected void a(final ItemStack itemstack, final int i) {
        this.b += i;
        this.b(itemstack);
    }
    
    protected void b(final ItemStack itemstack) {
        itemstack.a(this.a.world, this.a, this.b);
        if (!this.a.world.isStatic) {
            int i = this.b;
            final float f = RecipesFurnace.getInstance().c(itemstack.id);
            if (f == 0.0f) {
                i = 0;
            }
            else if (f < 1.0f) {
                int j = MathHelper.d(i * f);
                if (j < MathHelper.f(i * f) && (float)Math.random() < i * f - j) {
                    ++j;
                }
                i = j;
            }
            final Player player = (Player)this.a.getBukkitEntity();
            final TileEntityFurnace furnace = (TileEntityFurnace)this.inventory;
            final Block block = this.a.world.getWorld().getBlockAt(furnace.x, furnace.y, furnace.z);
            final FurnaceExtractEvent event = new FurnaceExtractEvent(player, block, Material.getMaterial(itemstack.id), itemstack.count, i);
            this.a.world.getServer().getPluginManager().callEvent(event);
            i = event.getExpToDrop();
            while (i > 0) {
                final int j = EntityExperienceOrb.getOrbValue(i);
                i -= j;
                this.a.world.addEntity(new EntityExperienceOrb(this.a.world, this.a.locX, this.a.locY + 0.5, this.a.locZ + 0.5, j));
            }
        }
        this.b = 0;
        if (itemstack.id == Item.IRON_INGOT.id) {
            this.a.a(AchievementList.k, 1);
        }
        if (itemstack.id == Item.COOKED_FISH.id) {
            this.a.a(AchievementList.p, 1);
        }
    }
}
