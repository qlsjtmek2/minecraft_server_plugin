// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;
import org.bukkit.event.entity.CreatureSpawnEvent;
import java.util.Iterator;
import java.util.List;

public class PathfinderGoalBreed extends PathfinderGoal
{
    private EntityAnimal d;
    World a;
    private EntityAnimal e;
    int b;
    float c;
    
    public PathfinderGoalBreed(final EntityAnimal entityanimal, final float f) {
        this.b = 0;
        this.d = entityanimal;
        this.a = entityanimal.world;
        this.c = f;
        this.a(3);
    }
    
    public boolean a() {
        if (!this.d.r()) {
            return false;
        }
        this.e = this.f();
        return this.e != null;
    }
    
    public boolean b() {
        return this.e.isAlive() && this.e.r() && this.b < 60;
    }
    
    public void d() {
        this.e = null;
        this.b = 0;
    }
    
    public void e() {
        this.d.getControllerLook().a(this.e, 10.0f, this.d.bs());
        this.d.getNavigation().a(this.e, this.c);
        ++this.b;
        if (this.b >= 60 && this.d.e(this.e) < 9.0) {
            this.g();
        }
    }
    
    private EntityAnimal f() {
        final float f = 8.0f;
        final List list = this.a.a(this.d.getClass(), this.d.boundingBox.grow(f, f, f));
        double d0 = Double.MAX_VALUE;
        EntityAnimal entityanimal = null;
        for (final EntityAnimal entityanimal2 : list) {
            if (this.d.mate(entityanimal2) && this.d.e(entityanimal2) < d0) {
                entityanimal = entityanimal2;
                d0 = this.d.e(entityanimal2);
            }
        }
        return entityanimal;
    }
    
    private void g() {
        final EntityAgeable entityageable = this.d.createChild(this.e);
        if (entityageable != null) {
            this.d.setAge(6000);
            this.e.setAge(6000);
            this.d.s();
            this.e.s();
            entityageable.setAge(-24000);
            entityageable.setPositionRotation(this.d.locX, this.d.locY, this.d.locZ, 0.0f, 0.0f);
            this.a.addEntity(entityageable, CreatureSpawnEvent.SpawnReason.BREEDING);
            final Random random = this.d.aE();
            for (int i = 0; i < 7; ++i) {
                final double d0 = random.nextGaussian() * 0.02;
                final double d2 = random.nextGaussian() * 0.02;
                final double d3 = random.nextGaussian() * 0.02;
                this.a.addParticle("heart", this.d.locX + random.nextFloat() * this.d.width * 2.0f - this.d.width, this.d.locY + 0.5 + random.nextFloat() * this.d.length, this.d.locZ + random.nextFloat() * this.d.width * 2.0f - this.d.width, d0, d2, d3);
            }
            this.a.addEntity(new EntityExperienceOrb(this.a, this.d.locX, this.d.locY, this.d.locZ, random.nextInt(7) + 1));
        }
    }
}
