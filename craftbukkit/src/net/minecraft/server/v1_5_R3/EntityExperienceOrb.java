// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityTargetEvent;

public class EntityExperienceOrb extends Entity
{
    public int a;
    public int b;
    public int c;
    private int d;
    public int value;
    private EntityHuman targetPlayer;
    private int targetTime;
    
    public EntityExperienceOrb(final World world, final double d0, final double d1, final double d2, final int i) {
        super(world);
        this.b = 0;
        this.d = 5;
        this.a(0.5f, 0.5f);
        this.height = this.length / 2.0f;
        this.setPosition(d0, d1, d2);
        this.yaw = (float)(Math.random() * 360.0);
        this.motX = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612) * 2.0f;
        this.motY = (float)(Math.random() * 0.2) * 2.0f;
        this.motZ = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612) * 2.0f;
        this.value = i;
    }
    
    protected boolean f_() {
        return false;
    }
    
    public EntityExperienceOrb(final World world) {
        super(world);
        this.b = 0;
        this.d = 5;
        this.a(0.25f, 0.25f);
        this.height = this.length / 2.0f;
    }
    
    protected void a() {
    }
    
    public void l_() {
        super.l_();
        if (this.c > 0) {
            --this.c;
        }
        this.lastX = this.locX;
        this.lastY = this.locY;
        this.lastZ = this.locZ;
        this.motY -= 0.029999999329447746;
        if (this.world.getMaterial(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)) == Material.LAVA) {
            this.motY = 0.20000000298023224;
            this.motX = (this.random.nextFloat() - this.random.nextFloat()) * 0.2f;
            this.motZ = (this.random.nextFloat() - this.random.nextFloat()) * 0.2f;
            this.makeSound("random.fizz", 0.4f, 2.0f + this.random.nextFloat() * 0.4f);
        }
        this.i(this.locX, (this.boundingBox.b + this.boundingBox.e) / 2.0, this.locZ);
        final double d0 = 8.0;
        if (this.targetTime < this.a - 20 + this.id % 100) {
            if (this.targetPlayer == null || this.targetPlayer.e(this) > d0 * d0) {
                this.targetPlayer = this.world.findNearbyPlayer(this, d0);
            }
            this.targetTime = this.a;
        }
        if (this.targetPlayer != null) {
            final EntityTargetEvent event = CraftEventFactory.callEntityTargetEvent(this, this.targetPlayer, EntityTargetEvent.TargetReason.CLOSEST_PLAYER);
            final Entity target = (event.getTarget() == null) ? null : ((CraftEntity)event.getTarget()).getHandle();
            if (!event.isCancelled() && target != null) {
                final double d2 = (target.locX - this.locX) / d0;
                final double d3 = (target.locY + target.getHeadHeight() - this.locY) / d0;
                final double d4 = (target.locZ - this.locZ) / d0;
                final double d5 = Math.sqrt(d2 * d2 + d3 * d3 + d4 * d4);
                double d6 = 1.0 - d5;
                if (d6 > 0.0) {
                    d6 *= d6;
                    this.motX += d2 / d5 * d6 * 0.1;
                    this.motY += d3 / d5 * d6 * 0.1;
                    this.motZ += d4 / d5 * d6 * 0.1;
                }
            }
        }
        this.move(this.motX, this.motY, this.motZ);
        float f = 0.98f;
        if (this.onGround) {
            f = 0.58800006f;
            final int i = this.world.getTypeId(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ));
            if (i > 0) {
                f = Block.byId[i].frictionFactor * 0.98f;
            }
        }
        this.motX *= f;
        this.motY *= 0.9800000190734863;
        this.motZ *= f;
        if (this.onGround) {
            this.motY *= -0.8999999761581421;
        }
        ++this.a;
        ++this.b;
        if (this.b >= 6000) {
            this.die();
        }
    }
    
    public boolean H() {
        return this.world.a(this.boundingBox, Material.WATER, this);
    }
    
    protected void burn(final int i) {
        this.damageEntity(DamageSource.FIRE, i);
    }
    
    public boolean damageEntity(final DamageSource damagesource, final int i) {
        if (this.isInvulnerable()) {
            return false;
        }
        this.J();
        this.d -= i;
        if (this.d <= 0) {
            this.die();
        }
        return false;
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        nbttagcompound.setShort("Health", (byte)this.d);
        nbttagcompound.setShort("Age", (short)this.b);
        nbttagcompound.setShort("Value", (short)this.value);
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        this.d = (nbttagcompound.getShort("Health") & 0xFF);
        this.b = nbttagcompound.getShort("Age");
        this.value = nbttagcompound.getShort("Value");
    }
    
    public void b_(final EntityHuman entityhuman) {
        if (!this.world.isStatic && this.c == 0 && entityhuman.bT == 0) {
            entityhuman.bT = 2;
            this.makeSound("random.orb", 0.1f, 0.5f * ((this.random.nextFloat() - this.random.nextFloat()) * 0.7f + 1.8f));
            entityhuman.receive(this, 1);
            entityhuman.giveExp(CraftEventFactory.callPlayerExpChangeEvent(entityhuman, this.value).getAmount());
            this.die();
        }
    }
    
    public int c() {
        return this.value;
    }
    
    public static int getOrbValue(final int i) {
        if (i > 162670129) {
            return i - 100000;
        }
        if (i > 81335063) {
            return 81335063;
        }
        if (i > 40667527) {
            return 40667527;
        }
        if (i > 20333759) {
            return 20333759;
        }
        if (i > 10166857) {
            return 10166857;
        }
        if (i > 5083423) {
            return 5083423;
        }
        if (i > 2541701) {
            return 2541701;
        }
        if (i > 1270849) {
            return 1270849;
        }
        if (i > 635413) {
            return 635413;
        }
        if (i > 317701) {
            return 317701;
        }
        if (i > 158849) {
            return 158849;
        }
        if (i > 79423) {
            return 79423;
        }
        if (i > 39709) {
            return 39709;
        }
        if (i > 19853) {
            return 19853;
        }
        if (i > 9923) {
            return 9923;
        }
        if (i > 4957) {
            return 4957;
        }
        return (i >= 2477) ? 2477 : ((i >= 1237) ? 1237 : ((i >= 617) ? 617 : ((i >= 307) ? 307 : ((i >= 149) ? 149 : ((i >= 73) ? 73 : ((i >= 37) ? 37 : ((i >= 17) ? 17 : ((i >= 7) ? 7 : ((i >= 3) ? 3 : 1)))))))));
    }
    
    public boolean ap() {
        return false;
    }
}
