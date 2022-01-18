// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class EntityCaveSpider extends EntitySpider
{
    public EntityCaveSpider(final World world) {
        super(world);
        this.texture = "/mob/cavespider.png";
        this.a(0.7f, 0.5f);
    }
    
    public int getMaxHealth() {
        return 12;
    }
    
    public boolean m(final Entity entity) {
        if (super.m(entity)) {
            if (entity instanceof EntityLiving) {
                int n = 0;
                if (this.world.difficulty > 1) {
                    if (this.world.difficulty == 2) {
                        n = 7;
                    }
                    else if (this.world.difficulty == 3) {
                        n = 15;
                    }
                }
                if (n > 0) {
                    ((EntityLiving)entity).addEffect(new MobEffect(MobEffectList.POISON.id, n * 20, 0));
                }
            }
            return true;
        }
        return false;
    }
    
    public void bJ() {
    }
}
