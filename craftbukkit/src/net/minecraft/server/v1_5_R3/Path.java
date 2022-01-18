// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class Path
{
    private PathPoint[] a;
    private int b;
    
    public Path() {
        this.a = new PathPoint[128];
        this.b = 0;
    }
    
    public PathPoint a(final PathPoint pathpoint) {
        if (pathpoint.d >= 0) {
            throw new IllegalStateException("OW KNOWS!");
        }
        if (this.b == this.a.length) {
            final PathPoint[] apathpoint = new PathPoint[this.b << 1];
            System.arraycopy(this.a, 0, apathpoint, 0, this.b);
            this.a = apathpoint;
        }
        this.a[this.b] = pathpoint;
        pathpoint.d = this.b;
        this.a(this.b++);
        return pathpoint;
    }
    
    public void a() {
        this.b = 0;
    }
    
    public PathPoint c() {
        final PathPoint pathpoint = this.a[0];
        final PathPoint[] a = this.a;
        final int n = 0;
        final PathPoint[] a2 = this.a;
        final int b = this.b - 1;
        this.b = b;
        a[n] = a2[b];
        this.a[this.b] = null;
        if (this.b > 0) {
            this.b(0);
        }
        pathpoint.d = -1;
        return pathpoint;
    }
    
    public void a(final PathPoint pathpoint, final float f) {
        final float f2 = pathpoint.g;
        pathpoint.g = f;
        if (f < f2) {
            this.a(pathpoint.d);
        }
        else {
            this.b(pathpoint.d);
        }
    }
    
    private void a(int i) {
        final PathPoint pathpoint = this.a[i];
        final float f = pathpoint.g;
        while (i > 0) {
            final int j = i - 1 >> 1;
            final PathPoint pathpoint2 = this.a[j];
            if (f >= pathpoint2.g) {
                break;
            }
            this.a[i] = pathpoint2;
            pathpoint2.d = i;
            i = j;
        }
        this.a[i] = pathpoint;
        pathpoint.d = i;
    }
    
    private void b(int i) {
        final PathPoint pathpoint = this.a[i];
        final float f = pathpoint.g;
        while (true) {
            final int j = 1 + (i << 1);
            final int k = j + 1;
            if (j >= this.b) {
                break;
            }
            final PathPoint pathpoint2 = this.a[j];
            final float f2 = pathpoint2.g;
            PathPoint pathpoint3;
            float f3;
            if (k >= this.b) {
                pathpoint3 = null;
                f3 = Float.POSITIVE_INFINITY;
            }
            else {
                pathpoint3 = this.a[k];
                f3 = pathpoint3.g;
            }
            if (f2 < f3) {
                if (f2 >= f) {
                    break;
                }
                this.a[i] = pathpoint2;
                pathpoint2.d = i;
                i = j;
            }
            else {
                if (f3 >= f) {
                    break;
                }
                this.a[i] = pathpoint3;
                pathpoint3.d = i;
                i = k;
            }
        }
        this.a[i] = pathpoint;
        pathpoint.d = i;
    }
    
    public boolean e() {
        return this.b == 0;
    }
}
