// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.event.Cancellable;
import org.bukkit.plugin.PluginManager;
import java.util.Iterator;
import java.util.List;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.event.block.Action;

public class BlockPressurePlateBinary extends BlockPressurePlateAbstract
{
    private EnumMobType a;
    
    protected BlockPressurePlateBinary(final int i, final String s, final Material material, final EnumMobType enummobtype) {
        super(i, s, material);
        this.a = enummobtype;
    }
    
    protected int d(final int i) {
        return (i > 0) ? 1 : 0;
    }
    
    protected int c(final int i) {
        return (i == 1) ? 15 : 0;
    }
    
    protected int e(final World world, final int i, final int j, final int k) {
        List list = null;
        if (this.a == EnumMobType.EVERYTHING) {
            list = world.getEntities(null, this.a(i, j, k));
        }
        if (this.a == EnumMobType.MOBS) {
            list = world.a(EntityLiving.class, this.a(i, j, k));
        }
        if (this.a == EnumMobType.PLAYERS) {
            list = world.a(EntityHuman.class, this.a(i, j, k));
        }
        if (!list.isEmpty()) {
            for (final Entity entity : list) {
                if (this.c(world.getData(i, j, k)) == 0) {
                    final org.bukkit.World bworld = world.getWorld();
                    final PluginManager manager = world.getServer().getPluginManager();
                    Cancellable cancellable;
                    if (entity instanceof EntityHuman) {
                        cancellable = CraftEventFactory.callPlayerInteractEvent((EntityHuman)entity, Action.PHYSICAL, i, j, k, -1, null);
                    }
                    else {
                        cancellable = new EntityInteractEvent(entity.getBukkitEntity(), bworld.getBlockAt(i, j, k));
                        manager.callEvent((Event)cancellable);
                    }
                    if (cancellable.isCancelled()) {
                        continue;
                    }
                }
                if (!entity.at()) {
                    return 15;
                }
            }
        }
        return 0;
    }
}
