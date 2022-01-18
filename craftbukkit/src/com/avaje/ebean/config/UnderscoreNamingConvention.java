// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config;

public class UnderscoreNamingConvention extends AbstractNamingConvention
{
    private boolean forceUpperCase;
    private boolean digitsCompressed;
    
    public UnderscoreNamingConvention(final String sequenceFormat) {
        super(sequenceFormat);
        this.forceUpperCase = false;
        this.digitsCompressed = true;
    }
    
    public UnderscoreNamingConvention() {
        this.forceUpperCase = false;
        this.digitsCompressed = true;
    }
    
    public TableName getTableNameByConvention(final Class<?> beanClass) {
        return new TableName(this.getCatalog(), this.getSchema(), this.toUnderscoreFromCamel(beanClass.getSimpleName()));
    }
    
    public String getColumnFromProperty(final Class<?> beanClass, final String propertyName) {
        return this.toUnderscoreFromCamel(propertyName);
    }
    
    public String getPropertyFromColumn(final Class<?> beanClass, final String dbColumnName) {
        return this.toCamelFromUnderscore(dbColumnName);
    }
    
    public boolean isForceUpperCase() {
        return this.forceUpperCase;
    }
    
    public void setForceUpperCase(final boolean forceUpperCase) {
        this.forceUpperCase = forceUpperCase;
    }
    
    public boolean isDigitsCompressed() {
        return this.digitsCompressed;
    }
    
    public void setDigitsCompressed(final boolean digitsCompressed) {
        this.digitsCompressed = digitsCompressed;
    }
    
    protected String toUnderscoreFromCamel(final String camelCase) {
        int lastUpper = -1;
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < camelCase.length(); ++i) {
            final char c = camelCase.charAt(i);
            if ('_' == c) {
                sb.append(c);
                lastUpper = i;
            }
            else if (Character.isDigit(c)) {
                if (i > lastUpper + 1 && !this.digitsCompressed) {
                    sb.append("_");
                }
                sb.append(c);
                lastUpper = i;
            }
            else if (Character.isUpperCase(c)) {
                if (i > lastUpper + 1) {
                    sb.append("_");
                }
                sb.append(Character.toLowerCase(c));
                lastUpper = i;
            }
            else {
                sb.append(c);
            }
        }
        String ret = sb.toString();
        if (this.forceUpperCase) {
            ret = ret.toUpperCase();
        }
        return ret;
    }
    
    protected String toCamelFromUnderscore(final String underscore) {
        final StringBuffer result = new StringBuffer();
        final String[] vals = underscore.split("_");
        for (int i = 0; i < vals.length; ++i) {
            final String lower = vals[i].toLowerCase();
            if (i > 0) {
                final char c = Character.toUpperCase(lower.charAt(0));
                result.append(c);
                result.append(lower.substring(1));
            }
            else {
                result.append(lower);
            }
        }
        return result.toString();
    }
}
