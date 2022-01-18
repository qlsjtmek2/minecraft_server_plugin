// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class Navigation
{
    private EntityLiving a;
    private World b;
    private PathEntity c;
    private float d;
    private float e;
    private boolean f;
    private int g;
    private int h;
    private Vec3D i;
    private boolean j;
    private boolean k;
    private boolean l;
    private boolean m;
    
    public Navigation(final EntityLiving a, final World b, final float e) {
        this.f = false;
        this.i = Vec3D.a(0.0, 0.0, 0.0);
        this.j = true;
        this.k = false;
        this.l = false;
        this.m = false;
        this.a = a;
        this.b = b;
        this.e = e;
    }
    
    public void a(final boolean l) {
        this.l = l;
    }
    
    public boolean a() {
        return this.l;
    }
    
    public void b(final boolean k) {
        this.k = k;
    }
    
    public void c(final boolean j) {
        this.j = j;
    }
    
    public boolean c() {
        return this.k;
    }
    
    public void d(final boolean f) {
        this.f = f;
    }
    
    public void a(final float d) {
        this.d = d;
    }
    
    public void e(final boolean m) {
        this.m = m;
    }
    
    public PathEntity a(final double n, final double n2, final double n3) {
        if (!this.k()) {
            return null;
        }
        return this.b.a(this.a, MathHelper.floor(n), (int)n2, MathHelper.floor(n3), this.e, this.j, this.k, this.l, this.m);
    }
    
    public boolean a(final double n, final double n2, final double n3, final float n4) {
        return this.a(this.a(MathHelper.floor(n), (int)n2, MathHelper.floor(n3)), n4);
    }
    
    public PathEntity a(final EntityLiving entity1) {
        if (!this.k()) {
            return null;
        }
        return this.b.findPath(this.a, entity1, this.e, this.j, this.k, this.l, this.m);
    }
    
    public boolean a(final EntityLiving entityLiving, final float n) {
        final PathEntity a = this.a(entityLiving);
        return a != null && this.a(a, n);
    }
    
    public boolean a(final PathEntity c, final float d) {
        if (c == null) {
            this.c = null;
            return false;
        }
        if (!c.a(this.c)) {
            this.c = c;
        }
        if (this.f) {
            this.m();
        }
        if (this.c.d() == 0) {
            return false;
        }
        this.d = d;
        final Vec3D i = this.i();
        this.h = this.g;
        this.i.c = i.c;
        this.i.d = i.d;
        this.i.e = i.e;
        return true;
    }
    
    public PathEntity d() {
        return this.c;
    }
    
    public void e() {
        ++this.g;
        if (this.f()) {
            return;
        }
        if (this.k()) {
            this.h();
        }
        if (this.f()) {
            return;
        }
        final Vec3D a = this.c.a(this.a);
        if (a == null) {
            return;
        }
        this.a.getControllerMove().a(a.c, a.d, a.e, this.d);
    }
    
    private void h() {
        final Vec3D i = this.i();
        int d = this.c.d();
        for (int j = this.c.e(); j < this.c.d(); ++j) {
            if (this.c.a(j).b != (int)i.d) {
                d = j;
                break;
            }
        }
        final float n = this.a.width * this.a.width;
        for (int k = this.c.e(); k < d; ++k) {
            if (i.distanceSquared(this.c.a(this.a, k)) < n) {
                this.c.c(k + 1);
            }
        }
        final int f = MathHelper.f(this.a.width);
        final int n2 = (int)this.a.length + 1;
        final int n3 = f;
        for (int l = d - 1; l >= this.c.e(); --l) {
            if (this.a(i, this.c.a(this.a, l), f, n2, n3)) {
                this.c.c(l);
                break;
            }
        }
        if (this.g - this.h > 100) {
            if (i.distanceSquared(this.i) < 2.25) {
                this.g();
            }
            this.h = this.g;
            this.i.c = i.c;
            this.i.d = i.d;
            this.i.e = i.e;
        }
    }
    
    public boolean f() {
        return this.c == null || this.c.b();
    }
    
    public void g() {
        this.c = null;
    }
    
    private Vec3D i() {
        return this.b.getVec3DPool().create(this.a.locX, this.j(), this.a.locZ);
    }
    
    private int j() {
        if (!this.a.G() || !this.m) {
            return (int)(this.a.boundingBox.b + 0.5);
        }
        int n = (int)this.a.boundingBox.b;
        int n2 = this.b.getTypeId(MathHelper.floor(this.a.locX), n, MathHelper.floor(this.a.locZ));
        int n3 = 0;
        while (n2 == Block.WATER.id || n2 == Block.STATIONARY_WATER.id) {
            ++n;
            n2 = this.b.getTypeId(MathHelper.floor(this.a.locX), n, MathHelper.floor(this.a.locZ));
            if (++n3 > 16) {
                return (int)this.a.boundingBox.b;
            }
        }
        return n;
    }
    
    private boolean k() {
        return this.a.onGround || (this.m && this.l());
    }
    
    private boolean l() {
        return this.a.G() || this.a.I();
    }
    
    private void m() {
        if (this.b.l(MathHelper.floor(this.a.locX), (int)(this.a.boundingBox.b + 0.5), MathHelper.floor(this.a.locZ))) {
            return;
        }
        for (int i = 0; i < this.c.d(); ++i) {
            final PathPoint a = this.c.a(i);
            if (this.b.l(a.a, a.b, a.c)) {
                this.c.b(i - 1);
                return;
            }
        }
    }
    
    private boolean a(final Vec3D vec3D, final Vec3D vec3D2, int n, final int n2, int n3) {
        int floor = MathHelper.floor(vec3D.c);
        int floor2 = MathHelper.floor(vec3D.e);
        final double n4 = vec3D2.c - vec3D.c;
        final double n5 = vec3D2.e - vec3D.e;
        final double n6 = n4 * n4 + n5 * n5;
        if (n6 < 1.0E-8) {
            return false;
        }
        final double n7 = 1.0 / Math.sqrt(n6);
        final double n8 = n4 * n7;
        final double n9 = n5 * n7;
        n += 2;
        n3 += 2;
        if (!this.a(floor, (int)vec3D.d, floor2, n, n2, n3, vec3D, n8, n9)) {
            return false;
        }
        n -= 2;
        n3 -= 2;
        final double n10 = 1.0 / Math.abs(n8);
        final double n11 = 1.0 / Math.abs(n9);
        double n12 = floor * 1 - vec3D.c;
        double n13 = floor2 * 1 - vec3D.e;
        if (n8 >= 0.0) {
            ++n12;
        }
        if (n9 >= 0.0) {
            ++n13;
        }
        double n14 = n12 / n8;
        double n15 = n13 / n9;
        final int n16 = (n8 < 0.0) ? -1 : 1;
        final int n17 = (n9 < 0.0) ? -1 : 1;
        final int floor3 = MathHelper.floor(vec3D2.c);
        final int floor4 = MathHelper.floor(vec3D2.e);
        int n18 = floor3 - floor;
        int n19 = floor4 - floor2;
        while (n18 * n16 > 0 || n19 * n17 > 0) {
            if (n14 < n15) {
                n14 += n10;
                floor += n16;
                n18 = floor3 - floor;
            }
            else {
                n15 += n11;
                floor2 += n17;
                n19 = floor4 - floor2;
            }
            if (!this.a(floor, (int)vec3D.d, floor2, n, n2, n3, vec3D, n8, n9)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean a(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final Vec3D vec3D, final double n7, final double n8) {
        final int n9 = n - n4 / 2;
        final int n10 = n3 - n6 / 2;
        if (!this.b(n9, n2, n10, n4, n5, n6, vec3D, n7, n8)) {
            return false;
        }
        for (int i = n9; i < n9 + n4; ++i) {
            for (int j = n10; j < n10 + n6; ++j) {
                if ((i + 0.5 - vec3D.c) * n7 + (j + 0.5 - vec3D.e) * n8 >= 0.0) {
                    final int typeId = this.b.getTypeId(i, n2 - 1, j);
                    if (typeId <= 0) {
                        return false;
                    }
                    final Material material = Block.byId[typeId].material;
                    if (material == Material.WATER && !this.a.G()) {
                        return false;
                    }
                    if (material == Material.LAVA) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    private boolean b(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final Vec3D vec3D, final double n7, final double n8) {
        for (int i = n; i < n + n4; ++i) {
            for (int j = n2; j < n2 + n5; ++j) {
                for (int k = n3; k < n3 + n6; ++k) {
                    if ((i + 0.5 - vec3D.c) * n7 + (k + 0.5 - vec3D.e) * n8 >= 0.0) {
                        final int typeId = this.b.getTypeId(i, j, k);
                        if (typeId > 0) {
                            if (!Block.byId[typeId].b((IBlockAccess)this.b, i, j, k)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
