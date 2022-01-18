// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import org.bukkit.craftbukkit.v1_5_R3.TrigMath;
import java.util.List;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;

public class EntitySquid extends EntityWaterAnimal
{
    public float d;
    public float e;
    public float f;
    public float g;
    public float h;
    public float i;
    public float j;
    public float bK;
    private float bL;
    private float bM;
    private float bN;
    private float bO;
    private float bP;
    private float bQ;
    
    public EntitySquid(final World world) {
        super(world);
        this.d = 0.0f;
        this.e = 0.0f;
        this.f = 0.0f;
        this.g = 0.0f;
        this.h = 0.0f;
        this.i = 0.0f;
        this.j = 0.0f;
        this.bK = 0.0f;
        this.bL = 0.0f;
        this.bM = 0.0f;
        this.bN = 0.0f;
        this.bO = 0.0f;
        this.bP = 0.0f;
        this.bQ = 0.0f;
        this.texture = "/mob/squid.png";
        this.a(0.95f, 0.95f);
        this.bM = 1.0f / (this.random.nextFloat() + 1.0f) * 0.2f;
    }
    
    public int getMaxHealth() {
        return 10;
    }
    
    protected String bb() {
        return null;
    }
    
    protected String bc() {
        return null;
    }
    
    protected String bd() {
        return null;
    }
    
    protected float ba() {
        return 0.4f;
    }
    
    protected int getLootId() {
        return 0;
    }
    
    protected void dropDeathLoot(final boolean flag, final int i) {
        final List<ItemStack> loot = new ArrayList<ItemStack>();
        final int count = this.random.nextInt(3 + i) + 1;
        if (count > 0) {
            loot.add(new ItemStack(Material.INK_SACK, count));
        }
        CraftEventFactory.callEntityDeathEvent(this, loot);
    }
    
    public void c() {
        super.c();
        this.e = this.d;
        this.g = this.f;
        this.i = this.h;
        this.bK = this.j;
        this.h += this.bM;
        if (this.h > 6.2831855f) {
            this.h -= 6.2831855f;
            if (this.random.nextInt(10) == 0) {
                this.bM = 1.0f / (this.random.nextFloat() + 1.0f) * 0.2f;
            }
        }
        if (this.G()) {
            if (this.h < 3.1415927f) {
                final float f = this.h / 3.1415927f;
                this.j = MathHelper.sin(f * f * 3.1415927f) * 3.1415927f * 0.25f;
                if (f > 0.75) {
                    this.bL = 1.0f;
                    this.bN = 1.0f;
                }
                else {
                    this.bN *= 0.8f;
                }
            }
            else {
                this.j = 0.0f;
                this.bL *= 0.9f;
                this.bN *= 0.99f;
            }
            if (!this.world.isStatic) {
                this.motX = this.bO * this.bL;
                this.motY = this.bP * this.bL;
                this.motZ = this.bQ * this.bL;
            }
            final float f = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
            this.ay += (-(float)TrigMath.atan2(this.motX, this.motZ) * 180.0f / 3.1415927f - this.ay) * 0.1f;
            this.yaw = this.ay;
            this.f += 3.1415927f * this.bN * 1.5f;
            this.d += (-(float)TrigMath.atan2(f, this.motY) * 180.0f / 3.1415927f - this.d) * 0.1f;
        }
        else {
            this.j = MathHelper.abs(MathHelper.sin(this.h)) * 3.1415927f * 0.25f;
            if (!this.world.isStatic) {
                this.motX = 0.0;
                this.motY -= 0.08;
                this.motY *= 0.9800000190734863;
                this.motZ = 0.0;
            }
            this.d += (float)((-90.0f - this.d) * 0.02);
        }
    }
    
    public void e(final float f, final float f1) {
        this.move(this.motX, this.motY, this.motZ);
    }
    
    protected void bq() {
        ++this.bC;
        if (this.bC > 100) {
            final float bo = 0.0f;
            this.bQ = bo;
            this.bP = bo;
            this.bO = bo;
        }
        else if (this.random.nextInt(50) == 0 || !this.inWater || (this.bO == 0.0f && this.bP == 0.0f && this.bQ == 0.0f)) {
            final float f = this.random.nextFloat() * 3.1415927f * 2.0f;
            this.bO = MathHelper.cos(f) * 0.2f;
            this.bP = -0.1f + this.random.nextFloat() * 0.2f;
            this.bQ = MathHelper.sin(f) * 0.2f;
        }
        this.bn();
    }
    
    public boolean canSpawn() {
        return this.locY > 45.0 && this.locY < 63.0 && super.canSpawn();
    }
}
