// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;

public class EntityBlaze extends EntityMonster
{
    private float d;
    private int e;
    private int f;
    
    public EntityBlaze(final World world) {
        super(world);
        this.d = 0.5f;
        this.texture = "/mob/fire.png";
        this.fireProof = true;
        this.be = 10;
    }
    
    public int getMaxHealth() {
        return 20;
    }
    
    protected void a() {
        super.a();
        this.datawatcher.a(16, new Byte((byte)0));
    }
    
    protected String bb() {
        return "mob.blaze.breathe";
    }
    
    protected String bc() {
        return "mob.blaze.hit";
    }
    
    protected String bd() {
        return "mob.blaze.death";
    }
    
    public float c(final float f) {
        return 1.0f;
    }
    
    public void c() {
        if (!this.world.isStatic) {
            if (this.F()) {
                this.damageEntity(DamageSource.DROWN, 1);
            }
            --this.e;
            if (this.e <= 0) {
                this.e = 100;
                this.d = 0.5f + (float)this.random.nextGaussian() * 3.0f;
            }
            if (this.l() != null && this.l().locY + this.l().getHeadHeight() > this.locY + this.getHeadHeight() + this.d) {
                this.motY += (0.30000001192092896 - this.motY) * 0.30000001192092896;
            }
        }
        if (this.random.nextInt(24) == 0) {
            this.world.makeSound(this.locX + 0.5, this.locY + 0.5, this.locZ + 0.5, "fire.fire", 1.0f + this.random.nextFloat(), this.random.nextFloat() * 0.7f + 0.3f);
        }
        if (!this.onGround && this.motY < 0.0) {
            this.motY *= 0.6;
        }
        for (int i = 0; i < 2; ++i) {
            this.world.addParticle("largesmoke", this.locX + (this.random.nextDouble() - 0.5) * this.width, this.locY + this.random.nextDouble() * this.length, this.locZ + (this.random.nextDouble() - 0.5) * this.width, 0.0, 0.0, 0.0);
        }
        super.c();
    }
    
    protected void a(final Entity entity, final float f) {
        if (this.attackTicks <= 0 && f < 2.0f && entity.boundingBox.e > this.boundingBox.b && entity.boundingBox.b < this.boundingBox.e) {
            this.attackTicks = 20;
            this.m(entity);
        }
        else if (f < 30.0f) {
            final double d0 = entity.locX - this.locX;
            final double d2 = entity.boundingBox.b + entity.length / 2.0f - (this.locY + this.length / 2.0f);
            final double d3 = entity.locZ - this.locZ;
            if (this.attackTicks == 0) {
                ++this.f;
                if (this.f == 1) {
                    this.attackTicks = 60;
                    this.a(true);
                }
                else if (this.f <= 4) {
                    this.attackTicks = 6;
                }
                else {
                    this.attackTicks = 100;
                    this.f = 0;
                    this.a(false);
                }
                if (this.f > 1) {
                    final float f2 = MathHelper.c(f) * 0.5f;
                    this.world.a(null, 1009, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
                    for (int i = 0; i < 1; ++i) {
                        final EntitySmallFireball entitysmallfireball = new EntitySmallFireball(this.world, this, d0 + this.random.nextGaussian() * f2, d2, d3 + this.random.nextGaussian() * f2);
                        entitysmallfireball.locY = this.locY + this.length / 2.0f + 0.5;
                        this.world.addEntity(entitysmallfireball);
                    }
                }
            }
            this.yaw = (float)(Math.atan2(d3, d0) * 180.0 / 3.1415927410125732) - 90.0f;
            this.b = true;
        }
    }
    
    protected void a(final float f) {
    }
    
    protected int getLootId() {
        return Item.BLAZE_ROD.id;
    }
    
    public boolean isBurning() {
        return this.m();
    }
    
    protected void dropDeathLoot(final boolean flag, final int i) {
        if (flag) {
            final List<ItemStack> loot = new ArrayList<ItemStack>();
            final int j = this.random.nextInt(2 + i);
            if (j > 0) {
                loot.add(new ItemStack(Item.BLAZE_ROD.id, j));
            }
            CraftEventFactory.callEntityDeathEvent(this, loot);
        }
    }
    
    public boolean m() {
        return (this.datawatcher.getByte(16) & 0x1) != 0x0;
    }
    
    public void a(final boolean flag) {
        byte b0 = this.datawatcher.getByte(16);
        if (flag) {
            b0 |= 0x1;
        }
        else {
            b0 &= 0xFFFFFFFE;
        }
        this.datawatcher.watch(16, b0);
    }
    
    protected boolean i_() {
        return true;
    }
    
    public int c(final Entity entity) {
        return 6;
    }
}
