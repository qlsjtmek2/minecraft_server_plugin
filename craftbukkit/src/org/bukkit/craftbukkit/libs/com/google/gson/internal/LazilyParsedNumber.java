// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.com.google.gson.internal;

import java.math.BigInteger;

public final class LazilyParsedNumber extends Number
{
    private final String value;
    
    public LazilyParsedNumber(final String value) {
        this.value = value;
    }
    
    public int intValue() {
        try {
            return Integer.parseInt(this.value);
        }
        catch (NumberFormatException e) {
            try {
                return (int)Long.parseLong(this.value);
            }
            catch (NumberFormatException nfe) {
                return new BigInteger(this.value).intValue();
            }
        }
    }
    
    public long longValue() {
        try {
            return Long.parseLong(this.value);
        }
        catch (NumberFormatException e) {
            return new BigInteger(this.value).longValue();
        }
    }
    
    public float floatValue() {
        return Float.parseFloat(this.value);
    }
    
    public double doubleValue() {
        return Double.parseDouble(this.value);
    }
    
    public String toString() {
        return this.value;
    }
}
