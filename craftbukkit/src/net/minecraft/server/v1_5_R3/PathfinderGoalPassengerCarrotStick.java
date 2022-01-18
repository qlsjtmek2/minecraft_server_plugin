// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class PathfinderGoalPassengerCarrotStick extends PathfinderGoal
{
    private final EntityLiving a;
    private final float b;
    private float c;
    private boolean d;
    private int e;
    private int f;
    
    public PathfinderGoalPassengerCarrotStick(final EntityLiving a, final float b) {
        this.c = 0.0f;
        this.d = false;
        this.e = 0;
        this.f = 0;
        this.a = a;
        this.b = b;
        this.a(7);
    }
    
    public void c() {
        this.c = 0.0f;
    }
    
    public void d() {
        this.d = false;
        this.c = 0.0f;
    }
    
    public boolean a() {
        return this.a.isAlive() && this.a.passenger != null && this.a.passenger instanceof EntityHuman && (this.d || this.a.bL());
    }
    
    public void e() {
        final EntityHuman entityliving = (EntityHuman)this.a.passenger;
        final EntityCreature entityCreature = (EntityCreature)this.a;
        float n = MathHelper.g(entityliving.yaw - this.a.yaw) * 0.5f;
        if (n > 5.0f) {
            n = 5.0f;
        }
        if (n < -5.0f) {
            n = -5.0f;
        }
        this.a.yaw = MathHelper.g(this.a.yaw + n);
        if (this.c < this.b) {
            this.c += (this.b - this.c) * 0.01f;
        }
        if (this.c > this.b) {
            this.c = this.b;
        }
        final int floor = MathHelper.floor(this.a.locX);
        final int floor2 = MathHelper.floor(this.a.locY);
        final int floor3 = MathHelper.floor(this.a.locZ);
        float c = this.c;
        if (this.d) {
            if (this.e++ > this.f) {
                this.d = false;
            }
            c += c * 1.15f * MathHelper.sin(this.e / this.f * 3.1415927f);
        }
        float n2 = 0.91f;
        if (this.a.onGround) {
            n2 = 0.54600006f;
            final int typeId = this.a.world.getTypeId(MathHelper.d(floor), MathHelper.d(floor2) - 1, MathHelper.d(floor3));
            if (typeId > 0) {
                n2 = Block.byId[typeId].frictionFactor * 0.91f;
            }
        }
        final float n3 = 0.16277136f / (n2 * n2 * n2);
        final float sin = MathHelper.sin(entityCreature.yaw * 3.1415927f / 180.0f);
        final float cos = MathHelper.cos(entityCreature.yaw * 3.1415927f / 180.0f);
        final float n4 = c * (entityCreature.aI() * n3 / Math.max(c, 1.0f));
        float n5 = -(n4 * sin);
        float n6 = n4 * cos;
        if (MathHelper.abs(n5) > MathHelper.abs(n6)) {
            if (n5 < 0.0f) {
                n5 -= this.a.width / 2.0f;
            }
            if (n5 > 0.0f) {
                n5 += this.a.width / 2.0f;
            }
            n6 = 0.0f;
        }
        else {
            n5 = 0.0f;
            if (n6 < 0.0f) {
                n6 -= this.a.width / 2.0f;
            }
            if (n6 > 0.0f) {
                n6 += this.a.width / 2.0f;
            }
        }
        final int floor4 = MathHelper.floor(this.a.locX + n5);
        final int floor5 = MathHelper.floor(this.a.locZ + n6);
        final PathPoint pathPoint = new PathPoint(MathHelper.d(this.a.width + 1.0f), MathHelper.d(this.a.length + entityliving.length + 1.0f), MathHelper.d(this.a.width + 1.0f));
        if (floor != floor4 || floor3 != floor5) {
            final int typeId2 = this.a.world.getTypeId(floor, floor2, floor3);
            final int typeId3 = this.a.world.getTypeId(floor, floor2 - 1, floor3);
            if (!this.b(typeId2) && (Block.byId[typeId2] != null || !this.b(typeId3)) && Pathfinder.a(this.a, floor4, floor2, floor5, pathPoint, false, false, true) == 0 && Pathfinder.a(this.a, floor, floor2 + 1, floor3, pathPoint, false, false, true) == 1 && Pathfinder.a(this.a, floor4, floor2 + 1, floor5, pathPoint, false, false, true) == 1) {
                entityCreature.getControllerJump().a();
            }
        }
        if (!entityliving.abilities.canInstantlyBuild && this.c >= this.b * 0.5f && this.a.aE().nextFloat() < 0.006f && !this.d) {
            final ItemStack bg = entityliving.bG();
            if (bg != null && bg.id == Item.CARROT_STICK.id) {
                bg.damage(1, entityliving);
                if (bg.count == 0) {
                    final ItemStack itemStack = new ItemStack(Item.FISHING_ROD);
                    itemStack.setTag(bg.tag);
                    entityliving.inventory.items[entityliving.inventory.itemInHandIndex] = itemStack;
                }
            }
        }
        this.a.e(0.0f, c);
    }
    
    private boolean b(final int n) {
        return Block.byId[n] != null && (Block.byId[n].d() == 10 || Block.byId[n] instanceof BlockStepAbstract);
    }
    
    public boolean f() {
        return this.d;
    }
    
    public void g() {
        this.d = true;
        this.e = 0;
        this.f = this.a.aE().nextInt(841) + 140;
    }
    
    public boolean h() {
        return !this.f() && this.c > this.b * 0.3f;
    }
}
