// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.util.Currency;

public class ScalarTypeCurrency extends ScalarTypeBaseVarchar<Currency>
{
    public ScalarTypeCurrency() {
        super(Currency.class);
    }
    
    public int getLength() {
        return 3;
    }
    
    public Currency convertFromDbString(final String dbValue) {
        return Currency.getInstance(dbValue);
    }
    
    public String convertToDbString(final Currency beanValue) {
        return beanValue.getCurrencyCode();
    }
    
    public String formatValue(final Currency v) {
        return v.toString();
    }
    
    public Currency parse(final String value) {
        return Currency.getInstance(value);
    }
}
