// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

final class EnchantmentModifierDamage implements EnchantmentModifier
{
    public int a;
    public EntityLiving b;
    
    public void a(final Enchantment enchantment, final int i) {
        this.a += enchantment.a(i, this.b);
    }
}
