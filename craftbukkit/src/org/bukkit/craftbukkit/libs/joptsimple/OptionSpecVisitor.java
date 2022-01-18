// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.joptsimple;

interface OptionSpecVisitor
{
    void visit(final NoArgumentOptionSpec p0);
    
    void visit(final RequiredArgumentOptionSpec<?> p0);
    
    void visit(final OptionalArgumentOptionSpec<?> p0);
    
    void visit(final AlternativeLongOptionSpec p0);
}
