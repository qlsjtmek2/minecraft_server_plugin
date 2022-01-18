// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;

public class EntitySilverfish extends EntityMonster
{
    private int d;
    
    public EntitySilverfish(final World world) {
        super(world);
        this.texture = "/mob/silverfish.png";
        this.a(0.3f, 0.7f);
        this.bI = 0.6f;
    }
    
    public int getMaxHealth() {
        return 8;
    }
    
    protected boolean f_() {
        return false;
    }
    
    protected Entity findTarget() {
        final double d0 = 8.0;
        return this.world.findNearbyVulnerablePlayer(this, d0);
    }
    
    protected String bb() {
        return "mob.silverfish.say";
    }
    
    protected String bc() {
        return "mob.silverfish.hit";
    }
    
    protected String bd() {
        return "mob.silverfish.kill";
    }
    
    public boolean damageEntity(final DamageSource damagesource, final int i) {
        if (this.isInvulnerable()) {
            return false;
        }
        if (this.d <= 0 && (damagesource instanceof EntityDamageSource || damagesource == DamageSource.MAGIC)) {
            this.d = 20;
        }
        return super.damageEntity(damagesource, i);
    }
    
    protected void a(final Entity entity, final float f) {
        if (this.attackTicks <= 0 && f < 1.2f && entity.boundingBox.e > this.boundingBox.b && entity.boundingBox.b < this.boundingBox.e) {
            this.attackTicks = 20;
            this.m(entity);
        }
    }
    
    protected void a(final int i, final int j, final int k, final int l) {
        this.makeSound("mob.silverfish.step", 0.15f, 1.0f);
    }
    
    protected int getLootId() {
        return 0;
    }
    
    public void l_() {
        this.ay = this.yaw;
        super.l_();
    }
    
    protected void bq() {
        super.bq();
        if (!this.world.isStatic) {
            if (this.d > 0) {
                --this.d;
                if (this.d == 0) {
                    final int i = MathHelper.floor(this.locX);
                    final int j = MathHelper.floor(this.locY);
                    final int k = MathHelper.floor(this.locZ);
                    boolean flag = false;
                    for (int l = 0; !flag && l <= 5 && l >= -5; l = ((l <= 0) ? (1 - l) : (0 - l))) {
                        for (int i2 = 0; !flag && i2 <= 10 && i2 >= -10; i2 = ((i2 <= 0) ? (1 - i2) : (0 - i2))) {
                            for (int j2 = 0; !flag && j2 <= 10 && j2 >= -10; j2 = ((j2 <= 0) ? (1 - j2) : (0 - j2))) {
                                final int k2 = this.world.getTypeId(i + i2, j + l, k + j2);
                                if (k2 == Block.MONSTER_EGGS.id) {
                                    if (!CraftEventFactory.callEntityChangeBlockEvent(this, i + i2, j + l, k + j2, 0, 0).isCancelled()) {
                                        this.world.setAir(i + i2, j + l, k + j2, false);
                                        Block.MONSTER_EGGS.postBreak(this.world, i + i2, j + l, k + j2, 0);
                                        if (this.random.nextBoolean()) {
                                            flag = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (this.target == null && !this.k()) {
                final int i = MathHelper.floor(this.locX);
                final int j = MathHelper.floor(this.locY + 0.5);
                final int k = MathHelper.floor(this.locZ);
                final int l2 = this.random.nextInt(6);
                final int l = this.world.getTypeId(i + Facing.b[l2], j + Facing.c[l2], k + Facing.d[l2]);
                if (BlockMonsterEggs.d(l)) {
                    if (CraftEventFactory.callEntityChangeBlockEvent(this, i + Facing.b[l2], j + Facing.c[l2], k + Facing.d[l2], Block.MONSTER_EGGS.id, BlockMonsterEggs.e(l)).isCancelled()) {
                        return;
                    }
                    this.world.setTypeIdAndData(i + Facing.b[l2], j + Facing.c[l2], k + Facing.d[l2], Block.MONSTER_EGGS.id, BlockMonsterEggs.e(l), 3);
                    this.aU();
                    this.die();
                }
                else {
                    this.i();
                }
            }
            else if (this.target != null && !this.k()) {
                this.target = null;
            }
        }
    }
    
    public float a(final int i, final int j, final int k) {
        return (this.world.getTypeId(i, j - 1, k) == Block.STONE.id) ? 10.0f : super.a(i, j, k);
    }
    
    protected boolean i_() {
        return true;
    }
    
    public boolean canSpawn() {
        if (super.canSpawn()) {
            final EntityHuman entityhuman = this.world.findNearbyPlayer(this, 5.0);
            return entityhuman == null;
        }
        return false;
    }
    
    public int c(final Entity entity) {
        return 1;
    }
    
    public EnumMonsterType getMonsterType() {
        return EnumMonsterType.ARTHROPOD;
    }
}
