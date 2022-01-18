// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.Material;

public class PathfinderGoalEatTile extends PathfinderGoal
{
    private EntityLiving b;
    private World c;
    int a;
    
    public PathfinderGoalEatTile(final EntityLiving entityliving) {
        this.a = 0;
        this.b = entityliving;
        this.c = entityliving.world;
        this.a(7);
    }
    
    public boolean a() {
        if (this.b.aE().nextInt(this.b.isBaby() ? 50 : 1000) != 0) {
            return false;
        }
        final int i = MathHelper.floor(this.b.locX);
        final int j = MathHelper.floor(this.b.locY);
        final int k = MathHelper.floor(this.b.locZ);
        return (this.c.getTypeId(i, j, k) == Block.LONG_GRASS.id && this.c.getData(i, j, k) == 1) || this.c.getTypeId(i, j - 1, k) == Block.GRASS.id;
    }
    
    public void c() {
        this.a = 40;
        this.c.broadcastEntityEffect(this.b, (byte)10);
        this.b.getNavigation().g();
    }
    
    public void d() {
        this.a = 0;
    }
    
    public boolean b() {
        return this.a > 0;
    }
    
    public int f() {
        return this.a;
    }
    
    public void e() {
        this.a = Math.max(0, this.a - 1);
        if (this.a == 4) {
            final int i = MathHelper.floor(this.b.locX);
            final int j = MathHelper.floor(this.b.locY);
            final int k = MathHelper.floor(this.b.locZ);
            if (this.c.getTypeId(i, j, k) == Block.LONG_GRASS.id) {
                if (!CraftEventFactory.callEntityChangeBlockEvent(this.b.getBukkitEntity(), this.b.world.getWorld().getBlockAt(i, j, k), Material.AIR).isCancelled()) {
                    this.c.setAir(i, j, k, false);
                    this.b.aK();
                }
            }
            else if (this.c.getTypeId(i, j - 1, k) == Block.GRASS.id && !CraftEventFactory.callEntityChangeBlockEvent(this.b.getBukkitEntity(), this.b.world.getWorld().getBlockAt(i, j - 1, k), Material.DIRT).isCancelled()) {
                this.c.triggerEffect(2001, i, j - 1, k, Block.GRASS.id);
                this.c.setTypeIdAndData(i, j - 1, k, Block.DIRT.id, 0, 2);
                this.b.aK();
            }
        }
    }
}
