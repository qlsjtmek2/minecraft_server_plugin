// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.sql.Timestamp;
import java.sql.Time;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;
import java.util.UUID;
import java.util.TimeZone;
import java.util.Currency;
import java.util.Locale;
import java.math.BigInteger;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import com.avaje.ebeaninternal.api.ClassUtil;
import com.avaje.ebeaninternal.server.type.reflect.ReflectionBasedCompoundTypeProperty;
import com.avaje.ebean.config.CompoundTypeProperty;
import java.util.Arrays;
import com.avaje.ebean.config.ScalarTypeConverter;
import java.util.Iterator;
import com.avaje.ebeaninternal.server.lib.util.StringHelper;
import com.avaje.ebean.annotation.EnumMapping;
import java.lang.reflect.Field;
import java.util.Map;
import com.avaje.ebean.annotation.EnumValue;
import java.util.HashMap;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import com.avaje.ebeaninternal.server.type.reflect.ReflectionBasedCompoundType;
import com.avaje.ebeaninternal.server.type.reflect.ImmutableMeta;
import com.avaje.ebean.config.CompoundType;
import com.avaje.ebeaninternal.server.type.reflect.CheckImmutableResponse;
import java.util.ArrayList;
import com.avaje.ebeaninternal.server.core.BootupClasses;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebeaninternal.server.type.reflect.ReflectionBasedTypeBuilder;
import com.avaje.ebeaninternal.server.type.reflect.ImmutableMetaFactory;
import com.avaje.ebeaninternal.server.type.reflect.CheckImmutable;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import com.avaje.ebeaninternal.server.type.reflect.KnownImmutable;

public final class DefaultTypeManager implements TypeManager, KnownImmutable
{
    private static final Logger logger;
    private final ConcurrentHashMap<Class<?>, CtCompoundType<?>> compoundTypeMap;
    private final ConcurrentHashMap<Class<?>, ScalarType<?>> typeMap;
    private final ConcurrentHashMap<Integer, ScalarType<?>> nativeMap;
    private final DefaultTypeFactory extraTypeFactory;
    private final ScalarType<?> charType;
    private final ScalarType<?> charArrayType;
    private final ScalarType<?> longVarcharType;
    private final ScalarType<?> clobType;
    private final ScalarType<?> byteType;
    private final ScalarType<?> binaryType;
    private final ScalarType<?> blobType;
    private final ScalarType<?> varbinaryType;
    private final ScalarType<?> longVarbinaryType;
    private final ScalarType<?> shortType;
    private final ScalarType<?> integerType;
    private final ScalarType<?> longType;
    private final ScalarType<?> doubleType;
    private final ScalarType<?> floatType;
    private final ScalarType<?> bigDecimalType;
    private final ScalarType<?> timeType;
    private final ScalarType<?> dateType;
    private final ScalarType<?> timestampType;
    private final ScalarType<?> uuidType;
    private final ScalarType<?> urlType;
    private final ScalarType<?> uriType;
    private final ScalarType<?> localeType;
    private final ScalarType<?> currencyType;
    private final ScalarType<?> timeZoneType;
    private final ScalarType<?> stringType;
    private final ScalarType<?> classType;
    private final ScalarTypeLongToTimestamp longToTimestamp;
    private final List<ScalarType<?>> customScalarTypes;
    private final CheckImmutable checkImmutable;
    private final ImmutableMetaFactory immutableMetaFactory;
    private final ReflectionBasedTypeBuilder reflectScalarBuilder;
    
    public DefaultTypeManager(final ServerConfig config, final BootupClasses bootupClasses) {
        this.charType = new ScalarTypeChar();
        this.charArrayType = new ScalarTypeCharArray();
        this.longVarcharType = new ScalarTypeLongVarchar();
        this.clobType = new ScalarTypeClob();
        this.byteType = new ScalarTypeByte();
        this.binaryType = new ScalarTypeBytesBinary();
        this.blobType = new ScalarTypeBytesBlob();
        this.varbinaryType = new ScalarTypeBytesVarbinary();
        this.longVarbinaryType = new ScalarTypeBytesLongVarbinary();
        this.shortType = new ScalarTypeShort();
        this.integerType = new ScalarTypeInteger();
        this.longType = new ScalarTypeLong();
        this.doubleType = new ScalarTypeDouble();
        this.floatType = new ScalarTypeFloat();
        this.bigDecimalType = new ScalarTypeBigDecimal();
        this.timeType = new ScalarTypeTime();
        this.dateType = new ScalarTypeDate();
        this.timestampType = new ScalarTypeTimestamp();
        this.uuidType = new ScalarTypeUUID();
        this.urlType = new ScalarTypeURL();
        this.uriType = new ScalarTypeURI();
        this.localeType = new ScalarTypeLocale();
        this.currencyType = new ScalarTypeCurrency();
        this.timeZoneType = new ScalarTypeTimeZone();
        this.stringType = new ScalarTypeString();
        this.classType = new ScalarTypeClass();
        this.longToTimestamp = new ScalarTypeLongToTimestamp();
        this.customScalarTypes = new ArrayList<ScalarType<?>>();
        this.immutableMetaFactory = new ImmutableMetaFactory();
        final int clobType = (config == null) ? 2005 : config.getDatabasePlatform().getClobDbType();
        final int blobType = (config == null) ? 2004 : config.getDatabasePlatform().getBlobDbType();
        this.checkImmutable = new CheckImmutable(this);
        this.reflectScalarBuilder = new ReflectionBasedTypeBuilder(this);
        this.compoundTypeMap = new ConcurrentHashMap<Class<?>, CtCompoundType<?>>();
        this.typeMap = new ConcurrentHashMap<Class<?>, ScalarType<?>>();
        this.nativeMap = new ConcurrentHashMap<Integer, ScalarType<?>>();
        this.extraTypeFactory = new DefaultTypeFactory(config);
        this.initialiseStandard(clobType, blobType);
        this.initialiseJodaTypes();
        if (bootupClasses != null) {
            this.initialiseCustomScalarTypes(bootupClasses);
            this.initialiseScalarConverters(bootupClasses);
            this.initialiseCompoundTypes(bootupClasses);
        }
    }
    
    public boolean isKnownImmutable(final Class<?> cls) {
        if (cls == null) {
            return true;
        }
        if (cls.isPrimitive() || Object.class.equals(cls)) {
            return true;
        }
        final ScalarDataReader<?> scalarDataReader = this.getScalarDataReader(cls);
        return scalarDataReader != null;
    }
    
    public CheckImmutableResponse checkImmutable(final Class<?> cls) {
        return this.checkImmutable.checkImmutable(cls);
    }
    
    private ScalarType<?> register(final ScalarType<?> st) {
        this.add(st);
        DefaultTypeManager.logger.info("Registering ScalarType for " + st.getType() + " implemented using reflection");
        return st;
    }
    
    public ScalarDataReader<?> recursiveCreateScalarDataReader(final Class<?> cls) {
        final ScalarDataReader<?> scalarReader = this.getScalarDataReader(cls);
        if (scalarReader != null) {
            return scalarReader;
        }
        final ImmutableMeta meta = this.immutableMetaFactory.createImmutableMeta(cls);
        if (!meta.isCompoundType()) {
            return this.register(this.reflectScalarBuilder.buildScalarType(meta));
        }
        final ReflectionBasedCompoundType compoundType = this.reflectScalarBuilder.buildCompound(meta);
        final Class<?> compoundTypeClass = compoundType.getCompoundType();
        return (ScalarDataReader<?>)this.createCompoundScalarDataReader(compoundTypeClass, compoundType, " using reflection");
    }
    
    public ScalarType<?> recursiveCreateScalarTypes(final Class<?> cls) {
        final ScalarType<?> scalarType = this.getScalarType(cls);
        if (scalarType != null) {
            return scalarType;
        }
        final ImmutableMeta meta = this.immutableMetaFactory.createImmutableMeta(cls);
        if (!meta.isCompoundType()) {
            return this.register(this.reflectScalarBuilder.buildScalarType(meta));
        }
        throw new RuntimeException("Not allowed compound types here");
    }
    
    public void add(final ScalarType<?> scalarType) {
        this.typeMap.put(scalarType.getType(), scalarType);
        this.logAdd(scalarType);
    }
    
    protected void logAdd(final ScalarType<?> scalarType) {
        if (DefaultTypeManager.logger.isLoggable(Level.FINE)) {
            String msg = "ScalarType register [" + scalarType.getClass().getName() + "]";
            msg = msg + " for [" + scalarType.getType().getName() + "]";
            DefaultTypeManager.logger.fine(msg);
        }
    }
    
    public CtCompoundType<?> getCompoundType(final Class<?> type) {
        return this.compoundTypeMap.get(type);
    }
    
    public ScalarType<?> getScalarType(final int jdbcType) {
        return this.nativeMap.get(jdbcType);
    }
    
    public <T> ScalarType<T> getScalarType(final Class<T> type) {
        return (ScalarType<T>)this.typeMap.get(type);
    }
    
    public ScalarDataReader<?> getScalarDataReader(final Class<?> propertyType, final int sqlType) {
        if (sqlType == 0) {
            return this.recursiveCreateScalarDataReader(propertyType);
        }
        for (int i = 0; i < this.customScalarTypes.size(); ++i) {
            final ScalarType<?> customScalarType = this.customScalarTypes.get(i);
            if (sqlType == customScalarType.getJdbcType() && propertyType.equals(customScalarType.getType())) {
                return customScalarType;
            }
        }
        final String msg = "Unable to find a custom ScalarType with type [" + propertyType + "] and java.sql.Type [" + sqlType + "]";
        throw new RuntimeException(msg);
    }
    
    public ScalarDataReader<?> getScalarDataReader(final Class<?> type) {
        ScalarDataReader<?> reader = this.typeMap.get(type);
        if (reader == null) {
            reader = this.compoundTypeMap.get(type);
        }
        return reader;
    }
    
    public <T> ScalarType<T> getScalarType(final Class<T> type, final int jdbcType) {
        ScalarType<?> scalarType = this.getLobTypes(jdbcType);
        if (scalarType != null) {
            return (ScalarType<T>)scalarType;
        }
        scalarType = this.typeMap.get(type);
        if (scalarType != null && (jdbcType == 0 || scalarType.getJdbcType() == jdbcType)) {
            return (ScalarType<T>)scalarType;
        }
        if (type.equals(Date.class)) {
            return (ScalarType<T>)this.extraTypeFactory.createUtilDate(jdbcType);
        }
        if (type.equals(Calendar.class)) {
            return (ScalarType<T>)this.extraTypeFactory.createCalendar(jdbcType);
        }
        final String msg = "Unmatched ScalarType for " + type + " jdbcType:" + jdbcType;
        throw new RuntimeException(msg);
    }
    
    private ScalarType<?> getLobTypes(final int jdbcType) {
        return this.getScalarType(jdbcType);
    }
    
    public Object convert(final Object value, final int toJdbcType) {
        if (value == null) {
            return null;
        }
        final ScalarType<?> type = this.nativeMap.get(toJdbcType);
        if (type != null) {
            return type.toJdbcType(value);
        }
        return value;
    }
    
    private boolean isIntegerType(final String s) {
        try {
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
    
    private ScalarType<?> createEnumScalarType2(final Class<?> enumType) {
        boolean integerType = true;
        final Map<String, String> nameValueMap = new HashMap<String, String>();
        final Field[] fields = enumType.getDeclaredFields();
        for (int i = 0; i < fields.length; ++i) {
            final EnumValue enumValue = fields[i].getAnnotation(EnumValue.class);
            if (enumValue != null) {
                nameValueMap.put(fields[i].getName(), enumValue.value());
                if (integerType && !this.isIntegerType(enumValue.value())) {
                    integerType = false;
                }
            }
        }
        if (nameValueMap.isEmpty()) {
            return null;
        }
        return this.createEnumScalarType(enumType, nameValueMap, integerType, 0);
    }
    
    public ScalarType<?> createEnumScalarType(final Class<?> enumType) {
        final EnumMapping enumMapping = enumType.getAnnotation(EnumMapping.class);
        if (enumMapping == null) {
            return this.createEnumScalarType2(enumType);
        }
        final String nameValuePairs = enumMapping.nameValuePairs();
        final boolean integerType = enumMapping.integerType();
        final int dbColumnLength = enumMapping.length();
        final Map<String, String> nameValueMap = StringHelper.delimitedToMap(nameValuePairs, ",", "=");
        return this.createEnumScalarType(enumType, nameValueMap, integerType, dbColumnLength);
    }
    
    private ScalarType<?> createEnumScalarType(final Class enumType, final Map<String, String> nameValueMap, final boolean integerType, int dbColumnLength) {
        final EnumToDbValueMap<?> beanDbMap = EnumToDbValueMap.create(integerType);
        int maxValueLen = 0;
        for (final Map.Entry entry : nameValueMap.entrySet()) {
            final String name = entry.getKey();
            final String value = entry.getValue();
            maxValueLen = Math.max(maxValueLen, value.length());
            final Object enumValue = Enum.valueOf((Class<Object>)enumType, name.trim());
            beanDbMap.add(enumValue, value.trim());
        }
        if (dbColumnLength == 0 && !integerType) {
            dbColumnLength = maxValueLen;
        }
        return (ScalarType<?>)new ScalarTypeEnumWithMapping(beanDbMap, enumType, dbColumnLength);
    }
    
    protected void initialiseCustomScalarTypes(final BootupClasses bootupClasses) {
        this.customScalarTypes.add(this.longToTimestamp);
        final List<Class<?>> foundTypes = bootupClasses.getScalarTypes();
        for (int i = 0; i < foundTypes.size(); ++i) {
            final Class<?> cls = foundTypes.get(i);
            try {
                final ScalarType<?> scalarType = (ScalarType<?>)cls.newInstance();
                this.add(scalarType);
                this.customScalarTypes.add(scalarType);
            }
            catch (Exception e) {
                final String msg = "Error loading ScalarType [" + cls.getName() + "]";
                DefaultTypeManager.logger.log(Level.SEVERE, msg, e);
            }
        }
    }
    
    protected void initialiseScalarConverters(final BootupClasses bootupClasses) {
        final List<Class<?>> foundTypes = bootupClasses.getScalarConverters();
        for (int i = 0; i < foundTypes.size(); ++i) {
            final Class<?> cls = foundTypes.get(i);
            try {
                final Class<?>[] paramTypes = TypeReflectHelper.getParams(cls, ScalarTypeConverter.class);
                if (paramTypes.length != 2) {
                    throw new IllegalStateException("Expected 2 generics paramtypes but got: " + Arrays.toString(paramTypes));
                }
                final Class<?> logicalType = paramTypes[0];
                final Class<?> persistType = paramTypes[1];
                final ScalarType<?> wrappedType = this.getScalarType(persistType);
                if (wrappedType == null) {
                    throw new IllegalStateException("Could not find ScalarType for: " + paramTypes[1]);
                }
                final ScalarTypeConverter converter = (ScalarTypeConverter)cls.newInstance();
                final ScalarTypeWrapper stw = new ScalarTypeWrapper((Class<B>)logicalType, (ScalarType<S>)wrappedType, converter);
                DefaultTypeManager.logger.info("Register ScalarTypeWrapper from " + logicalType + " -> " + persistType + " using:" + cls);
                this.add(stw);
            }
            catch (Exception e) {
                final String msg = "Error loading ScalarType [" + cls.getName() + "]";
                DefaultTypeManager.logger.log(Level.SEVERE, msg, e);
            }
        }
    }
    
    protected void initialiseCompoundTypes(final BootupClasses bootupClasses) {
        final ArrayList<Class<?>> compoundTypes = bootupClasses.getCompoundTypes();
        for (int j = 0; j < compoundTypes.size(); ++j) {
            final Class<?> type = compoundTypes.get(j);
            try {
                final Class<?>[] paramTypes = TypeReflectHelper.getParams(type, CompoundType.class);
                if (paramTypes.length != 1) {
                    throw new RuntimeException("Expecting 1 generic paramter type but got " + Arrays.toString(paramTypes) + " for " + type);
                }
                final Class<?> compoundTypeClass = paramTypes[0];
                final CompoundType<?> compoundType = (CompoundType<?>)type.newInstance();
                this.createCompoundScalarDataReader(compoundTypeClass, compoundType, "");
            }
            catch (Exception e) {
                final String msg = "Error initialising component " + type;
                throw new RuntimeException(msg, e);
            }
        }
    }
    
    protected CtCompoundType createCompoundScalarDataReader(final Class<?> compoundTypeClass, final CompoundType<?> compoundType, final String info) {
        final CtCompoundType<?> ctCompoundType = this.compoundTypeMap.get(compoundTypeClass);
        if (ctCompoundType != null) {
            DefaultTypeManager.logger.info("Already registered compound type " + compoundTypeClass);
            return ctCompoundType;
        }
        final CompoundTypeProperty<?, ?>[] cprops = compoundType.getProperties();
        final ScalarDataReader[] dataReaders = new ScalarDataReader[cprops.length];
        for (int i = 0; i < cprops.length; ++i) {
            final Class<?> propertyType = this.getCompoundPropertyType(cprops[i]);
            final ScalarDataReader<?> scalarDataReader = this.getScalarDataReader(propertyType, cprops[i].getDbType());
            if (scalarDataReader == null) {
                throw new RuntimeException("Could not find ScalarDataReader for " + propertyType);
            }
            dataReaders[i] = scalarDataReader;
        }
        final CtCompoundType ctType = new CtCompoundType((Class<V>)compoundTypeClass, (CompoundType<V>)compoundType, dataReaders);
        DefaultTypeManager.logger.info("Registering CompoundType " + compoundTypeClass + " " + info);
        this.compoundTypeMap.put(compoundTypeClass, ctType);
        return ctType;
    }
    
    private Class<?> getCompoundPropertyType(final CompoundTypeProperty<?, ?> prop) {
        if (prop instanceof ReflectionBasedCompoundTypeProperty) {
            return ((ReflectionBasedCompoundTypeProperty)prop).getPropertyType();
        }
        final Class<?>[] propParamTypes = TypeReflectHelper.getParams(prop.getClass(), CompoundTypeProperty.class);
        if (propParamTypes.length != 2) {
            throw new RuntimeException("Expecting 2 generic paramter types but got " + Arrays.toString(propParamTypes) + " for " + prop.getClass());
        }
        return propParamTypes[1];
    }
    
    protected void initialiseJodaTypes() {
        if (ClassUtil.isPresent("org.joda.time.LocalDateTime", this.getClass())) {
            final String msg = "Registering Joda data types";
            DefaultTypeManager.logger.log(Level.INFO, msg);
            this.typeMap.put(LocalDateTime.class, new ScalarTypeJodaLocalDateTime());
            this.typeMap.put(LocalDate.class, new ScalarTypeJodaLocalDate());
            this.typeMap.put(LocalTime.class, new ScalarTypeJodaLocalTime());
            this.typeMap.put(DateTime.class, new ScalarTypeJodaDateTime());
            this.typeMap.put(DateMidnight.class, new ScalarTypeJodaDateMidnight());
        }
    }
    
    protected void initialiseStandard(final int platformClobType, final int platformBlobType) {
        final ScalarType<?> utilDateType = this.extraTypeFactory.createUtilDate();
        this.typeMap.put(Date.class, utilDateType);
        final ScalarType<?> calType = this.extraTypeFactory.createCalendar();
        this.typeMap.put(Calendar.class, calType);
        final ScalarType<?> mathBigIntType = this.extraTypeFactory.createMathBigInteger();
        this.typeMap.put(BigInteger.class, mathBigIntType);
        final ScalarType<?> booleanType = this.extraTypeFactory.createBoolean();
        this.typeMap.put(Boolean.class, booleanType);
        this.typeMap.put(Boolean.TYPE, booleanType);
        this.nativeMap.put(16, booleanType);
        if (booleanType.getJdbcType() == -7) {
            this.nativeMap.put(-7, booleanType);
        }
        this.typeMap.put(Locale.class, this.localeType);
        this.typeMap.put(Currency.class, this.currencyType);
        this.typeMap.put(TimeZone.class, this.timeZoneType);
        this.typeMap.put(UUID.class, this.uuidType);
        this.typeMap.put(URL.class, this.urlType);
        this.typeMap.put(URI.class, this.uriType);
        this.typeMap.put(char[].class, this.charArrayType);
        this.typeMap.put(Character.TYPE, this.charType);
        this.typeMap.put(String.class, this.stringType);
        this.nativeMap.put(12, this.stringType);
        this.nativeMap.put(1, this.stringType);
        this.nativeMap.put(-1, this.longVarcharType);
        this.typeMap.put(Class.class, this.classType);
        if (platformClobType == 2005) {
            this.nativeMap.put(2005, this.clobType);
        }
        else {
            final ScalarType<?> platClobScalarType = this.nativeMap.get(platformClobType);
            if (platClobScalarType == null) {
                throw new IllegalArgumentException("Type for dbPlatform clobType [" + this.clobType + "] not found.");
            }
            this.nativeMap.put(2005, platClobScalarType);
        }
        this.typeMap.put(byte[].class, this.varbinaryType);
        this.nativeMap.put(-2, this.binaryType);
        this.nativeMap.put(-3, this.varbinaryType);
        this.nativeMap.put(-4, this.longVarbinaryType);
        if (platformBlobType == 2004) {
            this.nativeMap.put(2004, this.blobType);
        }
        else {
            final ScalarType<?> platBlobScalarType = this.nativeMap.get(platformBlobType);
            if (platBlobScalarType == null) {
                throw new IllegalArgumentException("Type for dbPlatform blobType [" + this.blobType + "] not found.");
            }
            this.nativeMap.put(2004, platBlobScalarType);
        }
        this.typeMap.put(Byte.class, this.byteType);
        this.typeMap.put(Byte.TYPE, this.byteType);
        this.nativeMap.put(-6, this.byteType);
        this.typeMap.put(Short.class, this.shortType);
        this.typeMap.put(Short.TYPE, this.shortType);
        this.nativeMap.put(5, this.shortType);
        this.typeMap.put(Integer.class, this.integerType);
        this.typeMap.put(Integer.TYPE, this.integerType);
        this.nativeMap.put(4, this.integerType);
        this.typeMap.put(Long.class, this.longType);
        this.typeMap.put(Long.TYPE, this.longType);
        this.nativeMap.put(-5, this.longType);
        this.typeMap.put(Double.class, this.doubleType);
        this.typeMap.put(Double.TYPE, this.doubleType);
        this.nativeMap.put(6, this.doubleType);
        this.nativeMap.put(8, this.doubleType);
        this.typeMap.put(Float.class, this.floatType);
        this.typeMap.put(Float.TYPE, this.floatType);
        this.nativeMap.put(7, this.floatType);
        this.typeMap.put(BigDecimal.class, this.bigDecimalType);
        this.nativeMap.put(3, this.bigDecimalType);
        this.nativeMap.put(2, this.bigDecimalType);
        this.typeMap.put(Time.class, this.timeType);
        this.nativeMap.put(92, this.timeType);
        this.typeMap.put(java.sql.Date.class, this.dateType);
        this.nativeMap.put(91, this.dateType);
        this.typeMap.put(Timestamp.class, this.timestampType);
        this.nativeMap.put(93, this.timestampType);
    }
    
    static {
        logger = Logger.getLogger(DefaultTypeManager.class.getName());
    }
}
