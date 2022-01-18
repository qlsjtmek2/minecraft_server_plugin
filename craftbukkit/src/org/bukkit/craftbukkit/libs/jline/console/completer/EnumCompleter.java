// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.jline.console.completer;

public class EnumCompleter extends StringsCompleter
{
    public EnumCompleter(final Class<? extends Enum> source) {
        assert source != null;
        for (final Enum<?> n : (Enum[])source.getEnumConstants()) {
            this.getStrings().add(n.name().toLowerCase());
        }
    }
}
