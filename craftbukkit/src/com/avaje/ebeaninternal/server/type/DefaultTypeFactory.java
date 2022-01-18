// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import com.avaje.ebean.config.ServerConfig;

public class DefaultTypeFactory
{
    private final ServerConfig serverConfig;
    
    public DefaultTypeFactory(final ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }
    
    private ScalarType<Boolean> createBoolean(final String trueValue, final String falseValue) {
        try {
            final Integer intTrue = BasicTypeConverter.toInteger(trueValue);
            final Integer intFalse = BasicTypeConverter.toInteger(falseValue);
            return new ScalarTypeBoolean.IntBoolean(intTrue, intFalse);
        }
        catch (NumberFormatException e) {
            return new ScalarTypeBoolean.StringBoolean(trueValue, falseValue);
        }
    }
    
    public ScalarType<Boolean> createBoolean() {
        if (this.serverConfig == null) {
            return new ScalarTypeBoolean.Native();
        }
        final String trueValue = this.serverConfig.getDatabaseBooleanTrue();
        final String falseValue = this.serverConfig.getDatabaseBooleanFalse();
        if (falseValue != null && trueValue != null) {
            return this.createBoolean(trueValue, falseValue);
        }
        final int booleanDbType = this.serverConfig.getDatabasePlatform().getBooleanDbType();
        if (booleanDbType == -7) {
            return new ScalarTypeBoolean.BitBoolean();
        }
        if (booleanDbType == 4) {
            return new ScalarTypeBoolean.IntBoolean(Integer.valueOf(1), Integer.valueOf(0));
        }
        if (booleanDbType == 12) {
            return new ScalarTypeBoolean.StringBoolean("T", "F");
        }
        if (booleanDbType == 16) {
            return new ScalarTypeBoolean.Native();
        }
        return new ScalarTypeBoolean.Native();
    }
    
    public ScalarType<Date> createUtilDate() {
        final int utilDateType = this.getTemporalMapType("timestamp");
        return this.createUtilDate(utilDateType);
    }
    
    public ScalarType<Date> createUtilDate(final int utilDateType) {
        switch (utilDateType) {
            case 91: {
                return new ScalarTypeUtilDate.DateType();
            }
            case 93: {
                return new ScalarTypeUtilDate.TimestampType();
            }
            default: {
                throw new RuntimeException("Invalid type " + utilDateType);
            }
        }
    }
    
    public ScalarType<Calendar> createCalendar() {
        final int jdbcType = this.getTemporalMapType("timestamp");
        return this.createCalendar(jdbcType);
    }
    
    public ScalarType<Calendar> createCalendar(final int jdbcType) {
        return new ScalarTypeCalendar(jdbcType);
    }
    
    private int getTemporalMapType(final String mapType) {
        if (mapType.equalsIgnoreCase("date")) {
            return 91;
        }
        return 93;
    }
    
    public ScalarType<BigInteger> createMathBigInteger() {
        return new ScalarTypeMathBigInteger();
    }
}
