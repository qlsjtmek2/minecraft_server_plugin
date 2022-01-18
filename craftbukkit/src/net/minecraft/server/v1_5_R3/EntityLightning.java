// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;

public class EntityLightning extends EntityWeather
{
    private int lifeTicks;
    public long a;
    private int c;
    public boolean isEffect;
    
    public EntityLightning(final World world, final double d0, final double d1, final double d2) {
        this(world, d0, d1, d2, false);
    }
    
    public EntityLightning(final World world, final double d0, final double d1, final double d2, final boolean isEffect) {
        super(world);
        this.a = 0L;
        this.isEffect = false;
        this.isEffect = isEffect;
        this.setPositionRotation(d0, d1, d2, 0.0f, 0.0f);
        this.lifeTicks = 2;
        this.a = this.random.nextLong();
        this.c = this.random.nextInt(3) + 1;
        if (!isEffect && !world.isStatic && world.difficulty >= 2 && world.areChunksLoaded(MathHelper.floor(d0), MathHelper.floor(d1), MathHelper.floor(d2), 10)) {
            int i = MathHelper.floor(d0);
            int j = MathHelper.floor(d1);
            int k = MathHelper.floor(d2);
            if (world.getTypeId(i, j, k) == 0 && Block.FIRE.canPlace(world, i, j, k) && !CraftEventFactory.callBlockIgniteEvent(world, i, j, k, this).isCancelled()) {
                world.setTypeIdUpdate(i, j, k, Block.FIRE.id);
            }
            for (i = 0; i < 4; ++i) {
                j = MathHelper.floor(d0) + this.random.nextInt(3) - 1;
                k = MathHelper.floor(d1) + this.random.nextInt(3) - 1;
                final int l = MathHelper.floor(d2) + this.random.nextInt(3) - 1;
                if (world.getTypeId(j, k, l) == 0 && Block.FIRE.canPlace(world, j, k, l) && !CraftEventFactory.callBlockIgniteEvent(world, j, k, l, this).isCancelled()) {
                    world.setTypeIdUpdate(i, j, k, Block.FIRE.id);
                }
            }
        }
    }
    
    public void l_() {
        super.l_();
        if (this.lifeTicks == 2) {
            this.world.makeSound(this.locX, this.locY, this.locZ, "ambient.weather.thunder", 10000.0f, 0.8f + this.random.nextFloat() * 0.2f);
            this.world.makeSound(this.locX, this.locY, this.locZ, "random.explode", 2.0f, 0.5f + this.random.nextFloat() * 0.2f);
        }
        --this.lifeTicks;
        if (this.lifeTicks < 0) {
            if (this.c == 0) {
                this.die();
            }
            else if (this.lifeTicks < -this.random.nextInt(10)) {
                --this.c;
                this.lifeTicks = 1;
                this.a = this.random.nextLong();
                if (!this.isEffect && !this.world.isStatic && this.world.areChunksLoaded(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ), 10)) {
                    final int i = MathHelper.floor(this.locX);
                    final int j = MathHelper.floor(this.locY);
                    final int k = MathHelper.floor(this.locZ);
                    if (this.world.getTypeId(i, j, k) == 0 && Block.FIRE.canPlace(this.world, i, j, k) && !CraftEventFactory.callBlockIgniteEvent(this.world, i, j, k, this).isCancelled()) {
                        this.world.setTypeIdUpdate(i, j, k, Block.FIRE.id);
                    }
                }
            }
        }
        if (this.lifeTicks >= 0 && !this.isEffect) {
            if (this.world.isStatic) {
                this.world.q = 2;
            }
            else {
                final double d0 = 3.0;
                final List list = this.world.getEntities(this, AxisAlignedBB.a().a(this.locX - d0, this.locY - d0, this.locZ - d0, this.locX + d0, this.locY + 6.0 + d0, this.locZ + d0));
                for (int l = 0; l < list.size(); ++l) {
                    final Entity entity = list.get(l);
                    entity.a(this);
                }
            }
        }
    }
    
    protected void a() {
    }
    
    protected void a(final NBTTagCompound nbttagcompound) {
    }
    
    protected void b(final NBTTagCompound nbttagcompound) {
    }
}
