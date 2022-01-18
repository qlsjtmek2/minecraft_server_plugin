// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class TileEntityEnchantTable extends TileEntity
{
    public int a;
    public float b;
    public float c;
    public float d;
    public float e;
    public float f;
    public float g;
    public float h;
    public float i;
    public float j;
    private static Random r;
    private String s;
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        if (this.b()) {
            nbttagcompound.setString("CustomName", this.s);
        }
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.hasKey("CustomName")) {
            this.s = nbttagcompound.getString("CustomName");
        }
    }
    
    public void h() {
        super.h();
        this.g = this.f;
        this.i = this.h;
        final EntityHuman nearbyPlayer = this.world.findNearbyPlayer(this.x + 0.5f, this.y + 0.5f, this.z + 0.5f, 3.0);
        if (nearbyPlayer != null) {
            this.j = (float)Math.atan2(nearbyPlayer.locZ - (this.z + 0.5f), nearbyPlayer.locX - (this.x + 0.5f));
            this.f += 0.1f;
            if (this.f < 0.5f || TileEntityEnchantTable.r.nextInt(40) == 0) {
                do {
                    this.d += TileEntityEnchantTable.r.nextInt(4) - TileEntityEnchantTable.r.nextInt(4);
                } while (this.d == this.d);
            }
        }
        else {
            this.j += 0.02f;
            this.f -= 0.1f;
        }
        while (this.h >= 3.1415927f) {
            this.h -= 6.2831855f;
        }
        while (this.h < -3.1415927f) {
            this.h += 6.2831855f;
        }
        while (this.j >= 3.1415927f) {
            this.j -= 6.2831855f;
        }
        while (this.j < -3.1415927f) {
            this.j += 6.2831855f;
        }
        float n;
        for (n = this.j - this.h; n >= 3.1415927f; n -= 6.2831855f) {}
        while (n < -3.1415927f) {
            n += 6.2831855f;
        }
        this.h += n * 0.4f;
        if (this.f < 0.0f) {
            this.f = 0.0f;
        }
        if (this.f > 1.0f) {
            this.f = 1.0f;
        }
        ++this.a;
        this.c = this.b;
        float n2 = (this.d - this.b) * 0.4f;
        final float n3 = 0.2f;
        if (n2 < -n3) {
            n2 = -n3;
        }
        if (n2 > n3) {
            n2 = n3;
        }
        this.e += (n2 - this.e) * 0.9f;
        this.b += this.e;
    }
    
    public String a() {
        return this.b() ? this.s : "container.enchant";
    }
    
    public boolean b() {
        return this.s != null && this.s.length() > 0;
    }
    
    public void a(final String s) {
        this.s = s;
    }
    
    static {
        TileEntityEnchantTable.r = new Random();
    }
}
