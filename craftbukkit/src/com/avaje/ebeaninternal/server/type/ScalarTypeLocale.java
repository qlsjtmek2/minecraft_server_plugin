// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.util.Locale;

public class ScalarTypeLocale extends ScalarTypeBaseVarchar<Locale>
{
    public ScalarTypeLocale() {
        super(Locale.class);
    }
    
    public int getLength() {
        return 20;
    }
    
    public Locale convertFromDbString(final String dbValue) {
        return this.parse(dbValue);
    }
    
    public String convertToDbString(final Locale beanValue) {
        return beanValue.toString();
    }
    
    public String formatValue(final Locale t) {
        return t.toString();
    }
    
    public Locale parse(final String value) {
        int pos1 = -1;
        int pos2 = -1;
        for (int i = 0; i < value.length(); ++i) {
            final char c = value.charAt(i);
            if (c == '_') {
                if (pos1 > -1) {
                    pos2 = i;
                    break;
                }
                pos1 = i;
            }
        }
        if (pos1 == -1) {
            return new Locale(value);
        }
        final String language = value.substring(0, pos1);
        if (pos2 == -1) {
            final String country = value.substring(pos1 + 1);
            return new Locale(language, country);
        }
        final String country = value.substring(pos1 + 1, pos2);
        final String variant = value.substring(pos2 + 1);
        return new Locale(language, country, variant);
    }
}
