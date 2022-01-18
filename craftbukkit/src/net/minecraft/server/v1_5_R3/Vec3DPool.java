// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class Vec3DPool
{
    private final int a;
    private final int b;
    private Vec3D freelist;
    private Vec3D alloclist;
    private Vec3D freelisthead;
    private Vec3D alloclisthead;
    private int total_size;
    private int position;
    private int largestSize;
    private int resizeTime;
    
    public Vec3DPool(final int i, final int j) {
        this.freelist = null;
        this.alloclist = null;
        this.freelisthead = null;
        this.alloclisthead = null;
        this.total_size = 0;
        this.position = 0;
        this.largestSize = 0;
        this.resizeTime = 0;
        this.a = i;
        this.b = j;
    }
    
    public final Vec3D create(final double d0, final double d1, final double d2) {
        if (this.resizeTime == 0) {
            return Vec3D.a(d0, d1, d2);
        }
        Vec3D vec3d;
        if (this.freelist == null) {
            vec3d = new Vec3D(this, d0, d1, d2);
            ++this.total_size;
        }
        else {
            vec3d = this.freelist;
            this.freelist = vec3d.next;
            vec3d.b(d0, d1, d2);
        }
        if (this.alloclist == null) {
            this.alloclisthead = vec3d;
        }
        vec3d.next = this.alloclist;
        this.alloclist = vec3d;
        ++this.position;
        return vec3d;
    }
    
    public void release(final Vec3D v) {
        if (this.alloclist == v) {
            this.alloclist = v.next;
            if (this.freelist == null) {
                this.freelisthead = v;
            }
            v.next = this.freelist;
            this.freelist = v;
            --this.position;
        }
    }
    
    public void a() {
        if (this.position > this.largestSize) {
            this.largestSize = this.position;
        }
        if (this.alloclist != null) {
            if (this.freelist == null) {
                this.freelist = this.alloclist;
                this.freelisthead = this.alloclisthead;
            }
            else {
                this.alloclisthead.next = this.freelist;
                this.freelist = this.alloclist;
                this.freelisthead = this.alloclisthead;
            }
            this.alloclist = null;
        }
        if ((this.resizeTime++ & 0xFF) == 0x0) {
            final int newSize = this.total_size - (this.total_size >> 3);
            if (newSize > this.largestSize) {
                for (int i = this.total_size; i > newSize; --i) {
                    this.freelist = this.freelist.next;
                }
                this.total_size = newSize;
            }
            this.largestSize = 0;
        }
        this.position = 0;
    }
    
    public int c() {
        return this.total_size;
    }
    
    public int d() {
        return this.position;
    }
    
    private boolean e() {
        return this.b < 0 || this.a < 0;
    }
}
