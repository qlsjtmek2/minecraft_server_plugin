// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class CombatTracker
{
    private final List a;
    private final EntityLiving b;
    private int c;
    private boolean d;
    private boolean e;
    private String f;
    
    public CombatTracker(final EntityLiving b) {
        this.a = new ArrayList();
        this.c = 0;
        this.d = false;
        this.e = false;
        this.b = b;
    }
    
    public void a() {
        this.g();
        if (this.b.g_()) {
            final int typeId = this.b.world.getTypeId(MathHelper.floor(this.b.locX), MathHelper.floor(this.b.boundingBox.b), MathHelper.floor(this.b.locZ));
            if (typeId == Block.LADDER.id) {
                this.f = "ladder";
            }
            else if (typeId == Block.VINE.id) {
                this.f = "vines";
            }
        }
        else if (this.b.G()) {
            this.f = "water";
        }
    }
    
    public void a(final DamageSource damageSource, final int n, final int n2) {
        this.h();
        this.a();
        final CombatEntry combatEntry = new CombatEntry(damageSource, this.b.ticksLived, n, n2, this.f, this.b.fallDistance);
        this.a.add(combatEntry);
        this.c = this.b.ticksLived;
        this.e = true;
        this.d |= combatEntry.f();
    }
    
    public String b() {
        if (this.a.size() == 0) {
            return this.b.getScoreboardDisplayName() + " died";
        }
        final CombatEntry f = this.f();
        final CombatEntry combatEntry = this.a.get(this.a.size() - 1);
        final String h = combatEntry.h();
        final Entity entity = combatEntry.a().getEntity();
        String s;
        if (f != null && combatEntry.a() == DamageSource.FALL) {
            final String h2 = f.h();
            if (f.a() == DamageSource.FALL || f.a() == DamageSource.OUT_OF_WORLD) {
                s = LocaleI18n.get("death.fell.accident." + this.a(f), this.b.getScoreboardDisplayName());
            }
            else if (h2 != null && (h == null || !h2.equals(h))) {
                final Entity entity2 = f.a().getEntity();
                final ItemStack itemStack = (entity2 instanceof EntityLiving) ? ((EntityLiving)entity2).bG() : null;
                if (itemStack != null && itemStack.hasName()) {
                    s = LocaleI18n.get("death.fell.assist.item", this.b.getScoreboardDisplayName(), h, itemStack.getName());
                }
                else {
                    s = LocaleI18n.get("death.fell.assist", this.b.getScoreboardDisplayName(), h2);
                }
            }
            else if (h != null) {
                final ItemStack itemStack2 = (entity instanceof EntityLiving) ? ((EntityLiving)entity).bG() : null;
                if (itemStack2 != null && itemStack2.hasName()) {
                    s = LocaleI18n.get("death.fell.finish.item", this.b.getScoreboardDisplayName(), h, itemStack2.getName());
                }
                else {
                    s = LocaleI18n.get("death.fell.finish", this.b.getScoreboardDisplayName(), h);
                }
            }
            else {
                s = LocaleI18n.get("death.fell.killer", this.b.getScoreboardDisplayName());
            }
        }
        else {
            s = combatEntry.a().getLocalizedDeathMessage(this.b);
        }
        return s;
    }
    
    public EntityLiving c() {
        EntityLiving entityLiving = null;
        EntityLiving entityLiving2 = null;
        int c = 0;
        int c2 = 0;
        for (final CombatEntry combatEntry : this.a) {
            if (combatEntry.a().getEntity() instanceof EntityHuman && (entityLiving2 == null || combatEntry.c() > c2)) {
                c2 = combatEntry.c();
                entityLiving2 = (EntityHuman)combatEntry.a().getEntity();
            }
            if (combatEntry.a().getEntity() instanceof EntityLiving && (entityLiving == null || combatEntry.c() > c)) {
                c = combatEntry.c();
                entityLiving = (EntityLiving)combatEntry.a().getEntity();
            }
        }
        if (entityLiving2 != null && c2 >= c / 3) {
            return entityLiving2;
        }
        return entityLiving;
    }
    
    private CombatEntry f() {
        CombatEntry combatEntry = null;
        CombatEntry combatEntry2 = null;
        final int n = 0;
        float i = 0.0f;
        for (int j = 0; j < this.a.size(); ++j) {
            final CombatEntry combatEntry3 = this.a.get(j);
            final CombatEntry combatEntry4 = (j > 0) ? this.a.get(j - 1) : null;
            if ((combatEntry3.a() == DamageSource.FALL || combatEntry3.a() == DamageSource.OUT_OF_WORLD) && combatEntry3.i() > 0.0f && (combatEntry == null || combatEntry3.i() > i)) {
                if (j > 0) {
                    combatEntry = combatEntry4;
                }
                else {
                    combatEntry = combatEntry3;
                }
                i = combatEntry3.i();
            }
            if (combatEntry3.g() != null && (combatEntry2 == null || combatEntry3.c() > n)) {
                combatEntry2 = combatEntry3;
            }
        }
        if (i > 5.0f && combatEntry != null) {
            return combatEntry;
        }
        if (n > 5 && combatEntry2 != null) {
            return combatEntry2;
        }
        return null;
    }
    
    private String a(final CombatEntry combatEntry) {
        return (combatEntry.g() == null) ? "generic" : combatEntry.g();
    }
    
    private void g() {
        this.f = null;
    }
    
    private void h() {
        final int n = this.d ? 300 : 100;
        if (this.e && this.b.ticksLived - this.c > n) {
            this.a.clear();
            this.e = false;
            this.d = false;
        }
    }
}
