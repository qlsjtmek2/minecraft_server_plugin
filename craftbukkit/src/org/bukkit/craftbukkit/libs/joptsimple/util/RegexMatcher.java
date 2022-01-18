// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.joptsimple.util;

import org.bukkit.craftbukkit.libs.joptsimple.ValueConversionException;
import java.util.regex.Pattern;
import org.bukkit.craftbukkit.libs.joptsimple.ValueConverter;

public class RegexMatcher implements ValueConverter<String>
{
    private final Pattern pattern;
    
    public RegexMatcher(final String pattern, final int flags) {
        this.pattern = Pattern.compile(pattern, flags);
    }
    
    public static ValueConverter<String> regex(final String pattern) {
        return new RegexMatcher(pattern, 0);
    }
    
    public String convert(final String value) {
        if (!this.pattern.matcher(value).matches()) {
            throw new ValueConversionException("Value [" + value + "] did not match regex [" + this.pattern.pattern() + ']');
        }
        return value;
    }
    
    public Class<String> valueType() {
        return String.class;
    }
    
    public String valuePattern() {
        return this.pattern.pattern();
    }
}
