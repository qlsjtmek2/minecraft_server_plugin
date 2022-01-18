// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

public class TileEntityPiston extends TileEntity
{
    private int a;
    private int b;
    private int c;
    private boolean d;
    private boolean e;
    private float f;
    private float g;
    private List h;
    
    public TileEntityPiston() {
        this.h = new ArrayList();
    }
    
    public TileEntityPiston(final int i, final int j, final int k, final boolean flag, final boolean flag1) {
        this.h = new ArrayList();
        this.a = i;
        this.b = j;
        this.c = k;
        this.d = flag;
        this.e = flag1;
    }
    
    public int a() {
        return this.a;
    }
    
    public int p() {
        return this.b;
    }
    
    public boolean b() {
        return this.d;
    }
    
    public int c() {
        return this.c;
    }
    
    public float a(float f) {
        if (f > 1.0f) {
            f = 1.0f;
        }
        return this.g + (this.f - this.g) * f;
    }
    
    private void a(float f, final float f1) {
        if (this.d) {
            f = 1.0f - f;
        }
        else {
            --f;
        }
        final AxisAlignedBB axisalignedbb = Block.PISTON_MOVING.b(this.world, this.x, this.y, this.z, this.a, f, this.c);
        if (axisalignedbb != null) {
            final List list = this.world.getEntities(null, axisalignedbb);
            if (!list.isEmpty()) {
                this.h.addAll(list);
                for (final Entity entity : this.h) {
                    entity.move(f1 * Facing.b[this.c], f1 * Facing.c[this.c], f1 * Facing.d[this.c]);
                }
                this.h.clear();
            }
        }
    }
    
    public void f() {
        if (this.g < 1.0f && this.world != null) {
            final float n = 1.0f;
            this.f = n;
            this.g = n;
            this.world.s(this.x, this.y, this.z);
            this.w_();
            if (this.world.getTypeId(this.x, this.y, this.z) == Block.PISTON_MOVING.id) {
                this.world.setTypeIdAndData(this.x, this.y, this.z, this.a, this.b, 3);
                this.world.g(this.x, this.y, this.z, this.a);
            }
        }
    }
    
    public void h() {
        if (this.world == null) {
            return;
        }
        this.g = this.f;
        if (this.g >= 1.0f) {
            this.a(1.0f, 0.25f);
            this.world.s(this.x, this.y, this.z);
            this.w_();
            if (this.world.getTypeId(this.x, this.y, this.z) == Block.PISTON_MOVING.id) {
                this.world.setTypeIdAndData(this.x, this.y, this.z, this.a, this.b, 3);
                this.world.g(this.x, this.y, this.z, this.a);
            }
        }
        else {
            this.f += 0.5f;
            if (this.f >= 1.0f) {
                this.f = 1.0f;
            }
            if (this.d) {
                this.a(this.f, this.f - this.g + 0.0625f);
            }
        }
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.a = nbttagcompound.getInt("blockId");
        this.b = nbttagcompound.getInt("blockData");
        this.c = nbttagcompound.getInt("facing");
        final float float1 = nbttagcompound.getFloat("progress");
        this.f = float1;
        this.g = float1;
        this.d = nbttagcompound.getBoolean("extending");
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("blockId", this.a);
        nbttagcompound.setInt("blockData", this.b);
        nbttagcompound.setInt("facing", this.c);
        nbttagcompound.setFloat("progress", this.g);
        nbttagcompound.setBoolean("extending", this.d);
    }
}
