// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.com.google.gson;

import org.bukkit.craftbukkit.libs.com.google.gson.internal.GsonInternalAccess;
import org.bukkit.craftbukkit.libs.com.google.gson.internal.bind.JsonTreeReader;
import java.io.EOFException;
import org.bukkit.craftbukkit.libs.com.google.gson.stream.MalformedJsonException;
import java.io.Reader;
import java.io.StringReader;
import org.bukkit.craftbukkit.libs.com.google.gson.internal.Primitives;
import java.io.Writer;
import org.bukkit.craftbukkit.libs.com.google.gson.internal.Streams;
import java.io.StringWriter;
import org.bukkit.craftbukkit.libs.com.google.gson.internal.bind.JsonTreeWriter;
import java.util.Iterator;
import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonWriter;
import java.io.IOException;
import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonToken;
import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonReader;
import org.bukkit.craftbukkit.libs.com.google.gson.internal.bind.ArrayTypeAdapter;
import org.bukkit.craftbukkit.libs.com.google.gson.internal.bind.MapTypeAdapterFactory;
import org.bukkit.craftbukkit.libs.com.google.gson.internal.bind.SqlDateTypeAdapter;
import org.bukkit.craftbukkit.libs.com.google.gson.internal.bind.TimeTypeAdapter;
import org.bukkit.craftbukkit.libs.com.google.gson.internal.bind.DateTypeAdapter;
import org.bukkit.craftbukkit.libs.com.google.gson.internal.bind.CollectionTypeAdapterFactory;
import java.util.Collection;
import org.bukkit.craftbukkit.libs.com.google.gson.internal.bind.ObjectTypeAdapter;
import org.bukkit.craftbukkit.libs.com.google.gson.internal.bind.BigIntegerTypeAdapter;
import java.math.BigInteger;
import org.bukkit.craftbukkit.libs.com.google.gson.internal.bind.BigDecimalTypeAdapter;
import java.math.BigDecimal;
import org.bukkit.craftbukkit.libs.com.google.gson.internal.bind.TypeAdapters;
import java.util.ArrayList;
import org.bukkit.craftbukkit.libs.com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import java.util.HashMap;
import java.lang.reflect.Type;
import java.util.Collections;
import org.bukkit.craftbukkit.libs.com.google.gson.internal.Excluder;
import org.bukkit.craftbukkit.libs.com.google.gson.internal.ConstructorConstructor;
import java.util.List;
import org.bukkit.craftbukkit.libs.com.google.gson.reflect.TypeToken;
import java.util.Map;

public final class Gson
{
    static final boolean DEFAULT_JSON_NON_EXECUTABLE = false;
    private static final String JSON_NON_EXECUTABLE_PREFIX = ")]}'\n";
    private final ThreadLocal<Map<TypeToken<?>, FutureTypeAdapter<?>>> calls;
    private final Map<TypeToken<?>, TypeAdapter<?>> typeTokenCache;
    private final List<TypeAdapterFactory> factories;
    private final ConstructorConstructor constructorConstructor;
    private final boolean serializeNulls;
    private final boolean htmlSafe;
    private final boolean generateNonExecutableJson;
    private final boolean prettyPrinting;
    final JsonDeserializationContext deserializationContext;
    final JsonSerializationContext serializationContext;
    
    public Gson() {
        this(Excluder.DEFAULT, FieldNamingPolicy.IDENTITY, Collections.emptyMap(), false, false, false, true, false, false, LongSerializationPolicy.DEFAULT, Collections.emptyList());
    }
    
    Gson(final Excluder excluder, final FieldNamingStrategy fieldNamingPolicy, final Map<Type, InstanceCreator<?>> instanceCreators, final boolean serializeNulls, final boolean complexMapKeySerialization, final boolean generateNonExecutableGson, final boolean htmlSafe, final boolean prettyPrinting, final boolean serializeSpecialFloatingPointValues, final LongSerializationPolicy longSerializationPolicy, final List<TypeAdapterFactory> typeAdapterFactories) {
        this.calls = new ThreadLocal<Map<TypeToken<?>, FutureTypeAdapter<?>>>() {
            protected Map<TypeToken<?>, FutureTypeAdapter<?>> initialValue() {
                return new HashMap<TypeToken<?>, FutureTypeAdapter<?>>();
            }
        };
        this.typeTokenCache = Collections.synchronizedMap(new HashMap<TypeToken<?>, TypeAdapter<?>>());
        this.deserializationContext = new JsonDeserializationContext() {
            public <T> T deserialize(final JsonElement json, final Type typeOfT) throws JsonParseException {
                return Gson.this.fromJson(json, typeOfT);
            }
        };
        this.serializationContext = new JsonSerializationContext() {
            public JsonElement serialize(final Object src) {
                return Gson.this.toJsonTree(src);
            }
            
            public JsonElement serialize(final Object src, final Type typeOfSrc) {
                return Gson.this.toJsonTree(src, typeOfSrc);
            }
        };
        this.constructorConstructor = new ConstructorConstructor(instanceCreators);
        this.serializeNulls = serializeNulls;
        this.generateNonExecutableJson = generateNonExecutableGson;
        this.htmlSafe = htmlSafe;
        this.prettyPrinting = prettyPrinting;
        final TypeAdapterFactory reflectiveTypeAdapterFactory = new ReflectiveTypeAdapterFactory(this.constructorConstructor, fieldNamingPolicy, excluder);
        final ConstructorConstructor constructorConstructor = new ConstructorConstructor();
        final List<TypeAdapterFactory> factories = new ArrayList<TypeAdapterFactory>();
        factories.add(TypeAdapters.STRING_FACTORY);
        factories.add(TypeAdapters.INTEGER_FACTORY);
        factories.add(TypeAdapters.BOOLEAN_FACTORY);
        factories.add(TypeAdapters.BYTE_FACTORY);
        factories.add(TypeAdapters.SHORT_FACTORY);
        factories.add(TypeAdapters.newFactory(Long.TYPE, Long.class, this.longAdapter(longSerializationPolicy)));
        factories.add(TypeAdapters.newFactory(Double.TYPE, Double.class, this.doubleAdapter(serializeSpecialFloatingPointValues)));
        factories.add(TypeAdapters.newFactory(Float.TYPE, Float.class, this.floatAdapter(serializeSpecialFloatingPointValues)));
        factories.add(excluder);
        factories.add(TypeAdapters.NUMBER_FACTORY);
        factories.add(TypeAdapters.CHARACTER_FACTORY);
        factories.add(TypeAdapters.STRING_BUILDER_FACTORY);
        factories.add(TypeAdapters.STRING_BUFFER_FACTORY);
        factories.add(TypeAdapters.newFactory(BigDecimal.class, new BigDecimalTypeAdapter()));
        factories.add(TypeAdapters.newFactory(BigInteger.class, new BigIntegerTypeAdapter()));
        factories.add(TypeAdapters.JSON_ELEMENT_FACTORY);
        factories.add(ObjectTypeAdapter.FACTORY);
        factories.addAll(typeAdapterFactories);
        factories.add(new CollectionTypeAdapterFactory(constructorConstructor));
        factories.add(TypeAdapters.URL_FACTORY);
        factories.add(TypeAdapters.URI_FACTORY);
        factories.add(TypeAdapters.UUID_FACTORY);
        factories.add(TypeAdapters.LOCALE_FACTORY);
        factories.add(TypeAdapters.INET_ADDRESS_FACTORY);
        factories.add(TypeAdapters.BIT_SET_FACTORY);
        factories.add(DateTypeAdapter.FACTORY);
        factories.add(TypeAdapters.CALENDAR_FACTORY);
        factories.add(TimeTypeAdapter.FACTORY);
        factories.add(SqlDateTypeAdapter.FACTORY);
        factories.add(TypeAdapters.TIMESTAMP_FACTORY);
        factories.add(new MapTypeAdapterFactory(constructorConstructor, complexMapKeySerialization));
        factories.add(ArrayTypeAdapter.FACTORY);
        factories.add(TypeAdapters.ENUM_FACTORY);
        factories.add(TypeAdapters.CLASS_FACTORY);
        factories.add(reflectiveTypeAdapterFactory);
        this.factories = Collections.unmodifiableList((List<? extends TypeAdapterFactory>)factories);
    }
    
    private TypeAdapter<Number> doubleAdapter(final boolean serializeSpecialFloatingPointValues) {
        if (serializeSpecialFloatingPointValues) {
            return TypeAdapters.DOUBLE;
        }
        return new TypeAdapter<Number>() {
            public Double read(final JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                return in.nextDouble();
            }
            
            public void write(final JsonWriter out, final Number value) throws IOException {
                if (value == null) {
                    out.nullValue();
                    return;
                }
                final double doubleValue = value.doubleValue();
                Gson.this.checkValidFloatingPoint(doubleValue);
                out.value(value);
            }
        };
    }
    
    private TypeAdapter<Number> floatAdapter(final boolean serializeSpecialFloatingPointValues) {
        if (serializeSpecialFloatingPointValues) {
            return TypeAdapters.FLOAT;
        }
        return new TypeAdapter<Number>() {
            public Float read(final JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                return (float)in.nextDouble();
            }
            
            public void write(final JsonWriter out, final Number value) throws IOException {
                if (value == null) {
                    out.nullValue();
                    return;
                }
                final float floatValue = value.floatValue();
                Gson.this.checkValidFloatingPoint(floatValue);
                out.value(value);
            }
        };
    }
    
    private void checkValidFloatingPoint(final double value) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException(value + " is not a valid double value as per JSON specification. To override this" + " behavior, use GsonBuilder.serializeSpecialDoubleValues() method.");
        }
    }
    
    private TypeAdapter<Number> longAdapter(final LongSerializationPolicy longSerializationPolicy) {
        if (longSerializationPolicy == LongSerializationPolicy.DEFAULT) {
            return TypeAdapters.LONG;
        }
        return new TypeAdapter<Number>() {
            public Number read(final JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                return in.nextLong();
            }
            
            public void write(final JsonWriter out, final Number value) throws IOException {
                if (value == null) {
                    out.nullValue();
                    return;
                }
                out.value(value.toString());
            }
        };
    }
    
    public <T> TypeAdapter<T> getAdapter(final TypeToken<T> type) {
        final TypeAdapter<?> cached = this.typeTokenCache.get(type);
        if (cached != null) {
            return (TypeAdapter<T>)cached;
        }
        final Map<TypeToken<?>, FutureTypeAdapter<?>> threadCalls = this.calls.get();
        final FutureTypeAdapter<T> ongoingCall = (FutureTypeAdapter<T>)threadCalls.get(type);
        if (ongoingCall != null) {
            return ongoingCall;
        }
        final FutureTypeAdapter<T> call = new FutureTypeAdapter<T>();
        threadCalls.put(type, call);
        try {
            for (final TypeAdapterFactory factory : this.factories) {
                final TypeAdapter<T> candidate = factory.create(this, type);
                if (candidate != null) {
                    call.setDelegate(candidate);
                    this.typeTokenCache.put(type, candidate);
                    return candidate;
                }
            }
            throw new IllegalArgumentException("GSON cannot handle " + type);
        }
        finally {
            threadCalls.remove(type);
        }
    }
    
    public <T> TypeAdapter<T> getAdapter(final Class<T> type) {
        return this.getAdapter((TypeToken<T>)TypeToken.get((Class<T>)type));
    }
    
    public JsonElement toJsonTree(final Object src) {
        if (src == null) {
            return JsonNull.INSTANCE;
        }
        return this.toJsonTree(src, src.getClass());
    }
    
    public JsonElement toJsonTree(final Object src, final Type typeOfSrc) {
        final JsonTreeWriter writer = new JsonTreeWriter();
        this.toJson(src, typeOfSrc, writer);
        return writer.get();
    }
    
    public String toJson(final Object src) {
        if (src == null) {
            return this.toJson(JsonNull.INSTANCE);
        }
        return this.toJson(src, src.getClass());
    }
    
    public String toJson(final Object src, final Type typeOfSrc) {
        final StringWriter writer = new StringWriter();
        this.toJson(src, typeOfSrc, writer);
        return writer.toString();
    }
    
    public void toJson(final Object src, final Appendable writer) throws JsonIOException {
        if (src != null) {
            this.toJson(src, src.getClass(), writer);
        }
        else {
            this.toJson(JsonNull.INSTANCE, writer);
        }
    }
    
    public void toJson(final Object src, final Type typeOfSrc, final Appendable writer) throws JsonIOException {
        try {
            final JsonWriter jsonWriter = this.newJsonWriter(Streams.writerForAppendable(writer));
            this.toJson(src, typeOfSrc, jsonWriter);
        }
        catch (IOException e) {
            throw new JsonIOException(e);
        }
    }
    
    public void toJson(final Object src, final Type typeOfSrc, final JsonWriter writer) throws JsonIOException {
        final TypeAdapter<?> adapter = this.getAdapter(TypeToken.get(typeOfSrc));
        final boolean oldLenient = writer.isLenient();
        writer.setLenient(true);
        final boolean oldHtmlSafe = writer.isHtmlSafe();
        writer.setHtmlSafe(this.htmlSafe);
        final boolean oldSerializeNulls = writer.getSerializeNulls();
        writer.setSerializeNulls(this.serializeNulls);
        try {
            adapter.write(writer, src);
        }
        catch (IOException e) {
            throw new JsonIOException(e);
        }
        finally {
            writer.setLenient(oldLenient);
            writer.setHtmlSafe(oldHtmlSafe);
            writer.setSerializeNulls(oldSerializeNulls);
        }
    }
    
    public String toJson(final JsonElement jsonElement) {
        final StringWriter writer = new StringWriter();
        this.toJson(jsonElement, writer);
        return writer.toString();
    }
    
    public void toJson(final JsonElement jsonElement, final Appendable writer) throws JsonIOException {
        try {
            final JsonWriter jsonWriter = this.newJsonWriter(Streams.writerForAppendable(writer));
            this.toJson(jsonElement, jsonWriter);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private JsonWriter newJsonWriter(final Writer writer) throws IOException {
        if (this.generateNonExecutableJson) {
            writer.write(")]}'\n");
        }
        final JsonWriter jsonWriter = new JsonWriter(writer);
        if (this.prettyPrinting) {
            jsonWriter.setIndent("  ");
        }
        jsonWriter.setSerializeNulls(this.serializeNulls);
        return jsonWriter;
    }
    
    public void toJson(final JsonElement jsonElement, final JsonWriter writer) throws JsonIOException {
        final boolean oldLenient = writer.isLenient();
        writer.setLenient(true);
        final boolean oldHtmlSafe = writer.isHtmlSafe();
        writer.setHtmlSafe(this.htmlSafe);
        final boolean oldSerializeNulls = writer.getSerializeNulls();
        writer.setSerializeNulls(this.serializeNulls);
        try {
            Streams.write(jsonElement, writer);
        }
        catch (IOException e) {
            throw new JsonIOException(e);
        }
        finally {
            writer.setLenient(oldLenient);
            writer.setHtmlSafe(oldHtmlSafe);
            writer.setSerializeNulls(oldSerializeNulls);
        }
    }
    
    public <T> T fromJson(final String json, final Class<T> classOfT) throws JsonSyntaxException {
        final Object object = this.fromJson(json, (Type)classOfT);
        return Primitives.wrap(classOfT).cast(object);
    }
    
    public <T> T fromJson(final String json, final Type typeOfT) throws JsonSyntaxException {
        if (json == null) {
            return null;
        }
        final StringReader reader = new StringReader(json);
        final T target = this.fromJson(reader, typeOfT);
        return target;
    }
    
    public <T> T fromJson(final Reader json, final Class<T> classOfT) throws JsonSyntaxException, JsonIOException {
        final JsonReader jsonReader = new JsonReader(json);
        final Object object = this.fromJson(jsonReader, classOfT);
        assertFullConsumption(object, jsonReader);
        return Primitives.wrap(classOfT).cast(object);
    }
    
    public <T> T fromJson(final Reader json, final Type typeOfT) throws JsonIOException, JsonSyntaxException {
        final JsonReader jsonReader = new JsonReader(json);
        final T object = this.fromJson(jsonReader, typeOfT);
        assertFullConsumption(object, jsonReader);
        return object;
    }
    
    private static void assertFullConsumption(final Object obj, final JsonReader reader) {
        try {
            if (obj != null && reader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonIOException("JSON document was not fully consumed.");
            }
        }
        catch (MalformedJsonException e) {
            throw new JsonSyntaxException(e);
        }
        catch (IOException e2) {
            throw new JsonIOException(e2);
        }
    }
    
    public <T> T fromJson(final JsonReader reader, final Type typeOfT) throws JsonIOException, JsonSyntaxException {
        boolean isEmpty = true;
        final boolean oldLenient = reader.isLenient();
        reader.setLenient(true);
        try {
            reader.peek();
            isEmpty = false;
            final TypeAdapter<T> typeAdapter = this.getAdapter(TypeToken.get(typeOfT));
            return typeAdapter.read(reader);
        }
        catch (EOFException e) {
            if (isEmpty) {
                return null;
            }
            throw new JsonSyntaxException(e);
        }
        catch (IllegalStateException e2) {
            throw new JsonSyntaxException(e2);
        }
        catch (IOException e3) {
            throw new JsonSyntaxException(e3);
        }
        finally {
            reader.setLenient(oldLenient);
        }
    }
    
    public <T> T fromJson(final JsonElement json, final Class<T> classOfT) throws JsonSyntaxException {
        final Object object = this.fromJson(json, (Type)classOfT);
        return Primitives.wrap(classOfT).cast(object);
    }
    
    public <T> T fromJson(final JsonElement json, final Type typeOfT) throws JsonSyntaxException {
        if (json == null) {
            return null;
        }
        return this.fromJson(new JsonTreeReader(json), typeOfT);
    }
    
    public String toString() {
        final StringBuilder sb = new StringBuilder("{").append("serializeNulls:").append(this.serializeNulls).append("factories:").append(this.factories).append(",instanceCreators:").append(this.constructorConstructor).append("}");
        return sb.toString();
    }
    
    static {
        GsonInternalAccess.INSTANCE = new GsonInternalAccess() {
            public <T> TypeAdapter<T> getNextAdapter(final Gson gson, final TypeAdapterFactory skipPast, final TypeToken<T> type) {
                boolean skipPastFound = false;
                for (final TypeAdapterFactory factory : gson.factories) {
                    if (!skipPastFound) {
                        if (factory != skipPast) {
                            continue;
                        }
                        skipPastFound = true;
                    }
                    else {
                        final TypeAdapter<T> candidate = factory.create(gson, type);
                        if (candidate != null) {
                            return candidate;
                        }
                        continue;
                    }
                }
                throw new IllegalArgumentException("GSON cannot serialize " + type);
            }
        };
    }
    
    static class FutureTypeAdapter<T> extends TypeAdapter<T>
    {
        private TypeAdapter<T> delegate;
        
        public void setDelegate(final TypeAdapter<T> typeAdapter) {
            if (this.delegate != null) {
                throw new AssertionError();
            }
            this.delegate = typeAdapter;
        }
        
        public T read(final JsonReader in) throws IOException {
            if (this.delegate == null) {
                throw new IllegalStateException();
            }
            return this.delegate.read(in);
        }
        
        public void write(final JsonWriter out, final T value) throws IOException {
            if (this.delegate == null) {
                throw new IllegalStateException();
            }
            this.delegate.write(out, value);
        }
    }
}
