// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import org.bukkit.craftbukkit.v1_5_R3.util.UnsafeList;
import java.util.List;

public class PathfinderGoalSelector
{
    private List a;
    private List b;
    private final MethodProfiler c;
    private int d;
    private int e;
    
    public PathfinderGoalSelector(final MethodProfiler methodprofiler) {
        this.a = new UnsafeList();
        this.b = new UnsafeList();
        this.d = 0;
        this.e = 3;
        this.c = methodprofiler;
    }
    
    public void a(final int i, final PathfinderGoal pathfindergoal) {
        this.a.add(new PathfinderGoalSelectorItem(this, i, pathfindergoal));
    }
    
    public void a(final PathfinderGoal pathfindergoal) {
        final Iterator iterator = this.a.iterator();
        while (iterator.hasNext()) {
            final PathfinderGoalSelectorItem pathfindergoalselectoritem = iterator.next();
            final PathfinderGoal pathfindergoal2 = pathfindergoalselectoritem.a;
            if (pathfindergoal2 == pathfindergoal) {
                if (this.b.contains(pathfindergoalselectoritem)) {
                    pathfindergoal2.d();
                    this.b.remove(pathfindergoalselectoritem);
                }
                iterator.remove();
            }
        }
    }
    
    public void a() {
        if (this.d++ % this.e == 0) {
            for (final PathfinderGoalSelectorItem pathfindergoalselectoritem : this.a) {
                final boolean flag = this.b.contains(pathfindergoalselectoritem);
                if (flag) {
                    if (this.b(pathfindergoalselectoritem) && this.a(pathfindergoalselectoritem)) {
                        continue;
                    }
                    pathfindergoalselectoritem.a.d();
                    this.b.remove(pathfindergoalselectoritem);
                }
                if (this.b(pathfindergoalselectoritem) && pathfindergoalselectoritem.a.a()) {
                    pathfindergoalselectoritem.a.c();
                    this.b.add(pathfindergoalselectoritem);
                }
            }
        }
        else {
            final Iterator iterator = this.b.iterator();
            while (iterator.hasNext()) {
                final PathfinderGoalSelectorItem pathfindergoalselectoritem = iterator.next();
                if (!pathfindergoalselectoritem.a.b()) {
                    pathfindergoalselectoritem.a.d();
                    iterator.remove();
                }
            }
        }
        this.c.a("goalStart");
        this.c.b();
        this.c.a("goalTick");
        final Iterator iterator = this.b.iterator();
        while (iterator.hasNext()) {
            final PathfinderGoalSelectorItem pathfindergoalselectoritem = iterator.next();
            pathfindergoalselectoritem.a.e();
        }
        this.c.b();
    }
    
    private boolean a(final PathfinderGoalSelectorItem pathfindergoalselectoritem) {
        this.c.a("canContinue");
        final boolean flag = pathfindergoalselectoritem.a.b();
        this.c.b();
        return flag;
    }
    
    private boolean b(final PathfinderGoalSelectorItem pathfindergoalselectoritem) {
        this.c.a("canUse");
        final Iterator iterator = this.a.iterator();
        while (iterator.hasNext()) {
            final PathfinderGoalSelectorItem pathfindergoalselectoritem2 = iterator.next();
            if (pathfindergoalselectoritem2 != pathfindergoalselectoritem) {
                if (pathfindergoalselectoritem.b >= pathfindergoalselectoritem2.b) {
                    if (!this.a(pathfindergoalselectoritem, pathfindergoalselectoritem2) && this.b.contains(pathfindergoalselectoritem2)) {
                        this.c.b();
                        return ((UnsafeList.Itr)iterator).valid = false;
                    }
                    continue;
                }
                else {
                    if (!pathfindergoalselectoritem2.a.i() && this.b.contains(pathfindergoalselectoritem2)) {
                        this.c.b();
                        return ((UnsafeList.Itr)iterator).valid = false;
                    }
                    continue;
                }
            }
        }
        this.c.b();
        return true;
    }
    
    private boolean a(final PathfinderGoalSelectorItem pathfindergoalselectoritem, final PathfinderGoalSelectorItem pathfindergoalselectoritem1) {
        return (pathfindergoalselectoritem.a.j() & pathfindergoalselectoritem1.a.j()) == 0x0;
    }
}
