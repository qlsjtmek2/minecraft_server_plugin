// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.util;

import net.minecraft.server.v1_5_R3.DamageSource;

public final class CraftDamageSource extends DamageSource
{
    public static DamageSource copyOf(final DamageSource original) {
        final CraftDamageSource newSource = new CraftDamageSource(original.translationIndex);
        if (original.ignoresArmor()) {
            newSource.j();
        }
        if (original.q()) {
            newSource.r();
        }
        if (original.c()) {
            newSource.l();
        }
        return newSource;
    }
    
    private CraftDamageSource(final String identifier) {
        super(identifier);
    }
}
