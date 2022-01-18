// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.map.MapView;
import org.bukkit.event.server.MapInitializeEvent;

public class ItemMapEmpty extends ItemWorldMapBase
{
    protected ItemMapEmpty(final int i) {
        super(i);
        this.a(CreativeModeTab.f);
    }
    
    public ItemStack a(final ItemStack itemstack, final World world, final EntityHuman entityhuman) {
        final ItemStack itemstack2 = new ItemStack(Item.MAP, 1, world.b("map"));
        final String s = "map_" + itemstack2.getData();
        final WorldMap worldmap = new WorldMap(s);
        world.a(s, worldmap);
        worldmap.scale = 0;
        final int i = 128 * (1 << worldmap.scale);
        worldmap.centerX = (int)(Math.round(entityhuman.locX / i) * i);
        worldmap.centerZ = (int)(Math.round(entityhuman.locZ / i) * i);
        worldmap.map = (byte)((WorldServer)world).dimension;
        worldmap.c();
        CraftEventFactory.callEvent(new MapInitializeEvent(worldmap.mapView));
        --itemstack.count;
        if (itemstack.count <= 0) {
            return itemstack2;
        }
        if (!entityhuman.inventory.pickup(itemstack2.cloneItemStack())) {
            entityhuman.drop(itemstack2);
        }
        return itemstack;
    }
}
