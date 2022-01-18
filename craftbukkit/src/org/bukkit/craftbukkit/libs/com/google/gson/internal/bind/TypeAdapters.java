// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.com.google.gson.internal.bind;

import org.bukkit.craftbukkit.libs.com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonObject;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonArray;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonNull;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonPrimitive;
import java.util.StringTokenizer;
import java.util.GregorianCalendar;
import java.util.Date;
import java.sql.Timestamp;
import java.net.URISyntaxException;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonIOException;
import org.bukkit.craftbukkit.libs.com.google.gson.internal.LazilyParsedNumber;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonSyntaxException;
import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonToken;
import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonReader;
import java.io.IOException;
import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonWriter;
import org.bukkit.craftbukkit.libs.com.google.gson.reflect.TypeToken;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonElement;
import java.util.Locale;
import java.util.Calendar;
import java.util.UUID;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.util.BitSet;
import org.bukkit.craftbukkit.libs.com.google.gson.TypeAdapterFactory;
import org.bukkit.craftbukkit.libs.com.google.gson.TypeAdapter;

public final class TypeAdapters
{
    public static final TypeAdapter<Class> CLASS;
    public static final TypeAdapterFactory CLASS_FACTORY;
    public static final TypeAdapter<BitSet> BIT_SET;
    public static final TypeAdapterFactory BIT_SET_FACTORY;
    public static final TypeAdapter<Boolean> BOOLEAN;
    public static final TypeAdapter<Boolean> BOOLEAN_AS_STRING;
    public static final TypeAdapterFactory BOOLEAN_FACTORY;
    public static final TypeAdapter<Number> BYTE;
    public static final TypeAdapterFactory BYTE_FACTORY;
    public static final TypeAdapter<Number> SHORT;
    public static final TypeAdapterFactory SHORT_FACTORY;
    public static final TypeAdapter<Number> INTEGER;
    public static final TypeAdapterFactory INTEGER_FACTORY;
    public static final TypeAdapter<Number> LONG;
    public static final TypeAdapter<Number> FLOAT;
    public static final TypeAdapter<Number> DOUBLE;
    public static final TypeAdapter<Number> NUMBER;
    public static final TypeAdapterFactory NUMBER_FACTORY;
    public static final TypeAdapter<Character> CHARACTER;
    public static final TypeAdapterFactory CHARACTER_FACTORY;
    public static final TypeAdapter<String> STRING;
    public static final TypeAdapterFactory STRING_FACTORY;
    public static final TypeAdapter<StringBuilder> STRING_BUILDER;
    public static final TypeAdapterFactory STRING_BUILDER_FACTORY;
    public static final TypeAdapter<StringBuffer> STRING_BUFFER;
    public static final TypeAdapterFactory STRING_BUFFER_FACTORY;
    public static final TypeAdapter<URL> URL;
    public static final TypeAdapterFactory URL_FACTORY;
    public static final TypeAdapter<URI> URI;
    public static final TypeAdapterFactory URI_FACTORY;
    public static final TypeAdapter<InetAddress> INET_ADDRESS;
    public static final TypeAdapterFactory INET_ADDRESS_FACTORY;
    public static final TypeAdapter<UUID> UUID;
    public static final TypeAdapterFactory UUID_FACTORY;
    public static final TypeAdapterFactory TIMESTAMP_FACTORY;
    public static final TypeAdapter<Calendar> CALENDAR;
    public static final TypeAdapterFactory CALENDAR_FACTORY;
    public static final TypeAdapter<Locale> LOCALE;
    public static final TypeAdapterFactory LOCALE_FACTORY;
    public static final TypeAdapter<JsonElement> JSON_ELEMENT;
    public static final TypeAdapterFactory JSON_ELEMENT_FACTORY;
    public static final TypeAdapterFactory ENUM_FACTORY;
    
    public static <TT> TypeAdapterFactory newEnumTypeHierarchyFactory() {
        return new TypeAdapterFactory() {
            public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
                Class<? super T> rawType = typeToken.getRawType();
                if (!Enum.class.isAssignableFrom(rawType) || rawType == Enum.class) {
                    return null;
                }
                if (!rawType.isEnum()) {
                    rawType = rawType.getSuperclass();
                }
                return new EnumTypeAdapter<T>((Class<T>)rawType);
            }
        };
    }
    
    public static <TT> TypeAdapterFactory newFactory(final TypeToken<TT> type, final TypeAdapter<TT> typeAdapter) {
        return new TypeAdapterFactory() {
            public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
                return (TypeAdapter<T>)(typeToken.equals(type) ? typeAdapter : null);
            }
        };
    }
    
    public static <TT> TypeAdapterFactory newFactory(final Class<TT> type, final TypeAdapter<TT> typeAdapter) {
        return new TypeAdapterFactory() {
            public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
                return (TypeAdapter<T>)((typeToken.getRawType() == type) ? typeAdapter : null);
            }
            
            public String toString() {
                return "Factory[type=" + type.getName() + ",adapter=" + typeAdapter + "]";
            }
        };
    }
    
    public static <TT> TypeAdapterFactory newFactory(final Class<TT> unboxed, final Class<TT> boxed, final TypeAdapter<? super TT> typeAdapter) {
        return new TypeAdapterFactory() {
            public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
                final Class<? super T> rawType = typeToken.getRawType();
                return (TypeAdapter<T>)((rawType == unboxed || rawType == boxed) ? typeAdapter : null);
            }
            
            public String toString() {
                return "Factory[type=" + boxed.getName() + "+" + unboxed.getName() + ",adapter=" + typeAdapter + "]";
            }
        };
    }
    
    public static <TT> TypeAdapterFactory newFactoryForMultipleTypes(final Class<TT> base, final Class<? extends TT> sub, final TypeAdapter<? super TT> typeAdapter) {
        return new TypeAdapterFactory() {
            public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
                final Class<? super T> rawType = typeToken.getRawType();
                return (TypeAdapter<T>)((rawType == base || rawType == sub) ? typeAdapter : null);
            }
            
            public String toString() {
                return "Factory[type=" + base.getName() + "+" + sub.getName() + ",adapter=" + typeAdapter + "]";
            }
        };
    }
    
    public static <TT> TypeAdapterFactory newTypeHierarchyFactory(final Class<TT> clazz, final TypeAdapter<TT> typeAdapter) {
        return new TypeAdapterFactory() {
            public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
                return (TypeAdapter<T>)(clazz.isAssignableFrom(typeToken.getRawType()) ? typeAdapter : null);
            }
            
            public String toString() {
                return "Factory[typeHierarchy=" + clazz.getName() + ",adapter=" + typeAdapter + "]";
            }
        };
    }
    
    static {
        CLASS = new TypeAdapter<Class>() {
            public void write(final JsonWriter out, final Class value) throws IOException {
                throw new UnsupportedOperationException("Attempted to serialize java.lang.Class: " + value.getName() + ". Forgot to register a type adapter?");
            }
            
            public Class read(final JsonReader in) throws IOException {
                throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
            }
        };
        CLASS_FACTORY = newFactory(Class.class, TypeAdapters.CLASS);
        BIT_SET = new TypeAdapter<BitSet>() {
            public BitSet read(final JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                final BitSet bitset = new BitSet();
                in.beginArray();
                int i = 0;
                for (JsonToken tokenType = in.peek(); tokenType != JsonToken.END_ARRAY; tokenType = in.peek()) {
                    boolean set = false;
                    switch (tokenType) {
                        case NUMBER: {
                            set = (in.nextInt() != 0);
                            break;
                        }
                        case BOOLEAN: {
                            set = in.nextBoolean();
                            break;
                        }
                        case STRING: {
                            final String stringValue = in.nextString();
                            try {
                                set = (Integer.parseInt(stringValue) != 0);
                                break;
                            }
                            catch (NumberFormatException e) {
                                throw new JsonSyntaxException("Error: Expecting: bitset number value (1, 0), Found: " + stringValue);
                            }
                            throw new JsonSyntaxException("Invalid bitset value type: " + tokenType);
                        }
                    }
                    if (set) {
                        bitset.set(i);
                    }
                    ++i;
                }
                in.endArray();
                return bitset;
            }
            
            public void write(final JsonWriter out, final BitSet src) throws IOException {
                if (src == null) {
                    out.nullValue();
                    return;
                }
                out.beginArray();
                for (int i = 0; i < src.length(); ++i) {
                    final int value = src.get(i) ? 1 : 0;
                    out.value(value);
                }
                out.endArray();
            }
        };
        BIT_SET_FACTORY = newFactory(BitSet.class, TypeAdapters.BIT_SET);
        BOOLEAN = new TypeAdapter<Boolean>() {
            public Boolean read(final JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                if (in.peek() == JsonToken.STRING) {
                    return Boolean.parseBoolean(in.nextString());
                }
                return in.nextBoolean();
            }
            
            public void write(final JsonWriter out, final Boolean value) throws IOException {
                if (value == null) {
                    out.nullValue();
                    return;
                }
                out.value(value);
            }
        };
        BOOLEAN_AS_STRING = new TypeAdapter<Boolean>() {
            public Boolean read(final JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                return Boolean.valueOf(in.nextString());
            }
            
            public void write(final JsonWriter out, final Boolean value) throws IOException {
                out.value((value == null) ? "null" : value.toString());
            }
        };
        BOOLEAN_FACTORY = newFactory(Boolean.TYPE, Boolean.class, TypeAdapters.BOOLEAN);
        BYTE = new TypeAdapter<Number>() {
            public Number read(final JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                try {
                    final int intValue = in.nextInt();
                    return (byte)intValue;
                }
                catch (NumberFormatException e) {
                    throw new JsonSyntaxException(e);
                }
            }
            
            public void write(final JsonWriter out, final Number value) throws IOException {
                out.value(value);
            }
        };
        BYTE_FACTORY = newFactory(Byte.TYPE, Byte.class, TypeAdapters.BYTE);
        SHORT = new TypeAdapter<Number>() {
            public Number read(final JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                try {
                    return (short)in.nextInt();
                }
                catch (NumberFormatException e) {
                    throw new JsonSyntaxException(e);
                }
            }
            
            public void write(final JsonWriter out, final Number value) throws IOException {
                out.value(value);
            }
        };
        SHORT_FACTORY = newFactory(Short.TYPE, Short.class, TypeAdapters.SHORT);
        INTEGER = new TypeAdapter<Number>() {
            public Number read(final JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                try {
                    return in.nextInt();
                }
                catch (NumberFormatException e) {
                    throw new JsonSyntaxException(e);
                }
            }
            
            public void write(final JsonWriter out, final Number value) throws IOException {
                out.value(value);
            }
        };
        INTEGER_FACTORY = newFactory(Integer.TYPE, Integer.class, TypeAdapters.INTEGER);
        LONG = new TypeAdapter<Number>() {
            public Number read(final JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                try {
                    return in.nextLong();
                }
                catch (NumberFormatException e) {
                    throw new JsonSyntaxException(e);
                }
            }
            
            public void write(final JsonWriter out, final Number value) throws IOException {
                out.value(value);
            }
        };
        FLOAT = new TypeAdapter<Number>() {
            public Number read(final JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                return (float)in.nextDouble();
            }
            
            public void write(final JsonWriter out, final Number value) throws IOException {
                out.value(value);
            }
        };
        DOUBLE = new TypeAdapter<Number>() {
            public Number read(final JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                return in.nextDouble();
            }
            
            public void write(final JsonWriter out, final Number value) throws IOException {
                out.value(value);
            }
        };
        NUMBER = new TypeAdapter<Number>() {
            public Number read(final JsonReader in) throws IOException {
                final JsonToken jsonToken = in.peek();
                switch (jsonToken) {
                    case NULL: {
                        in.nextNull();
                        return null;
                    }
                    case NUMBER: {
                        return new LazilyParsedNumber(in.nextString());
                    }
                    default: {
                        throw new JsonSyntaxException("Expecting number, got: " + jsonToken);
                    }
                }
            }
            
            public void write(final JsonWriter out, final Number value) throws IOException {
                out.value(value);
            }
        };
        NUMBER_FACTORY = newFactory(Number.class, TypeAdapters.NUMBER);
        CHARACTER = new TypeAdapter<Character>() {
            public Character read(final JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                final String str = in.nextString();
                if (str.length() != 1) {
                    throw new JsonSyntaxException("Expecting character, got: " + str);
                }
                return str.charAt(0);
            }
            
            public void write(final JsonWriter out, final Character value) throws IOException {
                out.value((value == null) ? null : String.valueOf(value));
            }
        };
        CHARACTER_FACTORY = newFactory(Character.TYPE, Character.class, TypeAdapters.CHARACTER);
        STRING = new TypeAdapter<String>() {
            public String read(final JsonReader in) throws IOException {
                final JsonToken peek = in.peek();
                if (peek == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                if (peek == JsonToken.BOOLEAN) {
                    return Boolean.toString(in.nextBoolean());
                }
                return in.nextString();
            }
            
            public void write(final JsonWriter out, final String value) throws IOException {
                out.value(value);
            }
        };
        STRING_FACTORY = newFactory(String.class, TypeAdapters.STRING);
        STRING_BUILDER = new TypeAdapter<StringBuilder>() {
            public StringBuilder read(final JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                return new StringBuilder(in.nextString());
            }
            
            public void write(final JsonWriter out, final StringBuilder value) throws IOException {
                out.value((value == null) ? null : value.toString());
            }
        };
        STRING_BUILDER_FACTORY = newFactory(StringBuilder.class, TypeAdapters.STRING_BUILDER);
        STRING_BUFFER = new TypeAdapter<StringBuffer>() {
            public StringBuffer read(final JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                return new StringBuffer(in.nextString());
            }
            
            public void write(final JsonWriter out, final StringBuffer value) throws IOException {
                out.value((value == null) ? null : value.toString());
            }
        };
        STRING_BUFFER_FACTORY = newFactory(StringBuffer.class, TypeAdapters.STRING_BUFFER);
        URL = new TypeAdapter<URL>() {
            public URL read(final JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                final String nextString = in.nextString();
                return "null".equals(nextString) ? null : new URL(nextString);
            }
            
            public void write(final JsonWriter out, final URL value) throws IOException {
                out.value((value == null) ? null : value.toExternalForm());
            }
        };
        URL_FACTORY = newFactory(URL.class, TypeAdapters.URL);
        URI = new TypeAdapter<URI>() {
            public URI read(final JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                try {
                    final String nextString = in.nextString();
                    return "null".equals(nextString) ? null : new URI(nextString);
                }
                catch (URISyntaxException e) {
                    throw new JsonIOException(e);
                }
            }
            
            public void write(final JsonWriter out, final URI value) throws IOException {
                out.value((value == null) ? null : value.toASCIIString());
            }
        };
        URI_FACTORY = newFactory(URI.class, TypeAdapters.URI);
        INET_ADDRESS = new TypeAdapter<InetAddress>() {
            public InetAddress read(final JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                return InetAddress.getByName(in.nextString());
            }
            
            public void write(final JsonWriter out, final InetAddress value) throws IOException {
                out.value((value == null) ? null : value.getHostAddress());
            }
        };
        INET_ADDRESS_FACTORY = newTypeHierarchyFactory(InetAddress.class, TypeAdapters.INET_ADDRESS);
        UUID = new TypeAdapter<UUID>() {
            public UUID read(final JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                return java.util.UUID.fromString(in.nextString());
            }
            
            public void write(final JsonWriter out, final UUID value) throws IOException {
                out.value((value == null) ? null : value.toString());
            }
        };
        UUID_FACTORY = newFactory(UUID.class, TypeAdapters.UUID);
        TIMESTAMP_FACTORY = new TypeAdapterFactory() {
            public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
                if (typeToken.getRawType() != Timestamp.class) {
                    return null;
                }
                final TypeAdapter<Date> dateTypeAdapter = gson.getAdapter(Date.class);
                return (TypeAdapter<T>)new TypeAdapter<Timestamp>() {
                    public Timestamp read(final JsonReader in) throws IOException {
                        final Date date = dateTypeAdapter.read(in);
                        return (date != null) ? new Timestamp(date.getTime()) : null;
                    }
                    
                    public void write(final JsonWriter out, final Timestamp value) throws IOException {
                        dateTypeAdapter.write(out, value);
                    }
                };
            }
        };
        CALENDAR = new TypeAdapter<Calendar>() {
            private static final String YEAR = "year";
            private static final String MONTH = "month";
            private static final String DAY_OF_MONTH = "dayOfMonth";
            private static final String HOUR_OF_DAY = "hourOfDay";
            private static final String MINUTE = "minute";
            private static final String SECOND = "second";
            
            public Calendar read(final JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                in.beginObject();
                int year = 0;
                int month = 0;
                int dayOfMonth = 0;
                int hourOfDay = 0;
                int minute = 0;
                int second = 0;
                while (in.peek() != JsonToken.END_OBJECT) {
                    final String name = in.nextName();
                    final int value = in.nextInt();
                    if ("year".equals(name)) {
                        year = value;
                    }
                    else if ("month".equals(name)) {
                        month = value;
                    }
                    else if ("dayOfMonth".equals(name)) {
                        dayOfMonth = value;
                    }
                    else if ("hourOfDay".equals(name)) {
                        hourOfDay = value;
                    }
                    else if ("minute".equals(name)) {
                        minute = value;
                    }
                    else {
                        if (!"second".equals(name)) {
                            continue;
                        }
                        second = value;
                    }
                }
                in.endObject();
                return new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute, second);
            }
            
            public void write(final JsonWriter out, final Calendar value) throws IOException {
                if (value == null) {
                    out.nullValue();
                    return;
                }
                out.beginObject();
                out.name("year");
                out.value(value.get(1));
                out.name("month");
                out.value(value.get(2));
                out.name("dayOfMonth");
                out.value(value.get(5));
                out.name("hourOfDay");
                out.value(value.get(11));
                out.name("minute");
                out.value(value.get(12));
                out.name("second");
                out.value(value.get(13));
                out.endObject();
            }
        };
        CALENDAR_FACTORY = newFactoryForMultipleTypes(Calendar.class, GregorianCalendar.class, TypeAdapters.CALENDAR);
        LOCALE = new TypeAdapter<Locale>() {
            public Locale read(final JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                final String locale = in.nextString();
                final StringTokenizer tokenizer = new StringTokenizer(locale, "_");
                String language = null;
                String country = null;
                String variant = null;
                if (tokenizer.hasMoreElements()) {
                    language = tokenizer.nextToken();
                }
                if (tokenizer.hasMoreElements()) {
                    country = tokenizer.nextToken();
                }
                if (tokenizer.hasMoreElements()) {
                    variant = tokenizer.nextToken();
                }
                if (country == null && variant == null) {
                    return new Locale(language);
                }
                if (variant == null) {
                    return new Locale(language, country);
                }
                return new Locale(language, country, variant);
            }
            
            public void write(final JsonWriter out, final Locale value) throws IOException {
                out.value((value == null) ? null : value.toString());
            }
        };
        LOCALE_FACTORY = newFactory(Locale.class, TypeAdapters.LOCALE);
        JSON_ELEMENT = new TypeAdapter<JsonElement>() {
            public JsonElement read(final JsonReader in) throws IOException {
                switch (in.peek()) {
                    case STRING: {
                        return new JsonPrimitive(in.nextString());
                    }
                    case NUMBER: {
                        final String number = in.nextString();
                        return new JsonPrimitive(new LazilyParsedNumber(number));
                    }
                    case BOOLEAN: {
                        return new JsonPrimitive(Boolean.valueOf(in.nextBoolean()));
                    }
                    case NULL: {
                        in.nextNull();
                        return JsonNull.INSTANCE;
                    }
                    case BEGIN_ARRAY: {
                        final JsonArray array = new JsonArray();
                        in.beginArray();
                        while (in.hasNext()) {
                            array.add(this.read(in));
                        }
                        in.endArray();
                        return array;
                    }
                    case BEGIN_OBJECT: {
                        final JsonObject object = new JsonObject();
                        in.beginObject();
                        while (in.hasNext()) {
                            object.add(in.nextName(), this.read(in));
                        }
                        in.endObject();
                        return object;
                    }
                    default: {
                        throw new IllegalArgumentException();
                    }
                }
            }
            
            public void write(final JsonWriter out, final JsonElement value) throws IOException {
                if (value == null || value.isJsonNull()) {
                    out.nullValue();
                }
                else if (value.isJsonPrimitive()) {
                    final JsonPrimitive primitive = value.getAsJsonPrimitive();
                    if (primitive.isNumber()) {
                        out.value(primitive.getAsNumber());
                    }
                    else if (primitive.isBoolean()) {
                        out.value(primitive.getAsBoolean());
                    }
                    else {
                        out.value(primitive.getAsString());
                    }
                }
                else if (value.isJsonArray()) {
                    out.beginArray();
                    for (final JsonElement e : value.getAsJsonArray()) {
                        this.write(out, e);
                    }
                    out.endArray();
                }
                else {
                    if (!value.isJsonObject()) {
                        throw new IllegalArgumentException("Couldn't write " + value.getClass());
                    }
                    out.beginObject();
                    for (final Map.Entry<String, JsonElement> e2 : value.getAsJsonObject().entrySet()) {
                        out.name(e2.getKey());
                        this.write(out, e2.getValue());
                    }
                    out.endObject();
                }
            }
        };
        JSON_ELEMENT_FACTORY = newFactory(JsonElement.class, TypeAdapters.JSON_ELEMENT);
        ENUM_FACTORY = newEnumTypeHierarchyFactory();
    }
    
    private static final class EnumTypeAdapter<T extends Enum<T>> extends TypeAdapter<T>
    {
        private final Map<String, T> nameToConstant;
        private final Map<T, String> constantToName;
        
        public EnumTypeAdapter(final Class<T> classOfT) {
            this.nameToConstant = new HashMap<String, T>();
            this.constantToName = new HashMap<T, String>();
            try {
                for (final T constant : classOfT.getEnumConstants()) {
                    String name = constant.name();
                    final SerializedName annotation = classOfT.getField(name).getAnnotation(SerializedName.class);
                    if (annotation != null) {
                        name = annotation.value();
                    }
                    this.nameToConstant.put(name, constant);
                    this.constantToName.put(constant, name);
                }
            }
            catch (NoSuchFieldException e) {
                throw new AssertionError();
            }
        }
        
        public T read(final JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            return this.nameToConstant.get(in.nextString());
        }
        
        public void write(final JsonWriter out, final T value) throws IOException {
            out.value((value == null) ? null : this.constantToName.get(value));
        }
    }
}
