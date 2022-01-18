// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.event.block.Action;

public class ItemMinecart extends Item
{
    private static final IDispenseBehavior b;
    public int a;
    
    public ItemMinecart(final int i, final int j) {
        super(i);
        this.maxStackSize = 1;
        this.a = j;
        this.a(CreativeModeTab.e);
        BlockDispenser.a.a(this, ItemMinecart.b);
    }
    
    public boolean interactWith(final ItemStack itemstack, final EntityHuman entityhuman, final World world, final int i, final int j, final int k, final int l, final float f, final float f1, final float f2) {
        final int i2 = world.getTypeId(i, j, k);
        if (BlockMinecartTrackAbstract.d_(i2)) {
            if (!world.isStatic) {
                final PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(entityhuman, Action.RIGHT_CLICK_BLOCK, i, j, k, l, itemstack);
                if (event.isCancelled()) {
                    return false;
                }
                final EntityMinecartAbstract entityminecartabstract = EntityMinecartAbstract.a(world, i + 0.5f, j + 0.5f, k + 0.5f, this.a);
                if (itemstack.hasName()) {
                    entityminecartabstract.a(itemstack.getName());
                }
                world.addEntity(entityminecartabstract);
            }
            --itemstack.count;
            return true;
        }
        return false;
    }
    
    static {
        b = new DispenseBehaviorMinecart();
    }
}
